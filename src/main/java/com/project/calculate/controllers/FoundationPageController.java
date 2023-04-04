package com.project.calculate.controllers;

import com.project.calculate.CalculateApplication;
import com.project.calculate.entity.*;
import com.project.calculate.form.FoundationForm;
import com.project.calculate.form.FrameForm;
import com.project.calculate.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.*;

@Controller
public class FoundationPageController {

    private final static String CONCRETE = "Бетон";
    private final static String CONCRETE_PILES = "Бетонные сваи";

    @Autowired
    private StructuralElementBasementRepository structuralElementBasementRepository;
    @Autowired
    private MaterialsRepository materialsRepository;
    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private CalculationRepository calculationRepository;
    @Autowired
    private CalculationStatusRepository calculationStatusRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private MeasurementUnitRepository measurementUnitRepository;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/foundationPage", method = RequestMethod.GET)
    public String structuralElementBasementPage(HttpServletRequest request, Model model,
                                             @RequestParam(name = "customerId", defaultValue = "") Long customerId,
                                             @RequestParam(name = "calculationId", defaultValue = "", required = false) Long calculationId) {
        model.addAttribute("foundationForm", new FoundationForm());
        model.addAttribute("customerId", customerId);
        if (calculationId != null) {
            model.addAttribute("calculationId", calculationId);
            model.addAttribute("adress", calculationRepository.getReferenceById(calculationId).getAddressObjectConstractions());
        } else {
            model.addAttribute("adress", "");
        }

        List<Material> materialList = materialsRepository.findAll();
        List<Material> concreteList = new ArrayList<>();
        List<Material> concretePilesList = new ArrayList<>();
        for (Material x : materialList) {
            if (x.getMaterialType().equals("Бетон"))
                concreteList.add(x);
            else if (x.getMaterialType().equals("Свая"))
                concretePilesList.add(x);
        }
        model.addAttribute("concreteList", concreteList);
        model.addAttribute("concretePilesList", concretePilesList);

        //Отображение данных о клиенте
        Customer customer = customerRepository.findById(customerId).get();
        model.addAttribute("customerId", customerId);
        String Customers_info = customer.getFullName() + "<br/>" + customer.getAdress();
        model.addAttribute("Customers_info", Customers_info);
        model.addAttribute("customer_phone", customer.getPhone());
        model.addAttribute("customer_phone_str", customer.getPhoneMask());

        //Отображение ФИ:должность пользователя
        String principal = request.getUserPrincipal().getName();
        User user = userRepository.findByLogin(principal);
        String user_name = user.getUserName();
        String user_role = "";
        Collection<? extends GrantedAuthority> roles = user.getAuthorities();
        for (GrantedAuthority x : roles) {
            if (x.getAuthority().equals("USER"))
                user_role = "Менеджер";
            else if (x.getAuthority().equals("ADMIN"))
                user_role = "Администратор";
        }
        String user_info = user_name + ": " + user_role;
        model.addAttribute("user_name", user_info);
        return "foundationPage";
    }

    @RequestMapping(value = { "/foundationPage" }, method = RequestMethod.POST)
    public String saveBasement(HttpServletRequest request, Model model,
                            @ModelAttribute("foundationForm") FoundationForm foundationForm,
                            @RequestParam(value = "calculateButton", required = false) Long customerId,
                            @RequestParam(name = "calculationId", defaultValue = "") Long calculationId,
                            @RequestParam(name = "adress", defaultValue = "") String adress) {

        StructuralElementBasement basement = new StructuralElementBasement();
        try {
            basement.setPerimeterOfExternalWalls(foundationForm.getExternalWallLength());
            basement.setInternalWallLength(foundationForm.getInternalWallLength());
            basement.setConcrete(foundationForm.getConcrete());
            basement.setConcretePiles(foundationForm.getConcretePiles());
        } catch (Exception e){
            System.out.println(e);
            return "redirect:/foundationPage?customerId=" + customerId;
        }

        Customer customer = customerRepository.findById(customerId).get();

        int calculationNumber = 1;
        Calculation calculation = new Calculation();
        if (calculationId != null)
            calculation = calculationRepository.getReferenceById(calculationId);
        else {
            try {
                calculationNumber = calculationRepository.getMaxNumber(customerId) + 1;

                calculation.setAddressObjectConstractions(adress);
                calculation.setCreatedDate(LocalDate.now());
                calculation.setNumber(calculationNumber);
                calculation.setCustomer(customer);
                calculation.setСalculationState(calculationStatusRepository.findById(1L).get());
            } catch (Exception e){
                System.out.println(e);
                return "redirect:/foundationPage?customerId=" + customerId;
            }
        }

        Set<Result> results = new HashSet<>();

        //Бетон
        Material concreteMaterial = materialsRepository.findByName(foundationForm.getConcrete());
        Result concreteResult = createResult(basement, calculation, concreteMaterial, foundationForm.getExternalWallLength() + foundationForm.getInternalWallLength(), CONCRETE);
        //Бетонные сваи
        Material concretePilesMaterial = materialsRepository.findByName(foundationForm.getConcretePiles());
        Result concretePilesResult = createResult(basement, calculation, concretePilesMaterial, foundationForm.getExternalWallLength() + foundationForm.getInternalWallLength(), CONCRETE_PILES);

        results.add(concreteResult);
        results.add(concretePilesResult);

        try{
            basement.setResults(results);
            structuralElementBasementRepository.save(basement);
            if (calculationId == null)
                calculationRepository.save(calculation);
            resultRepository.saveAll(results);
        } catch (Exception e){
            System.out.println(e);
            LoggerFactory.getLogger(CalculateApplication.class).error("ERROR: " + e.getMessage());
        }

        if (calculationId == null)
            calculationId = calculationRepository.getMaxId();
        return "redirect:/calculation?calculationId=" + calculationId;
    }

    private int getAmountConcretePiles(double length){
        Double amount = length / 2;
        return (int)Math.ceil(amount);
    }
    private int getAmountConcrete(double length){
        Double amount = length * 0.4 * 0.4;
        return (int)Math.ceil(amount);
    }

    private Result createResult(StructuralElementBasement basement, Calculation calculation, Material material, Double wallLength, String elementType){
        Result result = new Result();
        try {
            result.setBasement(basement);
            result.setCalculation(calculation);
            result.setMaterial(material.getName());
            MaterialCharacteristic materialCharacteristic = material.getMaterialCharacteristics().iterator().next();
            result.setMaterialCharacteristics(materialCharacteristic);
            int amount = 0;
            if (elementType.equals(CONCRETE))
                amount = getAmountConcrete(wallLength);
            else if (elementType.equals(CONCRETE_PILES))
                amount = getAmountConcretePiles(wallLength);
            result.setAmount(amount);
            PriceList priceList = materialCharacteristic.getPriceLists().iterator().next();
            result.setPrice(priceList.getPurchasePrice() * amount);
            result.setFullPrice(priceList.getSellingPrice() * amount);
            result.setMeasurementUnit(materialCharacteristic.getMeasurementUnit().getMeasurementUnitsName());
            result.setElementType(elementType);
        } catch (Exception e){
            System.out.println(e);
        }

        return result;
    }
}

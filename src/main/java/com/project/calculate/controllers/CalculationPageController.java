package com.project.calculate.controllers;

import com.project.calculate.CalculateApplication;
import com.project.calculate.entity.*;
import com.project.calculate.form.BasementFormToCalculationPage;
import com.project.calculate.form.CalculationForm;
import com.project.calculate.form.FrameFormToCalculationPage;
import com.project.calculate.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class CalculationPageController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CalculationRepository calculationRepository;
    @Autowired
    private CalculationStatusRepository calculationStatusRepository;
    @Autowired
    private StructuralElementFrameRepository structuralElementFrameRepository;

    @RequestMapping(value = "/calculation", method = RequestMethod.GET)
    public String calculationPage(HttpServletRequest request, Model model,
                                  @RequestParam(name = "calculationId", defaultValue = "") Long calculationId) {
        Calculation calculation = calculationRepository.getReferenceById(calculationId);
        Set<Result> results = calculation.getResults();

        double allPrice = 0;

        Set<StructuralElementFrame> frames = new HashSet<>();
        Long frameId = -1l;
        BasementFormToCalculationPage basement = null;
        for (Result x : results){
            if (x.getFrame() != null){
                Long id = x.getFrame().getId();
                if (id != frameId){
                    frames.add(structuralElementFrameRepository.getReferenceById(id));
                    frameId = id;
                }
            }
            else if (x.getBasement() != null && basement == null){
                basement = new BasementFormToCalculationPage(x.getBasement());
                allPrice += basement.getFullPrice();
            }
        }

        Set<FrameFormToCalculationPage> frameForms = new HashSet<>();

        for (StructuralElementFrame x : frames){
            FrameFormToCalculationPage frameForm = new FrameFormToCalculationPage();
            frameForm.setFloorNumber(x.getFloorNumber());
            Set<Result> frameResults = x.getResults();
            Set<Result> resultsExternal = new HashSet<>();
            Set<Result> resultsInternal = new HashSet<>();
            Set<Result> resultsOverlap = new HashSet<>();
            double externalPrice = 0;
            double internalPrice = 0;
            double overlapPrice = 0;
            for (Result result : frameResults) {
                if (result.getElementType().equals("Внешние стены")){
                    resultsExternal.add(result);
                    externalPrice += result.getPrice();
                }
                else if (result.getElementType().equals("Внутренние стены")){
                    resultsInternal.add(result);
                    internalPrice += result.getPrice();
                }
                else if (result.getElementType().equals("Перекрытие")){
                    resultsOverlap.add(result);
                    overlapPrice += result.getPrice();
                }
            }
            frameForm.setExternalPrice(getPriceToStringFormat(externalPrice));
            frameForm.setInternalPrice(getPriceToStringFormat(internalPrice));
            frameForm.setOverlapPrice(getPriceToStringFormat(overlapPrice));
            frameForm.setFullPriceMoneyFormat(getPriceToStringFormat(externalPrice + internalPrice + overlapPrice));
            frameForm.setFullPrice(externalPrice + internalPrice + overlapPrice);
            frameForm.setResultsExternal(resultsExternal);
            frameForm.setResultsInternal(resultsInternal);
            frameForm.setResultsOverlap(resultsOverlap);
            frameForms.add(frameForm);
        }


        for (FrameFormToCalculationPage x : frameForms){
            allPrice += x.getFullPrice();
        }

        CalculationStatus calculationState = calculation.getСalculationState();
        List<CalculationStatus> statuses = new ArrayList<>();
        if (calculationState.getId() == 1){
            statuses.add(new CalculationStatus(2l, "Не актуален"));
            statuses.add(new CalculationStatus(3l, "Заключен договор"));
        }
        else if (calculationState.getId() == 2){
            statuses.add(new CalculationStatus(1l, "Актуален"));
        }

        Comparator<FrameFormToCalculationPage> comparator = (left, right) -> left.getFloorNumber() - right.getFloorNumber();
        List<FrameFormToCalculationPage> frameFormsSorted = new ArrayList<>(frameForms);
        Collections.sort(frameFormsSorted, comparator);

        model.addAttribute("customerId", calculation.getCustomer().getId());
        model.addAttribute("basement", basement);
        model.addAttribute("frames", frameFormsSorted);
        model.addAttribute("allPrice", getPriceToStringFormat(allPrice));
        model.addAttribute("calculationId", calculation.getId());
        model.addAttribute("calculationDate", calculation.getCreatedDate().toString());
        model.addAttribute("calculationAdres", calculation.getAddressObjectConstractions());
        model.addAttribute("calculationStatus", calculationState.getTitle());
        model.addAttribute("calculationStatusId", calculationState.getId());
        model.addAttribute("calculationStates", statuses);
        model.addAttribute("calculationNumber", calculation.getNumber());

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

        //Отображение данных о клиенте
        Customer customer = calculation.getCustomer();
        String Customers_info = customer.getFullName() + "<br/>" + customer.getAdress();
        model.addAttribute("Customers_info", Customers_info);
        model.addAttribute("customer_phone", customer.getPhone());
        model.addAttribute("customer_phone_str", customer.getPhoneMask());
        model.addAttribute("id", customer.getId());
        return "calculation";
    }

    /**
     * Метод переводящий число в "денежный" формат
     * @param price
     * @return String
     */
    private String getPriceToStringFormat(double price){
        return String.format("%.2f", price) + " Руб";
    }

    /**
     * POST запрос. Изменяет статус расчета.
     * @param calculationStateId
     * @param calculationId
     */
    @RequestMapping(value = "/calculation", method = RequestMethod.POST)
    public String calculationPageChangeCalculationState(@RequestParam(name = "changeCalculationState") Long calculationStateId,
                                  @RequestParam(name = "calculationId") Long calculationId) {
        Calculation calculation = calculationRepository.getReferenceById(calculationId);
        calculation.setСalculationState(calculationStatusRepository.findById(calculationStateId).get());
        calculationRepository.save(calculation);
        return "redirect:/calculation?calculationId=" + calculationId;
    }
}

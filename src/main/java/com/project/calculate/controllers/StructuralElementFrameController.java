package com.project.calculate.controllers;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.project.calculate.CalculateApplication;
import com.project.calculate.entity.*;
import com.project.calculate.form.CalculationInfo;
import com.project.calculate.form.ClientForm;
import com.project.calculate.form.FrameForm;
import com.project.calculate.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import net.minidev.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.*;

@Controller
public class StructuralElementFrameController {

    @Autowired
    private StructuralElementFrameRepository structuralElementFrameRepository;
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
    private MaterialCharacteristicsRepository materialCharacteristicsRepository;
    @Autowired
    private PriceListRepository priceListRepository;

    @RequestMapping(value = "/framePage", method = RequestMethod.GET)
    public String structuralElementFramePage(HttpServletRequest request, Model model, @RequestParam(name = "customerId", defaultValue = "") Long customerId) {
        model.addAttribute("frameForm", new FrameForm());
        model.addAttribute("calculationInfo", new CalculationInfo());
        model.addAttribute("customerId", customerId);

        List<Material> materialList = materialsRepository.findAll();
        List<Material> OsbList = new ArrayList<>();
        List<Material> InsulationList = new ArrayList<>();
        List<Material> WaterproofingsList = new ArrayList<>();
        List<Material> WindscreensList = new ArrayList<>();
        for (Material x : materialList) {
            if (x.getMaterialType().equals("ОСБ"))
                OsbList.add(x);
            else if (x.getMaterialType().equals("Утеплитель"))
                InsulationList.add(x);
            else if (x.getMaterialType().equals("Парогидроизоляция"))
                WaterproofingsList.add(x);
            else if (x.getMaterialType().equals("Ветрозащита"))
                WindscreensList.add(x);
        }
        model.addAttribute("OsbList", OsbList);
        model.addAttribute("Insulations", InsulationList);
        model.addAttribute("Waterproofings", WaterproofingsList);
        model.addAttribute("Windscreens", WindscreensList);
        return "framePage";
    }

    @RequestMapping(value = { "/framePage" }, method = RequestMethod.POST)
    public String saveFrame(HttpServletRequest request, Model model,
                            @ModelAttribute("frameForm") FrameForm frameForm,
                            @ModelAttribute("calculationInfo") CalculationInfo calculationInfo,
                            @RequestParam("calculateButton") Long customerId) {

        boolean error = false;

        //Получаем значения из формы
        int height = frameForm.getHeight();
        double perimeter_of_external_walls = frameForm.getPerimeter_of_external_walls();
        double base_area = frameForm.getBase_area();
        double external_wall_thickness = frameForm.getExternal_wall_thickness();
        double internal_wall_length = frameForm.getInternal_wall_length();
        double internal_wall_thickness = frameForm.getInternal_wall_thickness();
        String OSB_external_wall = frameForm.getOSB_external_wall();
        String steam_waterproofing_external = frameForm.getSteam_waterproofing_external();
        String windscreen_external_wall = frameForm.getWindscreen_external_wall();
        String insulation_external_wall = frameForm.getInsulation_external_wall();
        String OSB_internal_wal = frameForm.getOSB_internal_wal();
        String OSB_thickness = frameForm.getOSB_thickness();
        String steam_waterproofing_thicknes = frameForm.getSteam_waterproofing_external();
        String windscreen_thickness = frameForm.getWindscreen_thickness();
        String insulation__thickness = frameForm.getInsulation__thickness();
        int overlap_thickness = frameForm.getOverlap_thickness();
        int floor_number = frameForm.getFloor_number();

        Double externalWallSquare = perimeter_of_external_walls * height * 2;
        Double internalWallSquare = internal_wall_length * height * 2;

        StructuralElementFrame frame = new StructuralElementFrame();
        try {
            frame.setFloorHeight(height);
            frame.setPerimeterOfExternalWalls(perimeter_of_external_walls);
            frame.setBaseArea(base_area);
            frame.setExternalWallThickness(external_wall_thickness);
            frame.setInternalWallLength(internal_wall_length);
            frame.setInternalWallThickness(internal_wall_thickness);
            frame.setOsbExternalWall(OSB_external_wall);
            frame.setSteamWaterproofingExternalWall(steam_waterproofing_external);
            frame.setWindscreenExternalWall(windscreen_external_wall);
            frame.setInsulationExternalWall(insulation_external_wall);
            frame.setOsbInternalWal(OSB_internal_wal);
            frame.setOsbThickness(OSB_thickness);
            frame.setSteamWaterproofingThicknes(steam_waterproofing_thicknes);
            frame.setWindscreenThickness(windscreen_thickness);
            frame.setInsulationThickness(insulation__thickness);
            frame.setOverlapThickness(overlap_thickness);
            frame.setFloorNumber(floor_number);
            frame.setAmountFloor(1);
        } catch (Exception e){
            System.out.println(e);
            LoggerFactory.getLogger(CalculateApplication.class).error("FRAME ERROR: " + e.getMessage());
            error = true;
        }
        Calculation calculation = new Calculation();
        try {
            calculation.setAddressObjectConstractions(calculationInfo.getAdress());
            calculation.setCreatedDate(LocalDate.now());
            calculation.setNumber(calculationInfo.getAmountFloor());
            calculation.setCustomer(customerRepository.findById(customerId).get());
            calculation.setСalculationState(calculationStatusRepository.findById(1L).get());
        } catch (Exception e){
            System.out.println(e);
            LoggerFactory.getLogger(CalculateApplication.class).error("CALCULATION ERROR: " + e.getMessage());
            error = true;
        }

        Long newResultsId = 1L;
        try {
            newResultsId = resultRepository.getMaxId() + 1;
        } catch (Exception e){
            System.out.println(e);
        }

        Set<Result> results = new HashSet<>();
        Set<StructuralElementFrame> frames = new HashSet<>();
        frames.add(frame);

        Result resultOsbExternalWall = new Result();
        try {
            resultOsbExternalWall.setStructuralElementFrames(frames);
            resultOsbExternalWall.setId(newResultsId);
            resultOsbExternalWall.setCalculation(calculation);
            resultOsbExternalWall.setMaterial(OSB_external_wall);
            MaterialCharacteristic materialCharacteristic = materialsRepository.findByName(OSB_external_wall).getMaterialCharacteristics().iterator().next();
            resultOsbExternalWall.setMaterialCharacteristics(materialCharacteristic);
            Integer osbExternalWallAmount = getMaterialAmount(externalWallSquare, materialCharacteristic.getLength() * materialCharacteristic.getWedth());
            resultOsbExternalWall.setAmount(osbExternalWallAmount);
            PriceList priceList = materialCharacteristic.getPriceLists().iterator().next();
            resultOsbExternalWall.setPrice(priceList.getPurchasePrice() * osbExternalWallAmount);
            resultOsbExternalWall.setFullPrice(priceList.getSellingPrice() * osbExternalWallAmount);
            resultOsbExternalWall.setMeasurementUnit(measurementUnitRepository.findById(1L).get().getMeasurementUnitsName());
            results.add(resultOsbExternalWall);
            newResultsId++;
        } catch (Exception e){
            System.out.println(e);
            LoggerFactory.getLogger(CalculateApplication.class).error("RESULTS ERROR: " + e.getMessage());
            error = true;
        }
        Result resultSteamWaterproofingExternal = new Result();
        try {
            resultSteamWaterproofingExternal.setStructuralElementFrames(frames);
            resultSteamWaterproofingExternal.setId(newResultsId);
            resultSteamWaterproofingExternal.setCalculation(calculation);
            resultSteamWaterproofingExternal.setMaterial(steam_waterproofing_external);
            MaterialCharacteristic materialCharacteristic = materialsRepository.findByName(steam_waterproofing_external).getMaterialCharacteristics().iterator().next();
            resultSteamWaterproofingExternal.setMaterialCharacteristics(materialCharacteristic);
            Integer steamWaterproofingExternalAmount = getMaterialAmount(externalWallSquare, materialCharacteristic.getLength() * materialCharacteristic.getWedth());
            resultSteamWaterproofingExternal.setAmount(steamWaterproofingExternalAmount);
            PriceList priceList = materialCharacteristic.getPriceLists().iterator().next();
            resultSteamWaterproofingExternal.setPrice(priceList.getPurchasePrice() * steamWaterproofingExternalAmount);
            resultSteamWaterproofingExternal.setFullPrice(priceList.getSellingPrice() * steamWaterproofingExternalAmount);
            resultSteamWaterproofingExternal.setMeasurementUnit(measurementUnitRepository.findById(1L).get().getMeasurementUnitsName());
            results.add(resultSteamWaterproofingExternal);
            newResultsId++;
        } catch (Exception e){
            System.out.println(e);
            error = true;
        }

        try{
            if (!error){
                structuralElementFrameRepository.save(frame);
                calculationRepository.save(calculation);

                resultRepository.save(resultOsbExternalWall);
                resultRepository.save(resultSteamWaterproofingExternal);
            }
        } catch (Exception e){
            System.out.println(e);
            LoggerFactory.getLogger(CalculateApplication.class).error("RESULTS ERROR: " + e.getMessage());
        }
        return "redirect:/home";
    }

    private Integer getMaterialAmount(Double allSquare, Double materialSquare){
        Double amount = allSquare / materialSquare;
        return (int)Math.ceil(amount);
    }
}

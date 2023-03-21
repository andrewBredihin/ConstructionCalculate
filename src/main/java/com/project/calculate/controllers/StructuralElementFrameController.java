package com.project.calculate.controllers;

import com.project.calculate.CalculateApplication;
import com.project.calculate.entity.*;
import com.project.calculate.form.CalculationInfo;
import com.project.calculate.form.FrameForm;
import com.project.calculate.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

/**
 * Контроллер страницы расчета каркаса (/framePage)
 */
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
    @Autowired OpeningRepository openingRepository;

    /**
     * GET запрос. Принимает id клиента.
     * @param request
     * @param model
     * @param customerId
     */
    @RequestMapping(value = "/framePage", method = RequestMethod.GET)
    public String structuralElementFramePage(HttpServletRequest request, Model model, @RequestParam(name = "customerId", defaultValue = "") Long customerId) {
        model.addAttribute("frameForm", new FrameForm());
        model.addAttribute("calculationInfo", new CalculationInfo());
        model.addAttribute("customerId", customerId);
        model.addAttribute("id", customerId);

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

    /**
     * POST запрос. Включает в себя расчет каркаса по заданным параметрам. В БД добавляются записи в таблицы:
     * structural_element_frame, calculation, results, openings, frame_results, structural_element_frame.
     * @param request
     * @param model
     * @param frameForm
     * @param calculationInfo
     * @param customerId
     * @param requestValue
     */
    @RequestMapping(value = { "/framePage" }, method = RequestMethod.POST)
    public String saveFrame(HttpServletRequest request, Model model,
                            @ModelAttribute("frameForm") FrameForm frameForm,
                            @ModelAttribute("calculationInfo") CalculationInfo calculationInfo,
                            @RequestParam("calculateButton") Long customerId,
                            @RequestParam("request_value") String requestValue) {
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
            frame.setAmountFloor(calculationInfo.getAmountFloor());
        } catch (Exception e){
            System.out.println(e);
            LoggerFactory.getLogger(CalculateApplication.class).error("FRAME ERROR: " + e.getMessage());
            return null;
        }

        int calculationNumber = 1;
        Customer customer = customerRepository.findById(customerId).get();
        try {
            calculationNumber = customer.getCalculations().size();
        } catch (Exception e){
            System.out.println(e);
        }
        Calculation calculation = new Calculation();
        try {
            calculation.setAddressObjectConstractions(calculationInfo.getAdress());
            calculation.setCreatedDate(LocalDate.now());
            calculation.setNumber(calculationNumber);
            calculation.setCustomer(customer);
            calculation.setСalculationState(calculationStatusRepository.findById(1L).get());
        } catch (Exception e){
            System.out.println(e);
            LoggerFactory.getLogger(CalculateApplication.class).error("CALCULATION ERROR: " + e.getMessage());
            return null;
        }

        Set<StructuralElementFrame> frames = new HashSet<>();
        frames.add(frame);

        Set<Opening> openings = createOpenings(requestValue, frames);
        Set<Result> results = new HashSet<>();

        Double externalWallSquare = perimeter_of_external_walls * height;
        Double internalWallSquare = internal_wall_length * height;

        //ОСБ внешних стен
        Result resultOsbExternalWall = createResult(frames, calculation, OSB_external_wall, externalWallSquare * 2);
        results.add(resultOsbExternalWall);
        //Парогидроизоляция внешних стен
        Result resultSteamWaterproofingExternal = createResult(frames, calculation, steam_waterproofing_external, externalWallSquare);
        results.add(resultSteamWaterproofingExternal);
        //Ветрозащита внешних стен
        Result windscreenExternalWall = createResult(frames, calculation, windscreen_external_wall, externalWallSquare);
        results.add(windscreenExternalWall);
        //Утеплитель внешних стен
        Result insulationExternalWall = createResult(frames, calculation, insulation_external_wall, externalWallSquare);
        results.add(insulationExternalWall);
        //ОСБ внутренних стен
        if (!OSB_internal_wal.equals(null) || OSB_internal_wal != ""){
            Result OsbInternalWal = createResult(frames, calculation, OSB_internal_wal, internalWallSquare * 2);
            results.add(OsbInternalWal);
        }
        //ОСБ перекрытия
        if (!OSB_thickness.equals(null) || OSB_thickness != ""){
            Result OsbThickness = createResult(frames, calculation, OSB_thickness, base_area);
            results.add(OsbThickness);
        }
        //Парогидроизоляция перекрытия
        if (!steam_waterproofing_thicknes.equals(null) || steam_waterproofing_thicknes != ""){
            Result steamWaterproofingThicknes = createResult(frames, calculation, steam_waterproofing_thicknes, base_area);
            results.add(steamWaterproofingThicknes);
        }
        //Ветрозащита перекрытия
        if (!windscreen_thickness.equals(null) || windscreen_thickness != ""){
            Result windscreenThickness = createResult(frames, calculation, windscreen_thickness, base_area);
            results.add(windscreenThickness);
        }
        //Утеплитель перекрытия
        if (!insulation__thickness.equals(null) || insulation__thickness != ""){
            Result insulationThickness = createResult(frames, calculation, insulation__thickness, base_area);
            results.add(insulationThickness);
        }


        try{
            frame.setResults(results);
            frame.setOpenings(openings);

            structuralElementFrameRepository.save(frame);
            calculationRepository.save(calculation);

            for (Result x : results){
                resultRepository.save(x);
            }
            for (Opening x : openings){
                openingRepository.save(x);
            }
        } catch (Exception e){
            System.out.println(e);
            LoggerFactory.getLogger(CalculateApplication.class).error("RESULTS ERROR: " + e.getMessage());
            return null;
        }
        return "redirect:/home";
    }

    /**
     * Возвращает количество материалов, рассчитываемое по площади поверхности и площади материала в количестве 1шт
     * @param allSquare
     * @param materialSquare
     * @return Integer
     */
    private Integer getMaterialAmount(Double allSquare, Double materialSquare){
        Double amount = allSquare / materialSquare;
        return (int)Math.ceil(amount);
    }


    /**
     * Создает Entity объект типа Result с заданными параметрами
     * @param frame
     * @param calculation
     * @param material
     * @param square
     * @return Result
     */
    private Result createResult(Set<StructuralElementFrame> frame, Calculation calculation, String material, Double square){
        Long id = 1L;
        Result result = new Result();
        try {
            id = resultRepository.getMaxId() + 1;
        } catch (Exception e){
            System.out.println(e);
        }
        try {
            result.setStructuralElementFrames(frame);
            result.setId(id);
            result.setCalculation(calculation);
            result.setMaterial(material);
            MaterialCharacteristic materialCharacteristic = materialsRepository.findByName(material).getMaterialCharacteristics().iterator().next();
            result.setMaterialCharacteristics(materialCharacteristic);
            Integer amount = getMaterialAmount(square, materialCharacteristic.getLength() * materialCharacteristic.getWedth());
            result.setAmount(amount);
            PriceList priceList = materialCharacteristic.getPriceLists().iterator().next();
            result.setPrice(priceList.getPurchasePrice() * amount);
            result.setFullPrice(priceList.getSellingPrice() * amount);
            result.setMeasurementUnit(measurementUnitRepository.findById(1L).get().getMeasurementUnitsName());
        } catch (Exception e){
            System.out.println(e);
        }

        return result;
    }


    /**
     * Создает коллекцию оконных проемов и дверей по входной строке, содержащей их параметры
     * @param requestValue
     * @param frames
     * @return Set
     */
    private Set<Opening> createOpenings(String requestValue, Set<StructuralElementFrame> frames){
        Set<Opening> openings = new HashSet<>();
        if (!requestValue.equals("")){
            String[] openingsList = requestValue.split("&");
            for (int i = 1; i < openingsList.length; i++){
                String[] opening = openingsList[i].split("\\|");
                Opening openingObject = new Opening();
                openingObject.setStructuralElementFrames(frames);
                String openingType = opening[0].split("=")[0];
                if (openingType.equals("winHeight"))
                    openingObject.setType("Окно");
                else if (openingType.equals("externalDoorHeight"))
                    openingObject.setType("Внешняя дверь");
                else if (openingType.equals("internalDoorHeight"))
                    openingObject.setType("Внутренняя дверь");
                for (int j = 0; j < opening.length; j++) {
                    String value = opening[j].split("=")[1];
                    if (j == 0)
                        openingObject.setHeight(Double.valueOf(value));
                    else if (j == 1)
                        openingObject.setWidth(Double.valueOf(value));
                    else if (j == 2)
                        openingObject.setAmount(Integer.valueOf(value));
                }
                openings.add(openingObject);
            }
        }
        return openings;
    }
}

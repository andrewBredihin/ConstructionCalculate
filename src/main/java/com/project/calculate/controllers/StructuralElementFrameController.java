package com.project.calculate.controllers;

import com.project.calculate.CalculateApplication;
import com.project.calculate.entity.*;
import com.project.calculate.exeptions.WrongHeightExeption;
import com.project.calculate.form.FrameForm;
import com.project.calculate.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
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

    private final static String INTERNAL = "Внутренние стены";
    private final static String EXTERNAL = "Внешние стены";
    private final static String OVERLAP = "Перекрытие";

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
    @Autowired OpeningRepository openingRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * GET запрос. Принимает id клиента.
     * @param request
     * @param model
     * @param customerId
     */
    @RequestMapping(value = "/framePage", method = RequestMethod.GET)
    public String structuralElementFramePage(HttpServletRequest request, Model model,
                                             @RequestParam(name = "customerId", defaultValue = "") Long customerId,
                                             @RequestParam(name = "amountFloor") int amountFloor,
                                             @RequestParam(name = "calculationId", defaultValue = "", required = false) Long calculationId) {
        model.addAttribute("frameForm", new FrameForm());
        model.addAttribute("customerId", customerId);
        model.addAttribute("id", customerId);
        model.addAttribute("amountFloor", amountFloor);
        if (calculationId != null) {
            model.addAttribute("calculationId", calculationId);
            model.addAttribute("adress", calculationRepository.getReferenceById(calculationId).getAddressObjectConstractions());
        } else {
            model.addAttribute("adress", "");
        }

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
        return "framePage";
    }

    /**
     * POST запрос. Включает в себя расчет каркаса по заданным параметрам. В БД добавляются записи в таблицы:
     * structural_element_frame, calculation, results, openings, frame_results, structural_element_frame.
     * @param request
     * @param model
     * @param frameForm
     * @param customerId
     * @param openingsStr
     */
    @RequestMapping(value = { "/framePage" }, method = RequestMethod.POST)
    public String saveFrame(HttpServletRequest request, Model model,
                            @ModelAttribute("frameForm") FrameForm frameForm,
                            @RequestParam(value = "calculateButton", required = false) Long customerId,
                            @RequestParam("request_value") String openingsStr,
                            @RequestParam(name = "windowsAndDoorsCheck", defaultValue = "0") String windowsAndDoorsCheck,
                            @RequestParam(name = "internalCheck", defaultValue = "0") String internalCheck,
                            @RequestParam(name = "overlapCheck", defaultValue = "0") String overlapCheck,
                            @RequestParam(name = "amountFloor") int amountFloor,
                            @RequestParam(name = "calculationId", defaultValue = "") Long calculationId,
                            @RequestParam(name = "adress", defaultValue = "") String adress) {

        //Получаем значения из формы
        double height = frameForm.getHeight();
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
        double overlap_thickness = frameForm.getOverlap_thickness();
        int floor_number = amountFloor;

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
            if (internalCheck.equals("1"))
                frame.setOsbInternalWal(OSB_internal_wal);
            else
                frame.setOsbInternalWal("");
            if (overlapCheck.equals("1")){
                frame.setOsbThickness(OSB_thickness);
                frame.setSteamWaterproofingThicknes(steam_waterproofing_thicknes);
                frame.setWindscreenThickness(windscreen_thickness);
                frame.setInsulationThickness(insulation__thickness);
                frame.setOverlapThickness(overlap_thickness);
            }
            else{
                frame.setOsbThickness("");
                frame.setSteamWaterproofingThicknes("");
                frame.setWindscreenThickness("");
                frame.setInsulationThickness("");
                frame.setOverlapThickness(0);
            }
            frame.setFloorNumber(floor_number);
            frame.setAmountFloor(floor_number);
        } catch (Exception e){
            System.out.println(e);
            return "redirect:/framePage?customerId=" + customerId;
        }

        Customer customer = customerRepository.findById(customerId).get();
        int calculationNumber = 1;
        try {
            calculationNumber = calculationRepository.getMaxNumber(customerId) + 1;
        } catch (Exception e){
            System.out.println(e);
        }
        Calculation calculation = new Calculation();
        if (calculationId != null)
            calculation = calculationRepository.getReferenceById(calculationId);
        else {
            try {
                calculation.setAddressObjectConstractions(adress);
                calculation.setCreatedDate(LocalDate.now());
                calculation.setNumber(calculationNumber);
                calculation.setCustomer(customer);
                calculation.setСalculationState(calculationStatusRepository.findById(1L).get());
            } catch (Exception e){
                System.out.println(e);
                return "redirect:/framePage?customerId=" + customerId + "&amountFloor=" + amountFloor;
            }
        }

        Set<StructuralElementFrame> frames = new HashSet<>();
        frames.add(frame);

        Set<Opening> openings = new HashSet<>();
        if (windowsAndDoorsCheck.equals("1"))
            try {
                openings = createOpenings(openingsStr, frames);
            } catch (WrongHeightExeption e){
                System.out.println(e);
            }
        Set<Result> results = new HashSet<>();

        Double externalWallSquare = perimeter_of_external_walls * height - getOpeningsSquare(openings, EXTERNAL);
        Double internalWallSquare = internal_wall_length * height - getOpeningsSquare(openings, INTERNAL);

        //Доски для внешних стен
        try{
            MaterialCharacteristic boardExternal = materialCharacteristicsRepository.getMaterialCharacteristicByWedthAndName(external_wall_thickness / 1000, "Доска");
            Material boardExternalMaterial = boardExternal.getMaterials();
            Result resultBoardExternal = createBoardResult(frames, openings, calculation, boardExternalMaterial,
                    boardExternal, perimeter_of_external_walls, height, base_area, EXTERNAL);
            results.add(resultBoardExternal);
        }catch (Exception e){
            System.out.println(e);
        }
        //ОСБ внешних стен
        Result resultOsbExternalWall = createResult(frames, calculation, OSB_external_wall, externalWallSquare * 2, EXTERNAL);
        results.add(resultOsbExternalWall);
        //Парогидроизоляция внешних стен
        Result resultSteamWaterproofingExternal = createResult(frames, calculation, steam_waterproofing_external, externalWallSquare, EXTERNAL);
        results.add(resultSteamWaterproofingExternal);
        //Ветрозащита внешних стен
        Result windscreenExternalWall = createResult(frames, calculation, windscreen_external_wall, externalWallSquare, EXTERNAL);
        results.add(windscreenExternalWall);
        //Утеплитель внешних стен
        Result insulationExternalWall = createResult(frames, calculation, insulation_external_wall, externalWallSquare, EXTERNAL);
        results.add(insulationExternalWall);

        //Доски для внутренних стен
        try{
            MaterialCharacteristic boardInternal = materialCharacteristicsRepository.getMaterialCharacteristicByWedthAndName(internal_wall_thickness / 1000, "Доска");
            Material boardInternalMaterial = boardInternal.getMaterials();
            Result resultBoardInternal = createBoardResult(frames, openings, calculation, boardInternalMaterial,
                    boardInternal, internal_wall_length, height, base_area, INTERNAL);
            results.add(resultBoardInternal);
        }catch (Exception e){
            System.out.println(e);
        }
        //ОСБ внутренних стен
        if (internalCheck.equals("1")){
            Result OsbInternalWal = createResult(frames, calculation, OSB_internal_wal, internalWallSquare * 2, INTERNAL);
            results.add(OsbInternalWal);
        }

        //Перекрытие
        if (overlapCheck.equals("1")){
            //Доски для Перекрытие
            try{
                MaterialCharacteristic boardOverlap = materialCharacteristicsRepository.getMaterialCharacteristicByWedthAndName(overlap_thickness / 1000, "Доска");
                Material boardOverlapMaterial = boardOverlap.getMaterials();
                Result resultBoardInternal = createBoardResult(frames, openings, calculation, boardOverlapMaterial,
                        boardOverlap, internal_wall_length, height, base_area, OVERLAP);
                results.add(resultBoardInternal);
            }catch (Exception e){
                System.out.println(e);
            }
            //ОСБ перекрытия
            Result OsbThickness = createResult(frames, calculation, OSB_thickness, base_area, OVERLAP);
            results.add(OsbThickness);
            //Парогидроизоляция перекрытия
            Result steamWaterproofingThicknes = createResult(frames, calculation, steam_waterproofing_thicknes, base_area, OVERLAP);
            results.add(steamWaterproofingThicknes);
            //Ветрозащита перекрытия
            Result windscreenThickness = createResult(frames, calculation, windscreen_thickness, base_area, OVERLAP);
            results.add(windscreenThickness);
            //Утеплитель перекрытия
            Result insulationThickness = createResult(frames, calculation, insulation__thickness, base_area, OVERLAP);
            results.add(insulationThickness);
        }

        try{
            frame.setResults(results);
            frame.setOpenings(openings);

            structuralElementFrameRepository.save(frame);
            if (calculationId == null)
                calculationRepository.save(calculation);

            resultRepository.saveAll(results);
            openingRepository.saveAll(openings);
        } catch (Exception e){
            System.out.println(e);
        }
        //return "redirect:/home";
        amountFloor += 1;
        if (calculationId == null)
            calculationId = calculationRepository.getMaxId();
        return "redirect:/framePage?customerId=" + customerId + "&amountFloor=" + amountFloor + "&calculationId=" + calculationId;
    }

    /**
     * Возвращает количество материалов, рассчитываемое по площади/длины поверхности и площади/длины материала в количестве 1шт
     * @param allSquareOrLength
     * @param materialSquareOrLength
     * @return Integer
     */
    private Integer getMaterialAmount(Double allSquareOrLength, Double materialSquareOrLength){
        Double amount = allSquareOrLength / materialSquareOrLength;
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
    private Result createResult(Set<StructuralElementFrame> frame, Calculation calculation, String material, Double square, String elementType){
        //Long id = 1L;
        Result result = new Result();
        /*try {
            id = resultRepository.getMaxId() + 1;
        } catch (Exception e){
            System.out.println(e);
        }*/
        try {
            result.setStructuralElementFrames(frame);
            //result.setId(id);
            result.setCalculation(calculation);
            result.setMaterial(material);
            MaterialCharacteristic materialCharacteristic = materialsRepository.findByName(material).getMaterialCharacteristics().iterator().next();
            result.setMaterialCharacteristics(materialCharacteristic);
            Integer amount = getMaterialAmount(square, materialCharacteristic.getLength() * materialCharacteristic.getWedth());
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

    /**
     * Создает Entity объект типа Result для досок с заданными параметрами
     * @param frame
     * @param openings
     * @param calculation
     * @param material
     * @param materialCharacteristic
     * @param length
     * @param height
     * @param elementType
     * @return Result
     */
    private Result createBoardResult(Set<StructuralElementFrame> frame, Set<Opening> openings, Calculation calculation, Material material, MaterialCharacteristic materialCharacteristic, double length, double height, double square, String elementType){
        Result result = new Result();
        try {
            result.setStructuralElementFrames(frame);
            result.setCalculation(calculation);
            result.setMaterial(material.getName());
            result.setMaterialCharacteristics(materialCharacteristic);
            double boardsLength = getBoardLength(length, height, square, elementType) + getOpeningsBoardLength(openings, height, elementType);
            Integer amount = getMaterialAmount(boardsLength, materialCharacteristic.getLength());
            result.setAmount(amount);
            PriceList priceList = materialCharacteristic.getPriceLists().iterator().next();
            result.setPrice(priceList.getPurchasePrice() * amount);
            result.setFullPrice(priceList.getSellingPrice() * amount);
            result.setMeasurementUnit(measurementUnitRepository.findById(1L).get().getMeasurementUnitsName());
            result.setElementType(elementType);
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
    private Set<Opening> createOpenings(String requestValue, Set<StructuralElementFrame> frames) throws WrongHeightExeption {
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
                else if (openingType.equals("doorHeight"))
                    openingObject.setType("Внешняя дверь");
                else if (openingType.equals("doorHeightI"))
                    openingObject.setType("Внутренняя дверь");
                for (int j = 0; j < opening.length; j++) {
                    String value = opening[j].split("=")[1];
                    if (j == 0){
                        if (frames.iterator().next().getFloorHeight() <= Double.valueOf(value))
                            throw new WrongHeightExeption("Размер окна/двери не должен превышать размеры стены");
                        else
                            openingObject.setHeight(Double.valueOf(value));
                    }
                    else if (j == 1)
                        if (frames.iterator().next().getPerimeterOfExternalWalls() <= Double.valueOf(value))
                            throw new WrongHeightExeption("Размер окна/двери не должен превышать размеры стены");
                        else
                            openingObject.setWidth(Double.valueOf(value));
                    else if (j == 2)
                        openingObject.setAmount(Integer.valueOf(value));
                }
                openings.add(openingObject);
            }
        }
        return openings;
    }

    /**
     * Возвращает площадь окон и дверных проемов
     * @param openings
     * @return double
     */
    private double getOpeningsSquare(Set<Opening> openings, String internalOrExternal){
        double squareResult = 0;
        for (Opening x : openings) {
            if (internalOrExternal.equals(EXTERNAL)){
                if (x.getType().equals("Окно") || x.getType().equals("Внешняя дверь"))
                    squareResult += x.getHeight() * x.getWidth() * x.getAmount();
            }
            else if (internalOrExternal.equals(INTERNAL)){
                if (x.getType().equals("Внутренняя дверь"))
                    squareResult += x.getHeight() * x.getWidth() * x.getAmount();
            }
        }
        return squareResult;
    }

    /**
     * Возвращает длину досок, необходимую для оконных и дверных проемов
     * @param openings
     * @param height
     * @param internalOrExternal
     * @return double
     */
    private double getOpeningsBoardLength(Set<Opening> openings, double height, String internalOrExternal) {
        double length = 0;
        for (Opening x : openings) {
            if (internalOrExternal.equals(EXTERNAL)){
                if (x.getType().equals("Окно")){
                        length += (x.getWidth() * 2 + height * 2 + height - x.getHeight()) * x.getAmount();
                }
                else if (x.getType().equals("Внешняя дверь")){
                        length += (x.getWidth() + height * 2 + height - x.getHeight()) * x.getAmount();
                }
            }
            else if (internalOrExternal.equals(INTERNAL)){
                if (x.getType().equals("Внутренняя дверь")){
                        length += (x.getWidth() + height * 2 + height - x.getHeight()) * x.getAmount();
                }
            }
        }
        return  length;
    }

    /**
     * Возвращает длину досок для выбранного элемента каркаса
     * @param length
     * @param height
     * @param square
     * @param elementType
     * @return double
     */
    private double getBoardLength(double length, double height, double square, String elementType){
        if (elementType.equals(EXTERNAL))
            return length * 2 + height * 8 + length / 0.6 * height;
        else if (elementType.equals(INTERNAL))
            return length * 2 + length / 0.6 * height;
        else if (elementType.equals(OVERLAP))
            return square / 0.6;
        else
            return 0;
    }
}

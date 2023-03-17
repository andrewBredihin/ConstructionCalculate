package com.project.calculate.controllers;

import com.project.calculate.CalculateApplication;
import com.project.calculate.entity.Material;
import com.project.calculate.entity.StructuralElementFrame;
import com.project.calculate.form.ClientForm;
import com.project.calculate.form.FrameForm;
import com.project.calculate.repository.MaterialsRepository;
import com.project.calculate.repository.StructuralElementFrameRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StructuralElementFrameController {

    @Autowired
    private StructuralElementFrameRepository structuralElementFrameRepository;
    @Autowired
    private MaterialsRepository materialsRepository;

    @RequestMapping(value = "/framePage", method = RequestMethod.GET)
    public String structuralElementFramePage(Model model) {
        FrameForm frameForm = new FrameForm();
        model.addAttribute("frameForm", frameForm);
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
                            @ModelAttribute("frameForm") FrameForm frameForm) {
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
        try {
            StructuralElementFrame frame = new StructuralElementFrame();
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

            structuralElementFrameRepository.save(frame);
            return "redirect:/home";
        } catch (Exception e){
            System.out.println(e);
            LoggerFactory.getLogger(CalculateApplication.class).error(e.getMessage());
        }
        return null;
    }
}

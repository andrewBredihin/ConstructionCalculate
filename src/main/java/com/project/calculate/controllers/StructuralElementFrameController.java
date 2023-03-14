package com.project.calculate.controllers;

import com.project.calculate.entity.StructuralElementFrame;
import com.project.calculate.form.ClientForm;
import com.project.calculate.form.FrameForm;
import com.project.calculate.repository.StructuralElementFrameRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class StructuralElementFrameController {

    @Autowired
    private StructuralElementFrameRepository structuralElementFrameRepository;

    @RequestMapping(value = "/framePage", method = RequestMethod.GET)
    public String structuralElementFramePage(Model model) {
        FrameForm frameForm = new FrameForm();
        model.addAttribute("frameForm", frameForm);
        return "framePage";
    }

    @RequestMapping(value = { "/framePage" }, method = RequestMethod.POST)
    public String saveFrame(HttpServletRequest request, Model model,
                            @ModelAttribute("frameForm") FrameForm frameForm) {
        //Получаем значения из формы
        int height;
        double perimeter_of_external_walls;
        double base_area;
        double external_wall_thickness;
        double internal_wall_length;
        double internal_wall_thickness;
        String OSB_external_wall;
        String steam_waterproofing_external;
        String windscreen_external_wall;
        String insulation_external_wall;
        String OSB_internal_wal;
        String OSB_thickness;
        String steam_waterproofing_thicknes;
        String windscreen_thickness;
        String insulation__thickness;
        int overlap_thickness;
        int floor_number;
        try {
            height = frameForm.getHeight();
            perimeter_of_external_walls = frameForm.getPerimeter_of_external_walls();
            base_area = frameForm.getBase_area();
            external_wall_thickness = frameForm.getExternal_wall_thickness();
            internal_wall_length = frameForm.getInternal_wall_length();
            internal_wall_thickness = frameForm.getInternal_wall_thickness();
            OSB_external_wall = frameForm.getOSB_external_wall();
            steam_waterproofing_external = frameForm.getSteam_waterproofing_external();
            windscreen_external_wall = frameForm.getWindscreen_external_wall();
            insulation_external_wall = frameForm.getInsulation_external_wall();
            OSB_internal_wal = frameForm.getOSB_internal_wal();
            OSB_thickness = frameForm.getOSB_thickness();
            steam_waterproofing_thicknes = frameForm.getSteam_waterproofing_external();
            windscreen_thickness = frameForm.getWindscreen_thickness();
            insulation__thickness = frameForm.getInsulation__thickness();
            overlap_thickness = frameForm.getOverlap_thickness();
            floor_number = frameForm.getFloor_number();
        } catch (Exception e){
            System.out.println(e);
            return null;
        }
        //Создаем нового клиента
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
        }
        return null;
    }
}

package com.project.calculate.controllers;

import com.project.calculate.CalculateApplication;
import com.project.calculate.entity.*;
import com.project.calculate.form.CalculationInfo;
import com.project.calculate.form.FrameForm;
import com.project.calculate.repository.CalculationRepository;
import com.project.calculate.repository.MaterialsRepository;
import com.project.calculate.repository.ResultRepository;
import com.project.calculate.repository.StructuralElementFrameRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class CalculationPageController {

    @Autowired
    private StructuralElementFrameRepository structuralElementFrameRepository;
    @Autowired
    private MaterialsRepository materialsRepository;
    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private CalculationRepository calculationRepository;

    @RequestMapping(value = "/calculation", method = RequestMethod.GET)
    public String calculationPage(HttpServletRequest request,
                                  Model model,
                                  @RequestParam(name = "calculationId", defaultValue = "") Long calculationId) {
        Calculation calculation = calculationRepository.getReferenceById(calculationId);
        Set<Result> results = calculation.getResults();
        Set<Result> resultsExternal = new HashSet<>();
        Set<Result> resultsInternal = new HashSet<>();
        Set<Result> resultsOverlap = new HashSet<>();
        double externalPrice = 0;
        double internalPrice = 0;
        double overlapPrice = 0;

        for (Result x : results) {
            if (x.getElementType().equals("Внешние стены")){
                resultsExternal.add(x);
                externalPrice += x.getPrice();
            }
            else if (x.getElementType().equals("Внутренние стены")){
                resultsInternal.add(x);
                internalPrice += x.getPrice();
            }
            else if (x.getElementType().equals("Перекрытие")){
                resultsOverlap.add(x);
                overlapPrice += x.getPrice();
            }
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

        model.addAttribute("calculationDate", calculation.getCreatedDate().toString());
        model.addAttribute("calculationAdres", calculation.getAddressObjectConstractions());
        model.addAttribute("calculationStatus", calculationState.getTitle());
        model.addAttribute("calculationStates", statuses);
        model.addAttribute("externalPrice", getPriceToStringFormat(externalPrice));
        model.addAttribute("internalPrice", getPriceToStringFormat(internalPrice));
        model.addAttribute("overlapPrice", getPriceToStringFormat(overlapPrice));
        model.addAttribute("allPrice", getPriceToStringFormat(externalPrice + internalPrice + overlapPrice));
        model.addAttribute("resultsExternal", resultsExternal);
        model.addAttribute("resultsInternal", resultsInternal);
        model.addAttribute("resultsOverlap", resultsOverlap);
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
}

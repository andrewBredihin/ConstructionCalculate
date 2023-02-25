package com.project.calculate.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainPageController {

    @RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request) {

        return "MainPage";
    }
}

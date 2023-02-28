package com.project.calculate.controllers;

import com.project.calculate.entity.User;
import com.project.calculate.entity.UserGroup;
import com.project.calculate.entity.UserStatus;
import com.project.calculate.repository.UserGroupRepository;
import com.project.calculate.repository.UserRepository;
import com.project.calculate.repository.UserStatusRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Controller
public class MainPageController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserStatusRepository userStatusRepository;
    @Autowired
    private UserGroupRepository userGroupRepository;

    @RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request) {

        return "MainPage";
    }
}

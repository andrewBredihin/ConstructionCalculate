package com.project.calculate.controllers;

import com.project.calculate.entity.User;
import com.project.calculate.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

@Controller
public class MainPageController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/home" , method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request) {

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

        return "MainPage";
    }
}

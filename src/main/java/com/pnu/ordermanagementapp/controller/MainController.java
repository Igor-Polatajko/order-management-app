package com.pnu.ordermanagementapp.controller;

import com.pnu.ordermanagementapp.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping
    public String homePage(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("userRole", user.getRole());
        return "index";
    }

}

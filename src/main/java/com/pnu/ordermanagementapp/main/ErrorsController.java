package com.pnu.ordermanagementapp.main;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorsController implements ErrorController {

    @RequestMapping("/error")
    public String error(Model model) {
        model.addAttribute("message", "Internal service error!");
        return "error_page";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}

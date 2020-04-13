package com.pnu.ordermanagementapp.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MainController {

    @GetMapping("/")
    public String homePage() {
        return "index";
    }


    @GetMapping("/errors")
    public String errors() {
        return "errors.html";
    }
}

package com.pnu.ordermanagementapp.order;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {

    @GetMapping("")
    public String get() {
        return "index";
    }

}

package com.pnu.ordermanagementapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class OrderManagementAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderManagementAppApplication.class, args);
    }

}

package com.pnu.ordermanagementapp.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsInterceptor {

    @ExceptionHandler(Exception.class)
    public String generalError(Model model) {
        model.addAttribute("message", "Internal service error!");
        return "error_page";
    }

    @ExceptionHandler(ServiceException.class)
    public String serviceError(ServiceException serviceException, Model model) {
        model.addAttribute("message", serviceException.getMessage());
        return "error_page";
    }

}

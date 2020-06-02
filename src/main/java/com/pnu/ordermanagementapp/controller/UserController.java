package com.pnu.ordermanagementapp.controller;

import com.pnu.ordermanagementapp.dto.user.UserRegistrationFormDto;
import com.pnu.ordermanagementapp.service.UserService;
import com.pnu.ordermanagementapp.util.validation.DataValidator;
import com.pnu.ordermanagementapp.util.validation.ValidationResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static java.util.Objects.nonNull;

@Controller
public class UserController {

    private UserService userService;

    private DataValidator dataValidator;

    @Autowired
    public UserController(UserService userService, DataValidator dataValidator) {
        this.userService = userService;
        this.dataValidator = dataValidator;
    }

    @GetMapping("/login")
    public String getLoginPage(Model model, String error, String logout) {

        if (nonNull(error))
            model.addAttribute("error", "Your username or password is incorrect!");

        if (nonNull(logout))
            model.addAttribute("message", "You have been logged out successfully.");

        return "users/login";
    }

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "users/registration";
    }

    @PostMapping("/registration")
    public String registerUser(Model model, @ModelAttribute UserRegistrationFormDto userFormDto) {

        ValidationResult validationResult = dataValidator.validate(userFormDto);

        if (validationResult.isError()) {
            model.addAttribute("error", validationResult.getErrorMessage());
            return "users/registration";
        }

        if (!StringUtils.equals(userFormDto.getPassword(), userFormDto.getPasswordRepeated())) {
            model.addAttribute("error", "Passwords should match");
            return "users/registration";
        }

        userService.create(userFormDto);
        return "redirect:users/login";
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String findUsers(@RequestParam(name = "page", required = false, defaultValue = "1") int pageNumber,
                            @RequestParam(name = "reverse", required = false, defaultValue = "false") boolean reverseSort,
                            Model model) {
        model.addAttribute("users", userService.findAllUsers(pageNumber, reverseSort));
        model.addAttribute("reverse", reverseSort);
        model.addAttribute("headline", "All users");
        return "users/show_users";
    }

    @GetMapping("/users/find")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String findUsersByName(@RequestParam(name = "page", required = false, defaultValue = "1") int pageNumber,
                                  @RequestParam(name = "reverse", required = false, defaultValue = "false") boolean reverseSort,
                                  @ModelAttribute("q") String nameQuery, Model model) {
        model.addAttribute("users", userService.findUsersByName(pageNumber, nameQuery, reverseSort));
        model.addAttribute("reverse", reverseSort);
        model.addAttribute("nameQuery", nameQuery);
        model.addAttribute("headline", "Find users by '" + nameQuery + "'");
        return "users/show_users";
    }

    @PostMapping("/users/deactivate/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deactivateUser(@PathVariable("id") Long id) {
        userService.deactivate(id);
        return "redirect:/users";
    }

    @PostMapping("/users/activate/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String activateUser(@PathVariable("id") Long id) {
        userService.activate(id);
        return "redirect:/users";
    }

}

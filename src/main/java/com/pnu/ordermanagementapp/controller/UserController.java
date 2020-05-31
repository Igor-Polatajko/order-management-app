package com.pnu.ordermanagementapp.controller;

import com.pnu.ordermanagementapp.dto.user.UserRegistrationFormDto;
import com.pnu.ordermanagementapp.model.User;
import com.pnu.ordermanagementapp.service.UserService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

// ToDo make better error messages
@Controller
public class UserController {

    private UserService userService;

    private Validator validator;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
        validator = Validation.buildDefaultValidatorFactory().getValidator();
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

        Set<ConstraintViolation<UserRegistrationFormDto>> constraintViolations = validator.validate(userFormDto);

        if (CollectionUtils.isNotEmpty(constraintViolations)) {
            String errorMessage = constraintViolations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(StringUtils.LF));
            model.addAttribute("error", errorMessage);
            return "users/registration";
        }

        if (!StringUtils.equals(userFormDto.getPassword(), userFormDto.getPasswordRepeated())) {
            model.addAttribute("error", "Passwords should match");
            return "users/registration";
        }

        User user = User.builder()
                .firstName(userFormDto.getFirstName())
                .lastName(userFormDto.getLastName())
                .username(userFormDto.getUsername())
                .password(userFormDto.getPassword())
                .build();

        userService.create(user);

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

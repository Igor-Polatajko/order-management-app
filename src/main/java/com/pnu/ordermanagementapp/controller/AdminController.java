package com.pnu.ordermanagementapp.controller;

import com.pnu.ordermanagementapp.model.User;
import com.pnu.ordermanagementapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String findUsers(@RequestParam(name = "page", required = false, defaultValue = "1") int pageNumber,
                            @RequestParam(name = "reverse", required = false, defaultValue = "false") boolean reverseSort,
                            Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("users", userService.findAllUsers(pageNumber, reverseSort));
        model.addAttribute("reverse", reverseSort);
        model.addAttribute("headline", "All users");
        return "users/show_users";
    }

    @GetMapping("/users/find")
    public String findUsersByName(@RequestParam(name = "page", required = false, defaultValue = "1") int pageNumber,
                                  @RequestParam(name = "reverse", required = false, defaultValue = "false") boolean reverseSort,
                                  @ModelAttribute("q") String nameQuery, Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("users", userService.findUsersByName(pageNumber, nameQuery, reverseSort));
        model.addAttribute("reverse", reverseSort);
        model.addAttribute("nameQuery", nameQuery);
        model.addAttribute("headline", "Find users by '" + nameQuery + "'");
        return "users/show_users";
    }

    @PostMapping("/users/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id, @AuthenticationPrincipal User user) {
        userService.delete(id);
        return "redirect:/admin/users";
    }

    @PostMapping("/users/activate/{id}")
    public String activateProduct(@PathVariable("id") Long id, @AuthenticationPrincipal User user) {
        userService.activate(id);
        return "redirect:/admin/users";
    }

}

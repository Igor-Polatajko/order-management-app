package com.pnu.ordermanagementapp.controller;

import com.pnu.ordermanagementapp.model.Product;
import com.pnu.ordermanagementapp.model.User;
import com.pnu.ordermanagementapp.service.ProductService;
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
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String findAll(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                          @RequestParam(name = "active", required = false, defaultValue = "true") boolean isActive,
                          Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("active", isActive);
        model.addAttribute("products", productService.findAllByActivity(page, isActive, user.getId()));
        return "product/show_products";
    }

    @GetMapping("/find")
    public String findAllByName(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                @RequestParam(name = "active", required = false, defaultValue = "true") boolean isActive,
                                @ModelAttribute("name") String nameQuery, Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("active", isActive);
        model.addAttribute("products", productService.findAllByNameAndActivity(page, nameQuery, isActive, user.getId()));
        return "product/show_products";
    }

    @GetMapping("/new")
    public String createNew() {
        return "/product/form_product";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("product", productService.findById(id, user.getId()));
        return "/product/form_product";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("product") Product product, @AuthenticationPrincipal User user) {
        productService.create(product, user.getId());
        return "redirect:/products";
    }

    @PostMapping("/update")
    public String updateProduct(@ModelAttribute("product") Product product, @AuthenticationPrincipal User user) {
        productService.update(product, user.getId());
        return "redirect:/products";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id, @AuthenticationPrincipal User user) {
        productService.delete(id, user.getId());
        return "redirect:/products";
    }

    @PostMapping("/activate/{id}")
    public String activateProduct(@PathVariable("id") Long id, @AuthenticationPrincipal User user) {
        productService.activate(id, user.getId());
        return "redirect:/products";
    }
}

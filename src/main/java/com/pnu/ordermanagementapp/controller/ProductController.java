package com.pnu.ordermanagementapp.controller;

import com.pnu.ordermanagementapp.dto.product.ProductFormSubmitDto;
import com.pnu.ordermanagementapp.model.Product;
import com.pnu.ordermanagementapp.model.User;
import com.pnu.ordermanagementapp.service.ProductService;
import com.pnu.ordermanagementapp.util.validation.DataValidator;
import com.pnu.ordermanagementapp.util.validation.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    private DataValidator dataValidator;

    @Autowired
    public ProductController(ProductService productService, DataValidator dataValidator) {
        this.productService = productService;
        this.dataValidator = dataValidator;
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
                                @ModelAttribute("name") String nameQuery, Model model,
                                @AuthenticationPrincipal User user) {
        model.addAttribute("active", isActive);
        model.addAttribute("products", productService
                .findAllByNameAndActivity(page, nameQuery, isActive, user.getId()));
        return "product/show_products";
    }

    @GetMapping("/new")
    public String createNew() {
        return "/product/form_product";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal User user) {
        Product product = productService.findById(id, user.getId());
        model.addAttribute("product", product);
        return "/product/form_product";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("productDto") ProductFormSubmitDto productFormDto, Model model,
                         @AuthenticationPrincipal User user) {

        ValidationResult validationResult = dataValidator.validate(productFormDto);

        if (validationResult.isError()) {
            model.addAttribute("error", validationResult.getErrorMessage());
            return "/product/form_product";
        }

        productService.create(productFormDto, user.getId());
        return "redirect:/products";
    }

    @PostMapping("/update")
    public String updateProduct(@ModelAttribute("productDto") ProductFormSubmitDto productFormDto, Model model,
                                @AuthenticationPrincipal User user) {

        ValidationResult validationResult = dataValidator.validate(productFormDto);

        if (validationResult.isError()) {
            model.addAttribute("error", validationResult.getErrorMessage());
            return "/product/form_product";
        }

        productService.update(productFormDto, user.getId());
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

    @GetMapping("/export")
    public String downloadProductsExcel(Model model, @AuthenticationPrincipal User user) {

        model.addAttribute("products", productService.findAll(user.getId()));
        return "productsExcelView";
    }
}

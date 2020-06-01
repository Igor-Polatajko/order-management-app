package com.pnu.ordermanagementapp.controller;

import com.pnu.ordermanagementapp.dto.product.ProductFormSubmitDto;
import com.pnu.ordermanagementapp.model.Product;
import com.pnu.ordermanagementapp.model.User;
import com.pnu.ordermanagementapp.service.ProductService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    private Validator validator;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
        validator = Validation.buildDefaultValidatorFactory().getValidator();
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

        if (validateProductFormDto(productFormDto, model)) return "/product/form_product";

        Product product = Product.builder()
                .name(productFormDto.getName())
                .price(productFormDto.getPrice())
                .amount(productFormDto.getAmount())
                .build();

        productService.create(product, user.getId());
        return "redirect:/products";
    }

    @PostMapping("/update")
    public String updateProduct(@ModelAttribute("productDto") ProductFormSubmitDto productFormDto, Model model,
                                @AuthenticationPrincipal User user) {

        if (validateProductFormDto(productFormDto, model)) return "/product/form_product";

        Product product = Product.builder()
                .id(productFormDto.getId())
                .name(productFormDto.getName())
                .price(productFormDto.getPrice())
                .amount(productFormDto.getAmount())
                .build();

        productService.update(product, user.getId());
        return "redirect:/products";
    }

    private boolean validateProductFormDto(ProductFormSubmitDto productFormDto, Model model) {
        Set<ConstraintViolation<ProductFormSubmitDto>> constraintViolations = validator.validate(productFormDto);

        if (CollectionUtils.isNotEmpty(constraintViolations)) {
            String errorMessage = constraintViolations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(StringUtils.LF));
            model.addAttribute("error", errorMessage);
            model.addAttribute("product", productFormDto);
            return true;
        }
        return false;
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

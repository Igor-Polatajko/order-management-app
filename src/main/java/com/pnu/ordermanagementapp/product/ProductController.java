package com.pnu.ordermanagementapp.product;

import com.pnu.ordermanagementapp.model.Product;
import com.pnu.ordermanagementapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
                          Model model,
                          @AuthenticationPrincipal User user) {
        model.addAttribute("products", productService.findAll(page));
        return "product/show_products";
    }

    @GetMapping("/find")
    public String findAllByName(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                @ModelAttribute("name") String name,
                                Model model,
                                @AuthenticationPrincipal User user) {
        model.addAttribute("products", productService.findAllByName(page, name));
        return "product/show_products";
    }

    @GetMapping("/new")
    public String createNew() {
        return "/product/form_product";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("product", productService.findById(id));
        return "/product/form_product";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("product") Product product, @AuthenticationPrincipal User user) {
        productService.create(product);
        return "redirect:/products";
    }

    @PostMapping("/update")
    public String updateProduct(@ModelAttribute("product") Product product, @AuthenticationPrincipal User user) {
        productService.update(product);
        return "redirect:/products";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id, @AuthenticationPrincipal User user) {
        productService.delete(id);
        return "redirect:/products";
    }
}

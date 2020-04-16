package com.pnu.ordermanagementapp.product;

import com.pnu.ordermanagementapp.adapter.DbAdapter;
import com.pnu.ordermanagementapp.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    private DbAdapter<Product> productDbAdapter;

    @Autowired
    public ProductController(DbAdapter<Product> productDbAdapter) {
        this.productDbAdapter = productDbAdapter;
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("products", productDbAdapter.findAll());
        return "/product/index_products";
    }

    @GetMapping("/new")
    public String createNew() {
        return "/product/form_product";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        model.addAttribute("product", productDbAdapter.findById(id));
        return "/product/form_product";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("product") Product product) {
        productDbAdapter.create(product);
        return "redirect:/products";
    }

    @PostMapping("/update")
    public String updateProduct(@ModelAttribute("product") Product product) {
        productDbAdapter.update(product);
        return "redirect:/products";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productDbAdapter.delete(id);
        return "redirect:/products";
    }
}

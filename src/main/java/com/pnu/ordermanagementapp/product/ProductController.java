package com.pnu.ordermanagementapp.product;

import com.pnu.ordermanagementapp.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
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

    private ProductDbAdapter productDbAdapter;

    @Autowired
    public ProductController(ProductDbAdapter productDbAdapter) {
        this.productDbAdapter = productDbAdapter;
    }

    @GetMapping
    public String findAll(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page, Model model) {
        model.addAttribute("products", productDbAdapter.findAll(page));
        return "product/show_products";
    }

    @GetMapping("/find")
    public String findAllByName(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                @ModelAttribute("name")String name, Model model) {
        model.addAttribute("products", productDbAdapter.findAllByName(page, name));
        return "product/show_products";
        }

        @GetMapping("/new")
        public String createNew () {
            return "/product/form_product";
        }

        @GetMapping("/update/{id}")
        public String update (@PathVariable("id") Long id, Model model){
            model.addAttribute("product", productDbAdapter.findById(id));
            return "/product/form_product";
        }

        @PostMapping("/new")
        public String create (@ModelAttribute("product") Product product){
            productDbAdapter.create(product);
            return "redirect:/products";
        }

        @PostMapping("/update")
        public String updateProduct (@ModelAttribute("product") Product product){
            productDbAdapter.update(product);
            return "redirect:/products";
        }

        @PostMapping("/delete/{id}")
        public String deleteProduct (@PathVariable("id") Long id){
            productDbAdapter.delete(id);
            return "redirect:/products";
        }
    }

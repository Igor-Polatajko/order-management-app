package com.pnu.ordermanagementapp.controller;

import com.pnu.ordermanagementapp.dto.order.OrderFormSubmitDto;
import com.pnu.ordermanagementapp.dto.order.OrdersFtlPageDto;
import com.pnu.ordermanagementapp.model.*;
import com.pnu.ordermanagementapp.service.ClientService;
import com.pnu.ordermanagementapp.service.OrderService;
import com.pnu.ordermanagementapp.service.ProductService;
import com.pnu.ordermanagementapp.util.OrdersPageToOrdersFtlPageDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;

    private ClientService clientService;

    private ProductService productService;

    private OrdersPageToOrdersFtlPageDtoMapper ordersPageToOrdersFtlPageDtoMapper;

    @Autowired
    public OrderController(OrderService orderService,
                           ClientService clientService,
                           ProductService productService,
                           OrdersPageToOrdersFtlPageDtoMapper ordersPageToOrdersFtlPageDtoMapper) {
        this.orderService = orderService;
        this.clientService = clientService;
        this.productService = productService;
        this.ordersPageToOrdersFtlPageDtoMapper = ordersPageToOrdersFtlPageDtoMapper;
    }

    @GetMapping
    public String findAll(@RequestParam(name = "page", required = false, defaultValue = "1") int pageNumber,
                          @RequestParam(name = "state", required = false) String state,
                          Model model,
                          @AuthenticationPrincipal User user) {

        OrderState orderState = OrderState.fetch(state);

        Page<Order> ordersPage = orderService.findAll(pageNumber, user.getId());
        OrdersFtlPageDto orders = ordersPageToOrdersFtlPageDtoMapper.map(ordersPage);

        model.addAttribute("currentState", orderState);
        model.addAttribute("orders", orders);
        model.addAttribute("headline", "The most recent orders");

        return "orders/show_orders";
    }

    @GetMapping("/client/{clientId}")
    public String findAllForClient(@RequestParam(name = "page", required = false, defaultValue = "1") int pageNumber,
                                   @RequestParam(name = "state", required = false) String state,
                                   @PathVariable("clientId") Long clientId, Model model,
                                   @AuthenticationPrincipal User user) {

        OrderState orderState = OrderState.fetch(state);

        Page<Order> ordersPage = orderService.findByClientId(clientId, pageNumber, user.getId());
        OrdersFtlPageDto orders = ordersPageToOrdersFtlPageDtoMapper.map(ordersPage);

        Client client = clientService.findById(clientId, user.getId());

        model.addAttribute("currentState", orderState);
        model.addAttribute("orders", orders);
        model.addAttribute("headline", String.format("Orders made by %s %s",
                client.getFirstName(), client.getLastName()
        ));

        return "orders/show_orders";
    }

    @GetMapping("/product/{productId}")
    public String findAllForProduct(@RequestParam(name = "page", required = false, defaultValue = "1") int pageNumber,
                                    @RequestParam(name = "state", required = false) String state,
                                    @PathVariable("productId") Long productId, Model model,
                                    @AuthenticationPrincipal User user) {

        OrderState orderState = OrderState.fetch(state);

        Page<Order> ordersPage = orderService.findByProductId(productId, pageNumber, user.getId());
        OrdersFtlPageDto orders = ordersPageToOrdersFtlPageDtoMapper.map(ordersPage);

        Product product = productService.findById(productId, user.getId());

        model.addAttribute("currentState", orderState);
        model.addAttribute("orders", orders);
        model.addAttribute("headline", String.format("Orders of %s (id: %s)",
                product.getName(), product.getId()
        ));

        return "orders/show_orders";
    }

    @GetMapping("/new")
    public String getCreateView(Model model, @AuthenticationPrincipal User user) {

        List<Client> clients = clientService.findAllActive(user.getId());
        List<Product> products = productService.findAllActive(user.getId());

        model.addAttribute("clients", clients);
        model.addAttribute("products", products);

        return "orders/new_order";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute OrderFormSubmitDto orderDto, @AuthenticationPrincipal User user) {

        orderService.create(user.getId(), orderDto);
        return "redirect:/orders";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, @AuthenticationPrincipal User user) {

        orderService.delete(id, user.getId());
        return "redirect:/orders";
    }

    @PostMapping("/cancel/{id}")
    public String cancel(@PathVariable("id") Long id, @AuthenticationPrincipal User user) {

        orderService.cancel(id, user.getId());
        return "redirect:/orders";
    }

    @PostMapping("/resolve/{id}")
    public String resolve(@PathVariable("id") Long id, @AuthenticationPrincipal User user) {

        orderService.resolve(id, user.getId());
        return "redirect:/orders";
    }

}

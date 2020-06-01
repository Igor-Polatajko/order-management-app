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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;

import static java.util.Objects.nonNull;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .append(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
            .toFormatter();

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

        Page<Order> ordersPage = orderService.findAllByState(orderState, pageNumber, user.getId());
        OrdersFtlPageDto orders = ordersPageToOrdersFtlPageDtoMapper.map(ordersPage);

        model.addAttribute("redirectBackUrl", String.format("/orders?page=%s&state=%s", pageNumber, state));
        model.addAttribute("exportUrl", "/orders/export");
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

        Page<Order> ordersPage = orderService.findByClientIdAndState(clientId, orderState, pageNumber, user.getId());
        OrdersFtlPageDto orders = ordersPageToOrdersFtlPageDtoMapper.map(ordersPage);

        Client client = clientService.findById(clientId, user.getId());

        model.addAttribute("redirectBackUrl",
                String.format("/orders/client/%s?page=%s&state=%s", clientId, pageNumber, state)
        );
        model.addAttribute("exportUrl", String.format("/orders/export?clientId=%s", clientId));
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

        Page<Order> ordersPage = orderService.findByProductIdAndState(productId, orderState, pageNumber, user.getId());
        OrdersFtlPageDto orders = ordersPageToOrdersFtlPageDtoMapper.map(ordersPage);

        Product product = productService.findById(productId, user.getId());

        model.addAttribute("redirectBackUrl",
                String.format("/orders/product/%s?page=%s&state=%s", productId, pageNumber, state)
        );
        model.addAttribute("exportUrl", String.format("/orders/export?productId=%s", productId));
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
    public String delete(@PathVariable("id") Long id,
                         @RequestParam(value = "redirect", required = false, defaultValue = "orders") String redirectUrl,
                         @AuthenticationPrincipal User user) {

        orderService.delete(id, user.getId());
        return "redirect:" + redirectUrl;
    }

    @PostMapping("/cancel/{id}")
    public String cancel(@PathVariable("id") Long id,
                         @RequestParam(value = "redirect", required = false, defaultValue = "orders") String redirectUrl,
                         @AuthenticationPrincipal User user) {

        orderService.cancel(id, user.getId());
        return "redirect:" + redirectUrl;
    }

    @PostMapping("/resolve/{id}")
    public String resolve(@PathVariable("id") Long id,
                          @RequestParam(value = "redirect", required = false, defaultValue = "orders") String redirectUrl,
                          @AuthenticationPrincipal User user) {

        orderService.resolve(id, user.getId());
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/export")
    public String download(
            @RequestParam("fromDate") String fromDate,
            @RequestParam("toDate") String toDate,
            @RequestParam(name = "clientId", required = false) Long clientId,
            @RequestParam(name = "productId", required = false) Long productId,
            Model model, @AuthenticationPrincipal User user) {

        LocalDateTime fromDateTime = LocalDateTime.parse(fromDate, DATE_TIME_FORMATTER);
        LocalDateTime toDateTime = LocalDateTime.parse(toDate, DATE_TIME_FORMATTER);

        List<Order> orders;
        if (nonNull(clientId)) {
            orders = orderService.findByClientId(clientId, user.getId(), fromDateTime, toDateTime);
        } else if (nonNull(productId)) {
            orders = orderService.findByProductId(productId, user.getId(), fromDateTime, toDateTime);
        } else {
            orders = orderService.findAll(user.getId(), fromDateTime, toDateTime);
        }

        model.addAttribute("orders", ordersPageToOrdersFtlPageDtoMapper.mapOrdersList(orders));
        return "ordersExcelView";
    }

}

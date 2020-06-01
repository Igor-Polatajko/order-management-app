package com.pnu.ordermanagementapp.controller;

import com.pnu.ordermanagementapp.dto.order.OrderFormSubmitDto;
import com.pnu.ordermanagementapp.dto.order.OrdersExportDatesRangeDto;
import com.pnu.ordermanagementapp.dto.order.OrdersExportQuery;
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

import java.time.LocalDate;
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

        Page<Order> ordersPage = orderService.findAllByState(orderState, pageNumber, user.getId());
        OrdersFtlPageDto orders = ordersPageToOrdersFtlPageDtoMapper.map(ordersPage);

        model.addAttribute("redirectBackUrl", String.format("/orders?page=%s&state=%s", pageNumber, state));
        model.addAttribute("exportUrl", "/orders/export");
        model.addAttribute("exportDates", buildDefaultExportDatesRange(user));
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
        model.addAttribute("exportDates", buildDefaultExportDatesRange(user));
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
        model.addAttribute("exportDates", buildDefaultExportDatesRange(user));
        model.addAttribute("currentState", orderState);
        model.addAttribute("orders", orders);
        model.addAttribute("headline", String.format("Orders of %s (id: %s)",
                product.getName(), product.getId()
        ));

        return "orders/show_orders";
    }

    @GetMapping("/export")
    public String download(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam(name = "clientId", required = false) Long clientId,
            @RequestParam(name = "productId", required = false) Long productId,
            Model model, @AuthenticationPrincipal User user) {

        OrdersExportQuery ordersExportQuery = OrdersExportQuery.builder()
                .ordersExportDatesRangeDto(OrdersExportDatesRangeDto.builder()
                        .startDate(startDate)
                        .endDate(endDate)
                        .build())
                .clientId(clientId)
                .productId(productId)
                .userId(user.getId())
                .build();

        List<Order> orders = orderService.findForExport(ordersExportQuery);
        model.addAttribute("orders", ordersPageToOrdersFtlPageDtoMapper.mapOrdersList(orders));
        return "ordersExcelView";
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

    private OrdersExportDatesRangeDto buildDefaultExportDatesRange(User user) {
        return OrdersExportDatesRangeDto.builder()
                .startDate(user.getCreatedDateTime().toLocalDate().toString())
                .endDate(LocalDate.now().toString())
                .build();
    }

}

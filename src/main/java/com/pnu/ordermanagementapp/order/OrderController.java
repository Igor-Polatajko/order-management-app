package com.pnu.ordermanagementapp.order;

import com.pnu.ordermanagementapp.adapter.DbAdapter;
import com.pnu.ordermanagementapp.model.Client;
import com.pnu.ordermanagementapp.model.Order;
import com.pnu.ordermanagementapp.model.Product;
import com.pnu.ordermanagementapp.order.dto.OrderFormSubmitDto;
import com.pnu.ordermanagementapp.order.dto.OrderFtlDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private OrderDbAdapter orderDbAdapter;

    private DbAdapter<Client> clientDbAdapter;

    private DbAdapter<Product> productDbAdapter;

    @Autowired
    public OrderController(OrderDbAdapter orderDbAdapter,
                           DbAdapter<Client> clientDbAdapter,
                           DbAdapter<Product> productDbAdapter) {
        this.orderDbAdapter = orderDbAdapter;
        this.clientDbAdapter = clientDbAdapter;
        this.productDbAdapter = productDbAdapter;
    }

    @GetMapping
    public String getAll(Model model) {

        List<OrderFtlDto> orders = mapToOrderDtos(
                sortedByCreatedDate(orderDbAdapter.findAll()));

        model.addAttribute("orders", orders);

        return "show_orders";
    }

    @GetMapping("/client/{clientId}")
    public String getAllForClient(@PathVariable("clientId") Long clientId, Model model) {

        List<OrderFtlDto> orders = mapToOrderDtos(
                sortedByCreatedDate(orderDbAdapter.findByClientId(clientId)));

        model.addAttribute("orders", orders);

        return "show_orders";
    }

    @GetMapping("/product/{productId}")
    public String getAllForProduct(@PathVariable("productId") Long productId, Model model) {

        List<OrderFtlDto> orders = mapToOrderDtos(
                sortedByCreatedDate(orderDbAdapter.findByClientId(productId)));

        model.addAttribute("orders", orders);

        return "show_orders";
    }

    @GetMapping("/new")
    public String createNew(Model model) {

        List<Client> clients = clientDbAdapter.findAll();
        List<Product> products = productDbAdapter.findAll();

        Map<String, Object> params = new HashMap<>();
        params.put("clients", clients);
        params.put("products", products);
        model.addAllAttributes(params);

        return "new_order";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute OrderFormSubmitDto orderDto) {

        Client client = clientDbAdapter.findById(orderDto.getClientId());
        Product product = productDbAdapter.findById(orderDto.getProductId());

        Order order = Order.builder()
                .product(product)
                .client(client)
                .amount(orderDto.getAmount())
                .createdDate(LocalDateTime.now())
                .build();

        orderDbAdapter.create(order);
        return "redirect:/orders";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        orderDbAdapter.delete(id);
        return "redirect:/orders";
    }

    private List<Order> sortedByCreatedDate(List<Order> orders) {
        return orders.stream()
                .sorted(Comparator.comparing(Order::getCreatedDate))
                .collect(Collectors.toList());
    }

    private List<OrderFtlDto> mapToOrderDtos(List<Order> orders) {
        return orders.stream()
                .map(order -> OrderFtlDto.builder()
                        .orderId(order.getId())
                        .productName(order.getProduct().getName())
                        .clientFirstName(order.getClient().getFirstName())
                        .clientLastName(order.getClient().getLastName())
                        .clientEmail(order.getClient().getEmail())
                        .itemPrice(order.getProduct().getPrice())
                        .productOrderAmount(order.getAmount())
                        .totalPrice(calcTotalPrice(order))
                        .createdDate(order.getCreatedDate().toString())
                        .build())
                .collect(Collectors.toList());
    }


    private double calcTotalPrice(Order order) {
        return order.getProduct().getPrice() * order.getAmount();
    }

}

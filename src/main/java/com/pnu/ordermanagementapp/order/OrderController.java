package com.pnu.ordermanagementapp.order;

import com.pnu.ordermanagementapp.model.Client;
import com.pnu.ordermanagementapp.model.Order;
import com.pnu.ordermanagementapp.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private OrderDbAdapter orderDbAdapter;

    @Autowired
    public OrderController(OrderDbAdapter orderDbAdapter) {
        this.orderDbAdapter = orderDbAdapter;
    }

    public List<Order> setup() { // ToDo delete later
        return IntStream.range(0, 6)
                .mapToObj(i -> Order.builder()
                        .id(new Long(i))
                        .client(Client.builder()
                                .firstName("name_" + i)
                                .lastName("lastName_" + i)
                                .email("email_" + i)
                                .build())
                        .amount(5)
                        .product(Product.builder()
                                .name("some product")
                                .price(25)
                                .build())
                        .createdDate(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());
    }

    @GetMapping
    public String getAll(Model model) {

        List<OrderDto> orders = mapToOrderDto(
                sortedByCreatedDate(orderDbAdapter.findAll()));

        orders = mapToOrderDto(setup()); // ToDo remove later

        model.addAllAttributes(Collections.singletonMap("orders", orders));

        return "show_orders";
    }

    @GetMapping("/client/{clientId}")
    public String getAllForClient(@PathVariable("clientId") Long clientId, Model model) {

        List<OrderDto> orders = mapToOrderDto(
                sortedByCreatedDate(orderDbAdapter.findByClientId(clientId)));

        orders = mapToOrderDto(setup()); // ToDo remove later

        model.addAllAttributes(Collections.singletonMap("orders", orders));

        return "show_orders";
    }

    @GetMapping("/new")
    public String createNew() {
        return "new_order";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute Order order) {
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

    private List<OrderDto> mapToOrderDto(List<Order> orders) {
        return orders.stream()
                .map(order -> OrderDto.builder()
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

package com.pnu.ordermanagementapp.order;

import com.pnu.ordermanagementapp.model.Order;
import com.pnu.ordermanagementapp.order.dto.OrderFtlDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrdersToOrderFtlDtosMapper {

    public List<OrderFtlDto> map(List<Order> orders) {
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

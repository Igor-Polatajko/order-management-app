package com.pnu.ordermanagementapp.order;

import com.pnu.ordermanagementapp.model.Order;
import com.pnu.ordermanagementapp.order.dto.OrderFtlDto;
import com.pnu.ordermanagementapp.order.dto.OrdersFtlPageDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrdersPageToOrdersFtlPageDtoMapper {

    public OrdersFtlPageDto map(Page<Order> ordersPage) {
        return OrdersFtlPageDto.builder()
                .content(mapOrdersList(ordersPage.getContent()))
                .currentPageNumber(ordersPage.getNumber() + 1) // starting from one
                .totalPageNumber(ordersPage.getTotalPages())
                .hasNextPage(ordersPage.hasNext())
                .hasPreviousPage(ordersPage.hasPrevious())
                .build();
    }

    private List<OrderFtlDto> mapOrdersList(List<Order> orders) {
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

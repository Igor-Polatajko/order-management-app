package com.pnu.ordermanagementapp.service;

import com.pnu.ordermanagementapp.dto.order.OrderFormSubmitDto;
import com.pnu.ordermanagementapp.model.Order;
import org.springframework.data.domain.Page;

public interface OrderService {

    Page<Order> findByClientId(Long clintId, int pageNumber, Long userId);

    Page<Order> findByProductId(Long productId, int pageNumber, Long userId);

    Page<Order> findAll(int pageNumber, Long userId);

    void create(Long userId, OrderFormSubmitDto orderDto);

    void delete(Long orderId, Long userId);

    void cancel(Long orderId, Long userId);

    void resolve(Long orderId, Long userId);
}

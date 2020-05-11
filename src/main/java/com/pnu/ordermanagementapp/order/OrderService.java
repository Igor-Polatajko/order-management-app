package com.pnu.ordermanagementapp.order;

import com.pnu.ordermanagementapp.model.Order;
import org.springframework.data.domain.Page;

public interface OrderService {

    Page<Order> findByClientId(Long clintId, int pageNumber, Long userId);

    Page<Order> findByProductId(Long productId, int pageNumber, Long userId);

    Page<Order> findAll(int pageNumber, Long userId);

    void create(Order order);

    void delete(Long orderId, Long userId);
}

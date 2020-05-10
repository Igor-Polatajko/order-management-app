package com.pnu.ordermanagementapp.order;

import com.pnu.ordermanagementapp.model.Order;
import org.springframework.data.domain.Page;

public interface OrderService {

    Page<Order> findByClientId(Long clintId, int pageNumber);

    Page<Order> findByProductId(Long productId, int pageNumber);

    Page<Order> findAll(int pageNumber);

    void create(Order obj);

    void update(Order obj);

    void delete(Long id);
}

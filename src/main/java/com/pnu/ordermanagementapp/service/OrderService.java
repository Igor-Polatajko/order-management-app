package com.pnu.ordermanagementapp.service;

import com.pnu.ordermanagementapp.dto.order.OrderFormSubmitDto;
import com.pnu.ordermanagementapp.dto.order.OrdersExportQuery;
import com.pnu.ordermanagementapp.model.Order;
import com.pnu.ordermanagementapp.model.OrderState;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {

    Page<Order> findByClientIdAndState(Long clintId, OrderState state, int pageNumber, Long userId);

    Page<Order> findByProductIdAndState(Long productId, OrderState state, int pageNumber, Long userId);

    Page<Order> findAllByState(OrderState state, int pageNumber, Long userId);

    void create(Long userId, OrderFormSubmitDto orderDto);

    void delete(Long orderId, Long userId);

    void cancel(Long orderId, Long userId);

    void resolve(Long orderId, Long userId);

    List<Order> findForExport(OrdersExportQuery ordersExportQuery);
}

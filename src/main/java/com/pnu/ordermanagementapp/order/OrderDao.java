package com.pnu.ordermanagementapp.order;

import com.pnu.ordermanagementapp.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDao extends JpaRepository<Order, Long> {

    Page<Order> findAll(Pageable pageable);

    Page<Order> findByClientId(Long clintId, Pageable pageable);

    Page<Order> findByProductId(Long productId, Pageable pageable);

}

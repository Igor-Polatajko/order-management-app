package com.pnu.ordermanagementapp.order;

import com.pnu.ordermanagementapp.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderDao extends JpaRepository<Order, Long> {

    Page<Order> findAllByUserId(Long userId, Pageable pageable);

    Page<Order> findByClientIdAndUserId(Long clintId, Long userId, Pageable pageable);

    Page<Order> findByProductIdAndUserId(Long productId, Long userId, Pageable pageable);

    Optional<Order> findByIdAndUserId(Long id, Long userId);

}

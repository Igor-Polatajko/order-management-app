package com.pnu.ordermanagementapp.repository;

import com.pnu.ordermanagementapp.model.Order;
import com.pnu.ordermanagementapp.model.OrderState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findAllByStateAndUserId(OrderState state, Long userId, Pageable pageable);

    Page<Order> findByClientIdAndStateAndUserId(Long clintId, OrderState state, Long userId, Pageable pageable);

    Page<Order> findByProductIdAndStateAndUserId(Long productId, OrderState state, Long userId, Pageable pageable);

    Optional<Order> findByIdAndUserId(Long id, Long userId);

    List<Order> findByClientIdAndUserIdAndCreatedDateTimeBetween(
            Long clintId, Long userId, LocalDateTime from, LocalDateTime to, Sort sort);

    List<Order> findByProductIdAndUserIdAndCreatedDateTimeBetween(
            Long productId, Long userId, LocalDateTime from, LocalDateTime to, Sort sort);

    List<Order> findAllByUserIdAndCreatedDateTimeBetween(
            Long userId, LocalDateTime from, LocalDateTime to, Sort sort);

}

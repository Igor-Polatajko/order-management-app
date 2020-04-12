package com.pnu.ordermanagementapp.order;

import com.pnu.ordermanagementapp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDao extends JpaRepository<Order, Long> {

    List<Order> findByClientId(Long clintId);

}

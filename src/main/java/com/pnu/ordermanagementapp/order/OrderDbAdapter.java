package com.pnu.ordermanagementapp.order;

import com.pnu.ordermanagementapp.adapter.DbAdapter;
import com.pnu.ordermanagementapp.model.Order;
import org.springframework.data.domain.Page;

public interface OrderDbAdapter extends DbAdapter<Order> {

    Page<Order> findByClientId(Long clintId, int pageNumber);

    Page<Order> findByProductId(Long productId, int pageNumber);
}

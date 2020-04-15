package com.pnu.ordermanagementapp.order;

import com.pnu.ordermanagementapp.adapter.DbAdapter;
import com.pnu.ordermanagementapp.model.Order;

import java.util.List;

public interface OrderDbAdapter extends DbAdapter<Order> {

    List<Order> findByClientId(Long id);

    List<Order> findByProductId(Long id);

}

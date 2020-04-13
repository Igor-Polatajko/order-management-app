package com.pnu.ordermanagementapp.order;

import com.pnu.ordermanagementapp.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OrderDbAdapterImpl implements OrderDbAdapter { // added only to meet task requirements

    private OrderDao orderDao;

    @Autowired
    public OrderDbAdapterImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    @Override
    public Order findById(Long id) {
        return orderDao.findById(id).orElse(null);
    }

    @Override
    public void create(Order order) {
        orderDao.save(order);
    }

    @Override
    public void update(Order order) {
        orderDao.save(order);
    }

    @Override
    public void delete(Long id) {
        Optional<Order> orderOptional = orderDao.findById(id);

        orderOptional.ifPresent(order -> orderDao.delete(order));
    }

    @Override
    public List<Order> findByClientId(Long id) {
        return orderDao.findByClientId(id);
    }
}

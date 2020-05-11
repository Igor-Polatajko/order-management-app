package com.pnu.ordermanagementapp.order;

import com.pnu.ordermanagementapp.exception.ServiceException;
import com.pnu.ordermanagementapp.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private static final int PAGE_SIZE = 10;

    private OrderDao orderDao;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public Page<Order> findAll(int pageNumber, Long userId) {
        Pageable pageable = createPageable(pageNumber);
        return orderDao.findAllByUserId(userId, pageable);
    }

    @Override
    public void create(Order order) {
        orderDao.save(order);
    }

    @Override
    public void delete(Long id, Long userId) {
        Optional<Order> orderOptional = orderDao.findByIdAndUserId(id, userId);
        Order order = orderOptional.orElseThrow(() -> new ServiceException("Order doesn't exist!"));
        orderDao.delete(order);
    }

    @Override
    public Page<Order> findByClientId(Long id, int pageNumber, Long userId) {
        Pageable pageable = createPageable(pageNumber);
        return orderDao.findByClientIdAndUserId(id, userId, pageable);
    }

    @Override
    public Page<Order> findByProductId(Long id, int pageNumber, Long userId) {
        Pageable pageable = createPageable(pageNumber);
        return orderDao.findByProductIdAndUserId(id, userId, pageable);
    }

    private Pageable createPageable(int pageNumber) {

        if (pageNumber < 1) {
            throw new ServiceException("Incorrect page number!");
        }

        return PageRequest.of(pageNumber - 1, PAGE_SIZE, Sort.by("createdDate").descending());
    }
}

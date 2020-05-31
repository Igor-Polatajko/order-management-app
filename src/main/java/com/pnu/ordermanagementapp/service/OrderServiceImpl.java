package com.pnu.ordermanagementapp.service;

import com.pnu.ordermanagementapp.dto.order.OrderFormSubmitDto;
import com.pnu.ordermanagementapp.exception.ServiceException;
import com.pnu.ordermanagementapp.model.Client;
import com.pnu.ordermanagementapp.model.Order;
import com.pnu.ordermanagementapp.model.OrderState;
import com.pnu.ordermanagementapp.model.Product;
import com.pnu.ordermanagementapp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private static final int PAGE_SIZE = 10;

    private static final Sort SORT = Sort.by("createdDate").descending();

    private OrderRepository orderRepository;

    private ClientService clientService;

    private ProductService productService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            ClientService clientService,
                            ProductService productService) {

        this.orderRepository = orderRepository;
        this.clientService = clientService;
        this.productService = productService;
    }

    @Override
    public Page<Order> findAllByState(OrderState state, int pageNumber, Long userId) {
        Pageable pageable = createPageable(pageNumber);
        return orderRepository.findAllByStateAndUserId(state, userId, pageable);
    }

    @Override
    public List<Order> findByClientId(Long clintId, Long userId) {
        return orderRepository.findByClientIdAndUserId(clintId, userId, SORT);
    }

    @Override
    public List<Order> findByProductId(Long productId, Long userId) {
        return orderRepository.findByProductIdAndUserId(productId, userId, SORT);
    }

    @Override
    public List<Order> findAll(Long userId) {
        return orderRepository.findAllByUserId(userId, SORT);
    }

    @Override
    public Page<Order> findByClientIdAndState(Long id, OrderState state, int pageNumber, Long userId) {
        Pageable pageable = createPageable(pageNumber);
        return orderRepository.findByClientIdAndStateAndUserId(id, state, userId, pageable);
    }

    @Override
    public Page<Order> findByProductIdAndState(Long id, OrderState state, int pageNumber, Long userId) {
        Pageable pageable = createPageable(pageNumber);
        return orderRepository.findByProductIdAndStateAndUserId(id, state, userId, pageable);
    }

    @Override
    @Transactional
    public void create(Long userId, OrderFormSubmitDto orderDto) {
        Product product = productService.findById(orderDto.getProductId(), userId);

        if (!product.isActive()) {
            throw new ServiceException("Cannot create order. Product is inactive");
        }

        Client client = clientService.findById(orderDto.getClientId(), userId);
        if (!client.isActive()) {
            throw new ServiceException("Cannot create order. Client is inactive");
        }

        if (product.getAmount() < orderDto.getAmount()) {
            throw new ServiceException("Cannot create order. Product amount is less the requested");
        }

        Order order = Order.builder()
                .product(product)
                .client(client)
                .amount(orderDto.getAmount())
                .state(OrderState.PENDING)
                .createdDate(LocalDateTime.now())
                .userId(userId)
                .build();

        Product updatedProduct = product.toBuilder()
                .amount(product.getAmount() - orderDto.getAmount())
                .build();

        orderRepository.save(order);
        productService.update(updatedProduct, userId);
    }

    @Override
    public void delete(Long id, Long userId) {
        Order order = getOrderOrThrowException(id, userId);

        if (order.getState() == OrderState.PENDING) {
            throw new ServiceException("Cannot delete pending order. Cancel or resolve it first");
        }

        orderRepository.delete(order);
    }

    @Transactional
    @Override
    public void cancel(Long id, Long userId) {
        Order order = getOrderOrThrowException(id, userId);

        if (order.getState() == OrderState.CANCELLED) {
            throw new ServiceException("Order is already cancelled");
        }

        Order updatedOrder = order.toBuilder()
                .state(OrderState.CANCELLED)
                .build();

        Product product = order.getProduct();

        Product updatedProduct = product.toBuilder()
                .amount(product.getAmount() + order.getAmount())
                .build();

        productService.update(updatedProduct, userId);
        orderRepository.save(updatedOrder);
    }

    @Override
    public void resolve(Long id, Long userId) {
        Order order = getOrderOrThrowException(id, userId);

        if (order.getState() != OrderState.PENDING) {
            throw new ServiceException("Order state is not pending");
        }

        Order updatedOrder = order.toBuilder()
                .state(OrderState.RESOLVED)
                .build();

        orderRepository.save(updatedOrder);
    }

    private Order getOrderOrThrowException(Long id, Long userId) {
        Optional<Order> order = orderRepository.findByIdAndUserId(id, userId);
        return order.orElseThrow(() -> new ServiceException("Order doesn't exist!"));
    }

    private Pageable createPageable(int pageNumber) {

        if (pageNumber < 1) {
            throw new ServiceException("Incorrect page number!");
        }

        return PageRequest.of(pageNumber - 1, PAGE_SIZE, SORT);
    }
}

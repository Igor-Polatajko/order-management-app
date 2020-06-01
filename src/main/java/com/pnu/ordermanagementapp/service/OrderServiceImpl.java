package com.pnu.ordermanagementapp.service;

import com.pnu.ordermanagementapp.dto.order.OrderFormSubmitDto;
import com.pnu.ordermanagementapp.dto.order.OrdersExportQuery;
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
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class OrderServiceImpl implements OrderService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .append(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
            .toFormatter();

    private static final int PAGE_SIZE = 10;

    private static final Sort SORT = Sort.by("updatedDateTime").descending();

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
    public List<Order> findForExport(OrdersExportQuery ordersExportQuery) {

        LocalDateTime fromDateTime = LocalDateTime
                .parse(ordersExportQuery.getOrdersExportDatesRangeDto().getStartDate(), DATE_TIME_FORMATTER);
        LocalDateTime toDateTime = LocalDateTime
                .parse(ordersExportQuery.getOrdersExportDatesRangeDto().getEndDate(), DATE_TIME_FORMATTER);

        if (nonNull(ordersExportQuery.getClientId())) {
            return orderRepository.findByClientIdAndUserIdAndCreatedDateTimeBetween(
                    ordersExportQuery.getClientId(),
                    ordersExportQuery.getUserId(),
                    fromDateTime,
                    toDateTime, SORT);
        }

        if (nonNull(ordersExportQuery.getProductId())) {
            return orderRepository.findByProductIdAndUserIdAndCreatedDateTimeBetween(
                    ordersExportQuery.getProductId(),
                    ordersExportQuery.getUserId(),
                    fromDateTime,
                    toDateTime,
                    SORT);
        }

        return orderRepository.findAllByUserIdAndCreatedDateTimeBetween(
                ordersExportQuery.getUserId(),
                fromDateTime,
                toDateTime,
                SORT);
    }

    @Override
    @Transactional
    public void create(Long userId, OrderFormSubmitDto orderDto) {

        if (isNull(orderDto.getClientId()) || isNull(orderDto.getProductId())) {
            throw new ServiceException("Cannot create order. Client and product should be selected");
        }

        if (orderDto.getAmount() <= 0) {
            throw new ServiceException("Cannot create order. Amount should be greater than zero");
        }

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

        LocalDateTime localDateTimeNow = LocalDateTime.now();

        Order order = Order.builder()
                .product(product)
                .client(client)
                .amount(orderDto.getAmount())
                .state(OrderState.PENDING)
                .createdDateTime(localDateTimeNow)
                .updatedDateTime(localDateTimeNow)
                .userId(userId)
                .build();

        orderRepository.save(order);

        Product updatedProduct = product.toBuilder()
                .amount(product.getAmount() - orderDto.getAmount())
                .build();

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
                .updatedDateTime(LocalDateTime.now())
                .build();

        orderRepository.save(updatedOrder);

        Product product = order.getProduct();

        Product updatedProduct = product.toBuilder()
                .amount(product.getAmount() + order.getAmount())
                .build();

        productService.update(updatedProduct, userId);
    }

    @Override
    public void resolve(Long id, Long userId) {
        Order order = getOrderOrThrowException(id, userId);

        if (order.getState() != OrderState.PENDING) {
            throw new ServiceException("Order state is not pending");
        }

        Order updatedOrder = order.toBuilder()
                .state(OrderState.RESOLVED)
                .updatedDateTime(LocalDateTime.now())
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

package com.testtaskalex.services.impl;

import com.testtaskalex.constants.Constants;
import com.testtaskalex.dtos.ItemDto;
import com.testtaskalex.dtos.OrderDto;
import com.testtaskalex.dtos.PaymentDto;
import com.testtaskalex.dtos.resources.OrderResource;
import com.testtaskalex.dtos.resources.PaymentResourceSum;
import com.testtaskalex.enums.OrderStatus;
import com.testtaskalex.exceptions.BadRequestException;
import com.testtaskalex.exceptions.OrderStatusException;
import com.testtaskalex.exceptions.ResourceNotFoundException;
import com.testtaskalex.mappers.ItemMapper;
import com.testtaskalex.mappers.OrderMapper;
import com.testtaskalex.mappers.PaymentMapper;
import com.testtaskalex.persistance.entities.Item;
import com.testtaskalex.persistance.entities.Order;
import com.testtaskalex.persistance.entities.Payment;
import com.testtaskalex.persistance.repositories.ItemRepository;
import com.testtaskalex.persistance.repositories.OrderRepository;
import com.testtaskalex.persistance.repositories.PaymentRepository;
import com.testtaskalex.services.OrderService;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.testtaskalex.constants.Constants.*;

@Service
@EqualsAndHashCode
@ToString
public class OrderImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    private ItemMapper itemMapper;

    @Override
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<OrderDto> allOrders = orders.stream().map((Order order) ->
                orderMapper.toDto(order)).collect(Collectors.toList());
        return new ResponseEntity<>(allOrders, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<OrderDto> getOrder(Long id) {
        Order order = orderRepository
                .findById(id).orElseThrow(() ->
                        new ResourceNotFoundException(
                                String.format(Constants.NOT_FOUND_ORDER_BY_ID, id)));
        OrderDto orderDto = orderMapper.toDto(order);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<OrderDto> createOrder(OrderResource orderBody) {
        Order newOrder = new Order();
        newOrder.setStatus(OrderStatus.CREATED);
        newOrder.setTotal_items(orderBody.getTotal_items());
        newOrder.setTotal_payments(orderBody.getTotal_payments());

        Order savedOrder = orderRepository.save(newOrder);
        OrderDto paymentDto = orderMapper.toDto(savedOrder);
        return new ResponseEntity<>(paymentDto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<OrderDto> updateOrder(Long id, OrderResource orderBody) {
        Order updatedOrder = orderRepository
                .findById(id).orElseThrow(() ->
                        new ResourceNotFoundException(
                                String.format(Constants.NOT_FOUND_ORDER_BY_ID, id)));
        updatedOrder.setStatus(orderBody.getStatus());
        updatedOrder.setTotal_items(orderBody.getTotal_items());
        updatedOrder.setTotal_payments(orderBody.getTotal_payments());
        Order savedOrder = orderRepository.save(updatedOrder);
        return new ResponseEntity<>(orderMapper.toDto(savedOrder), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteOrder(Long id) throws ResourceNotFoundException {
        Order order = orderRepository
                .findById(id).orElseThrow(() ->
                        new ResourceNotFoundException(
                                String.format(Constants.NOT_FOUND_ORDER_BY_ID, id)));
        orderRepository.delete(order);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<OrderDto> addItemToOrder(Long orderId, Long itemId) {

        Order order = orderRepository
                .findById(orderId).orElseThrow(() ->
                        new ResourceNotFoundException(
                                String.format(Constants.NOT_FOUND_ORDER_BY_ID, orderId)));
        ;
        Item item = itemRepository
                .findById(itemId).orElseThrow(() ->
                        new ResourceNotFoundException(
                                String.format(Constants.NOT_FOUND_ITEM_BY_ID, itemId)));

        OrderStatus currentStatus = order.getStatus();
        if (Objects.equals(currentStatus, OrderStatus.DELIVERED) ||
                Objects.equals(currentStatus, OrderStatus.SHIPPING)) {
            throw new BadRequestException(FORBIDDEN_ADD_ITEM_TO_ORDER + currentStatus);
        }

        order.setTotal_items(order.getTotal_items() + 1);
        order.setTotal_payments(order.getTotal_payments() + item.getPrice());
        order.setStatus(OrderStatus.CREATED);


        Set<Order> itemOrders = item.getOrders();
        itemOrders.add(order);

        item.setOrders(itemOrders);
        Set<Item> allOrderItems = order.getItems();
        allOrderItems.add(item);
        ;
        order.setItems(allOrderItems);
        Order savedOrder = orderRepository.save(order);
        itemRepository.save(item);
        return new ResponseEntity<>(orderMapper.toDto(savedOrder), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<OrderDto> payForTheOrder(Long orderId, PaymentResourceSum paymentSum) {
        Payment currentPayment = new Payment();
        Order order = orderRepository
                .findById(orderId).orElseThrow(() ->
                        new ResourceNotFoundException(
                                String.format(Constants.NOT_FOUND_ORDER_BY_ID, orderId)));

        currentPayment.setSum(paymentSum.getSum());
        currentPayment.setPayment_date(Timestamp.valueOf(LocalDateTime.now()));
        currentPayment.setOrder(order);

        if (Objects.equals(order.getStatus(), OrderStatus.CREATED)) {
            double total = order.getTotal_payments() - paymentSum.getSum();
            order.setTotal_payments(total);
            if (total <= 0) {
                order.setStatus(OrderStatus.PROCESSING);
            }
        } else {
            return new ResponseEntity<>(orderMapper.toDto(order), HttpStatus.OK);
        }
        paymentRepository.save(currentPayment);
        Order savedOrder = orderRepository.save(order);
        OrderDto orderDto = orderMapper.toDto(savedOrder);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<PaymentDto>> getOrderPayments(Long orderId) {
        List<Payment> payments = paymentRepository.getAllPaymentsByOrderId(orderId);
        if (payments.isEmpty()) {
            throw new ResourceNotFoundException(NOT_FOUND_ORDERS + orderId);
        }
        List<PaymentDto> allPayments = payments.stream().map((Payment payment) ->
                paymentMapper.toDto(payment)).collect(Collectors.toList());

        return new ResponseEntity<>(allPayments, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ItemDto>> getOrderItems(Long orderId) {
        Order order = orderRepository
                .findById(orderId).orElseThrow(() ->
                        new ResourceNotFoundException(
                                String.format(Constants.NOT_FOUND_ORDER_BY_ID, orderId)));

        Set<Item> orderItems = order.getItems();
        if (orderItems.isEmpty()) {
            throw new ResourceNotFoundException(NOT_FOUND_ITEMS_ORDERS + orderId);
        }
        List<ItemDto> allItems = orderItems.stream().map((Item item) ->
                itemMapper.toDto(item)).collect(Collectors.toList());

        return new ResponseEntity<>(allItems, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<OrderDto> shipOrder(Long orderId) throws ResourceNotFoundException {
        Order order = orderRepository
                .findById(orderId).orElseThrow(() ->
                        new ResourceNotFoundException(
                                String.format(Constants.NOT_FOUND_ORDER_BY_ID, orderId)));
        double total = order.getTotal_payments();
        if (Objects.equals(order.getStatus(), OrderStatus.PROCESSING) && total <= 0) {
            order.setStatus(OrderStatus.SHIPPING);
        } else {
            throw new OrderStatusException(FORBIDDEN_CHANGE_STATUS_ORDER);
        }
        Order savedOrder = orderRepository.save(order);
        return new ResponseEntity<>(orderMapper.toDto(savedOrder), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<OrderDto> deliveredOrder(Long orderId)
            throws ResourceNotFoundException, OrderStatusException {

        Order order = orderRepository
                .findById(orderId).orElseThrow(() ->
                        new ResourceNotFoundException(
                                String.format(Constants.NOT_FOUND_ORDER_BY_ID, orderId)));

        double total = order.getTotal_payments();
        if (Objects.equals(order.getStatus(), OrderStatus.SHIPPING) && total <= 0) {
            order.setStatus(OrderStatus.DELIVERED);
        } else {
            throw new OrderStatusException(FORBIDDEN_CHANGE_STATUS_ORDER);
        }
        Order savedOrder = orderRepository.save(order);
        return new ResponseEntity<>(orderMapper.toDto(savedOrder), HttpStatus.OK);
    }

}

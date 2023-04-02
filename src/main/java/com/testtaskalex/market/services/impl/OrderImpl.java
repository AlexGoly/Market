package com.testtaskalex.market.services.impl;

import com.testtaskalex.market.dtos.*;
import com.testtaskalex.market.exceptions.BadRequestException;
import com.testtaskalex.market.exceptions.OrderStatusException;
import com.testtaskalex.market.exceptions.ResourceNotFoundException;
import com.testtaskalex.market.mappers.ItemMapper;
import com.testtaskalex.market.mappers.OrderMapper;
import com.testtaskalex.market.mappers.PaymentMapper;
import com.testtaskalex.market.persistance.entities.Item;
import com.testtaskalex.market.persistance.entities.Order;
import com.testtaskalex.market.persistance.entities.Payment;
import com.testtaskalex.market.persistance.repositories.ItemRepository;
import com.testtaskalex.market.persistance.repositories.OrderRepository;
import com.testtaskalex.market.persistance.repositories.PaymentRepository;
import com.testtaskalex.market.services.OrderService;
import com.testtaskalex.market.services.OrderStatus;
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
                        new ResourceNotFoundException("Not found Order with id = " + id));
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
                        new ResourceNotFoundException(("Not found Order with id = " + id)));
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
                        new ResourceNotFoundException(("Not found Order with id = " + id)));
        orderRepository.delete(order);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<OrderDto> addItemToOrder(Long orderId, Long itemId) {

        Order order = orderRepository
                .findById(orderId).orElseThrow(() ->
                        new ResourceNotFoundException("Not found Order with id = " + orderId));
        Item item = itemRepository
                .findById(itemId).orElseThrow(() ->
                        new ResourceNotFoundException("Not found item with id = " + itemId));

        OrderStatus currentStatus = order.getStatus();
        if (Objects.equals(currentStatus, OrderStatus.DELIVERED) ||
                Objects.equals(currentStatus, OrderStatus.SHIPPING)) {
            throw new BadRequestException("You can’t add items to Order with status= " + currentStatus);
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
                        new ResourceNotFoundException("Not found Order with id = " + orderId));

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
            throw new ResourceNotFoundException("Not found payments order :: " + orderId);
        }
        List<PaymentDto> allPayments = payments.stream().map((Payment payment) ->
                paymentMapper.toDto(payment)).collect(Collectors.toList());

        return new ResponseEntity<>(allPayments, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ItemDto>> getOrderItems(Long orderId) {
        Order order = orderRepository
                .findById(orderId).orElseThrow(() ->
                        new ResourceNotFoundException("Not found Order with id = " + orderId));

        Set<Item> orderItems = order.getItems();
        if (orderItems.isEmpty()) {
            throw new ResourceNotFoundException("No items in order :: " + orderId);
        }
        List<ItemDto> allItems = orderItems.stream().map((Item item) ->
                itemMapper.toDto(item)).collect(Collectors.toList());

        return new ResponseEntity<>(allItems, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<OrderDto> shipOrder(Long orderId) throws ResourceNotFoundException {
        Order order = orderRepository
                .findById(orderId).orElseThrow(() ->
                        new ResourceNotFoundException("Not found Order with id :: " + orderId));
        double total = order.getTotal_payments();
        if (Objects.equals(order.getStatus(), OrderStatus.PROCESSING) && total <= 0) {
            order.setStatus(OrderStatus.SHIPPING);
        } else {
            throw new OrderStatusException("Order status can't change");
        }
        Order savedOrder = orderRepository.save(order);
        return new ResponseEntity<>(orderMapper.toDto(savedOrder), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<OrderDto> deliveredOrder(Long orderId)
            throws ResourceNotFoundException, OrderStatusException {

        Order order = orderRepository
                .findById(orderId).orElseThrow(() ->
                        new ResourceNotFoundException("Not found Order with id :: " + orderId));

        double total = order.getTotal_payments();
        if (Objects.equals(order.getStatus(), OrderStatus.SHIPPING) && total <= 0) {
            order.setStatus(OrderStatus.DELIVERED);
        } else {
            throw new OrderStatusException("Order status can't change");
        }
        Order savedOrder = orderRepository.save(order);
        return new ResponseEntity<>(orderMapper.toDto(savedOrder), HttpStatus.OK);
    }

}

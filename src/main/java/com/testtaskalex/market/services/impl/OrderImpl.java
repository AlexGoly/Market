package com.testtaskalex.market.services.impl;

import com.testtaskalex.market.dtos.OrderDto;
import com.testtaskalex.market.exceptions.ResourceNotFoundException;
import com.testtaskalex.market.mappers.OrderMapper;
import com.testtaskalex.market.persistance.entities.Order;
import com.testtaskalex.market.persistance.repositories.OrderRepository;
import com.testtaskalex.market.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderMapper orderMapper;

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
    public ResponseEntity<OrderDto> createOrder(Order orderBody) {
        Order newOrder = orderRepository.save(orderBody);
        OrderDto paymentDto = orderMapper.toDto(newOrder);
        return new ResponseEntity<>(paymentDto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<OrderDto> updateOrder(Long id, Order order) {
        Order updatedOrder = orderRepository
                .findById(id).orElseThrow(() ->
                        new ResourceNotFoundException(("Not found Order with id = " + id)));
        updatedOrder.setStatus(order.getStatus());
        updatedOrder.setTotal_items(order.getTotal_items());
        updatedOrder.setTotal_payments(order.getTotal_payments());
        Order savedOrder = orderRepository.save(updatedOrder);
        return new ResponseEntity<>(orderMapper.toDto(savedOrder), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteOrder(Long id) {
        orderRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

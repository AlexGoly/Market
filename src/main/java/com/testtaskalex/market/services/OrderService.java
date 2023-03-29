package com.testtaskalex.market.services;

import com.testtaskalex.market.dtos.OrderDto;
import com.testtaskalex.market.persistance.entities.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {
    ResponseEntity<List<OrderDto>> getAllOrders();

    ResponseEntity<OrderDto> getOrder(Long id);

    ResponseEntity<OrderDto> createOrder(Order order);

    ResponseEntity<OrderDto> updateOrder(Long id, Order order);

    ResponseEntity<HttpStatus> deleteOrder(Long id);
}

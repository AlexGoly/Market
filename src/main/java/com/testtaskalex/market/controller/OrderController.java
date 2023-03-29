package com.testtaskalex.market.controller;

import com.testtaskalex.market.dtos.OrderDto;
import com.testtaskalex.market.persistance.entities.Order;
import com.testtaskalex.market.persistance.repositories.OrderRepository;
import com.testtaskalex.market.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrders() {
        return orderService.getAllOrders();
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> returnOrder(@PathVariable Long id) {
        return orderService.getOrder(id);
    }


    @PostMapping
    // ToDo: Request body validation
    public ResponseEntity<OrderDto> createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }


    @PutMapping("/{id}")
    // ToDo: Request body validation
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        return orderService.updateOrder(id, order);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteOrder(@PathVariable Long id) {
        return orderService.deleteOrder(id);
    }

}

package com.testtaskalex.market.controller;

import com.testtaskalex.market.dtos.OrderDto;
import com.testtaskalex.market.dtos.OrderResource;
import com.testtaskalex.market.dtos.PaymentDto;
import com.testtaskalex.market.dtos.PaymentResource;
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
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrders() {
        return orderService.getAllOrders();
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> returnOrder(@PathVariable Long id) {
        return orderService.getOrder(id);
    }


    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderResource orderBody) {
        return orderService.createOrder(orderBody);
    }


    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id, @RequestBody OrderResource orderBody) {
        return orderService.updateOrder(id, orderBody);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteOrder(@PathVariable Long id) {
        return orderService.deleteOrder(id);
    }

    @GetMapping("/{orderId}/item/{itemId}")
    public ResponseEntity<OrderDto> addItemToOrder(@PathVariable Long orderId,
                                                   @PathVariable Long itemId) {
        return orderService.addItemToOrder(orderId, itemId);
    }

    @PostMapping("/{orderId}/payment")
    public ResponseEntity<OrderDto> payForTheOrder(@PathVariable Long orderId,
                                                   @RequestBody PaymentResource paymentBody) {
        return orderService.payForTheOrder(orderId, paymentBody);
    }

    @GetMapping("/{orderId}/payment")
    public ResponseEntity<List<PaymentDto>> getOrderItems(@PathVariable Long orderId) {
        return orderService.getOrderPayments(orderId);
    }
}

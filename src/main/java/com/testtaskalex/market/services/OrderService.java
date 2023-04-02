package com.testtaskalex.market.services;

import com.testtaskalex.market.dtos.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {
    ResponseEntity<List<OrderDto>> getAllOrders();

    ResponseEntity<OrderDto> getOrder(Long id);

    ResponseEntity<OrderDto> createOrder(OrderResource orderResource);

    ResponseEntity<OrderDto> updateOrder(Long id, OrderResource orderResource);

    ResponseEntity<HttpStatus> deleteOrder(Long id);

    ResponseEntity<OrderDto> addItemToOrder(Long orderId, Long itemId);

    ResponseEntity<OrderDto> payForTheOrder(Long orderId, PaymentResourceSum paymentResourceSum);

    ResponseEntity<List<PaymentDto>> getOrderPayments(Long orderId);

    ResponseEntity<List<ItemDto>> getOrderItems(Long orderId);

    ResponseEntity<OrderDto> shipOrder(Long orderId);

    ResponseEntity<OrderDto> deliveredOrder(Long orderId);

}

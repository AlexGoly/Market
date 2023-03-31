package com.testtaskalex.market.services;

import com.testtaskalex.market.dtos.OrderDto;
import com.testtaskalex.market.dtos.OrderResource;
import com.testtaskalex.market.dtos.PaymentDto;
import com.testtaskalex.market.dtos.PaymentResource;
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

    ResponseEntity<OrderDto> payForTheOrder(Long orderId, PaymentResource paymentResource);

    ResponseEntity<List<PaymentDto>> getOrderPayments(Long orderId);
}

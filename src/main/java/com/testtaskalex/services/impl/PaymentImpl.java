package com.testtaskalex.services.impl;

import com.testtaskalex.constants.Constants;
import com.testtaskalex.dtos.PaymentDto;
import com.testtaskalex.dtos.resources.PaymentResource;
import com.testtaskalex.exceptions.ResourceNotFoundException;
import com.testtaskalex.mappers.PaymentMapper;
import com.testtaskalex.persistance.entities.Order;
import com.testtaskalex.persistance.entities.Payment;
import com.testtaskalex.persistance.repositories.OrderRepository;
import com.testtaskalex.persistance.repositories.PaymentRepository;
import com.testtaskalex.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PaymentMapper paymentMapper;

    @Override
    public ResponseEntity<List<PaymentDto>> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();

        if (payments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<PaymentDto> allPayments = payments.stream().map((Payment payment) ->
                paymentMapper.toDto(payment)).collect(Collectors.toList());

        return new ResponseEntity<>(allPayments, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PaymentDto> getPayment(Long id) {
        Payment payment = paymentRepository
                .findById(id).orElseThrow(() ->
                        new ResourceNotFoundException(
                                String.format(Constants.NOT_FOUND_PAYMENT_BY_ID, id)));
        PaymentDto paymentDto = paymentMapper.toDto(payment);
        return new ResponseEntity<>(paymentDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PaymentDto> createPayment(PaymentResource paymentResource) {
        Payment newPayment = new Payment();
        newPayment.setSum(paymentResource.getSum());
        newPayment.setPayment_date(Timestamp.valueOf(LocalDateTime.now()));
        Long orderId = paymentResource.getOrderId();
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                String.format(Constants.NOT_FOUND_ORDER_BY_ID, orderId)));
        newPayment.setOrder(order);
        paymentRepository.save(newPayment);
        PaymentDto paymentDto = paymentMapper.toDto(newPayment);

        return new ResponseEntity<>(paymentDto, HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<PaymentDto> updatePayment(Long id, PaymentResource paymentBody) {
        Payment updatedPayment = paymentRepository
                .findById(id).orElseThrow(() ->
                        new ResourceNotFoundException(
                                String.format(Constants.NOT_FOUND_PAYMENT_BY_ID, id)));
        updatedPayment.setSum(paymentBody.getSum());
        updatedPayment.setPayment_date(paymentBody.getPayment_date());

        Payment savedPayment = paymentRepository.save(updatedPayment);
        return new ResponseEntity<>(paymentMapper.toDto(savedPayment), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> deletePayment(Long id) {
        paymentRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

package com.testtaskalex.market.services.impl;

import com.testtaskalex.market.dtos.PaymentDto;
import com.testtaskalex.market.exceptions.ResourceNotFoundException;
import com.testtaskalex.market.mappers.PaymentMapper;
import com.testtaskalex.market.persistance.entities.Payment;
import com.testtaskalex.market.persistance.repositories.PaymentRepository;
import com.testtaskalex.market.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentImpl implements PaymentService {
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    PaymentMapper paymentMapper;

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
                        new ResourceNotFoundException("Not found Payment with id = " + id));
        PaymentDto paymentDto = paymentMapper.toDto(payment);
        return new ResponseEntity<>(paymentDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PaymentDto> createPayment(Payment payment) {
        Payment newPayment = paymentRepository.save(payment);
        PaymentDto paymentDto = paymentMapper.toDto(newPayment);
        return new ResponseEntity<>(paymentDto, HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<PaymentDto> updatePayment(Long id, Payment payment) {
        Payment updatedPayment = paymentRepository
                .findById(id).orElseThrow(() -> new ResourceNotFoundException(("Not found Payment with id = " + id)));
        updatedPayment.setSum(payment.getSum());
        updatedPayment.setPayment_date(payment.getPayment_date());
        Payment savedPayment = paymentRepository.save(updatedPayment);
        return new ResponseEntity<>(paymentMapper.toDto(savedPayment), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> deletePayment(Long id) {
        paymentRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

package com.testtaskalex.services;

import com.testtaskalex.dtos.PaymentDto;
import com.testtaskalex.dtos.resources.PaymentResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PaymentService {
    ResponseEntity<List<PaymentDto>> getAllPayments();

    ResponseEntity<PaymentDto> getPayment(Long id);

    ResponseEntity<PaymentDto> createPayment(PaymentResource paymentResource);

    ResponseEntity<PaymentDto> updatePayment(Long id, PaymentResource paymentResource);

    ResponseEntity<HttpStatus> deletePayment(Long id);
}

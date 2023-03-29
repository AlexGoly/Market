package com.testtaskalex.market.controller;

import com.testtaskalex.market.dtos.PaymentDto;
import com.testtaskalex.market.persistance.entities.Payment;
import com.testtaskalex.market.persistance.repositories.PaymentRepository;
import com.testtaskalex.market.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/payments")
public class PaymentController {
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<PaymentDto>> getPayments() {
        return paymentService.getAllPayments();
    }


    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> returnPayment(@PathVariable Long id) {
        return paymentService.getPayment(id);
    }


    @PostMapping
    // ToDo: Request body validation
    public ResponseEntity<PaymentDto> createPayment(@RequestBody Payment payment) {
        return paymentService.createPayment(payment);
    }


    @PutMapping("/{id}")
    // ToDo: Request body validation
    public ResponseEntity<PaymentDto> updatePayment(@PathVariable Long id, @RequestBody Payment payment) {
        return paymentService.updatePayment(id, payment);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePayment(@PathVariable Long id) {
        return paymentService.deletePayment(id);
    }
}

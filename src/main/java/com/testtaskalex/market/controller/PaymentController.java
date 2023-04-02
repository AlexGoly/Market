package com.testtaskalex.market.controller;

import com.testtaskalex.market.dtos.PaymentDto;
import com.testtaskalex.market.dtos.PaymentResource;
import com.testtaskalex.market.services.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<PaymentDto>> getPayments() {
        return paymentService.getAllPayments();
    }


    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> returnPayment(@PathVariable Long id) {
        return paymentService.getPayment(id);
    }


    @PostMapping
    public ResponseEntity<PaymentDto> createPayment(@Valid @RequestBody PaymentResource paymentResource) {
        return paymentService.createPayment(paymentResource);
    }


    @PutMapping("/{id}")
    public ResponseEntity<PaymentDto> updatePayment(@PathVariable Long id,
                                                    @Valid @RequestBody PaymentResource paymentBody) {
        return paymentService.updatePayment(id, paymentBody);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePayment(@PathVariable Long id) {
        return paymentService.deletePayment(id);
    }


}

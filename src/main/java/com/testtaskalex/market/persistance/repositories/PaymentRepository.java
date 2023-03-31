package com.testtaskalex.market.persistance.repositories;

import com.testtaskalex.market.persistance.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> getAllPaymentsByOrderId(Long orderId);
}

package com.testtaskalex.market.persistance.repositories;

import com.testtaskalex.market.persistance.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
}

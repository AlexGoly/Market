package com.testtaskalex.persistance.repositories;

import com.testtaskalex.persistance.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

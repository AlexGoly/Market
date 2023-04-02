package com.testtaskalex.market.persistance.entities;

import com.testtaskalex.market.services.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus status;
    private Integer total_items;
    private Double total_payments;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Payment> payments = new HashSet<>();

    @ManyToMany(mappedBy = "orders")
    private Set<Item> items = new HashSet<>();


}

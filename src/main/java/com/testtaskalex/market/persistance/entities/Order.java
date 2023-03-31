package com.testtaskalex.market.persistance.entities;

import com.testtaskalex.market.services.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ORDER_")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "current_status",
            columnDefinition = "enum('CREATED', 'PROCESSING', 'SHIPPING', 'DELIVERED')")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private Integer total_items;
    private Double total_payments;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Payment> payments = new HashSet<>();

}

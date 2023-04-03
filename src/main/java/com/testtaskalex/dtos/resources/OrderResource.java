package com.testtaskalex.dtos.resources;

import com.testtaskalex.enums.OrderStatus;
import lombok.Data;

@Data
public class OrderResource {
    private OrderStatus status;
    private Integer total_items;
    private Double total_payments;
}

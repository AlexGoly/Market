package com.testtaskalex.market.dtos;

import com.testtaskalex.market.services.OrderStatus;
import lombok.Data;

@Data
public class OrderResource {
    private OrderStatus status;
    private Integer total_items;
    private Double total_payments;
}

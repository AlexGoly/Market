package com.testtaskalex.dtos;

import com.testtaskalex.enums.OrderStatus;
import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private OrderStatus status;
    private Integer total_items;
    private Double total_payments;
}

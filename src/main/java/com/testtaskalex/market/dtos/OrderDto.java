package com.testtaskalex.market.dtos;

import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private String status;
    private Integer total_items;
    private Double total_payments;
}

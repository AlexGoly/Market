package com.testtaskalex.dtos;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PaymentDto {
    private Long id;
    private Double sum;
    private Timestamp payment_date;
}

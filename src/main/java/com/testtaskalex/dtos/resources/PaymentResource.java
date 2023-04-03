package com.testtaskalex.dtos.resources;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PaymentResource {
    private Long orderId;
    private Double sum;
    private Timestamp payment_date;
}

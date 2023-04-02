package com.testtaskalex.market.exceptions;

import java.io.Serial;

public class OrderStatusException extends ApplicationException {

    @Serial
    private static final long serialVersionUID = 1L;

    public OrderStatusException(String msg) {
        super(msg);
    }
}

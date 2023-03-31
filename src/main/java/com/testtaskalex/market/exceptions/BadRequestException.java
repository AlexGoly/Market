package com.testtaskalex.market.exceptions;

import java.io.Serial;

public class BadRequestException extends ApplicationException {
    @Serial
    private static final long serialVersionUID = 1L;

    public BadRequestException(String msg) {
        super(msg);
    }
}

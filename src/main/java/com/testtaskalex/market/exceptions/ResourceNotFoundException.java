package com.testtaskalex.market.exceptions;

public class ResourceNotFoundException extends ApplicationException {
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}

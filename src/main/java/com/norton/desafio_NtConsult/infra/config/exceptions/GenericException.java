package com.norton.desafio_NtConsult.infra.config.exceptions;

public class GenericException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public GenericException() {
        super();
    }
    public GenericException(String message) {
        super(message);
    }

    public GenericException(String message, Throwable cause) {
        super(message, cause);
    }
    
}

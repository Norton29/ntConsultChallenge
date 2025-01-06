package com.norton.desafio_NtConsult.infra.config.exceptions;

public class ForbiddenException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public ForbiddenException() {
        super();
    }
    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }
    
}

package com.estudos.br.parkapi.exception;

public class CodigoUniqueViolationException extends RuntimeException{

    public CodigoUniqueViolationException(String message) {
        super(message);
    }

}

package com.estudos.br.parkapi.exceptions;

public class CodigoUniqueViolationException extends RuntimeException{

    public CodigoUniqueViolationException(String message) {
        super(message);
    }

}

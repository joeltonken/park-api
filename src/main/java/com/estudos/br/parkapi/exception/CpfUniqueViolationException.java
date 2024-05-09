package com.estudos.br.parkapi.exception;

public class CpfUniqueViolationException extends RuntimeException{

    public CpfUniqueViolationException(String message) {
        super(message);
    }

}

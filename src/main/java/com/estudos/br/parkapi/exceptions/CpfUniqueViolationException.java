package com.estudos.br.parkapi.exceptions;

public class CpfUniqueViolationException extends RuntimeException{

    public CpfUniqueViolationException(String message) {
        super(message);
    }

}

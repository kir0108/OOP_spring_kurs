package com.korshunov.spring.exception;

public class OperationNotFoundException extends RuntimeException{
    public OperationNotFoundException(String message){
        super(message);
    }
}
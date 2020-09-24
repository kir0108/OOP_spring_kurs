package com.korshunov.spring.exception;

public class BalanceNotFoundException extends RuntimeException{
    public BalanceNotFoundException(String message){
        super(message);
    }
}
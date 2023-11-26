package com.mycompany.propertymanagement.exception;

public class BusinesssClassException extends Exception {
    private String message;
    public BusinesssClassException(){}
    public BusinesssClassException(String message){
        this.message=message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

package com.example.drool.PolicyMakingDroolExample.exceptiom;

public class WrongPasswordException extends RuntimeException {

    public WrongPasswordException(String message) {
        super(message);
    }
}


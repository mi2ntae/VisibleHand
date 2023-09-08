package com.it.vh.common.exception;

public class AuthenticationAccessForbiddenException extends RuntimeException{
    public AuthenticationAccessForbiddenException(){
        super();
    }
    public AuthenticationAccessForbiddenException(String message){
        super(message);
    }
    public AuthenticationAccessForbiddenException(String message, Throwable th){
        super(message, th);
    }
}
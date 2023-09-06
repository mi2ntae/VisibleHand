package com.it.vh.user.exception;

public class NonExistUserIdException extends RuntimeException{
    public NonExistUserIdException(){
        super();
    }
    public NonExistUserIdException(String message){
        super(message);
    }
    public NonExistUserIdException(String message, Throwable th){
        super(message, th);
    }
}
package com.it.vh.feed.exception;

public class NonExistFeedIdException extends RuntimeException{
    public NonExistFeedIdException(){
        super();
    }
    public NonExistFeedIdException(String message){
        super(message);
    }
    public NonExistFeedIdException(String message, Throwable th){
        super(message, th);
    }
}
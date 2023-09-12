package com.it.vh.quiz.domain.exception;

public class NonExistNewsQuizException extends RuntimeException{
    public NonExistNewsQuizException(){
        super();
    }
    public NonExistNewsQuizException(String message){
        super(message);
    }
    public NonExistNewsQuizException(String message, Throwable th){
        super(message, th);
    }
}

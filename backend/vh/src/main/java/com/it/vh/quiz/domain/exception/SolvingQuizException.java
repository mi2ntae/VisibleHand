package com.it.vh.quiz.domain.exception;

public class SolvingQuizException extends RuntimeException{
    public SolvingQuizException(){
        super();
    }
    public SolvingQuizException(String message){
        super(message);
    }
    public SolvingQuizException(String message, Throwable th){
        super(message, th);
    }
}

package com.it.vh.article.exception;

public class NonExistArticleIdException extends RuntimeException {
    public NonExistArticleIdException() {
        super();
    }
    public NonExistArticleIdException(String message){
        super(message);
    }
    public NonExistArticleIdException(String message, Throwable th){
        super(message, th);
    }
}

package com.it.vh.article.domain.exception;

public class NonExistScrapIdException extends RuntimeException{
    public NonExistScrapIdException(){
        super();
    }
    public NonExistScrapIdException(String message){
        super(message);
    }
    public NonExistScrapIdException(String message, Throwable th){
        super(message, th);
    }
}
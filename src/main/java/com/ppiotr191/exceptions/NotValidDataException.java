package com.ppiotr191.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value= HttpStatus.BAD_REQUEST) //404
public class NotValidDataException extends RuntimeException {
    private static final long serialVersionUID = -3332292346834265372L;

    public NotValidDataException(String msg){
        super( msg );
    }
}
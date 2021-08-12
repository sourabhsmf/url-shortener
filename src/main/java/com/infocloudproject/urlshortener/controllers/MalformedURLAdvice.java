package com.infocloudproject.urlshortener.controllers;

import com.infocloudproject.urlshortener.exceptions.MalformedURLException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MalformedURLAdvice extends ResponseEntityExceptionHandler{
    @ResponseBody
    @ExceptionHandler(MalformedURLException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String urlMalformedHandler(MalformedURLException ex) {
        return ex.getMessage();
    }
}

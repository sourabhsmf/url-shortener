package com.infocloudproject.urlshortener.controllers;

import com.infocloudproject.urlshortener.exceptions.URLNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class URLNotFoundAdvice {
    
  @ResponseBody
  @ExceptionHandler(URLNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String urlNotFoundHandler(URLNotFoundException ex) {
    return ex.getMessage();
  }
}

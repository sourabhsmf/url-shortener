package com.infocloudproject.urlshortener.exceptions;


public class MalformedURLException extends RuntimeException{
    public MalformedURLException(String url) {
        super("Url is not in valid format " + url);
      }
  
}

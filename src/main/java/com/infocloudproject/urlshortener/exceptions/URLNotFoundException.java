package com.infocloudproject.urlshortener.exceptions;

public class URLNotFoundException extends RuntimeException {

    public URLNotFoundException(String shortenedURL) {
      super("Could not find shortened url by " + shortenedURL);
    }

}

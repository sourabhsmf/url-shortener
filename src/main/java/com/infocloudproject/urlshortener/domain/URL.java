package com.infocloudproject.urlshortener.domain;

public class URL {
    
    private String expandedURL;

    private String shortenedURL;

    // private Boolean wroteToFile;

    public String getExpandedURL() {
        return expandedURL;
    }

    public String getShortenedURL() {
        return shortenedURL;
    }

    public void setShortenedURL(String shortenedURL) {
        this.shortenedURL = shortenedURL;
    }

    public void setExpandedURL(String expandedURL) {
        this.expandedURL = expandedURL;
    }
    
}

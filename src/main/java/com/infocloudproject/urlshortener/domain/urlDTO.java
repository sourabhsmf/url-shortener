package com.infocloudproject.urlshortener.domain;

public class urlDTO {

    private String expandedURL;

    private String shortenedURL;

    public urlDTO(String expandedURL, String shortenedURL){
        this.expandedURL = expandedURL;
        this.shortenedURL = shortenedURL;
    }
    public urlDTO(){
    }
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

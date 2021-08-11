package com.infocloudproject.urlshortener.services;

import java.util.Optional;
import java.util.Set;

import com.infocloudproject.urlshortener.domain.URL;

public interface UrlService{
    Set<URL> findAll();
    Optional<URL> findByShortenedURL(String shortenedURL);
    Optional<URL> find(URL url);
    URL save(URL object);
    void delete(URL object);
    void deleteByShortenedURL(String shortenedURL);
}

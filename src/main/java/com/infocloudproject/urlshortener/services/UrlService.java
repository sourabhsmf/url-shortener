package com.infocloudproject.urlshortener.services;

import java.util.Optional;
import java.util.Set;

import com.infocloudproject.urlshortener.domain.urlDTO;

public interface UrlService{
    Set<urlDTO> findAll();
    Optional<urlDTO> findByShortenedURL(String shortenedURL);
    Optional<urlDTO> find(urlDTO url);
    urlDTO save(urlDTO object);
    void delete(urlDTO object);
    void deleteByShortenedURL(String shortenedURL);
}

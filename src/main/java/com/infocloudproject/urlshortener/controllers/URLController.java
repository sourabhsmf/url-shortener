package com.infocloudproject.urlshortener.controllers;

import java.util.Optional;
import java.util.Set;


import com.infocloudproject.urlshortener.domain.urlDTO;
import com.infocloudproject.urlshortener.exceptions.MalformedURLException;
import com.infocloudproject.urlshortener.exceptions.URLNotFoundException;
import com.infocloudproject.urlshortener.services.UrlService;
import com.infocloudproject.urlshortener.validators.UrlValidation;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "CRUD Operations for Url Shortener")
@RequestMapping(path = {"/api"}, produces = MediaType.APPLICATION_JSON_VALUE)
public class URLController {
    
    private final UrlService urlService;

    private final UrlValidation urlValidation;

    public URLController(UrlService urlService, UrlValidation urlValidation) {
        this.urlService = urlService;
        this.urlValidation = urlValidation;
    }

    //CRUD operations - Read
    @GetMapping({""})
    public Set<urlDTO> index(){
        return urlService.findAll();
    }
    @GetMapping({"/{shortenedURL}"})
    public urlDTO findUrl(@RequestParam String shortenedURL){
        urlValidation.isValidUrl(shortenedURL).orElseThrow(() -> new MalformedURLException(shortenedURL));
        return urlService.findByShortenedURL(shortenedURL)
                            .orElseThrow(() -> new URLNotFoundException(shortenedURL));
    }

    //CRUD operation - Create
    @PostMapping(path = {"/create"}, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public urlDTO createUrl(@RequestParam String url){
        urlValidation.isValidUrl(url).orElseThrow(() -> new MalformedURLException(url));
        urlDTO urlToSave = new urlDTO();
        urlToSave.setExpandedURL(url);
        return urlService.save(urlToSave);
    }

    //CRUD operation - Delete
    @DeleteMapping({"/{shortenedURL}/delete"})
    public urlDTO deleteUrl(@RequestParam String shortenedURL) {
        urlValidation.isValidUrl(shortenedURL).orElseThrow(() -> new MalformedURLException(shortenedURL));
        
        urlDTO urlToDelete = urlService.findByShortenedURL(shortenedURL)
                                    .orElseThrow(() -> new URLNotFoundException(shortenedURL));
        if(urlToDelete != null){
            urlService.delete(urlToDelete);
        }
        return urlToDelete;
    }
}

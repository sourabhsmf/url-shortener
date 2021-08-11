package com.infocloudproject.urlshortener.controllers;

import java.util.Set;

import com.infocloudproject.urlshortener.domain.URL;
import com.infocloudproject.urlshortener.exceptions.URLNotFoundException;
import com.infocloudproject.urlshortener.services.UrlService;

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

    public URLController(UrlService urlService) {
        this.urlService = urlService;
    }

    //CRUD operations - Read
    @GetMapping({""})
    public Set<URL> index(){
        return urlService.findAll();
    }
    @GetMapping({"/{shortenedURL}"})
    public URL findUrl(@PathVariable String shortenedURL){
        return urlService.findByShortenedURL(shortenedURL)
                            .orElseThrow(() -> new URLNotFoundException(shortenedURL));
    }

    //CRUD operation - Create
    @PostMapping(path = {"/create"}, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public URL createUrl(@RequestParam String url){
        URL urlToSave = new URL();
        urlToSave.setExpandedURL(url);
        return urlService.save(urlToSave);
    }

    //CRUD operation - Delete
    @DeleteMapping({"/{shortenedURL}/delete"})
    public URL deleteUrl(@PathVariable String shortenedURL) {
        URL urlToDelete = urlService.findByShortenedURL(shortenedURL)
                                    .orElseThrow(() -> new URLNotFoundException(shortenedURL));
        if(urlToDelete != null){
            urlService.delete(urlToDelete);
        }
        return urlToDelete;
    }
}

package com.infocloudproject.urlshortener.tasks;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.infocloudproject.urlshortener.services.UrlMapService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartUpFillUrls implements CommandLineRunner{

    private UrlMapService urlMapService;

    public StartUpFillUrls(UrlMapService urlMapService) {
        this.urlMapService = urlMapService;
    }

    @Override
    public void run(String... args) throws Exception {
        List<String> urlDtoEntries = Files.readAllLines(Paths.get("data/data.csv"), StandardCharsets.UTF_16);
        if(!urlDtoEntries.isEmpty() && urlDtoEntries.get(0).length() > 1){
            urlMapService.addAll(urlDtoEntries);
        }
    }
    
}

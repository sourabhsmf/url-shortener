package com.infocloudproject.urlshortener.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.infocloudproject.urlshortener.domain.URL;
import com.infocloudproject.urlshortener.utils.URLConversion;

import org.springframework.stereotype.Service;

@Service
public class UrlMapService implements UrlService{

    protected Map<Long, URL> map = new HashMap<>();

    private final URLConversion urlConversion;

    public UrlMapService(URLConversion urlConversion) {
        this.urlConversion = urlConversion;
    }

    @Override
    public Set<URL> findAll() {
        return new HashSet<>(map.values()); 
    }

    @Override
    public Optional<URL> findByShortenedURL(String shortenedURL) {
        return Optional.ofNullable(map.get(urlConversion.decode(shortenedURL)));
    }

    @Override
    public Optional<URL> find(URL url) {
        URL urlFound = null;
        for(URL entry : map.values()){
            if(entry.getExpandedURL().equals(url.getExpandedURL())) urlFound = url;
        }
        return Optional.ofNullable(urlFound);
    }

    @Override
    public URL save(URL object) {
        Optional<URL> urlToSave = find(object);
        if(object != null && urlToSave.isEmpty()){
            Long id = getNextId();
            object.setShortenedURL(urlConversion.encode(id));
            map.put(id, object);
            
        }else if(urlToSave.isPresent()){
            return urlToSave.get();
        }else{
            throw new RuntimeException("Objects cannot be null");
        }
        return object;
    }

    @Override
    public void delete(URL object) {
        map.entrySet().removeIf(entry -> entry.getValue().equals(object));    
    }

    @Override
    public void deleteByShortenedURL(String shortenedURL) {
        map.remove(urlConversion.decode(shortenedURL));
    }

    public Long getNextId(){
        return map.size() > 0 ? map.size() + 1L : 1L;
    }

}

package com.infocloudproject.urlshortener.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.infocloudproject.urlshortener.domain.urlDTO;
import com.infocloudproject.urlshortener.utils.URLConversion;

import org.springframework.stereotype.Service;

@Service
public class UrlMapService implements UrlService{

    /**
     *
     */
    private static final String HTTPS_SHORTI_FY = "https://shorti.fy/";

    protected Map<Long, urlDTO> map = new HashMap<>();

    private final URLConversion urlConversion;

    public UrlMapService(URLConversion urlConversion) {
        this.urlConversion = urlConversion;
    }

    @Override
    public Set<urlDTO> findAll() {
        return new HashSet<>(map.values()); 
    }

    @Override
    public Optional<urlDTO> findByShortenedURL(String shortenedURL) {
        String shortenedURLEncoded = urlConversion.extractEncodedIndex(shortenedURL);
        return Optional.ofNullable(map.get(urlConversion.decode(shortenedURLEncoded)));
    }

    @Override
    public Optional<urlDTO> find(urlDTO url) {
        urlDTO urlFound = null;
        for(urlDTO entry : map.values()){
            if(entry.getExpandedURL().equals(url.getExpandedURL())) urlFound = url;
        }
        return Optional.ofNullable(urlFound);
    }

    @Override
    public urlDTO save(urlDTO object) {
        Optional<urlDTO> urlToSave = find(object);
        if(object != null && urlToSave.isEmpty()){
            Long id = getNextId();
            object.setShortenedURL(HTTPS_SHORTI_FY + urlConversion.encode(id));
            map.put(id, object);
            
        }else if(urlToSave.isPresent()){
            return urlToSave.get();
        }else{
            throw new RuntimeException("Objects cannot be null");
        }
        return object;
    }

    @Override
    public void delete(urlDTO object) {
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

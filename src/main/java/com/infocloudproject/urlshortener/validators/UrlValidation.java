package com.infocloudproject.urlshortener.validators;

import java.util.Optional;

import com.infocloudproject.urlshortener.domain.urlDTO;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

@Component
public class UrlValidation {
    public Optional<urlDTO> isValidUrl(String resourceLocation){
        urlDTO urlDTO = null;
        if(ResourceUtils.isUrl(resourceLocation)) urlDTO = new urlDTO();

        return Optional.ofNullable(urlDTO);
    }
}

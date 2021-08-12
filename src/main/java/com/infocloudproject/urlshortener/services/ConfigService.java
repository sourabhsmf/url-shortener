package com.infocloudproject.urlshortener.services;

import org.springframework.stereotype.Service;

@Service
public class ConfigService {
    public boolean getFileWriteConfig(){
        String configValue = System.getenv("WRITE_TO_FILE");
        if(configValue != null) return configValue.equals("true") ? true : false;
        return false;
    }
}

package com.infocloudproject.urlshortener.utils;

import org.springframework.stereotype.Component;

@Component
public class URLConversion {
    
    private static final String ALLOWED_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    
    private static final int ALLOWED_CHAR_LENGTH = ALLOWED_CHARACTERS.length();
    
    public String encode(Long id){
        StringBuilder convertedId = new StringBuilder();
        while(id > 0){
            Long remainder = id % ALLOWED_CHAR_LENGTH;
            id = id / ALLOWED_CHAR_LENGTH;
            convertedId.insert(0, ALLOWED_CHARACTERS.charAt(remainder.intValue()));
        }
        return convertedId.toString();
    }
    
    public Long decode(String convertedId){
        Long id = 0L;
        int counter = 1;
        int length = convertedId.length();
        for(char letter : convertedId.toCharArray()){
            id += ALLOWED_CHARACTERS.indexOf(letter) * (long)(Math.pow(ALLOWED_CHAR_LENGTH, length - counter));
            counter++;
        }
        return id;
    }

    public String extractEncodedIndex(String shortenedURL) {
        String[] allSlashSeperatedValue = shortenedURL.split("/");
        return allSlashSeperatedValue[allSlashSeperatedValue.length - 1];
    }
}

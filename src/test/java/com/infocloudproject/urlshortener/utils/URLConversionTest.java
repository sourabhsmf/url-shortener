package com.infocloudproject.urlshortener.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class URLConversionTest {

    URLConversion urlConversion = new URLConversion();

    @Test
    void encode(){
        //given 
        Long longToEncode = 5L;

        //when 
        String encodedActual = urlConversion.encode(longToEncode);
        
        //then
        assertEquals("f", encodedActual);
    }
    @Test
    void decode(){
        //given 
        String stringToDecode = "f";

        //when 
       Long deodedActual = urlConversion.decode(stringToDecode);
        
        //then
        assertEquals(5L, deodedActual);
    }
}

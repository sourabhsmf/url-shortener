package com.infocloudproject.urlshortener.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.infocloudproject.urlshortener.domain.urlDTO;
import com.infocloudproject.urlshortener.utils.URLConversion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UrlMapServiceTest {

    private static final String HTTP_SOMEEXPANDEDURL_TWO_SOMEPATH = "http://someexpandedurl.two/somepath";

    private static final String HTTP_SOMEEXPANDEDURL_ONE_SOMEPATH = "http://someexpandedurl.one/somepath";

    private static final String SOME_SHORTENED_URL = "http://shorti.fy/bc";

    @Mock
    URLConversion urlConversion;

    @InjectMocks
    UrlMapService urlMapService;

    urlDTO url1 = new urlDTO(); 
    urlDTO url2 = new urlDTO();

    @BeforeEach
    public void setUp(){
        url1.setExpandedURL(HTTP_SOMEEXPANDEDURL_ONE_SOMEPATH);
        url2.setExpandedURL(HTTP_SOMEEXPANDEDURL_TWO_SOMEPATH);
    }
    @Test
    void findAll() {
        //Given
        Set<urlDTO> urlSetExpected = new HashSet<>();
        urlSetExpected.add(url1);
        urlSetExpected.add(url2);
    
        urlMapService.map.put(1L, url1);
        urlMapService.map.put(2L, url2);
    
        //When
        Set<urlDTO> urlSetActual = urlMapService.findAll();

        //Then
        assertNotNull(urlSetActual);
        assertEquals(2, urlSetActual.size());
        assertEquals(urlSetExpected, urlSetActual);
        
    }

    @Test
    void findByShortenedURL() {
        //Given
        urlMapService.map.put(1L, url1);
        urlDTO urlExpected = url1;
        when(urlConversion.decode(anyString())).thenReturn(1L);
        when(urlConversion.extractEncodedIndex(anyString())).thenReturn("b");
        
        //When
        Optional<urlDTO> urlActual = urlMapService.findByShortenedURL(SOME_SHORTENED_URL);

        //Then
        assertTrue(urlActual.isPresent());
        assertEquals(urlExpected, urlActual.get());
    }
    @Test
    void findDoesNotExsist() {
        //Given - map does not contain any entry
        urlDTO urlToFindExpected = url1;

        //when - search for a url
        Optional<urlDTO> urlFoundActual = urlMapService.find(urlToFindExpected);

        //then returned value should be null
        assertTrue(urlFoundActual.isEmpty());
    }
    @Test
    void save() {
        //Given
        url1.setShortenedURL(SOME_SHORTENED_URL);
        urlDTO urlToSave = url1;
        when(urlConversion.encode(anyLong())).thenReturn(SOME_SHORTENED_URL);

        //When
        urlDTO savedUrl = urlMapService.save(urlToSave);
        
        //Then
        assertEquals(urlToSave, savedUrl);
        verify(urlConversion).encode(anyLong());
    }

    @Test
    void delete() {
        //Given
        urlMapService.map.put(1L, url1);

        //When
        urlMapService.delete(url1);

        //then
        assertTrue(urlMapService.map.isEmpty());
    }
    @Test
    void deleteByShortenedURL() {
        //Given
        urlMapService.map.put(1L, url1);
        when(urlConversion.decode(anyString())).thenReturn(1L);

        //When
        urlMapService.deleteByShortenedURL(SOME_SHORTENED_URL);;

        //then
        assertTrue(urlMapService.map.isEmpty());
        verify(urlConversion).decode(anyString());
        
    }

}

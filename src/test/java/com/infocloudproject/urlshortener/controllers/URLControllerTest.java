package com.infocloudproject.urlshortener.controllers;

import com.infocloudproject.urlshortener.domain.urlDTO;
import com.infocloudproject.urlshortener.services.UrlService;
import com.infocloudproject.urlshortener.validators.UrlValidation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class URLControllerTest {
    
    private static final String HTTP_SOMEEXPANDEDURL_TWO_SOMEPATH = "http://someexpandedurl.two/somepath";

    private static final String HTTP_SOMEEXPANDEDURL_ONE_SOMEPATH = "http://someexpandedurl.one/somepath";

    private static final String HTTP_SOMEURL_SOMEPATH = "http://someurl/somepath";

    private static final String SOME_SHORTENED_URL = "http://shorti.fy/bc";

    @Mock
    UrlService urlService;

    @Mock
    UrlValidation urlValidation;

    @InjectMocks
    URLController urlController;

    MockMvc mockMvc;

    urlDTO url1 = new urlDTO(); 
    urlDTO url2 = new urlDTO(); 
    
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(urlController).build();
        url1.setExpandedURL(HTTP_SOMEEXPANDEDURL_ONE_SOMEPATH);
        url2.setExpandedURL(HTTP_SOMEEXPANDEDURL_TWO_SOMEPATH);
        url1.setShortenedURL(SOME_SHORTENED_URL);
        url2.setShortenedURL(SOME_SHORTENED_URL);
    }
    
    @Test
    void index() throws Exception{
        //Given
        Set<urlDTO> urlSetToReturn = new HashSet<>();
        urlSetToReturn.add(url1);
        
        when(urlService.findAll()).thenReturn(urlSetToReturn);

        //When
        mockMvc.perform(get("/api/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].expandedURL").value(HTTP_SOMEEXPANDEDURL_ONE_SOMEPATH))
                .andExpect(jsonPath("$[0].shortenedURL").value(SOME_SHORTENED_URL));
                
        verify(urlService, times(1)).findAll();
    }
    @Test
    void findUrl() throws Exception{
        //given
        when(urlValidation.isValidUrl(anyString())).thenReturn(Optional.ofNullable(url1));
        when(urlService.findByShortenedURL(anyString())).thenReturn(Optional.ofNullable(url1));

        //when
        mockMvc.perform(get("/api/find?shortenedURL=" + SOME_SHORTENED_URL))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.expandedURL").value(HTTP_SOMEEXPANDEDURL_ONE_SOMEPATH))
            .andExpect(jsonPath("$.shortenedURL").value(SOME_SHORTENED_URL));

        verify(urlService, times(1)).findByShortenedURL(anyString());
        
    }
    @Test
    void createUrl() throws Exception{
        when(urlValidation.isValidUrl(anyString())).thenReturn(Optional.ofNullable(url1));
        //given
        urlDTO urlToReturn = new urlDTO();
        urlToReturn.setExpandedURL(HTTP_SOMEURL_SOMEPATH);
        urlToReturn.setShortenedURL(SOME_SHORTENED_URL);

        
        //when
        when(urlService.save(any(urlDTO.class))).thenReturn(urlToReturn);

        //then
        mockMvc.perform(post("/api/create")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                            .content("url=" + HTTP_SOMEURL_SOMEPATH)
                        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.expandedURL").value(HTTP_SOMEURL_SOMEPATH))
                .andExpect(jsonPath("$.shortenedURL").value(SOME_SHORTENED_URL));

        verify(urlService, times(1)).save(any(urlDTO.class));
    }
    @Test
    void deleteUrl() throws Exception{
        //given
        when(urlValidation.isValidUrl(anyString())).thenReturn(Optional.ofNullable(url1));
        when(urlService.findByShortenedURL(anyString())).thenReturn(Optional.ofNullable(url1));
        //when
        mockMvc.perform(delete("/api/delete?shortenedURL=" + SOME_SHORTENED_URL))
            .andExpect(status().isOk());

        //then
        verify(urlService, times(1)).delete(any(urlDTO.class));
    }
}

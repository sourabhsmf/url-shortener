package com.infocloudproject.urlshortener.controllers;

import com.infocloudproject.urlshortener.domain.URL;
import com.infocloudproject.urlshortener.services.UrlService;

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
import static org.mockito.Mockito.doNothing;
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
    
    @Mock
    UrlService urlService;

    @InjectMocks
    URLController urlController;

    MockMvc mockMvc;

    URL url1 = new URL(); 
    URL url2 = new URL(); 
    
    
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(urlController).build();
        url1.setExpandedURL("http://someexpandedurl.one/somepath");
        url2.setExpandedURL("http://someexpandedurl.two/somepath");
        url1.setShortenedURL("b");
        url2.setShortenedURL("c");
    }
    
    @Test
    void index() throws Exception{
        //Given
        Set<URL> urlSetToReturn = new HashSet<>();
        urlSetToReturn.add(url1);
        
        when(urlService.findAll()).thenReturn(urlSetToReturn);

        //When
        mockMvc.perform(get("/api/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].expandedURL").value("http://someexpandedurl.one/somepath"))
                .andExpect(jsonPath("$[0].shortenedURL").value("b"));
                
        verify(urlService, times(1)).findAll();
    }
    @Test
    void findUrl() throws Exception{
        //given
        when(urlService.findByShortenedURL(anyString())).thenReturn(Optional.ofNullable(url1));

        //when
        mockMvc.perform(get("/api/b"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.expandedURL").value("http://someexpandedurl.one/somepath"))
            .andExpect(jsonPath("$.shortenedURL").value("b"));

        verify(urlService, times(1)).findByShortenedURL(anyString());
        
    }
    @Test
    void createUrl() throws Exception{
        
        //given
        URL urlToReturn = new URL();
        urlToReturn.setExpandedURL("http://someurl/somepath");
        urlToReturn.setShortenedURL("b");

        
        //when
        when(urlService.save(any(URL.class))).thenReturn(urlToReturn);

        //then
        mockMvc.perform(post("/api/create")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                            .content("url=http://someurl/somepath")
                        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.expandedURL").value("http://someurl/somepath"))
                .andExpect(jsonPath("$.shortenedURL").value("b"));

        verify(urlService, times(1)).save(any(URL.class));
    }
    @Test
    void deleteUrl() throws Exception{
        //given
        when(urlService.findByShortenedURL(anyString())).thenReturn(Optional.ofNullable(url1));
        //when
        mockMvc.perform(delete("/api/b/delete"))
            .andExpect(status().isOk());

        //then
        verify(urlService, times(1)).delete(any(URL.class));
    }
}

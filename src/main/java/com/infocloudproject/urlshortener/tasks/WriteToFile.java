package com.infocloudproject.urlshortener.tasks;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.infocloudproject.urlshortener.domain.urlDTO;


public class WriteToFile implements Runnable{

    urlDTO urlDTO;

    public WriteToFile(urlDTO urlDTO) {
        this.urlDTO = urlDTO;
    }

    @Override
    public void run() {
        
        if(urlDTO != null)
            try {
                writeOperation(urlDTO);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public synchronized void writeOperation(urlDTO urlDTO) throws IOException{
        String toWrite = "\"" + urlDTO.getExpandedURL() + "\"" + "," 
                        + "\"" + urlDTO.getShortenedURL() + "\""
                        + "\n";

        try {
            Files.writeString(Paths.get("data.csv"), toWrite, StandardCharsets.UTF_16, StandardOpenOption.APPEND);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}

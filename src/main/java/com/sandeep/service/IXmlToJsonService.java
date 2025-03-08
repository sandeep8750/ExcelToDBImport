package com.sandeep.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface IXmlToJsonService {
    public String convertAndSaveJson(MultipartFile file) throws IOException;
}

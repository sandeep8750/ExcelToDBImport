package com.sandeep.serviceImpl;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.sandeep.service.IXmlToJsonService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class XmlToJsonServiceImpl implements IXmlToJsonService {

    @Value("${file.json.path}")
    private String filePathToSaveXml;

    @Override
    public String convertAndSaveJson(MultipartFile file) throws IOException {

        XmlMapper xmlMapper = new XmlMapper();
        String jsonNode = xmlMapper.readTree(file.getInputStream()).toPrettyString();

        // Save JSON file
        String jsonFileName = file.getOriginalFilename().replace(".xml", ".json");
        File outputFile = new File(filePathToSaveXml + jsonFileName);


        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(jsonNode);
        }

        return "JSON file saved at: " + outputFile.getAbsolutePath();
    }
}

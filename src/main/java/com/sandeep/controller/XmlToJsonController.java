package com.sandeep.controller;

import com.sandeep.service.IXmlToJsonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

@RestController
@RequestMapping("/api")
public class XmlToJsonController {

    @Autowired
    private IXmlToJsonService xmlToJsonService;

    @Operation(
            summary = "Upload an Xml file",
            description = "Uploads an Xml file and processes its data.",
            responses =
                    {
                            @ApiResponse(responseCode = "200", description = "Xml file saved successfully"),
                            @ApiResponse(responseCode = "400", description = "Invalid file upload"),
                            @ApiResponse(responseCode = "500", description = "Internal server error")
                    }
    )

    @PostMapping(value = "/upload-xml", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadXmlFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload an XML file.");
        }
        if(!file.getOriginalFilename().endsWith(".xml")){
            return ResponseEntity.badRequest().body("Please upload Xml file only");
        }
        try {
            String responseMessage = xmlToJsonService.convertAndSaveJson(file);
            return ResponseEntity.ok(responseMessage);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error processing XML file: " + e.getMessage());
        }
    }
}

package com.sandeep.controller;


import com.sandeep.Entity.EmployeeEntity;
import com.sandeep.service.IExcelImportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ExcelToDBController {

    @Autowired
    private IExcelImportService excelImportService;

    @Operation(
            summary = "Upload an Excel file",
            description = "Uploads an Excel file and processes its data.",
            responses =
                    {
                            @ApiResponse(responseCode = "200", description = "Xml file saved successfully"),
                            @ApiResponse(responseCode = "400", description = "Invalid file upload"),
                            @ApiResponse(responseCode = "500", description = "Internal server error")
                    }
    )

    @PostMapping(value = "/save-excel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> ExcelFileSave(@RequestParam("excelFile") MultipartFile excelFile) {
        if (excelFile.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "File is empty. Please upload a valid Excel file."));
        }
        return ResponseEntity.ok(this.excelImportService.processExcelFile(excelFile));
    }

    @Operation(
            summary = "Read Entire Excel file",
            description = "Read Entire Excel file from DATABASE."
    )
    @GetMapping("/read-excel")
    public ResponseEntity<List<EmployeeEntity>> readExcelFile() {
        List<EmployeeEntity> employeeEntityList = excelImportService.getAllData();
        return ResponseEntity.ok(employeeEntityList);
    }


}

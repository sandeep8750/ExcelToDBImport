package com.sandeep.service;

import com.sandeep.Entity.EmployeeEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
public interface IExcelImportService {

    public List<EmployeeEntity> processExcelFile(MultipartFile file);

    public List<EmployeeEntity> getAllData();
}

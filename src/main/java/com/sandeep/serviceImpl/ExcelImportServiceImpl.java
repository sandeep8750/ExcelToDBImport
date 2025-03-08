package com.sandeep.serviceImpl;

import com.sandeep.Entity.EmployeeEntity;
import com.sandeep.helper.ExcelImportHelper;
import com.sandeep.respository.ExcelSaveRepository;
import com.sandeep.service.IExcelImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Repository
public class ExcelImportServiceImpl implements IExcelImportService {
    @Autowired
    private ExcelSaveRepository excelSaveRepository;

    @Override
    public List<EmployeeEntity> processExcelFile(MultipartFile file) {

        if(ExcelImportHelper.isValidExcelFile(file)){
            try {
                List<EmployeeEntity> employeeEntities = ExcelImportHelper.getEmployeesDataFromExcel(file.getInputStream());
         return  this.excelSaveRepository.saveAll(employeeEntities);
            } catch (IOException e) {
                throw new IllegalArgumentException("The file is not a valid excel file");
            }
        }
        else if (ExcelImportHelper.isValidCsvFile(file)) {
            try{
                List<EmployeeEntity> employeeEntities = ExcelImportHelper.getEmployeesDataFromCsv(file.getInputStream());
                return this.excelSaveRepository.saveAll(employeeEntities);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<EmployeeEntity> getAllData() {
        return excelSaveRepository.findAll();
    }
}

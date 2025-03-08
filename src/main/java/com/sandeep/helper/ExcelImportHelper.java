package com.sandeep.helper;

import com.sandeep.Entity.EmployeeEntity;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ExcelImportHelper {

    public static boolean isValidExcelFile(MultipartFile file) {
        return (Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") ||
                Objects.equals(file.getContentType(), "application/vnd.ms-excel"));
    }

    public static List<EmployeeEntity> getEmployeesDataFromExcel(InputStream inputStream) {
        List<EmployeeEntity> employees = new ArrayList<>();
        try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowIndex = 0;

            for (Row row : sheet) {
                if (rowIndex == 0) { // Skip the header row
                    rowIndex++;
                    continue;
                }

                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;
                EmployeeEntity employeeEntity = new EmployeeEntity();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();


                    Object value = getCellValue(cell); // Get value dynamically
                    System.out.println("Cell Index: " + cellIndex + " | Value: " + value + " | Type: " + cell.getCellType()); // Debugging

                    switch (cellIndex) {
                        case 0 -> employeeEntity.setFirstName(value != null ? value.toString() : null);
                        case 1 -> employeeEntity.setLastName(value != null ? value.toString() : null);
                        case 2 -> employeeEntity.setEmail(value != null ? value.toString() : null);
                        case 3 -> employeeEntity.setPhoneNumber(value != null ? value.toString() : null);
                        case 4 -> employeeEntity.setDepartment(value != null ? value.toString() : null);
                        case 5 -> employeeEntity.setDesignation(value != null ? value.toString() : null);
                        case 6 -> employeeEntity.setSalary(value instanceof Double ? (Double) value : 0.0);
                        case 7 ->
                                employeeEntity.setDateOfBirth(value != null ? LocalDate.parse((String) value, DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null); // Adjust format if needed
                        case 8 ->
                                employeeEntity.setDateOfJoining(value != null ? LocalDate.parse((String) value, DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null);
                        case 9 -> employeeEntity.setAddress(value != null ? value.toString() : null);
                        case 10 -> employeeEntity.setStatus(value != null ? value.toString() : null);
                        default -> {
                        }
                    }

                    cellIndex++; // Move to the next cell
                }

                employees.add(employeeEntity); // Add only after processing all columns
            }
        } catch (IOException e) {
            e.printStackTrace(); // Proper exception handling
        }

        return employees;
    }

    public static Object getCellValue(Cell cell) {
        if (cell == null) return null;

        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> {
                if (DateUtil.isCellDateFormatted(cell)) {
                    yield cell.getLocalDateTimeCellValue().toLocalDate(); // Convert date to LocalDate
                } else {
                    yield cell.getNumericCellValue(); // Convert numeric values properly
                }
            }
            case BOOLEAN -> cell.getBooleanCellValue();
            case FORMULA -> {
                try {
                    yield cell.getNumericCellValue(); // Try numeric formula result
                } catch (Exception e) {
                    yield cell.getStringCellValue(); // If not numeric, return as string
                }
            }
            default -> null;
        };
    }

    public static boolean isValidCsvFile(MultipartFile file) {
        return file != null &&
                !file.isEmpty() &&
                Objects.equals(file.getContentType(), "text/csv");
    }

    public static List<EmployeeEntity> getEmployeesDataFromCsv(InputStream inputStream) {
        List<EmployeeEntity> employees = new ArrayList<>();

        try (Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : csvParser) {

                EmployeeEntity employeeEntity = new EmployeeEntity();

                // Mapping CSV Headers to EmployeeEntity fields
                employeeEntity.setFirstName(record.get("First Name"));
                employeeEntity.setLastName(record.get("Last Name"));
                employeeEntity.setEmail(record.get("Email"));
                employeeEntity.setPhoneNumber(record.get("Phone Number"));
                employeeEntity.setDepartment(record.get("Department"));
                employeeEntity.setDesignation(record.get("Designation"));
                employeeEntity.setSalary(Double.parseDouble(record.get("Salary")));
                employeeEntity.setDateOfBirth(LocalDate.parse(record.get("Date of Birth"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                employeeEntity.setDateOfJoining(LocalDate.parse(record.get("Date of Joining"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                employeeEntity.setAddress(record.get("Address"));
                employeeEntity.setStatus(record.get("Status"));

                employees.add(employeeEntity);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return employees;
    }
}

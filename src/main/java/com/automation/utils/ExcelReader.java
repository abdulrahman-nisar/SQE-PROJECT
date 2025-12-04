package com.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExcelReader {

    private static final Logger logger = LogManager.getLogger(ExcelReader.class);
    private static final String EXCEL_FILE_PATH = ConfigurationFileReader.getProperty("excel.path");

    public static Map<String, String> getUserCredentialsFromExcel(int rowIndex) {
        Map<String, String> credentials = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(EXCEL_FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(rowIndex + 1);

            if (row != null) {
                Cell emailCell = row.getCell(0);
                Cell passwordCell = row.getCell(1);

                String email = getCellValueAsString(emailCell);
                String password = getCellValueAsString(passwordCell);

                credentials.put("email", email);
                credentials.put("password", password);

                logger.info("Retrieved credentials from Excel row {}: {}", rowIndex, email);
            } else {
                logger.warn("Row {} not found in Excel file", rowIndex);
            }

        } catch (IOException e) {
            logger.error("Error reading Excel file: {}", e.getMessage());
            throw new RuntimeException("Failed to read Excel file: " + EXCEL_FILE_PATH, e);
        }

        return credentials;
    }

    public static Map<String, String> getUserCredentialsByEmail(String email) {
        Map<String, String> credentials = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(EXCEL_FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    String currentEmail = getCellValueAsString(row.getCell(0));

                    if (currentEmail != null && currentEmail.equalsIgnoreCase(email)) {
                        String password = getCellValueAsString(row.getCell(1));
                        credentials.put("email", currentEmail);
                        credentials.put("password", password);
                        logger.info("Found credentials in Excel for email: {}", email);
                        break;
                    }
                }
            }

            if (credentials.isEmpty()) {
                logger.warn("No credentials found in Excel for email: {}", email);
            }

        } catch (IOException e) {
            logger.error("Error reading Excel file: {}", e.getMessage());
            throw new RuntimeException("Failed to read Excel file: " + EXCEL_FILE_PATH, e);
        }

        return credentials;
    }

    public static Map<String, Map<String, String>> getAllUserCredentialsFromExcel() {
        Map<String, Map<String, String>> allCredentials = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(EXCEL_FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    String email = getCellValueAsString(row.getCell(0));
                    String password = getCellValueAsString(row.getCell(1));

                    if (email != null && !email.trim().isEmpty()) {
                        Map<String, String> credentials = new HashMap<>();
                        credentials.put("email", email);
                        credentials.put("password", password);
                        allCredentials.put(email, credentials);
                    }
                }
            }

            logger.info("Retrieved {} user credentials from Excel", allCredentials.size());

        } catch (IOException e) {
            logger.error("Error reading Excel file: {}", e.getMessage());
            throw new RuntimeException("Failed to read Excel file: " + EXCEL_FILE_PATH, e);
        }

        return allCredentials;
    }

    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}


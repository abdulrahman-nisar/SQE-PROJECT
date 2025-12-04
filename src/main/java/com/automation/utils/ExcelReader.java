package com.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExcelReader {

    private static final Logger logger = LogManager.getLogger(ExcelReader.class);

    public static List<Map<String, String>> readExcelData(String filePath, String sheetName) {
        List<Map<String, String>> data = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                logger.error("Sheet '{}' not found in Excel file", sheetName);
                throw new RuntimeException("Sheet '" + sheetName + "' not found");
            }

            Row headerRow = sheet.getRow(0);
            int columnCount = headerRow.getLastCellNum();

            // Get headers
            List<String> headers = new ArrayList<>();
            for (int i = 0; i < columnCount; i++) {
                Cell cell = headerRow.getCell(i);
                headers.add(getCellValue(cell));
            }

            // Read data rows
            int rowCount = sheet.getLastRowNum();
            for (int i = 1; i <= rowCount; i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    Map<String, String> rowData = new HashMap<>();
                    for (int j = 0; j < columnCount; j++) {
                        Cell cell = row.getCell(j);
                        rowData.put(headers.get(j), getCellValue(cell));
                    }
                    data.add(rowData);
                }
            }

            logger.info("Read {} rows from Excel sheet '{}'", data.size(), sheetName);

        } catch (IOException e) {
            logger.error("Error reading Excel file: {}", filePath, e);
            throw new RuntimeException("Failed to read Excel file: " + filePath, e);
        }

        return data;
    }

    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf((long) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "";
        }
    }

    public static Map<String, String> readRowData(String filePath, String sheetName, int rowNumber) {
        List<Map<String, String>> allData = readExcelData(filePath, sheetName);
        if (rowNumber > 0 && rowNumber <= allData.size()) {
            return allData.get(rowNumber - 1);
        }
        throw new RuntimeException("Invalid row number: " + rowNumber);
    }

    public static Object[][] getExcelDataAsArray(String filePath, String sheetName) {
        List<Map<String, String>> data = readExcelData(filePath, sheetName);
        Object[][] dataArray = new Object[data.size()][1];

        for (int i = 0; i < data.size(); i++) {
            dataArray[i][0] = data.get(i);
        }

        return dataArray;
    }
}


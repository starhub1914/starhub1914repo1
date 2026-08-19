package com.cth.sdm.service.handler;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;

@Component
public class ExcelHandler implements DocumentHandler {

    @Override
    public boolean supports(String fileName) {
        return fileName != null && (fileName.toLowerCase().endsWith(".xlsx") || fileName.toLowerCase().endsWith(".xls"));
    }

    @Override
    public String extractText(File file) throws Exception {
        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = WorkbookFactory.create(fis)) {
            StringBuilder sb = new StringBuilder();
            for (Sheet sheet : workbook) {
                sb.append("Sheet: ").append(sheet.getSheetName()).append("\n");
                for (Row row : sheet) {
                    for (Cell cell : row) {
                        sb.append(cell.toString()).append("\t");
                    }
                    sb.append("\n");
                }
            }
            return sb.toString();
        }
    }

    @Override
    public String getHandlerType() {
        return "Excel Spreadsheet Handler";
    }
}

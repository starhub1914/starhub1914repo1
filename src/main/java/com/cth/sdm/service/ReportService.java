package com.cth.sdm.service;

import com.cth.sdm.model.DocumentItem;
import com.cth.sdm.repository.DocumentRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ReportService {

    private final DocumentRepository documentRepository;

    public ReportService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public byte[] generateExcelReport() throws Exception {
        List<DocumentItem> docs = documentRepository.findAll();
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Approval Report");
            Row headerRow = sheet.createRow(0);

            String[] headers = {"Doc ID", "Phase", "Title", "App Code", "Version", "Status", "Maker", "Checker", "Submitted At", "Approved At"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            int rowIdx = 1;
            for (DocumentItem doc : docs) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(doc.getDocId());
                row.createCell(1).setCellValue(doc.getPhaseNumber() != null ? doc.getPhaseNumber() : 0);
                row.createCell(2).setCellValue(doc.getTitle() != null ? doc.getTitle() : "");
                row.createCell(3).setCellValue(doc.getAppCode() != null ? doc.getAppCode() : "SDM");
                row.createCell(4).setCellValue(doc.getVersionNum() != null ? doc.getVersionNum() : "1.0.0");
                row.createCell(5).setCellValue(doc.getStatus() != null ? doc.getStatus() : "");
                row.createCell(6).setCellValue(doc.getMakerUsername() != null ? doc.getMakerUsername() : "-");
                row.createCell(7).setCellValue(doc.getCheckerUsername() != null ? doc.getCheckerUsername() : "-");
                row.createCell(8).setCellValue(doc.getSubmittedAt() != null ? doc.getSubmittedAt().toString() : "-");
                row.createCell(9).setCellValue(doc.getApprovedAt() != null ? doc.getApprovedAt().toString() : "-");
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }

    public byte[] generatePdfReport() throws Exception {
        List<DocumentItem> docs = documentRepository.findAll();
        try (PDDocument pdf = new PDDocument(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PDPage page = new PDPage();
            pdf.addPage(page);

            try (PDPageContentStream cs = new PDPageContentStream(pdf, page)) {
                cs.beginText();
                cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 16);
                cs.newLineAtOffset(50, 750);
                cs.showText("Software Development Document Environment - Approval Summary");
                cs.endText();

                int y = 710;
                cs.beginText();
                cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 10);
                cs.newLineAtOffset(50, y);
                cs.showText("Doc ID   | Phase | Status              | Maker     | Checker");
                cs.endText();

                y -= 20;
                for (DocumentItem doc : docs) {
                    if (y < 50) break;
                    cs.beginText();
                    cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 9);
                    cs.newLineAtOffset(50, y);
                    String line = String.format("%-8s | P%-4d | %-18s | %-9s | %-9s",
                            doc.getDocId(),
                            doc.getPhaseNumber(),
                            doc.getStatus(),
                            doc.getMakerUsername() != null ? doc.getMakerUsername() : "-",
                            doc.getCheckerUsername() != null ? doc.getCheckerUsername() : "-");
                    cs.showText(line);
                    cs.endText();
                    y -= 15;
                }
            }

            pdf.save(out);
            return out.toByteArray();
        }
    }
}

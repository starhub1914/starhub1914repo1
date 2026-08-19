package com.cth.sdm.controller;

import com.cth.sdm.service.ReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportRestController {

    private final ReportService reportService;

    public ReportRestController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/excel")
    public ResponseEntity<byte[]> downloadExcel() throws Exception {
        byte[] data = reportService.generateExcelReport();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Approval_Report.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(data);
    }

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> downloadPdf() throws Exception {
        byte[] data = reportService.generatePdfReport();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Approval_Report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(data);
    }
}

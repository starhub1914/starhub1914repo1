package com.cth.sdm.service.handler;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class PdfHandler implements DocumentHandler {

    @Override
    public boolean supports(String fileName) {
        return fileName != null && fileName.toLowerCase().endsWith(".pdf");
    }

    @Override
    public String extractText(File file) throws Exception {
        try (PDDocument document = Loader.loadPDF(file)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    @Override
    public String getHandlerType() {
        return "PDF Document Handler";
    }
}

package com.cth.sdm.service.handler;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;

@Component
public class WordHandler implements DocumentHandler {

    @Override
    public boolean supports(String fileName) {
        return fileName != null && (fileName.toLowerCase().endsWith(".docx") || fileName.toLowerCase().endsWith(".doc"));
    }

    @Override
    public String extractText(File file) throws Exception {
        try (FileInputStream fis = new FileInputStream(file);
             XWPFDocument document = new XWPFDocument(fis)) {
            StringBuilder sb = new StringBuilder();
            for (XWPFParagraph p : document.getParagraphs()) {
                sb.append(p.getText()).append("\n");
            }
            return sb.toString();
        }
    }

    @Override
    public String getHandlerType() {
        return "Word Document Handler";
    }
}

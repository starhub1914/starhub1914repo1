package com.cth.sdm.service.handler;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFTextShape;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;

@Component
public class PowerPointHandler implements DocumentHandler {

    @Override
    public boolean supports(String fileName) {
        return fileName != null && (fileName.toLowerCase().endsWith(".pptx") || fileName.toLowerCase().endsWith(".ppt"));
    }

    @Override
    public String extractText(File file) throws Exception {
        try (FileInputStream fis = new FileInputStream(file);
             XMLSlideShow ppt = new XMLSlideShow(fis)) {
            StringBuilder sb = new StringBuilder();
            int slideNum = 1;
            for (XSLFSlide slide : ppt.getSlides()) {
                sb.append("Slide ").append(slideNum++).append(":\n");
                for (XSLFShape shape : slide.getShapes()) {
                    if (shape instanceof XSLFTextShape textShape) {
                        sb.append(textShape.getText()).append("\n");
                    }
                }
            }
            return sb.toString();
        }
    }

    @Override
    public String getHandlerType() {
        return "PowerPoint Presentation Handler";
    }
}

package com.cth.sdm.service.handler;

import org.springframework.stereotype.Component;
import java.io.File;
import java.nio.file.Files;

@Component
public class XmlHandler implements DocumentHandler {

    @Override
    public boolean supports(String fileName) {
        return fileName != null && fileName.toLowerCase().endsWith(".xml");
    }

    @Override
    public String extractText(File file) throws Exception {
        return Files.readString(file.toPath());
    }

    @Override
    public String getHandlerType() {
        return "XML Document Handler";
    }
}

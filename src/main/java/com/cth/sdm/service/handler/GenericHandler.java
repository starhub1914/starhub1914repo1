package com.cth.sdm.service.handler;

import org.springframework.stereotype.Component;
import java.io.File;
import java.nio.file.Files;

@Component
public class GenericHandler implements DocumentHandler {

    @Override
    public boolean supports(String fileName) {
        return true; // Fallback handler
    }

    @Override
    public String extractText(File file) throws Exception {
        try {
            return Files.readString(file.toPath());
        } catch (Exception e) {
            return "Binary file content from " + file.getName();
        }
    }

    @Override
    public String getHandlerType() {
        return "Generic / Plain Text Handler";
    }
}

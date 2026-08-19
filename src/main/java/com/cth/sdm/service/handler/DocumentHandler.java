package com.cth.sdm.service.handler;

import java.io.File;

public interface DocumentHandler {
    boolean supports(String fileName);
    String extractText(File file) throws Exception;
    String getHandlerType();
}

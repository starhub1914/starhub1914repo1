package com.cth.sdm.service.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentHandlerRegistry {

    private final List<DocumentHandler> handlers;

    @Autowired
    public DocumentHandlerRegistry(List<DocumentHandler> handlers) {
        this.handlers = handlers;
    }

    public DocumentHandler getHandler(String fileName) {
        for (DocumentHandler handler : handlers) {
            if (!(handler instanceof GenericHandler) && handler.supports(fileName)) {
                return handler;
            }
        }
        // Return generic fallback
        return handlers.stream()
                .filter(h -> h instanceof GenericHandler)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No suitable document handler found"));
    }
}

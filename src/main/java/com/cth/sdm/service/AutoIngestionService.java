package com.cth.sdm.service;

import com.cth.sdm.model.AuditLog;
import com.cth.sdm.repository.AuditLogRepository;
import com.cth.sdm.service.handler.DocumentHandler;
import com.cth.sdm.service.handler.DocumentHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@EnableScheduling
public class AutoIngestionService {

    private static final Logger log = LoggerFactory.getLogger(AutoIngestionService.class);

    @Value("${app.ingestion.watch-folder:./auto_ingest}")
    private String watchFolderPath;

    private final DocumentHandlerRegistry handlerRegistry;
    private final AuditLogRepository auditLogRepository;

    public AutoIngestionService(DocumentHandlerRegistry handlerRegistry, AuditLogRepository auditLogRepository) {
        this.handlerRegistry = handlerRegistry;
        this.auditLogRepository = auditLogRepository;
    }

    @Scheduled(fixedDelayString = "${app.ingestion.poll-interval-ms:5000}")
    public void scanAndIngest() {
        try {
            Path folder = Paths.get(watchFolderPath);
            if (!Files.exists(folder)) {
                Files.createDirectories(folder);
                Path processed = folder.resolve("processed");
                if (!Files.exists(processed)) Files.createDirectories(processed);
                return;
            }

            File[] files = folder.toFile().listFiles((dir, name) -> new File(dir, name).isFile());
            if (files == null || files.length == 0) return;

            Path processedDir = folder.resolve("processed");
            if (!Files.exists(processedDir)) Files.createDirectories(processedDir);

            for (File file : files) {
                log.info("Auto Ingesting picked file: {}", file.getName());
                DocumentHandler handler = handlerRegistry.getHandler(file.getName());
                String extracted = handler.extractText(file);

                log.info("Successfully extracted text via {}. Length: {}", handler.getHandlerType(), extracted.length());

                // Log audit trail
                auditLogRepository.save(AuditLog.builder()
                        .action("AUTO_INGEST")
                        .username("SYSTEM_HANDLER")
                        .details("Picked up file: " + file.getName() + " via " + handler.getHandlerType())
                        .status("SUCCESS")
                        .build());

                // Move to processed folder
                Files.move(file.toPath(), processedDir.resolve(file.getName()), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            log.error("Error during auto ingestion scan: {}", e.getMessage(), e);
        }
    }
}

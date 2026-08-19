package com.cth.sdm.service;

import com.cth.sdm.model.DocumentItem;
import com.cth.sdm.model.DocumentVersion;
import com.cth.sdm.repository.DocumentRepository;
import com.cth.sdm.repository.DocumentVersionRepository;
import com.cth.sdm.repository.SystemConfigRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentVersionRepository versionRepository;
    private final SystemConfigRepository systemConfigRepository;

    public DocumentService(DocumentRepository documentRepository,
                           DocumentVersionRepository versionRepository,
                           SystemConfigRepository systemConfigRepository) {
        this.documentRepository = documentRepository;
        this.versionRepository = versionRepository;
        this.systemConfigRepository = systemConfigRepository;
    }

    public List<DocumentItem> getDocumentsByPhase(Integer phase) {
        return documentRepository.findByPhaseNumber(phase);
    }

    public List<DocumentItem> getAllDocuments() {
        return documentRepository.findAll();
    }

    public Optional<DocumentItem> getDocumentByDocId(String docId) {
        return documentRepository.findByDocId(docId);
    }

    public String getAppCode() {
        return systemConfigRepository.findByConfigKey("APP_CODE")
                .map(c -> c.getConfigValue())
                .orElse("SDM");
    }

    public DocumentItem saveDocument(DocumentItem doc) {
        if (doc.getAppCode() == null || doc.getAppCode().isEmpty()) {
            doc.setAppCode(getAppCode());
        }
        return documentRepository.save(doc);
    }

    public void addVersion(String docId, String fileName, String filePath, String username) {
        DocumentItem item = documentRepository.findByDocId(docId)
                .orElseThrow(() -> new RuntimeException("Document not found: " + docId));

        String newVersion = bumpVersion(item.getVersionNum());
        item.setVersionNum(newVersion);
        item.setFileName(fileName);
        item.setFilePath(filePath);
        documentRepository.save(item);

        versionRepository.save(DocumentVersion.builder()
                .docId(docId)
                .versionNum(newVersion)
                .fileName(fileName)
                .filePath(filePath)
                .uploadedBy(username)
                .build());
    }

    private String bumpVersion(String currentVersion) {
        if (currentVersion == null || currentVersion.isEmpty()) return "1.0.0";
        try {
            String[] parts = currentVersion.split("\\.");
            int minor = Integer.parseInt(parts[1]) + 1;
            return parts[0] + "." + minor + ".0";
        } catch (Exception e) {
            return "1.1.0";
        }
    }
}

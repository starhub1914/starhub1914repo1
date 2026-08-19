package com.cth.sdm.service;

import com.cth.sdm.model.ApprovalTask;
import com.cth.sdm.model.AuditLog;
import com.cth.sdm.model.DocumentItem;
import com.cth.sdm.repository.ApprovalTaskRepository;
import com.cth.sdm.repository.AuditLogRepository;
import com.cth.sdm.repository.DocumentRepository;
import com.cth.sdm.repository.SystemConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MakerCheckerService {

    private static final Logger log = LoggerFactory.getLogger(MakerCheckerService.class);

    private final DocumentRepository documentRepository;
    private final ApprovalTaskRepository approvalTaskRepository;
    private final AuditLogRepository auditLogRepository;
    private final SystemConfigRepository systemConfigRepository;

    public MakerCheckerService(DocumentRepository documentRepository,
                               ApprovalTaskRepository approvalTaskRepository,
                               AuditLogRepository auditLogRepository,
                               SystemConfigRepository systemConfigRepository) {
        this.documentRepository = documentRepository;
        this.approvalTaskRepository = approvalTaskRepository;
        this.auditLogRepository = auditLogRepository;
        this.systemConfigRepository = systemConfigRepository;
    }

    public DocumentItem submitByMaker(String docId, String makerUsername, String comments) {
        DocumentItem item = documentRepository.findByDocId(docId)
                .orElseThrow(() -> new RuntimeException("Document not found: " + docId));

        item.setStatus("PENDING_APPROVAL");
        item.setMakerUsername(makerUsername);
        item.setSubmittedAt(LocalDateTime.now());
        documentRepository.save(item);

        approvalTaskRepository.save(ApprovalTask.builder()
                .docId(docId)
                .makerUsername(makerUsername)
                .action("SUBMIT")
                .comments(comments)
                .build());

        auditLogRepository.save(AuditLog.builder()
                .action("MAKER_SUBMIT")
                .username(makerUsername)
                .details("Submitted document " + docId + " for approval")
                .status("PENDING_APPROVAL")
                .build());

        sendNotification("Checker", "Document " + docId + " submitted by " + makerUsername + " requires approval.");

        return item;
    }

    public DocumentItem approveByChecker(String docId, String checkerUsername, String comments) {
        DocumentItem item = documentRepository.findByDocId(docId)
                .orElseThrow(() -> new RuntimeException("Document not found: " + docId));

        item.setStatus("APPROVED");
        item.setCheckerUsername(checkerUsername);
        item.setApprovedAt(LocalDateTime.now());
        documentRepository.save(item);

        approvalTaskRepository.save(ApprovalTask.builder()
                .docId(docId)
                .makerUsername(item.getMakerUsername())
                .checkerUsername(checkerUsername)
                .action("APPROVE")
                .comments(comments)
                .build());

        auditLogRepository.save(AuditLog.builder()
                .action("CHECKER_APPROVE")
                .username(checkerUsername)
                .details("Approved document " + docId)
                .status("APPROVED")
                .build());

        sendNotification(item.getMakerUsername(), "Document " + docId + " has been APPROVED by " + checkerUsername);

        return item;
    }

    public DocumentItem rejectByChecker(String docId, String checkerUsername, String comments) {
        DocumentItem item = documentRepository.findByDocId(docId)
                .orElseThrow(() -> new RuntimeException("Document not found: " + docId));

        item.setStatus("REJECTED");
        item.setCheckerUsername(checkerUsername);
        documentRepository.save(item);

        approvalTaskRepository.save(ApprovalTask.builder()
                .docId(docId)
                .makerUsername(item.getMakerUsername())
                .checkerUsername(checkerUsername)
                .action("REJECT")
                .comments(comments)
                .build());

        auditLogRepository.save(AuditLog.builder()
                .action("CHECKER_REJECT")
                .username(checkerUsername)
                .details("Rejected document " + docId + ". Reason: " + comments)
                .status("REJECTED")
                .build());

        sendNotification(item.getMakerUsername(), "Document " + docId + " was REJECTED by " + checkerUsername);

        return item;
    }

    private void sendNotification(String recipient, String message) {
        boolean emailEnabled = systemConfigRepository.findByConfigKey("NOTIFICATION_EMAIL_ENABLED")
                .map(c -> Boolean.parseBoolean(c.getConfigValue())).orElse(false);
        boolean smsEnabled = systemConfigRepository.findByConfigKey("NOTIFICATION_SMS_ENABLED")
                .map(c -> Boolean.parseBoolean(c.getConfigValue())).orElse(false);

        if (emailEnabled) {
            log.info("[EMAIL NOTIFICATION] To: {}, Message: {}", recipient, message);
        }
        if (smsEnabled) {
            log.info("[SMS NOTIFICATION] To: {}, Message: {}", recipient, message);
        }
    }
}

package com.cth.sdm;

import com.cth.sdm.model.DocumentItem;
import com.cth.sdm.model.User;
import com.cth.sdm.service.AuthService;
import com.cth.sdm.service.DocumentService;
import com.cth.sdm.service.MakerCheckerService;
import com.cth.sdm.service.ai.AiAgentOrchestrator;
import com.cth.sdm.service.handler.DocumentHandler;
import com.cth.sdm.service.handler.DocumentHandlerRegistry;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private AuthService authService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private MakerCheckerService makerCheckerService;

    @Autowired
    private AiAgentOrchestrator aiAgentOrchestrator;

    @Autowired
    private DocumentHandlerRegistry handlerRegistry;

    @Test
    void testAuthenticationLocalDB() {
        boolean success = authService.authenticate("admin", "password123");
        assertTrue(success, "Admin user should authenticate successfully with password123");
    }

    @Test
    void testDocumentServiceSeededItems() {
        List<DocumentItem> phase1Docs = documentService.getDocumentsByPhase(1);
        assertFalse(phase1Docs.isEmpty(), "Phase 1 should contain pre-seeded items P101, P102, P103");

        Optional<DocumentItem> p101 = documentService.getDocumentByDocId("P101");
        assertTrue(p101.isPresent(), "Doc ID P101 should exist");
        assertEquals("Project Charter", p101.get().getTitle());
    }

    @Test
    void testMakerCheckerWorkflow() {
        makerCheckerService.submitByMaker("P203", "maker1", "Ready for review");
        Optional<DocumentItem> submitted = documentService.getDocumentByDocId("P203");
        assertTrue(submitted.isPresent());
        assertEquals("PENDING_APPROVAL", submitted.get().getStatus());

        makerCheckerService.approveByChecker("P203", "checker1", "Approved for release");
        Optional<DocumentItem> approved = documentService.getDocumentByDocId("P203");
        assertTrue(approved.isPresent());
        assertEquals("APPROVED", approved.get().getStatus());
    }

    @Test
    void testDocumentHandlerRegistry() {
        DocumentHandler wordHandler = handlerRegistry.getHandler("test.docx");
        assertNotNull(wordHandler);
        assertTrue(wordHandler.supports("test.docx"));

        DocumentHandler excelHandler = handlerRegistry.getHandler("test.xlsx");
        assertNotNull(excelHandler);
        assertTrue(excelHandler.supports("test.xlsx"));
    }

    @Test
    void testAiMultiAgentPipeline() {
        String result = aiAgentOrchestrator.processDocumentWithAgents("P101");
        assertNotNull(result);
        assertTrue(result.contains("AI MULTI-AGENT RECOMMENDATIONS"));
    }
}

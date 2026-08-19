package com.cth.sdm.controller;

import com.cth.sdm.model.DocumentItem;
import com.cth.sdm.service.AuthService;
import com.cth.sdm.service.DocumentService;
import com.cth.sdm.service.MakerCheckerService;
import com.cth.sdm.service.ai.AiAgentOrchestrator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainWebController {

    private final DocumentService documentService;
    private final AuthService authService;
    private final MakerCheckerService makerCheckerService;
    private final AiAgentOrchestrator aiAgentOrchestrator;

    public MainWebController(DocumentService documentService,
                             AuthService authService,
                             MakerCheckerService makerCheckerService,
                             AiAgentOrchestrator aiAgentOrchestrator) {
        this.documentService = documentService;
        this.authService = authService;
        this.makerCheckerService = makerCheckerService;
        this.aiAgentOrchestrator = aiAgentOrchestrator;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/dashboard";
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "logout", required = false) String logout, Model model) {
        if (logout != null) {
            model.addAttribute("msg", "You have been logged out successfully.");
        }
        model.addAttribute("isLdap", authService.isLdapEnabled());
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<DocumentItem> allDocs = documentService.getAllDocuments();
        long approvedCount = allDocs.stream().filter(d -> "APPROVED".equals(d.getStatus())).count();
        long pendingCount = allDocs.stream().filter(d -> "PENDING_APPROVAL".equals(d.getStatus())).count();
        long draftCount = allDocs.stream().filter(d -> "PENDING_MAKER".equals(d.getStatus())).count();

        model.addAttribute("allDocs", allDocs);
        model.addAttribute("approvedCount", approvedCount);
        model.addAttribute("pendingCount", pendingCount);
        model.addAttribute("draftCount", draftCount);
        model.addAttribute("appName", "Software Development Document Environment");
        return "dashboard";
    }

    @GetMapping("/phase/{phaseNum}")
    public String phaseView(@PathVariable("phaseNum") Integer phaseNum, Model model) {
        List<DocumentItem> phaseDocs = documentService.getDocumentsByPhase(phaseNum);
        model.addAttribute("phaseNum", phaseNum);
        model.addAttribute("phaseDocs", phaseDocs);
        return "phase_view";
    }

    @GetMapping("/document/{docId}")
    public String documentDetail(@PathVariable("docId") String docId, Model model) {
        DocumentItem doc = documentService.getDocumentByDocId(docId)
                .orElseThrow(() -> new RuntimeException("Document not found: " + docId));
        model.addAttribute("doc", doc);
        return "doc_detail";
    }

    @PostMapping("/document/{docId}/ai-process")
    public String triggerAiProcess(@PathVariable("docId") String docId) {
        aiAgentOrchestrator.processDocumentWithAgents(docId);
        return "redirect:/document/" + docId;
    }

    @PostMapping("/document/{docId}/submit")
    public String submitMaker(@PathVariable("docId") String docId, @RequestParam("comments") String comments) {
        makerCheckerService.submitByMaker(docId, "maker1", comments);
        return "redirect:/document/" + docId;
    }

    @PostMapping("/document/{docId}/approve")
    public String approveChecker(@PathVariable("docId") String docId, @RequestParam("comments") String comments) {
        makerCheckerService.approveByChecker(docId, "checker1", comments);
        return "redirect:/document/" + docId;
    }

    @PostMapping("/document/{docId}/reject")
    public String rejectChecker(@PathVariable("docId") String docId, @RequestParam("comments") String comments) {
        makerCheckerService.rejectByChecker(docId, "checker1", comments);
        return "redirect:/document/" + docId;
    }

    @GetMapping("/admin")
    public String adminPanel(Model model) {
        model.addAttribute("isLdap", authService.isLdapEnabled());
        return "admin";
    }
}

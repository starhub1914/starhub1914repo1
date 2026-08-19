package com.cth.sdm.config;

import com.cth.sdm.model.DocumentItem;
import com.cth.sdm.repository.DocumentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseInitializer {

    @Bean
    public CommandLineRunner initDatabase(DocumentRepository documentRepository) {
        return args -> {
            if (documentRepository.count() == 0) {
                // Phase 1
                documentRepository.save(DocumentItem.builder().docId("P101").phaseNumber(1).title("Project Charter").description("Defines initial scope, goals, stakeholders, and authorization.").appCode("SDM").versionNum("1.0.0").docCode("DOC-P101").status("APPROVED").build());
                documentRepository.save(DocumentItem.builder().docId("P102").phaseNumber(1).title("Project Plan & Schedule").description("Detailed work breakdown structure, timelines, milestones.").appCode("SDM").versionNum("1.0.0").docCode("DOC-P102").status("APPROVED").build());
                documentRepository.save(DocumentItem.builder().docId("P103").phaseNumber(1).title("Risk Management Plan").description("Identifies potential project risks and mitigation strategies.").appCode("SDM").versionNum("1.0.0").docCode("DOC-P103").status("APPROVED").build());

                // Phase 2
                documentRepository.save(DocumentItem.builder().docId("P201").phaseNumber(2).title("Functional Specification Document (FSD)").description("Comprehensive functional requirements and user workflows.").appCode("SDM").versionNum("1.0.0").docCode("DOC-P201").status("APPROVED").build());
                documentRepository.save(DocumentItem.builder().docId("P202").phaseNumber(2).title("Software Requirements Specification (SRS)").description("Technical software constraints and benchmarks.").appCode("SDM").versionNum("1.0.0").docCode("DOC-P202").status("PENDING_APPROVAL").build());
                documentRepository.save(DocumentItem.builder().docId("P203").phaseNumber(2).title("System Architecture Document (SAD)").description("High-level software architecture and module boundaries.").appCode("SDM").versionNum("1.0.0").docCode("DOC-P203").status("PENDING_MAKER").build());

                // Phase 3
                documentRepository.save(DocumentItem.builder().docId("P301").phaseNumber(3).title("High-Level Design (HLD)").description("Architectural design and external interfaces.").appCode("SDM").versionNum("1.0.0").docCode("DOC-P301").status("PENDING_MAKER").build());
                documentRepository.save(DocumentItem.builder().docId("P302").phaseNumber(3).title("Low-Level Design (LLD)").description("Class diagrams, DB details, and algorithm logic.").appCode("SDM").versionNum("1.0.0").docCode("DOC-P302").status("PENDING_MAKER").build());
                documentRepository.save(DocumentItem.builder().docId("P303").phaseNumber(3).title("Database Design Document (DDD)").description("Entity-Relationship diagrams and data dictionary.").appCode("SDM").versionNum("1.0.0").docCode("DOC-P303").status("PENDING_MAKER").build());
                documentRepository.save(DocumentItem.builder().docId("P304").phaseNumber(3).title("Interface Control Document (ICD)").description("API contract specifications and payload mappings.").appCode("SDM").versionNum("1.0.0").docCode("DOC-P304").status("PENDING_MAKER").build());

                // Phase 4
                documentRepository.save(DocumentItem.builder().docId("P401").phaseNumber(4).title("Source Code Repository & Build Scripts").description("Source code inventory and build automation.").appCode("SDM").versionNum("1.0.0").docCode("DOC-P401").status("PENDING_MAKER").build());
                documentRepository.save(DocumentItem.builder().docId("P402").phaseNumber(4).title("Code Review & Quality Report").description("Static code analysis metrics and security scan findings.").appCode("SDM").versionNum("1.0.0").docCode("DOC-P402").status("PENDING_MAKER").build());
                documentRepository.save(DocumentItem.builder().docId("P403").phaseNumber(4).title("Unit Test Cases & Results").description("Unit test coverage reports and execution logs.").appCode("SDM").versionNum("1.0.0").docCode("DOC-P403").status("PENDING_MAKER").build());

                // Phase 5
                documentRepository.save(DocumentItem.builder().docId("P501").phaseNumber(5).title("System Integration Test (SIT) Plan & Report").description("SIT test scenarios and defect logs.").appCode("SDM").versionNum("1.0.0").docCode("DOC-P501").status("PENDING_MAKER").build());
                documentRepository.save(DocumentItem.builder().docId("P502").phaseNumber(5).title("User Acceptance Test (UAT) Plan & Report").description("Business user test cases and signoff metrics.").appCode("SDM").versionNum("1.0.0").docCode("DOC-P502").status("PENDING_MAKER").build());
                documentRepository.save(DocumentItem.builder().docId("P503").phaseNumber(5).title("Security & Performance Test Report").description("Penetration testing results and stress test graphs.").appCode("SDM").versionNum("1.0.0").docCode("DOC-P503").status("PENDING_MAKER").build());

                // Phase 6
                documentRepository.save(DocumentItem.builder().docId("P601").phaseNumber(6).title("Deployment & Installation Guide").description("Step-by-step production installation guide.").appCode("SDM").versionNum("1.0.0").docCode("DOC-P601").status("PENDING_MAKER").build());
                documentRepository.save(DocumentItem.builder().docId("P602").phaseNumber(6).title("User Manual & Training Material").description("End-user operational guide and training deck.").appCode("SDM").versionNum("1.0.0").docCode("DOC-P602").status("PENDING_MAKER").build());
                documentRepository.save(DocumentItem.builder().docId("P603").phaseNumber(6).title("Operational Handover & Support Plan").description("Support escalation matrix and SLA definitions.").appCode("SDM").versionNum("1.0.0").docCode("DOC-P603").status("PENDING_MAKER").build());

                // Phase 7
                documentRepository.save(DocumentItem.builder().docId("P701").phaseNumber(7).title("Post-Implementation Review (PIR) Report").description("Post-go-live evaluation and key lessons learned.").appCode("SDM").versionNum("1.0.0").docCode("DOC-P701").status("PENDING_MAKER").build());
                documentRepository.save(DocumentItem.builder().docId("P702").phaseNumber(7).title("Maintenance & Support Agreement").description("Long-term support agreement and bug fix SLA.").appCode("SDM").versionNum("1.0.0").docCode("DOC-P702").status("PENDING_MAKER").build());
            }
        };
    }
}

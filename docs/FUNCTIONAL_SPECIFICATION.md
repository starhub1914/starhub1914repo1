# FUNCTIONAL SPECIFICATION DOCUMENT (FSD)
## System: Software Development Document Environment (SDM)
**Version:** 1.0.0
**Date:** August 2024
**Author:** Software Engineering Team

---

### 1. Executive Summary
The **Software Development Document Environment** is an enterprise-grade web application built using Java Spring Boot 3 and Maven, designed to streamline, automate, and govern SDM deliverable submissions across 7 distinct software engineering lifecycle phases (P101 through P702).

---

### 2. SDM Lifecycle Phases & Deliverables Matrix

| Phase | Phase Name | Doc ID | Deliverable Title |
|-------|------------|--------|-------------------|
| 1 | Project Initiation & Planning | P101 | Project Charter |
| 1 | Project Initiation & Planning | P102 | Project Plan & Schedule |
| 1 | Project Initiation & Planning | P103 | Risk Management Plan |
| 2 | Requirements & Analysis | P201 | Functional Specification Document (FSD) |
| 2 | Requirements & Analysis | P202 | Software Requirements Specification (SRS) |
| 2 | Requirements & Analysis | P203 | System Architecture Document (SAD) |
| 3 | System & Software Design | P301 | High-Level Design (HLD) |
| 3 | System & Software Design | P302 | Low-Level Design (LLD) |
| 3 | System & Software Design | P303 | Database Design Document (DDD) |
| 3 | System & Software Design | P304 | Interface Control Document (ICD) |
| 4 | Implementation & Coding | P401 | Source Code Repository & Build Scripts |
| 4 | Implementation & Coding | P402 | Code Review & Quality Report |
| 4 | Implementation & Coding | P403 | Unit Test Cases & Results |
| 5 | Testing & Quality Assurance | P501 | System Integration Test (SIT) Plan & Report |
| 5 | Testing & Quality Assurance | P502 | User Acceptance Test (UAT) Plan & Report |
| 5 | Testing & Quality Assurance | P503 | Security & Performance Test Report |
| 6 | Deployment & Transition | P601 | Deployment & Installation Guide |
| 6 | Deployment & Transition | P602 | User Manual & Training Material |
| 6 | Deployment & Transition | P603 | Operational Handover & Support Plan |
| 7 | Maintenance & Post-Implementation | P701 | Post-Implementation Review (PIR) Report |
| 7 | Maintenance & Post-Implementation | P702 | Maintenance & Support Agreement |

---

### 3. Key Functional Modules

#### 3.1 Authentication & Security
- **Dual Authentication Engine:** Dynamically switchable from the frontend between Local Database Auth and Active Directory / LDAP.
- **User Maintenance:** Account lock/unlock, password expiration handling, password reset, and failed login throttling.
- **UI UX:** Eye icon show/hide password toggle on login screen.

#### 3.2 Automated Ingestion & File Handlers
- Polling watcher continuously monitors configurable directory `./auto_ingest`.
- Dynamic handler registry automatically selects appropriate parsing engines for `.docx`, `.xlsx`, `.pptx`, `.xml`, `.pdf`, and generic formats.

#### 3.3 Maker-Checker Approval Workflow
- **Maker Role:** Prepares and submits deliverable versions.
- **Checker Role:** Reviews documents via embedded viewer and signs off on hard gates (Approve / Reject).
- **Notifications:** Email & SMS notification dispatchers (toggleable for demo mode).

#### 3.4 Multi-Agent AI Framework & RAG Integration
- **Processor Agent:** Extracts structural tokens and text.
- **Drafter Agent:** Generates functional insights.
- **Reviewer Agent:** Assesses compliance against Anthropic AI Fluency standards.
- **Log Monitor Agent:** Inspects execution logs for zero error guarantees.
- **Local RAG Service:** Python Flask service powered by Ollama (`llama3.2:latest`, `nomic-embed-text`), Docling parser, and FAISS vector index.

#### 3.5 Reporting & Analytics
- Live dashboard displaying document processing metrics.
- PDF and Excel downloadable approval summary reports.

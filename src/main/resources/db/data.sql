-- Seed Data for SDM Application
INSERT INTO system_config (config_key, config_value, description) VALUES
('APP_NAME', 'Software Development Document Environment', 'Application Title'),
('AUTH_MODE', 'LOCAL', 'Authentication mode (LOCAL or LDAP)'),
('NOTIFICATION_EMAIL_ENABLED', 'false', 'Enable or disable email notifications'),
('NOTIFICATION_SMS_ENABLED', 'false', 'Enable or disable SMS notifications'),
('AUTO_INGESTION_FOLDER', './auto_ingest', 'Auto ingestion file pickup path');

-- Standard Users (Passwords are BCrypt encrypted for "password123")
INSERT INTO app_users (username, password, full_name, email, mobile, role, account_locked, password_expired, failed_attempts) VALUES
('admin', '$2a$10$e0MYzXyjpJS7Pd0RVvHwHe11.7.q11X4/A35k1a2M1gYhGkH.0e3q', 'System Administrator', 'admin@cth.com', '+1234567890', 'ADMIN', false, false, 0),
('maker1', '$2a$10$e0MYzXyjpJS7Pd0RVvHwHe11.7.q11X4/A35k1a2M1gYhGkH.0e3q', 'John Maker', 'maker1@cth.com', '+1234567891', 'MAKER', false, false, 0),
('checker1', '$2a$10$e0MYzXyjpJS7Pd0RVvHwHe11.7.q11X4/A35k1a2M1gYhGkH.0e3q', 'Sarah Checker', 'checker1@cth.com', '+1234567892', 'CHECKER', false, false, 0);

-- Pre-seed standard 20 SDM Deliverable Items from Phase 1 to Phase 7
-- Phase 1: Project Initiation & Planning
INSERT INTO doc_items (doc_id, phase_number, title, description, app_code, version_num, doc_code, status) VALUES
('P101', 1, 'Project Charter', 'Defines initial scope, goals, stakeholders, and authorization of the SDM initiative.', 'SDM', '1.0.0', 'DOC-P101', 'APPROVED'),
('P102', 1, 'Project Plan & Schedule', 'Detailed work breakdown structure, timelines, milestones, and resource allocations.', 'SDM', '1.0.0', 'DOC-P102', 'APPROVED'),
('P103', 1, 'Risk Management Plan', 'Identifies potential project risks, impact assessment, and mitigation strategies.', 'SDM', '1.0.0', 'DOC-P103', 'APPROVED');

-- Phase 2: Requirements & Analysis
INSERT INTO doc_items (doc_id, phase_number, title, description, app_code, version_num, doc_code, status) VALUES
('P201', 2, 'Functional Specification Document (FSD)', 'Comprehensive functional requirements, user workflows, and UI specifications.', 'SDM', '1.0.0', 'DOC-P201', 'APPROVED'),
('P202', 2, 'Software Requirements Specification (SRS)', 'Technical software constraints, performance benchmarks, and compliance rules.', 'SDM', '1.0.0', 'DOC-P202', 'PENDING_APPROVAL'),
('P203', 2, 'System Architecture Document (SAD)', 'High-level software architecture, module boundaries, and technology stack choices.', 'SDM', '1.0.0', 'DOC-P203', 'PENDING_MAKER');

-- Phase 3: System & Software Design
INSERT INTO doc_items (doc_id, phase_number, title, description, app_code, version_num, doc_code, status) VALUES
('P301', 3, 'High-Level Design (HLD)', 'Architectural design, system components interaction, and external interfaces.', 'SDM', '1.0.0', 'DOC-P301', 'PENDING_MAKER'),
('P302', 3, 'Low-Level Design (LLD)', 'Class diagrams, database schema details, algorithm logic, and API endpoints.', 'SDM', '1.0.0', 'DOC-P302', 'PENDING_MAKER'),
('P303', 3, 'Database Design Document (DDD)', 'Entity-Relationship diagrams, data dictionary, indexing, and migration strategy.', 'SDM', '1.0.0', 'DOC-P303', 'PENDING_MAKER'),
('P304', 3, 'Interface Control Document (ICD)', 'API contract specifications, payloads, data mappings, and error codes.', 'SDM', '1.0.0', 'DOC-P304', 'PENDING_MAKER');

-- Phase 4: Implementation & Coding
INSERT INTO doc_items (doc_id, phase_number, title, description, app_code, version_num, doc_code, status) VALUES
('P401', 4, 'Source Code Repository & Build Scripts', 'Source code inventory, repository tag, build automation, and packaging scripts.', 'SDM', '1.0.0', 'DOC-P401', 'PENDING_MAKER'),
('P402', 4, 'Code Review & Quality Report', 'Static code analysis metrics, security scan findings, and peer review signoffs.', 'SDM', '1.0.0', 'DOC-P402', 'PENDING_MAKER'),
('P403', 4, 'Unit Test Cases & Results', 'Unit test coverage reports, execution logs, and automated assertion passes.', 'SDM', '1.0.0', 'DOC-P403', 'PENDING_MAKER');

-- Phase 5: Testing & Quality Assurance
INSERT INTO doc_items (doc_id, phase_number, title, description, app_code, version_num, doc_code, status) VALUES
('P501', 5, 'System Integration Test (SIT) Plan & Report', 'SIT test scenarios, execution traces, integration defect logs, and resolution.', 'SDM', '1.0.0', 'DOC-P501', 'PENDING_MAKER'),
('P502', 5, 'User Acceptance Test (UAT) Plan & Report', 'Business user test cases, signoff metrics, UAT feedback, and approval matrix.', 'SDM', '1.0.0', 'DOC-P502', 'PENDING_MAKER'),
('P503', 5, 'Security & Performance Test Report', 'Penetration testing results, vulnerability assessment, and stress test graphs.', 'SDM', '1.0.0', 'DOC-P503', 'PENDING_MAKER');

-- Phase 6: Deployment & Transition
INSERT INTO doc_items (doc_id, phase_number, title, description, app_code, version_num, doc_code, status) VALUES
('P601', 6, 'Deployment & Installation Guide', 'Step-by-step production installation, environment setup, and rollback plan.', 'SDM', '1.0.0', 'DOC-P601', 'PENDING_MAKER'),
('P602', 6, 'User Manual & Training Material', 'End-user operational guide, system walkthrough, and training presentation deck.', 'SDM', '1.0.0', 'DOC-P602', 'PENDING_MAKER'),
('P603', 6, 'Operational Handover & Support Plan', 'Support escalation matrix, SLA definitions, monitoring setup, and operational runbook.', 'SDM', '1.0.0', 'DOC-P603', 'PENDING_MAKER');

-- Phase 7: Maintenance & Post-Implementation
INSERT INTO doc_items (doc_id, phase_number, title, description, app_code, version_num, doc_code, status) VALUES
('P701', 7, 'Post-Implementation Review (PIR) Report', 'Post-go-live evaluation, project objectives vs actuals, and key lessons learned.', 'SDM', '1.0.0', 'DOC-P701', 'PENDING_MAKER'),
('P702', 7, 'Maintenance & Support Agreement', 'Long-term support agreement, bug fix SLA, and feature enhancement workflow.', 'SDM', '1.0.0', 'DOC-P702', 'PENDING_MAKER');

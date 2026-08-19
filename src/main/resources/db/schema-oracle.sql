-- SDM Database Schema for Oracle
CREATE TABLE app_users (
    id NUMBER(19) GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username VARCHAR2(100) UNIQUE NOT NULL,
    password VARCHAR2(255) NOT NULL,
    full_name VARCHAR2(200) NOT NULL,
    email VARCHAR2(200),
    mobile VARCHAR2(50),
    role VARCHAR2(50) NOT NULL,
    account_locked NUMBER(1) DEFAULT 0,
    password_expired NUMBER(1) DEFAULT 0,
    failed_attempts NUMBER(10) DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE system_config (
    id NUMBER(19) GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    config_key VARCHAR2(100) UNIQUE NOT NULL,
    config_value VARCHAR2(500) NOT NULL,
    description VARCHAR2(255)
);

CREATE TABLE doc_items (
    id NUMBER(19) GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    doc_id VARCHAR2(50) UNIQUE NOT NULL,
    phase_number NUMBER(10) NOT NULL,
    title VARCHAR2(255) NOT NULL,
    description CLOB,
    app_code VARCHAR2(10) DEFAULT 'SDM' NOT NULL,
    version_num VARCHAR2(20) DEFAULT '1.0.0',
    doc_code VARCHAR2(50),
    file_name VARCHAR2(255),
    file_path VARCHAR2(500),
    file_type VARCHAR2(50),
    status VARCHAR2(50) DEFAULT 'PENDING_MAKER' NOT NULL,
    maker_username VARCHAR2(100),
    checker_username VARCHAR2(100),
    submitted_at TIMESTAMP,
    approved_at TIMESTAMP,
    ai_status VARCHAR2(50) DEFAULT 'UNPROCESSED',
    ai_recommendation CLOB
);

CREATE TABLE audit_logs (
    id NUMBER(19) GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    action VARCHAR2(100) NOT NULL,
    username VARCHAR2(100) NOT NULL,
    details CLOB,
    status VARCHAR2(50)
);

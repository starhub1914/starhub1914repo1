-- SDM Database Schema for PostgreSQL
CREATE TABLE IF NOT EXISTS app_users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(200) NOT NULL,
    email VARCHAR(200),
    mobile VARCHAR(50),
    role VARCHAR(50) NOT NULL,
    account_locked BOOLEAN DEFAULT FALSE,
    password_expired BOOLEAN DEFAULT FALSE,
    failed_attempts INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS system_config (
    id BIGSERIAL PRIMARY KEY,
    config_key VARCHAR(100) UNIQUE NOT NULL,
    config_value VARCHAR(500) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS doc_items (
    id BIGSERIAL PRIMARY KEY,
    doc_id VARCHAR(50) UNIQUE NOT NULL,
    phase_number INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    app_code VARCHAR(10) NOT NULL DEFAULT 'SDM',
    version_num VARCHAR(20) DEFAULT '1.0.0',
    doc_code VARCHAR(50),
    file_name VARCHAR(255),
    file_path VARCHAR(500),
    file_type VARCHAR(50),
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING_MAKER',
    maker_username VARCHAR(100),
    checker_username VARCHAR(100),
    submitted_at TIMESTAMP,
    approved_at TIMESTAMP,
    ai_status VARCHAR(50) DEFAULT 'UNPROCESSED',
    ai_recommendation TEXT
);

CREATE TABLE IF NOT EXISTS audit_logs (
    id BIGSERIAL PRIMARY KEY,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    action VARCHAR(100) NOT NULL,
    username VARCHAR(100) NOT NULL,
    details TEXT,
    status VARCHAR(50)
);

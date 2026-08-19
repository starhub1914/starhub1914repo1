-- SDM Database Schema for SQL Server
CREATE TABLE app_users (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    username NVARCHAR(100) UNIQUE NOT NULL,
    password NVARCHAR(255) NOT NULL,
    full_name NVARCHAR(200) NOT NULL,
    email NVARCHAR(200),
    mobile NVARCHAR(50),
    role NVARCHAR(50) NOT NULL,
    account_locked BIT DEFAULT 0,
    password_expired BIT DEFAULT 0,
    failed_attempts INT DEFAULT 0,
    created_at DATETIME2 DEFAULT GETDATE()
);

CREATE TABLE system_config (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    config_key NVARCHAR(100) UNIQUE NOT NULL,
    config_value NVARCHAR(500) NOT NULL,
    description NVARCHAR(255)
);

CREATE TABLE doc_items (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    doc_id NVARCHAR(50) UNIQUE NOT NULL,
    phase_number INT NOT NULL,
    title NVARCHAR(255) NOT NULL,
    description NVARCHAR(MAX),
    app_code NVARCHAR(10) DEFAULT 'SDM' NOT NULL,
    version_num NVARCHAR(20) DEFAULT '1.0.0',
    doc_code NVARCHAR(50),
    file_name NVARCHAR(255),
    file_path NVARCHAR(500),
    file_type NVARCHAR(50),
    status NVARCHAR(50) DEFAULT 'PENDING_MAKER' NOT NULL,
    maker_username NVARCHAR(100),
    checker_username NVARCHAR(100),
    submitted_at DATETIME2,
    approved_at DATETIME2,
    ai_status NVARCHAR(50) DEFAULT 'UNPROCESSED',
    ai_recommendation NVARCHAR(MAX)
);

CREATE TABLE audit_logs (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    timestamp DATETIME2 DEFAULT GETDATE(),
    action NVARCHAR(100) NOT NULL,
    username NVARCHAR(100) NOT NULL,
    details NVARCHAR(MAX),
    status NVARCHAR(50)
);

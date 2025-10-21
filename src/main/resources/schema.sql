-- Credit Bureau Service Database Schema

-- Credit Scores Table
CREATE TABLE IF NOT EXISTS credit_scores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ssn VARCHAR(11) NOT NULL UNIQUE,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    score INTEGER NOT NULL,
    risk_level VARCHAR(10) NOT NULL,
    last_updated TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL
);

-- Credit History Table
CREATE TABLE IF NOT EXISTS credit_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ssn VARCHAR(11) NOT NULL,
    account_type VARCHAR(20) NOT NULL,
    creditor_name VARCHAR(100) NOT NULL,
    original_amount DECIMAL(15,2),
    current_balance DECIMAL(15,2),
    credit_limit DECIMAL(15,2),
    payment_status VARCHAR(20) NOT NULL,
    days_late INTEGER DEFAULT 0,
    account_open_date TIMESTAMP,
    last_payment_date TIMESTAMP,
    reported_date TIMESTAMP NOT NULL,
    is_active BOOLEAN DEFAULT TRUE
);

-- Loan Applications Table
CREATE TABLE IF NOT EXISTS loan_applications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    application_id VARCHAR(36) NOT NULL UNIQUE,
    ssn VARCHAR(11) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255),
    phone VARCHAR(20),
    requested_amount DECIMAL(15,2) NOT NULL,
    loan_type VARCHAR(20) NOT NULL,
    term_months INTEGER NOT NULL,
    annual_income DECIMAL(15,2) NOT NULL,
    employment_status VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    rejection_reason VARCHAR(500),
    application_date TIMESTAMP NOT NULL,
    processed_date TIMESTAMP
);

-- Indexes for better performance
CREATE INDEX IF NOT EXISTS idx_credit_scores_ssn ON credit_scores(ssn);
CREATE INDEX IF NOT EXISTS idx_credit_history_ssn ON credit_history(ssn);
CREATE INDEX IF NOT EXISTS idx_credit_history_ssn_active ON credit_history(ssn, is_active);
CREATE INDEX IF NOT EXISTS idx_loan_applications_ssn ON loan_applications(ssn);
CREATE INDEX IF NOT EXISTS idx_loan_applications_status ON loan_applications(status);
CREATE INDEX IF NOT EXISTS idx_loan_applications_app_id ON loan_applications(application_id);

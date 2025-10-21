-- Sample data for Credit Bureau Service

-- Insert sample credit scores
INSERT INTO credit_scores (ssn, first_name, last_name, score, risk_level, last_updated, created_at) VALUES
('123-45-6789', 'John', 'Doe', 750, 'LOW', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('987-65-4321', 'Jane', 'Smith', 620, 'MEDIUM', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('555-12-3456', 'Bob', 'Johnson', 580, 'MEDIUM', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('111-22-3333', 'Alice', 'Brown', 720, 'LOW', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('444-55-6666', 'Charlie', 'Wilson', 450, 'HIGH', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample credit history
INSERT INTO credit_history (ssn, account_type, creditor_name, original_amount, current_balance, credit_limit, payment_status, days_late, account_open_date, last_payment_date, reported_date, is_active) VALUES
-- John Doe's credit history (good credit)
('123-45-6789', 'CREDIT_CARD', 'Chase Bank', 5000.00, 1200.00, 5000.00, 'CURRENT', 0, '2020-01-01 00:00:00', '2024-10-15 00:00:00', CURRENT_TIMESTAMP, TRUE),
('123-45-6789', 'AUTO_LOAN', 'Ford Credit', 25000.00, 15000.00, 25000.00, 'CURRENT', 0, '2022-03-15 00:00:00', '2024-10-10 00:00:00', CURRENT_TIMESTAMP, TRUE),
('123-45-6789', 'CREDIT_CARD', 'Capital One', 3000.00, 500.00, 3000.00, 'CURRENT', 0, '2019-06-01 00:00:00', '2024-10-20 00:00:00', CURRENT_TIMESTAMP, TRUE),

-- Jane Smith's credit history (fair credit)
('987-65-4321', 'CREDIT_CARD', 'Discover', 2000.00, 1800.00, 2000.00, 'CURRENT', 0, '2021-02-01 00:00:00', '2024-10-12 00:00:00', CURRENT_TIMESTAMP, TRUE),
('987-65-4321', 'LOAN', 'Personal Loan Co', 10000.00, 7500.00, 10000.00, 'LATE_30', 25, '2023-01-01 00:00:00', '2024-09-15 00:00:00', CURRENT_TIMESTAMP, TRUE),
('987-65-4321', 'CREDIT_CARD', 'Bank of America', 1500.00, 1400.00, 1500.00, 'CURRENT', 0, '2020-08-01 00:00:00', '2024-10-18 00:00:00', CURRENT_TIMESTAMP, TRUE),

-- Bob Johnson's credit history (fair credit with issues)
('555-12-3456', 'CREDIT_CARD', 'Wells Fargo', 1000.00, 950.00, 1000.00, 'LATE_60', 55, '2022-05-01 00:00:00', '2024-08-20 00:00:00', CURRENT_TIMESTAMP, TRUE),
('555-12-3456', 'CREDIT_CARD', 'Citi Bank', 2500.00, 2500.00, 2500.00, 'LATE_30', 20, '2021-03-01 00:00:00', '2024-09-25 00:00:00', CURRENT_TIMESTAMP, TRUE),

-- Alice Brown's credit history (good credit)
('111-22-3333', 'MORTGAGE', 'Wells Fargo Home', 300000.00, 250000.00, 300000.00, 'CURRENT', 0, '2018-01-01 00:00:00', '2024-10-01 00:00:00', CURRENT_TIMESTAMP, TRUE),
('111-22-3333', 'CREDIT_CARD', 'American Express', 10000.00, 2000.00, 10000.00, 'CURRENT', 0, '2017-01-01 00:00:00', '2024-10-22 00:00:00', CURRENT_TIMESTAMP, TRUE),
('111-22-3333', 'AUTO_LOAN', 'Honda Finance', 20000.00, 5000.00, 20000.00, 'CURRENT', 0, '2021-06-01 00:00:00', '2024-10-05 00:00:00', CURRENT_TIMESTAMP, TRUE),

-- Charlie Wilson's credit history (poor credit)
('444-55-6666', 'CREDIT_CARD', 'Capital One', 500.00, 500.00, 500.00, 'LATE_90', 85, '2023-01-01 00:00:00', '2024-07-15 00:00:00', CURRENT_TIMESTAMP, TRUE),
('444-55-6666', 'LOAN', 'Quick Cash Loans', 2000.00, 2000.00, 2000.00, 'COLLECTIONS', 120, '2022-08-01 00:00:00', '2024-06-01 00:00:00', CURRENT_TIMESTAMP, FALSE);

-- Insert sample loan applications
INSERT INTO loan_applications (application_id, ssn, first_name, last_name, email, phone, requested_amount, loan_type, term_months, annual_income, employment_status, status, rejection_reason, application_date, processed_date) VALUES
('app-001', '123-45-6789', 'John', 'Doe', 'john.doe@email.com', '555-0101', 15000.00, 'PERSONAL', 36, 75000.00, 'EMPLOYED', 'APPROVED', NULL, '2024-10-15 10:00:00', '2024-10-15 11:30:00'),
('app-002', '987-65-4321', 'Jane', 'Smith', 'jane.smith@email.com', '555-0102', 25000.00, 'AUTO', 60, 55000.00, 'EMPLOYED', 'MANUAL_REVIEW', NULL, '2024-10-18 14:00:00', NULL),
('app-003', '444-55-6666', 'Charlie', 'Wilson', 'charlie.wilson@email.com', '555-0103', 5000.00, 'PERSONAL', 24, 30000.00, 'EMPLOYED', 'REJECTED', 'Credit score too low', '2024-10-20 09:00:00', '2024-10-20 09:15:00');

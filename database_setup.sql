-- Database Setup for Test Automation Framework
-- This SQL script creates sample database structure for testing

-- Create database
CREATE DATABASE IF NOT EXISTS testdb;
USE testdb;

-- Users table for storing test user credentials
CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    status VARCHAR(50) DEFAULT 'active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Contacts table for storing test contact data
CREATE TABLE IF NOT EXISTS contacts (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    birthdate DATE,
    email VARCHAR(255),
    phone VARCHAR(50),
    street1 VARCHAR(255),
    street2 VARCHAR(255),
    city VARCHAR(100),
    state_province VARCHAR(100),
    postal_code VARCHAR(20),
    country VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Test data table for various test scenarios
CREATE TABLE IF NOT EXISTS test_data (
    id INT PRIMARY KEY AUTO_INCREMENT,
    test_type VARCHAR(100) NOT NULL,
    test_key VARCHAR(100) NOT NULL,
    test_value TEXT,
    description VARCHAR(255),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert sample test users
INSERT INTO users (first_name, last_name, email, password, status) VALUES
('John', 'Doe', 'john.doe@test.com', 'password123', 'active'),
('Jane', 'Smith', 'jane.smith@test.com', 'password456', 'active'),
('Bob', 'Johnson', 'bob.johnson@test.com', 'password789', 'active'),
('Alice', 'Williams', 'alice.williams@test.com', 'password000', 'inactive');

-- Insert sample contacts
INSERT INTO contacts (user_id, first_name, last_name, birthdate, email, phone, street1, city, state_province, postal_code, country) VALUES
(1, 'Michael', 'Brown', '1985-06-15', 'michael.brown@example.com', '5551234567', '123 Main St', 'New York', 'NY', '10001', 'USA'),
(1, 'Sarah', 'Davis', '1990-03-20', 'sarah.davis@example.com', '5559876543', '456 Oak Ave', 'Los Angeles', 'CA', '90001', 'USA'),
(2, 'Robert', 'Wilson', '1988-11-10', 'robert.wilson@example.com', '5555551234', '789 Pine Rd', 'Chicago', 'IL', '60601', 'USA'),
(2, 'Emily', 'Jones', '1992-07-25', 'emily.jones@example.com', '5554443333', '321 Elm St', 'Houston', 'TX', '77001', 'USA');

-- Insert sample test data
INSERT INTO test_data (test_type, test_key, test_value, description, is_active) VALUES
('login', 'valid_email', 'test@example.com', 'Valid email for login tests', TRUE),
('login', 'valid_password', 'password123', 'Valid password for login tests', TRUE),
('login', 'invalid_email', 'invalid@test.com', 'Invalid email for negative tests', TRUE),
('login', 'invalid_password', 'wrongpass', 'Invalid password for negative tests', TRUE),
('contact', 'test_first_name', 'TestFirst', 'Test first name for contacts', TRUE),
('contact', 'test_last_name', 'TestLast', 'Test last name for contacts', TRUE),
('contact', 'test_email', 'testcontact@example.com', 'Test email for contacts', TRUE),
('contact', 'test_phone', '1234567890', 'Test phone number', TRUE);

-- Create indexes for better query performance
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_status ON users(status);
CREATE INDEX idx_contacts_user_id ON contacts(user_id);
CREATE INDEX idx_contacts_email ON contacts(email);
CREATE INDEX idx_test_data_type ON test_data(test_type);
CREATE INDEX idx_test_data_key ON test_data(test_key);

-- Create views for common queries
CREATE OR REPLACE VIEW active_users AS
SELECT id, first_name, last_name, email, created_at
FROM users
WHERE status = 'active';

CREATE OR REPLACE VIEW user_contacts_summary AS
SELECT
    u.id as user_id,
    u.first_name as user_first_name,
    u.last_name as user_last_name,
    u.email as user_email,
    COUNT(c.id) as total_contacts
FROM users u
LEFT JOIN contacts c ON u.id = c.user_id
GROUP BY u.id, u.first_name, u.last_name, u.email;

-- Verify data
SELECT 'Users table created and populated' as Status;
SELECT COUNT(*) as Total_Users FROM users;

SELECT 'Contacts table created and populated' as Status;
SELECT COUNT(*) as Total_Contacts FROM contacts;

SELECT 'Test data table created and populated' as Status;
SELECT COUNT(*) as Total_Test_Data FROM test_data;

SELECT 'Database setup completed successfully!' as Status;


-- Time Tracking System Database Schema
-- MySQL Script

-- Create database (if not exists)
CREATE DATABASE IF NOT EXISTS timetracker_db;
USE timetracker_db;

-- Roles table
CREATE TABLE IF NOT EXISTS roles (
    role_id INT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Permissions table
CREATE TABLE IF NOT EXISTS permissions (
    permission_id INT PRIMARY KEY AUTO_INCREMENT,
    permission_name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Role-Permission junction table
CREATE TABLE IF NOT EXISTS role_permissions (
    role_id INT NOT NULL,
    permission_id INT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permissions(permission_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Users table
CREATE TABLE IF NOT EXISTS users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- User-Role junction table
CREATE TABLE IF NOT EXISTS user_roles (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Projects table
CREATE TABLE IF NOT EXISTS projects (
    project_id INT PRIMARY KEY AUTO_INCREMENT,
    project_name VARCHAR(255) NOT NULL,
    description TEXT,
    start_date DATE,
    end_date DATE,
    status ENUM('ACTIVE', 'COMPLETED', 'ON_HOLD', 'CANCELLED') DEFAULT 'ACTIVE',
    created_by INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES users(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Milestones table
CREATE TABLE IF NOT EXISTS milestones (
    milestone_id INT PRIMARY KEY AUTO_INCREMENT,
    project_id INT NOT NULL,
    milestone_name VARCHAR(255) NOT NULL,
    description TEXT,
    target_date DATE,
    status ENUM('PENDING', 'IN_PROGRESS', 'COMPLETED', 'DELAYED') DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (project_id) REFERENCES projects(project_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tasks table
CREATE TABLE IF NOT EXISTS tasks (
    task_id INT PRIMARY KEY AUTO_INCREMENT,
    project_id INT NOT NULL,
    milestone_id INT,
    task_title VARCHAR(255) NOT NULL,
    description TEXT,
    assigned_to INT,
    status ENUM('TODO', 'IN_PROGRESS', 'COMPLETED', 'BLOCKED') DEFAULT 'TODO',
    priority ENUM('LOW', 'MEDIUM', 'HIGH', 'CRITICAL') DEFAULT 'MEDIUM',
    estimated_hours DECIMAL(10, 2),
    deadline DATE,
    created_by INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (project_id) REFERENCES projects(project_id) ON DELETE CASCADE,
    FOREIGN KEY (milestone_id) REFERENCES milestones(milestone_id) ON DELETE SET NULL,
    FOREIGN KEY (assigned_to) REFERENCES users(user_id) ON DELETE SET NULL,
    FOREIGN KEY (created_by) REFERENCES users(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Time Logs table
CREATE TABLE IF NOT EXISTS time_logs (
    time_log_id INT PRIMARY KEY AUTO_INCREMENT,
    task_id INT NOT NULL,
    user_id INT NOT NULL,
    hours_spent DECIMAL(10, 2) NOT NULL,
    log_date DATE NOT NULL,
    description VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (task_id) REFERENCES tasks(task_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Insert default roles
INSERT INTO roles (role_name, description) VALUES
('ADMIN', 'Administrator - Full system access'),
('PROJECT_MANAGER', 'Project Manager - Manage projects and tasks'),
('TEAM_MEMBER', 'Team Member - Log time and update tasks');

-- Insert default permissions
INSERT INTO permissions (permission_name, description) VALUES
('USER_MANAGE', 'Manage users and roles'),
('PROJECT_CREATE', 'Create and manage projects'),
('PROJECT_VIEW', 'View projects'),
('TASK_ASSIGN', 'Assign tasks to team members'),
('TASK_UPDATE', 'Update task status'),
('TASK_VIEW', 'View tasks'),
('TIME_LOG', 'Log time against tasks'),
('TIME_VIEW', 'View time logs'),
('REPORT_VIEW', 'View reports'),
('INTEGRATION_MANAGE', 'Manage integrations');

-- Grant permissions to Admin role
INSERT INTO role_permissions (role_id, permission_id) VALUES
((SELECT role_id FROM roles WHERE role_name='ADMIN'), (SELECT permission_id FROM permissions WHERE permission_name='USER_MANAGE')),
((SELECT role_id FROM roles WHERE role_name='ADMIN'), (SELECT permission_id FROM permissions WHERE permission_name='PROJECT_CREATE')),
((SELECT role_id FROM roles WHERE role_name='ADMIN'), (SELECT permission_id FROM permissions WHERE permission_name='PROJECT_VIEW')),
((SELECT role_id FROM roles WHERE role_name='ADMIN'), (SELECT permission_id FROM permissions WHERE permission_name='TASK_ASSIGN')),
((SELECT role_id FROM roles WHERE role_name='ADMIN'), (SELECT permission_id FROM permissions WHERE permission_name='TASK_UPDATE')),
((SELECT role_id FROM roles WHERE role_name='ADMIN'), (SELECT permission_id FROM permissions WHERE permission_name='TASK_VIEW')),
((SELECT role_id FROM roles WHERE role_name='ADMIN'), (SELECT permission_id FROM permissions WHERE permission_name='TIME_LOG')),
((SELECT role_id FROM roles WHERE role_name='ADMIN'), (SELECT permission_id FROM permissions WHERE permission_name='TIME_VIEW')),
((SELECT role_id FROM roles WHERE role_name='ADMIN'), (SELECT permission_id FROM permissions WHERE permission_name='REPORT_VIEW')),
((SELECT role_id FROM roles WHERE role_name='ADMIN'), (SELECT permission_id FROM permissions WHERE permission_name='INTEGRATION_MANAGE'));

-- Grant permissions to Project Manager role
INSERT INTO role_permissions (role_id, permission_id) VALUES
((SELECT role_id FROM roles WHERE role_name='PROJECT_MANAGER'), (SELECT permission_id FROM permissions WHERE permission_name='PROJECT_VIEW')),
((SELECT role_id FROM roles WHERE role_name='PROJECT_MANAGER'), (SELECT permission_id FROM permissions WHERE permission_name='TASK_ASSIGN')),
((SELECT role_id FROM roles WHERE role_name='PROJECT_MANAGER'), (SELECT permission_id FROM permissions WHERE permission_name='TASK_UPDATE')),
((SELECT role_id FROM roles WHERE role_name='PROJECT_MANAGER'), (SELECT permission_id FROM permissions WHERE permission_name='TASK_VIEW')),
((SELECT role_id FROM roles WHERE role_name='PROJECT_MANAGER'), (SELECT permission_id FROM permissions WHERE permission_name='TIME_LOG')),
((SELECT role_id FROM roles WHERE role_name='PROJECT_MANAGER'), (SELECT permission_id FROM permissions WHERE permission_name='TIME_VIEW')),
((SELECT role_id FROM roles WHERE role_name='PROJECT_MANAGER'), (SELECT permission_id FROM permissions WHERE permission_name='REPORT_VIEW'));

-- Grant permissions to Team Member role
INSERT INTO role_permissions (role_id, permission_id) VALUES
((SELECT role_id FROM roles WHERE role_name='TEAM_MEMBER'), (SELECT permission_id FROM permissions WHERE permission_name='TASK_UPDATE')),
((SELECT role_id FROM roles WHERE role_name='TEAM_MEMBER'), (SELECT permission_id FROM permissions WHERE permission_name='TASK_VIEW')),
((SELECT role_id FROM roles WHERE role_name='TEAM_MEMBER'), (SELECT permission_id FROM permissions WHERE permission_name='TIME_LOG')),
((SELECT role_id FROM roles WHERE role_name='TEAM_MEMBER'), (SELECT permission_id FROM permissions WHERE permission_name='TIME_VIEW'));

-- Create indexes for performance
CREATE INDEX idx_user_username ON users(username);
CREATE INDEX idx_user_email ON users(email);
CREATE INDEX idx_project_status ON projects(status);
CREATE INDEX idx_task_project ON tasks(project_id);
CREATE INDEX idx_task_assigned_to ON tasks(assigned_to);
CREATE INDEX idx_task_status ON tasks(status);
CREATE INDEX idx_time_log_user ON time_logs(user_id);
CREATE INDEX idx_time_log_task ON time_logs(task_id);
CREATE INDEX idx_time_log_date ON time_logs(log_date);


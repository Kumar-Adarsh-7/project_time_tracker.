-- Demo Data for Time Tracking System

-- ============================================================================
-- IMPORTANT: Password Hashing Instructions
-- ============================================================================
-- The password_hash values below are PLACEHOLDERS and MUST be replaced with
-- actual SHA-256 hashed passwords before using this script.
--
-- To generate hashed passwords, use the PasswordUtil Java class:
--
--   String hash = PasswordUtil.hashPassword("password");
--   System.out.println(hash);
--
-- Demo Credentials:
--   admin / admin123
--   pm_user / pm123
--   team_user / team123
--   john_dev / john123
--   jane_dev / jane123
--
-- After generating hashes using PasswordUtil, replace PLACEHOLDER_HASH_1 through
-- PLACEHOLDER_HASH_5 with the actual hashes before running this script.
-- ============================================================================

-- Create demo users with PLAIN TEXT passwords (for development/testing)
-- Credentials: admin/admin123, pm_user/pm123, team_user/team123, john_dev/john123, jane_dev/jane123
INSERT INTO users (username, email, password_hash, first_name, last_name, is_active) VALUES
('admin', 'admin@timetracker.com', 'admin123', 'Admin', 'User', TRUE),
('pm_user', 'pm@timetracker.com', 'pm123', 'Project', 'Manager', TRUE),
('team_user', 'team@timetracker.com', 'team123', 'Team', 'Member', TRUE),
('john_dev', 'john@timetracker.com', 'john123', 'John', 'Developer', TRUE),
('jane_dev', 'jane@timetracker.com', 'jane123', 'Jane', 'Developer', TRUE);

-- Assign roles to users
INSERT INTO user_roles (user_id, role_id) VALUES
((SELECT user_id FROM users WHERE username='admin'), (SELECT role_id FROM roles WHERE role_name='ADMIN')),
((SELECT user_id FROM users WHERE username='pm_user'), (SELECT role_id FROM roles WHERE role_name='PROJECT_MANAGER')),
((SELECT user_id FROM users WHERE username='team_user'), (SELECT role_id FROM roles WHERE role_name='TEAM_MEMBER')),
((SELECT user_id FROM users WHERE username='john_dev'), (SELECT role_id FROM roles WHERE role_name='TEAM_MEMBER')),
((SELECT user_id FROM users WHERE username='jane_dev'), (SELECT role_id FROM roles WHERE role_name='TEAM_MEMBER'));

-- Create sample projects
INSERT INTO projects (project_name, description, start_date, end_date, status, created_by) VALUES
('E-Commerce Platform', 'Build a full-featured e-commerce platform with payment integration', '2024-01-01', '2024-06-30', 'ACTIVE', 1),
('Mobile App', 'Develop cross-platform mobile application', '2024-02-01', '2024-05-31', 'ACTIVE', 1),
('Data Analytics Dashboard', 'Create real-time analytics dashboard for business insights', '2024-03-01', '2024-04-30', 'IN_PROGRESS', 1);

-- Create sample milestones
INSERT INTO milestones (project_id, milestone_name, description, target_date, status) VALUES
(1, 'Database Setup', 'Design and setup database schema', '2024-01-15', 'COMPLETED'),
(1, 'Backend APIs', 'Develop REST APIs for core functionality', '2024-02-28', 'IN_PROGRESS'),
(1, 'Frontend Development', 'Build user interface', '2024-04-30', 'PENDING'),
(2, 'App Design', 'UI/UX design phase', '2024-02-15', 'COMPLETED'),
(2, 'Development Sprint 1', 'First development sprint', '2024-03-31', 'IN_PROGRESS');

-- Create sample tasks
INSERT INTO tasks (project_id, milestone_id, task_title, description, assigned_to, status, priority, estimated_hours, deadline, created_by) VALUES
(1, 1, 'User Authentication', 'Implement user login and registration', 3, 'COMPLETED', 'HIGH', 16, '2024-01-10', 1),
(1, 2, 'Product API', 'Create REST API for product management', 4, 'IN_PROGRESS', 'HIGH', 20, '2024-02-20', 1),
(1, 2, 'Order Management', 'Develop order processing system', 5, 'IN_PROGRESS', 'HIGH', 24, '2024-02-25', 1),
(2, 4, 'Mobile UI Design', 'Design mobile app interface', 3, 'COMPLETED', 'MEDIUM', 12, '2024-02-10', 1),
(2, 5, 'Home Screen Implementation', 'Code home screen features', 4, 'IN_PROGRESS', 'MEDIUM', 16, '2024-03-20', 1),
(3, NULL, 'Data Collection Setup', 'Setup data collection pipelines', 5, 'PENDING', 'HIGH', 8, '2024-03-15', 1);

-- Insert sample time logs
INSERT INTO time_logs (task_id, user_id, hours_spent, log_date, description) VALUES
(1, 3, 4, '2024-01-08', 'Implemented login page and authentication logic'),
(1, 3, 3, '2024-01-09', 'Fixed password hashing and session management'),
(2, 4, 5, '2024-02-18', 'Created product CRUD endpoints'),
(2, 4, 4, '2024-02-19', 'Added validation and error handling'),
(3, 5, 6, '2024-02-20', 'Implemented order creation and status tracking'),
(3, 5, 5, '2024-02-21', 'Added payment integration'),
(4, 3, 8, '2024-02-08', 'Designed complete mobile app UI'),
(5, 4, 4, '2024-03-15', 'Coded navigation structure'),
(5, 4, 6, '2024-03-16', 'Implemented home screen components');


# Time Tracker - Project Management System

A comprehensive web-based time tracking and project management tool built with Servlets, JSP, and JDBC.

## Overview

The Time Tracker is designed for three user types:
- **Admin**: Manages users, projects, and views system-wide reports
- **Project Manager**: Assigns tasks, monitors progress, and generates project reports
- **Team Member**: Logs time, updates task status, and views personal time logs

## Technology Stack

- **Backend**: Java Servlets (Jakarta)
- **Frontend**: JSP, HTML5, CSS3
- **Database**: MySQL
- **ORM/Database Access**: JDBC
- **Connection Pooling**: HikariCP
- **Logging**: SLF4J + Logback
- **Build Tool**: Maven

## Project Structure

```
demo1/
├── pom.xml                           # Maven configuration
├── src/
│   ├── main/
│   │   ├── java/org/timetracker/demo1/
│   │   │   ├── models/              # Entity classes
│   │   │   │   ├── User.java
│   │   │   │   ├── Role.java
│   │   │   │   ├── Project.java
│   │   │   │   ├── Task.java
│   │   │   │   ├── TimeLog.java
│   │   │   │   ├── Milestone.java
│   │   │   │   └── Permission.java
│   │   │   ├── dao/                 # Data Access Objects
│   │   │   │   ├── UserDAO.java
│   │   │   │   ├── ProjectDAO.java
│   │   │   │   ├── TaskDAO.java
│   │   │   │   ├── TimeLogDAO.java
│   │   │   │   └── RoleDAO.java
│   │   │   ├── servlets/            # HTTP Servlets
│   │   │   │   ├── LoginServlet.java
│   │   │   │   ├── LogoutServlet.java
│   │   │   │   ├── UserManagementServlet.java
│   │   │   │   ├── ProjectManagementServlet.java
│   │   │   │   ├── TaskManagementServlet.java
│   │   │   │   ├── TimeLoggingServlet.java
│   │   │   │   └── ReportServlet.java
│   │   │   ├── security/            # Authentication & Authorization
│   │   │   │   ├── PasswordUtil.java
│   │   │   │   └── AuthFilter.java
│   │   │   ├── util/
│   │   │   │   └── DBConnection.java
│   │   │   └── HelloServlet.java
│   │   ├── resources/
│   │   │   ├── database_schema.sql  # Database schema
│   │   │   └── demo_data.sql        # Demo data
│   │   └── webapp/
│   │       ├── index.jsp            # Home page
│   │       ├── login.jsp            # Login page
│   │       ├── WEB-INF/
│   │       │   ├── header.jsp       # Navigation header
│   │       │   └── web.xml          # Deployment descriptor
│   │       ├── admin/               # Admin views
│   │       │   ├── dashboard.jsp
│   │       │   ├── user-list.jsp
│   │       │   ├── user-edit.jsp
│   │       │   ├── project-list.jsp
│   │       │   ├── project-edit.jsp
│   │       │   └── reports.jsp
│   │       ├── project-manager/     # Project Manager views
│   │       │   ├── dashboard.jsp
│   │       │   ├── task-list.jsp
│   │       │   ├── task-edit.jsp
│   │       │   └── reports.jsp
│   │       └── team-member/         # Team Member views
│   │           ├── dashboard.jsp
│   │           ├── log-time.jsp
│   │           ├── time-logs.jsp
│   │           └── tasks.jsp
│   └── test/
│       └── java/                    # Unit tests
└── mvnw, mvnw.cmd                   # Maven wrapper
```

## Prerequisites

- Java 23 or higher
- Maven 3.6+
- MySQL 8.0+
- Apache Tomcat 10+ or similar servlet container

## Setup Instructions

### 1. Database Setup

Create the database and tables:

```bash
mysql -u root -p < src/main/resources/database_schema.sql
```

Insert demo data:

```bash
mysql -u root -p timetracker_db < src/main/resources/demo_data.sql
```

### 2. Database Configuration

Edit `DBConnection.java` with your database credentials:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/timetracker_db?useSSL=false&serverTimezone=UTC";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "root";
```

### 3. Build the Project

```bash
mvn clean package
```

### 4. Deploy to Tomcat

Copy the generated WAR file to Tomcat's webapps directory:

```bash
cp target/demo1-1.0-SNAPSHOT.war $CATALINA_HOME/webapps/
```

Start Tomcat:

```bash
./catalina.sh start
```

Access the application at: `http://localhost:8080/demo1/`

## Demo Credentials

| Role | Username | Password |
|------|----------|----------|
| Admin | admin | admin123 |
| Project Manager | pm_user | pm123 |
| Team Member | team_user | team123 |

## Key Features

### Admin Dashboard
- ✅ User Access Control - Assign roles and permissions
- ✅ Project Setup - Create and manage projects
- ✅ Project Reports - View time allocation per project
- ✅ System Settings - Configure integration options

### Project Manager Dashboard
- ✅ Task Assignment - Create and assign tasks to team members
- ✅ Monitor Time Tracking - View time spent on tasks
- ✅ Progress Review - Track project milestones
- ✅ Generate Reports - Create comprehensive project reports

### Team Member Dashboard
- ✅ Log Time - Record hours spent on tasks
- ✅ View Time Logs - Review personal time log history
- ✅ Update Task Status - Mark tasks as completed

## Database Schema

### Core Tables
- `users` - User accounts
- `roles` - User roles (Admin, Project Manager, Team Member)
- `permissions` - System permissions
- `user_roles` - Junction table for user-role mapping
- `projects` - Projects
- `milestones` - Project milestones
- `tasks` - Individual tasks
- `time_logs` - Time tracking entries

## API Endpoints

### Authentication
- `GET/POST /login` - User login
- `GET /logout` - User logout

### Admin Management
- `GET /admin/user-management` - List all users
- `POST /admin/user-management` - Create/update users
- `GET /admin/project-management` - List all projects
- `POST /admin/project-management` - Create/update projects

### Project Manager
- `GET /project-manager/task-management` - List tasks
- `POST /project-manager/task-management` - Manage tasks
- `GET /project-manager/reports` - View reports

### Team Member
- `GET /team-member/log-time` - Time logging form
- `POST /team-member/log-time` - Submit time log
- `GET /team-member/log-time?action=my-logs` - View time logs

## Security Features

- Password hashing with SHA-256 and salt
- Role-based access control (RBAC)
- Session-based authentication
- Auth filter for protected endpoints
- Input validation and error handling

## Performance Optimization

- HikariCP connection pooling
- Indexed database queries
- Efficient JSP caching
- Prepared statements for SQL injection prevention

## Future Enhancements

- [ ] REST API endpoints
- [ ] JWT token-based authentication
- [ ] Advanced reporting with charts
- [ ] Email notifications
- [ ] Integration with Jira/GitHub
- [ ] Mobile app support
- [ ] Real-time collaboration features
- [ ] Audit logging

## Troubleshooting

### Database Connection Issues
- Verify MySQL is running
- Check database credentials in `DBConnection.java`
- Ensure database exists: `CREATE DATABASE timetracker_db;`

### Login Failures
- Verify demo data was inserted
- Check password hashing in `PasswordUtil.java`
- Review application logs

### JSP Compilation Errors
- Ensure JSP JSTL libraries are in classpath
- Check JSP syntax
- Review servlet logs

## Contributing

1. Create a feature branch
2. Make your changes
3. Submit a pull request

## License

This project is open source and available under the MIT License.

## Support

For issues and questions, please create an issue in the repository.


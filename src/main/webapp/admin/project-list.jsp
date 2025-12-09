<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="org.timetracker.demo1.models.Project" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Project Management - Admin Panel</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f5f7fa;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 30px 20px;
        }

        .page-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
        }

        .page-header h1 {
            color: #333;
            font-size: 28px;
        }

        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 12px 25px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            font-weight: 600;
            text-decoration: none;
            display: inline-block;
        }

        .btn-primary:hover {
            opacity: 0.9;
        }

        .section {
            background: white;
            border-radius: 10px;
            padding: 25px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
        }

        .project-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 20px;
            margin-top: 20px;
        }

        .project-card {
            background: white;
            border: 1px solid #e0e0e0;
            border-radius: 8px;
            padding: 20px;
            transition: transform 0.3s, box-shadow 0.3s;
        }

        .project-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        }

        .project-card h3 {
            color: #333;
            margin-bottom: 10px;
            font-size: 18px;
        }

        .project-card p {
            color: #666;
            font-size: 13px;
            margin-bottom: 10px;
            line-height: 1.5;
        }

        .project-status {
            display: inline-block;
            padding: 4px 10px;
            border-radius: 3px;
            font-size: 12px;
            font-weight: 600;
            margin-bottom: 15px;
        }

        .status-active {
            background: #d4edda;
            color: #155724;
        }

        .status-completed {
            background: #cfe2ff;
            color: #084298;
        }

        .status-on-hold {
            background: #fff3cd;
            color: #664d03;
        }

        .project-dates {
            font-size: 12px;
            color: #999;
            margin-bottom: 15px;
        }

        .card-actions {
            display: flex;
            gap: 10px;
        }

        .btn-sm {
            padding: 6px 12px;
            font-size: 12px;
            border: none;
            border-radius: 3px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
        }

        .btn-edit {
            background: #3498db;
            color: white;
        }

        .btn-delete {
            background: #e74c3c;
            color: white;
        }

        .btn-sm:hover {
            opacity: 0.8;
        }

        .no-data {
            text-align: center;
            padding: 40px;
            color: #999;
        }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/header.jsp" />

    <div class="container">
        <div class="page-header">
            <h1>📋 Project Management</h1>
            <button class="btn-primary" onclick="location.href='<%= request.getContextPath() %>/admin/project-management?action=edit'">+ Create New Project</button>
        </div>

        <div class="section">
            <h2>All Projects</h2>

            <%
                List<Project> projects = (List<Project>) request.getAttribute("projects");
                if (projects != null && !projects.isEmpty()) {
            %>
                <div class="project-grid">
                    <%
                        for (Project project : projects) {
                    %>
                        <div class="project-card">
                            <h3><%= project.getProjectName() %></h3>
                            <span class="project-status status-<%= project.getStatus().toLowerCase() %>">
                                <%= project.getStatus() %>
                            </span>
                            <p><%= project.getDescription() != null ? project.getDescription() : "No description" %></p>
                            <div class="project-dates">
                                📅 <%= project.getStartDate() %> to <%= project.getEndDate() %>
                            </div>
                            <div class="card-actions">
                                <a href="<%= request.getContextPath() %>/admin/project-management?action=edit&projectId=<%= project.getProjectId() %>" class="btn-sm btn-edit">Edit</a>
                                <form method="POST" action="<%= request.getContextPath() %>/admin/project-management" style="display: inline;" onsubmit="return confirm('Are you sure?');">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="projectId" value="<%= project.getProjectId() %>">
                                    <button type="submit" class="btn-sm btn-delete">Delete</button>
                                </form>
                            </div>
                        </div>
                    <%
                        }
                    %>
                </div>
            <%
                } else {
            %>
                <div class="no-data">
                    <p>No projects found. <a href="<%= request.getContextPath() %>/admin/project-management?action=edit">Create one</a></p>
                </div>
            <%
                }
            %>
        </div>
    </div>
</body>
</html>


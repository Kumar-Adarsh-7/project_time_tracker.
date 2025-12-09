<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.timetracker.demo1.models.Project" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Project Edit - Admin Panel</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f5f7fa;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            padding: 30px 20px;
        }
        .page-header h1 {
            color: #333;
            font-size: 28px;
            margin-bottom: 30px;
        }
        .section {
            background: white;
            border-radius: 10px;
            padding: 30px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
        }
        .form-group {
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 8px;
            color: #333;
            font-weight: 600;
            font-size: 14px;
        }
        input[type="text"],
        input[type="date"],
        select,
        textarea {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
            font-family: inherit;
        }
        textarea {
            resize: vertical;
            min-height: 100px;
        }
        input:focus, select:focus, textarea:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 5px rgba(102, 126, 234, 0.1);
        }
        .button-group {
            display: flex;
            gap: 10px;
            margin-top: 30px;
        }
        button, .btn {
            padding: 12px 25px;
            border: none;
            border-radius: 5px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
        }
        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }
        .btn-secondary {
            background: #e0e0e0;
            color: #333;
        }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/header.jsp" />
    <div class="container">
        <div class="page-header">
            <h1>📋 Edit Project</h1>
        </div>
        <div class="section">
            <%
                Project project = (Project) request.getAttribute("project");
                String actionText = project != null ? "Update" : "Create";
            %>
            <form method="POST" action="<%= request.getContextPath() %>/admin/project-management">
                <input type="hidden" name="action" value="<%= project != null ? "update" : "create" %>">
                <% if (project != null) { %>
                    <input type="hidden" name="projectId" value="<%= project.getProjectId() %>">
                <% } %>

                <div class="form-group">
                    <label for="projectName">Project Name *</label>
                    <input type="text" id="projectName" name="projectName" value="<%= project != null ? project.getProjectName() : "" %>" required>
                </div>

                <div class="form-group">
                    <label for="description">Description</label>
                    <textarea id="description" name="description"><%= project != null && project.getDescription() != null ? project.getDescription() : "" %></textarea>
                </div>

                <div class="form-group">
                    <label for="startDate">Start Date *</label>
                    <input type="date" id="startDate" name="startDate" value="<%= project != null ? project.getStartDate() : "" %>" required>
                </div>

                <div class="form-group">
                    <label for="endDate">End Date *</label>
                    <input type="date" id="endDate" name="endDate" value="<%= project != null ? project.getEndDate() : "" %>" required>
                </div>

                <% if (project != null) { %>
                <div class="form-group">
                    <label for="status">Status *</label>
                    <select id="status" name="status" required>
                        <option value="ACTIVE" <%= "ACTIVE".equals(project.getStatus()) ? "selected" : "" %>>Active</option>
                        <option value="COMPLETED" <%= "COMPLETED".equals(project.getStatus()) ? "selected" : "" %>>Completed</option>
                        <option value="ON_HOLD" <%= "ON_HOLD".equals(project.getStatus()) ? "selected" : "" %>>On Hold</option>
                        <option value="CANCELLED" <%= "CANCELLED".equals(project.getStatus()) ? "selected" : "" %>>Cancelled</option>
                    </select>
                </div>
                <% } %>

                <div class="button-group">
                    <button type="submit" class="btn-primary"><%= actionText %> Project</button>
                    <a href="<%= request.getContextPath() %>/admin/project-management" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>


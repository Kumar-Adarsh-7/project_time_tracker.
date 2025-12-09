<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.timetracker.demo1.models.Task" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Task - Project Manager</title>
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
        input[type="number"],
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
            <h1>✓ Edit Task</h1>
        </div>
        <div class="section">
            <%
                Task task = (Task) request.getAttribute("task");
                String actionText = task != null ? "Update" : "Create";
            %>
            <form method="POST" action="<%= request.getContextPath() %>/project-manager/task-management">
                <input type="hidden" name="action" value="<%= task != null ? "update" : "create" %>">
                <% if (task != null) { %>
                    <input type="hidden" name="taskId" value="<%= task.getTaskId() %>">
                <% } %>

                <div class="form-group">
                    <label for="taskTitle">Task Title *</label>
                    <input type="text" id="taskTitle" name="taskTitle" value="<%= task != null ? task.getTaskTitle() : "" %>" required>
                </div>

                <div class="form-group">
                    <label for="description">Description</label>
                    <textarea id="description" name="description"><%= task != null && task.getDescription() != null ? task.getDescription() : "" %></textarea>
                </div>

                <div class="form-group">
                    <label for="deadline">Deadline *</label>
                    <input type="date" id="deadline" name="deadline" value="<%= task != null ? task.getDeadline() : "" %>" required>
                </div>

                <div class="form-group">
                    <label for="priority">Priority</label>
                    <select id="priority" name="priority">
                        <option value="LOW" <%= task != null && "LOW".equals(task.getPriority()) ? "selected" : "" %>>Low</option>
                        <option value="MEDIUM" <%= task != null && "MEDIUM".equals(task.getPriority()) ? "selected" : "" %>>Medium</option>
                        <option value="HIGH" <%= task != null && "HIGH".equals(task.getPriority()) ? "selected" : "" %>>High</option>
                        <option value="CRITICAL" <%= task != null && "CRITICAL".equals(task.getPriority()) ? "selected" : "" %>>Critical</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="estimatedHours">Estimated Hours</label>
                    <input type="number" id="estimatedHours" name="estimatedHours" min="0" step="0.5" value="<%= task != null && task.getEstimatedHours() != null ? task.getEstimatedHours() : "" %>">
                </div>

                <% if (task != null) { %>
                <div class="form-group">
                    <label for="status">Status</label>
                    <select id="status" name="status">
                        <option value="TODO" <%= "TODO".equals(task.getStatus()) ? "selected" : "" %>>To Do</option>
                        <option value="IN_PROGRESS" <%= "IN_PROGRESS".equals(task.getStatus()) ? "selected" : "" %>>In Progress</option>
                        <option value="COMPLETED" <%= "COMPLETED".equals(task.getStatus()) ? "selected" : "" %>>Completed</option>
                        <option value="BLOCKED" <%= "BLOCKED".equals(task.getStatus()) ? "selected" : "" %>>Blocked</option>
                    </select>
                </div>
                <% } %>

                <div class="button-group">
                    <button type="submit" class="btn-primary"><%= actionText %> Task</button>
                    <a href="<%= request.getContextPath() %>/project-manager/task-management" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>


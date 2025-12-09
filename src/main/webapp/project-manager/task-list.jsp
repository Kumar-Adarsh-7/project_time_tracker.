<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="org.timetracker.demo1.models.Task" %>
<%@ page import="org.timetracker.demo1.models.Project" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Task Management - Project Manager</title>
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

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th {
            background: #f5f7fa;
            color: #333;
            padding: 15px;
            text-align: left;
            font-weight: 600;
            font-size: 14px;
            border-bottom: 2px solid #e0e0e0;
        }

        td {
            padding: 15px;
            border-bottom: 1px solid #e0e0e0;
            font-size: 14px;
            color: #666;
        }

        tr:hover {
            background: #f9f9f9;
        }

        .priority-high { color: #e74c3c; font-weight: 600; }
        .priority-medium { color: #f39c12; font-weight: 600; }
        .priority-low { color: #27ae60; font-weight: 600; }

        .status-todo { color: #95a5a6; }
        .status-in-progress { color: #f39c12; font-weight: 600; }
        .status-completed { color: #27ae60; font-weight: 600; }

        .action-btns {
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
            <h1>✓ Task Management</h1>
            <button class="btn-primary" onclick="location.href='<%= request.getContextPath() %>/project-manager/task-management?action=edit'">+ Create New Task</button>
        </div>

        <div class="section">
            <h2>All Tasks</h2>

            <%
                List<Task> tasks = (List<Task>) request.getAttribute("tasks");
                if (tasks != null && !tasks.isEmpty()) {
            %>
                <table>
                    <thead>
                        <tr>
                            <th>Task Title</th>
                            <th>Priority</th>
                            <th>Status</th>
                            <th>Deadline</th>
                            <th>Estimated Hours</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            for (Task task : tasks) {
                        %>
                            <tr>
                                <td><strong><%= task.getTaskTitle() %></strong></td>
                                <td><span class="priority-<%= task.getPriority().toLowerCase() %>"><%= task.getPriority() %></span></td>
                                <td><span class="status-<%= task.getStatus().toLowerCase().replace("_", "-") %>"><%= task.getStatus() %></span></td>
                                <td><%= task.getDeadline() %></td>
                                <td><%= task.getEstimatedHours() != null ? task.getEstimatedHours() + " hrs" : "N/A" %></td>
                                <td>
                                    <div class="action-btns">
                                        <a href="<%= request.getContextPath() %>/project-manager/task-management?action=edit&taskId=<%= task.getTaskId() %>" class="btn-sm btn-edit">Edit</a>
                                        <form method="POST" action="<%= request.getContextPath() %>/project-manager/task-management" style="display: inline;" onsubmit="return confirm('Are you sure?');">
                                            <input type="hidden" name="action" value="delete">
                                            <input type="hidden" name="taskId" value="<%= task.getTaskId() %>">
                                            <button type="submit" class="btn-sm btn-delete">Delete</button>
                                        </form>
                                    </div>
                                </td>
                            </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            <%
                } else {
            %>
                <div class="no-data">
                    <p>No tasks found. <a href="<%= request.getContextPath() %>/project-manager/task-management?action=edit">Create one</a></p>
                </div>
            <%
                }
            %>
        </div>
    </div>
</body>
</html>


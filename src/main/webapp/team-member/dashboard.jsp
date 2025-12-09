<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="org.timetracker.demo1.models.Task" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Team Member Dashboard - Time Tracker</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f5f7fa;
        }

        .container {
            max-width: 1400px;
            margin: 0 auto;
            padding: 30px 20px;
        }

        .page-header {
            margin-bottom: 30px;
        }

        .page-header h1 {
            color: #333;
            font-size: 32px;
            margin-bottom: 10px;
        }

        .dashboard-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
            gap: 20px;
            margin-bottom: 40px;
        }

        .card {
            background: white;
            border-radius: 10px;
            padding: 25px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
            transition: transform 0.3s, box-shadow 0.3s;
        }

        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.12);
        }

        .card-icon {
            font-size: 40px;
            margin-bottom: 15px;
        }

        .card h3 {
            color: #333;
            font-size: 18px;
            margin-bottom: 10px;
        }

        .card p {
            color: #666;
            font-size: 14px;
            line-height: 1.6;
            margin-bottom: 15px;
        }

        .card-link {
            display: inline-block;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 10px 20px;
            border-radius: 5px;
            text-decoration: none;
            font-size: 14px;
            font-weight: 600;
            transition: opacity 0.3s;
        }

        .card-link:hover {
            opacity: 0.9;
        }

        .section {
            background: white;
            border-radius: 10px;
            padding: 25px;
            margin-bottom: 20px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
        }

        .section h2 {
            color: #333;
            font-size: 24px;
            margin-bottom: 20px;
            border-bottom: 2px solid #667eea;
            padding-bottom: 15px;
        }

        .task-list {
            list-style: none;
        }

        .task-item {
            padding: 15px;
            border-bottom: 1px solid #e0e0e0;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .task-item:last-child {
            border-bottom: none;
        }

        .task-info h4 {
            color: #333;
            margin-bottom: 5px;
        }

        .task-info p {
            color: #999;
            font-size: 13px;
        }

        .task-status {
            display: inline-block;
            padding: 4px 10px;
            border-radius: 3px;
            font-size: 12px;
            font-weight: 600;
        }

        .status-in-progress {
            background: #fff3cd;
            color: #664d03;
        }

        .status-todo {
            background: #cfe2ff;
            color: #084298;
        }

        .no-tasks {
            text-align: center;
            padding: 30px;
            color: #999;
        }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/header.jsp" />

    <div class="container">
        <div class="page-header">
            <h1>👨‍💻 Team Member Dashboard</h1>
            <p>Log your time, update task status, and track your progress.</p>
        </div>

        <div class="dashboard-grid">
            <div class="card">
                <div class="card-icon">⏱️</div>
                <h3>Log Time</h3>
                <p>Record hours spent on your assigned tasks.</p>
                <a href="<%= request.getContextPath() %>/team-member/log-time" class="card-link">Log Time</a>
            </div>

            <div class="card">
                <div class="card-icon">📋</div>
                <h3>My Time Logs</h3>
                <p>View and manage your time log entries.</p>
                <a href="<%= request.getContextPath() %>/team-member/log-time?action=my-logs" class="card-link">View Logs</a>
            </div>

            <div class="card">
                <div class="card-icon">✓</div>
                <h3>My Tasks</h3>
                <p>Check your assigned tasks and update their status.</p>
                <a href="<%= request.getContextPath() %>/team-member/tasks" class="card-link">View Tasks</a>
            </div>
        </div>

        <div class="section">
            <h2>My Assigned Tasks</h2>
            <%
                List<Task> tasks = (List<Task>) request.getAttribute("tasks");
                if (tasks != null && !tasks.isEmpty()) {
            %>
                <ul class="task-list">
                    <%
                        for (Task task : tasks) {
                    %>
                        <li class="task-item">
                            <div class="task-info">
                                <h4><%= task.getTaskTitle() %></h4>
                                <p>Deadline: <%= task.getDeadline() %> | Status: <%= task.getStatus() %></p>
                            </div>
                            <span class="task-status status-<%= task.getStatus().toLowerCase().replace("_", "-") %>">
                                <%= task.getStatus() %>
                            </span>
                        </li>
                    <%
                        }
                    %>
                </ul>
            <%
                } else {
            %>
                <div class="no-tasks">
                    <p>No tasks assigned to you yet.</p>
                </div>
            <%
                }
            %>
        </div>
    </div>
</body>
</html>


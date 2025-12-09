<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Project Manager Dashboard - Time Tracker</title>
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
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/header.jsp" />

    <div class="container">
        <div class="page-header">
            <h1>👔 Project Manager Dashboard</h1>
            <p>Manage tasks, monitor progress, and track team productivity.</p>
        </div>

        <div class="dashboard-grid">
            <div class="card">
                <div class="card-icon">✓</div>
                <h3>Task Management</h3>
                <p>Create tasks, assign to team members, and track progress.</p>
                <a href="<%= request.getContextPath() %>/project-manager/task-management" class="card-link">Manage Tasks</a>
            </div>

            <div class="card">
                <div class="card-icon">⏱️</div>
                <h3>Log Time</h3>
                <p>Log time spent on tasks and review team time logs.</p>
                <a href="<%= request.getContextPath() %>/project-manager/log-time" class="card-link">Log Time</a>
            </div>

            <div class="card">
                <div class="card-icon">📊</div>
                <h3>Reports</h3>
                <p>View project progress and time allocation reports.</p>
                <a href="<%= request.getContextPath() %>/project-manager/reports" class="card-link">View Reports</a>
            </div>

            <div class="card">
                <div class="card-icon">📈</div>
                <h3>Progress Tracking</h3>
                <p>Monitor project milestones and team progress.</p>
                <a href="<%= request.getContextPath() %>/project-manager/progress" class="card-link">View Progress</a>
            </div>
        </div>
    </div>
</body>
</html>


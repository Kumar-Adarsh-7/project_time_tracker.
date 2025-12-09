<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Time Tracker</title>
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

        .page-header p {
            color: #666;
            font-size: 16px;
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

        .stats-row {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 15px;
        }

        .stat-box {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 20px;
            border-radius: 8px;
            text-align: center;
        }

        .stat-box .value {
            font-size: 32px;
            font-weight: 700;
            margin-bottom: 5px;
        }

        .stat-box .label {
            font-size: 14px;
            opacity: 0.9;
        }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/header.jsp" />

    <div class="container">
        <div class="page-header">
            <h1>👨‍💼 Admin Dashboard</h1>
            <p>Welcome! Manage users, projects, and monitor system activity.</p>
        </div>

        <div class="dashboard-grid">
            <div class="card">
                <div class="card-icon">👥</div>
                <h3>User Management</h3>
                <p>Manage user accounts, assign roles, and control access permissions.</p>
                <a href="<%= request.getContextPath() %>/admin/user-management" class="card-link">Manage Users</a>
            </div>

            <div class="card">
                <div class="card-icon">📋</div>
                <h3>Project Setup</h3>
                <p>Create new projects, define milestones, and manage project details.</p>
                <a href="<%= request.getContextPath() %>/admin/project-management" class="card-link">Manage Projects</a>
            </div>

            <div class="card">
                <div class="card-icon">📊</div>
                <h3>Reports</h3>
                <p>View comprehensive reports on time spent per project and user.</p>
                <a href="<%= request.getContextPath() %>/admin/reports" class="card-link">View Reports</a>
            </div>

            <div class="card">
                <div class="card-icon">⚙️</div>
                <h3>System Settings</h3>
                <p>Configure system settings and integration options.</p>
                <a href="<%= request.getContextPath() %>/admin/settings" class="card-link">Settings</a>
            </div>
        </div>

        <div class="section">
            <h2>Quick Stats</h2>
            <div class="stats-row">
                <div class="stat-box">
                    <div class="value">0</div>
                    <div class="label">Total Users</div>
                </div>
                <div class="stat-box">
                    <div class="value">0</div>
                    <div class="label">Active Projects</div>
                </div>
                <div class="stat-box">
                    <div class="value">0</div>
                    <div class="label">Total Tasks</div>
                </div>
                <div class="stat-box">
                    <div class="value">0</div>
                    <div class="label">Total Hours Logged</div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>


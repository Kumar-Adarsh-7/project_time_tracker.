<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="org.timetracker.demo1.models.Project" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reports - Admin Panel</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f5f7fa;
        }
        .container {
            max-width: 1000px;
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
        .report-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
        }
        .report-card {
            border: 1px solid #e0e0e0;
            border-radius: 8px;
            padding: 20px;
            text-align: center;
            cursor: pointer;
            transition: all 0.3s;
        }
        .report-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        }
        .report-icon {
            font-size: 40px;
            margin-bottom: 10px;
        }
        .report-card h3 {
            color: #333;
            margin-bottom: 10px;
        }
        .report-card p {
            color: #666;
            font-size: 13px;
            margin-bottom: 15px;
        }
        .btn {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 13px;
            text-decoration: none;
            display: inline-block;
        }
        .btn:hover {
            opacity: 0.9;
        }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/header.jsp" />
    <div class="container">
        <div class="page-header">
            <h1>📊 Reports</h1>
        </div>
        <div class="section">
            <h2>Select Report Type</h2>
            <div class="report-grid" style="margin-top: 30px;">
                <div class="report-card">
                    <div class="report-icon">📈</div>
                    <h3>Project Time Report</h3>
                    <p>View total hours logged per project</p>
                    <a href="<%= request.getContextPath() %>/admin/reports?type=project-time" class="btn">Generate Report</a>
                </div>

                <div class="report-card">
                    <div class="report-icon">📋</div>
                    <h3>Task Time Report</h3>
                    <p>View hours logged per task</p>
                    <a href="<%= request.getContextPath() %>/admin/reports?type=task-time" class="btn">Generate Report</a>
                </div>

                <div class="report-card">
                    <div class="report-icon">👤</div>
                    <h3>User Time Report</h3>
                    <p>View total hours logged by each user</p>
                    <a href="<%= request.getContextPath() %>/admin/reports?type=user-time" class="btn">Generate Report</a>
                </div>

                <div class="report-card">
                    <div class="report-icon">📅</div>
                    <h3>Date Range Report</h3>
                    <p>View logs within a specific date range</p>
                    <a href="<%= request.getContextPath() %>/admin/reports?type=date-range" class="btn">Generate Report</a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>


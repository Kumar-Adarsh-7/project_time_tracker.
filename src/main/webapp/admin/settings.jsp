<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Settings - Admin Panel</title>
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
        .section h2 {
            color: #333;
            font-size: 20px;
            margin-bottom: 20px;
            border-bottom: 2px solid #667eea;
            padding-bottom: 10px;
        }
        .setting-item {
            padding: 20px 0;
            border-bottom: 1px solid #e0e0e0;
        }
        .setting-item:last-child {
            border-bottom: none;
        }
        .setting-item h3 {
            color: #333;
            font-size: 16px;
            margin-bottom: 5px;
        }
        .setting-item p {
            color: #666;
            font-size: 14px;
        }
        .info-box {
            background: #f5f7fa;
            border-left: 4px solid #667eea;
            padding: 15px;
            border-radius: 5px;
            margin-top: 10px;
        }
        .info-box strong {
            display: block;
            margin-bottom: 5px;
        }
        .info-box code {
            background: white;
            padding: 2px 5px;
            border-radius: 3px;
            font-family: monospace;
        }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/header.jsp" />
    <div class="container">
        <div class="page-header">
            <h1>⚙️ System Settings</h1>
        </div>
        <div class="section">
            <h2>Application Configuration</h2>

            <div class="setting-item">
                <h3>Database Connection</h3>
                <p>Manage database connection settings</p>
                <div class="info-box">
                    <strong>Current Database:</strong>
                    <code>timetracker_db @ localhost:3306</code>
                </div>
            </div>

            <div class="setting-item">
                <h3>Application Version</h3>
                <p>Time Tracker v1.0</p>
                <div class="info-box">
                    <strong>Build Date:</strong> December 2024
                </div>
            </div>

            <div class="setting-item">
                <h3>Session Configuration</h3>
                <p>Session timeout and security settings</p>
                <div class="info-box">
                    <strong>Session Timeout:</strong> 30 minutes (configurable)
                </div>
            </div>

            <div class="setting-item">
                <h3>Integration Options</h3>
                <p>Third-party integrations (coming soon)</p>
                <ul style="margin-top: 10px; margin-left: 20px; color: #666;">
                    <li>Jira Integration</li>
                    <li>GitHub Integration</li>
                    <li>Email Notifications</li>
                </ul>
            </div>
        </div>
    </div>
</body>
</html>


<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Time Tracker - Project Management</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }

        .container {
            text-align: center;
            background: white;
            border-radius: 15px;
            padding: 50px;
            box-shadow: 0 10px 40px rgba(0, 0, 0, 0.2);
            max-width: 600px;
            width: 100%;
        }

        .container h1 {
            font-size: 48px;
            color: #333;
            margin-bottom: 20px;
        }

        .container p {
            color: #666;
            font-size: 18px;
            margin-bottom: 30px;
            line-height: 1.6;
        }

        .features {
            text-align: left;
            margin: 30px 0;
            background: #f5f7fa;
            padding: 20px;
            border-radius: 10px;
        }

        .features h3 {
            color: #333;
            margin-bottom: 15px;
        }

        .features ul {
            list-style: none;
            color: #666;
        }

        .features li {
            padding: 8px 0;
            border-bottom: 1px solid #e0e0e0;
        }

        .features li:last-child {
            border-bottom: none;
        }

        .features li:before {
            content: "✓ ";
            color: #667eea;
            font-weight: bold;
            margin-right: 8px;
        }

        .cta-buttons {
            margin-top: 40px;
            display: flex;
            gap: 15px;
            justify-content: center;
            flex-wrap: wrap;
        }

        .btn {
            padding: 14px 30px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            transition: all 0.3s;
        }

        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }

        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
        }

        .btn-secondary {
            background: #e0e0e0;
            color: #333;
        }

        .btn-secondary:hover {
            background: #d0d0d0;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>⏱️ Time Tracker</h1>
        <p>Comprehensive project management and time tracking system for teams</p>

        <div class="features">
            <h3>Features</h3>
            <ul>
                <li>Admin Dashboard - User and project management</li>
                <li>Project Manager Dashboard - Task assignment and monitoring</li>
                <li>Team Member Dashboard - Time logging and task updates</li>
                <li>Real-time time tracking and reporting</li>
                <li>Comprehensive analytics and insights</li>
                <li>Role-based access control</li>
            </ul>
        </div>

        <div class="cta-buttons">
            <a href="<%= request.getContextPath() %>/login" class="btn btn-primary">Login</a>
        </div>

        <p style="margin-top: 40px; font-size: 14px; color: #999;">
            Demo credentials available on login page
        </p>
    </div>
</body>
</html>
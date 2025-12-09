<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Time Tracker - Login</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            /* Dark Mode Gradient Background */
            background: linear-gradient(135deg, #111135 0%, #2a1a50 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            color: #ffffff;
        }

        .login-container {
            /* Glassmorphism Dark Card */
            background: rgba(30, 30, 70, 0.7);
            backdrop-filter: blur(15px);
            border: 1px solid rgba(255, 255, 255, 0.1);
            border-radius: 20px;
            /* Glowing Purple Shadow */
            box-shadow: 0 0 40px rgba(100, 50, 255, 0.2);
            padding: 40px;
            width: 100%;
            max-width: 400px;
        }

        .login-header {
            text-align: center;
            margin-bottom: 30px;
        }

        .login-header h1 {
            color: #ffffff;
            font-size: 28px;
            margin-bottom: 10px;
            font-weight: 800;
        }

        .login-header p {
            color: #b0b0d0; /* Muted light purple/gray */
            font-size: 14px;
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin-bottom: 8px;
            color: #d0d0e0;
            font-weight: 500;
            font-size: 14px;
        }

        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 12px;
            /* Dark Input Fields */
            background: rgba(20, 20, 45, 0.6);
            border: 1px solid rgba(100, 100, 200, 0.3);
            color: #ffffff;
            border-radius: 8px;
            font-size: 14px;
            transition: all 0.3s ease;
        }

        input[type="text"]:focus,
        input[type="password"]:focus {
            outline: none;
            border-color: #8c52ff;
            box-shadow: 0 0 10px rgba(140, 82, 255, 0.3);
            background: rgba(20, 20, 45, 0.9);
        }

        .error-message {
            color: #ff6b6b;
            font-size: 14px;
            margin-bottom: 20px;
            padding: 12px;
            /* Darker Red Background for Error */
            background-color: rgba(231, 76, 60, 0.15);
            border-radius: 5px;
            border-left: 4px solid #ff6b6b;
            border: 1px solid rgba(255, 107, 107, 0.2);
        }

        button {
            width: 100%;
            padding: 12px;
            /* Vibrant Gradient Button */
            background: linear-gradient(135deg, #6b46c1 0%, #8e2de2 100%);
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: transform 0.2s, box-shadow 0.2s;
            box-shadow: 0 0 20px rgba(130, 60, 255, 0.4);
        }

        button:hover {
            transform: translateY(-2px);
            box-shadow: 0 0 30px rgba(130, 60, 255, 0.7);
        }

        button:active {
            transform: translateY(0);
        }

        /* Styling for the Sign Up link button */
        .btn-outline {
            display: inline-block;
            width: 100%;
            padding: 12px;
            background: transparent;
            color: #ffffff;
            border: 2px solid #6b46c1;
            border-radius: 8px;
            text-decoration: none;
            font-weight: 600;
            transition: all 0.2s;
            text-align: center;
        }

        .btn-outline:hover {
            background: rgba(107, 70, 193, 0.2);
            box-shadow: 0 0 15px rgba(130, 60, 255, 0.3);
        }

        .demo-credentials {
            margin-top: 25px;
            padding-top: 20px;
            border-top: 1px solid rgba(255, 255, 255, 0.1);
            text-align: center;
            color: #8080a0;
            font-size: 13px;
        }

        .demo-credentials p {
            margin-bottom: 8px;
        }

        .demo-credentials code {
            background: rgba(0, 0, 0, 0.3);
            padding: 3px 8px;
            border-radius: 4px;
            font-family: monospace;
            color: #d0d0e0;
            border: 1px solid rgba(255, 255, 255, 0.05);
        }
    </style>
</head>
<body>
<div class="login-container">
    <div class="login-header">
        <h1>⏱️ Time Tracker</h1>
        <p>Project Management System</p>
    </div>

    <% if (request.getAttribute("error") != null) { %>
    <div class="error-message">
        <%= request.getAttribute("error") %>
    </div>
    <% } %>

    <form method="POST" action="<%= request.getContextPath() %>/login">
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" required autofocus>
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required>
        </div>

        <button type="submit">Login</button>
    </form>

    <div style="margin-top: 20px; text-align: center;">
        <p style="color: #b0b0d0; font-size: 14px; margin-bottom: 10px;">Don't have an account?</p>
        <a href="<%= request.getContextPath() %>/signup.jsp" class="btn-outline">Sign Up</a>
    </div>

    <div class="demo-credentials">
        <p><strong>Demo Credentials:</strong></p>
        <p>Admin: <code>admin</code> / <code>admin123</code></p>
        <p>PM: <code>pm_user</code> / <code>pm123</code></p>
        <p>Team: <code>team_user</code> / <code>team123</code></p>
    </div>
</div>
</body>
</html>
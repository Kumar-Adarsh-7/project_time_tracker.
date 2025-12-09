<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.timetracker.demo1.models.TimeLog" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Time Log - Time Tracker</title>
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
        input[type="number"],
        input[type="date"],
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
        input:focus, textarea:focus {
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
            <h1>⏱️ Edit Time Log</h1>
        </div>
        <div class="section">
            <%
                TimeLog timeLog = (TimeLog) request.getAttribute("timeLog");
            %>
            <form method="POST" action="<%= request.getContextPath() %>/team-member/log-time">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="timeLogId" value="<%= timeLog != null ? timeLog.getTimeLogId() : "" %>">

                <div class="form-group">
                    <label for="hoursSpent">Hours Spent *</label>
                    <input type="number" id="hoursSpent" name="hoursSpent" min="0.25" step="0.25" value="<%= timeLog != null ? timeLog.getHoursSpent() : "" %>" required>
                </div>

                <div class="form-group">
                    <label for="description">Description</label>
                    <textarea id="description" name="description"><%= timeLog != null && timeLog.getDescription() != null ? timeLog.getDescription() : "" %></textarea>
                </div>

                <div class="button-group">
                    <button type="submit" class="btn-primary">Update Time Log</button>
                    <a href="<%= request.getContextPath() %>/team-member/log-time?action=my-logs" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>


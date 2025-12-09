<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<header class="navbar">
    <div class="navbar-container">
        <div class="navbar-brand">
            <a href="<%= request.getContextPath() %>/">⏱️ Time Tracker</a>
        </div>

        <nav class="navbar-nav">
            <% String username = (String) session.getAttribute("username"); %>
            <% java.util.List<org.timetracker.demo1.models.Role> roles =
                (java.util.List<org.timetracker.demo1.models.Role>) session.getAttribute("roles"); %>

            <% if (roles != null && !roles.isEmpty()) {
                String roleName = roles.get(0).getRoleName();
            %>
                <% if ("ADMIN".equals(roleName)) { %>
                    <a href="<%= request.getContextPath() %>/admin/dashboard.jsp">Dashboard</a>
                    <a href="<%= request.getContextPath() %>/admin/user-management">Users</a>
                    <a href="<%= request.getContextPath() %>/admin/project-management">Projects</a>
                    <a href="<%= request.getContextPath() %>/admin/reports">Reports</a>
                <% } else if ("PROJECT_MANAGER".equals(roleName)) { %>
                    <a href="<%= request.getContextPath() %>/project-manager/dashboard.jsp">Dashboard</a>
                    <a href="<%= request.getContextPath() %>/project-manager/task-management">Tasks</a>
                    <a href="<%= request.getContextPath() %>/project-manager/log-time">Log Time</a>
                    <a href="<%= request.getContextPath() %>/project-manager/reports">Reports</a>
                <% } else if ("TEAM_MEMBER".equals(roleName)) { %>
                    <a href="<%= request.getContextPath() %>/team-member/dashboard.jsp">Dashboard</a>
                    <a href="<%= request.getContextPath() %>/team-member/log-time">Log Time</a>
                    <a href="<%= request.getContextPath() %>/team-member/log-time?action=my-logs">My Logs</a>
                <% } %>
            <% } %>
        </nav>

        <div class="navbar-user">
            <span class="user-info">
                <% if (username != null) { %>
                    Welcome, <strong><%= username %></strong>
                <% } %>
            </span>
            <a href="<%= request.getContextPath() %>/logout" class="logout-btn">Logout</a>
        </div>
    </div>
</header>

<style>
.navbar {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: white;
    padding: 0;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    position: sticky;
    top: 0;
    z-index: 1000;
}

.navbar-container {
    max-width: 1400px;
    margin: 0 auto;
    padding: 15px 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.navbar-brand a {
    font-size: 24px;
    font-weight: 700;
    color: white;
    text-decoration: none;
    display: flex;
    align-items: center;
    gap: 10px;
}

.navbar-nav {
    display: flex;
    gap: 25px;
    flex: 1;
    margin-left: 50px;
}

.navbar-nav a {
    color: rgba(255, 255, 255, 0.9);
    text-decoration: none;
    font-size: 15px;
    font-weight: 500;
    transition: color 0.3s;
    padding: 5px 10px;
    border-radius: 3px;
}

.navbar-nav a:hover {
    color: white;
    background-color: rgba(255, 255, 255, 0.1);
}

.navbar-user {
    display: flex;
    align-items: center;
    gap: 20px;
}

.user-info {
    font-size: 14px;
    white-space: nowrap;
}

.logout-btn {
    background-color: rgba(255, 255, 255, 0.2);
    color: white;
    padding: 8px 15px;
    border-radius: 5px;
    text-decoration: none;
    font-size: 14px;
    transition: background-color 0.3s;
}

.logout-btn:hover {
    background-color: rgba(255, 255, 255, 0.3);
}
</style>


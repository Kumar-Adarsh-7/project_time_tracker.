package org.timetracker.demo1.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.timetracker.demo1.dao.RoleDAO;
import org.timetracker.demo1.dao.UserDAO;
import org.timetracker.demo1.models.Role;
import org.timetracker.demo1.models.User;
import org.timetracker.demo1.security.PasswordUtil;

import java.io.IOException;
import java.util.List;

/**
 * Servlet for user login
 */
@WebServlet(name = "loginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);
    private UserDAO userDAO;
    private RoleDAO roleDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
        roleDAO = new RoleDAO();
        logger.info("LoginServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("error", "Username and password are required");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        try {
            User user = userDAO.getUserByUsername(username);

            if (user != null && user.isActive() && PasswordUtil.verifyPassword(password, user.getPasswordHash())) {
                logger.info("User logged in successfully: {}", username);

                // Create session
                HttpSession session = request.getSession();
                session.setAttribute("userId", user.getUserId());
                session.setAttribute("username", user.getUsername());
                session.setAttribute("userEmail", user.getEmail());
                session.setAttribute("fullName", user.getFullName());

                // Get user roles
                List<Role> roles = roleDAO.getRolesByUserId(user.getUserId());
                session.setAttribute("roles", roles);

                // Redirect based on role
                if (!roles.isEmpty()) {
                    String roleName = roles.get(0).getRoleName();
                    if ("ADMIN".equals(roleName)) {
                        response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp");
                    } else if ("PROJECT_MANAGER".equals(roleName)) {
                        response.sendRedirect(request.getContextPath() + "/project-manager/dashboard.jsp");
                    } else {
                        response.sendRedirect(request.getContextPath() + "/team-member/dashboard.jsp");
                    }
                } else {
                    response.sendRedirect(request.getContextPath() + "/index.jsp");
                }
            } else {
                logger.warn("Failed login attempt for user: {}", username);
                request.setAttribute("error", "Invalid username or password");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            logger.error("Error during login", e);
            request.setAttribute("error", "An error occurred during login");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}


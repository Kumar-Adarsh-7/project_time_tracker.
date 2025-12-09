package org.timetracker.demo1.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
 * Servlet for user management (Admin)
 */
@WebServlet(name = "userManagementServlet", value = "/admin/user-management")
public class UserManagementServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(UserManagementServlet.class);
    private UserDAO userDAO;
    private RoleDAO roleDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
        roleDAO = new RoleDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("list".equals(action)) {
                List<User> users = userDAO.getAllUsers();
                request.setAttribute("users", users);
                request.getRequestDispatcher("/admin/user-list.jsp").forward(request, response);
            } else if ("edit".equals(action)) {
                int userId = Integer.parseInt(request.getParameter("userId"));
                User user = userDAO.getUserById(userId);
                List<Role> allRoles = roleDAO.getAllRoles();
                List<Role> userRoles = roleDAO.getRolesByUserId(userId);
                request.setAttribute("user", user);
                request.setAttribute("allRoles", allRoles);
                request.setAttribute("userRoles", userRoles);
                request.getRequestDispatcher("/admin/user-edit.jsp").forward(request, response);
            } else {
                List<User> users = userDAO.getAllUsers();
                request.setAttribute("users", users);
                request.getRequestDispatcher("/admin/user-list.jsp").forward(request, response);
            }
        } catch (Exception e) {
            logger.error("Error in user management", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("create".equals(action)) {
                createUser(request, response);
            } else if ("update".equals(action)) {
                updateUser(request, response);
            } else if ("delete".equals(action)) {
                deleteUser(request, response);
            } else if ("assign-role".equals(action)) {
                assignRole(request, response);
            } else if ("remove-role".equals(action)) {
                removeRole(request, response);
            }
        } catch (Exception e) {
            logger.error("Error processing user management action", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void createUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        User user = new User(username, email, PasswordUtil.hashPassword(password), firstName, lastName);
        if (userDAO.createUser(user)) {
            logger.info("User created: {}", username);
            response.sendRedirect(request.getContextPath() + "/admin/user-management?action=list");
        } else {
            request.setAttribute("error", "Failed to create user");
            doGet(request, response);
        }
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        User user = userDAO.getUserById(userId);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        if (userDAO.updateUser(user)) {
            logger.info("User updated: {}", user.getUsername());
            response.sendRedirect(request.getContextPath() + "/admin/user-management?action=list");
        } else {
            request.setAttribute("error", "Failed to update user");
            doGet(request, response);
        }
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        if (userDAO.deleteUser(userId)) {
            logger.info("User deleted: {}", userId);
            response.sendRedirect(request.getContextPath() + "/admin/user-management?action=list");
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void assignRole(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        int roleId = Integer.parseInt(request.getParameter("roleId"));

        if (roleDAO.assignRoleToUser(userId, roleId)) {
            logger.info("Role {} assigned to user {}", roleId, userId);
            response.sendRedirect(request.getContextPath() + "/admin/user-management?action=edit&userId=" + userId);
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void removeRole(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        int roleId = Integer.parseInt(request.getParameter("roleId"));

        if (roleDAO.removeRoleFromUser(userId, roleId)) {
            logger.info("Role {} removed from user {}", roleId, userId);
            response.sendRedirect(request.getContextPath() + "/admin/user-management?action=edit&userId=" + userId);
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}


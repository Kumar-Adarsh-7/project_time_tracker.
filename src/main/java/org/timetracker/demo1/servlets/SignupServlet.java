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

/**
 * Servlet for user registration/signup
 */
@WebServlet(name = "signupServlet", value = "/signup")
public class SignupServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(SignupServlet.class);
    private UserDAO userDAO;
    private RoleDAO roleDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
        roleDAO = new RoleDAO();
        logger.info("SignupServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Redirect to signup page
        response.sendRedirect(request.getContextPath() + "/signup.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String roleName = request.getParameter("role");

        // Validation
        if (username == null || username.trim().isEmpty()) {
            request.setAttribute("error", "Username is required");
            request.getRequestDispatcher("/signup.jsp").forward(request, response);
            return;
        }

        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("error", "Email is required");
            request.getRequestDispatcher("/signup.jsp").forward(request, response);
            return;
        }

        if (password == null || password.isEmpty()) {
            request.setAttribute("error", "Password is required");
            request.getRequestDispatcher("/signup.jsp").forward(request, response);
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match");
            request.getRequestDispatcher("/signup.jsp").forward(request, response);
            return;
        }

        if (password.length() < 6) {
            request.setAttribute("error", "Password must be at least 6 characters");
            request.getRequestDispatcher("/signup.jsp").forward(request, response);
            return;
        }

        if (roleName == null || roleName.trim().isEmpty()) {
            request.setAttribute("error", "Role is required");
            request.getRequestDispatcher("/signup.jsp").forward(request, response);
            return;
        }

        // Check if username already exists
        User existingUser = userDAO.getUserByUsername(username.trim());
        if (existingUser != null) {
            request.setAttribute("error", "Username already exists");
            request.getRequestDispatcher("/signup.jsp").forward(request, response);
            return;
        }

        // Check if email already exists
        existingUser = userDAO.getUserByEmail(email.trim());
        if (existingUser != null) {
            request.setAttribute("error", "Email already exists");
            request.getRequestDispatcher("/signup.jsp").forward(request, response);
            return;
        }

        try {
            // Create new user with plain text password (as per system requirement)
            User newUser = new User(
                    username.trim(),
                    email.trim(),
                    PasswordUtil.hashPassword(password),
                    firstName != null ? firstName.trim() : null,
                    lastName != null ? lastName.trim() : null
            );
            newUser.setActive(true);

            // Save user to database
            if (userDAO.createUser(newUser)) {
                logger.info("New user created: {}", username);

                // Get the role ID and assign it to the user
                Role role = roleDAO.getRoleByName(roleName);
                if (role != null) {
                    roleDAO.assignRoleToUser(newUser.getUserId(), role.getRoleId());
                    logger.info("Role {} assigned to user {}", roleName, username);
                }

                // Redirect to login with success message
                request.setAttribute("success", "Account created successfully! Please login with your credentials.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Failed to create account. Please try again.");
                request.getRequestDispatcher("/signup.jsp").forward(request, response);
            }
        } catch (Exception e) {
            logger.error("Error during signup", e);
            request.setAttribute("error", "An error occurred during signup: " + e.getMessage());
            request.getRequestDispatcher("/signup.jsp").forward(request, response);
        }
    }
}


package org.timetracker.demo1.security;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Authentication filter to check if user is logged in
 */
@WebFilter(urlPatterns = {"/admin/*", "/project-manager/*", "/team-member/*", "/api/*"})
public class AuthFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("AuthFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String requestPath = httpRequest.getRequestURI();

        // Check if user is logged in
        if (session != null && session.getAttribute("userId") != null) {
            logger.debug("User authenticated: {}", session.getAttribute("username"));
            chain.doFilter(request, response);
        } else {
            logger.warn("Unauthorized access attempt to: {}", requestPath);
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
        }
    }

    @Override
    public void destroy() {
        logger.info("AuthFilter destroyed");
    }
}


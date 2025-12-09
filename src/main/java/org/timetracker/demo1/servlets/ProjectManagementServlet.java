package org.timetracker.demo1.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.timetracker.demo1.dao.ProjectDAO;
import org.timetracker.demo1.models.Project;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Servlet for project management (Admin)
 */
@WebServlet(name = "projectManagementServlet", value = "/admin/project-management")
public class ProjectManagementServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ProjectManagementServlet.class);
    private ProjectDAO projectDAO;

    @Override
    public void init() throws ServletException {
        projectDAO = new ProjectDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("list".equals(action)) {
                List<Project> projects = projectDAO.getAllProjects();
                request.setAttribute("projects", projects);
                request.getRequestDispatcher("/admin/project-list.jsp").forward(request, response);
            } else if ("edit".equals(action)) {
                int projectId = Integer.parseInt(request.getParameter("projectId"));
                Project project = projectDAO.getProjectById(projectId);
                request.setAttribute("project", project);
                request.getRequestDispatcher("/admin/project-edit.jsp").forward(request, response);
            } else {
                List<Project> projects = projectDAO.getAllProjects();
                request.setAttribute("projects", projects);
                request.getRequestDispatcher("/admin/project-list.jsp").forward(request, response);
            }
        } catch (Exception e) {
            logger.error("Error in project management", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("create".equals(action)) {
                createProject(request, response);
            } else if ("update".equals(action)) {
                updateProject(request, response);
            } else if ("delete".equals(action)) {
                deleteProject(request, response);
            }
        } catch (Exception e) {
            logger.error("Error processing project management action", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void createProject(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String projectName = request.getParameter("projectName");
        String description = request.getParameter("description");
        LocalDate startDate = LocalDate.parse(request.getParameter("startDate"));
        LocalDate endDate = LocalDate.parse(request.getParameter("endDate"));
        int createdBy = (Integer) request.getSession().getAttribute("userId");

        Project project = new Project(projectName, description, startDate, endDate);
        project.setCreatedBy(createdBy);

        if (projectDAO.createProject(project)) {
            logger.info("Project created: {}", projectName);
            response.sendRedirect(request.getContextPath() + "/admin/project-management?action=list");
        } else {
            request.setAttribute("error", "Failed to create project");
            doGet(request, response);
        }
    }

    private void updateProject(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int projectId = Integer.parseInt(request.getParameter("projectId"));
        String projectName = request.getParameter("projectName");
        String description = request.getParameter("description");
        LocalDate startDate = LocalDate.parse(request.getParameter("startDate"));
        LocalDate endDate = LocalDate.parse(request.getParameter("endDate"));
        String status = request.getParameter("status");

        Project project = projectDAO.getProjectById(projectId);
        project.setProjectName(projectName);
        project.setDescription(description);
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setStatus(status);

        if (projectDAO.updateProject(project)) {
            logger.info("Project updated: {}", projectName);
            response.sendRedirect(request.getContextPath() + "/admin/project-management?action=list");
        } else {
            request.setAttribute("error", "Failed to update project");
            doGet(request, response);
        }
    }

    private void deleteProject(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int projectId = Integer.parseInt(request.getParameter("projectId"));
        if (projectDAO.deleteProject(projectId)) {
            logger.info("Project deleted: {}", projectId);
            response.sendRedirect(request.getContextPath() + "/admin/project-management?action=list");
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}


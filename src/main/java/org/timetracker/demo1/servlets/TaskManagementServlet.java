package org.timetracker.demo1.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.timetracker.demo1.dao.ProjectDAO;
import org.timetracker.demo1.dao.TaskDAO;
import org.timetracker.demo1.dao.UserDAO;
import org.timetracker.demo1.models.Task;
import org.timetracker.demo1.models.User;
import org.timetracker.demo1.models.Project;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Servlet for task management (Project Manager)
 */
@WebServlet(name = "taskManagementServlet", value = "/project-manager/task-management")
public class TaskManagementServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(TaskManagementServlet.class);
    private TaskDAO taskDAO;
    private ProjectDAO projectDAO;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        taskDAO = new TaskDAO();
        projectDAO = new ProjectDAO();
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("list".equals(action)) {
                String projectIdParam = request.getParameter("projectId");
                if (projectIdParam != null) {
                    int projectId = Integer.parseInt(projectIdParam);
                    List<Task> tasks = taskDAO.getTasksByProjectId(projectId);
                    Project project = projectDAO.getProjectById(projectId);
                    request.setAttribute("tasks", tasks);
                    request.setAttribute("project", project);
                } else {
                    List<Task> tasks = taskDAO.getAllTasks();
                    request.setAttribute("tasks", tasks);
                }
                request.getRequestDispatcher("/project-manager/task-list.jsp").forward(request, response);
            } else if ("edit".equals(action)) {
                int taskId = Integer.parseInt(request.getParameter("taskId"));
                Task task = taskDAO.getTaskById(taskId);
                List<User> users = userDAO.getActiveUsers();
                List<Project> projects = projectDAO.getAllProjects();
                request.setAttribute("task", task);
                request.setAttribute("users", users);
                request.setAttribute("projects", projects);
                request.getRequestDispatcher("/project-manager/task-edit.jsp").forward(request, response);
            } else {
                List<Task> tasks = taskDAO.getAllTasks();
                List<Project> projects = projectDAO.getAllProjects();
                request.setAttribute("tasks", tasks);
                request.setAttribute("projects", projects);
                request.getRequestDispatcher("/project-manager/task-list.jsp").forward(request, response);
            }
        } catch (Exception e) {
            logger.error("Error in task management", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("create".equals(action)) {
                createTask(request, response);
            } else if ("update".equals(action)) {
                updateTask(request, response);
            } else if ("delete".equals(action)) {
                deleteTask(request, response);
            } else if ("assign".equals(action)) {
                assignTask(request, response);
            }
        } catch (Exception e) {
            logger.error("Error processing task management action", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void createTask(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int projectId = Integer.parseInt(request.getParameter("projectId"));
        String taskTitle = request.getParameter("taskTitle");
        String description = request.getParameter("description");
        LocalDate deadline = LocalDate.parse(request.getParameter("deadline"));
        String priority = request.getParameter("priority");
        double estimatedHours = Double.parseDouble(request.getParameter("estimatedHours"));
        int createdBy = (Integer) request.getSession().getAttribute("userId");

        Task task = new Task(projectId, taskTitle, description, deadline);
        task.setPriority(priority);
        task.setEstimatedHours(estimatedHours);
        task.setCreatedBy(createdBy);

        if (taskDAO.createTask(task)) {
            logger.info("Task created: {}", taskTitle);
            response.sendRedirect(request.getContextPath() + "/project-manager/task-management?action=list&projectId=" + projectId);
        } else {
            request.setAttribute("error", "Failed to create task");
            doGet(request, response);
        }
    }

    private void updateTask(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int taskId = Integer.parseInt(request.getParameter("taskId"));
        String taskTitle = request.getParameter("taskTitle");
        String description = request.getParameter("description");
        LocalDate deadline = LocalDate.parse(request.getParameter("deadline"));
        String priority = request.getParameter("priority");
        double estimatedHours = Double.parseDouble(request.getParameter("estimatedHours"));
        String status = request.getParameter("status");

        Task task = taskDAO.getTaskById(taskId);
        task.setTaskTitle(taskTitle);
        task.setDescription(description);
        task.setDeadline(deadline);
        task.setPriority(priority);
        task.setEstimatedHours(estimatedHours);
        task.setStatus(status);

        if (taskDAO.updateTask(task)) {
            logger.info("Task updated: {}", taskTitle);
            response.sendRedirect(request.getContextPath() + "/project-manager/task-management?action=list");
        } else {
            request.setAttribute("error", "Failed to update task");
            doGet(request, response);
        }
    }

    private void deleteTask(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int taskId = Integer.parseInt(request.getParameter("taskId"));
        if (taskDAO.deleteTask(taskId)) {
            logger.info("Task deleted: {}", taskId);
            response.sendRedirect(request.getContextPath() + "/project-manager/task-management?action=list");
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void assignTask(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int taskId = Integer.parseInt(request.getParameter("taskId"));
        int userId = Integer.parseInt(request.getParameter("userId"));

        Task task = taskDAO.getTaskById(taskId);
        task.setAssignedTo(userId);

        if (taskDAO.updateTask(task)) {
            logger.info("Task {} assigned to user {}", taskId, userId);
            response.sendRedirect(request.getContextPath() + "/project-manager/task-management?action=list");
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}


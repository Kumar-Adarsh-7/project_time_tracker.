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
import org.timetracker.demo1.dao.TimeLogDAO;
import org.timetracker.demo1.models.Project;
import org.timetracker.demo1.models.Task;
import org.timetracker.demo1.models.TimeLog;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Servlet for viewing reports (Admin & Project Manager)
 */
@WebServlet(name = "reportServlet", value = {"/admin/reports", "/project-manager/reports"})
public class ReportServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ReportServlet.class);
    private ProjectDAO projectDAO;
    private TaskDAO taskDAO;
    private TimeLogDAO timeLogDAO;

    @Override
    public void init() throws ServletException {
        projectDAO = new ProjectDAO();
        taskDAO = new TaskDAO();
        timeLogDAO = new TimeLogDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reportType = request.getParameter("type");

        try {
            if ("project-time".equals(reportType)) {
                generateProjectTimeReport(request, response);
            } else if ("task-time".equals(reportType)) {
                generateTaskTimeReport(request, response);
            } else if ("user-time".equals(reportType)) {
                generateUserTimeReport(request, response);
            } else if ("date-range".equals(reportType)) {
                generateDateRangeReport(request, response);
            } else {
                showReportOptions(request, response);
            }
        } catch (Exception e) {
            logger.error("Error generating report", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void showReportOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Project> projects = projectDAO.getAllProjects();
        List<Task> tasks = taskDAO.getAllTasks();
        request.setAttribute("projects", projects);
        request.setAttribute("tasks", tasks);
        request.getRequestDispatcher("/admin/reports.jsp").forward(request, response);
    }

    private void generateProjectTimeReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int projectId = Integer.parseInt(request.getParameter("projectId"));
        Project project = projectDAO.getProjectById(projectId);
        List<Task> tasks = taskDAO.getTasksByProjectId(projectId);

        Map<Task, Double> taskTimeMap = tasks.stream()
                .collect(Collectors.toMap(
                        task -> task,
                        task -> timeLogDAO.getTotalHoursByTask(task.getTaskId())
                ));

        double totalHours = taskTimeMap.values().stream().mapToDouble(Double::doubleValue).sum();

        request.setAttribute("project", project);
        request.setAttribute("taskTimeMap", taskTimeMap);
        request.setAttribute("totalHours", totalHours);
        request.getRequestDispatcher("/admin/report-project-time.jsp").forward(request, response);
    }

    private void generateTaskTimeReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int taskId = Integer.parseInt(request.getParameter("taskId"));
        Task task = taskDAO.getTaskById(taskId);
        List<TimeLog> timeLogs = timeLogDAO.getTimeLogsByTaskId(taskId);

        double totalHours = timeLogDAO.getTotalHoursByTask(taskId);

        request.setAttribute("task", task);
        request.setAttribute("timeLogs", timeLogs);
        request.setAttribute("totalHours", totalHours);
        request.getRequestDispatcher("/admin/report-task-time.jsp").forward(request, response);
    }

    private void generateUserTimeReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        List<TimeLog> timeLogs = timeLogDAO.getTimeLogsByUserId(userId);

        double totalHours = timeLogDAO.getTotalHoursByUser(userId);

        request.setAttribute("userId", userId);
        request.setAttribute("timeLogs", timeLogs);
        request.setAttribute("totalHours", totalHours);
        request.getRequestDispatcher("/admin/report-user-time.jsp").forward(request, response);
    }

    private void generateDateRangeReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LocalDate startDate = LocalDate.parse(request.getParameter("startDate"));
        LocalDate endDate = LocalDate.parse(request.getParameter("endDate"));
        List<TimeLog> timeLogs = timeLogDAO.getTimeLogsByDateRange(startDate, endDate);

        double totalHours = timeLogs.stream().mapToDouble(TimeLog::getHoursSpent).sum();

        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
        request.setAttribute("timeLogs", timeLogs);
        request.setAttribute("totalHours", totalHours);
        request.getRequestDispatcher("/admin/report-date-range.jsp").forward(request, response);
    }
}


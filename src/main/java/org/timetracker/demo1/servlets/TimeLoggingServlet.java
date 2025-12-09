package org.timetracker.demo1.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.timetracker.demo1.dao.TaskDAO;
import org.timetracker.demo1.dao.TimeLogDAO;
import org.timetracker.demo1.models.TimeLog;
import org.timetracker.demo1.models.Task;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Servlet for time logging (Team Member & Project Manager)
 */
@WebServlet(name = "timeLoggingServlet", value = {"/team-member/log-time", "/project-manager/log-time"})
public class TimeLoggingServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(TimeLoggingServlet.class);
    private TimeLogDAO timeLogDAO;
    private TaskDAO taskDAO;

    @Override
    public void init() throws ServletException {
        timeLogDAO = new TimeLogDAO();
        taskDAO = new TaskDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int userId = (Integer) request.getSession().getAttribute("userId");

        try {
            if ("my-logs".equals(action)) {
                List<TimeLog> timeLogs = timeLogDAO.getTimeLogsByUserId(userId);
                request.setAttribute("timeLogs", timeLogs);
                request.getRequestDispatcher("/team-member/time-logs.jsp").forward(request, response);
            } else if ("edit".equals(action)) {
                int timeLogId = Integer.parseInt(request.getParameter("timeLogId"));
                TimeLog timeLog = timeLogDAO.getTimeLogById(timeLogId);
                List<Task> tasks = taskDAO.getTasksByAssignedUser(userId);
                request.setAttribute("timeLog", timeLog);
                request.setAttribute("tasks", tasks);
                request.getRequestDispatcher("/team-member/time-log-edit.jsp").forward(request, response);
            } else {
                List<Task> tasks = taskDAO.getTasksByAssignedUser(userId);
                request.setAttribute("tasks", tasks);
                request.getRequestDispatcher("/team-member/log-time.jsp").forward(request, response);
            }
        } catch (Exception e) {
            logger.error("Error in time logging", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int userId = (Integer) request.getSession().getAttribute("userId");

        try {
            if ("log".equals(action)) {
                logTime(request, response, userId);
            } else if ("update".equals(action)) {
                updateTimeLog(request, response);
            } else if ("delete".equals(action)) {
                deleteTimeLog(request, response);
            }
        } catch (Exception e) {
            logger.error("Error processing time logging action", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void logTime(HttpServletRequest request, HttpServletResponse response, int userId) throws IOException, ServletException {
        int taskId = Integer.parseInt(request.getParameter("taskId"));
        double hoursSpent = Double.parseDouble(request.getParameter("hoursSpent"));
        LocalDate logDate = LocalDate.parse(request.getParameter("logDate"));
        String description = request.getParameter("description");

        TimeLog timeLog = new TimeLog(taskId, userId, hoursSpent, logDate);
        timeLog.setDescription(description);

        if (timeLogDAO.createTimeLog(timeLog)) {
            logger.info("Time logged: {} hours for task {}", hoursSpent, taskId);
            response.sendRedirect(request.getContextPath() + "/team-member/log-time?action=my-logs");
        } else {
            request.setAttribute("error", "Failed to log time");
            doGet(request, response);
        }
    }

    private void updateTimeLog(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int timeLogId = Integer.parseInt(request.getParameter("timeLogId"));
        double hoursSpent = Double.parseDouble(request.getParameter("hoursSpent"));
        String description = request.getParameter("description");

        TimeLog timeLog = timeLogDAO.getTimeLogById(timeLogId);
        timeLog.setHoursSpent(hoursSpent);
        timeLog.setDescription(description);

        if (timeLogDAO.updateTimeLog(timeLog)) {
            logger.info("Time log updated: {}", timeLogId);
            response.sendRedirect(request.getContextPath() + "/team-member/log-time?action=my-logs");
        } else {
            request.setAttribute("error", "Failed to update time log");
            doGet(request, response);
        }
    }

    private void deleteTimeLog(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int timeLogId = Integer.parseInt(request.getParameter("timeLogId"));
        if (timeLogDAO.deleteTimeLog(timeLogId)) {
            logger.info("Time log deleted: {}", timeLogId);
            response.sendRedirect(request.getContextPath() + "/team-member/log-time?action=my-logs");
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}


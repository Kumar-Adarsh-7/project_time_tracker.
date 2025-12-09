package org.timetracker.demo1.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.timetracker.demo1.models.Task;
import org.timetracker.demo1.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Task entity
 */
public class TaskDAO {
    private static final Logger logger = LoggerFactory.getLogger(TaskDAO.class);

    /**
     * Create a new task
     */
    public boolean createTask(Task task) {
        String sql = "INSERT INTO tasks (project_id, milestone_id, task_title, description, assigned_to, status, priority, estimated_hours, deadline, created_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, task.getProjectId());
            stmt.setObject(2, task.getMilestoneId());
            stmt.setString(3, task.getTaskTitle());
            stmt.setString(4, task.getDescription());
            stmt.setObject(5, task.getAssignedTo());
            stmt.setString(6, task.getStatus());
            stmt.setString(7, task.getPriority());
            stmt.setObject(8, task.getEstimatedHours());
            stmt.setDate(9, Date.valueOf(task.getDeadline()));
            stmt.setInt(10, task.getCreatedBy());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        task.setTaskId(rs.getInt(1));
                        logger.info("Task created successfully: {}", task.getTaskTitle());
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Error creating task", e);
        }
        return false;
    }

    /**
     * Get task by ID
     */
    public Task getTaskById(int taskId) {
        String sql = "SELECT * FROM tasks WHERE task_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, taskId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTask(rs);
                }
            }
        } catch (SQLException e) {
            logger.error("Error getting task by ID", e);
        }
        return null;
    }

    /**
     * Get tasks by project ID
     */
    public List<Task> getTasksByProjectId(int projectId) {
        String sql = "SELECT * FROM tasks WHERE project_id = ? ORDER BY deadline";
        List<Task> tasks = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tasks.add(mapResultSetToTask(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error getting tasks by project ID", e);
        }
        return tasks;
    }

    /**
     * Get tasks assigned to a user
     */
    public List<Task> getTasksByAssignedUser(int userId) {
        String sql = "SELECT * FROM tasks WHERE assigned_to = ? AND status != 'COMPLETED' ORDER BY deadline";
        List<Task> tasks = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tasks.add(mapResultSetToTask(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error getting tasks by assigned user", e);
        }
        return tasks;
    }

    /**
     * Get tasks by status
     */
    public List<Task> getTasksByStatus(String status) {
        String sql = "SELECT * FROM tasks WHERE status = ? ORDER BY deadline";
        List<Task> tasks = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tasks.add(mapResultSetToTask(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error getting tasks by status", e);
        }
        return tasks;
    }

    /**
     * Get all tasks
     */
    public List<Task> getAllTasks() {
        String sql = "SELECT * FROM tasks ORDER BY deadline";
        List<Task> tasks = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                tasks.add(mapResultSetToTask(rs));
            }
        } catch (SQLException e) {
            logger.error("Error getting all tasks", e);
        }
        return tasks;
    }

    /**
     * Update task
     */
    public boolean updateTask(Task task) {
        String sql = "UPDATE tasks SET task_title = ?, description = ?, assigned_to = ?, status = ?, priority = ?, estimated_hours = ?, deadline = ? WHERE task_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, task.getTaskTitle());
            stmt.setString(2, task.getDescription());
            stmt.setObject(3, task.getAssignedTo());
            stmt.setString(4, task.getStatus());
            stmt.setString(5, task.getPriority());
            stmt.setObject(6, task.getEstimatedHours());
            stmt.setDate(7, Date.valueOf(task.getDeadline()));
            stmt.setInt(8, task.getTaskId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Task updated successfully: {}", task.getTaskTitle());
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error updating task", e);
        }
        return false;
    }

    /**
     * Update task status
     */
    public boolean updateTaskStatus(int taskId, String status) {
        String sql = "UPDATE tasks SET status = ? WHERE task_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, taskId);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Task status updated: {} -> {}", taskId, status);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error updating task status", e);
        }
        return false;
    }

    /**
     * Delete task
     */
    public boolean deleteTask(int taskId) {
        String sql = "DELETE FROM tasks WHERE task_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, taskId);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Task deleted: {}", taskId);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error deleting task", e);
        }
        return false;
    }

    /**
     * Map ResultSet to Task object
     */
    private Task mapResultSetToTask(ResultSet rs) throws SQLException {
        Task task = new Task();
        task.setTaskId(rs.getInt("task_id"));
        task.setProjectId(rs.getInt("project_id"));

        Integer milestoneId = rs.getInt("milestone_id");
        if (!rs.wasNull()) {
            task.setMilestoneId(milestoneId);
        }

        task.setTaskTitle(rs.getString("task_title"));
        task.setDescription(rs.getString("description"));

        Integer assignedTo = rs.getInt("assigned_to");
        if (!rs.wasNull()) {
            task.setAssignedTo(assignedTo);
        }

        task.setStatus(rs.getString("status"));
        task.setPriority(rs.getString("priority"));

        Double estimatedHours = rs.getDouble("estimated_hours");
        if (!rs.wasNull()) {
            task.setEstimatedHours(estimatedHours);
        }

        Date deadline = rs.getDate("deadline");
        if (deadline != null) {
            task.setDeadline(deadline.toLocalDate());
        }

        task.setCreatedBy(rs.getInt("created_by"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            task.setCreatedAt(createdAt.toLocalDateTime());
        }

        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            task.setUpdatedAt(updatedAt.toLocalDateTime());
        }

        return task;
    }
}


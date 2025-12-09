package org.timetracker.demo1.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.timetracker.demo1.models.TimeLog;
import org.timetracker.demo1.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for TimeLog entity
 */
public class TimeLogDAO {
    private static final Logger logger = LoggerFactory.getLogger(TimeLogDAO.class);

    /**
     * Create a new time log entry
     */
    public boolean createTimeLog(TimeLog timeLog) {
        String sql = "INSERT INTO time_logs (task_id, user_id, hours_spent, log_date, description) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, timeLog.getTaskId());
            stmt.setInt(2, timeLog.getUserId());
            stmt.setDouble(3, timeLog.getHoursSpent());
            stmt.setDate(4, Date.valueOf(timeLog.getLogDate()));
            stmt.setString(5, timeLog.getDescription());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        timeLog.setTimeLogId(rs.getInt(1));
                        logger.info("Time log created successfully for task: {}", timeLog.getTaskId());
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Error creating time log", e);
        }
        return false;
    }

    /**
     * Get time log by ID
     */
    public TimeLog getTimeLogById(int timeLogId) {
        String sql = "SELECT * FROM time_logs WHERE time_log_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, timeLogId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTimeLog(rs);
                }
            }
        } catch (SQLException e) {
            logger.error("Error getting time log by ID", e);
        }
        return null;
    }

    /**
     * Get time logs by task ID
     */
    public List<TimeLog> getTimeLogsByTaskId(int taskId) {
        String sql = "SELECT * FROM time_logs WHERE task_id = ? ORDER BY log_date DESC";
        List<TimeLog> timeLogs = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, taskId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    timeLogs.add(mapResultSetToTimeLog(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error getting time logs by task ID", e);
        }
        return timeLogs;
    }

    /**
     * Get time logs by user ID
     */
    public List<TimeLog> getTimeLogsByUserId(int userId) {
        String sql = "SELECT * FROM time_logs WHERE user_id = ? ORDER BY log_date DESC";
        List<TimeLog> timeLogs = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    timeLogs.add(mapResultSetToTimeLog(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error getting time logs by user ID", e);
        }
        return timeLogs;
    }

    /**
     * Get time logs by date range
     */
    public List<TimeLog> getTimeLogsByDateRange(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT * FROM time_logs WHERE log_date BETWEEN ? AND ? ORDER BY log_date DESC";
        List<TimeLog> timeLogs = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(startDate));
            stmt.setDate(2, Date.valueOf(endDate));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    timeLogs.add(mapResultSetToTimeLog(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error getting time logs by date range", e);
        }
        return timeLogs;
    }

    /**
     * Get time logs by user and date range
     */
    public List<TimeLog> getTimeLogsByUserAndDateRange(int userId, LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT * FROM time_logs WHERE user_id = ? AND log_date BETWEEN ? AND ? ORDER BY log_date DESC";
        List<TimeLog> timeLogs = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setDate(2, Date.valueOf(startDate));
            stmt.setDate(3, Date.valueOf(endDate));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    timeLogs.add(mapResultSetToTimeLog(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error getting time logs by user and date range", e);
        }
        return timeLogs;
    }

    /**
     * Get total hours for a task
     */
    public double getTotalHoursByTask(int taskId) {
        String sql = "SELECT COALESCE(SUM(hours_spent), 0) as total_hours FROM time_logs WHERE task_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, taskId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("total_hours");
                }
            }
        } catch (SQLException e) {
            logger.error("Error getting total hours by task", e);
        }
        return 0.0;
    }

    /**
     * Get total hours for a user
     */
    public double getTotalHoursByUser(int userId) {
        String sql = "SELECT COALESCE(SUM(hours_spent), 0) as total_hours FROM time_logs WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("total_hours");
                }
            }
        } catch (SQLException e) {
            logger.error("Error getting total hours by user", e);
        }
        return 0.0;
    }

    /**
     * Get all time logs
     */
    public List<TimeLog> getAllTimeLogs() {
        String sql = "SELECT * FROM time_logs ORDER BY log_date DESC";
        List<TimeLog> timeLogs = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                timeLogs.add(mapResultSetToTimeLog(rs));
            }
        } catch (SQLException e) {
            logger.error("Error getting all time logs", e);
        }
        return timeLogs;
    }

    /**
     * Update time log
     */
    public boolean updateTimeLog(TimeLog timeLog) {
        String sql = "UPDATE time_logs SET hours_spent = ?, description = ? WHERE time_log_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, timeLog.getHoursSpent());
            stmt.setString(2, timeLog.getDescription());
            stmt.setInt(3, timeLog.getTimeLogId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Time log updated successfully: {}", timeLog.getTimeLogId());
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error updating time log", e);
        }
        return false;
    }

    /**
     * Delete time log
     */
    public boolean deleteTimeLog(int timeLogId) {
        String sql = "DELETE FROM time_logs WHERE time_log_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, timeLogId);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Time log deleted: {}", timeLogId);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error deleting time log", e);
        }
        return false;
    }

    /**
     * Map ResultSet to TimeLog object
     */
    private TimeLog mapResultSetToTimeLog(ResultSet rs) throws SQLException {
        TimeLog timeLog = new TimeLog();
        timeLog.setTimeLogId(rs.getInt("time_log_id"));
        timeLog.setTaskId(rs.getInt("task_id"));
        timeLog.setUserId(rs.getInt("user_id"));
        timeLog.setHoursSpent(rs.getDouble("hours_spent"));

        Date logDate = rs.getDate("log_date");
        if (logDate != null) {
            timeLog.setLogDate(logDate.toLocalDate());
        }

        timeLog.setDescription(rs.getString("description"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            timeLog.setCreatedAt(createdAt.toLocalDateTime());
        }

        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            timeLog.setUpdatedAt(updatedAt.toLocalDateTime());
        }

        return timeLog;
    }
}


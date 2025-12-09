package org.timetracker.demo1.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.timetracker.demo1.models.Project;
import org.timetracker.demo1.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Project entity
 */
public class ProjectDAO {
    private static final Logger logger = LoggerFactory.getLogger(ProjectDAO.class);

    /**
     * Create a new project
     */
    public boolean createProject(Project project) {
        String sql = "INSERT INTO projects (project_name, description, start_date, end_date, status, created_by) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, project.getProjectName());
            stmt.setString(2, project.getDescription());
            stmt.setDate(3, Date.valueOf(project.getStartDate()));
            stmt.setDate(4, Date.valueOf(project.getEndDate()));
            stmt.setString(5, project.getStatus());
            stmt.setInt(6, project.getCreatedBy());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        project.setProjectId(rs.getInt(1));
                        logger.info("Project created successfully: {}", project.getProjectName());
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Error creating project", e);
        }
        return false;
    }

    /**
     * Get project by ID
     */
    public Project getProjectById(int projectId) {
        String sql = "SELECT * FROM projects WHERE project_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToProject(rs);
                }
            }
        } catch (SQLException e) {
            logger.error("Error getting project by ID", e);
        }
        return null;
    }

    /**
     * Get all projects
     */
    public List<Project> getAllProjects() {
        String sql = "SELECT * FROM projects ORDER BY project_name";
        List<Project> projects = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                projects.add(mapResultSetToProject(rs));
            }
        } catch (SQLException e) {
            logger.error("Error getting all projects", e);
        }
        return projects;
    }

    /**
     * Get active projects
     */
    public List<Project> getActiveProjects() {
        String sql = "SELECT * FROM projects WHERE status = 'ACTIVE' ORDER BY project_name";
        List<Project> projects = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                projects.add(mapResultSetToProject(rs));
            }
        } catch (SQLException e) {
            logger.error("Error getting active projects", e);
        }
        return projects;
    }

    /**
     * Get projects by status
     */
    public List<Project> getProjectsByStatus(String status) {
        String sql = "SELECT * FROM projects WHERE status = ? ORDER BY project_name";
        List<Project> projects = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    projects.add(mapResultSetToProject(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error getting projects by status", e);
        }
        return projects;
    }

    /**
     * Update project
     */
    public boolean updateProject(Project project) {
        String sql = "UPDATE projects SET project_name = ?, description = ?, start_date = ?, end_date = ?, status = ? WHERE project_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, project.getProjectName());
            stmt.setString(2, project.getDescription());
            stmt.setDate(3, Date.valueOf(project.getStartDate()));
            stmt.setDate(4, Date.valueOf(project.getEndDate()));
            stmt.setString(5, project.getStatus());
            stmt.setInt(6, project.getProjectId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Project updated successfully: {}", project.getProjectName());
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error updating project", e);
        }
        return false;
    }

    /**
     * Delete project
     */
    public boolean deleteProject(int projectId) {
        String sql = "DELETE FROM projects WHERE project_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Project deleted: {}", projectId);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error deleting project", e);
        }
        return false;
    }

    /**
     * Map ResultSet to Project object
     */
    private Project mapResultSetToProject(ResultSet rs) throws SQLException {
        Project project = new Project();
        project.setProjectId(rs.getInt("project_id"));
        project.setProjectName(rs.getString("project_name"));
        project.setDescription(rs.getString("description"));

        Date startDate = rs.getDate("start_date");
        if (startDate != null) {
            project.setStartDate(startDate.toLocalDate());
        }

        Date endDate = rs.getDate("end_date");
        if (endDate != null) {
            project.setEndDate(endDate.toLocalDate());
        }

        project.setStatus(rs.getString("status"));
        project.setCreatedBy(rs.getInt("created_by"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            project.setCreatedAt(createdAt.toLocalDateTime());
        }

        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            project.setUpdatedAt(updatedAt.toLocalDateTime());
        }

        return project;
    }
}


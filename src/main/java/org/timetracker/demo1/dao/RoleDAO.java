package org.timetracker.demo1.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.timetracker.demo1.models.Role;
import org.timetracker.demo1.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Role entity
 */
public class RoleDAO {
    private static final Logger logger = LoggerFactory.getLogger(RoleDAO.class);

    /**
     * Get role by ID
     */
    public Role getRoleById(int roleId) {
        String sql = "SELECT * FROM roles WHERE role_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, roleId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToRole(rs);
                }
            }
        } catch (SQLException e) {
            logger.error("Error getting role by ID", e);
        }
        return null;
    }

    /**
     * Get role by name
     */
    public Role getRoleByName(String roleName) {
        String sql = "SELECT * FROM roles WHERE role_name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, roleName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToRole(rs);
                }
            }
        } catch (SQLException e) {
            logger.error("Error getting role by name", e);
        }
        return null;
    }

    /**
     * Get all roles
     */
    public List<Role> getAllRoles() {
        String sql = "SELECT * FROM roles ORDER BY role_name";
        List<Role> roles = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                roles.add(mapResultSetToRole(rs));
            }
        } catch (SQLException e) {
            logger.error("Error getting all roles", e);
        }
        return roles;
    }

    /**
     * Get roles for a user
     */
    public List<Role> getRolesByUserId(int userId) {
        String sql = "SELECT r.* FROM roles r " +
                "INNER JOIN user_roles ur ON r.role_id = ur.role_id " +
                "WHERE ur.user_id = ? ORDER BY r.role_name";
        List<Role> roles = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    roles.add(mapResultSetToRole(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error getting roles by user ID", e);
        }
        return roles;
    }

    /**
     * Assign role to user
     */
    public boolean assignRoleToUser(int userId, int roleId) {
        String sql = "INSERT INTO user_roles (user_id, role_id) VALUES (?, ?) ON DUPLICATE KEY UPDATE assigned_at = CURRENT_TIMESTAMP";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, roleId);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Role {} assigned to user {}", roleId, userId);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error assigning role to user", e);
        }
        return false;
    }

    /**
     * Remove role from user
     */
    public boolean removeRoleFromUser(int userId, int roleId) {
        String sql = "DELETE FROM user_roles WHERE user_id = ? AND role_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, roleId);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Role {} removed from user {}", roleId, userId);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error removing role from user", e);
        }
        return false;
    }

    /**
     * Map ResultSet to Role object
     */
    private Role mapResultSetToRole(ResultSet rs) throws SQLException {
        Role role = new Role();
        role.setRoleId(rs.getInt("role_id"));
        role.setRoleName(rs.getString("role_name"));
        role.setDescription(rs.getString("description"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            role.setCreatedAt(createdAt.toLocalDateTime());
        }

        return role;
    }
}


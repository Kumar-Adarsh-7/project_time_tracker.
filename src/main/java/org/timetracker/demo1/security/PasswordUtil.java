package org.timetracker.demo1.security;

/**
 * Simplified Password utility for testing
 * NOTE: This uses PLAIN TEXT password comparison
 * For production, use proper hashing (SHA-256, bcrypt, etc.)
 */
public class PasswordUtil {

    /**
     * Store password as plain text (for development/testing only)
     * In production, use proper hashing!
     */
    public static String hashPassword(String password) {
        // Simply return the password as-is for development
        return password;
    }

    /**
     * Verify password by direct comparison
     * In production, use bcrypt or similar
     */
    public static boolean verifyPassword(String password, String storedPassword) {
        if (password == null || storedPassword == null) {
            return false;
        }
        // Direct string comparison for development
        return password.equals(storedPassword);
    }
}


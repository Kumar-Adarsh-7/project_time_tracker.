package org.timetracker.demo1.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Database connection utility using HikariCP for connection pooling
 */
public class DBConnection {
    private static final Logger logger = LoggerFactory.getLogger(DBConnection.class);
    private static HikariDataSource dataSource;

    // Database configuration
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/timetracker_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "KUNAL@903455";
    private static final int MAX_POOL_SIZE = 10;
    private static final int MIN_IDLE = 5;
    private static final long IDLE_TIMEOUT = 600000; // 10 minutes
    private static final long MAX_LIFETIME = 1800000; // 30 minutes

    static {
        try {
            Class.forName(DB_DRIVER);
            logger.info("MySQL Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            logger.error("MySQL Driver not found", e);
            throw new RuntimeException("MySQL Driver not found", e);
        }
    }

    /**
     * Initialize HikariCP connection pool
     */
    public static void initializePool() {
        if (dataSource == null) {
            try {
                HikariConfig config = new HikariConfig();
                config.setJdbcUrl(DB_URL);
                config.setUsername(DB_USER);
                config.setPassword(DB_PASSWORD);
                config.setMaximumPoolSize(MAX_POOL_SIZE);
                config.setMinimumIdle(MIN_IDLE);
                config.setIdleTimeout(IDLE_TIMEOUT);
                config.setMaxLifetime(MAX_LIFETIME);
                config.setConnectionTestQuery("SELECT 1");
                config.setLeakDetectionThreshold(60000); // 1 minute

                dataSource = new HikariDataSource(config);
                logger.info("HikariCP connection pool initialized successfully");
            } catch (Exception e) {
                logger.error("Failed to initialize HikariCP connection pool", e);
                throw new RuntimeException("Failed to initialize database connection pool", e);
            }
        }
    }

    /**
     * Get a connection from the pool
     *
     * @return Connection object
     * @throws SQLException if connection cannot be obtained
     */
    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            initializePool();
        }
        return dataSource.getConnection();
    }

    /**
     * Close the connection pool
     */
    public static void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            logger.info("HikariCP connection pool closed");
        }
    }

    /**
     * Check if the database is connected
     *
     * @return true if connected, false otherwise
     */
    public static boolean isConnected() {
        try {
            Connection conn = getConnection();
            boolean connected = !conn.isClosed();
            conn.close();
            return connected;
        } catch (SQLException e) {
            logger.error("Database connection check failed", e);
            return false;
        }
    }
}


package org.timetracker.demo1.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TimeLog {
    private int timeLogId;
    private int taskId;
    private int userId;
    private double hoursSpent;
    private LocalDate logDate;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TimeLog() {
    }

    public TimeLog(int taskId, int userId, double hoursSpent, LocalDate logDate) {
        this.taskId = taskId;
        this.userId = userId;
        this.hoursSpent = hoursSpent;
        this.logDate = logDate;
    }

    // Getters and Setters
    public int getTimeLogId() {
        return timeLogId;
    }

    public void setTimeLogId(int timeLogId) {
        this.timeLogId = timeLogId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getHoursSpent() {
        return hoursSpent;
    }

    public void setHoursSpent(double hoursSpent) {
        this.hoursSpent = hoursSpent;
    }

    public LocalDate getLogDate() {
        return logDate;
    }

    public void setLogDate(LocalDate logDate) {
        this.logDate = logDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "TimeLog{" +
                "timeLogId=" + timeLogId +
                ", taskId=" + taskId +
                ", userId=" + userId +
                ", hoursSpent=" + hoursSpent +
                ", logDate=" + logDate +
                '}';
    }
}


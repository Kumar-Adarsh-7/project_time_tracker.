package org.timetracker.demo1.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Milestone {
    private int milestoneId;
    private int projectId;
    private String milestoneName;
    private String description;
    private LocalDate targetDate;
    private String status; // PENDING, IN_PROGRESS, COMPLETED, DELAYED
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Milestone() {
    }

    public Milestone(int projectId, String milestoneName, LocalDate targetDate) {
        this.projectId = projectId;
        this.milestoneName = milestoneName;
        this.targetDate = targetDate;
        this.status = "PENDING";
    }

    // Getters and Setters
    public int getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(int milestoneId) {
        this.milestoneId = milestoneId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getMilestoneName() {
        return milestoneName;
    }

    public void setMilestoneName(String milestoneName) {
        this.milestoneName = milestoneName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        return "Milestone{" +
                "milestoneId=" + milestoneId +
                ", milestoneName='" + milestoneName + '\'' +
                ", status='" + status + '\'' +
                ", targetDate=" + targetDate +
                '}';
    }
}


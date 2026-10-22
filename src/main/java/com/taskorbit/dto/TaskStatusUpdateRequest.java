package com.taskorbit.dto;

public class TaskStatusUpdateRequest {

    private String status;

    public TaskStatusUpdateRequest() {
    }

    public TaskStatusUpdateRequest(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

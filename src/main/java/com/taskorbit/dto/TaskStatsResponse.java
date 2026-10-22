package com.taskorbit.dto;

public class TaskStatsResponse {

    private long total;
    private long pending;
    private long completed;

    public TaskStatsResponse() {
    }

    public TaskStatsResponse(long total, long pending, long completed) {
        this.total = total;
        this.pending = pending;
        this.completed = completed;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getPending() {
        return pending;
    }

    public void setPending(long pending) {
        this.pending = pending;
    }

    public long getCompleted() {
        return completed;
    }

    public void setCompleted(long completed) {
        this.completed = completed;
    }
}

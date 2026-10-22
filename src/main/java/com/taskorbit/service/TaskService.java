package com.taskorbit.service;

import com.taskorbit.dto.TaskRequest;
import com.taskorbit.dto.TaskStatsResponse;
import com.taskorbit.exception.ResourceNotFoundException;
import com.taskorbit.model.Task;
import com.taskorbit.model.TaskStatus;
import com.taskorbit.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(TaskRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle().trim());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setStatus(TaskStatus.PENDING);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public List<Task> getTasks(String statusFilter) {
        List<Task> tasks;

        if (statusFilter == null || statusFilter.isBlank() || "ALL".equalsIgnoreCase(statusFilter)) {
            tasks = taskRepository.findAll();
        } else {
            try {
                TaskStatus status = TaskStatus.valueOf(statusFilter.trim().toUpperCase());
                tasks = taskRepository.findByStatusOrderByDueDateAsc(status);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid status filter: " + statusFilter + ". Allowed values: ALL, PENDING, COMPLETED");
            }
        }

        // Sort by dueDate ascending, placing tasks with null dueDate at the end, then by createdAt desc
        return tasks.stream()
                .sorted(Comparator.comparing(Task::getDueDate, Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(Task::getCreatedAt, Comparator.nullsLast(Comparator.reverseOrder())))
                .collect(Collectors.toList());
    }

    public Task getTaskById(String id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
    }

    public Task updateTask(String id, TaskRequest request) {
        Task task = getTaskById(id);
        task.setTitle(request.getTitle().trim());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setUpdatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public Task updateStatus(String id, String statusStr) {
        Task task = getTaskById(id);
        TaskStatus targetStatus;

        if (statusStr != null && !statusStr.isBlank()) {
            try {
                targetStatus = TaskStatus.valueOf(statusStr.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid status: " + statusStr + ". Allowed values: PENDING, COMPLETED");
            }
        } else {
            // Toggle status if not explicitly provided
            targetStatus = (task.getStatus() == TaskStatus.PENDING) ? TaskStatus.COMPLETED : TaskStatus.PENDING;
        }

        task.setStatus(targetStatus);
        task.setUpdatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public void deleteTask(String id) {
        Task task = getTaskById(id);
        taskRepository.delete(task);
    }

    public TaskStatsResponse getStats() {
        long total = taskRepository.count();
        long pending = taskRepository.countByStatus(TaskStatus.PENDING);
        long completed = taskRepository.countByStatus(TaskStatus.COMPLETED);
        return new TaskStatsResponse(total, pending, completed);
    }
}

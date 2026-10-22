package com.taskorbit.controller;

import com.taskorbit.dto.TaskRequest;
import com.taskorbit.dto.TaskStatsResponse;
import com.taskorbit.dto.TaskStatusUpdateRequest;
import com.taskorbit.model.Task;
import com.taskorbit.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody TaskRequest request) {
        Task createdTask = taskService.createTask(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getTasks(
            @RequestParam(required = false, defaultValue = "ALL") String status) {
        List<Task> tasks = taskService.getTasks(status);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/stats")
    public ResponseEntity<TaskStatsResponse> getStats() {
        TaskStatsResponse stats = taskService.getStats();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable String id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable String id,
            @Valid @RequestBody TaskRequest request) {
        Task updatedTask = taskService.updateTask(id, request);
        return ResponseEntity.ok(updatedTask);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Task> updateTaskStatus(
            @PathVariable String id,
            @RequestBody(required = false) TaskStatusUpdateRequest request) {
        String statusStr = (request != null) ? request.getStatus() : null;
        Task updatedTask = taskService.updateStatus(id, statusStr);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}

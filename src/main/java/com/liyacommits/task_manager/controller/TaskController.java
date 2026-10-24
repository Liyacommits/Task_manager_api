package com.liyacommits.task_manager.controller;

import com.liyacommits.task_manager.entity.Task;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private List<Task> tasks = new ArrayList<>();
    private Long nextId = 1L;

    @GetMapping
    public List<Task> getTasks() {
        return tasks;
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable Long id) {
        return tasks.stream().filter(task-> task.getId().equals(id)).findFirst().orElse(null);
    }

    @PostMapping
    public void addTask(@RequestBody Task task) {
        task.setId(nextId++);
        task.setCreatedAt(LocalDateTime.now());
        task.setCompleted(false);
        tasks.add(task);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task task) {
        for  (Task task1 : tasks) {
            if (task1.getId().equals(id)) {
                task1.setTitle(task.getTitle());
                task1.setDescription(task.getDescription());
                return task1;
            }
        }
        return null;
    }

    @DeleteMapping("/id")
    public void deleteTask(@PathVariable Long id) {
        tasks.removeIf(task -> task.getId().equals(id));
    }
}

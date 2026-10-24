package com.liyacommits.task_manager.service;


import com.liyacommits.task_manager.entity.Task;
import com.liyacommits.task_manager.respository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskService {

    @Autowired
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();

    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Optional<Task> updateTask(Long id, Task updatedTask) {
        return taskRepository.findById(id).map(
                task -> {
                    task.setTitle(updatedTask.getTitle());
                    task.setDescription(updatedTask.getDescription());
                    task.setCompleted(updatedTask.getCompleted());
                    return taskRepository.save(task);
                });
    }

    public boolean deleteTaskById(Long id) {
        return taskRepository.findById(id)
                .map(task -> {taskRepository.delete(task);
                return true;}).orElse(false);
    }

    public List<Task> getTasksByStatus(Boolean status) {
        return taskRepository.findByCompleted(status);
    }

    public List<Task> searchTasks(String title) {
        return taskRepository.findByTitleContainingIgnoreCase(title);
    }

}

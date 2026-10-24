package com.liyacommits.task_manager.service;

import com.liyacommits.task_manager.dto.TaskRequestDTO;
import com.liyacommits.task_manager.dto.TaskResponseDTO;
import com.liyacommits.task_manager.entity.Task;
import com.liyacommits.task_manager.exception.ResourceNotFoundException;
import com.liyacommits.task_manager.respository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public TaskResponseDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
        return mapToResponseDTO(task);
    }

    public TaskResponseDTO createTask(TaskRequestDTO requestDTO) {
        Task task = mapToEntity(requestDTO);
        Task savedTask = taskRepository.save(task);
        return mapToResponseDTO(savedTask);
    }

    public TaskResponseDTO updateTask(Long id, TaskRequestDTO requestDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));

        task.setTitle(requestDTO.getTitle());
        task.setDescription(requestDTO.getDescription());
        if (requestDTO.getCompleted() != null) {
            task.setCompleted(requestDTO.getCompleted());
        }

        Task updatedTask = taskRepository.save(task);
        return mapToResponseDTO(updatedTask);
    }

    public void deleteTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
        taskRepository.delete(task);
    }

    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getTasksByStatus(Boolean status) {
        return taskRepository.findByCompleted(status)
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TaskResponseDTO> searchTasks(String title) {
        return taskRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    private TaskResponseDTO mapToResponseDTO(Task task) {
        return TaskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .completed(task.getCompleted())
                .createdAt(task.getCreatedAt())
                .build();
    }

    private Task mapToEntity(TaskRequestDTO requestDTO) {
        Task task = new Task();
        task.setTitle(requestDTO.getTitle());
        task.setDescription(requestDTO.getDescription());
        task.setCompleted(requestDTO.getCompleted() != null ? requestDTO.getCompleted() : false);
        return task;
    }
}

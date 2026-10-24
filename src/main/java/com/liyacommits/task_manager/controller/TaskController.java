package com.liyacommits.task_manager.controller;

import com.liyacommits.task_manager.dto.ErrorResponseDTO;
import com.liyacommits.task_manager.dto.TaskRequestDTO;
import com.liyacommits.task_manager.dto.TaskResponseDTO;
import com.liyacommits.task_manager.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@Tag(name = "Tasks", description = "Endpoints for creating, retrieving, updating, deleting, and searching tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "Get all tasks", description = "Retrieves a list of all tasks in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all tasks",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TaskResponseDTO.class))))
    })
    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @Operation(summary = "Get a task by ID", description = "Retrieves a specific task by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task found and returned",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTask(
            @Parameter(description = "ID of the task to retrieve", example = "1", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @Operation(summary = "Create a new task", description = "Creates a new task with the provided title, description, and optional completion status.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data or validation failure",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PostMapping
    public ResponseEntity<TaskResponseDTO> addTask(
            @Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        TaskResponseDTO savedTask = taskService.createTask(taskRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    @Operation(summary = "Update an existing task", description = "Updates the title, description, and completion status of an existing task by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data or validation failure",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @Parameter(description = "ID of the task to update", example = "1", required = true)
            @PathVariable Long id,
            @Valid @RequestBody TaskRequestDTO updatedTaskDTO) {
        return ResponseEntity.ok(taskService.updateTask(id, updatedTaskDTO));
    }

    @Operation(summary = "Delete a task", description = "Deletes an existing task by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @Parameter(description = "ID of the task to delete", example = "1", required = true)
            @PathVariable Long id) {
        taskService.deleteTaskById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Filter tasks by completion status", description = "Retrieves all tasks matching the specified completion status (`true` or `false`).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved matching tasks",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TaskResponseDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid status parameter",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/completed/{status}")
    public ResponseEntity<List<TaskResponseDTO>> getCompletedTasks(
            @Parameter(description = "Completion status to filter by", example = "true", required = true)
            @PathVariable Boolean status) {
        return ResponseEntity.ok(taskService.getTasksByStatus(status));
    }

    @Operation(summary = "Search tasks by title", description = "Searches for tasks whose title contains the given keyword (case-insensitive).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved matching tasks",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TaskResponseDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Missing search query parameter",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/search")
    public ResponseEntity<List<TaskResponseDTO>> searchTasks(
            @Parameter(description = "Title keyword to search for", example = "documentation", required = true)
            @RequestParam String title) {
        return ResponseEntity.ok(taskService.searchTasks(title));
    }
}

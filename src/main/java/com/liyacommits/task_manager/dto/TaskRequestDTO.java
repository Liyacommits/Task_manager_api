package com.liyacommits.task_manager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request payload for creating or updating a task")
public class TaskRequestDTO {

    @NotBlank(message = "Task title is required and cannot be blank")
    @Size(max = 255, message = "Task title must not exceed 255 characters")
    @Schema(description = "Title of the task", example = "Complete OpenAPI documentation", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @Size(max = 1024, message = "Task description must not exceed 1024 characters")
    @Schema(description = "Detailed description of the task", example = "Add Swagger annotations, DTOs, and global exception handling to the API")
    private String description;

    @Schema(description = "Completion status of the task", example = "false", defaultValue = "false")
    private Boolean completed;
}

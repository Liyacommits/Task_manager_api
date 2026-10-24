package com.liyacommits.task_manager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Response payload representing a task")
public class TaskResponseDTO {

    @Schema(description = "Unique identifier of the task", example = "1")
    private Long id;

    @Schema(description = "Title of the task", example = "Complete OpenAPI documentation")
    private String title;

    @Schema(description = "Detailed description of the task", example = "Add Swagger annotations, DTOs, and global exception handling to the API")
    private String description;

    @Schema(description = "Indicates whether the task is completed", example = "false")
    private Boolean completed;

    @Schema(description = "Timestamp when the task was created", example = "2026-10-24T19:15:00")
    private LocalDateTime createdAt;
}

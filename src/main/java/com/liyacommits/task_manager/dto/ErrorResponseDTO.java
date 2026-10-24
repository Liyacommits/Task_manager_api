package com.liyacommits.task_manager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Standardized error response returned when an API exception occurs")
public class ErrorResponseDTO {

    @Schema(description = "Timestamp when the error occurred", example = "2026-10-24T19:15:00")
    private LocalDateTime timestamp;

    @Schema(description = "HTTP status code", example = "404")
    private int status;

    @Schema(description = "HTTP status error phrase", example = "Not Found")
    private String error;

    @Schema(description = "Detailed error message", example = "Task not found with id: 99")
    private String message;

    @Schema(description = "Request URI path where the error occurred", example = "/api/v1/tasks/99")
    private String path;

    @Schema(description = "Map of field-level validation errors, if applicable", example = "{\"title\": \"Task title is required and cannot be blank\"}")
    private Map<String, String> validationErrors;
}

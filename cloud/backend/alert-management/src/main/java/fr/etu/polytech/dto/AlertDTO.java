package fr.etu.polytech.dto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Date;


public record AlertDTO(
        @NotNull(message = "Time must not be null")
        Date time,

        @NotNull(message = "Alert type must not be null")
        @NotEmpty(message = "Alert type must not be empty")
        String type,

        @NotNull(message = "Alert message must not be null")
        @NotEmpty(message = "Alert message must not be empty")
        String message,

        @NotNull(message = "Gateway ID must not be null")
        @NotEmpty(message = "Gateway ID must not be empty")
        String gatewayId,

        float value,

        @NotNull(message = "Severity must not be null")
        @Pattern(regexp = "LOW|MEDIUM|INFO|WARNING|CRITICAL", message = "Invalid severity value")
        String severity
) {}


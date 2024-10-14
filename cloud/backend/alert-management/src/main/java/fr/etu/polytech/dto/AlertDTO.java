package fr.etu.polytech.dto;
import fr.etu.polytech.entity.Severity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;



public record AlertDTO(
        @NotNull(message = "Alert type must not be null")
        @NotEmpty(message = "Alert type must not be empty")
        String type,
        @NotNull(message = "Alert message must not be null")
        @NotEmpty(message = "Alert message must not be empty")
        String message,
        @NotNull(message = "Gateway ID must not be null")
        @NotEmpty(message = "Gateway ID must not be empty")
        String gatewayId,
        int value,
        @NotNull(message = "Severity must not be null")
        Severity severity
) {}


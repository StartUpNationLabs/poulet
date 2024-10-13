package fr.etu.polytech.mapper;

import fr.etu.polytech.dto.ErrorDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        // Get the message of the first violation
        String errorMsg = exception.getConstraintViolations().stream()
                .findFirst() // Get the first violation
                .map(ConstraintViolation::getMessage) // Extract its message
                .orElse("Validation failed."); // Fallback message if no violations

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorDTO(Response.Status.BAD_REQUEST.getStatusCode(), errorMsg))
                .build();
    }
}
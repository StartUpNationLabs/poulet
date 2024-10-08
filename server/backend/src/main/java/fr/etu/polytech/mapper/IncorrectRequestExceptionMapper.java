package fr.etu.polytech.mapper;

import fr.etu.polytech.exception.IncorrectRequestException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class IncorrectRequestExceptionMapper implements ExceptionMapper<IncorrectRequestException> {

    @Override
    public Response toResponse(IncorrectRequestException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(exception.getMessage())
                .build();
    }
}
package fr.etu.polytech.mapper;

import fr.etu.polytech.dto.ErrorDTO;
import fr.etu.polytech.exception.ResourceNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ResourceNotFoundExceptionMapper implements ExceptionMapper<ResourceNotFoundException> {

    @Override
    public Response toResponse(ResourceNotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorDTO(Response.Status.NOT_FOUND.getStatusCode(),exception.getMessage()))
                .build();
    }
}

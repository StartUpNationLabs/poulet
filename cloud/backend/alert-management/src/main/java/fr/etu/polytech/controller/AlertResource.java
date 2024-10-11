package fr.etu.polytech.controller;

import fr.etu.polytech.dto.AlertDTO;
import fr.etu.polytech.entity.Alert;
import fr.etu.polytech.exception.IncorrectRequestException;
import fr.etu.polytech.exception.ResourceNotFoundException;
import fr.etu.polytech.repository.AlertRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


@Path("/alert")
public class AlertResource {
    private static final Logger LOGGER = Logger.getLogger(AlertResource.class.getName());

    @Inject
    AlertRepository alertRepository;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAlertById(@PathParam("id") String id) throws IncorrectRequestException, ResourceNotFoundException {
        LOGGER.info("Received request to get alert with ID: " + id);
        return findAlertById(id)
                .map(alert -> {
                    LOGGER.info("Alert found with ID: " + id);
                    return Response.ok(alert).build();
                })
                .orElseThrow(() -> {
                    LOGGER.warning("Alert not found with ID: " + id);
                    return new ResourceNotFoundException("Alert with ID " + id + " not found.");
                });
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Alert> getAllAlerts() {
        return alertRepository.listAll();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAlert(AlertDTO alertDTO) throws IncorrectRequestException {
        validateAlertDTO(alertDTO);
        Alert alert = new Alert(alertDTO.type(), alertDTO.message(), alertDTO.patientId(),alertDTO.value());
        alertRepository.persist(alert);
        return Response.status(Response.Status.CREATED).entity(alert).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAlert(@PathParam("id") String id, AlertDTO alertDTO) throws IncorrectRequestException, ResourceNotFoundException {
        validateAlertDTO(alertDTO);
        return findAlertById(id)
                .map(alert -> updateAlertFields(alert, alertDTO))
                .map(alert -> {
                    alertRepository.update(alert);
                    return Response.ok(alert).build();
                })
                .orElseThrow(() -> new ResourceNotFoundException("Alert with ID " + id + " not found."));
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAlert(@PathParam("id") String id) throws ResourceNotFoundException, IncorrectRequestException {
        validateId(id);
        boolean isDeleted = alertRepository.deleteById(new ObjectId(id));
        if (isDeleted) {
            return Response.noContent().build();
        }
        throw new ResourceNotFoundException("Alert with ID " + id + " not found.");
    }

    @PATCH
    @Path("/{id}/mark-treated")
    @Produces(MediaType.APPLICATION_JSON)
    public Response markAlertAsTreated(@PathParam("id") String id) throws IncorrectRequestException, ResourceNotFoundException {
        return findAlertById(id)
                .map(alert -> {
                    alert.setTreated(true);
                    alertRepository.update(alert);
                    return Response.ok(alert).build();
                })
                .orElseThrow(() -> new ResourceNotFoundException("Alert with ID " + id + " not found."));
    }

    private Optional<Alert> findAlertById(String id) throws IncorrectRequestException {
        validateId(id);
        return alertRepository.findByIdOptional(new ObjectId(id));
    }

    private void validateId(String id) throws IncorrectRequestException {
        if (id == null || id.isEmpty()) {
            throw new IncorrectRequestException("ID must be provided");
        }
        if (!ObjectId.isValid(id)) {
            throw new IncorrectRequestException("Invalid ID format. ID must be a 24-character hexadecimal string.");
        }
    }

    private void validateAlertDTO(AlertDTO alertDTO) throws IncorrectRequestException {
        if (alertDTO == null || alertDTO.type() == null || alertDTO.type().isEmpty()) {
            throw new IncorrectRequestException("AlertDTO must be provided with a valid type");
        }
        if (alertDTO.message() == null || alertDTO.message().isEmpty()) {
            throw new IncorrectRequestException("Missing alert message");
        }
    }

    private Alert updateAlertFields(Alert alert, AlertDTO alertDTO) {
        if (alertDTO.type() != null && !alertDTO.type().isEmpty()) {
            alert.setType(alertDTO.type());
        }
        if (alertDTO.message() != null && !alertDTO.message().isEmpty()) {
            alert.setMessage(alertDTO.message());
        }
        return alert;
    }
}

package fr.etu.polytech.controller;

import fr.etu.polytech.dto.AlertDTO;
import fr.etu.polytech.entity.Alert;
import fr.etu.polytech.exception.IncorrectRequestException;
import fr.etu.polytech.exception.ResourceNotFoundException;
import fr.etu.polytech.repository.AlertRepository;
import io.quarkus.scheduler.Scheduled;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@Path("/alert")
public class AlertResource {
    private static final Logger LOGGER = Logger.getLogger(AlertResource.class.getName());

    @Inject
    AlertRepository alertRepository;

    @GET
    @Path("/{gatewayId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByGatewayId(
            @PathParam("gatewayId") String gatewayId,
            @QueryParam("limit") Integer limit,
            @QueryParam("offset") Integer offset) throws IncorrectRequestException, ResourceNotFoundException{

        validateGatewayId(gatewayId);

        LOGGER.info("Received request to get gateway by id: " + gatewayId);
        List<Alert> alerts = alertRepository.findByGatewayId(gatewayId);

        if(limit != null && alerts.size() > limit ){
            int effectiveOffset = offset == null ? 0 : offset;
            alerts = alerts.stream()
                    .skip(effectiveOffset)
                    .limit(limit)
                    .collect(Collectors.toList());
        }

        if (alerts.isEmpty()) {
            LOGGER.warning("No alerts found for gateway ID: " + gatewayId);
            return Response.status(Response.Status.NOT_FOUND).entity("No alerts found for gateway ID " + gatewayId).build();
        }
        return Response.ok(alerts).build();
    }

    @GET
    @Path("/{alertId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAlertById(@PathParam("alertId") String alertId) throws IncorrectRequestException, ResourceNotFoundException {
        LOGGER.info("Received request to get alert with ID: " + alertId);
        return findAlertById(alertId)
                .map(alert -> {
                    LOGGER.info("Alert found with ID: " + alertId);
                    return Response.ok(alert).build();
                })
                .orElseThrow(() -> {
                    LOGGER.warning("Alert not found with ID: " + alertId);
                    return new ResourceNotFoundException("Alert with ID " + alertId + " not found.");
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
        Alert alert = new Alert(alertDTO.type(), alertDTO.message(), alertDTO.gatewayId());
        alertRepository.persist(alert);
        return Response.status(Response.Status.CREATED).entity(alert).build();
    }

    @PUT
    @Path("/{alertId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAlert(@PathParam("alertId") String alertId, AlertDTO alertDTO) throws IncorrectRequestException, ResourceNotFoundException {
        validateAlertDTO(alertDTO);
        return findAlertById(alertId)
                .map(alert -> updateAlertFields(alert, alertDTO))
                .map(alert -> {
                    alertRepository.update(alert);
                    return Response.ok(alert).build();
                })
                .orElseThrow(() -> new ResourceNotFoundException("Alert with ID " + alertId + " not found."));
    }

    @DELETE
    @Path("/{alertId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAlert(@PathParam("alertId") String alertId) throws ResourceNotFoundException, IncorrectRequestException {
        validateId(alertId);
        boolean isDeleted = alertRepository.deleteById(new ObjectId(alertId));
        if (isDeleted) {
            return Response.noContent().build();
        }
        throw new ResourceNotFoundException("Alert with ID " + alertId + " not found.");
    }

    @PATCH
    @Path("/{alertId}/mark-treated")
    @Produces(MediaType.APPLICATION_JSON)
    public Response markAlertAsTreated(@PathParam("alertId") String alertId) throws IncorrectRequestException, ResourceNotFoundException {
        return findAlertById(alertId)
                .map(alert -> {
                    alert.setTreated(true);
                    alertRepository.update(alert);
                    return Response.ok(alert).build();
                })
                .orElseThrow(() -> new ResourceNotFoundException("Alert with ID " + alertId + " not found."));
    }

   @Scheduled(every = "10m")
   void checkUnresolvedAlerts() {
        List<Alert> unresolvedAlerts = alertRepository.findUnresolvedAlerts();
        for (Alert alert : unresolvedAlerts) {
            if(alert.getTimestamp().isBefore(LocalDateTime.now().minusHours(1))){
                LOGGER.info("Reminder sent for unresolved alert with ID: " + alert.id);

            }
        }
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
    private void validateGatewayId(String gatewayId) throws IncorrectRequestException {
        if (gatewayId == null || gatewayId.isEmpty()) {
            throw new IncorrectRequestException("Gateway ID must be provided");
        }
    }

}

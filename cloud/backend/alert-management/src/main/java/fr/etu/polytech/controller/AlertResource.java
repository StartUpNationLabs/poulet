package fr.etu.polytech.controller;

import fr.etu.polytech.dto.AlertDTO;
import fr.etu.polytech.entity.Alert;
import fr.etu.polytech.entity.Severity;
import fr.etu.polytech.exception.IncorrectRequestException;
import fr.etu.polytech.exception.ResourceNotFoundException;
import fr.etu.polytech.repository.AlertRepository;
import io.quarkus.scheduler.Scheduled;
import jakarta.inject.Inject;
import jakarta.validation.executable.ValidateOnExecution;
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
import jakarta.validation.Valid;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;


@OpenAPIDefinition(
        info = @Info(
                title = "Alert Management API",
                version = "1.0.0",
                description = "API to manage alerts."
        ),
        tags = {
                @Tag(name = "alert", description = "Operations related to alerts")
        }
)



@Path("/alert")
@ValidateOnExecution
public class AlertResource {
    private static final Logger LOGGER = Logger.getLogger(AlertResource.class.getName());

    @Inject
    AlertRepository alertRepository;

    @GET
    @Path("/gateway/{gatewayId}")
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

    @GET
    @Path("/severity/{severity}")
    public List<Alert> getAlertsBySeverity(@PathParam("severity") String severity) {
        try {
            Severity sev = Severity.valueOf(severity.toUpperCase());
            return alertRepository.findBySeverity(sev);
        } catch (IllegalArgumentException e) {
            throw new WebApplicationException("Invalid severity level", 400);
        }
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAlert(@Valid AlertDTO alertDTO) throws IncorrectRequestException {
        Alert alert = new Alert(alertDTO.time(),alertDTO.type(), alertDTO.message(), alertDTO.gatewayId(),alertDTO.value(),alertDTO.severity());
        alertRepository.persist(alert);
        return Response.status(Response.Status.CREATED).entity(alert).build();
    }

    @PUT
    @Path("/{alertId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAlert(@PathParam("alertId") String alertId, @Valid AlertDTO alertDTO) throws IncorrectRequestException, ResourceNotFoundException {
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

    @GET
    @Path("/treated")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Alert> getTreatedAlerts() {
        return alertRepository.findByTreatedStatus(true);
    }

    @GET
    @Path("/untreated")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Alert> getUntreatedAlerts() {
        return alertRepository.findByTreatedStatus(false);
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


    public Optional<Alert> findAlertById(String id) throws IncorrectRequestException {
        validateId(id);
        Optional<Alert> alert = alertRepository.findByIdOptional(new ObjectId(id));
        LOGGER.info("Found alert: " + alert);
        return alert;
    }


    public void validateId(String id) throws IncorrectRequestException {
        if (id == null || id.isEmpty()) {
            throw new IncorrectRequestException("ID must be provided");
        }
        if (!ObjectId.isValid(id)) {
            throw new IncorrectRequestException("Invalid ID format. ID must be a 24-character hexadecimal string.");
        }
    }



    public Alert updateAlertFields(Alert alert, @Valid AlertDTO alertDTO) {
        alert.setType(alertDTO.type());
        alert.setMessage(alertDTO.message());
        return alert;
    }
    public void validateGatewayId(String gatewayId) throws IncorrectRequestException {
        if (gatewayId == null || gatewayId.isEmpty()) {
            throw new IncorrectRequestException("Gateway ID must be provided");
        }
    }



}

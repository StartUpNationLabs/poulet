package fr.etu.polytech.controller;

import fr.etu.polytech.dto.AlertDTO;
import fr.etu.polytech.entity.Alert;
import fr.etu.polytech.entity.Severity;
import fr.etu.polytech.exception.IncorrectRequestException;
import fr.etu.polytech.exception.ResourceNotFoundException;
import fr.etu.polytech.repository.AlertRepository;
import io.quarkus.scheduler.Scheduled;
import jakarta.inject.Inject;
import jakarta.validation.constraints.Pattern;
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
    private static final String alertIdErrorMessage = "Missing or invalid alert ID format. ID must be a 24-character hexadecimal string.";


    @GET
    @Path("/gateway/{gatewayId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Alert>  getByGatewayId(
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


        return alerts;
    }

    @GET
    @Path("/{alertId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Alert getAlertById(@PathParam("alertId") @Pattern(regexp = "^[0-9a-fA-F]{24}$", message = alertIdErrorMessage) String alertId) throws ResourceNotFoundException {
        Alert alert = alertRepository.findByAlertId(new ObjectId(alertId));
        if (alert == null) {
            throw new ResourceNotFoundException("Alert with ID " + alertId + " not found.");
        }
        return alert;
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
    public Alert  createAlert(@Valid AlertDTO alertDTO) throws IncorrectRequestException {
        Alert alert = new Alert(alertDTO.time(),alertDTO.type(), alertDTO.message(), alertDTO.gatewayId(),alertDTO.value(),alertDTO.severity());
        alertRepository.persist(alert);
        return alert;
    }

    @PUT
    @Path("/{alertId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Alert updateAlert(@PathParam("alertId") @Pattern(regexp = "^[0-9a-fA-F]{24}$", message = alertIdErrorMessage) String alertId,
                             @Valid AlertDTO alertDTO) throws IncorrectRequestException, ResourceNotFoundException {
        Alert alert = alertRepository.findByAlertId(new ObjectId(alertId));
        if (alert == null) {
            throw new ResourceNotFoundException("Alert with ID " + alertId + " not found.");
        }
        updateAlertFields(alert, alertDTO);
        alertRepository.update(alert);
        return alert;
    }

    @DELETE
    @Path("/{alertId}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteAlert(@PathParam("alertId") @Pattern(regexp = "^[0-9a-fA-F]{24}$", message = alertIdErrorMessage) String alertId) throws ResourceNotFoundException, IncorrectRequestException {
        validateId(alertId);
        boolean isDeleted = alertRepository.deleteByIdR(new ObjectId(alertId));
        if (!isDeleted) {
            throw new ResourceNotFoundException("Alert with ID " + alertId + " not found.");
        }
    }
    @DELETE
    @Path("/deleteAll")
    public void deleteAllAlerts() {
        alertRepository.deleteAll();
    }

    @PATCH
    @Path("/{alertId}/mark-treated")
    @Produces(MediaType.APPLICATION_JSON)
    public Alert markAlertAsTreated(@PathParam("alertId") @Pattern(regexp = "^[0-9a-fA-F]{24}$", message = alertIdErrorMessage) String alertId) throws IncorrectRequestException, ResourceNotFoundException {
        Alert alert = alertRepository.findByAlertId(new ObjectId(alertId));
        if (alert == null) {
            throw new ResourceNotFoundException("Alert with ID " + alertId + " not found.");
        }
        alert.setTreated(true);
        alertRepository.update(alert);
        return alert;
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

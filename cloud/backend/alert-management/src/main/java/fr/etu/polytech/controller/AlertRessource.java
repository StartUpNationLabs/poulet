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

@Path("/alert")
public class AlertRessource {
    @Inject
    AlertRepository alertRepository;
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAlertById(@PathParam("id") String id) throws ResourceNotFoundException, IncorrectRequestException {
        if (id == null || id.isEmpty()) {
            throw new IncorrectRequestException("ID must be provided");
        }

        if (!ObjectId.isValid(id)) {
            throw new IncorrectRequestException("Invalid ID format. ID must be a 24-character hexadecimal string.");
        }
        return alertRepository.findByIdOptional(new ObjectId(id))
                .map(alert -> Response.ok(alert).build())
                .orElseThrow(() -> new ResourceNotFoundException("Alert with ID " + id + " not found."));
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Alert> getAllAlerts() {
        return alertRepository.listAll();
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAlert(AlertDTO alertDTO) throws ResourceNotFoundException, IncorrectRequestException {
        if (alertDTO.type() == null || alertDTO.type().isEmpty()) {
            throw new IncorrectRequestException("AlertDTO must be provided");
        }
        if(alertDTO.message() == null || alertDTO.message().isEmpty()) {
            throw new IncorrectRequestException("Missing alert message");
        }
        Alert alert = new Alert(alertDTO.type(), alertDTO.message(), alertDTO.patientId());
        alertRepository.persist(alert);

        return Response.status(Response.Status.CREATED)
                .entity(alert)
                .build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAlert(@PathParam("id") String id) throws ResourceNotFoundException, IncorrectRequestException {
        if (id == null || id.isEmpty()) {
            throw new IncorrectRequestException("ID must be provided");
        }
        if (!ObjectId.isValid(id)) {
            throw new IncorrectRequestException("Invalid ID format. ID must be a 24-character hexadecimal string.");
        }
        boolean isDeleted = alertRepository.deleteById(new ObjectId(id));
        if (isDeleted) {
            return Response.ok().build();
        }
        throw new ResourceNotFoundException("Alert with ID " + id + " not found.");
    }
}

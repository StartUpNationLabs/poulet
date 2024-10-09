package fr.etu.polytech.controller.rest;

import fr.etu.polytech.dto.PatientDTO;
import fr.etu.polytech.entity.Patient;
import fr.etu.polytech.exception.IncorrectRequestException;
import fr.etu.polytech.exception.ResourceNotFoundException;
import fr.etu.polytech.repository.PatientRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;

@Path("/patient")
public class PatientResource {

    @Inject
    PatientRepository repository;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPatientById(@PathParam("id") String id) throws ResourceNotFoundException, IncorrectRequestException {
        if (id == null || id.isEmpty()) {
            throw new IncorrectRequestException("ID must be provided");
        }

        // Validate that id is a valid ObjectId
        if (!ObjectId.isValid(id)) {
            throw new IncorrectRequestException("Invalid ID format. ID must be a 24-character hexadecimal string.");
        }

        return repository.findByIdOptional(new ObjectId(id))
                .map(patient -> Response.ok(patient).build())
                .orElseThrow(() -> new ResourceNotFoundException("Patient with ID " + id + " not found."));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPatient(PatientDTO dto) throws IncorrectRequestException {
        System.out.println(dto.firstname()+ " " + dto.lastname());
        if(dto.firstname() == null || dto.firstname().isEmpty()) {
            throw new IncorrectRequestException("Missing patient firstname");
        }
        if(dto.lastname() == null || dto.lastname().isEmpty()) {
            throw new IncorrectRequestException("Missing patient lastname");
        }

        Patient patient = new Patient(dto.firstname(), dto.lastname());
        repository.persist(patient); // Insertion in the db and creation of the id
        return Response.status(Response.Status.CREATED)
                .entity(patient)
                .build();   
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePatientById(@PathParam("id") String id) throws ResourceNotFoundException, IncorrectRequestException {
        if (id == null || id.isEmpty()) {
            throw new IncorrectRequestException("ID must be provided");
        }

        // Validate that id is a valid ObjectId
        if (!ObjectId.isValid(id)) {
            throw new IncorrectRequestException("Invalid ID format. ID must be a 24-character hexadecimal string.");
        }

        boolean isDeleted = repository.deleteById(new ObjectId(id));

        if (isDeleted) {
            return Response.ok().build();
        }

        throw new ResourceNotFoundException("Patient with ID " + id + " not found.");
    }
}


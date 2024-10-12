package fr.etu.polytech.controller;

import fr.etu.polytech.dto.PatientDTO;
import fr.etu.polytech.entity.Patient;
import fr.etu.polytech.enumeration.Gender;
import fr.etu.polytech.exception.ResourceNotFoundException;
import fr.etu.polytech.repository.PatientRepository;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;

@Path("/patient")
public class PatientResource {
    private static final String patientIdErrorMessage = "Missing or invalid patient ID format. ID must be a 24-character hexadecimal string.";

    @Inject
    PatientRepository repository;

    @GET
    @Path("/{patientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPatientById(@PathParam("patientId") @Pattern(regexp = "^[0-9a-fA-F]{24}$", message = patientIdErrorMessage) String patientId) throws ResourceNotFoundException, ConstraintViolationException {
        return repository.findByIdOptional(new ObjectId(patientId)).map(patient -> Response.ok(patient).build()).orElseThrow(() -> new ResourceNotFoundException("Patient with ID " + patientId + " not found."));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPatient(@Valid PatientDTO dto) throws ConstraintViolationException {
        Patient patient = new Patient(dto.firstname(), dto.lastname(), Gender.fromAbbreviation(dto.gender()));
        repository.persist(patient);
        return Response.status(Response.Status.CREATED).entity(patient).build();
    }


    @DELETE
    @Path("/{patientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePatientById(@PathParam("patientId") @Pattern(regexp = "^[0-9a-fA-F]{24}$", message = patientIdErrorMessage) String patientId) throws ResourceNotFoundException, ConstraintViolationException {
        if (repository.deleteById(new ObjectId(patientId))) {
            return Response.ok().build();
        }

        throw new ResourceNotFoundException("Patient with ID " + patientId + " not found.");
    }
}


package fr.etu.polytech.controller;

import fr.etu.polytech.dto.PatientDTO;
import fr.etu.polytech.entity.Patient;
import fr.etu.polytech.enumeration.Gender;
import fr.etu.polytech.exception.ResourceNotFoundException;
import fr.etu.polytech.repository.PatientRepository;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Set;

@OpenAPIDefinition(
        info = @Info(
                title = "Patient Management API",
                version = "1.0.0",
                description = "API to manage patients in a hospital."
        ),
        tags = {
                @Tag(name = "patient", description = "Operations related to patients")
        }
)
@Path("/api/patient")
public class PatientResource {
    private static final String patientIdErrorMessage = "Missing or invalid patient ID format. ID must be a 24-character hexadecimal string.";
    private static final String gatewayIdErrorMessage = "Missing or invalid gateway ID format. ID must be a 24-character hexadecimal string.";

    @Inject
    PatientRepository repository;

    @GET
    @Path("/{patientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Patient getPatientById(@PathParam("patientId") @Pattern(regexp = "^[0-9a-fA-F]{24}$", message = patientIdErrorMessage) String patientId) throws ResourceNotFoundException, ConstraintViolationException {
        return repository.findByIdOptional(new ObjectId(patientId))
                .orElseThrow(() -> new ResourceNotFoundException("Patient with ID " + patientId + " not found."));
    }

    @GET
    @Path("/gateway/{gatewayId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Patient getPatientByGatewayId(@PathParam("gatewayId") @Pattern(regexp = "^[0-9a-fA-F]{24}$", message = gatewayIdErrorMessage) String gatewayId) throws ResourceNotFoundException, ConstraintViolationException {
        return repository.find("gatewayId", new ObjectId(gatewayId))
                .firstResultOptional()
                .orElseThrow(() -> new ResourceNotFoundException("Patient with gateway ID " + gatewayId + " not found."));
    }

    @GET
    @Path("/findByName")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Patient> getPatientByFirstnameAndLastname(
            @QueryParam("firstname") @NotBlank(message = "Firstname cannot be blank") String firstname,
            @QueryParam("lastname") @NotBlank(message = "Lastname cannot be blank") String lastname
    ) throws ResourceNotFoundException, ConstraintViolationException {
        Set<Patient> patients = repository.findByFirstnameAndLastname(firstname, lastname);
        if (patients.isEmpty()) {
            throw new ResourceNotFoundException("No patients found with the given first name and last name.");
        }
        return patients;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Patient createPatient(@Valid PatientDTO dto) throws ConstraintViolationException {
        Patient patient = new Patient(dto.firstname(), dto.lastname(), Gender.fromAbbreviation(dto.gender()), dto.emergencyContactPhoneNumber());
        repository.persist(patient);
        return patient;
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


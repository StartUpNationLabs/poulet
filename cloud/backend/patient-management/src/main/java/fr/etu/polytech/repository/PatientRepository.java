package fr.etu.polytech.repository;

import fr.etu.polytech.entity.Patient;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PatientRepository implements PanacheMongoRepository<Patient> {
}

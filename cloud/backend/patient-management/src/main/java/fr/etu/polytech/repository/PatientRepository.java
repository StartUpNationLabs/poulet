package fr.etu.polytech.repository;

import fr.etu.polytech.entity.Patient;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.regex.Pattern;

import java.util.List;

@ApplicationScoped
public class PatientRepository implements PanacheMongoRepository<Patient> {

    public List<Patient> findByFirstnameAndLastname(String firstname, String lastname) {
        Pattern firstNamePattern = Pattern.compile(firstname, Pattern.CASE_INSENSITIVE);
        Pattern lastNamePattern = Pattern.compile(lastname, Pattern.CASE_INSENSITIVE);
        return list("{ 'firstname': ?1, 'lastname': ?2 }", firstNamePattern, lastNamePattern);
    }
}

package fr.etu.polytech.repository;

import fr.etu.polytech.entity.Patient;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import java.util.List;

@ApplicationScoped
public class PatientRepository implements PanacheMongoRepository<Patient> {

    public Set<Patient> findByFirstnameAndLastname(String firstname, String lastname) {
        String firstnameRegex = "^" + firstname + "$";
        String lastnameRegex = "^" + lastname + "$";

        return new HashSet<>(list(
                "{ 'firstname': { '$regex': ?1, '$options': 'i' }, 'lastname': { '$regex': ?2, '$options': 'i' } }",
                firstnameRegex,
                lastnameRegex
        ));
    }
}

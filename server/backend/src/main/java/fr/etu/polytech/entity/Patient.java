package fr.etu.polytech.entity;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Patient extends PanacheMongoEntity {
    public String firstname;
    public String lastname;

    // No-arg constructor (needed for MongoDB)
    public Patient() {
    }

    // Constructor with all fields
    public Patient(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }
}

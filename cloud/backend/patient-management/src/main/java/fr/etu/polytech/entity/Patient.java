package fr.etu.polytech.entity;

import fr.etu.polytech.enumeration.Gender;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
public class Patient extends PanacheMongoEntity {
    public String firstname;
    public String lastname;
    public Gender gender;
    public ObjectId gatewayId;

    // No-arg constructor (needed for MongoDB)
    public Patient() {
    }

    // Constructor with all fields
    public Patient(String firstname, String lastname, Gender gender) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.gatewayId = new ObjectId();
        this.gender = gender;
    }
}

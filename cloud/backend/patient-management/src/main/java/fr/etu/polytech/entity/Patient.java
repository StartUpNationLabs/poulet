package fr.etu.polytech.entity;

import fr.etu.polytech.enumeration.Gender;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;


@Getter
@Setter
public class Patient extends PanacheMongoEntity {
    public String firstname;
    public String lastname;
    public Gender gender;

    @Schema(type = SchemaType.STRING, description = "Gateway ID as a 24-character hexadecimal string", implementation = String.class)
    public ObjectId gatewayId;
    @Schema(type = SchemaType.STRING, description = "Patient ID as a 24-character hexadecimal string", implementation = String.class)
    public ObjectId id;

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

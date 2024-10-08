package fr.etu.polytech.entity;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Patient extends PanacheMongoEntity {
    public String firstname;
    public String lastname;

    public Patient(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }
}

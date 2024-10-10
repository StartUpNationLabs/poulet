package fr.etu.polytech.entity;


import io.quarkus.mongodb.panache.PanacheMongoEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Alert extends PanacheMongoEntity {
    public String type;
    public String message;

    public Alert() {}

    public Alert(String type, String message) {
        this.type = type;
        this.message = message;
    }
}
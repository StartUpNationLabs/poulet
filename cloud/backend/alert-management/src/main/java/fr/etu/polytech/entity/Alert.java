package fr.etu.polytech.entity;


import io.quarkus.mongodb.panache.PanacheMongoEntity;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;


@Getter
@Setter
public class Alert extends PanacheMongoEntity {
    public String type;
    public String message;
    public String patientId;
    public LocalDateTime timestamp;
    public boolean treated;

    public Alert() {}

    public Alert(String type, String message, String patientId) {
        this.type = type;
        this.message = message;
        this.patientId = patientId;
        this.timestamp = LocalDateTime.now();
        this.treated = false;
    }
}
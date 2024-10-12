package fr.etu.polytech.entity;


import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;


@RegisterForReflection
@Getter
@Setter
public class Alert extends PanacheMongoEntity {
    public String type;
    public String message;
    public String patientId;
    public LocalDateTime timestamp;
    public boolean treated;
    private int value;

    public Alert() {}

    public Alert(String type, String message, String patientId, int value) {
        this.type = type;
        this.message = message;
        this.patientId = patientId;
        this.timestamp = LocalDateTime.now();
        this.treated = false;
        this.value = value;
    }
}
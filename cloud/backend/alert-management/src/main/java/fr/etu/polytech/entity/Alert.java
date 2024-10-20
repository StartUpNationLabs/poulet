package fr.etu.polytech.entity;


import fr.etu.polytech.enumerations.Severity;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Date;


@RegisterForReflection
@Getter
@Setter
public class Alert extends PanacheMongoEntity {
    @Schema(type = SchemaType.STRING, description = "Alert ID as a 24-character hexadecimal string", implementation = String.class)
    public ObjectId id;
    public Date time;
    public String type;
    public String message;
    public String gatewayId;
    public LocalDateTime timestamp;
    public boolean treated;
    public boolean modified;
    private float value;
    private Severity severity;

    public Alert() {
        //date enregistrement de l alerte
        this.timestamp = LocalDateTime.now();
        this.treated = false;
        this.modified = false;
    }
    public Alert(Date time, String type, String message, String gatewayId, float value, Severity severity) {
        this();
        this.time = time;
        this.type = type;
        this.message = message;
        this.gatewayId = gatewayId;
        this.value = value;
        this.severity = severity;
    }

    public Alert(String type, String message, String gatewayId, int value) {
        this();
        this.type = type;
        this.message = message;
        this.gatewayId = gatewayId;
        this.value = value;
    }



}
package fr.etu.polytech.entity;


import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;


@RegisterForReflection
@Getter
@Setter
public class Alert extends PanacheMongoEntity {
    public String type;
    public String message;
    public String gatewayId;
    public LocalDateTime timestamp;
    public boolean treated;
    private int value;
    private Severity severity;

    public Alert() {
        this.timestamp = LocalDateTime.now();
        this.treated = false;
    }
    public Alert(String type, String message, String gatewayId, int value,Severity severity) {
        this();
        this.type = type;
        this.message = message;
        this.gatewayId = gatewayId;
        this.value = value;
        this.severity = severity;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isTreated() {
        return treated;
    }

    public void setTreated(boolean treated) {
        this.treated = treated;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }
}
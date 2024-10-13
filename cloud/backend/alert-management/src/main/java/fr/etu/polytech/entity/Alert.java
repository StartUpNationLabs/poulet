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
    public String gatewayId;
    public LocalDateTime timestamp;
    public boolean treated;
    private int value;

    public Alert() {}

    public Alert(String type, String message, String gatewayId, int value) {
        this.type = type;
        this.message = message;
        this.gatewayId = gatewayId;
        this.timestamp = LocalDateTime.now();
        this.treated = false;
        this.value = value;
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
}
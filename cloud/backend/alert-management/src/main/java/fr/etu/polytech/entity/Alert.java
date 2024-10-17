package fr.etu.polytech.entity;


import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.Date;


@RegisterForReflection
@Getter
@Setter
public class Alert extends PanacheMongoEntity {
    public Date time;
    public String type;
    public String message;
    public String gatewayId;
    public LocalDateTime timestamp;
    public boolean treated;
    private int value;
    private Severity severity;

    public Alert() {
        //date enregistrement de l alerte
        this.timestamp = LocalDateTime.now();
        this.treated = false;
    }
    public Alert(Date time, String type, String message, String gatewayId, int value, Severity severity) {
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
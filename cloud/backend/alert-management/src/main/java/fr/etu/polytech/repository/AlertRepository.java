package fr.etu.polytech.repository;
import fr.etu.polytech.entity.Alert;

import fr.etu.polytech.entity.Severity;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;


@ApplicationScoped
public class AlertRepository implements PanacheMongoRepository<Alert> {

    public List<Alert> findUnresolvedAlerts() {
        return list("treated = false");
    }

    public List<Alert> findByGatewayId(String gatewayId) {
        return find("gatewayId", gatewayId).list();
    }

    public List<Alert> findBySeverity(Severity severity) {
        return find("severity", severity).list();
    }
}

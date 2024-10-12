package fr.etu.polytech.repository;
import fr.etu.polytech.entity.Alert;

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

}

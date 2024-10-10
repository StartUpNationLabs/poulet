package fr.etu.polytech.repository;
import fr.etu.polytech.entity.Alert;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class AlertRepository implements PanacheMongoRepository<Alert> {

}

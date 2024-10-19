package fr.etu.polytech.mapper;

import fr.etu.polytech.dto.AlertDTO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.logging.Logger;

public class AlertMapper {
    private static final Logger LOGGER = Logger.getLogger(AlertMapper.class.getName());

    public static AlertDTO toAlertDTO(Map<String, Object> alertData) {
        LOGGER.info("post receive " + alertData);

        // Vérifie l'existence de "labels" et "annotations"
        Map<String, Object> labels = (Map<String, Object>) alertData.get("labels");
        Map<String, Object> annotations = (Map<String, Object>) alertData.get("annotations");

        // Log si les labels ou annotations sont nuls
        if (labels == null) {
            LOGGER.warning("Labels are null.");
        }

        if (annotations == null) {
            LOGGER.warning("Annotations are null.");
        }

        // Initialise les variables avec des valeurs par défaut ou gère les null
        String type = (labels != null) ? (String) labels.get("type") : "unknown";
        String gatewayId = (labels != null) ? (String) labels.get("gatewayId") : "unknown";
        String severity = (labels != null) ? (String) labels.get("severity") : "UNKNOWN";
        float value = (labels != null && labels.get("value") != null)
                ? Float.parseFloat((String) labels.get("value"))
                : 0.0f;
        String message = (annotations != null) ? (String) annotations.get("message") : "No message available";

        // Récupérer la date de l'alerte à partir de startsAt
        String timeStr = (String) alertData.get("startsAt"); // Assurez-vous d'extraire startsAt directement à partir de alertData
        Date time = parseDateFromString(timeStr);

        return new AlertDTO(time, type, message, gatewayId, value, severity);
    }



    // Méthode pour convertir une chaîne de caractères en date
    private static Date parseDateFromString(String timeStr) {
        try {
            // Adapter le format en fonction de la chaîne reçue
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(timeStr);
        } catch (Exception e) {
            LOGGER.severe("Erreur de conversion de la date : " + e.getMessage());
            return new Date(); // Retourner la date actuelle en cas d'erreur
        }
    }
}

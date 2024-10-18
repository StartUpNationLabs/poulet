package fr.etu.polytech.controller;

import fr.etu.polytech.entity.Alert;
import fr.etu.polytech.repository.AlertRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class AlertResourceTest {

    @Inject
    AlertResource alertResource;

    @Inject
    AlertRepository alertRepository;

    @AfterEach
    void cleanup() {
        alertRepository.deleteAll();
    }

    @Test
    void testDefaultConstructor() {
        Alert alert = new Alert();

        assertNotNull(alert.getTimestamp());
        assertFalse(alert.isTreated());
    }
    /*
    @Test
    public void testCreateAlert() throws IncorrectRequestException {
        AlertDTO alertDTO = new AlertDTO("TestType", "Test message", "gatewayId", 0, Severity.LOW);
        Response response = alertResource.createAlert(alertDTO);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        Alert createdAlert = (Alert) response.getEntity();
        assertEquals("TestType", createdAlert.getType());
    }
    @Test
    void testParameterizedConstructor() {
        Alert alert = new Alert("Temperature", "High temperature detected", "gateway-1", 100, Severity.CRITICAL);

        assertEquals("Temperature", alert.getType());
        assertEquals("High temperature detected", alert.getMessage());
        assertEquals("gateway-1", alert.getGatewayId());
        assertEquals(100, alert.getValue());
        assertEquals(Severity.CRITICAL, alert.getSeverity());
        assertNotNull(alert.getTimestamp());
        assertFalse(alert.isTreated());
    }

    @Test
    void testGetAlertsBySeverity() {
        Alert alert1 = new Alert("Temperature", "High temperature detected", "gateway-1", 100, Severity.CRITICAL);
        Alert alert2 = new Alert("Temperature", "Normal temperature", "gateway-2", 70, Severity.LOW);
        alertRepository.persist(alert1);
        alertRepository.persist(alert2);

            List<Alert> highSeverityAlerts = alertResource.getAlertsBySeverity("CRITICAL");

        assertEquals(1, highSeverityAlerts.size());
        assertEquals(alert1.getType(), highSeverityAlerts.get(0).getType());
        assertEquals(alert1.getMessage(), highSeverityAlerts.get(0).getMessage());
        assertEquals(alert1.getGatewayId(), highSeverityAlerts.get(0).getGatewayId());
        assertEquals(alert1.getValue(), highSeverityAlerts.get(0).getValue());
        assertEquals(alert1.getSeverity(), highSeverityAlerts.get(0).getSeverity());
    }

    @Test
    void testDeleteAlert() throws IncorrectRequestException, ResourceNotFoundException {
        Alert alert = new Alert("Temperature", "High temperature detected", "gateway-1", 100, Severity.CRITICAL);
        alertRepository.persist(alert);

        Response response = alertResource.deleteAlert(alert.id.toHexString());

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        assertFalse(alertRepository.findByIdOptional(alert.id).isPresent());
    }*/

    /*@Test
    void testUpdateAlert() throws IncorrectRequestException, ResourceNotFoundException {
        Alert alert = new Alert("Temperature", "High temperature detected", "gateway-1", 100, Severity.CRITICAL);
        alertRepository.persist(alert);

        AlertDTO updateDTO = new AlertDTO("Temperature", "Updated message", "gateway-1", 90, Severity.MEDIUM);
        Response response = alertResource.updateAlert(alert.id.toHexString(), updateDTO);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Alert updatedAlert = (Alert) response.getEntity();
        assertEquals("Updated message", updatedAlert.getMessage());
        assertEquals(90, updatedAlert.getValue());
        assertEquals(Severity.MEDIUM, updatedAlert.getSeverity());
    }*/




}

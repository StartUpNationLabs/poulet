package fr.etu.polytech.dto;

public record AlertDTO(String type, String message, String patientId, int value) {
}


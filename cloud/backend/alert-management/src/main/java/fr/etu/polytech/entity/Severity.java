package fr.etu.polytech.entity;

public enum Severity {
    LOW,
    MEDIUM,
    INFO,
    WARNING,
    CRITICAL;

    public static Severity fromString(String value) {
        for (Severity severity : Severity.values()) {
            if (severity.name().equalsIgnoreCase(value)) {
                return severity;
            }
        }
        return null;
    }
}

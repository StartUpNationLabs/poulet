package fr.etu.polytech.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PatientDTO(
        @NotBlank(message = "Firstname is required")
        String firstname,

        @NotBlank(message = "Lastname is required")
        String lastname,

        @NotNull(message = "Missing gender, should be 'M' (male), 'F' (female), or 'U' (unspecified/other).")
        @Pattern(regexp = "^[MFU]$", message = "Invalid gender must be 'M' (male), 'F' (female), or 'U' (unspecified/other).")
        String gender,

        @NotNull(message = "Missing emergency contact phone number, it's a string with 10 digit.")
        @Pattern(regexp= "^[0-9]{10}$", message = "Invalid emergency contact phone number, it's a string with 10 digit.")
        String emergencyContactPhoneNumber) {
}

package fr.etu.polytech.enumeration;

public enum Gender {
    MALE("M"),
    FEMALE("F"),
    UNSPECIFIED("U");

    private final String abbreviation;

    Gender(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public static Gender fromAbbreviation(String abbreviation) {
        for (Gender gender : Gender.values()) {
            if (gender.abbreviation.equals(abbreviation)) {
                return gender;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return abbreviation;
    }
}

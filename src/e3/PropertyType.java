package e3;

public enum PropertyType {
    APARTMENT("APARTMENT"),
    LOCAL("LOCAL");

    private final String description;

    // Constructor
    PropertyType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}


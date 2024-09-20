package e3;

public enum AdType {
    PURCHASE("Purchase"),
    RENTAL("Rental");

    private final String description;

    // Constructor privado para el enum
    AdType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}

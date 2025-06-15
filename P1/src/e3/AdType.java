package e3;

public enum AdType {
    PURCHASE("Purchase"),
    RENTAL("Rental");

    private final String description;

    // Constructor
    AdType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}

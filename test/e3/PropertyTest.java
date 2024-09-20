import java.util.Objects;

public record Property(PropertyType propertyType, String cadastralNumber, String address, String postalCode,
                       int squareMeters, int rooms, int bathrooms) {

    // Sobrescribimos equals para definir la igualdad según el número de catastro
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return cadastralNumber.equals(property.cadastralNumber);
    }

    // Sobrescribimos hashCode para mantener consistencia con equals
    @Override
    public int hashCode() {
        return Objects.hash(cadastralNumber);
    }

    // Sobrescribimos toString para que se ajuste al formato especificado en el test
    @Override
    public String toString() {
        return propertyType + "\n" +
                cadastralNumber + "\n" +
                address + "\n" +
                postalCode + "\n" +
                squareMeters + " meters, " + rooms + " rooms, " + bathrooms + " bathrooms";
    }
}

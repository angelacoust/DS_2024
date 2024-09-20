import e3.PropertyType;
import java.util.Objects;

public record Property(PropertyType propertyType, String cadastralNumber, String address, String postalCode,
                       int squareMeters, int rooms, int bathrooms) {

    @Override
    public String toString() {
        return propertyType + "\n" +
                cadastralNumber + "\n" +
                address + "\n" +
                postalCode + "\n" +
                squareMeters + " meters, " + rooms + " rooms, " + bathrooms + " bathrooms";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return cadastralNumber.equals(property.cadastralNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cadastralNumber);
    }
}

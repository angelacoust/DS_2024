package e3;

import java.util.Objects;

public class Property {
    private final PropertyType type;
    private final String cadaster;
    private final String address;
    private final String postalCode;
    private final int meters;
    private final int rooms;
    private final int bathrooms;

    public Property(PropertyType type, String cadaster, String address, String postalCode, int meters, int rooms, int bathrooms) {
        this.type = type;
        this.cadaster = cadaster;
        this.address = address;
        this.postalCode = postalCode;
        this.meters = meters;
        this.rooms = rooms;
        this.bathrooms = bathrooms;
    }

    // Equals basado en el número de catastro (cadaster)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return cadaster.equals(property.cadaster);
    }

    // El hashCode también debe basarse en el número de catastro
    @Override
    public int hashCode() {
        return Objects.hash(cadaster);
    }

    // sobreescribir toString para imprimir
    @Override
    public String toString() {
        return type + "\n" +
                cadaster + "\n" +
                address + "\n" +
                postalCode + "\n" +
                meters + " meters, " + rooms + " rooms, " + bathrooms + " bathrooms\n";
    }


    public int getMeters() {
        return meters;
    }
}


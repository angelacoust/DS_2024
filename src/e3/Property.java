package e3;

import java.util.Objects;

public class Property {
    private PropertyType type;
    private String cadaster;
    private String address;
    private String postalCode;
    private int meters;
    private int rooms;
    private int bathrooms;

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

    // Metodo toString para que coincida con la prueba
    @Override
    public String toString() {
        return type + "\n" +
                cadaster + "\n" +
                address + "\n" +
                postalCode + "\n" +
                meters + " meters, " + rooms + " rooms, " + bathrooms + " bathrooms\n";
    }

    // Getters si es necesario.
    public String getCadaster() {
        return cadaster;
    }

    public int getMeters() {
        return meters;
    }
}


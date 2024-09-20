package e3;

import java.util.Objects;

public class Ad {
    private String agency;
    private Property property;
    private AdType adType;
    private double priceInEuros;

    // Constructor
    public Ad(String agency, Property property, AdType adType, double priceInEuros) {
        this.agency = agency;
        this.property = property;
        this.adType = adType;
        this.priceInEuros = priceInEuros;
    }

    // Constructor de copia
    public Ad(Ad other) {
        this.agency = other.agency;
        this.property = other.property; // Si necesitas una copia profunda, debes clonar la propiedad.
        this.adType = other.adType;
        this.priceInEuros = other.priceInEuros;
    }

    // Método para comparar propiedades entre anuncios
    public boolean isPropertyEqual(Ad other) {
        return this.property.equals(other.property);
    }

    // Verificación de precio razonable basado en el tipo de anuncio
    public boolean isPriceNormal() {
        if (adType == AdType.PURCHASE) {
            return priceInEuros > 100000;  // Precio mínimo para compra
        } else if (adType == AdType.RENTAL) {
            return priceInEuros <= 10000;  // Precio máximo para alquiler
        }
        return false; // En caso de que no sea ni compra ni alquiler.
    }

    // Cálculo del precio por metro cuadrado
    public double priceMetersEuros() {
        int meters = property.getMeters();
        if (meters == 0) {
            throw new ArithmeticException("La propiedad no tiene metros cuadrados.");
        }
        return priceInEuros / meters;
    }

    // Método para bajar el precio en un porcentaje
    public void dropPrice(double percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("El porcentaje debe estar entre 0 y 100.");
        }
        priceInEuros -= priceInEuros * (percentage / 100.0);
    }

    // Getter del precio
    public double getPriceInEuros() {
        return priceInEuros;
    }

    // Sobrescritura de equals para comparar anuncios
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ad ad = (Ad) o;
        return Double.compare(ad.priceInEuros, priceInEuros) == 0 &&
                agency.equals(ad.agency) &&
                property.equals(ad.property) &&
                adType == ad.adType;
    }

    // Sobrescritura de hashCode
    @Override
    public int hashCode() {
        return Objects.hash(agency, property, adType, priceInEuros);
    }

    // Método toString opcional si deseas mostrar la información del anuncio
    @Override
    public String toString() {
        return "Agency: " + agency + "\n" +
                "Property: " + property + "\n" +
                "Ad Type: " + adType + "\n" +
                "Price: " + priceInEuros + " euros";
    }
}


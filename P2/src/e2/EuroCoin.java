package e2;

import java.util.Objects;

public record EuroCoin(int valueInCents, CoinColor color, EuroCountry country, String design, int year)
        implements Comparable<EuroCoin> {

    // Implementación de Comparable para el orden natural
    @Override
    public int compareTo(EuroCoin other) {
        // Comparar primero por valor
        int valueComparison = Integer.compare(this.valueInCents, other.valueInCents);
        if (valueComparison != 0) {
            return valueComparison;
        }

        // Si los valores son iguales, comparar por país (alfabéticamente en inglés)
        int countryComparison = this.country.toString().compareTo(other.country.toString());
        if (countryComparison != 0) {
            return countryComparison;
        }

        // Si los países son iguales, comparar por año
        return Integer.compare(this.year, other.year);
    }


    // Equals y hashCode para cumplir con la definición del objeto
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EuroCoin euroCoin = (EuroCoin) o;
        return valueInCents == euroCoin.valueInCents &&
                year == euroCoin.year &&
                color == euroCoin.color &&
                country == euroCoin.country &&
                Objects.equals(design, euroCoin.design);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valueInCents, color, country, design, year);
    }
}

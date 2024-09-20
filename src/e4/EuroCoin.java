package e4;

import java.util.Objects;

public record EuroCoin(int valueInCents, CoinColor color, EuroCountry country, String design, int year) {

    // Sobrescribimos equals para ignorar el año en la comparación de monedas
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EuroCoin euroCoin = (EuroCoin) o;
        return valueInCents == euroCoin.valueInCents &&
                color == euroCoin.color &&
                country == euroCoin.country &&
                design.equals(euroCoin.design);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valueInCents, color, country, design);
    }
}


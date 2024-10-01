package e4;

import java.util.Objects;

public record EuroCoin(int valueInCents, CoinColor color, EuroCountry country, String design, int year) {

    // equals y hashCode para ignorar el año
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


package e2;

import java.util.Comparator;

public class ComparatorCountry implements Comparator<EuroCoin> {

    @Override
    public int compare(EuroCoin o1, EuroCoin o2) {
        // Ordenar por país (alfabético), luego por valor (mayor primero), y luego por año (antiguo primero)
        int countryCompare = o1.country().name().compareTo(o2.country().name()); // Orden alfabético país
        if (countryCompare != 0) return countryCompare;

        int valueCompare = Integer.compare(o2.valueInCents(), o1.valueInCents()); // Mayor valor primero
        if (valueCompare != 0) return valueCompare;

        return Integer.compare(o1.year(), o2.year()); // Año más antiguo primero
    }
}

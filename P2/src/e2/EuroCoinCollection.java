package e2;

import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EuroCoinCollection implements Iterable<EuroCoin> {
    private final Set<EuroCoin> coins;
    private EuroCountry countryFilter; // Filtro por país
    private int modCount = 0; // Para el seguimiento de modificaciones

    public EuroCoinCollection() {
        coins = new HashSet<>();  // Inicializamos el HashSet
        modCount = 0;
    }

    // Añadir moneda
    public boolean addCoin(EuroCoin coin) {
        if (coin == null) {
            return false; // Si la moneda es nula, no la agregamos
        }
        // Verificar si la moneda ya está en la colección
        if (coins.contains(coin)) {
            return false; // No se agrega si ya existe
        }
        // Si no es un duplicado, agregar la moneda
        boolean added = coins.add(coin);
        if (added) modCount++;
        return added;
    }

    // Eliminar moneda
    public boolean removeCoin(EuroCoin coin) {
        boolean removed = coins.remove(coin);
        if (removed) modCount++;
        return removed;
    }

    // Contar monedas
    public int countCoins() {
        return coins.size();
    }

    // Calcular el valor total
    public int getTotalValue() {
        int total = 0;
        for (EuroCoin coin : coins) {
            total += coin.valueInCents();
        }
        return total;
    }

    public List<EuroCoin> getCoinsSortedByCountry() {
        // Usamos el ComparatorCountry para ordenar las monedas
        List<EuroCoin> sortedCoins = new ArrayList<>(coins);
        Collections.sort(sortedCoins, new ComparatorCountry());
        return sortedCoins;
    }


    // Verificamos si contiene una moneda
    public boolean contains(EuroCoin coin) {
        return coins.contains(coin);
    }

    // Ordenar colección usando Comparable
    public List<EuroCoin> sortByNaturalOrder() {
        List<EuroCoin> sortedCoins = new ArrayList<>(this.coins); // Copiar lista original
        Collections.sort(sortedCoins); // Utiliza el compareTo de EuroCoin
        return sortedCoins;
    }


    // Ordenar usando Comparator
    public List<EuroCoin> sortByComparator(Comparator<EuroCoin> comparator) {
        List<EuroCoin> sortedList = new ArrayList<>(coins);
        sortedList.sort(comparator);
        return sortedList;
    }

    // Establecer filtro de país
    public void setCountryFilter(EuroCountry country) {
        this.countryFilter = country;
    }

    // Obtener monedas filtradas por país
    public List<EuroCoin> getCoinsByCountry(EuroCountry country) {
        List<EuroCoin> filteredCoins = new ArrayList<>();
        for (EuroCoin coin : coins) {
            if (coin.country().equals(country)) {
                filteredCoins.add(coin);
            }
        }
        return filteredCoins;
    }

    // Implementación de Iterable con iterador personalizado
    @Override
    public Iterator<EuroCoin> iterator() {
        return new EuroCoinIterator(coins, modCount);
    }


    // Clase EuroCoinIterator para iteración personalizada
    private class EuroCoinIterator implements Iterator<EuroCoin> {
        private final Iterator<EuroCoin> iterator;
        private final int expectedModCount;
        private EuroCoin current = null;

        public EuroCoinIterator(Set<EuroCoin> coins, int modCount) {
            this.expectedModCount = modCount;
            this.iterator = coins.iterator();
        }

        @Override
        public boolean hasNext() {
            if (modCount != expectedModCount) throw new ConcurrentModificationException();

            // Filtra por país si el filtro está establecido
            while (iterator.hasNext()) {
                EuroCoin coin = iterator.next();
                if (countryFilter == null || coin.country() == countryFilter) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public EuroCoin next() {
            if (modCount != expectedModCount) throw new ConcurrentModificationException();
            if (!hasNext()) throw new NoSuchElementException();

            current = iterator.next();
            return current;
        }

        @Override
        public void remove() {
            if (current == null) throw new IllegalStateException();
            iterator.remove();
            modCount++;
            current = null;
        }
    }
}

package e4;

import java.util.HashSet;
import java.util.Set;

public class EuroCoinCollection {
    private final Set<EuroCoin> coins;

    public EuroCoinCollection() {
        this.coins = new HashSet<>();
    }

    // Añadir una moneda a la colección (si no está repetida)
    public boolean addCoin(EuroCoin coin) {
        return coins.add(coin); // Retorna false si ya estaba
    }

    // Borrar una moneda de la colección
    public boolean removeCoin(EuroCoin coin) {
        return coins.remove(coin);
    }

    // Contar las monedas de la colección
    public int countCoins() {
        return coins.size();
    }

    // Calcular el valor nominal total de las monedas en céntimos
    public int getTotalValue() {
        return coins.stream().mapToInt(EuroCoin::valueInCents).sum();
    }

    // Verificar si una moneda ya está en la colección
    public boolean contains(EuroCoin coin) {
        return coins.contains(coin);
    }
}


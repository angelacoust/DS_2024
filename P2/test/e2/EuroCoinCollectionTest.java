package e2;

import org.junit.jupiter.api.*;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Comparator;

public class EuroCoinCollectionTest {

    private EuroCoinCollection collection;
    private EuroCoin coin1, coin2, coin3, duplicateCoin1, coin4, coin5, duplicateCoin2;

    @BeforeEach
    void setUp() {
        collection = new EuroCoinCollection();

        // Creo monedas de ejemplo
        coin1 = new EuroCoin(200, CoinColor.GOLD, EuroCountry.SPAIN, "Felipe VI", 2022);
        coin2 = new EuroCoin(50, CoinColor.BRONZE, EuroCountry.FRANCE, "Marianne", 2021);
        coin3 = new EuroCoin(100, CoinColor.GOLD_SILVER, EuroCountry.GERMANY, "Eagle", 2019);
        duplicateCoin1 = new EuroCoin(200, CoinColor.GOLD, EuroCountry.SPAIN, "Felipe VI", 2021); // Mismo diseño y país que coin1
        duplicateCoin2 = new EuroCoin(50, CoinColor.BRONZE, EuroCountry.FRANCE, "Marianne", 2021); // Mismo diseño y país que coin2
        coin4 = new EuroCoin(50, CoinColor.BRONZE, EuroCountry.ITALY, "Dante", 2020);
        coin5 = new EuroCoin(500, CoinColor.GOLD, EuroCountry.NETHERLANDS, "Rembrandt", 2018);
    }

    @Test
    void testAddUniqueCoins() {
        assertTrue(collection.addCoin(coin1), "coin1 should be added");
        assertTrue(collection.addCoin(coin2), "coin2 should be added");
        assertTrue(collection.addCoin(coin3), "coin3 should be added");
        assertEquals(3, collection.countCoins(), "Collection should have 3 unique coins");
    }

    @Test
    void testAddDuplicateCoin() {
        EuroCoinCollection collection = new EuroCoinCollection();
        EuroCoin coin1 = new EuroCoin(200, CoinColor.GOLD, EuroCountry.SPAIN, "Felipe VI", 2022);

        // Añadir la moneda por primera vez
        boolean addedFirstTime = collection.addCoin(coin1);
        assertTrue(addedFirstTime, "La moneda debería haberse añadido");

        // Intentar añadir la moneda duplicada
        boolean addedSecondTime = collection.addCoin(coin1);
        assertFalse(addedSecondTime, "La moneda duplicada no debería añadirse");
    }

    @Test
    void testRemoveCoin() {
        collection.addCoin(coin1);
        collection.addCoin(coin2);
        assertTrue(collection.removeCoin(coin1), "coin1 should be removed");
        assertFalse(collection.contains(coin1), "coin1 should not be in the collection anymore");
        assertEquals(1, collection.countCoins(), "Collection should have 1 coin after removal");
    }

    @Test
    void testRemoveNonExistentCoin() {
        collection.addCoin(coin1);
        assertFalse(collection.removeCoin(coin2), "Removing a non-existent coin should return false");
        assertEquals(1, collection.countCoins(), "Collection should still have 1 coin");
    }

    @Test
    void testCountCoins() {
        // Inicializamos las monedas de prueba
        EuroCoin coin1 = new EuroCoin(1, CoinColor.GOLD, EuroCountry.GERMANY, "Classic", 2021);
        EuroCoin coin2 = new EuroCoin(2, CoinColor.GOLD_SILVER, EuroCountry.FRANCE, "Felipe VI", 2022);
        EuroCoin duplicateCoin1 = new EuroCoin(1, CoinColor.GOLD, EuroCountry.GERMANY, "Classic", 2021);  // Duplicado de coin1

        // Comprobamos que la colección está vacía al principio
        assertEquals(0, collection.countCoins(), "Initially, the collection should be empty");

        // Añadimos monedas
        collection.addCoin(coin1);
        collection.addCoin(coin2);

        // Verificamos que la colección tiene 2 monedas después de añadir coin1 y coin2
        assertEquals(2, collection.countCoins(), "Collection should have 2 coins");

        // Intentamos añadir un duplicado (duplicateCoin1), que no debería añadirse
        collection.addCoin(duplicateCoin1);

        // Verificamos que la colección sigue teniendo 2 monedas después de intentar añadir un duplicado
        assertEquals(2, collection.countCoins(), "Collection should still have 2 coins after trying to add a duplicate");
    }

    @Test
    void testGetTotalValue() {
        EuroCoinCollection collection = new EuroCoinCollection();

        // Crear algunas monedas
        EuroCoin coin1 = new EuroCoin(100, CoinColor.GOLD, EuroCountry.GERMANY, "Classic", 2022);
        EuroCoin coin2 = new EuroCoin(100, CoinColor.GOLD, EuroCountry.GERMANY, "Classic", 2022); // Moneda duplicada

        collection.addCoin(coin1);
        collection.addCoin(coin2); // No se debería agregar, ya que es un duplicado

        // Verificar que el valor total sigue siendo 100 (y no 200)
        Assertions.assertEquals(100, collection.getTotalValue(), "Total value should remain 100 cents after trying to add a duplicate.");
    }

    @Test
    void testContains() {
        collection.addCoin(coin1);
        assertTrue(collection.contains(coin1), "Collection should contain coin1");
        assertFalse(collection.contains(coin2), "Collection should not contain coin2");
        collection.addCoin(coin2);
        assertTrue(collection.contains(coin2), "Collection should contain coin2 after adding it");
    }

    @Test
    void testAddMultipleCoinsAndCount() {
        collection.addCoin(coin1);
        collection.addCoin(coin2);
        collection.addCoin(coin3);
        collection.addCoin(coin4);
        collection.addCoin(coin5);
        assertEquals(5, collection.countCoins(), "Collection should have 5 unique coins");
    }

    @Test
    void testAddDifferentDesigns() {
        EuroCoin coinDifferentDesign = new EuroCoin(200, CoinColor.GOLD, EuroCountry.SPAIN, "Different Design", 2022);
        assertTrue(collection.addCoin(coin1), "coin1 should be added");
        assertTrue(collection.addCoin(coinDifferentDesign), "coinDifferentDesign should be added because it's a different design");
        assertEquals(2, collection.countCoins(), "Collection should have 2 unique coins");
    }

    // Tests for EuroCoin class
    @Test
    void testEuroCoinCreation() {
        EuroCoin coin = new EuroCoin(200, CoinColor.GOLD, EuroCountry.SPAIN, "Felipe VI", 2022);
        assertEquals(200, coin.valueInCents(), "Value in cents should be 200");
        assertEquals(CoinColor.GOLD, coin.color(), "Color should be GOLD");
        assertEquals(EuroCountry.SPAIN, coin.country(), "Country should be SPAIN");
        assertEquals("Felipe VI", coin.design(), "Design should be 'Felipe VI'");
        assertEquals(2022, coin.year(), "Year should be 2022");
    }

    @Test
    void testEquals() {
        EuroCoin coin1 = new EuroCoin(200, CoinColor.GOLD, EuroCountry.SPAIN, "Felipe VI", 2022);
        EuroCoin coin2 = new EuroCoin(200, CoinColor.GOLD, EuroCountry.SPAIN, "Felipe VI", 2022); // Cambié el año a 2022 para coincidir con coin1
        EuroCoin coin3 = new EuroCoin(200, CoinColor.GOLD, EuroCountry.SPAIN, "Different Design", 2022);
        EuroCoin coin4 = new EuroCoin(100, CoinColor.GOLD, EuroCountry.SPAIN, "Felipe VI", 2022);
        EuroCoin coin5 = new EuroCoin(200, CoinColor.GOLD_SILVER, EuroCountry.SPAIN, "Felipe VI", 2022);
        EuroCoin coin6 = new EuroCoin(200, CoinColor.GOLD, EuroCountry.FRANCE, "Felipe VI", 2022);

        assertEquals(coin1, coin1, "coin1 should be equal to itself");
        assertEquals(coin1, coin2, "coin1 should be equal to coin2");
        assertNotEquals(coin1, coin3, "coin1 should not be equal to coin3 (different design)");
        assertNotEquals(coin1, coin4, "coin1 should not be equal to coin4 (different value)");
        assertNotEquals(coin1, coin5, "coin1 should not be equal to coin5 (different color)");
        assertNotEquals(coin1, coin6, "coin1 should not be equal to coin6 (different country)");
        assertNotEquals(coin1, null, "coin1 should not be equal to null");
        assertNotEquals(coin1, new Object(), "coin1 should not be equal to an object of a different class");
    }

    @Test
    void testHashCode() {
        EuroCoin coin1 = new EuroCoin(200, CoinColor.GOLD, EuroCountry.SPAIN, "Felipe VI", 2022);
        EuroCoin coin2 = new EuroCoin(200, CoinColor.GOLD, EuroCountry.SPAIN, "Felipe VI", 2022);
        EuroCoin coin3 = new EuroCoin(100, CoinColor.BRONZE, EuroCountry.FRANCE, "Marianne", 2020);

        assertEquals(coin1.hashCode(), coin2.hashCode(), "coin1 and coin2 should have the same hash code");
        assertNotEquals(coin1.hashCode(), coin3.hashCode(), "coin1 and coin3 should have different hash codes");
    }

    @Test
    void testGetCoinsSortedByCountry() {
        collection.addCoin(coin1);
        collection.addCoin(coin2);
        collection.addCoin(coin3);
        collection.addCoin(coin4);
        collection.addCoin(coin5);

        // Obtener las monedas ordenadas por ComparatorCountry
        List<EuroCoin> sortedCoins = collection.getCoinsSortedByCountry();

        // Verificar que las monedas estén ordenadas correctamente
        // Alemania primero (orden alfabético)
        assertEquals(EuroCountry.FRANCE, sortedCoins.get(0).country());
        assertEquals(50, sortedCoins.get(0).valueInCents()); // Valor más alto primero
        assertEquals(2021, sortedCoins.get(0).year()); // Año más antiguo primero

        // Verificar la segunda moneda (Alemania)
        assertEquals(EuroCountry.GERMANY, sortedCoins.get(1).country());
        assertEquals(100, sortedCoins.get(1).valueInCents());
        assertEquals(2019, sortedCoins.get(1).year());

        // Verificar Italia
        assertEquals(EuroCountry.ITALY, sortedCoins.get(2).country());
        assertEquals(50, sortedCoins.get(2).valueInCents());
        assertEquals(2020, sortedCoins.get(2).year());

        // Verificar España
        assertEquals(EuroCountry.NETHERLANDS, sortedCoins.get(3).country());
        assertEquals(500, sortedCoins.get(3).valueInCents());
        assertEquals(2018, sortedCoins.get(3).year());

        // Verificar la última moneda (España)
        assertEquals(EuroCountry.SPAIN, sortedCoins.get(4).country());
        assertEquals(200, sortedCoins.get(4).valueInCents());
        assertEquals(2022, sortedCoins.get(4).year());
    }


    @Test
    void testSortByNaturalOrder() {
        // Crear colección
        EuroCoinCollection collection = new EuroCoinCollection();

        // Crear monedas desordenadas
        EuroCoin coin1 = new EuroCoin(50, CoinColor.GOLD_SILVER, EuroCountry.GERMANY, "Design1", 2020);
        EuroCoin coin2 = new EuroCoin(10, CoinColor.BRONZE, EuroCountry.FRANCE, "Design2", 2019);
        EuroCoin coin3 = new EuroCoin(200, CoinColor.GOLD, EuroCountry.SPAIN, "Design3", 2021);

        // Añadir monedas a la colección
        collection.addCoin(coin1);
        collection.addCoin(coin2);
        collection.addCoin(coin3);

        // Ordenar usando sortByNaturalOrder
        List<EuroCoin> sortedList = collection.sortByNaturalOrder();

        // Verificar que la lista esté ordenada según el orden natural
        assertEquals(coin2, sortedList.get(0), "La primera moneda debería ser la de menor valor");
        assertEquals(coin1, sortedList.get(1), "La segunda moneda debería ser la intermedia en valor");
        assertEquals(coin3, sortedList.get(2), "La tercera moneda debería ser la de mayor valor");
    }

    @Test
    void testCompareTo() {
        EuroCoin coin1 = new EuroCoin(10, CoinColor.BRONZE, EuroCountry.FRANCE, "Design1", 2015);
        EuroCoin coin2 = new EuroCoin(50, CoinColor.GOLD_SILVER, EuroCountry.GERMANY, "Design2", 2017);
        EuroCoin coin3 = new EuroCoin(10, CoinColor.BRONZE, EuroCountry.GERMANY, "Design1", 2015);

        // Comparar por valor
        assertTrue(coin1.compareTo(coin2) < 0, "coin1 debería ser menor que coin2 porque tiene menor valor");
        assertTrue(coin2.compareTo(coin1) > 0, "coin2 debería ser mayor que coin1 porque tiene mayor valor");

        // Comparar por país cuando el valor es igual
        assertTrue(coin1.compareTo(coin3) < 0, "coin1 debería ser menor que coin3 porque France < Germany alfabéticamente en inglés");
        assertTrue(coin3.compareTo(coin1) > 0, "coin3 debería ser mayor que coin1 porque Germany > France alfabéticamente en inglés");
    }


}
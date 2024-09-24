package e4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EuroCoinCollectionTest {

    private EuroCoinCollection collection;
    private EuroCoin coin1, coin2, coin3, duplicateCoin1, coin4, coin5,duplicateCoin2;

    @BeforeEach
    void setUp() {
        collection = new EuroCoinCollection();

        // Crear monedas de ejemplo
        coin1 = new EuroCoin(200, CoinColor.GOLD, EuroCountry.SPAIN, "Felipe VI", 2022);
        coin2 = new EuroCoin(50, CoinColor.BRONZE, EuroCountry.FRANCE, "Marianne", 2021);
        coin3 = new EuroCoin(100, CoinColor.GOLD_SILVER, EuroCountry.GERMANY, "Eagle", 2019);
        duplicateCoin1 = new EuroCoin(200, CoinColor.GOLD, EuroCountry.SPAIN, "Felipe VI", 2021); // Mismo diseño y país que coin1
        duplicateCoin2 = new EuroCoin(50, CoinColor.BRONZE, EuroCountry.FRANCE, "Marianne", 2021); // Mismo diseño y país que coin1
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
        collection.addCoin(coin1);
        assertFalse(collection.addCoin(duplicateCoin1), "Duplicate coin should not be added");
        assertEquals(1, collection.countCoins(), "Collection should still have 1 coin");
        collection.addCoin(coin2);
        assertFalse(collection.addCoin(duplicateCoin2), "Duplicate coin should not be added");
        assertEquals(2, collection.countCoins(), "Collection should still have 2 coin");
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
        assertEquals(0, collection.countCoins(), "Initially, the collection should be empty");
        collection.addCoin(coin1);
        collection.addCoin(coin2);
        assertEquals(2, collection.countCoins(), "Collection should have 2 coins");
        collection.addCoin(duplicateCoin1); // Should not add duplicate
        assertEquals(2, collection.countCoins(), "Collection should still have 2 coins after trying to add a duplicate");
    }

    @Test
    void testGetTotalValue() {
        collection.addCoin(coin1); // 200 cents
        collection.addCoin(coin2); // 50 cents
        collection.addCoin(coin3); // 100 cents
        collection.addCoin(coin4); // 50 cents
        assertEquals(400, collection.getTotalValue(), "Total value should be 400 cents (200 + 50 + 100 + 50)");
        collection.addCoin(duplicateCoin1); // Should not affect total
        assertEquals(400, collection.getTotalValue(), "Total value should remain 400 cents after trying to add a duplicate");
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
        EuroCoin coin2 = new EuroCoin(200, CoinColor.GOLD, EuroCountry.SPAIN, "Felipe VI", 2021); // Mismo valor, país, color y diseño
        EuroCoin coin3 = new EuroCoin(200, CoinColor.GOLD, EuroCountry.SPAIN, "Different Design", 2022); // Mismo valor, país, color pero diferente diseño
        EuroCoin coin4 = new EuroCoin(100, CoinColor.GOLD, EuroCountry.SPAIN, "Felipe VI", 2022); // Diferente valor
        EuroCoin coin5 = new EuroCoin(200, CoinColor.GOLD_SILVER, EuroCountry.SPAIN, "Felipe VI", 2022); // Diferente color
        EuroCoin coin6 = new EuroCoin(200, CoinColor.GOLD, EuroCountry.FRANCE, "Felipe VI", 2022); // Diferente país

        // Case: mismo objeto
        assertEquals(coin1, coin1, "coin1 should be equal to itself");

        // Case: igual
        assertEquals(coin1, coin2, "coin1 should be equal to coin2");

        // Case: diferente diseño
        assertNotEquals(coin1, coin3, "coin1 should not be equal to coin3 (different design)");

        // Case: diferente valor
        assertNotEquals(coin1, coin4, "coin1 should not be equal to coin4 (different value)");

        // Case: diferente color
        assertNotEquals(coin1, coin5, "coin1 should not be equal to coin5 (different color)");

        // Case: diferente país
        assertNotEquals(coin1, coin6, "coin1 should not be equal to coin6 (different country)");

        // Case: null
        assertNotEquals(coin1, null, "coin1 should not be equal to null");

        // Case: diferente tipo
        assertNotEquals(coin1, new Object(), "coin1 should not be equal to an object of a different class");
    }


    @Test
    void testHashCode() {
        EuroCoin coin1 = new EuroCoin(200, CoinColor.GOLD, EuroCountry.SPAIN, "Felipe VI", 2022);
        EuroCoin coin2 = new EuroCoin(200, CoinColor.GOLD, EuroCountry.SPAIN, "Felipe VI", 2021);
        EuroCoin coin3 = new EuroCoin(100, CoinColor.BRONZE, EuroCountry.FRANCE, "Marianne", 2020);

        assertEquals(coin1.hashCode(), coin2.hashCode(), "coin1 and coin2 should have the same hash code");
        assertNotEquals(coin1.hashCode(), coin3.hashCode(), "coin1 and coin3 should have different hash codes");
    }



    @Test
    void testDifferentYearEquals() {
        EuroCoin coin1 = new EuroCoin(200, CoinColor.GOLD, EuroCountry.SPAIN, "Felipe VI", 2022);
        EuroCoin coin2 = new EuroCoin(200, CoinColor.GOLD, EuroCountry.SPAIN, "Felipe VI", 2023); // Different year
        assertEquals(coin1, coin2, "coin1 should be equal to coin2 despite different years");
    }

    @Test
    void testGetIsoCode() {
        assertEquals("ES", EuroCountry.SPAIN.getIsoCode(), "ISO code for Spain should be 'ES'");
        assertEquals("FR", EuroCountry.FRANCE.getIsoCode(), "ISO code for France should be 'FR'");
        assertEquals("DE", EuroCountry.GERMANY.getIsoCode(), "ISO code for Germany should be 'DE'");
        assertEquals("IT", EuroCountry.ITALY.getIsoCode(), "ISO code for Italy should be 'IT'");
        assertEquals("NL", EuroCountry.NETHERLANDS.getIsoCode(), "ISO code for Netherlands should be 'NL'");
        assertEquals("PT", EuroCountry.PORTUGAL.getIsoCode(), "ISO code for Portugal should be 'PT'");
        // Añade más verificaciones para otros países según sea necesario
    }
}

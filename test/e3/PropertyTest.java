package e3;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertyTest {

    private static Property p1, p2, p3;

    @BeforeAll
    static void setUp() {

        p1 = new Property(PropertyType.APARTMENT,
                "01234567890123456789",
                "Aurelio Aguirre Galarraga 100, 1-A, A Coruna",
                "15190",
                80,
                2,
                1
                );

        p2 = new Property(PropertyType.APARTMENT,
                "56789012345678901234",
                "Aurelio Aguirre Galarraga 202, 5-D, A Coruna",
                "15190",
                100,
                3,
                2
                );

        /* Same cadaster as h2 but the entered address and meters are different. */
        p3 = new Property(PropertyType.APARTMENT,
                "56789012345678901234",
                "Aurelio Aguirre Galarraga 202, 5º D (A Coruna)",
                "15190",
                95,
                3,
                2
                );
    }

    /* Equality is defined by the cadaster. */

    @Test
    void testEquals() {
        assertEquals(p2, p3);
        assertNotEquals(p1, null);
        assertNotEquals(p1, p2);
        assertNotEquals(p1, p3);
    }

    @Test
    void testHashCode() {
        assertEquals(p2.hashCode(), p3.hashCode());
    }

    @Test
    void testToString() {
        String expected = """
                APARTMENT
                01234567890123456789
                Aurelio Aguirre Galarraga 100, 1-A, A Coruna
                15190
                80 meters, 2 rooms, 1 bathrooms
                """;
        String actual = p1.toString();
        assertEquals(expected, actual);
    }
}
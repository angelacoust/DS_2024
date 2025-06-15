package e3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

class ColeccionTest {

    private ColeccionMonedas3 coleccion;

    @BeforeEach
    void setUp() {
        coleccion = new ColeccionMonedas3();
        coleccion.addMoneda(new Moneda3("EURO2", "ES"));
        coleccion.addMoneda(new Moneda3("EURO1", "ES"));
        coleccion.addMoneda(new Moneda3("CENT20", "FR"));
        coleccion.addMoneda(new Moneda3("EURO1", "IT"));
        coleccion.addMoneda(new Moneda3("CENT50", "IT"));
    }

    @Test
    void testIterarTodasLasMonedas() {
        coleccion.setPaisParaIterar(null); // Itera sobre todas las monedas
        Iterator<Moneda3> it = coleccion.iterator();

        assertTrue(it.hasNext());
        assertEquals("EURO2-ES", it.next().toString());
        assertEquals("EURO1-ES", it.next().toString());
        assertEquals("CENT20-FR", it.next().toString());
        assertEquals("EURO1-IT", it.next().toString());
        assertEquals("CENT50-IT", it.next().toString());
        assertFalse(it.hasNext());
    }

    @Test
    void testIterarMonedasDeEspana() {
        coleccion.setPaisParaIterar("ES"); // Itera solo sobre las monedas de España
        Iterator<Moneda3> it = coleccion.iterator();

        assertTrue(it.hasNext());
        assertEquals("EURO2-ES", it.next().toString());
        assertEquals("EURO1-ES", it.next().toString());
        assertFalse(it.hasNext());
    }

    @Test
    void testRemoveMoneda() {
        coleccion.setPaisParaIterar("ES");
        Iterator<Moneda3> it = coleccion.iterator();

        assertTrue(it.hasNext());
        assertEquals("EURO2-ES", it.next().toString());
        it.remove(); // Elimina la moneda EURO2-ES
        assertEquals("EURO1-ES", it.next().toString());

        // Verificar que la moneda ha sido eliminada
        coleccion.setPaisParaIterar(null); // Iteramos sobre todas las monedas
        Iterator<Moneda3> it2 = coleccion.iterator();
        assertEquals("EURO1-ES", it2.next().toString()); // La primera moneda ahora es EURO1-ES
    }

    @Test
    void testRemoveIllegalStateException() {
        coleccion.setPaisParaIterar("ES");
        Iterator<Moneda3> it = coleccion.iterator();

        assertThrows(IllegalStateException.class, it::remove); // No se ha llamado a next(), debe lanzar IllegalStateException
    }

    @Test
    void testRemoveConcurrentModificationException() {
        coleccion.setPaisParaIterar("ES");
        Iterator<Moneda3> it = coleccion.iterator();
        it.next();

        // Modificar la colección desde fuera
        coleccion.addMoneda(new Moneda3("CENT10", "ES"));

        assertThrows(ConcurrentModificationException.class, it::next); // Debería lanzar ConcurrentModificationException
    }

    @Test
    void testNextNoSuchElementException() {
        coleccion.setPaisParaIterar("ES");
        Iterator<Moneda3> it = coleccion.iterator();

        assertTrue(it.hasNext());
        it.next(); // EURO2-ES
        it.next(); // EURO1-ES
        assertFalse(it.hasNext());

        assertThrows(NoSuchElementException.class, it::next); // No quedan más elementos
    }

    @Test
    void testIterarMonedasDeFrancia() {
        coleccion.setPaisParaIterar("FR"); // Iterar sobre monedas de Francia
        Iterator<Moneda3> it = coleccion.iterator();

        assertTrue(it.hasNext());
        assertEquals("CENT20-FR", it.next().toString());
        assertFalse(it.hasNext());
    }

    @Test
    void testIterarMonedasDeItalia() {
        coleccion.setPaisParaIterar("IT"); // Iterar sobre monedas de Italia
        Iterator<Moneda3> it = coleccion.iterator();

        assertTrue(it.hasNext());
        assertEquals("EURO1-IT", it.next().toString());
        assertEquals("CENT50-IT", it.next().toString());
        assertFalse(it.hasNext());
    }
}

package e2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StockTest {
    private Stock stockAAPL;
    private Stock stockGOOG;
    private FocusedClient focusedClient;
    private SimpleClient simpleClient;
    private DetailedClient detailedClient;

    @BeforeEach
    void setUp() {
        // Inicializar los stocks
        stockAAPL = new Stock("AAPL");
        stockGOOG = new Stock("GOOG");

        // Crear el cliente enfocado
        focusedClient = new FocusedClient("AAPL");

        // Crear el cliente simple
        simpleClient = new SimpleClient();

        // Crear el cliente detallado
        detailedClient = new DetailedClient();
    }

    @Test
    void testFocusedClientReceivesOnlySpecificSymbolUpdates() {
        // Registrar el cliente enfocado para "AAPL"
        stockAAPL.registerObserver(focusedClient);

        // Cambiar los datos para AAPL
        stockAAPL.setStockData(150.0, 155.0, 148.0, 1000000);

        // Cambiar los datos para GOOG (símbolo diferente)
        stockGOOG.setStockData(2500.0, 2550.0, 2400.0, 1200000);

        // Cambiar los datos para AAPL otra vez
        stockAAPL.setStockData(155.0, 160.0, 150.0, 1100000);

        // Verificar que el cliente enfocado solo recibe notificaciones de AAPL
        assertEquals(2, focusedClient.getNotifications().size()); // Solo debe haber 2 notificaciones para AAPL
        assertEquals("Focused: Symbol=AAPL, Close=150.0", focusedClient.getNotifications().get(0));
        assertEquals("Focused: Symbol=AAPL, Close=155.0", focusedClient.getNotifications().get(1));

        // Verificar que el cliente enfocado no recibe notificaciones para GOOG
        assertTrue(focusedClient.getNotifications().stream()
                .noneMatch(notification -> notification.contains("GOOG")));
    }

    @Test
    void testSimpleClientUpdate() {
        // Registrar el cliente simple
        stockAAPL.registerObserver(simpleClient);

        // Cambiar los datos para AAPL
        stockAAPL.setStockData(150.0, 155.0, 148.0, 1000000);

        // Verificar que la notificación fue enviada correctamente
        assertEquals("Simple: Symbol=AAPL, Close=150.0", simpleClient.getNotifications().getFirst()); // Comprobamos la notificación
    }

    @Test
    void testDetailedClientUpdate() {
        // Registrar el cliente detallado
        stockAAPL.registerObserver(detailedClient);

        // Cambiar los datos para AAPL
        stockAAPL.setStockData(150.0, 155.0, 148.0, 1000000);

        // Verificar que la notificación detallada fue enviada correctamente
        assertEquals("Detailed: Symbol=AAPL, Close=150.0, Max=155.0, Min=148.0, Volume=1000000", detailedClient.getNotifications().getFirst());
    }

    @Test
    void testMultipleStocksAndClientsUpdate() {
        // Registrar el cliente enfocado para AAPL y el cliente simple
        stockAAPL.registerObserver(focusedClient);
        stockGOOG.registerObserver(simpleClient);

        // Cambiar los datos para AAPL
        stockAAPL.setStockData(150.0, 155.0, 148.0, 1000000);

        // Cambiar los datos para GOOG
        stockGOOG.setStockData(2500.0, 2550.0, 2400.0, 1200000);

        // Verificar que el cliente enfocado reciba solo las actualizaciones de AAPL
        assertEquals("Focused: Symbol=AAPL, Close=150.0", focusedClient.getNotifications().getFirst());

        // Verificar que el cliente simple reciba solo las actualizaciones de GOOG
        assertEquals("Simple: Symbol=GOOG, Close=2500.0", simpleClient.getNotifications().getFirst());
    }

    @Test
    void testRemoveObserver() {
        // Registrar y luego eliminar el cliente enfocado
        stockAAPL.registerObserver(focusedClient);
        stockAAPL.setStockData(150.0, 155.0, 148.0, 1000000);

        // Verificar que se reciba la notificación
        assertEquals("Focused: Symbol=AAPL, Close=150.0", focusedClient.getNotifications().getFirst());

        // Eliminar el observador
        stockAAPL.removeObserver(focusedClient);

        // Cambiar los datos para AAPL y verificar que no reciba notificaciones
        stockAAPL.setStockData(155.0, 160.0, 150.0, 1100000);
        assertEquals(1, focusedClient.getNotifications().size()); // No debe haber más notificaciones
    }
}

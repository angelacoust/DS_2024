package e1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class FleetTest {
    private Fleet fleet;
    private Ship ship1;
    private Ship ship2;
    private Ship ship3;
    private Ship ship4;
    private Ship ship5;
    private Ship ship6;
    private Ship ship7;

    @BeforeEach
    public void setUp() {
        fleet = new Fleet("Fleet1",10000000);  // Inicializamos la flota con fondos suficientes
        ship1 = new Ship("Ship1", ShipType.BB, fleet);
        ship2 = new Ship("Ship2", ShipType.CV, fleet);
        ship3 = new Ship("Ship3", ShipType.CA, fleet);
        ship4 = new Ship("Ship4", ShipType.AV, fleet);
        ship5 = new Ship("Ship5", ShipType.CL, fleet);
        ship6 = new Ship("Ship6", ShipType.DE, fleet);
        ship7 = new Ship("Ship7", ShipType.DD, fleet);

        fleet.addShip(ship1);  // Añadimos los barcos a la flota
        fleet.addShip(ship2);
        fleet.addShip(ship3);
        fleet.addShip(ship4);
        fleet.addShip(ship5);
        fleet.addShip(ship6);
        fleet.addShip(ship7);
    }

    @Test
    public void testAddShip() {
        assertEquals(7, fleet.getShips().size(), "La flota debería tener 7 barcos");
    }

    @Test
    public void testFundsManagement() {
        fleet.addFundsFromExercise(2000);  // Añadir fondos por ejercicio
        assertEquals(10002000, fleet.getFunds(), "Los fondos deberían ser 10002000 después del ejercicio");
    }

    @Test
    public void testListActiveShips() {
        fleet.listActiveShips(); // Verifica que los barcos activos son listados correctamente
        assertTrue(fleet.getShips().contains(ship1), "Ship1 debería estar en la lista de barcos activos");
        assertTrue(fleet.getShips().contains(ship2), "Ship2 debería estar en la lista de barcos activos");
        assertTrue(fleet.getShips().contains(ship3), "Ship3 debería estar en la lista de barcos activos");
        assertTrue(fleet.getShips().contains(ship4), "Ship4 debería estar en la lista de barcos activos");
        assertTrue(fleet.getShips().contains(ship5), "Ship5 debería estar en la lista de barcos activos");
        assertTrue(fleet.getShips().contains(ship6), "Ship6 debería estar en la lista de barcos activos");
        assertTrue(fleet.getShips().contains(ship7), "Ship7 debería estar en la lista de barcos activos");
    }


    @Test
    public void testListInactiveShips() {
        // Hacemos que los barcos estén hundidos y desmantelados
        ship1.setState(new SunkState());  // Ship1 se hunde
        ship2.setState(new DecommissionedState());  // Ship2 se desmantela
        ship3.setState(new SunkState());  // Ship3 se hunde

        fleet.listInactiveShips();

        // Verificamos que la salida en consola contenga los barcos inactivos correctos
        List<Ship> inactiveShips = fleet.getShips().stream()
                .filter(ship -> ship.getState() instanceof SunkState || ship.getState() instanceof DecommissionedState)
                .collect(Collectors.toList());

        assertNotNull(inactiveShips, "The inactive ships list should not be null.");
        assertEquals(3, inactiveShips.size(), "There should be 3 inactive ships.");

        // Verificamos que todos los barcos en la lista tengan los estados correctos
        for (Ship ship : inactiveShips) {
            assertTrue(ship.getState() instanceof SunkState || ship.getState() instanceof DecommissionedState,
                    "Each ship should be in SunkState or DecommissionedState.");
        }
    }

    @Test
    public void testExpressRepairWithShipUnderRepair() {
        // Configuramos Ship1 en estado UnderRepairState
        ship1.setState(new UnderRepairState());
        assertEquals(UnderRepairState.class, ship1.getState().getClass(), "Ship1 should be in UnderRepairState.");

        // Configuramos Ship2 para intentar una reparación express
        ship6.setState(new PendingConfirmationState());
        assertThrows(ShipCannotException.class, () -> ship6.getState().repairExpress(ship6),
                "An exception should be thrown when attempting an express repair while another ship is under repair.");

        // Verificamos que los estados de ambos barcos permanecen sin cambios
        assertEquals(UnderRepairState.class, ship1.getState().getClass(), "Ship1 should remain in UnderRepairState.");
        assertEquals(PendingConfirmationState.class, ship6.getState().getClass(), "Ship6 should remain in PendingConfirmationState.");
    }

    @Test
    public void testShipCrash() {
        ship1.setState(new PendingConfirmationState());
        ship1.getFleet().setFunds(80000000);

        // Verificamos que el barco está en PendingConfirmationState
        assertEquals(PendingConfirmationState.class, ship1.getState().getClass(), "Ship1 should start in PendingConfirmationState.");

        // Ahora cancelamos la reparación, lo que debería cambiar el estado a AtBaseState
        ship1.getState().cancelRepair(ship1);
        assertEquals(AtBaseState.class, ship1.getState().getClass(), "Ship1 should transition to AtBaseState after cancelling repair.");

        // Ahora intentamos hacer ejercicio, lo cual debería lanzar una excepción
        assertThrows(ShipCannotException.class, () -> ship1.getState().doExercise(ship1), "Ship1 should not be able to do exercise while in AtBaseState.");

        ship1.getState().requestRepair(ship1);
        ship1.getState().acceptRepair(ship1);
        ship1.getState().repaired(ship1);
        ship1.getState().doExercise(ship1);
        assertEquals(InExerciseState.class, ship1.getState().getClass());
    }

    @Test
    public void testShowFleetStatus() {
        // Cambiar estados de los barcos inactivos
        ship1.setState(new DecommissionedState());
        ship2.setState(new SunkState());

        // Redirigir la salida para capturar lo que se imprime
        java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream();
        java.io.PrintStream originalOut = System.out; // Guardar la salida original
        System.setOut(new java.io.PrintStream(outputStream));

        try {
            // Llamar al método showFleetStatus
            fleet.showFleetStatus();
        } finally {
            // Restaurar la salida estándar
            System.setOut(originalOut);
        }

        // Obtener el separador de línea del sistema porque sino no pasa el test
        String lineSeparator = System.lineSeparator();

        // Salida esperada utilizando el separador de línea correcto
        String expectedOutput = "FLEET STATUS:" + lineSeparator +
                "ACTIVE VESSELS---------------------------------" + lineSeparator +
                "Name: Ship3 | Missions: 0" + lineSeparator +
                "Name: Ship4 | Missions: 0" + lineSeparator +
                "Name: Ship5 | Missions: 0" + lineSeparator +
                "Name: Ship6 | Missions: 0" + lineSeparator +
                "Name: Ship7 | Missions: 0" + lineSeparator +
                "INACTIVE VESSELS---------------------------------" + lineSeparator +
                "Name: Ship1 | Reasons: Decommissioned | Missions: 0" + lineSeparator +
                "Name: Ship2 | Reasons: Sunk | Missions: 0" + lineSeparator +
                "Available funds: 1.0E7";

        // Comparar la salida capturada con la esperada
        assertEquals(expectedOutput, outputStream.toString().trim());
    }










    //COMPROBACIÓN DE CADA UNA DE LAS FUNCIONES QUE SE PUEDEN REALIZAR EN CADA ESTADO
    @Test
    public void testShipAtBaseStateFunctionality() {

        ship2.getState().requestRepair(ship2);  // Cambia a InExerciseState
        assertEquals(PendingConfirmationState.class, ship2.getState().getClass(), "Ship2 should be in InExerciseState after starting exercise.");

        //Ahora ship2 está en PendingConfirmationState

        ship1.getState().doExercise(ship1);  // Cambia a InExerciseState
        assertEquals(InExerciseState.class, ship1.getState().getClass(), "Ship1 should be in InExerciseState after starting exercise.");

        //Ahora ship 1 está en InExerciseState

        assertThrows(ShipCannotException.class, () -> ship3.getState().completedExercise(ship3));
        assertThrows(ShipCannotException.class, () -> ship3.getState().sinkShip(ship3));
        assertThrows(ShipCannotException.class, () -> ship3.getState().acceptRepair(ship3));
        assertThrows(ShipCannotException.class, () -> ship3.getState().cancelRepair(ship3));
        assertThrows(ShipCannotException.class, () -> ship3.getState().repairExpress(ship3));
        assertThrows(ShipCannotException.class, () -> ship3.getState().repaired(ship3));
        assertThrows(ShipCannotException.class, () -> ship3.getState().needMoreReparations(ship3));
        assertThrows(ShipCannotException.class, () -> ship3.getState().decommission(ship3));
    }

    @Test
    public void testShipInExerciseStateFunctionality() {

        // Cambiamos el estado de ship2 a InExerciseState
        ship2.setState(new InExerciseState());
        assertEquals(InExerciseState.class, ship2.getState().getClass(), "Ship2 should start in InExerciseState.");

        // Pruebas de funciones permitidas
        ship2.getState().completedExercise(ship2);  // Cambia a AtBaseState
        assertEquals(AtBaseState.class, ship2.getState().getClass(), "Ship2 should transition to AtBaseState after completing the exercise.");

        // Ahora ship2 está en AtBaseState
        ship2.setState(new InExerciseState());  // Volvemos a InExerciseState para probar excepciones


        ship3.setState(new InExerciseState());
        assertEquals(InExerciseState.class, ship3.getState().getClass(), "Ship3 should start in InExerciseState.");

        // Pruebas de funciones permitidas
        ship3.getState().sinkShip(ship3);  // Cambia a AtBaseState
        assertEquals(SunkState.class, ship3.getState().getClass(), "Ship3 should transition to SunkState.");

        ship4.setState(new InExerciseState());

        // Pruebas de funciones no permitidas
        assertThrows(ShipCannotException.class, () -> ship4.getState().requestRepair(ship4));
        assertThrows(ShipCannotException.class, () -> ship4.getState().doExercise(ship2));
        assertThrows(ShipCannotException.class, () -> ship4.getState().acceptRepair(ship2));
        assertThrows(ShipCannotException.class, () -> ship4.getState().cancelRepair(ship2));
        assertThrows(ShipCannotException.class, () -> ship4.getState().repairExpress(ship2));
        assertThrows(ShipCannotException.class, () -> ship4.getState().repaired(ship2));
        assertThrows(ShipCannotException.class, () -> ship4.getState().needMoreReparations(ship2));
        assertThrows(ShipCannotException.class, () -> ship4.getState().decommission(ship2));
    }

    @Test
    public void testShipPendingConfirmationStateFunctionality() {

        // Cambiamos el estado de ship3 a PendingConfirmationState
        ship3.setState(new PendingConfirmationState());
        assertEquals(PendingConfirmationState.class, ship3.getState().getClass(), "Ship3 should start in PendingConfirmationState.");

        // Pruebas de funciones permitidas
        ship3.getState().acceptRepair(ship3);  // Cambia a UnderRepairState
        assertEquals(UnderRepairState.class, ship3.getState().getClass(), "Ship3 should transition to UnderRepairState after accepting repair.");

        // Volvemos a PendingConfirmationState para probar otras transiciones
        ship3.setState(new PendingConfirmationState());

        ship3.getState().cancelRepair(ship3);  // Cambia a AtBaseState
        assertEquals(AtBaseState.class, ship3.getState().getClass(), "Ship3 should transition to AtBaseState after cancelling repair.");

        // Volvemos a PendingConfirmationState para probar repairExpress
        ship7.setState(new PendingConfirmationState());

        ship7.getState().repairExpress(ship7);  // Cambia a AtBaseState
        assertEquals(AtBaseState.class, ship7.getState().getClass(), "Ship7 should transition to AtBaseState after repairExpress.");

        // Volvemos a PendingConfirmationState para probar excepciones
        ship3.setState(new PendingConfirmationState());

        // Pruebas de funciones no permitidas
        assertThrows(ShipCannotException.class, () -> ship3.getState().requestRepair(ship3), "Request repair should throw an exception in PendingConfirmationState.");
        assertThrows(ShipCannotException.class, () -> ship3.getState().doExercise(ship3), "Do exercise should throw an exception in PendingConfirmationState.");
        assertThrows(ShipCannotException.class, () -> ship3.getState().completedExercise(ship3), "Completed exercise should throw an exception in PendingConfirmationState.");
        assertThrows(ShipCannotException.class, () -> ship3.getState().sinkShip(ship3), "Sink ship should throw an exception in PendingConfirmationState.");
        assertThrows(ShipCannotException.class, () -> ship3.getState().repaired(ship3), "Repaired should throw an exception in PendingConfirmationState.");
        assertThrows(ShipCannotException.class, () -> ship3.getState().needMoreReparations(ship3), "Need more reparations should throw an exception in PendingConfirmationState.");
        // Decommission no lanza una excepción, sino que cambia el estado a DecommissionedState
        ship3.getState().decommission(ship3);  // Cambia a DecommissionedState
        assertEquals(DecommissionedState.class, ship3.getState().getClass(), "Ship3 should transition to DecommissionedState after being decommissioned.");
    }




    @Test
    public void testShipUnderRepairStateFunctionality() {

        // Cambiamos el estado de ship4 a UnderRepairState
        ship4.setState(new UnderRepairState());
        assertEquals(UnderRepairState.class, ship4.getState().getClass(), "Ship4 should start in UnderRepairState.");

        // Pruebas de funciones permitidas
        ship4.getState().repaired(ship4);  // Cambia a AtBaseState
        assertEquals(AtBaseState.class, ship4.getState().getClass(), "Ship4 should transition to AtBaseState after being repaired.");

        // Ahora ship4 está en AtBaseState, volvemos a UnderRepairState para probar otras funciones
        ship4.setState(new UnderRepairState());

        // Pruebas de funciones que deberían lanzar excepciones
        assertThrows(ShipCannotException.class, () -> ship4.getState().requestRepair(ship4), "Request repair should throw an exception in UnderRepairState.");
        assertThrows(ShipCannotException.class, () -> ship4.getState().doExercise(ship4), "Do exercise should throw an exception in UnderRepairState.");
        assertThrows(ShipCannotException.class, () -> ship4.getState().completedExercise(ship4), "Completed exercise should throw an exception in UnderRepairState.");
        assertThrows(ShipCannotException.class, () -> ship4.getState().sinkShip(ship4), "Sink ship should throw an exception in UnderRepairState.");
        assertThrows(ShipCannotException.class, () -> ship4.getState().acceptRepair(ship4), "Accept repair should throw an exception in UnderRepairState.");
        assertThrows(ShipCannotException.class, () -> ship4.getState().cancelRepair(ship4), "Cancel repair should throw an exception in UnderRepairState.");
        assertThrows(ShipCannotException.class, () -> ship4.getState().repairExpress(ship4), "Repair express should throw an exception in UnderRepairState.");

        // Pruebas de funciones permitidas que cambian de estado
        ship4.getState().needMoreReparations(ship4);  // Cambia a PendingConfirmationState
        assertEquals(PendingConfirmationState.class, ship4.getState().getClass(), "Ship4 should transition to PendingConfirmationState after needing more reparations.");

        // Volvemos a UnderRepairState para probar de nuevo el comportamiento
        ship4.setState(new UnderRepairState());
        assertThrows(ShipCannotException.class, () -> ship4.getState().decommission(ship4), "Decommission should throw an exception in UnderRepairState.");
    }

    @Test
    public void testShipSunkStateFunctionality() {

        // Cambiamos el estado de ship5 a SunkState
        ship5.setState(new SunkState());
        assertEquals(SunkState.class, ship5.getState().getClass(), "Ship5 should start in SunkState.");

        // Pruebas de funciones no permitidas (ninguna operación está permitida en este estado)
        assertThrows(ShipCannotException.class, () -> ship5.getState().requestRepair(ship5), "Request repair should throw an exception in SunkState.");
        assertThrows(ShipCannotException.class, () -> ship5.getState().doExercise(ship5), "Do exercise should throw an exception in SunkState.");
        assertThrows(ShipCannotException.class, () -> ship5.getState().completedExercise(ship5), "Completed exercise should throw an exception in SunkState.");
        assertThrows(ShipCannotException.class, () -> ship5.getState().sinkShip(ship5), "Sink ship should throw an exception in SunkState.");
        assertThrows(ShipCannotException.class, () -> ship5.getState().acceptRepair(ship5), "Accept repair should throw an exception in SunkState.");
        assertThrows(ShipCannotException.class, () -> ship5.getState().cancelRepair(ship5), "Cancel repair should throw an exception in SunkState.");
        assertThrows(ShipCannotException.class, () -> ship5.getState().repairExpress(ship5), "Repair express should throw an exception in SunkState.");
        assertThrows(ShipCannotException.class, () -> ship5.getState().repaired(ship5), "Repaired should throw an exception in SunkState.");
        assertThrows(ShipCannotException.class, () -> ship5.getState().needMoreReparations(ship5), "Need more reparations should throw an exception in SunkState.");
        assertThrows(ShipCannotException.class, () -> ship5.getState().decommission(ship5), "Decommission should throw an exception in SunkState.");
    }


    @Test
    public void testShipDecommissionedStateFunctionality() {

        // Cambiamos el estado de ship6 a DecommissionedState
        ship6.setState(new DecommissionedState());
        assertEquals(DecommissionedState.class, ship6.getState().getClass(), "Ship6 should start in DecommissionedState.");

        // Pruebas de funciones no permitidas (ninguna operación está permitida en este estado)
        assertThrows(ShipCannotException.class, () -> ship6.getState().requestRepair(ship6));
        assertThrows(ShipCannotException.class, () -> ship6.getState().doExercise(ship6));
        assertThrows(ShipCannotException.class, () -> ship6.getState().completedExercise(ship6));
        assertThrows(ShipCannotException.class, () -> ship6.getState().sinkShip(ship6));
        assertThrows(ShipCannotException.class, () -> ship6.getState().acceptRepair(ship6));
        assertThrows(ShipCannotException.class, () -> ship6.getState().cancelRepair(ship6));
        assertThrows(ShipCannotException.class, () -> ship6.getState().repairExpress(ship6));
        assertThrows(ShipCannotException.class, () -> ship6.getState().repaired(ship6));
        assertThrows(ShipCannotException.class, () -> ship6.getState().needMoreReparations(ship6));
        assertThrows(ShipCannotException.class, () -> ship6.getState().decommission(ship6));
    }














    //COMPROBACIÓN DE TODOS LOS BARCOS HACIENDO UN CIRCUITO DONDE SE PRUEBAN TODOAS LAS FUNCIONES Y ESTADOS EN DONDE
    //SE PUEDE ENCONTRAR EL BARCO

    //SHIP 1

    @Test
    public void testShip1StateTransitions() {
        // 1. Hacemos que el barco realice un ejercicio
        ship1.getState().doExercise(ship1);  // Cambia a InExerciseState
        assertEquals(InExerciseState.class, ship1.getState().getClass(), "Ship1 should be in InExerciseState after starting exercise.");

        // 2. El barco completa el ejercicio
        ship1.getState().completedExercise(ship1);  // Cambia a AtBaseState
        assertEquals(AtBaseState.class, ship1.getState().getClass(), "Ship1 should be at base after completing the exercise.");

        //Comprobamos los fondos de la flota
        assertEquals(36250000 ,ship1.getFleet().getFunds());

        // 3. Solicitamos reparación del barco
        ship1.getState().requestRepair(ship1);  // Cambia a PendingConfirmationState
        assertEquals(PendingConfirmationState.class, ship1.getState().getClass(), "Ship1 should be in PendingConfirmationState after requesting repair.");

        // 4. Realizamos una reparación express (esperamos que se lance una excepción)
        assertThrows(ShipCannotException.class, () -> ship1.getState().repairExpress(ship1), "RepairExpress should throw a ShipCannotException when attempted.");

        // 5. Aceptamos la reparación, lo que debería cambiar el estado a UnderRepairState
        ship1.getState().acceptRepair(ship1);  // Cambia a UnderRepairState
        assertEquals(UnderRepairState.class, ship1.getState().getClass(), "Ship1 should be in UnderRepairState after accepting repair.");

        //Comprobamos los fondos de la flota
        assertEquals(18750000 ,ship1.getFleet().getFunds());

        // 6. El barco es reparado, cambiamos a AtBaseState
        ship1.getState().repaired(ship1);  // Cambia a AtBaseState
        assertEquals(AtBaseState.class, ship1.getState().getClass(), "Ship1 should be at base after being repaired.");

        // 7. Hacemos que el barco realice un ejercicio nuevamente
        ship1.getState().doExercise(ship1);  // Cambia a InExerciseState
        assertEquals(InExerciseState.class, ship1.getState().getClass(), "Ship1 should be in InExerciseState after starting another exercise.");

        //Comprobamos los fondos de la flota
        assertEquals(18750000 ,ship1.getFleet().getFunds());

        // 8. Finalmente, el barco se hunde
        ship1.getState().sinkShip(ship1);  // Cambia a SunkState
        assertEquals(SunkState.class, ship1.getState().getClass(), "Ship1 should be in SunkState after sinking.");
    }

    @Test
    public void testShip1RepairAndDecommission() {
        // 1. El barco solicita reparación
        ship1.getState().requestRepair(ship1);  // Cambia a PendingConfirmationState
        assertEquals(PendingConfirmationState.class, ship1.getState().getClass(), "Ship1 should be in PendingConfirmationState after requesting repair.");

        // 2. El barco no puede aceptar la reparación por falta de fondos
        // Se espera que lance una excepción, como ShipCannotException
        assertThrows(ShipCannotException.class, () -> ship1.getState().acceptRepair(ship1), "Ship1 should throw ShipCannotException when attempting to accept repair due to lack of funds.");

        // 3. El barco sigue en PendingConfirmationState porque no puede aceptar la reparación
        assertEquals(PendingConfirmationState.class, ship1.getState().getClass(), "Ship1 should remain in PendingConfirmationState after failing to accept repair due to lack of funds.");

        // 4. El barco cancela la reparación
        ship1.getState().cancelRepair(ship1);  // Vuelve a AtBaseState
        assertEquals(AtBaseState.class, ship1.getState().getClass(), "Ship1 should be in AtBaseState after canceling repair.");

        // 5. Cambiamos los fondos de la flota
        double funds = ship1.getFleet().getFunds() + 10000000;  // Aumentamos los fondos
        ship1.getFleet().setFunds(funds);

        // 6. El barco solicita reparación de nuevo
        ship1.getState().requestRepair(ship1);  // Cambia a PendingConfirmationState
        assertEquals(PendingConfirmationState.class, ship1.getState().getClass(), "Ship1 should be in PendingConfirmationState after requesting repair again.");

        // 7. Aceptamos la reparación, lo que debería cambiar el estado a UnderRepairState
        ship1.getState().acceptRepair(ship1);  // Cambia a UnderRepairState
        assertEquals(UnderRepairState.class, ship1.getState().getClass(), "Ship1 should be in UnderRepairState after accepting repair.");

        // 8. El barco necesita más reparaciones
        ship1.getState().needMoreReparations(ship1);  // Cambia a PendingConfirmationState
        assertEquals(PendingConfirmationState.class, ship1.getState().getClass(), "Ship1 should be in PendingConfirmationState after needing more reparations.");

        // 9. Finalmente, el barco es desmantelado
        ship1.getState().decommission(ship1);  // Cambia a DecommissionedState
        assertEquals(DecommissionedState.class, ship1.getState().getClass(), "Ship1 should be in DecommissionedState after being decommissioned.");
    }




    //SHIP 2

    @Test
    public void testShip2StateTransitions() {
        // 1. Hacemos que el barco realice un ejercicio
        ship2.getState().doExercise(ship2);  // Cambia a InExerciseState
        assertEquals(InExerciseState.class, ship2.getState().getClass(), "Ship2 should be in InExerciseState after starting exercise.");

        // 2. El barco completa el ejercicio
        ship2.getState().completedExercise(ship2);  // Cambia a AtBaseState
        assertEquals(AtBaseState.class, ship2.getState().getClass(), "Ship2 should be at base after completing the exercise.");

        //Comprobamos los fondos de la flota
        assertEquals(28750000 ,ship2.getFleet().getFunds());

        // 3. Solicitamos reparación del barco
        ship2.getState().requestRepair(ship2);  // Cambia a PendingConfirmationState
        assertEquals(PendingConfirmationState.class, ship2.getState().getClass(), "Ship2 should be in PendingConfirmationState after requesting repair.");

        // 4. Realizamos una reparación express (esperamos que se lance una excepción)
        assertThrows(ShipCannotException.class, () -> ship2.getState().repairExpress(ship2), "RepairExpress should throw a RepairException when attempted.");

        // 5. Aceptamos la reparación, lo que debería cambiar el estado a UnderRepairState
        ship2.getState().acceptRepair(ship2);  // Cambia a UnderRepairState
        assertEquals(UnderRepairState.class, ship2.getState().getClass(), "Ship2 should be in UnderRepairState after accepting repair.");

        //Comprobamos los fondos de la flota
        assertEquals(16250000 ,ship2.getFleet().getFunds());

        // 6. El barco es reparado, cambiamos a AtBaseState
        ship2.getState().repaired(ship2);  // Cambia a AtBaseState
        assertEquals(AtBaseState.class, ship2.getState().getClass(), "Ship2 should be at base after being repaired.");

        // 7. Hacemos que el barco realice un ejercicio nuevamente
        ship2.getState().doExercise(ship2);  // Cambia a InExerciseState
        assertEquals(InExerciseState.class, ship2.getState().getClass(), "Ship2 should be in InExerciseState after starting another exercise.");

        // 8. Finalmente, el barco se hunde
        ship2.getState().sinkShip(ship2);  // Cambia a SunkState
        assertEquals(SunkState.class, ship2.getState().getClass(), "Ship2 should be in SunkState after sinking.");
    }

    @Test
    public void testShip2RepairAndDecommission() {
        // 1. El barco solicita reparación
        ship2.getState().requestRepair(ship2);  // Cambia a PendingConfirmationState
        assertEquals(PendingConfirmationState.class, ship2.getState().getClass(), "Ship2 should be in PendingConfirmationState after requesting repair.");

        // 2. El barco no puede aceptar la reparación por falta de fondos
        // Se espera que lance una excepción, como InsufficientFundsException
        assertThrows(ShipCannotException.class, () -> ship2.getState().acceptRepair(ship2), "Ship2 should throw InsufficientFundsException when attempting to accept repair due to lack of funds.");

        // 3. El barco sigue en PendingConfirmationState porque no puede aceptar la reparación
        assertEquals(PendingConfirmationState.class, ship2.getState().getClass(), "Ship2 should remain in PendingConfirmationState after failing to accept repair due to lack of funds.");

        // 4. El barco cancela la reparación
        ship2.getState().cancelRepair(ship2);  // Vuelve a AtBaseState
        assertEquals(AtBaseState.class, ship2.getState().getClass(), "Ship2 should be in AtBaseState after canceling repair.");

        // 5. Cambiamos los fondos de la flota
        double funds= ship2.getFleet().getFunds() +8000000;
        ship2.getFleet().setFunds(funds);

        // 6. El barco solicita reparación de nuevo
        ship2.getState().requestRepair(ship2);  // Cambia a PendingConfirmationState
        assertEquals(PendingConfirmationState.class, ship2.getState().getClass(), "Ship2 should be in PendingConfirmationState after requesting repair again.");

        // 7. Aceptamos la reparación, lo que debería cambiar el estado a UnderRepairState
        ship2.getState().acceptRepair(ship2);  // Cambia a UnderRepairState
        assertEquals(UnderRepairState.class, ship2.getState().getClass(), "Ship2 should be in UnderRepairState after accepting repair.");

        // 8. El barco necesita más reparaciones
        ship2.getState().needMoreReparations(ship2);  // Cambia a PendingConfirmationState
        assertEquals(PendingConfirmationState.class, ship2.getState().getClass(), "Ship2 should be in PendingConfirmationState after requesting repair again.");

        // 9. Finalmente, el barco es desmantelado
        ship2.getState().decommission(ship2);  // Cambia a DecommissionedState
        assertEquals(DecommissionedState.class, ship2.getState().getClass(), "Ship2 should be in DecommissionedState after being decommissioned.");
    }







    //SHIP 3

    @Test
    public void testShip3StateTransitions() {
        // 1. Hacemos que el barco realice un ejercicio
        ship3.getState().doExercise(ship3);  // Cambia a InExerciseState
        assertEquals(InExerciseState.class, ship3.getState().getClass(), "Ship3 should be in InExerciseState after starting exercise.");

        //Comprobamos los fondos de la flota
        assertEquals(10000000 ,ship2.getFleet().getFunds());

        // 2. El barco completa el ejercicio
        ship3.getState().completedExercise(ship3);  // Cambia a AtBaseState
        assertEquals(AtBaseState.class, ship3.getState().getClass(), "Ship3 should be at base after completing the exercise.");

        //Comprobamos los fondos de la flota
        assertEquals(21250000 ,ship2.getFleet().getFunds());

        // 3. Solicitamos reparación del barco
        ship3.getState().requestRepair(ship3);  // Cambia a PendingConfirmationState
        assertEquals(PendingConfirmationState.class, ship3.getState().getClass(), "Ship3 should be in PendingConfirmationState after requesting repair.");

        // 4. Realizamos una reparación express (esperamos que se lance una excepción)
        assertThrows(ShipCannotException.class, () -> ship3.getState().repairExpress(ship3), "RepairExpress should throw a RepairException when attempted.");

        // 5. Aceptamos la reparación, lo que debería cambiar el estado a UnderRepairState
        ship3.getState().acceptRepair(ship3);  // Cambia a UnderRepairState
        assertEquals(UnderRepairState.class, ship3.getState().getClass(), "Ship3 should be in UnderRepairState after accepting repair.");

        //Comprobamos los fondos de la flota
        assertEquals(13750000 ,ship2.getFleet().getFunds());

        // 6. El barco es reparado, cambiamos a AtBaseState
        ship3.getState().repaired(ship3);  // Cambia a AtBaseState
        assertEquals(AtBaseState.class, ship3.getState().getClass(), "Ship3 should be at base after being repaired.");

        // 7. Hacemos que el barco realice un ejercicio nuevamente
        ship3.getState().doExercise(ship3);  // Cambia a InExerciseState
        assertEquals(InExerciseState.class, ship3.getState().getClass(), "Ship3 should be in InExerciseState after starting another exercise.");

        // 8. Finalmente, el barco se hunde
        ship3.getState().sinkShip(ship3);  // Cambia a SunkState
        assertEquals(SunkState.class, ship3.getState().getClass(), "Ship3 should be in SunkState after sinking.");
    }

    @Test
    public void testShip3RepairAndDecommission() {
        // 1. El barco solicita reparación
        ship3.getState().requestRepair(ship3);  // Cambia a PendingConfirmationState
        assertEquals(PendingConfirmationState.class, ship3.getState().getClass(), "Ship3 should be in PendingConfirmationState after requesting repair.");

        // 2. El barco acepta la reparación
        ship3.getState().acceptRepair(ship3);  // Cambia a UnderRepairState
        assertEquals(UnderRepairState.class, ship3.getState().getClass(), "Ship3 should be in UnderRepairState after accepting repair.");

        //Comprobamos los fondos de la flota
        assertEquals(2500000 ,ship3.getFleet().getFunds());

        // 3. El barco necesita más reparaciones
        ship3.getState().needMoreReparations(ship3);  // Cambia a PendingConfirmationState
        assertEquals(PendingConfirmationState.class, ship3.getState().getClass(), "Ship3 should be in PendingConfirmationState after needing more reparations.");

        // 4. El barco cancela la reparación
        ship3.getState().cancelRepair(ship3);  // Vuelve a AtBaseState
        assertEquals(AtBaseState.class, ship3.getState().getClass(), "Ship3 should be in AtBaseState after canceling repair.");

        //Comprobamos los fondos de la flota
        assertEquals(2500000 ,ship3.getFleet().getFunds());

        // 5. El barco solicita reparación de nuevo
        ship3.getState().requestRepair(ship3);  // Cambia a PendingConfirmationState
        assertEquals(PendingConfirmationState.class, ship3.getState().getClass(), "Ship3 should be in PendingConfirmationState after requesting repair again.");

        // 6. Finalmente, el barco es desmantelado
        ship3.getState().decommission(ship3);  // Cambia a DecommissionedState
        assertEquals(DecommissionedState.class, ship3.getState().getClass(), "Ship3 should be in DecommissionedState after being decommissioned.");
    }





    //SHIP 4

    @Test
    public void testShip4StateTransitions() {
        // 1. Hacemos que el barco realice un ejercicio
        ship4.getState().doExercise(ship4);  // Cambia a InExerciseState
        assertEquals(InExerciseState.class, ship4.getState().getClass(), "Ship4 should be in InExerciseState after starting exercise.");

        // 2. El barco completa el ejercicio
        ship4.getState().completedExercise(ship4);  // Cambia a AtBaseState
        assertEquals(AtBaseState.class, ship4.getState().getClass(), "Ship4 should be at base after completing the exercise.");

        //Comprobamos los fondos de la flota
        assertEquals(19000000 ,ship4.getFleet().getFunds());

        // 3. Solicitamos reparación del barco
        ship4.getState().requestRepair(ship4);  // Cambia a PendingConfirmationState
        assertEquals(PendingConfirmationState.class, ship4.getState().getClass(), "Ship4 should be in PendingConfirmationState after requesting repair.");

        // 4. Realizamos una reparación express (esperamos que se lance una excepción)
        assertThrows(ShipCannotException.class, () -> ship4.getState().repairExpress(ship4), "RepairExpress should throw a RepairException when attempted.");

        // 5. Aceptamos la reparación, lo que debería cambiar el estado a UnderRepairState
        ship4.getState().acceptRepair(ship4);  // Cambia a UnderRepairState
        assertEquals(UnderRepairState.class, ship4.getState().getClass(), "Ship4 should be in UnderRepairState after accepting repair.");

        //Comprobamos los fondos de la flota
        assertEquals(13000000 ,ship2.getFleet().getFunds());

        // 6. El barco es reparado, cambiamos a AtBaseState
        ship4.getState().repaired(ship4);  // Cambia a AtBaseState
        assertEquals(AtBaseState.class, ship4.getState().getClass(), "Ship4 should be at base after being repaired.");

        // 7. Hacemos que el barco realice un ejercicio nuevamente
        ship4.getState().doExercise(ship4);  // Cambia a InExerciseState
        assertEquals(InExerciseState.class, ship4.getState().getClass(), "Ship4 should be in InExerciseState after starting another exercise.");

        //Comprobamos los fondos de la flota
        assertEquals(13000000 ,ship2.getFleet().getFunds());

        // 8. Finalmente, el barco se hunde
        ship4.getState().sinkShip(ship4);  // Cambia a SunkState
        assertEquals(SunkState.class, ship4.getState().getClass(), "Ship4 should be in SunkState after sinking.");
    }

    @Test
    public void testShip4RepairAndDecommission() {
        // 1. El barco solicita reparación
        ship4.getState().requestRepair(ship4);  // Cambia a PendingConfirmationState
        assertEquals(PendingConfirmationState.class, ship4.getState().getClass(), "Ship4 should be in PendingConfirmationState after requesting repair.");

        // 2. El barco acepta la reparación
        ship4.getState().acceptRepair(ship4);  // Cambia a UnderRepairState
        assertEquals(UnderRepairState.class, ship4.getState().getClass(), "Ship4 should be in UnderRepairState after accepting repair.");

        // 3. El barco necesita más reparaciones
        ship4.getState().needMoreReparations(ship4);  // Cambia a PendingConfirmationState
        assertEquals(PendingConfirmationState.class, ship4.getState().getClass(), "Ship4 should be in PendingConfirmationState after needing more reparations.");

        // 4. El barco cancela la reparación
        ship4.getState().cancelRepair(ship4);  // Vuelve a AtBaseState
        assertEquals(AtBaseState.class, ship4.getState().getClass(), "Ship4 should be in AtBaseState after canceling repair.");

        // 5. El barco solicita reparación de nuevo
        ship4.getState().requestRepair(ship4);  // Cambia a PendingConfirmationState
        assertEquals(PendingConfirmationState.class, ship4.getState().getClass(), "Ship4 should be in PendingConfirmationState after requesting repair again.");

        // 6. Finalmente, el barco es desmantelado
        ship4.getState().decommission(ship4);  // Cambia a DecommissionedState
        assertEquals(DecommissionedState.class, ship4.getState().getClass(), "Ship4 should be in DecommissionedState after being decommissioned.");
    }








    //SHIP 5

    @Test
    public void testShip5StateTransitions() {
        // 1. Hacemos que el barco realice un ejercicio
        ship5.getState().doExercise(ship5);  // Cambia a InExerciseState
        assertEquals(InExerciseState.class, ship5.getState().getClass(), "Ship5 should be in InExerciseState after starting exercise.");

        // 2. El barco completa el ejercicio
        ship5.getState().completedExercise(ship5);  // Cambia a AtBaseState
        assertEquals(AtBaseState.class, ship5.getState().getClass(), "Ship5 should be at base after completing the exercise.");

        // 3. Solicitamos reparación del barco
        ship5.getState().requestRepair(ship5);  // Cambia a PendingConfirmationState
        assertEquals(PendingConfirmationState.class, ship5.getState().getClass(), "Ship5 should be in PendingConfirmationState after requesting repair.");

        // 4. Realizamos una reparación express (esperamos que se lance una excepción)
        assertThrows(ShipCannotException.class, () -> ship5.getState().repairExpress(ship5), "RepairExpress should throw a RepairException when attempted.");

        // 5. Aceptamos la reparación, lo que debería cambiar el estado a UnderRepairState
        ship5.getState().acceptRepair(ship5);  // Cambia a UnderRepairState
        assertEquals(UnderRepairState.class, ship5.getState().getClass(), "Ship5 should be in UnderRepairState after accepting repair.");

        // 6. El barco es reparado, cambiamos a AtBaseState
        ship5.getState().repaired(ship5);  // Cambia a AtBaseState
        assertEquals(AtBaseState.class, ship5.getState().getClass(), "Ship5 should be at base after being repaired.");

        // 7. Hacemos que el barco realice un ejercicio nuevamente
        ship5.getState().doExercise(ship5);  // Cambia a InExerciseState
        assertEquals(InExerciseState.class, ship5.getState().getClass(), "Ship5 should be in InExerciseState after starting another exercise.");

        // 8. Finalmente, el barco se hunde
        ship5.getState().sinkShip(ship5);  // Cambia a SunkState
        assertEquals(SunkState.class, ship5.getState().getClass(), "Ship5 should be in SunkState after sinking.");
    }


    @Test
    public void testShip5RepairAndDecommission() {
        // 1. El barco solicita reparación
        ship5.getState().requestRepair(ship5);  // Cambia a PendingConfirmationState
        assertEquals(PendingConfirmationState.class, ship5.getState().getClass(), "Ship5 should be in PendingConfirmationState after requesting repair.");

        // 2. El barco acepta la reparación
        ship5.getState().acceptRepair(ship5);  // Cambia a UnderRepairState
        assertEquals(UnderRepairState.class, ship5.getState().getClass(), "Ship5 should be in UnderRepairState after accepting repair.");

        // 3. El barco necesita más reparaciones
        ship5.getState().needMoreReparations(ship5);  // Cambia a PendingConfirmationState
        assertEquals(PendingConfirmationState.class, ship5.getState().getClass(), "Ship5 should be in PendingConfirmationState after needing more reparations.");

        // 4. El barco cancela la reparación
        ship5.getState().cancelRepair(ship5);  // Vuelve a AtBaseState
        assertEquals(AtBaseState.class, ship5.getState().getClass(), "Ship5 should be in AtBaseState after canceling repair.");

        // 5. El barco solicita reparación de nuevo
        ship5.getState().requestRepair(ship5);  // Cambia a PendingConfirmationState
        assertEquals(PendingConfirmationState.class, ship5.getState().getClass(), "Ship5 should be in PendingConfirmationState after requesting repair again.");

        // 6. Finalmente, el barco es desmantelado
        ship5.getState().decommission(ship5);  // Cambia a DecommissionedState
        assertEquals(DecommissionedState.class, ship5.getState().getClass(), "Ship5 should be in DecommissionedState after being decommissioned.");
    }











    //SHIP 6

    @Test
    public void testShip6StateTransitions() {
        // 1. Hacemos que el barco realice un ejercicio
        ship6.getState().doExercise(ship6);  // Cambia a InExerciseState
        assertEquals(InExerciseState.class, ship6.getState().getClass(), "Ship6 should be in InExerciseState after starting exercise.");

        // 2. El barco completa el ejercicio
        ship6.getState().completedExercise(ship6);  // Cambia a AtBaseState
        assertEquals(AtBaseState.class, ship6.getState().getClass(), "Ship6 should be at base after completing the exercise.");

        //Comprobamos los fondos de la flota
        assertEquals(13750000 ,ship6.getFleet().getFunds());

        // 3. Solicitamos reparación del barco
        ship6.getState().requestRepair(ship6);  // Cambia a PendingConfirmationState
        assertEquals(PendingConfirmationState.class, ship6.getState().getClass(), "Ship6 should be in PendingConfirmationState after requesting repair.");

        // 4. Realizamos una reparación express
        ship6.getState().repairExpress(ship6);  // Cambia a AtBaseState
        assertEquals(AtBaseState.class, ship6.getState().getClass(), "Ship6 should be at base after express repair.");

        //Comprobamos los fondos de la flota
        assertEquals(13750000 ,ship6.getFleet().getFunds());

        // 5. Hacemos que el barco realice un ejercicio nuevamente
        ship6.getState().doExercise(ship6);  // Cambia a InExerciseState
        assertEquals(InExerciseState.class, ship6.getState().getClass(), "Ship6 should be in InExerciseState after starting another exercise.");

        //Comprobamos los fondos de la flota
        assertEquals(13750000 ,ship6.getFleet().getFunds());

        // 6. El barco completa el ejercicio
        ship6.getState().completedExercise(ship6);  // Cambia a AtBaseState
        assertEquals(AtBaseState.class, ship6.getState().getClass(), "Ship6 should be at base after completing the exercise.");

        //Comprobamos los fondos de la flota
        assertEquals(17500000 ,ship6.getFleet().getFunds());

        // 7. Solicitamos reparación del barco
        ship6.getState().requestRepair(ship6);  // Cambia a PendingConfirmationState
        assertEquals(PendingConfirmationState.class, ship6.getState().getClass(), "Ship6 should be in PendingConfirmationState after requesting repair.");

        // 7. Aceptamos la reparación
        ship6.getState().acceptRepair(ship6);  // Cambia a UnderRepairState
        assertEquals(UnderRepairState.class, ship6.getState().getClass(), "Ship6 should be in UnderRepairState after accepting repair.");

        //Comprobamos los fondos de la flota, en este caso se descuenta tmb el express
        assertEquals(12500000 ,ship6.getFleet().getFunds());

        // 8. El barco es reparado, cambiamos a AtBaseState
        ship6.getState().repaired(ship6);  // Cambia a AtBaseState
        assertEquals(AtBaseState.class, ship6.getState().getClass(), "Ship6 should be at base after being repaired.");


        // 9. Hacemos que el barco realice un ejercicio nuevamente
        ship6.getState().doExercise(ship6);  // Cambia a InExerciseState
        assertEquals(InExerciseState.class, ship6.getState().getClass(), "Ship6 should be in InExerciseState after starting another exercise.");


        // 10. Finalmente, el barco se hunde
        ship6.getState().sinkShip(ship6);  // Cambia a SunkState
        assertEquals(SunkState.class, ship6.getState().getClass(), "Ship6 should be in SunkState after sinking.");
    }

    @Test
    public void testShip6RepairAndDecommission() {

        // 1. El barco solicita reparación
        ship6.getState().requestRepair(ship6);  // Cambia a PendingConfirmationState
        assertEquals(PendingConfirmationState.class, ship6.getState().getClass(), "Ship6 should be in PendingConfirmationState after requesting repair.");

        // 2. El barco acepta la reparación
        ship6.getState().acceptRepair(ship6);  // Cambia a UnderRepairState
        assertEquals(UnderRepairState.class, ship6.getState().getClass(), "Ship6 should be in UnderRepairState after accepting repair.");

        // 3. El barco necesita más reparaciones
        ship6.getState().needMoreReparations(ship6);  // Cambia a PendingConfirmationState
        assertEquals(PendingConfirmationState.class, ship6.getState().getClass(), "Ship6 should be in PendingConfirmationState after needing more reparations.");

        // 4. El barco cancela la reparación
        ship6.getState().cancelRepair(ship6);  // Vuelve a AtBaseState
        assertEquals(AtBaseState.class, ship6.getState().getClass(), "Ship6 should be in AtBaseState after canceling repair.");

        // 5. El barco solicita reparación de nuevo
        ship6.getState().requestRepair(ship6);  // Cambia a PendingConfirmationState
        assertEquals(PendingConfirmationState.class, ship6.getState().getClass(), "Ship6 should be in PendingConfirmationState after requesting repair again.");

        // 6. Finalmente, el barco es desmantelado
        ship6.getState().decommission(ship6);  // Cambia a DecommissionedState
        assertEquals(DecommissionedState.class, ship6.getState().getClass(), "Ship6 should be in DecommissionedState after being decommissioned.");
    }






    //SHIP 7
    @Test
    public void testShip7RepairAndDecommission() {
        // 1. El barco solicita reparación
        ship7.getState().requestRepair(ship7);  // Cambia a PendingConfirmationState
        assertEquals(PendingConfirmationState.class, ship7.getState().getClass(), "Ship7 should be in PendingConfirmationState after requesting repair.");

        // 2. El barco acepta la reparación
        ship7.getState().acceptRepair(ship7);  // Cambia a UnderRepairState
        assertEquals(UnderRepairState.class, ship7.getState().getClass(), "Ship7 should be in UnderRepairState after accepting repair.");

        // 3. El barco necesita más reparaciones
        ship7.getState().needMoreReparations(ship7);  // Cambia a PendingConfirmationState
        assertEquals(PendingConfirmationState.class, ship7.getState().getClass(), "Ship7 should be in PendingConfirmationState after needing more reparations.");

        // 4. El barco cancela la reparación
        ship7.getState().cancelRepair(ship7);  // Vuelve a AtBaseState
        assertEquals(AtBaseState.class, ship7.getState().getClass(), "Ship7 should be in AtBaseState after canceling repair.");

        // 5. El barco solicita reparación de nuevo
        ship7.getState().requestRepair(ship7);  // Cambia a PendingConfirmationState
        assertEquals(PendingConfirmationState.class, ship7.getState().getClass(), "Ship7 should be in PendingConfirmationState after requesting repair again.");

        // 6. Finalmente, el barco es desmantelado
        ship7.getState().decommission(ship7);  // Cambia a DecommissionedState
        assertEquals(DecommissionedState.class, ship7.getState().getClass(), "Ship7 should be in DecommissionedState after being decommissioned.");
    }


}

package e1;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Fleet {
    private final List<Ship> ships;  // Lista de barcos en la flota
    private double funds;      // Fondos disponibles de la flota
    private double debts;      // Deudas xpress

    public Fleet(String name, double initialFunds) {
        this.ships = new ArrayList<>();
        this.funds = initialFunds;
        this.debts = 0;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public double getDebts() {
        return debts;
    }

    public void setDebts(double debts) {
        this.debts = debts;
    }

    // Agregar un barco a la flota
    public void addShip(Ship ship) {
        ships.add(ship);
        System.out.println(ship.getName() + " has been added to the fleet.");
    }

    // Listar todos los barcos activos de la flota
    public void listActiveShips() {
        System.out.println("ACTIVE SHIPS:");
        for (Ship ship : ships) {
            if (ship.getState() instanceof AtBaseState || ship.getState() instanceof InExerciseState) {
                System.out.println(ship.getName() + " | State: " + ship.getState().getClass().getSimpleName());
            }
        }
    }

    // Listar todos los barcos inactivos de la flota
    public void listInactiveShips() {
        System.out.println("INACTIVE SHIPS:");
        for (Ship ship : ships) {
            if (ship.getState() instanceof DecommissionedState || ship.getState() instanceof SunkState) {
                System.out.println(ship.getName() + " | State: " + ship.getState().getClass().getSimpleName());
            }
        }
    }


    // Obtener los fondos disponibles
    public double getFunds() {
        return funds;
    }

    public void setFunds(double funds) {
        this.funds = funds;
    }

    // Aumentar fondos de la flota tras completar ejercicios navales
    public void addFundsFromExercise(double amount) {
        funds += amount;
        System.out.println("Fleet funds increased by " + amount + ". Current funds: " + funds);
    }


    public void showFleetStatus() {
        System.out.println("FLEET STATUS:");

        // Mostrar barcos activos
        System.out.println("ACTIVE VESSELS---------------------------------");
        for (Ship ship : ships) {
            if (!(ship.getState() instanceof DecommissionedState) && !(ship.getState() instanceof SunkState)) {
                System.out.println("Name: " + ship.getName() + " | Missions: " + ship.getNumMissions());
            }
        }

        // Mostrar barcos inactivos
        System.out.println("INACTIVE VESSELS---------------------------------");
        for (Ship ship : ships) {
            if (ship.getState() instanceof DecommissionedState) {
                System.out.println("Name: " + ship.getName() + " | Reasons: Decommissioned | Missions: " + ship.getNumMissions());
            } else if (ship.getState() instanceof SunkState) {
                System.out.println("Name: " + ship.getName() + " | Reasons: Sunk | Missions: " + ship.getNumMissions());
            }
        }

        // Configuración para asegurar el formato exacto de los fondos
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat scientificFormat = new DecimalFormat("0.0E0", symbols);
        System.out.println("Available funds: " + scientificFormat.format(funds));
        System.out.println("\n");
    }



}

package e1;

public class Ship {
    private String name;
    private ShipType type;
    private int numMissions;
    private ShipState state;
    private Fleet fleet;  // Referencia a la flota que debe de estar
    private boolean crash;

    public Ship(String name, ShipType type, Fleet fleet) {
        this.name = name;
        this.type = type;
        this.state = new AtBaseState(); // Establece un estado inicial
        this.fleet = fleet;
        this.numMissions= 0;
        this.crash = false;
    }
    // Métodos para obtener el nombre del barco y el estado actual
    public String getName() {
        return name;
    }

    public ShipState getState() {
        return state;
    }

    public Fleet getFleet() {
        return fleet;
    }

    // Cambiar el estado del barco
    public void setState(ShipState state) {
        this.state = state;
    }

    // Obtener el tipo de barco (DE, DD, etc.)
    public ShipType getShipType() {
        return type;
    }

    // Obtener y establecer el número de misiones completadas
    public int getNumMissions() {
        return numMissions;
    }

    public void setNumMissions(int numMissions) {
        this.numMissions = numMissions;
    }

    public boolean isCrash() {
        return crash;
    }

    public void setCrash(boolean crash) {
        this.crash = crash;
    }

    // Metodo para calcular el coste de reparación en función del tipo de barco
    public double calculateRepairCost() {
        // El coste de reparación es el peso del barco (en toneladas) multiplicado por 500
        return this.type.getWeight() * 500;  // 500 unidades por tonelada de peso
    }


    // Metodo para calcular el coste de reparación en función del tipo de barco
    public double calculateRewardCost() {
        // El coste de reparación es el peso del barco (en toneladas) multiplicado por 500
        return this.type.getWeight() * 750;  // 500 unidades por tonelada de peso
    }


    // Metodo para mostrar la información del barco
    public String getShipInfo() {
        return "Name: " + name + " | Type: " + type.getName() + " | Weight: " + type.getWeight() + " tons";
    }

    public void repairShip(Ship ship) {
        // Aquí es donde se llama a la función de aceptar reparación, pasando la flota
        double repairCost = ship.calculateRepairCost();
        double fundsAfterRepare=ship.getFleet().getFunds()-repairCost;
        ship.getFleet().setFunds(fundsAfterRepare);
    }
}

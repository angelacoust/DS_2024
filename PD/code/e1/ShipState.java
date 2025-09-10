package e1;

public interface ShipState {
    void requestRepair(Ship ship);
    void doExercise(Ship ship);
    void completedExercise(Ship ship);
    void sinkShip(Ship ship);
    void acceptRepair(Ship ship);
    void cancelRepair(Ship ship);
    void repairExpress(Ship ship);
    void repaired(Ship ship);
    void needMoreReparations(Ship ship);
    void decommission(Ship ship);
}


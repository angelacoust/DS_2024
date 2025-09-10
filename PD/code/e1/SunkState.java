package e1;

public class SunkState implements ShipState {
    @Override
    public void requestRepair(Ship ship) {
        throw new ShipCannotException(ship.getName() + " is sunk and cannot be repaired.");
    }

    @Override
    public void doExercise(Ship ship) {
        throw new ShipCannotException(ship.getName() + " is sunk and cannot perform exercises.");
    }

    @Override
    public void completedExercise(Ship ship) {
        throw new ShipCannotException(ship.getName() + " cannot complete exercise because it is sunk.");
    }

    @Override
    public void sinkShip(Ship ship) {
        throw new ShipCannotException(ship.getName() + " is already sunk.");
    }

    @Override
    public void acceptRepair(Ship ship) {
        throw new ShipCannotException(ship.getName() + " cannot accept repair because it is sunk.");
    }

    @Override
    public void cancelRepair(Ship ship) {
        throw new ShipCannotException(ship.getName() + " cannot cancel repair because it is sunk.");
    }

    @Override
    public void repairExpress(Ship ship) {
        throw new ShipCannotException(ship.getName() + " cannot perform express repair because it is sunk.");
    }

    @Override
    public void repaired(Ship ship) {
        throw new ShipCannotException(ship.getName() + " cannot be repaired because it is sunk.");
    }

    @Override
    public void needMoreReparations(Ship ship) {
        throw new ShipCannotException(ship.getName() + " cannot request more reparations because it is sunk.");
    }

    @Override
    public void decommission(Ship ship) {
        throw new ShipCannotException(ship.getName() + " is sunk and cannot be decommissioned.");
    }
}

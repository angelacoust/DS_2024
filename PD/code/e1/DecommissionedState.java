package e1;public class DecommissionedState implements ShipState {
    @Override
    public void requestRepair(Ship ship) {
        throw new ShipCannotException(ship.getName() + " is decommissioned and cannot be repaired.");
    }

    @Override
    public void doExercise(Ship ship) {
        throw new ShipCannotException(ship.getName() + " is decommissioned and cannot perform exercises.");
    }

    @Override
    public void completedExercise(Ship ship) {
        throw new ShipCannotException(ship.getName() + " cannot complete exercises because it is decommissioned.");
    }

    @Override
    public void sinkShip(Ship ship) {
        throw new ShipCannotException(ship.getName() + " is already decommissioned and cannot sink.");
    }

    @Override
    public void acceptRepair(Ship ship) {
        throw new ShipCannotException(ship.getName() + " is decommissioned and cannot accept repair.");
    }

    @Override
    public void cancelRepair(Ship ship) {
        throw new ShipCannotException(ship.getName() + " is decommissioned and there is no repair to cancel.");
    }

    @Override
    public void repairExpress(Ship ship) {
        throw new ShipCannotException(ship.getName() + " is decommissioned and cannot undergo an express repair.");
    }

    @Override
    public void repaired(Ship ship) {
        throw new ShipCannotException(ship.getName() + " is decommissioned and cannot be repaired.");
    }

    @Override
    public void needMoreReparations(Ship ship) {
        throw new ShipCannotException(ship.getName() + " is decommissioned and does not need more reparations.");
    }

    @Override
    public void decommission(Ship ship) {
        throw new ShipCannotException(ship.getName() + " is already decommissioned.");
    }
}

package e1;

public class UnderRepairState implements ShipState {
    @Override
    public void requestRepair(Ship ship) {
        throw new ShipCannotException(ship.getName() + " is already under repair.");
    }

    @Override
    public void doExercise(Ship ship) {
        throw new ShipCannotException(ship.getName() + " cannot perform exercise while under repair.");
    }

    @Override
    public void completedExercise(Ship ship) {
        throw new ShipCannotException(ship.getName() + " cannot complete exercise while under repair.");
    }

    @Override
    public void sinkShip(Ship ship) {
        throw new ShipCannotException(ship.getName() + " cannot sink while under repair.");
    }

    @Override
    public void acceptRepair(Ship ship) {
        throw new ShipCannotException(ship.getName() + " cannot accept repair again while under repair.");
    }

    @Override
    public void cancelRepair(Ship ship) {
        throw new ShipCannotException(ship.getName() + " cannot cancel repair while under repair.");
    }

    @Override
    public void repairExpress(Ship ship) {
        throw new ShipCannotException(ship.getName() + " cannot perform express repair while under repair.");
    }

    @Override
    public void repaired(Ship ship) {
        ship.setCrash(false);
        System.out.println(ship.getName() + " has been successfully repaired.");
        ship.setState(new AtBaseState());
    }

    @Override
    public void needMoreReparations(Ship ship) {
        ship.setCrash(true);
        System.out.println(ship.getName() + " needs more reparations.");
        ship.setState(new PendingConfirmationState());
    }

    @Override
    public void decommission(Ship ship) {
        throw new ShipCannotException(ship.getName() + " cannot be decommissioned while under repair.");
    }
}


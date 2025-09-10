package e1;
public class AtBaseState implements ShipState {
    @Override
    public void requestRepair(Ship ship) {
        System.out.println("Repair requested for " + ship.getName() + " (" + ship.getShipType() + ").");
        ship.setState(new PendingConfirmationState());
    }

    @Override
    public void doExercise(Ship ship) {
        if(ship.isCrash()){
            throw new ShipCannotException("Cannot do exercise " + ship.getName() + " while it is crash.");
        } else {
            System.out.println(ship.getName() + " is now participating in an exercise.");
            ship.setState(new InExerciseState());
        }
    }

    @Override
    public void completedExercise(Ship ship) {
        throw new ShipCannotException( ship.getName() +"cannot complete de Exercise while at Base");
    }

    @Override
    public void sinkShip(Ship ship) {
        throw new ShipCannotException("Cannot sink " + ship.getName() + " while at base.");
    }

    @Override
    public void acceptRepair(Ship ship) {
        throw new ShipCannotException(ship.getName() + "cannot accept repair while at base.");
    }
    @Override
    public void cancelRepair(Ship ship) {
        throw new ShipCannotException(ship.getName() + "cannot cancel repair while at base.");
    }
    @Override
    public void repairExpress(Ship ship) {
        throw new ShipCannotException(ship.getName() + "cannot do a express repair while at base.");
    }
    @Override
    public void repaired(Ship ship) {
        throw new ShipCannotException(ship.getName() + "cannot be repair while at base.");
    }
    @Override
    public void needMoreReparations(Ship ship) {
        throw new ShipCannotException(ship.getName() + "cannot need more reparations while at base.");
    }
    @Override
    public void decommission(Ship ship) {
        throw new ShipCannotException(ship.getName() + "cannot decommission while at base.");
    }
}

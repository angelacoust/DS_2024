package e1;
public class InExerciseState implements ShipState {
    @Override
    public void requestRepair(Ship ship) {
        throw new ShipCannotException("Repair cannot be requested for " + ship.getName() + " while in exercise.");
    }

    @Override
    public void doExercise(Ship ship) {
        throw new ShipCannotException(ship.getName() + " is already in an exercise.");
    }

    @Override
    public void completedExercise(Ship ship) {
        int numMissions = ship.getNumMissions();
        double reward= ship.calculateRewardCost();
        double funds = ship.getFleet().getFunds() + reward;
        ship.getFleet().setFunds(funds);
        ship.setNumMissions(++numMissions);  // Increment the number of missions completed
        ship.setState(new AtBaseState());     // Change the state to AtBaseState
    }

    @Override
    public void sinkShip(Ship ship) {
        System.out.println(ship.getName() + " has sunk during the exercise!");
        ship.setState(new SunkState());  // Transition the state to SunkState
    }

    @Override
    public void acceptRepair(Ship ship) {
        throw new ShipCannotException("Repair cannot be accepted for " + ship.getName() + " while in exercise.");
    }

    @Override
    public void cancelRepair(Ship ship) {
        throw new ShipCannotException("Repair cannot be cancelled for " + ship.getName() + " while in exercise.");
    }

    @Override
    public void repairExpress(Ship ship) {
        throw new ShipCannotException("Express repair cannot be performed for " + ship.getName() + " while in exercise.");
    }

    @Override
    public void repaired(Ship ship) {
        throw new ShipCannotException("Cannot repair " + ship.getName() + " while in exercise.");
    }

    @Override
    public void needMoreReparations(Ship ship) {
        throw new ShipCannotException("Cannot request more reparations for " + ship.getName() + " while in exercise.");
    }

    @Override
    public void decommission(Ship ship) {
        throw new ShipCannotException("Cannot decommission " + ship.getName() + " while in exercise.");
    }
}

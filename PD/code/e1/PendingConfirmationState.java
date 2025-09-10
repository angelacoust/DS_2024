package e1;

public class PendingConfirmationState implements ShipState {
    @Override
    public void requestRepair(Ship ship) {
        throw new ShipCannotException(ship.getName() + " cannot request repair again because it is already pending confirmation.");
    }

    @Override
    public void doExercise(Ship ship) {
        throw new ShipCannotException("Cannot perform exercise. " + ship.getName() + " repair is pending confirmation.");
    }

    @Override
    public void completedExercise(Ship ship) {
        throw new ShipCannotException("Cannot complete exercise for " + ship.getName() + " while repair is pending confirmation.");
    }

    @Override
    public void sinkShip(Ship ship) {
        throw new ShipCannotException(ship.getName() + " cannot sink while pending confirmation.");
    }


    @Override
    public void acceptRepair(Ship ship) {
        double repairCost = ship.calculateRepairCost();  // Calculamos el coste de la reparación
        double debts=ship.getFleet().getDebts();
        if (ship.getFleet().getFunds() >= repairCost) {   // Verificamos si la flota tiene los fondos necesarios
            ship.repairShip(ship);                        // Aceptamos la reparación
            System.out.println("Repair for " + ship.getName() + " is accepted.");
            ship.setState(new UnderRepairState());
            //intentamos cobrar las deudas de las reparaciones xpress
            if(ship.getFleet().getFunds()>=debts){
                ship.getFleet().setFunds(ship.getFleet().getFunds()-debts);
                System.out.println("Xpress repairs payed");
                ship.getFleet().setDebts(0);
            }
        } else {
            throw new ShipCannotException(ship.getFleet()+" hadn´t suficient funds");
        }
    }


    @Override
    public void cancelRepair(Ship ship) {
        ship.setCrash(true);
        System.out.println("Repair canceled for " + ship.getName() + ".");
        ship.setState(new AtBaseState());
    }

    @Override
    public void repairExpress(Ship ship) {
        // Verificar si hay algún barco en estado UnderRepairState en la flota
        boolean isAnyShipUnderRepair = ship.getFleet().getShips().stream()
                .anyMatch(s -> s.getState() instanceof UnderRepairState);

        if (isAnyShipUnderRepair) {
            throw new ShipCannotException("Cannot perform express repair on " + ship.getName()
                    + " because another ship is already under repair.");
        }

        // Verificar si el barco es del tipo DE o DD
        if (ship.getShipType().equals(ShipType.DE) || ship.getShipType().equals(ShipType.DD)) {
            ship.setCrash(false);
            ship.getFleet().setDebts(ship.calculateRepairCost()+ship.getFleet().getDebts());
            System.out.println("Express repair completed for " + ship.getName() + ".");
            ship.setState(new AtBaseState());  // Cambiar el estado del barco a AtBaseState
        } else {
            throw new ShipCannotException(ship.getName() + " cannot perform an express repair.");
        }
    }


    @Override
    public void repaired(Ship ship) {
        throw new ShipCannotException("Cannot repair " + ship.getName() + " while repair is pending confirmation.");
    }

    @Override
    public void needMoreReparations(Ship ship) {
        throw new ShipCannotException("Cannot request more reparations for " + ship.getName() + " while repair is pending confirmation.");
    }

    @Override
    public void decommission(Ship ship) {
        System.out.println(ship.getName() + " has been decommissioned.");
        ship.setState(new DecommissionedState());
    }
}

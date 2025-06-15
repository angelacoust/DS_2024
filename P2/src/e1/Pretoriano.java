package e1;

public class Pretoriano extends Tropa {
    boolean armadura = false;

    public Pretoriano() {
        super("Praetorian",30, 65);
    }

    public void equiparArmadura() {
        armadura = true;
        nombre="Praetorian with armor";
    }

    @Override
    public double getPoderDefensa() {
        return armadura ? defensa * 1.1 : defensa;
    }
}

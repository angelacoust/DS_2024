package e1;

public class RayoTeutates extends Tropa {
    boolean armaduraPesada = false;

    public RayoTeutates() {
        super("Theutates Thunder",100, 25);
    }

    public void equiparArmaduraPesada() {
        armaduraPesada = true;
        nombre="Theutates Thunder with heavy armor";
    }

    @Override
    public double getPoderAtaque() {
        return armaduraPesada ? ataque * 0.75 : ataque;
    }

    @Override
    public double getPoderDefensa() {
        return armaduraPesada ? defensa * 1.25 : defensa;
    }
}

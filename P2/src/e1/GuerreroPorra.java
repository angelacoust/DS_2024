package e1;

public class GuerreroPorra extends Tropa {
    boolean armaMejorada = false;

    public GuerreroPorra() {
        super("Maceman",40, 20);
    }

    public void mejorarArma() {
        armaMejorada = true;
        nombre="Maceman with iron mace";
    }

    @Override
    public double getPoderAtaque() {
        return armaMejorada ? ataque * 1.25 : ataque;
    }
}


package e1;

import java.util.Objects;

public class AldeaTeutona extends Aldea {

    public AldeaTeutona(String nombre, int edad, int nivelMuralla) {
        super(nombre, edad, nivelMuralla);
    }

    @Override
    public void agregarTropa(Tropa tropa) {
        if(Objects.equals(tropa.nombre, "Axeman")||Objects.equals(tropa.nombre, "Maceman")||
                Objects.equals(tropa.nombre, "Maceman with iron mace")||Objects.equals(tropa.nombre, "Paladin")) {
            tropas.add(tropa);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public double calcularPoderAtaque() {
        double poderAtaque = 0;
        for (Tropa tropa : tropas) {
            poderAtaque += tropa.getPoderAtaque();
        }
        return poderAtaque * 0.95; // Disminución del 5% en el ataque
    }

    @Override
    public double calcularPoderDefensa() {
        double poderDefensa = 0;
        for (Tropa tropa : tropas) {
            poderDefensa += tropa.getPoderDefensa();
        }
        return poderDefensa - nivelMuralla; // Disminución de 1 punto por cada nivel de muralla
    }
}

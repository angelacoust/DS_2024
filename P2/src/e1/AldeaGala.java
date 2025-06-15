package e1;

import java.util.Objects;

public class AldeaGala extends Aldea {

    public AldeaGala(String nombre, int edad, int nivelMuralla) {
        super(nombre, edad, nivelMuralla);
    }

    @Override
    public void agregarTropa(Tropa tropa) {
        if(Objects.equals(tropa.nombre, "Druidrider")||Objects.equals(tropa.nombre, "Theutates Thunder")||
                Objects.equals(tropa.nombre, "Theutates Thunder with heavy armor")||Objects.equals(tropa.nombre, "Phalanx")) {
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
        return poderAtaque * 1.2; // Aumento del 20% en el ataque
    }

    @Override
    public double calcularPoderDefensa() {
        double poderDefensa = 0;
        for (Tropa tropa : tropas) {
            poderDefensa += tropa.getPoderDefensa();
        }
        return poderDefensa + 1.5 * nivelMuralla; // Aumento de 1.5 puntos por cada nivel de muralla
    }
}


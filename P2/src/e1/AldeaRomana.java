package e1;

import java.util.Objects;

public class AldeaRomana extends Aldea {

    public AldeaRomana(String nombre, int edad, int nivelMuralla) {
        super(nombre, edad, nivelMuralla);
    }

    @Override
    public void agregarTropa(Tropa tropa) {
        if(Objects.equals(tropa.nombre, "Legionnaire")||Objects.equals(tropa.nombre, "Praetorian")||
                Objects.equals(tropa.nombre, "Praetorian with armor")||Objects.equals(tropa.nombre, "Equites Imperatoris")) {
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
        return poderAtaque * 1.1; // Aumento del 10% en el ataque
    }

    @Override
    public double calcularPoderDefensa() {
        double poderDefensa = 0;
        for (Tropa tropa : tropas) {
            poderDefensa += tropa.getPoderDefensa();
        }
        return poderDefensa + 2 * nivelMuralla; // Aumento de 2 puntos por cada nivel de muralla
    }
}

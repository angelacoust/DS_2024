package e1;

import java.util.ArrayList;
import java.util.List;


public class Simulador {

    public List<String> batalla(Aldea atacante, Aldea defensor) {
        List<String> resultado = new ArrayList<>();

        // Inicio de la batalla
        resultado.add("### Starts the battle ! --> " + atacante.getNombre() + " Attacks " + defensor.getNombre() + " ! ###");

        // Tropas del atacante
        resultado.add(atacante.getNombre() + " have the following soldiery :");
        for (Tropa tropa : atacante.getTropas()) {
            resultado.add(atacante.getNombre() + " Soldiery - " + tropa.getNombre());
        }

        // Poder total de ataque del atacante
        int poderAtaque = (int) atacante.calcularPoderAtaque();
        resultado.add("Total " + atacante.getNombre() + " attack power : " + poderAtaque);

        // Tropas del defensor
        resultado.add(defensor.getNombre() + " have the following soldiery :");
        for (Tropa tropa : defensor.getTropas()) {
            resultado.add(defensor.getNombre() + " Soldiery - " + tropa.getNombre());
        }

        // Poder total de defensa del defensor
        int poderDefensa = (int) defensor.calcularPoderDefensa();
        resultado.add("Total " + defensor.getNombre() + " defense power : " + poderDefensa);

        // Determinar el ganador
        if (poderAtaque > poderDefensa) {
            resultado.add(atacante.getNombre() + " with an age of " + atacante.getEdad() + " years WINS !");
        } else {
            resultado.add(defensor.getNombre() + " with an age of " + defensor.getEdad() + " years WINS !");
        }

        return resultado;
    }
}


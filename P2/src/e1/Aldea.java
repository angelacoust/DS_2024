package e1;

import java.util.ArrayList;
import java.util.List;

public abstract class Aldea {
    String nombre;
    int edad;
    int nivelMuralla;
    List<Tropa> tropas = new ArrayList<>();

    public Aldea(String nombre, int edad, int nivelMuralla) {
        this.nombre = nombre;
        this.edad = edad;
        this.nivelMuralla = nivelMuralla;
    }

    public abstract void agregarTropa(Tropa tropa);

    public String getNombre() {
        return nombre;
    }

    public List<Tropa> getTropas() {
        return tropas;
    }

    public int getNivelMuralla() {
        return nivelMuralla;
    }

    public int getEdad() {
        return edad;
    }

    public abstract double calcularPoderAtaque();
    public abstract double calcularPoderDefensa();
}


package e1;

public abstract class Tropa {
    String nombre;
    double ataque;
    double defensa;

    public Tropa(String nombre, double ataque, double defensa) {
        this.nombre = nombre;
        this.ataque = ataque;
        this.defensa = defensa;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPoderAtaque() {
        return ataque;
    }

    public double getPoderDefensa() {
        return defensa;
    }
}

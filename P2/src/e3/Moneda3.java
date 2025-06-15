package e3;

public class Moneda3 {
    private String valor;
    private String pais;

    public Moneda3(String valor, String pais) {
        this.valor = valor;
        this.pais = pais;
    }


    public String getPais() {
        return pais;
    }

    @Override
    public String toString() {
        return valor + "-" + pais;
    }
}

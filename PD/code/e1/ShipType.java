package e1;

public enum ShipType {
    // Buques ultraligeros
    DE("Destructor de Escolta", 5000),  // Peso aproximado 5,000 toneladas
    DD("Destructor", 7000),            // Peso aproximado 7,000 toneladas

    // Buques ligeros
    CL("Crucero Ligero", 10000),       // Peso aproximado 10,000 toneladas
    AV("Portahidros", 12000),          // Peso aproximado 12,000 toneladas

    // Buques pesados
    CA("Crucero Pesado", 15000),       // Peso aproximado 15,000 toneladas
    CV("Portaaviones", 25000),        // Peso aproximado 25,000 toneladas

    // Buques ultrapesados
    BB("Acorazado", 35000);            // Peso aproximado 35,000 toneladas

    private String name;
    private double weight;

    // Constructor
    ShipType(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }
}



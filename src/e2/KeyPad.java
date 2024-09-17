package e2;

public class KeyPad {
    private char[][] matriz;
    private int filas;
    private int columnas;

    public KeyPad(char[][] matriz) {
        this.matriz = matriz;
        this.filas = matriz.length;
        this.columnas = matriz[0].length;
    }

    public static boolean isValidKeyPad(char[][] matriz) {
        // Verificar si la matriz es null
        if (matriz == null) return false;

        // Verificar si la matriz es vacía o tiene filas vacías
        if (matriz.length == 0 || matriz[0].length == 0) return false;

        // Verificar que todas las filas tengan la misma longitud
        int numColumnas = matriz[0].length;
        for (char[] fila : matriz) {
            if (fila == null || fila.length != numColumnas) return false;
            for (char c : fila) {
                // Verificar que cada carácter sea un dígito o una letra
                if (!Character.isDigit(c) && !Character.isLetter(c)) return false;
            }
        }
        return true;
    }

    public static boolean areValidMovements(String[] movimientos) {
        if (movimientos == null) return false;
        for (String movimiento : movimientos) {
            if (movimiento == null || movimiento.isEmpty()) return false;
            if (!movimiento.matches("[UDLR]*")) return false;
        }
        return true;
    }

    public static String obtainCode(char[][] teclado, String[] movimientos) {
        if (!isValidKeyPad(teclado)) {
            throw new IllegalArgumentException("Teclado no válido.");
        }
        if (!areValidMovements(movimientos)) {
            throw new IllegalArgumentException("Movimientos no válidos.");
        }

        KeyPad keyPad = new KeyPad(teclado);
        MovimientoSecuencia movimientoSecuencia = keyPad.new MovimientoSecuencia(keyPad);

        StringBuilder codigo = new StringBuilder();
        for (String movimiento : movimientos) {
            codigo.append(movimientoSecuencia.calculateKey(movimiento));
        }
        return codigo.toString();
    }

    public class MovimientoSecuencia {
        private KeyPad teclado;
        private int filaActual;
        private int columnaActual;

        public MovimientoSecuencia(KeyPad teclado) {
            this.teclado = teclado;
            this.filaActual = 0; // Inicialización a una posición válida para el primer movimiento
            this.columnaActual = 0;
        }

        public String calculateKey(String movimiento) {
            StringBuilder clave = new StringBuilder();
            if (!areValidMovements(new String[]{movimiento})) {
                throw new IllegalArgumentException("Secuencia de movimientos inválida.");
            }

            // Mantener el estado de filaActual y columnaActual entre movimientos
            for (char direccion : movimiento.toCharArray()) {
                switch (direccion) {
                    case 'U':
                        if (filaActual > 0) filaActual--;
                        break;
                    case 'D':
                        if (filaActual < teclado.getFilas() - 1) filaActual++;
                        break;
                    case 'L':
                        if (columnaActual > 0) columnaActual--;
                        break;
                    case 'R':
                        if (columnaActual < teclado.getColumnas() - 1) columnaActual++;
                        break;
                    default:
                        throw new IllegalArgumentException("Dirección de movimiento inválida.");
                }
            }
            clave.append(teclado.obtenerCaracter(filaActual, columnaActual));
            return clave.toString();
        }
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public char obtenerCaracter(int fila, int columna) {
        if (fila >= 0 && fila < filas && columna >= 0 && columna < columnas) {
            return matriz[fila][columna];
        }
        throw new IllegalArgumentException("Posición fuera del teclado.");
    }
}

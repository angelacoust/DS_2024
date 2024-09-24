package e2;
import java.util.Arrays;

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

        int numFilas = matriz.length;
        int numColumnas = matriz[0].length;

        // Verificar que todas las filas tengan la misma longitud
        for (char[] fila : matriz) if (fila == null || fila.length != numColumnas) return false;

        boolean filasOrdenadas = true;
        boolean columnasOrdenadas = true;

        // Verificar que los caracteres de cada fila estén en orden ascendente
        for (int i = 0; i < numFilas; i++) {
            for (int j = 1; j < numColumnas; j++) {
                // Verificar que cada carácter sea un dígito o una letra
                if (!Character.isDigit(matriz[i][j]) && !Character.isLetter(matriz[i][j])) return false;

                // Verificar si las filas están ordenadas
                if (matriz[i][j] <= matriz[i][j - 1]) {
                    filasOrdenadas = false;
                }
            }
        }

        // Verificar que los caracteres de cada columna estén en orden ascendente
        for (int j = 0; j < numColumnas; j++) {
            for (int i = 1; i < numFilas; i++) {
                // Verificar si las columnas están ordenadas
                if (matriz[i][j] <= matriz[i - 1][j]) {
                    columnasOrdenadas = false;
                }
            }
        }

        // Verificar si hay duplicados
        char[] allChars = new char[numFilas * numColumnas];
        int index = 0;
        for (int i = 0; i < numFilas; i++) {
            for (int j = 0; j < numColumnas; j++) {
                allChars[index++] = matriz[i][j];
            }
        }

        // Ordenar el array para verificar duplicados
        Arrays.sort(allChars);
        for (int i = 1; i < allChars.length; i++) {
            if (allChars[i] == allChars[i - 1]) return false; // Duplicado encontrado
        }

        // Retornar true si las filas o las columnas están ordenadas
        return filasOrdenadas || columnasOrdenadas;
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

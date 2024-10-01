package e2;

import java.util.Arrays;

public class KeyPad {
    private final char[][] matriz;
    private final int filas;
    private final int columnas;

    public KeyPad(char[][] matriz) {
        this.matriz = matriz;
        this.filas = matriz.length;
        this.columnas = matriz[0].length;
    }

    public static boolean isValidKeyPad(char[][] matriz) {
        boolean filasOrdenadas = true, columnasOrdenadas = true;
        // Verificar si la matriz es vacía, nula o tiene filas vacías
        if (matriz == null) return false;
        if (matriz.length == 0 || matriz[0].length == 0) return false;

        int numFilas = matriz.length, numColumnas = matriz[0].length;

        // Mirar que todas las filas tengan la misma longitud
        for (char[] fila : matriz) {
            if (fila == null || fila.length != numColumnas) return false;
        }

        // Filas ordenadas ascendente
        for (char[] chars : matriz) {
            for (int j = 1; j < numColumnas; j++) {
                // Verificar que los caracteres sean dígito o letra
                if (!Character.isDigit(chars[j]) && !Character.isLetter(chars[j])) return false;

                // Miramos si las filas están ordenadas
                if (chars[j] <= chars[j - 1]) {
                    filasOrdenadas = false;
                }
            }
        }

        // Columnas ordenadas ascendente
        for (int j = 0; j < numColumnas; j++) {
            for (int i = 1; i < numFilas; i++) {
                // Miramos si las columnas están ordenadas
                if (matriz[i][j] <= matriz[i - 1][j]) {
                    columnasOrdenadas = false;
                    break;
                }
            }
        }

        // Guardamos en allChars todos los caracteres
        char[] allChars = new char[numFilas * numColumnas];
        int index = 0;
        for (char[] chars : matriz) {
            for (int j = 0; j < numColumnas; j++) {
                allChars[index++] = chars[j];
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
            if (movimiento == null || movimiento.isEmpty() || !movimiento.matches("[UDLR]*")) {
                return false;
            }
        }
        return true;
    }

    public static String obtainCode(char[][] teclado, String[] movimientos) {
        KeyPad keyPad = new KeyPad(teclado);
        StringBuilder code = new StringBuilder();
        int filaActual = 0, columnaActual = 0;

        if (!isValidKeyPad(teclado)) {
            throw new IllegalArgumentException("Teclado no válido.");
        }
        if (!areValidMovements(movimientos)) {
            throw new IllegalArgumentException("Movimientos no válidos.");
        }



        for (String movimiento : movimientos) {
            code.append(calculateKey(movimiento, keyPad, filaActual, columnaActual));
            // Actualizar la posición después de cada movimiento
            for (char direction : movimiento.toCharArray()) {
                switch (direction) {
                    case 'U':
                        if (filaActual > 0) filaActual--;
                        break;
                    case 'D':
                        if (filaActual < keyPad.getFilas() - 1) filaActual++;
                        break;
                    case 'L':
                        if (columnaActual > 0) columnaActual--;
                        break;
                    case 'R':
                        if (columnaActual < keyPad.getColumnas() - 1) columnaActual++;
                        break;
                    default:
                        throw new IllegalArgumentException("Dirección de movimiento inválida.");
                }
            }
        }
        return code.toString();
    }

    private static String calculateKey(String movimiento, KeyPad teclado, int filaActual, int columnaActual) {
        StringBuilder clave = new StringBuilder();
        if (!areValidMovements(new String[]{movimiento})) {
            throw new IllegalArgumentException("Movimientos inválidos.");
        }

        // Mantener el estado de filaActual y columnaActual entre movimientos
        for (char direction : movimiento.toCharArray()) {
            switch (direction) {
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
                    throw new IllegalArgumentException("Dirección inválida.");
            }
        }
        clave.append(teclado.obtenerCharacter(filaActual, columnaActual));
        return clave.toString();
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public char obtenerCharacter(int fila, int columna) {
        if (fila >= 0 && fila < filas && columna >= 0 && columna < columnas) {
            return matriz[fila][columna];
        }
        throw new IllegalArgumentException("Posición fuera del teclado.");
    }
}

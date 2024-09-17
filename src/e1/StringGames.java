package e1;

public class StringGames {
    // Metodo 1: bestCharacters
    public static String bestCharacters(String str1, String str2) {
        int str1Lower = 0, str1Upper = 0, str1Digits = 0;
        int str2Lower = 0, str2Upper = 0, str2Digits = 0;

        if (str1.length() != str2.length()) {
            throw new IllegalArgumentException("Los strings deben tener la misma longitud.");
        }
        // Recorremos los Strings para contar caracteres
        for (int i = 0; i < str1.length(); i++) {
            char c1 = str1.charAt(i);
            char c2 = str2.charAt(i);

            // Contar minúsculas, mayúsculas y dígitos en el primer string
            if (Character.isLowerCase(c1)) str1Lower++;
            if (Character.isUpperCase(c1)) str1Upper++;
            if (Character.isDigit(c1)) str1Digits++;

            // Contar minúsculas, mayúsculas y dígitos en el segundo string
            if (Character.isLowerCase(c2)) str2Lower++;
            if (Character.isUpperCase(c2)) str2Upper++;
            if (Character.isDigit(c2)) str2Digits++;
        }

        // Contar las categorías ganadas por cada string
        int str1Wins = 0, str2Wins = 0;

        if (str1Lower > str2Lower) str1Wins++; else if (str1Lower < str2Lower) str2Wins++;
        if (str1Upper > str2Upper) str1Wins++; else if (str1Upper < str2Upper) str2Wins++;
        if (str1Digits > str2Digits) str1Wins++; else if (str1Digits < str2Digits) str2Wins++;

        // Devolver el string que gana más categorías o el primero en caso de empate
        if (str1Wins > str2Wins) return str1;
        if (str2Wins > str1Wins) return str2;
        return str1; // En caso de empate
    }

    // Metodo 2: crossingWords
    public static int crossingWords(String str1, String str2) {
        int count = 0;

        // Recorremos ambos Strings buscando coincidencias de caracteres
        for (int i = 0; i < str1.length(); i++) {
            char c1 = str1.charAt(i);
            for (int j = 0; j < str2.length(); j++) {
                char c2 = str2.charAt(j);
                if (c1 == c2) {
                    count++;
                }
            }
        }
        return count;
    }



    // Metodo 3: wackyAlphabet
    public static String wackyAlphabet(String str1, String str2) {
        // Verificar que str2 contiene exactamente todas las letras del alfabeto
        boolean[] alphabet = new boolean[26]; // Para verificar cada letra del alfabeto
        int uniqueLetters = 0; // Para contar cuántas letras únicas hemos encontrado

        // Recorrer str2 para validar su contenido
        for (int i = 0; i < str2.length(); i++) {
            char current = str2.charAt(i);

            // Asegurarse de que es una letra en minúscula
            if (current < 'a' || current > 'z') {
                throw new IllegalArgumentException("El segundo string debe contener solo letras del alfabeto en minúscula.");
            }

            // Verificar si la letra ya ha sido encontrada
            int index = current - 'a';
            if (!alphabet[index]) {
                alphabet[index] = true; // Marcar que esta letra ha sido encontrada
                uniqueLetters++; // Aumentar el contador de letras únicas
            }
        }

        // Si no encontramos exactamente 26 letras, lanzamos una excepción
        if (uniqueLetters != 26) {
            throw new IllegalArgumentException("El segundo string debe contener exactamente todas las letras del alfabeto.");
        }

        StringBuilder result = new StringBuilder();

        // Reordenar str1 según el orden de str2
        for (int i = 0; i < str2.length(); i++) {
            char current = str2.charAt(i);
            for (int j = 0; j < str1.length(); j++) {
                if (str1.charAt(j) == current) {
                    result.append(current);
                }
            }
        }

        return result.toString();
    }


}

package e1;

public class StringGames {

    public static String bestCharacters(String str1, String str2) {
        int str1Minus = 0, str1High = 0, str1Digito = 0, str2Minus = 0, str2High = 0, str2Digito = 0, str1Ganador = 0, str2Ganador = 0;

        if (str1.length() != str2.length()) {
            throw new IllegalArgumentException("Los strings deben tener la misma longitud.");
        }
        // Contamos caracteres
        for (int i = 0; i < str1.length(); i++) {
            char c1 = str1.charAt(i);
            char c2 = str2.charAt(i);

            // Contar minúsculas, mayúsculas y dígitos en cada string
            // 1
            if (Character.isLowerCase(c1)) str1Minus++;
            if (Character.isUpperCase(c1)) str1High++;
            if (Character.isDigit(c1)) str1Digito++;

            // 2
            if (Character.isLowerCase(c2)) str2Minus++;
            if (Character.isUpperCase(c2)) str2High++;
            if (Character.isDigit(c2)) str2Digito++;
        }


        if (str1Minus > str2Minus) str1Ganador++; else if (str1Minus < str2Minus) str2Ganador++;
        if (str1High > str2High) str1Ganador++; else if (str1High < str2High) str2Ganador++;
        if (str1Digito > str2Digito) str1Ganador++; else if (str1Digito < str2Digito) str2Ganador++;

        // Devolver el string que gana
        if (str1Ganador > str2Ganador) return str1;
        if (str2Ganador > str1Ganador) return str2;
        return str1; // En caso de empate
    }


    public static int crossingWords(String str1, String str2) {
        int contador = 0;

        // Recorremos los Strings buscando coincidencias
        for (int i = 0; i < str1.length(); i++) {
            char c1 = str1.charAt(i);
            for (int j = 0; j < str2.length(); j++) {
                char c2 = str2.charAt(j);
                if (c1 == c2) {
                    contador++;
                }
            }
        }
        return contador;
    }




    public static String wackyAlphabet(String str1, String str2) {
        // Verificar que str2 contiene exactamente todas las letras del alfabeto
        boolean[] alfabeto = new boolean[26]; // Para verificar cada letra del alfabeto
        int uniqueLetters = 0; // Para contar cuántas letras únicas hemos encontrado

        // Recorrer str2
        for (int i = 0; i < str2.length(); i++) {
            char iterator = str2.charAt(i);

            // Asegurarse de que es una letra en minúscula
            if (iterator < 'a' || iterator > 'z') {
                throw new IllegalArgumentException("El segundo string debe contener solo letras del alfabeto en minúscula.");
            }

            // Verificar si la letra ya ha sido encontrada
            int indice = iterator - 'a';
            if (!alfabeto[indice]) {
                alfabeto[indice] = true; //  letra encontrada
                uniqueLetters++; // Aumentar el contador
            }
        }

        // Si no encontramos las 26 letras
        if (uniqueLetters != 26) {
            throw new IllegalArgumentException("El segundo string debe contener exactamente todas las letras del alfabeto.");
        }

        StringBuilder result = new StringBuilder();

        // Reordenar str1 según el orden de str2
        for (int i = 0; i < str2.length(); i++) {
            char iterator = str2.charAt(i);
            for (int j = 0; j < str1.length(); j++) {
                if (str1.charAt(j) == iterator) {
                    result.append(iterator);
                }
            }
        }

        return result.toString();
    }

}

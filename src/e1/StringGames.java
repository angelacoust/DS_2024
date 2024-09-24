package e1;

public class StringGames {

    public static String bestCharacters(String str1, String str2) {
        int str1Minuscula = 0, str1Mayuscula = 0, str1Digito = 0;
        int str2minuscula = 0, str2Mayuscula = 0, str2Digito = 0;

        if (str1.length() != str2.length()) {
            throw new IllegalArgumentException("Los strings deben tener la misma longitud.");
        }
        // Contamos caracteres
        for (int i = 0; i < str1.length(); i++) {
            char c1 = str1.charAt(i);
            char c2 = str2.charAt(i);

            // Contar minúsculas, mayúsculas y dígitos en cada string
            // string 1
            if (Character.isLowerCase(c1)) str1Minuscula++;
            if (Character.isUpperCase(c1)) str1Mayuscula++;
            if (Character.isDigit(c1)) str1Digito++;

            // string 2
            if (Character.isLowerCase(c2)) str2minuscula++;
            if (Character.isUpperCase(c2)) str2Mayuscula++;
            if (Character.isDigit(c2)) str2Digito++;
        }

        // Contamos las categorias
        int str1Wins = 0, str2Wins = 0;

        if (str1Minuscula > str2minuscula) str1Wins++; else if (str1Minuscula < str2minuscula) str2Wins++;
        if (str1Mayuscula > str2Mayuscula) str1Wins++; else if (str1Mayuscula < str2Mayuscula) str2Wins++;
        if (str1Digito > str2Digito) str1Wins++; else if (str1Digito < str2Digito) str2Wins++;

        // Devolver el string que gana
        if (str1Wins > str2Wins) return str1;
        if (str2Wins > str1Wins) return str2;
        return str1; // En caso de empate
    }


    public static int crossingWords(String str1, String str2) {
        int count = 0;

        // Recorremos los Strings buscando coincidencias
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




    public static String wackyAlphabet(String str1, String str2) {
        // Verificar que str2 contiene exactamente todas las letras del alfabeto
        boolean[] alphabet = new boolean[26]; // Para verificar cada letra del alfabeto
        int uniqueLetters = 0; // Para contar cuántas letras únicas hemos encontrado

        // Recorrer str2
        for (int i = 0; i < str2.length(); i++) {
            char current = str2.charAt(i);

            // Asegurarse de que es una letra en minúscula
            if (current < 'a' || current > 'z') {
                throw new IllegalArgumentException("El segundo string debe contener solo letras del alfabeto en minúscula.");
            }

            // Verificar si la letra ya ha sido encontrada
            int index = current - 'a';
            if (!alphabet[index]) {
                alphabet[index] = true; //  letra encontrada
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

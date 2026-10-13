/*
 ====================================================================================
                                    PROMPT 
 ====================================================================================
 "Escribe un método estático en Java para determinar si un String es un palíndromo 
 de forma recursiva (es decir, si se lee igual de izquierda a derecha que de derecha 
 a izquierda).

 Requisitos y fundamentos del algoritmo:
 1. COMPARACIÓN DE EXTREMOS (PRIMERO Y ÚLTIMO CARÁCTER):
    Por definición, en una palabra palíndroma el primer carácter debe ser idéntico al 
    último (ej. en 'reconocer', 'r' == 'r'). En cada nivel de recursión, la función 
    compara `str.charAt(0)` con `str.charAt(str.length() - 1)`.

 2. SI LOS CARACTERES EXTREMOS SON DISTINTOS:
    Si en algún paso de la evaluación el primer y el último carácter son diferentes 
    (ej. en 'hola', 'h' != 'a'), la palabra NO es un palíndromo. La función debe romper 
    inmediatamente la recursión y devolver `false`.

 3. REDUCCIÓN DEL PROBLEMA (ELIMINACIÓN DE EXTREMOS):
    Si los extremos coinciden, el problema se reduce comprobando si la subcadena interna 
    también es palíndroma. Para ello, se pasa a la siguiente llamada recursiva la cadena 
    sin su primer ni su último carácter usando:
        str.substring(1, str.length() - 1)

 4. CASOS BASE:
    - Longitud 0 o 1 (`str.length() <= 1`): Si hemos eliminado pares de extremos iguales 
      hasta quedarnos con 1 solo carácter (ej. 'o' en 'reconocer') o con una cadena vacía 
      (en palabras de longitud par como 'anna'), significa que todas las parejas anteriores 
      coincidieron. La función debe devolver `true`.

 5. NORMALIZACIÓN Y PLAN DE PRUEBAS A VALIDAR:
    - La función debe normalizar el texto (convertir a minúsculas y omitir espacios o 
      acentos si se busca evaluar frases).
    - Plan de casos de prueba:
        - Palíndromos simples de longitud impar: `esPalindromo("reconocer")` -> Esperado: `true`
        - Palíndromos de longitud par: `esPalindromo("anna")` -> Esperado: `true`
        - No palíndromos: `esPalindromo("java")` -> Esperado: `false`
        - Palíndromos con mayúsculas/espacios: `esPalindromo("Anita lava la tina")` -> Esperado: `true`
        - Caso base de 1 carácter: `esPalindromo("A")` -> Esperado: `true`
        - Caso base cadena vacía: `esPalindromo("")` -> Esperado: `true`
 ====================================================================================
 */

public class Ejercicio9 {

    /**
     * Determina de forma recursiva si un String es palíndromo.
     *
     * @param str La cadena a evaluar.
     * @return true si es palíndromo, false en caso contrario.
     */
    public static boolean esPalindromo(String str) {
        if (str == null) {
            return false;
        }

        // Preprocesamiento/Normalización: elimina espacios y pasa a minúsculas
        String textoLimpio = str.replaceAll("\\s+", "").toLowerCase();

        return evaluarRecursivo(textoLimpio);
    }

    private static boolean evaluarRecursivo(String str) {
        // CASO BASE: Si la cadena es vacía (longitud 0) o de 1 solo carácter, es palíndromo
        if (str.length() <= 1) {
            return true;
        }

        // SI LOS EXTREMOS SON DISTINTOS: No es palíndromo (salida anticipada)
        if (str.charAt(0) != str.charAt(str.length() - 1)) {
            return false;
        }

        // PASO RECURSIVO: Eliminar el primer y último carácter y evaluar la subcadena restante
        return evaluarRecursivo(str.substring(1, str.length() - 1));
    }

    public static void main(String[] args) {
        // Batería de pruebas
        Object[][] pruebas = {
            {"reconocer", true},
            {"anna", true},
            {"java", false},
            {"Anita lava la tina", true},
            {"A", true},
            {"", true}
        };

        System.out.println("--- PRUEBAS DE PALÍNDROMO RECURSIVO ---");
        for (Object[] par : pruebas) {
            String entrada = (String) par[0];
            boolean esperado = (boolean) par[1];

            boolean resultado = esPalindromo(entrada);
            boolean pasaPrueba = (resultado == esperado);

            System.out.printf("Entrada: %-25s | Resultado: %-5b | Esperado: %-5b -> [%s]%n",
                    "\"" + entrada + "\"", resultado, esperado, pasaPrueba ? "OK" : "ERROR");
        }
    }
}

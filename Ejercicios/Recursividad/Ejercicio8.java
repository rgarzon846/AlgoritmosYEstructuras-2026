/*
 ====================================================================================
                                    PROMPT 
 ====================================================================================
 "Escribe un método estático en Java para invertir una cadena de texto (String) de 
 forma recursiva.

 Requisitos y fundamentos del algoritmo:
 1. CASO BASE (CADENA VACÍA O DE UN SOLO CARÁCTER):
    Si la cadena dada es nula (`null`), tiene una longitud de 0 (`""`) o de 1 único 
    carácter (ej. `"a"`), el inverso de la cadena es exactamente ella misma. En estos 
    casos, la función debe retornar la cadena directamente sin realizar más llamadas 
    recursivas. Esto detiene la recursión y evita errores de índice fuera de rango.

 2. SEPARACIÓN DEL PRIMER CARÁCTER Y REDUCCIÓN DE LA CADENA:
    Para reducir el problema en cada paso recursivo:
    - Extrae el primer carácter con `str.charAt(0)`.
    - Obtén el subcadena restante sin ese primer carácter mediante `str.substring(1)`.
    Esta reducción con `str.substring(1)` acorta la longitud del String en 1 unidad en 
    cada llamada hasta alcanzar el caso base.

 3. RECONSTRUCCIÓN DEL RESULTADO AL VOLVER DE LA RECURSIÓN:
    Para invertir el orden, el subcadena restante invertido recursivamente debe ir 
    PRIMERO y el primer carácter extraído debe concatenarse al FINAL:
        invertirCadena(str) = invertirCadena(str.substring(1)) + str.charAt(0)

 4. PLAN DE PRUEBAS DE VALIDACIÓN:
    Se deben verificar las siguientes palabras y casos especiales:
    - Palabras estándar: `invertirCadena("hola")` -> Esperado: `"aloh"`
    - Frases con espacios y puntuación: `invertirCadena("java 2026!")` -> Esperado: `"!6202 avaj"`
    - Palíndromos (el resultado debe ser igual al original): `invertirCadena("reconocer")` -> Esperado: `"reconocer"`
    - Caso base con 1 carácter: `invertirCadena("A")` -> Esperado: `"A"`
    - Caso base cadena vacía: `invertirCadena("")` -> Esperado: `""`
 ====================================================================================
 */

public class Ejercicio8 {

    /**
     * Invierte una cadena de caracteres de forma recursiva.
     *
     * @param str La cadena original a invertir.
     * @return La cadena invertida.
     */
    public static String invertirCadena(String str) {
        // Validación de nulidad
        if (str == null) {
            return null;
        }

        // CASO BASE: Si la cadena es vacía o de 1 solo carácter
        if (str.length() <= 1) {
            return str;
        }

        // PASO RECURSIVO: Invertir el resto + Concatenar el primer carácter al final
        return invertirCadena(str.substring(1)) + str.charAt(0);
    }

    public static void main(String[] args) {
        // Batería de pruebas
        String[][] pruebas = {
            {"hola", "aloh"},
            {"java 2026!", "!6202 avaj"},
            {"reconocer", "reconocer"},
            {"A", "A"},
            {"", ""}
        };

        System.out.println("--- PRUEBAS DE INVERSIÓN RECURSIVA DE STRINGS ---");
        for (String[] par : pruebas) {
            String entrada = par[0];
            String esperado = par[1];

            String resultado = invertirCadena(entrada);
            boolean pasaPrueba = (resultado != null && resultado.equals(esperado));

            System.out.printf("Entrada: %-15s | Resultado: %-15s -> [%s]%n",
                    "\"" + entrada + "\"", "\"" + resultado + "\"", pasaPrueba ? "OK" : "ERROR");
        }
    }
}

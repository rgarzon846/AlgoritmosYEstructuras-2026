/**
 * Algoritmo que cuenta cuántas veces aparece un determinado valor dentro
 * de un vector de enteros.
 *
 * ===========================================================================
 * JUSTIFICACIÓN: ¿POR QUÉ DEBE RECORRERSE COMPLETAMENTE EL VECTOR?
 * ===========================================================================
 *
 * 1. No existe información de orden ni índice auxiliar que permita saber de
 *    antemano cuántas copias hay ni en qué posición terminan. Para conocer
 *    el total, no alcanza con localizar UNA aparición: hay que verificar
 *    cada posición.
 *
 * 2. Contar exige examinar cada elemento, porque cualquier posición no
 *    visitada podría contener (o no) otra copia del valor. Si el recorrido
 *    se cortara antes de tiempo, el conteo sería solo un límite inferior,
 *    no el resultado correcto.
 *
 * 3. A diferencia de la búsqueda binaria (donde el orden permite DESCARTAR
 *    mitades del arreglo), aquí el orden —aunque existiera— no reduce el
 *    recorrido: el objetivo es un conteo TOTAL, no localizar una ocurrencia.
 *    Todas las posiciones aportan al resultado.
 *
 * 4. Solo al llegar al final del vector se puede garantizar que no quedan
 *    más apariciones por contar.
 *
 * ===========================================================================
 * ANÁLISIS DE COMPLEJIDAD
 * ===========================================================================
 *
 * Tiempo: O(n)
 *   - Se visitan los n elementos exactamente una vez (recorrido secuencial).
 *   - Mejor caso: O(n) — ej.: el valor buscado no aparece, o aparece solo
 *     al final; igualmente hay que recorrer todo.
 *   - Peor caso: O(n) — ej.: todos los elementos son iguales al valor.
 *   - Caso promedio: O(n).
 *   Los tres casos coinciden en O(n): el conteo no puede "cortarse" antes
 *   de visitar todas las posiciones.
 *
 * Espacio: O(1)
 *   - Solo se utiliza un contador (y los índices del bucle); no se requiere
 *     estructura auxiliar.
 *
 * Contraste con la búsqueda binaria:
 *   - Búsqueda binaria (localizar UNA ocurrencia en datos ordenados): O(log n),
 *     porque cada comparación descarta la mitad del espacio de búsqueda.
 *   - Conteo de apariciones: O(n) incluso con datos ordenados, porque la
 *     binaria solo sirve para encontrar UNA posición; contar exige explorar
 *     todas las posibles copias (que podrían estar repartidas en todo el
 *     vector). No hay forma de descartar mitades para un conteo total.
 */
public class Ejercicio5 {

    /**
     * Cuenta cuántas veces aparece {@code valor} en {@code vector}.
     *
     * @param vector arreglo de enteros a recorrer (puede ser null o vacío)
     * @param valor  valor a contar
     * @return cantidad de apariciones (0 si no aparece)
     */
    public static int contarApariciones(int[] vector, int valor) {
        // Caso borde: vector nulo o vacío → 0 apariciones
        if (vector == null || vector.length == 0) {
            System.out.println("  Vector nulo o vacío → 0 apariciones.");
            return 0;
        }

        int contador = 0;

        // Recorrido COMPLETO del vector: no se puede cortar antes de tiempo,
        // porque cualquier posición no visitada podría contener otra copia.
        for (int i = 0; i < vector.length; i++) {
            if (vector[i] == valor) {
                contador++;
                System.out.println("  [posición " + i + "] " + vector[i]
                        + " == " + valor + " → contador = " + contador);
            }
        }

        System.out.println("  Fin del recorrido (" + vector.length
                + " elementos visitados) → total = " + contador);
        return contador;
    }

    // ======================================================================
    // PRUEBAS
    // ======================================================================

    private static void probar(String descripcion, int[] vector, int valor) {
        System.out.println("=== " + descripcion + " ===");
        System.out.println("Vector: " + java.util.Arrays.toString(vector)
                + "  |  valor a contar: " + valor);
        int resultado = contarApariciones(vector, valor);
        System.out.println("Resultado: " + resultado);
        System.out.println();
    }

    public static void main(String[] args) {
        // Caso 1: valor presente en posiciones variadas
        probar("CASO 1: valor presente", new int[]{3, 1, 4, 1, 5, 9, 2, 6, 5}, 5);

        // Caso 2: valor ausente
        probar("CASO 2: valor ausente", new int[]{1, 2, 3, 4, 5}, 7);

        // Caso 3: todos los elementos iguales al valor
        probar("CASO 3: todos iguales", new int[]{8, 8, 8, 8}, 8);

        // Caso 4: valor al principio y al final (extremos)
        probar("CASO 4: extremos", new int[]{2, 1, 1, 1, 2}, 2);

        // Caso 5: vector vacío
        probar("CASO 5: vector vacío", new int[]{}, 4);

        // Resumen
        System.out.println("=== RESUMEN ===");
        System.out.println("Algoritmo: recorrido secuencial completo del vector.");
        System.out.println("Tiempo: O(n) en mejor, peor y caso promedio.");
        System.out.println("Espacio: O(1) — solo un contador.");
        System.out.println("Contraste: búsqueda binaria O(log n) localiza UNA");
        System.out.println("ocurrencia; el conteo total siempre es O(n).");
    }
}

/* ...CORRECCIONES HECHAS POR OPENCODE
====================================================================================
CORRECCIONES HECHAS POR OPENCODE
====================================================================================
[REDUNDANTE] El comentario dentro del bucle ("no se puede cortar antes de tiempo...") repite la justificación que la cabecera del archivo ya desarrolló con más detalle.
[PROMPT] El enunciado solo pedía contar las apariciones de un valor; el código además imprime traza en cada coincidencia, maneja null/vacío y ejecuta 5 pruebas con resumen comparativo contra búsqueda binaria.
====================================================================================
*/

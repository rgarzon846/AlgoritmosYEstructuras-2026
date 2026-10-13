
/**
 * Dos soluciones para detectar elementos duplicados dentro de un vector.
 *
 * SOLUCIÓN 1 — Dos ciclos anidados (fuerza bruta).
 * SOLUCIÓN 2 — HashSet (conjunto de elementos ya vistos).
 *
 * ===========================================================================
 * COMPARACIÓN DE COMPLEJIDADES
 * ===========================================================================
 *
 * 1) COMPLEJIDAD TEMPORAL (T)
 *
 *    Solución 1 — Ciclos anidados:
 *      - Mejor caso: O(1) → se compara el primer par y ya hay duplicado
 *        (o el algoritmo detecta el duplicado lo antes posible).
 *      - Peor caso: O(n²) → sin duplicados, se comparan todos los pares
 *        posibles: (n-1) + (n-2) + ... + 1 = n(n-1)/2 comparaciones.
 *      - Caso promedio: O(n²) → en la práctica siempre es cuadrático,
 *        porque debe recorrer casi todos los pares.
 *
 *    Solución 2 — HashSet:
 *      - Mejor caso: O(1) → el duplicado está al principio.
 *      - Peor caso: O(n) → se insertan los n elementos.
 *      - Caso promedio: O(n) → cada inserción/búsqueda en el HashSet es
 *        O(1) esperado (hashing), por lo tanto n operaciones = O(n).
 *        (Nota: en el PEOR caso teórico del hash —colisiones totales—
 *        sería O(n²), pero es un caso muy improbable.)
 *
 * 2) COMPLEJIDAD ESPACIAL (S)
 *
 *    Solución 1 — Ciclos anidados: O(1) → solo usa variables de índice
 *    (i, j) y un par de banderas; no necesita memoria adicional.
 *
 *    Solución 2 — HashSet: O(n) → en el peor caso guarda los n elementos
 *    distintos del vector dentro del conjunto.
 *
 * 3) CONCLUSIÓN
 *
 *    - Si el vector es pequeño o la memoria es muy limitada, la solución
 *      con ciclos anidados es válida (y no requiere estructuras extra).
 *    - Si el vector es grande, la solución con HashSet es claramente
 *      preferible: pasa de O(n²) a O(n) en tiempo a cambio de O(n) de
 *      memoria adicional. Es el trade off tiempo ↔ espacio.
 *
 * ===========================================================================
 */
import java.util.HashSet;
import java.util.Set;

public class Ejercicio4 {

    // ======================================================================
    // SOLUCIÓN 1 — Dos ciclos anidados (fuerza bruta)
    // ======================================================================
    /**
     * Detecta duplicados comparando cada par de elementos del vector. Devuelve
     * true si encuentra un duplicado y muestra el par (valores e índices).
     *
     * Complejidad: O(n²) tiempo / O(1) espacio.
     */
    public static boolean hayDuplicadoConCiclos(int[] v) {
        if (v == null || v.length < 2) {
            System.out.println("  Vector con menos de 2 elementos → sin duplicados posibles.");
            return false;
        }

        int comparaciones = 0;
        for (int i = 0; i < v.length - 1; i++) {
            // j arranca en i+1 para no comparar un elemento consigo mismo
            // ni repetir comparaciones ya hechas (i,j) y (j,i)
            for (int j = i + 1; j < v.length; j++) {
                comparaciones++;
                if (v[i] == v[j]) {
                    System.out.println("  ¡Duplicado! valor " + v[i]
                            + " en los índices " + i + " y " + j
                            + "  (" + comparaciones + " comparaciones)");
                    return true;
                }
            }
        }
        System.out.println("  Sin duplicados  (" + comparaciones + " comparaciones)");
        return false;
    }

    // ======================================================================
    // SOLUCIÓN 2 — HashSet
    // ======================================================================
    /**
     * Detecta duplicados insertando cada elemento en un HashSet. Si el elemento
     * ya pertenece al conjunto, es un duplicado. Devuelve true si encuentra un
     * duplicado y muestra el par (valores e índices).
     *
     * Complejidad: O(n) tiempo esperado / O(n) espacio.
     */
    public static boolean hayDuplicadoConHashSet(int[] v) {
        if (v == null || v.length < 2) {
            System.out.println("  Vector con menos de 2 elementos → sin duplicados posibles.");
            return false;
        }

        Set<Integer> vistos = new HashSet<>();
        // Mapa auxiliar valor -> primer índice donde apareció (solo para informar)
        java.util.Map<Integer, Integer> primerIndice = new java.util.HashMap<>();

        int operaciones = 0;
        for (int i = 0; i < v.length; i++) {
            operaciones++;
            if (!vistos.add(v[i])) {
                // add() devuelve false si el elemento ya estaba → duplicado
                int indiceAnterior = primerIndice.get(v[i]);
                System.out.println("  ¡Duplicado! valor " + v[i]
                        + " en los índices " + indiceAnterior + " y " + i
                        + "  (" + operaciones + " operaciones sobre el conjunto)");
                return true;
            }
            primerIndice.put(v[i], i);
        }
        System.out.println("  Sin duplicados  (" + operaciones + " operaciones sobre el conjunto)");
        return false;
    }

    // ======================================================================
    // PRUEBAS
    // ======================================================================
    /**
     * Ejecuta ambas soluciones sobre el mismo vector y verifica que coincidan.
     */
    private static void probar(String descripcion, int[] v) {
        System.out.println("=== " + descripcion + " ===");
        System.out.println("Vector: " + java.util.Arrays.toString(v));

        System.out.println("[Solución 1 — Ciclos anidados]");
        boolean r1 = hayDuplicadoConCiclos(v);

        System.out.println("[Solución 2 — HashSet]");
        boolean r2 = hayDuplicadoConHashSet(v);

        System.out.println("Resultado: ciclo=" + r1 + ", hashset=" + r2
                + " → " + (r1 == r2 ? "COINCIDEN ✓" : "DISCREPAN ✗"));
        System.out.println();
    }

    public static void main(String[] args) {
        // Caso 1: duplicado en los extremos (primera y última posición)
        probar("CASO 1: duplicado en extremos", new int[]{7, 2, 3, 5, 7});

        // Caso 2: elementos consecutivos duplicados
        probar("CASO 2: duplicado consecutivo", new int[]{1, 3, 3, 9, 4});

        // Caso 3: sin duplicados
        probar("CASO 3: sin duplicados", new int[]{1, 2, 3, 4, 5});

        // Caso 4: un solo elemento
        probar("CASO 4: un solo elemento", new int[]{42});

        // Caso 5: vector vacío
        probar("CASO 5: vector vacío", new int[]{});

        // Resumen
        System.out.println("=== RESUMEN ===");
        System.out.println("Todas las pruebas verifican que ambas soluciones coincidan.");
        System.out.println("Ciclos anidados : O(n²) tiempo / O(1) espacio");
        System.out.println("HashSet         : O(n) tiempo esperado / O(n) espacio");
    }
}

/* ...CORRECCIONES HECHAS POR OPENCODE
====================================================================================
CORRECCIONES HECHAS POR OPENCODE
====================================================================================
[REDUNDANTE] El `HashMap primerIndice` guarda la misma información que el `HashSet vistos` (qué valores ya aparecieron), duplicando el consumo de memoria O(n) solo para enriquecer el mensaje impreso.
[PROMPT] El enunciado solo pedía detectar si hay duplicados (resultado booleano); el código además informa los índices exactos del par duplicado, cuenta comparaciones/operaciones y valida que ambas soluciones coincidan entre sí.
====================================================================================
*/

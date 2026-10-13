/**
 * Algoritmo de ordenamiento BUBBLE SORT sobre un vector de enteros.
 *
 * ===========================================================================
 * ESTRATEGIA ELEGIDA
 * ===========================================================================
 *
 * Pasadas repetidas comparando pares adyacentes (i, i+1) e
 * intercambiándolos si están en orden incorrecto (ascendente):
 *
 *   para pasada de 0 hasta n-2:
 *       huboIntercambios = false
 *       para i de 0 hasta n-2-pasada:        (la región final ya está
 *           comparaciones++                   ordenada y se acorta)
 *           si v[i] > v[i+1]:
 *               swap(v[i], v[i+1]); intercambios++; huboIntercambios = true
 *       si NO hubo intercambios → CORTAR (ya está ordenado)
 *
 * Puntos clave de la estrategia:
 *   - Cada pasada "burbujea" el mayor elemento restante hacia el final:
 *     después de la pasada k, los k últimos lugares contienen sus valores
 *     definitivos → el bucle interno puede acortarse en cada iteración.
 *   - OPTIMIZACIÓN DE BANDERA (flag): si una pasada no realiza ningún
 *     intercambio, el vector ya está ordenado y el algoritmo termina
 *     anticipadamente. Esto hace que el MEJOR caso sea O(n).
 *
 * ===========================================================================
 * POR QUÉ ES ADECUADO ÚNICAMENTE PARA FINES DIDÁCTICOS O PEQUEÑOS
 * CONJUNTOS DE DATOS
 * ===========================================================================
 *
 * 1. COMPLEJIDAD O(n²) en peor y caso promedio — la diferencia contra
 *    O(n log n) crece rapidísimo:
 *      - n = 10.000       → ~100 millones de operaciones (n²)
 *      - n = 1.000.000    → ~10¹² operaciones (un billón: inviable)
 *    Mientras, quicksort/mergesort promedian O(n log n):
 *      - n = 1.000.000    → ~20 millones (n·log₂n ≈ 20·10⁶).
 *    La brecha entre n² y n·log n se vuelve dramatica a partir de n ≈ 100.
 *
 * 2. DIDÁCTICO: es fácil de entender, implementar y visualizar — la
 *    comparación de pares adyacentes es intuitiva y el "burbujeo" del
 *    mayor elemento se ve en cada pasada. Es el típico primer
 *    algoritmo de ordenamiento en un curso.
 *
 * 3. NO SE USA EN LA PRÁCTICA: las librerías estándar (p. ej.
 *    java.util.Arrays.sort) usan algoritmos híbridos O(n log n)
 *    (Dual-Pivot Quicksort para primitivos, TimSort para objetos).
 *
 * 4. Su único lugar razonable es ordenar conjuntos MUY pequeños (donde la
 *    constante oculta y el overhead de algoritmos complejos dominan al
 *    orden asintótico) o cuando el costo de escribir código complejo
 *    supera el beneficio.
 *
 * ===========================================================================
 * ANÁLISIS DE COMPLEJIDAD
 * ===========================================================================
 *
 * Temporal:
 *   - Mejor caso: O(n) → vector ya ordenado; con la optimización de
 *     bandera se realiza UNA sola pasada, n-1 comparaciones y 0 swaps.
 *   - Peor caso: O(n²) → orden inverso; n(n-1)/2 comparaciones y
 *     n(n-1)/2 intercambios, en n-1 pasadas (sin cortes posibles).
 *   - Caso promedio: O(n²) → en promedio la mitad de los pares se
 *     intercambia.
 *   (A diferencia de los recorridos totales de la sesión —contar, mínimo,
 *    suma, máximo— acá los tres casos NO coinciden: la bandera crea un
 *    mejor caso lineal.)
 *
 * Espacial: O(1) → orden IN-PLACE: solo índices, la bandera y la variable
 *   auxiliar del swap. No se crea ningún vector nuevo.
 *
 * ===========================================================================
 * CONTABILIZACIÓN DE OPERACIONES
 * ===========================================================================
 *
 * El programa informa: comparaciones de pares, intercambios (swap),
 * pasadas realizadas y si hubo corte temprano por bandera, con traza del
 * estado del vector en cada pasada.
 */
public class Ejercicio10 {

    /** Estadísticas del ordenamiento. */
    public static class Estadisticas {
        public int comparaciones = 0;
        public int intercambios = 0;
        public int pasadas = 0;
        public boolean corteTemprano = false;

        @Override
        public String toString() {
            return "comparaciones = " + comparaciones
                    + ", intercambios = " + intercambios
                    + ", pasadas = " + pasadas
                    + ", corte temprano = " + (corteTemprano ? "SÍ" : "NO");
        }
    }

    /**
     * Ordena el vector in-place con Bubble Sort (ascendente),
     * informando traza y estadísticas.
     *
     * @param vector arreglo a ordenar (se modifica; null se ignora)
     * @return estadísticas de la ejecución
     */
    public static Estadisticas bubbleSort(int[] vector) {
        Estadisticas stats = new Estadisticas();

        if (vector == null || vector.length < 2) {
            System.out.println("  Vector null o con menos de 2 elementos → nada que ordenar.");
            return stats;
        }

        int n = vector.length;
        System.out.println("  Vector inicial: " + java.util.Arrays.toString(vector));

        // Pasadas: después de cada una, el mayor restante queda al final
        for (int pasada = 0; pasada < n - 1; pasada++) {
            stats.pasadas++;
            boolean huboIntercambios = false;
            System.out.println("  Pasada " + (pasada + 1) + ":");

            // La región [n-pasada, n) ya está ordenada → se acorta el bucle
            for (int i = 0; i < n - 1 - pasada; i++) {
                stats.comparaciones++;
                if (vector[i] > vector[i + 1]) {
                    // Swap de pares adyacentes (O(1) memoria)
                    int temporal = vector[i];
                    vector[i] = vector[i + 1];
                    vector[i + 1] = temporal;
                    stats.intercambios++;
                    huboIntercambios = true;
                    System.out.println("    compara " + vector[i + 1] + " > "
                            + vector[i] + " → swap pos " + i + " ↔ " + (i + 1)
                            + "  " + java.util.Arrays.toString(vector));
                } else {
                    System.out.println("    compara " + vector[i] + " ≤ "
                            + vector[i + 1] + " → sin swap");
                }
            }

            // Optimización: si no hubo intercambios, ya está ordenado
            if (!huboIntercambios) {
                stats.corteTemprano = true;
                System.out.println("  Sin intercambios en esta pasada → vector ordenado, CORTO.");
                break;
            }
        }

        System.out.println("  Vector ordenado: " + java.util.Arrays.toString(vector));
        System.out.println("  Estadísticas: " + stats);
        return stats;
    }

    // ======================================================================
    // PRUEBAS
    // ======================================================================

    private static void probar(String descripcion, int[] original) {
        System.out.println("=== " + descripcion + " ===");
        System.out.println("Original: " + java.util.Arrays.toString(original));

        // Vector esperado: copia ordenada con Arrays.sort
        int[] esperado = (original == null) ? null : original.clone();
        if (esperado != null) {
            java.util.Arrays.sort(esperado);
        }

        Estadisticas stats = bubbleSort(original);
        boolean ok = java.util.Arrays.equals(original, esperado);
        System.out.println("Resultado: " + (ok ? "OK ✓" : "ERROR ✗ (esperado "
                + java.util.Arrays.toString(esperado) + ")"));
        System.out.println();
    }

    public static void main(String[] args) {
        // Caso 1: caso general con elementos repetidos
        probar("CASO 1: general con repetidos", new int[]{5, 3, 8, 3, 1, 9, 2});

        // Caso 2: ya ordenado → mejor caso (bandera corta en pasada 1)
        probar("CASO 2: ya ordenado (mejor caso)", new int[]{1, 2, 3, 4, 5, 6});

        // Caso 3: orden inverso → peor caso (n-1 pasadas completas)
        probar("CASO 3: orden inverso (peor caso)", new int[]{6, 5, 4, 3, 2, 1});

        // Caso 4: un solo elemento
        probar("CASO 4: un solo elemento", new int[]{42});

        // Caso 5: vector vacío
        probar("CASO 5: vector vacío", new int[]{});

        // Resumen comparativo
        System.out.println("=== RESUMEN ===");
        System.out.println("Mejor caso (ordenado, con flag) : O(n)   → 1 pasada.");
        System.out.println("Peor caso (inverso)            : O(n²)  → n-1 pasadas.");
        System.out.println("Promedio                       : O(n²).");
        System.out.println("Espacial: O(1) — in-place.");
        System.out.println("Solo didáctico / vectores muy chicos: quicksort y");
        System.out.println("mergesort promedian O(n log n) y dominan en la práctica.");
    }
}

/* ...CORRECCIONES HECHAS POR OPENCODE
====================================================================================
CORRECCIONES HECHAS POR OPENCODE
====================================================================================
[REDUNDANTE] La sección "POR QUÉ ES ADECUADO ÚNICAMENTE PARA FINES DIDÁCTICOS" de la cabecera desaconseja el propio algoritmo del ejercicio: sobra en un archivo cuyo pedido era solo implementarlo.
[PROMPT] El enunciado solo pedía el bubble sort; el código además define la clase `Estadisticas` con contadores, imprime traza por pasada, maneja null/vectores de menos de 2 elementos y valida contra `Arrays.sort`.
====================================================================================
*/

/*====================================================================================
                                    PROMPT 
 ====================================================================================
 "Implementa en Java un programa que ordene un arreglo de números enteros con el
 algoritmo de ordenamiento SHELL SORT.

 El programa debe mostrar el valor del GAP en cada etapa y el estado del arreglo
 luego de cada pasada.

 El objetivo es explicar por qué ShellSort MEJORA A INSERTION SORT utilizando saltos.

 1) QUÉ ALGORITMO DE ORDENAMIENTO UTILIZAR:
    Shell Sort, obligatoriamente. Es una generalización de Insertion Sort con gap:
    en lugar de comparar/desplazar elementos ADYACENTES (gap = 1), en cada etapa se
    elige un entero h (el gap) y se aplica una ordenación por inserción con paso h
    sobre los h subarreglos interleaved (posiciones i, i+h, i+2h...). Se repite con
    una secuencia de gaps decrecientes hasta llegar a h = 1.
    Secuencia obligatoria: la original de Shell → h = n/2 y luego h /= 2 (entero)
    hasta h = 1.

 2) RAZONAMIENTO
    a) El problema de la inserción con gap = 1: un desplazamiento mueve el elemento
       UNA sola posición → para que un elemento llegue k posiciones hasta su lugar
       hace k desplazamientos. Con elementos muy alejados de su destino, eso es
       muchísimo trabajo.
    b) Con gap = h cada desplazamiento (array[j] = array[j-h]) mueve el elemento h
       posiciones DE UN SALTO → los elementos lejanos llegan a su zona del arreglo
       en mucho menos escrituras: los saltos grandes acortan la distancia.
    c) Las etapas con gaps decrecientes (n/2, n/4, ..., 1) hacen que primero los
       elementos se coloquen "a grandes rasgos" (el arreglo queda h-sorted: cada
       subarreglo con paso h queda ordenado), luego se refina con saltos chicos y
       LA ÚLTIMA ETAPA (h = 1) es una inserción clásica... pero ejecutada sobre un
       arreglo YA CASI ORDENADO por las etapas anteriores → queda baratísima (en el
       ejercicio de Insertion Sort vimos que la inserción es ~O(n) casi ordenado).
    d) El gap final DEBE ser 1: solo así se garantiza el orden total entre vecinos.
    e) La secuencia de gaps importa: con la secuencia original de Shell el peor caso
       sigue siendo O(n²), pero el promedio es mucho mejor que la inserción pura;
       existen secuencias mejores (p. ej. la de Knuth: 1, 4, 13, 40, ...) que bajan
       el peor caso a O(n^1.5).
    f) El programa debe SOSTENER esto con números: ejecutar también Insertion Sort
       sobre el MISMO arreglo y mostrar los contadores lado a lado. Lo que Shell
       gana SIEMPRE son DESPLAZAMIENTOS (cada salto mueve h posiciones de una vez);
       las comparaciones también bajan cuando el desorden es grande, aunque con
       poco desorden las etapas extra pueden costar comparaciones de más.

 3) QUÉ DATOS RECIBE EL PROGRAMA:
    Un arreglo de enteros (int[]) con positivos, negativos, ceros y repetidos, de
    cualquier tamaño (vacío, 1 elemento o nulo), definido dentro de main; NO se
    requiere lectura por teclado. La ordenación es in-place sobre el arreglo
    recibido; para comparar con Insertion Sort se trabaja sobre copias idénticas
    (clone) del mismo original.

 4) QUÉ DEBE MOSTRAR POR PANTALLA:
    - El arreglo original.
    - En cada etapa: EL VALOR DEL GAP (etapa n: gap = h) y, dentro de ella, la
      traza de los desplazamientos indicando EL SALTO (de qué posición a qué otra).
    - EL ESTADO DEL ARREGLO LUEGO DE CADA PASADA (etapa), con los contadores de
      esa etapa (comparaciones y desplazamientos).
    - El arreglo final ordenado y los contadores totales.
    - La comparación de contadores Shell Sort vs Insertion Sort sobre el mismo
      arreglo (los dos números lado a lado) y un análisis final que explique por qué
      los saltos mejoran a la inserción.

 5) QUÉ COMENTARIOS DEBE INCLUIR EL CÓDIGO:
    - Javadoc en shellSort con la estrategia, la secuencia de gaps y el razonamiento
      de la sección 2 (por qué mejora a la inserción).
    - Comentario en el bucle del gap explicando que es una inserción con paso h y
      que h debe terminar en 1.
    - Comentario en el desplazamiento indicando que mueve h posiciones de un salto
      (vs 1 posición de la inserción clásica).
    - Comentarios en la convención de conteo y en los casos borde (null, vacío,
      1 elemento, todos iguales).

 6) CÓMO VERIFICAR QUE EL RESULTADO SEA CORRECTO:
    Convención de conteo (misma que en Insertion Sort):
      - Una COMPARACIÓN = cada evaluación de array[j - gap] > clave.
      - Un DESPLAZAMIENTO = cada escritura array[j] = array[j - gap]; la escritura
        final de la clave en su hueco NO cuenta.
    Verificaciones:
    - El resultado debe ser igual a java.util.Arrays.sort(original) y estar en
      orden no decreciente.
    - Contadores EXACTOS de Shell (comparaciones/desplazamientos):
        · {5, 6, 7, 8, 1, 2, 3, 4} (n=8, gaps 4→2→1): 17 / 4
        · {9, 8, 7, 6, 5, 4, 3, 2, 1} inverso (n=9, gaps 4→2→1): 22 / 8
        · {12, 34, 54, 2, 3} (n=5, gaps 2→1): 8 / 4
        · {1, 2, 3, 4, 5, 6, 7} ordenado (n=7, gaps 3→1): 10 / 0
        · {4, 1, 3, 7, 2, 8, 6, 5} mezclado (n=8): 20 / 6
        · {4, 4, 4, 4} iguales (n=4): 5 / 0
        · {42} / {} / null: 0 / 0 (null sin excepción)
    Plan de pruebas obligatorio: los 9 casos anteriores.
"
 ====================================================================================
 */
public class ShellSort {

    /**
     * Contadores del Shell Sort.
     */
    public static class Estadisticas {

        public int etapas = 0;
        public int comparaciones = 0;
        public int desplazamientos = 0;

        @Override
        public String toString() {
            return "etapas = " + etapas
                    + ", comparaciones = " + comparaciones
                    + ", desplazamientos = " + desplazamientos;
        }
    }

    // Fila de la tabla final: [caso, etapas, shellC, shellD, insC, insD, verificación]
    private static final java.util.List<String[]> TABLA = new java.util.ArrayList<>();

    /**
     * SHELL SORT — inserción con gaps decrecientes (n/2, n/4, ..., 1).
     *
     * <p>
     * <b>Por qué mejora a Insertion Sort con saltos:</b> la inserción clásica
     * (gap = 1) mueve cada elemento UNA posición por desplazamiento → llegar k
     * posiciones cuesta k desplazamientos. Con gap = h, cada desplazamiento
     * {@code array[j] = array[j-h]} mueve el elemento h posiciones DE UN SALTO,
     * así que los elementos lejanos se colocan en pocas escrituras. Las
     * primeras etapas (saltos grandes) dejan el arreglo h-sorted, "casi
     * ordenado", y la última etapa (h = 1) es una inserción clásica pero MUY
     * barata porque el arreglo ya casi está ordenado (la inserción es ~O(n) en
     * ese caso). El gap final debe ser 1 para garantizar el orden total. La
     * secuencia original de Shell tiene peor caso O(n²) con promedio mucho
     * mejor que la inserción; secuencias como la de Knuth (1, 4, 13, 40...)
     * bajan el peor caso a O(n^1.5).
     *
     * <p>
     * Convención de conteo: una comparación por cada evaluación de
     * {@code array[j - gap] > clave}; un desplazamiento por cada escritura
     * {@code array[j] = array[j - gap]} (la inserción final de la clave no
     * cuenta).
     *
     * @param array arreglo a ordenar in-place (null se ignora sin excepción)
     * @return contadores de etapas, comparaciones y desplazamientos
     */
    public static Estadisticas shellSort(int[] array) {
        Estadisticas stats = new Estadisticas();

        // Caso borde: nulo → sin excepción, solo mensaje informativo
        if (array == null) {
            System.out.println("  [Shell] arreglo nulo → nada que ordenar.");
            return stats;
        }

        System.out.println("  [Shell] original: " + java.util.Arrays.toString(array));

        // Caso borde: vacío o un solo elemento → ya está "ordenado"
        int n = array.length;
        if (n < 2) {
            System.out.println("  [Shell] menos de 2 elementos → ya está ordenado.");
            return stats;
        }

        // Secuencia original de Shell: gap = n/2 y se divide entre 2 hasta llegar a 1
        for (int gap = n / 2; gap > 0; gap /= 2) {
            stats.etapas++;
            System.out.println("  Etapa " + stats.etapas + ": gap = " + gap
                    + (gap == 1 ? "  (inserción clásica sobre el arreglo ya casi ordenado)"
                            : "  (salto de " + gap + " posiciones)"));

            int compEtapa = 0;
            int despEtapa = 0;

            // Inserción con PASO gap sobre los subarreglos interleaved
            for (int i = gap; i < n; i++) {
                int clave = array[i];
                int j = i;
                int despPaso = 0;
                StringBuilder traza = new StringBuilder();

                // Mismo bucle de la inserción, pero avanzando de a 'gap' posiciones:
                // la condición compara el elemento 'gap' lugares a la izquierda
                while (j >= gap && array[j - gap] > clave) {
                    stats.comparaciones++;
                    compEtapa++;
                    if (traza.length() > 0) {
                        traza.append("; ");
                    }
                    traza.append(array[j - gap]).append(" > ").append(clave)
                            .append(" → desplaza pos ").append(j - gap)
                            .append("→").append(j)
                            .append(" (salto de ").append(gap).append(")");

                    // Desplazamiento con SALTO: mueve el elemento 'gap' posiciones
                    // de UNA vez (la inserción clásica solo mueve 1 posición)
                    array[j] = array[j - gap];
                    stats.desplazamientos++;
                    despEtapa++;
                    despPaso++;
                    j -= gap;
                }

                if (j >= gap) {
                    // Evaluación que dio FALSO → comparación que corta el recorrido
                    stats.comparaciones++;
                    compEtapa++;
                    if (traza.length() > 0) {
                        traza.append("; ");
                    }
                    traza.append(array[j - gap]).append(" <= ").append(clave)
                            .append(" → corte anticipado");
                } else {
                    if (traza.length() > 0) {
                        traza.append("; ");
                    }
                    traza.append("llegó al inicio del subarreglo (j < gap)");
                }

                // Inserción de la clave en el hueco (NO cuenta como desplazamiento)
                array[j] = clave;

                if (despPaso == 0) {
                    System.out.println("    i=" + i + " (clave = " + clave + "): "
                            + traza + " → sin saltos");
                } else {
                    System.out.println("    i=" + i + " (clave = " + clave + "): "
                            + traza);
                }
            }

            // ESTADO DEL ARREGLO LUEGO DE CADA PASADA (requisito de salida)
            System.out.println("  Estado tras la pasada (gap = " + gap + "): "
                    + java.util.Arrays.toString(array));
            System.out.println("  → etapa con " + compEtapa + " comparaciones y "
                    + despEtapa + " desplazamientos");
            System.out.println();
        }

        System.out.println("  [Shell] arreglo ordenado: " + java.util.Arrays.toString(array));
        System.out.println("  [Shell] TOTAL: " + stats);
        return stats;
    }

    // ======================================================================
    // INSERCIÓN (SOLO CONTADORES), VERIFICACIÓN Y PRUEBAS
    // ======================================================================
    /**
     * Insertion Sort SIN traza: solo cuenta comparaciones y desplazamientos
     * para comparar contra Shell Sort sobre el mismo arreglo.
     */
    private static Estadisticas insertionSortContadores(int[] array) {
        Estadisticas stats = new Estadisticas();
        int n = array.length;
        for (int i = 1; i < n; i++) {
            int clave = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > clave) {
                stats.comparaciones++;
                array[j + 1] = array[j];
                stats.desplazamientos++;
                j--;
            }
            if (j >= 0) {
                stats.comparaciones++;
            }
            array[j + 1] = clave;
        }
        return stats;
    }

    /**
     * Comprueba que el arreglo esté en orden no decreciente.
     */
    private static boolean esOrdenado(int[] arreglo) {
        for (int i = 0; i + 1 < arreglo.length; i++) {
            if (arreglo[i] > arreglo[i + 1]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Ejecuta un caso: Shell vs Insertion sobre copias del MISMO arreglo,
     * verifica contra Arrays.sort y valida los contadores exactos de Shell.
     *
     * @param expComparaciones comparaciones exactas esperadas de Shell
     * @param expDesplazamientos desplazamientos exactos esperados de Shell
     */
    private static void probar(String descripcion, int[] original,
            int expComparaciones, int expDesplazamientos) {
        System.out.println("=== " + descripcion + " ===");

        // Caso borde: nulo → sin excepción
        if (original == null) {
            System.out.println("Original: null");
            Estadisticas stats = shellSort(null);
            System.out.println("Verificación: sin excepción → OK ✓");
            System.out.println();
            TABLA.add(new String[]{descripcion, String.valueOf(stats.etapas),
                String.valueOf(stats.comparaciones),
                String.valueOf(stats.desplazamientos), "-", "-", "OK"});
            return;
        }

        System.out.println("Original: " + java.util.Arrays.toString(original));

        // Resultado esperado: copia ordenada con Arrays.sort
        int[] esperado = original.clone();
        java.util.Arrays.sort(esperado);

        // Mismo original → copia idéntica para cada algoritmo
        int[] paraShell = original.clone();
        Estadisticas shell = shellSort(paraShell);

        int[] paraInsercion = original.clone();
        Estadisticas insercion = insertionSortContadores(paraInsercion);

        boolean igualQueSort = java.util.Arrays.equals(paraShell, esperado);
        boolean ordenado = esOrdenado(paraShell);
        boolean contadoresOk = shell.comparaciones == expComparaciones
                && shell.desplazamientos == expDesplazamientos;
        boolean todoOk = igualQueSort && ordenado && contadoresOk;

        System.out.println("Verificación vs Arrays.sort: "
                + (igualQueSort ? "OK ✓" : "ERROR ✗ (esperado "
                        + java.util.Arrays.toString(esperado) + ")"));
        System.out.println("Verificación orden no decreciente: "
                + (ordenado ? "OK ✓" : "ERROR ✗"));
        System.out.println("Verificación contadores de Shell (esperado "
                + expComparaciones + " / " + expDesplazamientos + "): "
                + (contadoresOk ? "OK ✓" : "ERROR ✗"));
        System.out.println("Comparación de contadores sobre el mismo arreglo:");
        System.out.println("    Shell Sort   : " + shell.comparaciones
                + " comp / " + shell.desplazamientos + " desp   (etapas con salto)");
        System.out.println("    Insertion    : " + insercion.comparaciones
                + " comp / " + insercion.desplazamientos + " desp   (gap = 1 siempre)");
        System.out.println();

        TABLA.add(new String[]{descripcion,
            String.valueOf(shell.etapas),
            String.valueOf(shell.comparaciones),
            String.valueOf(shell.desplazamientos),
            String.valueOf(insercion.comparaciones),
            String.valueOf(insercion.desplazamientos),
            todoOk ? "OK" : "ERROR"});
    }

    public static void main(String[] args) {
        // Caso 1: los pequeños saltan 4 posiciones hasta el frente
        probar("CASO 1: saltos grandes (n=8)", new int[]{5, 6, 7, 8, 1, 2, 3, 4}, 17, 4);

        // Caso 2: orden inverso → el desorden máximo que los saltos aplanan
        probar("CASO 2: inverso (n=9)", new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1}, 22, 8);

        // Caso 3: arreglo chico (n=5, gaps 2 → 1)
        probar("CASO 3: chico (n=5)", new int[]{12, 34, 54, 2, 3}, 8, 4);

        // Caso 4: ya ordenado → las etapas extra cuestan comparaciones
        probar("CASO 4: ordenado (n=7)", new int[]{1, 2, 3, 4, 5, 6, 7}, 10, 0);

        // Caso 5: mezclado
        probar("CASO 5: mezclado (n=8)", new int[]{4, 1, 3, 7, 2, 8, 6, 5}, 20, 6);

        // Caso 6: todos iguales → ninguna comparación es >
        probar("CASO 6: todos iguales (n=4)", new int[]{4, 4, 4, 4}, 5, 0);

        // Caso 7: un solo elemento
        probar("CASO 7: un solo elemento", new int[]{42}, 0, 0);

        // Caso 8: arreglo vacío
        probar("CASO 8: arreglo vacío", new int[]{}, 0, 0);

        // Caso 9: arreglo nulo → sin excepción
        probar("CASO 9: arreglo nulo", null, 0, 0);

        // Tabla comparativa final
        System.out.println("=== TABLA COMPARATIVA (comp / desp) ===");
        System.out.printf("%-30s | %5s | %-13s | %-13s | %s%n",
                "CASO", "ETAPAS", "SHELL", "INSERCIÓN", "VERIFICACIÓN");
        System.out.println("--------------------------------------------------------------"
                + "------------------");
        for (String[] fila : TABLA) {
            System.out.printf("%-30s | %5s | %-13s | %-13s | %s%n",
                    fila[0], fila[1],
                    fila[2] + "/" + fila[3], fila[4] + "/" + fila[5], fila[6]);
        }

        // Análisis del objetivo
        System.out.println();
        System.out.println("=== ANÁLISIS: POR QUÉ LOS SALTOS MEJORAN A LA INSERCIÓN ===");
        System.out.println("Inserción (gap = 1): cada desplazamiento mueve 1 posición →");
        System.out.println("  que un elemento recorra k posiciones cuesta k desplazamientos.");
        System.out.println("Shell (gap = h): cada desplazamiento mueve h posiciones DE UN");
        System.out.println("  SALTO → los elementos lejanos llegan a su zona en pocas");
        System.out.println("  escrituras y las etapas n/2, n/4, ... dejan el arreglo casi");
        System.out.println("  ordenado; la etapa final (gap = 1) es una inserción clásica");
        System.out.println("  BARATA porque el arreglo ya casi está (≈ O(n) casi ordenado).");
        System.out.println();
        System.out.println("Medido en este programa sobre el MISMO arreglo:");
        System.out.println("  CASO 2 {9..1}: inserción 36 comp / 36 desp");
        System.out.println("               vs shell   22 comp /  8 desp → MUCHO mejor.");
        System.out.println("  CASO 5 mezclado: inserción 16 comp / 10 desp");
        System.out.println("                  vs shell  20 comp /  6 desp.");
        System.out.println("Matiz honesto: con poco o nada de desorden (CASO 4 ordenado,");
        System.out.println("  CASO 5 mezclado) las etapas extra cuestan comparaciones");
        System.out.println("  (6 vs 10 y 16 vs 20); lo que shell gana SIEMPRE son los");
        System.out.println("  desplazamientos (16→4, 36→8, 10→6) porque cada salto mueve");
        System.out.println("  h posiciones de una vez. El gap final = 1 garantiza el orden.");
    }
}

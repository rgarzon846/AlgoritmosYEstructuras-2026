/*====================================================================================
                                    PROMPT 
 ====================================================================================
 "Implementa en Java un programa que ordene un arreglo de números enteros con el
 algoritmo QUICKSORT, usando SIEMPRE el primer elemento del subarreglo como pivote.

 El programa debe mostrar:
   • el pivote elegido (en cada partición);
   • el subarreglo izquierdo (< pivote);
   • el subarreglo derecho (>= pivote);
   • el arreglo final ordenado.

 El objetivo es RELACIONAR EL ALGORITMO CON RECURSIVIDAD Y DIVIDE Y VENCERÁS.

 1) QUÉ ALGORITMO DE ORDENAMIENTO UTILIZAR:
    Quicksort, obligatoriamente, con pivote = SIEMPRE el primer elemento del
    subarreglo (arreglo[inicio]). Convención de partición: los elementos MENORES
    que el pivote van a la izquierda y los IGUALES O MAYORES a la derecha (mandar
    los iguales a la derecha evita bucles infinitos); al terminar, el pivote se
    intercambia con el último elemento menor y queda en su posición FINAL.

 2) RAZONAMIENTO
    a) DIVIDE Y VENCERÁS con sus tres pasos:
       - DIVIDE: la PARTICIÓN parte el problema en dos subarreglos independientes
         (izquierda < pivote | pivote en su sitio | derecha >= pivote).
       - VENCER: cada subarreglo se resuelve con la MISMA función, recursivamente.
       - COMBINAR: no hace falta: la partición es in-place y el pivote ya quedó
         ordenado de forma definitiva, así que "unir" las mitades es gratis.
    b) RECURSIVIDAD: quicksort(inicio, fin) se llama a sí misma con
       quicksort(inicio, p-1) y quicksort(p+1, fin). CASOS BASE: subarreglo vacío
       o de un solo elemento (no hay nada que partir). La PROFUNDIDAD máxima de las
       llamadas es la altura del árbol de recursión: ≈ log n si las particiones
       parten ~al medio, ≈ n si parten mal.
    c) La partición con pivote primero: se recorre (inicio+1 .. fin) manteniendo la
       frontera de los elementos menores que el pivote (intercambiándolos hacia
       adelante) y al final se intercambia el pivote con el último menor.
    d) EL PIVOTE MANDA: con el PRIMER elemento, un arreglo ya ordenado o inverso
       produce particiones (n-1 | 0) → peor caso O(n²) y profundidad n (con n
       grande, riesgo de StackOverflowError). Con un pivote bien elegido
       (mediana de tres o aleatorio) las particiones parten ~al medio → O(n log n)
       esperado y profundidad ≈ log n. También degenera con todos los iguales
       (solución clásica: partición en 3 vías).
    e) Complejidad esperada: promedio O(n log n) tiempo / O(log n) espacio de pila
       (con este pivote, peor O(n²) tiempo / O(n) de pila).

 3) QUÉ DATOS RECIBE EL PROGRAMA:
    Un arreglo de enteros (int[]) con positivos, negativos, ceros y repetidos, de
    cualquier tamaño (vacío, 1 elemento o nulo), definido dentro de main; NO se
    requiere lectura por teclado. La ordenación es in-place sobre el arreglo
    recibido.

 4) QUÉ DEBE MOSTRAR POR PANTALLA:
    - En cada llamada recursiva: el rango (inicio, fin) y su PROFUNDIDAD, con
      sangrado según el nivel (para ver el árbol de recursión); los casos base
      (vacío / 1 elemento) también se informan.
    - En cada partición: EL PIVOTE ELEGIDO, EL SUBARREGLO IZQUIERDO y EL
      SUBARREGLO DERECHO (los cuatro elementos pedidos por el enunciado), la
      posición final del pivote y si los invariantes de la partición se cumplen.
    - EL ARREGLO FINAL ORDENADO.
    - Los estadísticos de la recursión: cantidad de particiones, total de llamadas
      a quicksort y profundidad máxima, más la verificación del resultado.

 5) QUÉ COMENTARIOS DEBE INCLUIR EL CÓDIGO:
    - Javadoc en quicksort con la estrategia y el mapeo explícito divide / vencer /
      combinar, los casos base y el papel del pivote.
    - Comentario en particionar() explicando la frontera de elementos < pivote y el
      intercambio final del pivote a su posición definitiva.
    - Comentario en las dos llamadas recursivas (izquierda y derecha) y en los
      casos base de quicksortRec.
    - Comentario sobre el riesgo del pivote primer elemento (peor caso O(n²),
      profundidad n) y en los casos borde (null, vacío, 1 elemento).

 6) CÓMO VERIFICAR QUE EL RESULTADO SEA CORRECTO:
    Verificaciones:
    - El resultado final debe ser igual a java.util.Arrays.sort(original) y estar
      en orden no decreciente.
    - En CADA partición verificar los invariantes: el pivote quedó en su posición,
      todo lo de la izquierda es < pivote y todo lo de la derecha es >= pivote.
    - Contadores EXACTOS de la recursión (particiones / llamadas / profundidad
      máxima; la llamada inicial cuenta como profundidad 1):
        · {5, 3, 8, 1, 9, 2, 7} general (n=7): 4 / 9 / 4
        · {1, 2, 3, 4, 5, 6} ordenado (n=6): 5 / 11 / 6
        · {6, 5, 4, 3, 2, 1} inverso (n=6): 5 / 11 / 6
        · {4, 4, 4, 4} todos iguales (n=4): 3 / 7 / 4
        · {-3, 0, -1, 7} con negativos (n=4): 2 / 5 / 3
        · {2, 8, 3, 8, 1, 9, 2} con repetidos (n=7): 4 / 9 / 5
        · {42} un elemento: 0 / 1 / 1
        · {} vacío: 0 / 1 / 1
        · null: 0 / 0 / 0 (sin excepción)
    Plan de pruebas obligatorio: los 9 casos anteriores.
"
 ====================================================================================
 */
public class QuicksortPivotePrimero {

    /**
     * Estadísticos de la recursión y verificación de invariantes.
     */
    public static class Estadisticas {

        public int particiones = 0;
        public int llamadas = 0;
        public int profundidadMaxima = 0;
        public boolean invariantesOk = true;

        @Override
        public String toString() {
            return "particiones = " + particiones
                    + ", llamadas = " + llamadas
                    + ", profundidad máxima = " + profundidadMaxima
                    + ", invariantes = " + (invariantesOk ? "OK ✓" : "ERROR ✗");
        }
    }

    // Fila de la tabla final: [caso, part, llam, prof, verificación]
    private static final java.util.List<String[]> TABLA = new java.util.ArrayList<>();

    /**
     * QUICKSORT con pivote = PRIMER ELEMENTO — ordena de forma recursiva
     * aplicando divide y vencerás.
     *
     * <p>
     * <b>Mapeo divide y vencerás:</b> DIVIDE = la partición parte el arreglo en
     * dos subarreglos independientes (izquierda &lt; pivote | pivote | derecha
     * &gt;= pivote) y deja el pivote en su posición final; VENCER = quicksort
     * se llama a sí misma sobre cada subarreglo; COMBINAR = no hace falta, la
     * partición es in-place y el pivote ya quedó ordenado.
     *
     * <p>
     * <b>Recursividad:</b> {@code quicksort(inicio, fin) → quicksort(inicio, p-1)
     * y quicksort(p+1, fin)}; casos base = subarreglo vacío o de 1 elemento. La
     * profundidad máxima de las llamadas es la altura del árbol de recursión (≈
     * log n con particiones balanceadas, ≈ n con el peor caso).
     *
     * <p>
     * <b>El pivote manda:</b> con el primer elemento, arreglos ordenados o
     * inversos producen particiones (n-1 | 0) → O(n²) y profundidad n (riesgo
     * de StackOverflowError); con pivote aleatorio/mediana de tres se alcanza
     * O(n log n) esperado.
     *
     * @param array arreglo a ordenar in-place (null se ignora sin excepción)
     * @return estadísticos de la recursión
     */
    public static Estadisticas quicksort(int[] array) {
        Estadisticas stats = new Estadisticas();

        // Caso borde: nulo → sin excepción, solo mensaje informativo
        if (array == null) {
            System.out.println("  [Quicksort] arreglo nulo → nada que ordenar.");
            return stats;
        }

        System.out.println("  [Quicksort] original: " + java.util.Arrays.toString(array));

        // Recursión: desde la llamada inicial (inicio=0, fin=n-1, profundidad=1).
        // Para el arreglo vacío esto invoca quicksort(0, -1) → caso base "vacío".
        quicksortRec(array, 0, array.length - 1, 1, stats);

        // ARREGLO FINAL ORDENADO (requisito de salida)
        System.out.println("Arreglo final ordenado: " + java.util.Arrays.toString(array));
        System.out.println("Estadísticos de la recursión: " + stats);
        return stats;
    }

    /**
     * Llamada recursiva de quicksort: caso base (vacío o 1 elemento) o
     * partición + dos llamadas a sí misma (izquierda y derecha).
     */
    private static void quicksortRec(int[] a, int low, int high, int profundidad,
            Estadisticas stats) {
        stats.llamadas++;
        if (profundidad > stats.profundidadMaxima) {
            stats.profundidadMaxima = profundidad;
        }
        String indent = indentar(profundidad);

        // CASO BASE 1: subarreglo vacío → no hay nada que partir
        if (low > high) {
            System.out.println(indent + "quicksort(inicio=" + low + ", fin=" + high
                    + ") [prof " + profundidad + "] → base: subarreglo vacío");
            return;
        }

        // CASO BASE 2: un solo elemento → ya está ordenado, no se divide
        if (low == high) {
            System.out.println(indent + "quicksort(inicio=" + low + ", fin=" + high
                    + ") [prof " + profundidad + "] → base: 1 elemento, ya ordenado");
            return;
        }

        System.out.println(indent + "quicksort(inicio=" + low + ", fin=" + high
                + ") [prof " + profundidad + "]");

        // DIVIDE: partición alrededor del pivote (primer elemento)
        int p = particionar(a, low, high, profundidad, stats);

        // VENCER: la misma función sobre los DOS subarreglos independientes
        // (COMBINAR no hace falta: la partición ya dejó el pivote ordenado)
        quicksortRec(a, low, p - 1, profundidad + 1, stats);
        quicksortRec(a, p + 1, high, profundidad + 1, stats);
    }

    /**
     * Partición con pivote = primer elemento: recorre (inicio+1..fin) y mueve a
     * la izquierda los elementos &lt; pivote; al final intercambia el pivote
     * con el último menor para que quede en su posición definitiva.
     *
     * @return la posición final del pivote
     */
    private static int particionar(int[] a, int low, int high, int profundidad,
            Estadisticas stats) {
        stats.particiones++;
        String indent = indentar(profundidad);

        // PIVOTE ELEGIDO: SIEMPRE el primer elemento del subarreglo
        int pivote = a[low];

        // Frontera: [low+1 .. i] contiene los elementos < pivote encontrados
        int i = low;
        for (int j = low + 1; j <= high; j++) {
            if (a[j] < pivote) {
                i++;
                if (i != j) {
                    int temporal = a[i];
                    a[i] = a[j];
                    a[j] = temporal;
                }
            }
        }

        // El pivote pasa a su posición final (último elemento < pivote)
        if (i != low) {
            int temporal = a[low];
            a[low] = a[i];
            a[i] = temporal;
        }
        int p = i;

        // Invariantes de la partición: pivote en su sitio, izq < pivote, der >= pivote
        boolean ok = (a[p] == pivote);
        for (int t = low; t < p && ok; t++) {
            if (a[t] >= pivote) {
                ok = false;
            }
        }
        for (int t = p + 1; t <= high && ok; t++) {
            if (a[t] < pivote) {
                ok = false;
            }
        }
        if (!ok) {
            stats.invariantesOk = false;
        }

        // SALIDA PEDIDA: pivote elegido, subarreglo izquierdo y subarreglo derecho
        String izquierdo = java.util.Arrays.toString(java.util.Arrays.copyOfRange(a, low, p));
        String derecho = java.util.Arrays.toString(java.util.Arrays.copyOfRange(a, p + 1, high + 1));
        System.out.println(indent + "  particion(inicio=" + low + ", fin=" + high
                + "): PIVOTE ELEGIDO = " + pivote + " (primer elemento)");
        System.out.println(indent + "  subarreglo izquierdo (< " + pivote + "): " + izquierdo);
        System.out.println(indent + "  subarreglo derecho   (>= " + pivote + "): " + derecho);
        System.out.println(indent + "  pivote " + pivote + " → posición " + p
                + " | invariantes de la partición: " + (ok ? "OK ✓" : "ERROR ✗"));

        return p;
    }

    /**
     * Sangrado según la profundidad de la llamada (muestra el árbol de
     * recursión).
     */
    private static String indentar(int profundidad) {
        StringBuilder sb = new StringBuilder();
        for (int k = 1; k < profundidad; k++) {
            sb.append("  ");
        }
        return sb.toString();
    }

    // ======================================================================
    // VERIFICACIÓN AUXILIAR Y PRUEBAS
    // ======================================================================
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
     * Ejecuta un caso: ordena, verifica contra Arrays.sort y valida los
     * estadísticos exactos de la recursión.
     *
     * @param espStats {particiones, llamadas, profundidad máxima} esperados
     */
    private static void probar(String descripcion, int[] original, int[] espStats) {
        System.out.println("=== " + descripcion + " ===");

        // Caso borde: nulo → sin excepción
        if (original == null) {
            System.out.println("Original: null");
            Estadisticas stats = quicksort(null);
            boolean statsOk = stats.particiones == espStats[0]
                    && stats.llamadas == espStats[1]
                    && stats.profundidadMaxima == espStats[2];
            System.out.println("Verificación: sin excepción → OK ✓");
            System.out.println("Verificación estadísticos: "
                    + (statsOk ? "OK ✓" : "ERROR ✗"));
            System.out.println();
            TABLA.add(new String[]{descripcion, String.valueOf(stats.particiones),
                String.valueOf(stats.llamadas),
                String.valueOf(stats.profundidadMaxima),
                statsOk ? "OK" : "ERROR"});
            return;
        }

        System.out.println("Original: " + java.util.Arrays.toString(original));

        // Resultado esperado: copia ordenada con Arrays.sort
        int[] esperado = original.clone();
        java.util.Arrays.sort(esperado);

        int[] paraOrdenar = original.clone();
        Estadisticas stats = quicksort(paraOrdenar);

        boolean igualQueSort = java.util.Arrays.equals(paraOrdenar, esperado);
        boolean ordenado = esOrdenado(paraOrdenar);
        boolean statsOk = stats.particiones == espStats[0]
                && stats.llamadas == espStats[1]
                && stats.profundidadMaxima == espStats[2];
        boolean todoOk = igualQueSort && ordenado && stats.invariantesOk && statsOk;

        System.out.println("Verificación vs Arrays.sort: "
                + (igualQueSort ? "OK ✓" : "ERROR ✗ (esperado "
                        + java.util.Arrays.toString(esperado) + ")"));
        System.out.println("Verificación orden no decreciente: "
                + (ordenado ? "OK ✓" : "ERROR ✗"));
        System.out.println("Verificación invariantes de TODAS las particiones: "
                + (stats.invariantesOk ? "OK ✓" : "ERROR ✗"));
        System.out.println("Verificación estadísticos (esperado "
                + espStats[0] + " / " + espStats[1] + " / " + espStats[2] + "): "
                + (statsOk ? "OK ✓" : "ERROR ✗"));
        System.out.println();

        TABLA.add(new String[]{descripcion,
            String.valueOf(stats.particiones),
            String.valueOf(stats.llamadas),
            String.valueOf(stats.profundidadMaxima),
            todoOk ? "OK" : "ERROR"});
    }

    public static void main(String[] args) {
        // Caso 1: caso general
        probar("CASO 1: general con repetidos", new int[]{5, 3, 8, 1, 9, 2, 7},
                new int[]{4, 9, 4});

        // Caso 2: ya ordenado → PEOR caso con pivote primer elemento (prof = n)
        probar("CASO 2: ordenado (peor caso)", new int[]{1, 2, 3, 4, 5, 6},
                new int[]{5, 11, 6});

        // Caso 3: orden inverso → también degenera (prof = n)
        probar("CASO 3: inverso (peor caso)", new int[]{6, 5, 4, 3, 2, 1},
                new int[]{5, 11, 6});

        // Caso 4: todos iguales → los iguales van a la derecha, también degenera
        probar("CASO 4: todos iguales", new int[]{4, 4, 4, 4},
                new int[]{3, 7, 4});

        // Caso 5: con negativos
        probar("CASO 5: con negativos", new int[]{-3, 0, -1, 7},
                new int[]{2, 5, 3});

        // Caso 6: con repetidos (los iguales al pivote se van a la derecha)
        probar("CASO 6: con repetidos", new int[]{2, 8, 3, 8, 1, 9, 2},
                new int[]{4, 9, 5});

        // Caso 7: un solo elemento → caso base directo
        probar("CASO 7: un solo elemento", new int[]{42}, new int[]{0, 1, 1});

        // Caso 8: arreglo vacío → caso base directo
        probar("CASO 8: arreglo vacío", new int[]{}, new int[]{0, 1, 1});

        // Caso 9: arreglo nulo → sin excepción
        probar("CASO 9: arreglo nulo", null, new int[]{0, 0, 0});

        // Tabla de estadísticos de la recursión
        System.out.println("=== TABLA DE LA RECURSIÓN (particiones / llamadas / profundidad) ===");
        System.out.printf("%-34s | %4s | %5s | %11s | %s%n",
                "CASO", "PART", "LLAM", "PROFUNDIDAD", "VERIFICACIÓN");
        System.out.println("--------------------------------------------------------------"
                + "------------------");
        for (String[] fila : TABLA) {
            System.out.printf("%-34s | %4s | %5s | %11s | %s%n",
                    fila[0], fila[1], fila[2], fila[3], fila[4]);
        }

        // Análisis: recursividad y divide y vencerás
        System.out.println();
        System.out.println("=== ANÁLISIS: RECURSIVIDAD Y DIVIDE Y VENCERÁS ===");
        System.out.println("DIVIDE   : la partición parte el arreglo en dos subarreglos");
        System.out.println("           independientes y fija el pivote en su posición.");
        System.out.println("VENCER   : quicksort(inicio, fin) se llama a sí misma con");
        System.out.println("           quicksort(inicio, p-1) y quicksort(p+1, fin).");
        System.out.println("COMBINAR : no hace falta: la partición es in-place y el pivote");
        System.out.println("           ya quedó ordenado (las mitades ya están ordenadas).");
        System.out.println("CASOS BASE: subarreglo vacío o de 1 elemento → la recursión");
        System.out.println("           se detiene (si no, nunca terminaría).");
        System.out.println();
        System.out.println("La profundidad = altura del árbol de recursión:");
        System.out.println("  - CASO 1 (mezclado): profundidad 4 con n=7 → particiones");
        System.out.println("    bastante balanceadas (como log2(7) ≈ 2.8, un poco peor).");
        System.out.println("  - CASO 2/3/4 (ordenado, inverso, iguales): profundidad = n →");
        System.out.println("    con el PRIMER elemento como pivote, la partición deja");
        System.out.println("    (n-1 | 0): peor caso O(n²) y la pila crece linealmente");
        System.out.println("    (con n grande: StackOverflowError).");
        System.out.println("  - Por eso en la práctica el pivote se ELIGE (mediana de tres");
        System.out.println("    o aleatorio): particiones ~al medio → O(n log n) esperado");
        System.out.println("    y profundidad ~log n.");
    }
}

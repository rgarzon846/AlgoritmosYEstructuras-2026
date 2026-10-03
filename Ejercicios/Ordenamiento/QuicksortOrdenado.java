/*====================================================================================
                                    PROMPT 
 ====================================================================================
 "Implementa en Java un programa que aplique QUICKSORT sobre un arreglo YA ORDENADO.
 Ejemplo:

     int[] numeros = {1, 2, 3, 4, 5, 6, 7};

 Usando SIEMPRE el primer elemento como pivote, el programa debe CONTAR LA CANTIDAD
 DE LLAMADAS RECURSIVAS realizadas.

 El objetivo es explicar por qué elegir siempre el primer elemento como pivote puede
 GENERAR UN MAL RENDIMIENTO.

 1) QUÉ ALGORITMO DE ORDENAMIENTO UTILIZAR:
    Quicksort, obligatoriamente, con pivote = PRIMER elemento del subarreglo
    (convención: menores a la izquierda, iguales o mayores a la derecha, el pivote
    queda en su posición final). Además, para CONTRASTAR el rendimiento, la misma
    implementación debe poder ejecutarse con pivote = elemento CENTRAL del
    subarreglo (solo cambia la elección del pivote, la partición es idéntica).

 2) RAZONAMIENTO
    a) En un arreglo ORDENADO el primer elemento es el MÍNIMO: la partición deja
       (0 | n-1) → subarreglo izquierdo VACÍO y derecho = todo el resto. El
       problema NO se parte en dos: se reduce de n a n-1. Con el arreglo INVERSO
       pasa lo mismo (el primero es el máximo → (n-1 | 0)) y con todos iguales
       también: es el peor caso posible de quicksort.
    b) La recursión degenera de LÍNEA: cada nodo tiene un único hijo no vacío →
       profundidad = n en lugar de ~log n → la pila de llamadas crece linealmente
       y con n grande puede provocar StackOverflowError.
    c) Comparaciones: cada nivel recorre todo lo que queda → 1 + 2 + ... + (n-1)
       = n(n-1)/2 = O(n²) tiempo, en lugar de ~n log n con particiones balanceadas.
    d) Además se llaman recursivamente sobre subarreglos VACÍOS (el hijo izquierdo
       de cada partición), que suman llamadas sin resolver nada.
    e) La solución en la práctica: ELIGIR el pivote (mediana de tres, aleatorio, o
       al menos el central) para que las particiones parten ~al medio → O(n log n)
       esperado y profundidad ~log n.
    f) Sustento numérico obligatorio: ejecutar el MISMO arreglo ordenado con pivote
       primero y con pivote central, y mostrar los contadores lado a lado.

 3) QUÉ DATOS RECIBE EL PROGRAMA:
    Arreglos de enteros (int[]) ORDENADOS de forma creciente: el del enunciado
    {1, 2, 3, 4, 5, 6, 7} y otros tamaños (15, 31, 63) para observar el
    crecimiento de los contadores; definidos dentro de main; NO se requiere lectura
    por teclado; la ordenación es in-place (se trabaja sobre una copia para
    verificar contra el original).

 4) QUÉ DEBE MOSTRAR POR PANTALLA:
    - Para {1..7}: la traza de las llamadas recursivas con su PROFUNDIDAD y de cada
      partición (pivote, subarreglo izquierdo, subarreglo derecho), evidenciando
      que la izquierda queda SIEMPRE vacía y que la recursión avanza en línea.
    - LA CANTIDAD DE LLAMADAS RECURSIVAS REALIZADAS (requisito central),
      diferenciadas de la llamada inicial, más totales, particiones, profundidad
      máxima y comparaciones.
    - La misma traza/contadores con pivote central para el mismo arreglo (contraste).
    - Una tabla de crecimiento: n vs contadores con pivote primero y con pivote
      central (n = 7, 15, 31, 63).
    - El arreglo final ordenado y las verificaciones.

 5) QUÉ COMENTARIOS DEBE INCLUIR EL CÓDIGO:
    - Javadoc en quicksort con la explicación completa del mal rendimiento del
      pivote primer elemento (sección 2) y de por qué el central lo evita.
    - Comentario en la elección del pivote (índice del subarreglo y swap previo
      para dejarlo en la posición inicial de la partición).
    - Comentario en las llamadas recursivas explicando por qué con este arreglo un
      lado queda vacío (la recursión no divide) y en los casos base.
    - Comentario de la convención de conteo de llamadas recursivas y comparaciones,
      y de los casos borde (null, vacío, 1 elemento).

 6) CÓMO VERIFICAR QUE EL RESULTADO SEA CORRECTO:
    Convención de conteo:
      - Una LLAMADA RECURSIVA = cada invocación de quicksort distinta de la
        inicial (la inicial se cuenta aparte como "total").
      - Una COMPARACIÓN = cada evaluación de arreglo[j] < pivote.
    Verificaciones:
    - El resultado debe ser igual a java.util.Arrays.sort(original) y estar en
      orden no decreciente; en cada partición se verifican sus invariantes
      (izquierda < pivote, derecha >= pivote).
    - Contadores EXACTOS {totales, recursivas, particiones, profundidad, comparaciones}:
        · {1..7} pivote PRIMERO (ejemplo del enunciado): 13, 12, 6, 7, 21
        · {1..7} pivote CENTRAL:                        7, 6, 3, 3, 10
        · {1..15} primero: 29, 28, 14, 15, 105   | central: 15, 14, 7, 4, 34
        · {1..31} primero: 61, 60, 30, 31, 465   | central: 31, 30, 15, 5, 98
        · {1..63} primero: 125, 124, 62, 63, 1953 | central: 63, 62, 31, 6, 258
        · {42} / {} (pivote primero): 1, 0, 0, 1, 0
        · null: 0, 0, 0, 0, 0 (sin excepción)
    Plan de pruebas obligatorio: los casos anteriores (los dos primeros con traza).
"
 ====================================================================================
 */
public class QuicksortOrdenado {

    /**
     * Estadísticos de la recursión (llamadas, particiones, profundidad, comps).
     */
    public static class Estadisticas {

        public int llamadasTotales = 0;
        public int llamadasRecursivas = 0; // totales menos la inicial
        public int particiones = 0;
        public int profundidadMaxima = 0;
        public int comparaciones = 0;
        public boolean invariantesOk = true;

        @Override
        public String toString() {
            return "llamadas recursivas = " + llamadasRecursivas
                    + " (totales " + llamadasTotales + ")"
                    + ", particiones = " + particiones
                    + ", profundidad máxima = " + profundidadMaxima
                    + ", comparaciones = " + comparaciones
                    + ", invariantes = " + (invariantesOk ? "OK ✓" : "ERROR ✗");
        }
    }

    // Fila de la tabla final: [etiqueta, tot, rec, part, prof, comp, OK]
    private static final java.util.List<String[]> TABLA = new java.util.ArrayList<>();

    /**
     * QUICKSORT sobre arreglos ordenados — con pivote PRIMER elemento (peor
     * caso) o CENTRAL (contraste).
     *
     * <p>
     * <b>Por qué el primer elemento como pivote puede generar mal
     * rendimiento:</b>
     * en un arreglo ordenado el primer elemento es el mínimo → la partición
     * deja (0 | n-1): el subarreglo izquierdo queda VACÍO y el derecho contiene
     * todo el resto. El problema no se divide: se reduce de n a n-1, la
     * recursión degenera en una LÍNEA de profundidad n (riesgo de
     * StackOverflowError) y las comparaciones suman 1+2+...+(n-1) = n(n-1)/2 =
     * O(n²). Lo mismo ocurre con el arreglo inverso (el primero es el máximo) o
     * con todos iguales. Por eso en la práctica el pivote se ELIGE (central,
     * mediana de tres o aleatorio): las particiones parten ~al medio → O(n log
     * n) esperado y profundidad ~log n.
     *
     * <p>
     * Convención de conteo: una llamada recursiva = cada invocación de
     * quicksort distinta de la inicial; una comparación = cada evaluación de
     * {@code arreglo[j] < pivote}.
     *
     * @param array arreglo a ordenar in-place (null se ignora sin excepción)
     * @param pivoteCentral true = pivote en el índice central del subarreglo
     * @param conTraza true = imprime el árbol de llamadas y las particiones
     * @return estadísticos de la recursión
     */
    public static Estadisticas quicksort(int[] array, boolean pivoteCentral,
            boolean conTraza) {
        Estadisticas stats = new Estadisticas();

        // Caso borde: nulo → sin excepción, solo mensaje informativo
        if (array == null) {
            if (conTraza) {
                System.out.println("  [Quicksort] arreglo nulo → nada que ordenar.");
            }
            return stats;
        }

        if (conTraza) {
            System.out.println("  [Quicksort] original: "
                    + java.util.Arrays.toString(array));
        }

        // Llamada inicial (NO recursiva): inicio=0, fin=n-1, profundidad=1
        quicksortRec(array, 0, array.length - 1, 1, stats, pivoteCentral, conTraza);

        // Toda invocación menos la inicial es una llamada recursiva
        stats.llamadasRecursivas = Math.max(0, stats.llamadasTotales - 1);

        if (conTraza) {
            System.out.println("Arreglo final ordenado: "
                    + java.util.Arrays.toString(array));
            System.out.println("Estadísticos: " + stats);
        }
        return stats;
    }

    /**
     * Llamada recursiva: caso base o partición + dos llamadas (una vacía acá).
     */
    private static void quicksortRec(int[] a, int low, int high, int profundidad,
            Estadisticas stats, boolean pivoteCentral,
            boolean conTraza) {
        stats.llamadasTotales++;
        if (profundidad > stats.profundidadMaxima) {
            stats.profundidadMaxima = profundidad;
        }
        String indent = indentar(profundidad);

        // CASOS BASE: vacío o 1 elemento → la recursión se detiene
        if (low > high) {
            if (conTraza) {
                System.out.println(indent + "quicksort(inicio=" + low + ", fin=" + high
                        + ") [prof " + profundidad + "] → base: subarreglo vacío");
            }
            return;
        }
        if (low == high) {
            if (conTraza) {
                System.out.println(indent + "quicksort(inicio=" + low + ", fin=" + high
                        + ") [prof " + profundidad + "] → base: 1 elemento");
            }
            return;
        }

        if (conTraza) {
            System.out.println(indent + "quicksort(inicio=" + low + ", fin=" + high
                    + ") [prof " + profundidad + "]");
        }

        int p = particionar(a, low, high, profundidad, stats, pivoteCentral, conTraza);

        // Llamadas recursivas: con el arreglo ordenado y pivote primero,
        // la izquierda SIEMPRE queda vacía → la recursión avanza en línea
        quicksortRec(a, low, p - 1, profundidad + 1, stats, pivoteCentral, conTraza);
        quicksortRec(a, p + 1, high, profundidad + 1, stats, pivoteCentral, conTraza);
    }

    /**
     * Partición con pivote elegido (primer o central), dejándolo en la posición
     * low.
     */
    private static int particionar(int[] a, int low, int high, int profundidad,
            Estadisticas stats, boolean pivoteCentral,
            boolean conTraza) {
        stats.particiones++;
        String indent = indentar(profundidad);

        // ELECCIÓN DEL PIVOTE: índice inicial (low) o central del subarreglo
        int idxPivote = pivoteCentral ? (low + high) / 2 : low;
        if (idxPivote != low) {
            // Se trae el pivote a la posición inicial para reusar la misma partición
            int temporal = a[low];
            a[low] = a[idxPivote];
            a[idxPivote] = temporal;
        }
        int pivote = a[low];

        // Frontera de elementos < pivote; cada evaluación cuenta 1 comparación
        int i = low;
        for (int j = low + 1; j <= high; j++) {
            stats.comparaciones++;
            if (a[j] < pivote) {
                i++;
                if (i != j) {
                    int temporal = a[i];
                    a[i] = a[j];
                    a[j] = temporal;
                }
            }
        }
        if (i != low) {
            int temporal = a[low];
            a[low] = a[i];
            a[i] = temporal;
        }
        int p = i;

        // Invariantes: izquierda < pivote, pivote en su sitio, derecha >= pivote
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

        if (conTraza) {
            String izq = java.util.Arrays.toString(java.util.Arrays.copyOfRange(a, low, p));
            String der = java.util.Arrays.toString(java.util.Arrays.copyOfRange(a, p + 1, high + 1));
            System.out.println(indent + "  particion(inicio=" + low + ", fin=" + high
                    + "): pivote = " + pivote + (pivoteCentral ? " (central)" : " (PRIMER elemento)")
                    + " → posición " + p);
            System.out.println(indent + "    izquierdo: " + izq + " | derecho: " + der
                    + (p == low ? "   ← izquierda VACÍA: NO divide el problema" : "")
                    + (p == high ? "   ← derecha VACÍA: NO divide el problema" : "")
                    + (ok ? "" : " | INVARIANTES ERROR ✗"));
        }
        return p;
    }

    /**
     * Sangrado según la profundidad (muestra la forma del árbol de recursión).
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
     * Ejecuta un caso, verifica contra Arrays.sort y los contadores exactos.
     *
     * @param esp {totales, recursivas, particiones, profundidad, comparaciones}
     */
    private static void probar(String descripcion, int[] original, boolean pivoteCentral,
            boolean conTraza, int[] esp) {
        System.out.println("=== " + descripcion + " ===");

        // Caso borde: nulo → sin excepción
        if (original == null) {
            Estadisticas stats = quicksort(null, pivoteCentral, conTraza);
            boolean statsOk = statsOk(stats, esp);
            System.out.println("Original: null → verificación: sin excepción OK ✓, estadísticos "
                    + (statsOk ? "OK ✓" : "ERROR ✗"));
            System.out.println();
            TABLA.add(new String[]{descripcion, "-", "-", "-", "-", "-",
                statsOk ? "OK" : "ERROR"});
            return;
        }

        System.out.println("Original: " + java.util.Arrays.toString(original));

        int[] esperado = original.clone();
        java.util.Arrays.sort(esperado);

        int[] paraOrdenar = original.clone();
        Estadisticas stats = quicksort(paraOrdenar, pivoteCentral, conTraza);

        boolean igualQueSort = java.util.Arrays.equals(paraOrdenar, esperado);
        boolean ordenado = esOrdenado(paraOrdenar);
        boolean statsOk = statsOk(stats, esp);
        boolean todoOk = igualQueSort && ordenado && stats.invariantesOk && statsOk;

        System.out.println("Verificación vs Arrays.sort: "
                + (igualQueSort ? "OK ✓" : "ERROR ✗"));
        System.out.println("Verificación orden no decreciente: "
                + (ordenado ? "OK ✓" : "ERROR ✗"));
        System.out.println("Verificación invariantes de partición: "
                + (stats.invariantesOk ? "OK ✓" : "ERROR ✗"));
        System.out.println("Verificación contadores {tot, rec, part, prof, comp} (esperado "
                + esp[0] + ", " + esp[1] + ", " + esp[2] + ", " + esp[3] + ", " + esp[4]
                + "): " + (statsOk ? "OK ✓" : "ERROR ✗"));
        System.out.println(">> LLAMADAS RECURSIVAS REALIZADAS: " + stats.llamadasRecursivas);
        System.out.println();

        TABLA.add(new String[]{descripcion,
            String.valueOf(stats.llamadasTotales),
            String.valueOf(stats.llamadasRecursivas),
            String.valueOf(stats.particiones),
            String.valueOf(stats.profundidadMaxima),
            String.valueOf(stats.comparaciones),
            todoOk ? "OK" : "ERROR"});
    }

    private static boolean statsOk(Estadisticas s, int[] esp) {
        return s.llamadasTotales == esp[0]
                && s.llamadasRecursivas == esp[1]
                && s.particiones == esp[2]
                && s.profundidadMaxima == esp[3]
                && s.comparaciones == esp[4];
    }

    /**
     * Fila silenciosa para la tabla de crecimiento (verifica igual).
     */
    private static String[] filaCrecimiento(String etiqueta, int[] original,
            boolean pivoteCentral, int[] esp) {
        int[] esperado = original.clone();
        java.util.Arrays.sort(esperado);
        int[] paraOrdenar = original.clone();
        Estadisticas s = quicksort(paraOrdenar, pivoteCentral, false);
        boolean ok = java.util.Arrays.equals(paraOrdenar, esperado)
                && statsOk(s, esp);
        return new String[]{etiqueta,
            s.llamadasRecursivas + " / " + s.comparaciones + " / " + s.profundidadMaxima,
            ok ? "OK" : "ERROR"};
    }

    public static void main(String[] args) {
        // CASO 1: el arreglo del enunciado, pivote PRIMER elemento → peor caso
        int[] numeros = {1, 2, 3, 4, 5, 6, 7};
        probar("CASO 1: {1..7} pivote PRIMERO (peor caso)", numeros,
                false, true, new int[]{13, 12, 6, 7, 21});

        // CASO 2: el MISMO arreglo con pivote central → contraste
        probar("CASO 2: {1..7} pivote CENTRAL (contraste)", numeros,
                true, true, new int[]{7, 6, 3, 3, 10});

        // Casos borde
        probar("CASO 3: un solo elemento", new int[]{42}, false, false,
                new int[]{1, 0, 0, 1, 0});
        probar("CASO 4: arreglo vacío", new int[]{}, false, false,
                new int[]{1, 0, 0, 1, 0});
        probar("CASO 5: arreglo nulo", null, false, false,
                new int[]{0, 0, 0, 0, 0});

        // Tabla de crecimiento: cómo se descontrolan los contadores con pivote primero
        System.out.println("=== TABLA DE CRECIMIENTO SOBRE ARREGLOS ORDENADOS ===");
        System.out.println("(columnas de contadores: llamadas recursivas / comparaciones"
                + " / profundidad)");
        System.out.printf("%-6s | %-30s | %-30s | %s%n",
                "n", "PIVOTE PRIMERO", "PIVOTE CENTRAL", "VERIF");
        System.out.println("--------------------------------------------------------------"
                + "------------------");
        int[] tamanos = {7, 15, 31, 63};
        for (int n : tamanos) {
            int[] ordenado = new int[n];
            for (int k = 0; k < n; k++) {
                ordenado[k] = k + 1;
            }
            // Contadores esperados: primero → 2n-2 rec, n(n-1)/2 comp, prof n;
            //                      central → n-1 rec, ~n log n comp, prof log2(n+1)
            int[] espPrimero = {2 * n - 1, 2 * n - 2, n - 1, n, n * (n - 1) / 2};
            int[] espCentral = {n, n - 1, (n - 1) / 2,
                (int) (Math.log(n + 1) / Math.log(2)),
                centralCompsEsperadas(n)};
            String[] filaP = filaCrecimiento("n=" + n + " primero", ordenado, false, espPrimero);
            String[] filaC = filaCrecimiento("n=" + n + " central", ordenado, true, espCentral);
            System.out.printf("%-6d | %-30s | %-30s | %s%n", n,
                    filaP[1], filaC[1],
                    (filaP[2].equals("OK") && filaC[2].equals("OK") ? "OK" : "ERROR"));
            TABLA.add(new String[]{"n=" + n, filaP[1], filaC[1],
                (filaP[2].equals("OK") && filaC[2].equals("OK") ? "OK" : "ERROR")});
        }

        // Análisis del objetivo
        System.out.println();
        System.out.println("=== ANÁLISIS: POR QUÉ EL PRIMER ELEMENTO COMO PIVOTE RINDE MAL ===");
        System.out.println("Arreglo ordenado → el primer elemento es el MÍNIMO:");
        System.out.println("cada partición deja (izq VACÍA | resto) → la recursión es una");
        System.out.println("LÍNEA, no un árbol: profundidad n (pila lineal → riesgo de");
        System.out.println("StackOverflowError) y comparaciones 1+2+...+(n-1) = O(n²).");
        System.out.println();
        System.out.println("Medido con el MISMO arreglo {1,2,3,4,5,6,7}:");
        System.out.println("  pivote PRIMERO: 12 llamadas recursivas, 21 comparaciones,"
                + " profundidad 7");
        System.out.println("  pivote CENTRAL:  6 llamadas recursivas, 10 comparaciones,"
                + " profundidad 3");
        System.out.println("  → mismos resultados con MUCHO menos trabajo y menos pila.");
        System.out.println();
        System.out.println("Crecimiento con el arreglo ordenado (recursivas / comps / prof):");
        System.out.println("  primero: n=7 → 12/21/7 · n=15 → 28/105/15 · n=31 → 60/465/31");
        System.out.println("           n=63 → 124/1953/63   (es O(n²), NO O(n log n))");
        System.out.println("  central: n=7 → 6/10/3 · n=15 → 14/34/4 · n=31 → 30/98/5");
        System.out.println("           n=63 → 62/258/6      (≈ O(n log n), prof ≈ log n)");
        System.out.println("Conclusión: el peor caso no lo causa el quicksort en sí,");
        System.out.println("sino la ELECCIÓN DEL PIVOTE: por eso se usa mediana de tres,");
        System.out.println("índice central o un pivote aleatorio.");
    }

    /**
     * Comparaciones esperadas de la partición con pivote central sobre {1..n}
     * ordenado con n = 2^k - 1: suma nivel a nivel de (tamaño-1).
     */
    private static int centralCompsEsperadas(int n) {
        int total = 0;
        int tam = n;
        while (tam > 1) {
            total += (tam - 1) * cuentaSubarreglos(n, tam);
            tam /= 2;
        }
        return total;
    }

    private static int cuentaSubarreglos(int n, int tam) {
        int cuenta = 0;
        int resto = n;
        // con n = 2^k - 1 los tamaños se parten exacto: 1, 2, 4, ...
        int actual = n;
        int nivel = 1;
        while (actual > 0) {
            if (actual == tam) {
                cuenta += nivel;
            }
            nivel *= 2;
            actual = (actual - 1) / 2;
        }
        return cuenta;
    }
}

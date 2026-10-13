/*====================================================================================
                                    PROMPT 
 ====================================================================================
 "Implementa en Java un programa que aplique MERGESORT sobre un arreglo de enteros.
  El programa debe mostrar:
      • cómo se divide el arreglo;
      • cuándo llega a la condición de corte;
      • cómo se van fusionando los subarreglos.
  El objetivo es que el prompt explique claramente la diferencia entre dividir y fusionar.

  1) QUÉ ALGORITMO DE ORDENAMIENTO UTILIZAR:
     MergeSort (dividir y conquistar), recursivo, sobre una copia del arreglo y con un
     arreglo AUXILIAR para la fusión (el ordenamiento es ESTABLE). El mismo programa
     imprime la traza completa: división, condición de corte y fusión.

  2) RAZONAMIENTO — LA DIFERENCIA ENTRE DIVIDIR Y FUSIONAR (este es el objetivo: debe
     quedar en el Javadoc, en la salida y en el análisis final):
     a) DIVIDIR es solo partir el problema: mergesort(inicio, fin) calcula la mitad
        (mid) y llama a la mitad izquierda y a la derecha. DIVIDIR NO ORDENA NADA:
        es un simple desglose del trabajo; los elementos no se mueven de sitio. Hasta
        llegar al corte, el programa no hizo NINGÚN trabajo útil de ordenamiento.
     b) La CONDICIÓN DE CORTE es lo que hace terminar la recursión: un subarreglo
        VACÍO o de 1 elemento YA ESTÁ ORDENADO (un solo valor siempre es orden;
        vacío, también). Ahí la llamada se devuelve sin hacer nada más. Sin ese caso
        base, "la mitad de la mitad de la mitad..." nunca llegaría al final.
     c) FUSIONAR es donde se ORDENA: merge recorre en paralelo los dos frentes de los
        subarreglos (ya ordenados por sus llamadas hijas) eligiendo en cada paso el
        MENOR de los dos → cada elemento elegido queda en su posición final → el
        resultado es un único arreglo ordenado del doble de tamaño.
     d) El ORDEN DE LOS PASOS es la clave: primero se divide hasta llegar al corte
        (descenso / pre-orden) y RECÍEN AL VOLVER, en sentido inverso, se fusiona
        (ascenso / post-orden). Las fusiones ocurren de ABAJO HACIA ARRIBA: primero
        pares de 1, luego arreglos de 2 con 2, de 4 con 4... NUNCA se fusiona algo
        que todavía no está ordenado. Dividir da el ORDEN de los pasos; fusionar hace
        el TRABAJO.
     e) Complejidad: n elementos terminan en n hojas de 1 → 1 + ⌈log₂ n⌉ niveles de
        llamadas, y en cada nivel se fusionan en total n elementos → O(n log n) en
        TODO caso (peor, promedio y mejor), contra el O(n²) de quicksort con pivote
        primer elemento sobre un arreglo ordenado. Se paga con O(n) de memoria
        auxiliar.
     f) Sustento numérico obligatorio: contadores exactos del MISMO arreglo
        desordenado, ordenado e inverso (llamadas, divisiones, fusiones, cortes y
        profundidad NO dependen de los datos → son iguales en los tres; las
        comparaciones SÍ dependen).

  3) QUÉ DATOS RECIBE EL PROGRAMA:
     Arreglos de enteros (int[]) definidos dentro de main: el de ejemplo
     {5, 3, 8, 1, 9, 2, 7}, {1,2,3,4,5,6,7} ya ordenado, {7,6,5,4,3,2,1} inverso,
     {42}, vacío y null, más tamaños 15, 31 y 63 para la tabla de crecimiento;
     NO se requiere lectura por teclado; se ordena una copia para verificar contra
     el original.

  4) QUÉ DEBE MOSTRAR POR PANTALLA:
     - CÓMO SE DIVIDE: cada llamada con su profundidad, el índice de la mitad y los
       dos subarreglos resultantes (izq | der), con sangrado que dibuja el árbol.
     - CUÁNDO LLEGA A LA CONDICIÓN DE CORTE: mensaje explícito en cada subarreglo de
       0 o 1 elemento ("CORTE: 1 elemento [x] → ya está ordenado").
     - CÓMO SE VAN FUSIONANDO: al volver de la recursión, cada fusión con sus dos
       mitades, el resultado y las comparaciones que le llevó, evidenciando el orden
       de abajo hacia arriba.
     - Los contadores de cada caso: llamadas (totales / recursivas), divisiones,
       fusiones, cortes, profundidad máxima y comparaciones.
     - Tabla de crecimiento n = 7, 15, 31, 63 (sobre arreglos ordenados {1..n}).
     - Un RESUMEN de verificación con los contadores exactos de todos los casos.
     - El análisis final: dividir vs fusionar y por qué es O(n log n) siempre.
     - El arreglo final ordenado y las verificaciones.

  5) QUÉ COMENTARIOS DEBE INCLUIR EL CÓDIGO:
     - Javadoc en mergesort con la explicación completa de dividir vs fusionar
       (sección 2), la condición de corte y la complejidad O(n log n).
     - Comentario en la condición de corte: por qué 0 o 1 elemento ya está ordenado y
       por qué sin ese caso base la recursión no terminaría.
     - Comentario en la fusión: por qué recorrer los dos frentes en paralelo mantiene
       el orden (estabilidad) y coloca cada elemento en su posición final; por qué
       primero se copia el rango a un arreglo auxiliar.
     - Comentario de la convención de conteo (llamadas, divisiones, fusiones, cortes,
       comparaciones) y de los casos borde (null, vacío, 1 elemento).

  6) CÓMO VERIFICAR QUE EL RESULTADO SEA CORRECTO:
     Convención de conteo:
       - Llamada RECURSIVA = cada invocación de mergesort distinta de la inicial.
       - DIVISIÓN = cada llamada que parte el rango en dos (tamaño > 1).
       - CORTE = cada llamada que cumple la condición de corte (tamaño 0 o 1).
       - FUSIÓN = cada merge ejecutado (siempre uno por cada división).
       - COMPARACIÓN = cada evaluación de "derecha[der] < izquierda[izq]" en la
         fusión (las copias de los sobrantes NO se cuentan como comparación).
     Verificaciones:
       - El resultado debe ser igual a java.util.Arrays.sort(original) y estar en
         orden no decreciente; en CADA fusión se verifica su invariante: el tramo
         resultante está ordenado y contiene exactamente los mismos elementos que
         antes de fusionar.
     Contadores EXACTOS {totales, recursivas, divisiones, fusiones, cortes, profundidad, comparaciones}:
         · {5,3,8,1,9,2,7} (ejemplo, con traza):    13, 12, 6, 6, 7, 4, 14
         · {1,2,3,4,5,6,7} ordenado (con traza):    13, 12, 6, 6, 7, 4, 11
         · {7,6,5,4,3,2,1} inverso:                13, 12, 6, 6, 7, 4, 9
         · {42} / {}:                               1, 0, 0, 0, 1, 1, 0
         · null:                                    0, 0, 0, 0, 0, 0, 0 (sin excepción)
     Para CUALQUIER arreglo de n ≥ 1 (no depende de los datos):
         totales = 2n-1, recursivas = 2n-2, divisiones = fusiones = n-1,
         cortes = n, profundidad = 1 + ⌈log₂ n⌉.
     Tabla de crecimiento sobre {1..n} ordenado:
         n=7  → 13, 12,  6,  6,  7, 4,  11
         n=15 → 29, 28, 14, 14, 15, 5,  31
         n=31 → 61, 60, 30, 30, 31, 6,  79
         n=63 → 125,124,62, 62, 63, 7, 191
     Plan de pruebas obligatorio: los casos anteriores (los dos primeros con traza).
 "
 ====================================================================================
*/
public class Ejercicio8 {

    /** Estadísticos de la recursión (llamadas, divisiones, fusiones, cortes, comps). */
    public static class Estadisticas {
        public int llamadasTotales = 0;
        public int llamadasRecursivas = 0; // totales menos la inicial
        public int divisiones = 0;         // llamadas que parten el rango en dos
        public int fusiones = 0;           // merges ejecutados (uno por división)
        public int casosDeCorte = 0;       // llamadas con 0 o 1 elemento
        public int profundidadMaxima = 0;
        public int comparaciones = 0;
        public boolean invariantesOk = true;

        @Override
        public String toString() {
            return "llamadas recursivas = " + llamadasRecursivas
                    + " (totales " + llamadasTotales + ")"
                    + ", divisiones = " + divisiones
                    + ", fusiones = " + fusiones
                    + ", cortes = " + casosDeCorte
                    + ", profundidad máxima = " + profundidadMaxima
                    + ", comparaciones = " + comparaciones
                    + ", invariantes = " + (invariantesOk ? "OK ✓" : "ERROR ✗");
        }
    }

    // Fila del resumen final: [descripción, contadores, verificación]
    private static final java.util.List<String[]> TABLA = new java.util.ArrayList<>();

    /**
     * MERGESORT — dividir y conquistar: la diferencia entre DIVIDIR y FUSIONAR.
     *
     * <p><b>DIVIDIR</b> (descenso, pre-orden): mergesort(inicio, fin) solo calcula la
     * mitad y llama a las dos mitades. Dividir NO ordena nada: es el desglose del
     * trabajo; hasta llegar al corte no se hizo ningún trabajo útil.
     *
     * <p><b>Condición de corte</b>: subarreglo vacío o de 1 elemento → YA está
     * ordenado (un solo valor siempre es orden) → la llamada se devuelve sin más.
     * Sin ese caso base la recursión nunca terminaría.
     *
     * <p><b>FUSIONAR</b> (ascenso, post-orden): recién al VOLER de la recursión,
     * merge recorre en paralelo los dos frentes de los subarreglos (ya ordenados por
     * las hijas) eligiendo siempre el MENOR → cada elegido queda en su posición
     * final → resultado ordenado del doble de tamaño. Las fusiones son de ABAJO
     * HACIA ARRIBA (1s, luego 2s, luego 4s...); nunca se fusiona algo no ordenado.
     *
     * <p><b>Complejidad</b>: n hojas de 1 → 1 + ⌈log₂ n⌉ niveles y n elementos
     * fusionados por nivel → <b>O(n log n) en todo caso</b> (mejor, promedio y
     * peor), con O(n) de memoria auxiliar; es ESTABLE. A diferencia de quicksort
     * con pivote primer elemento sobre arreglo ordenado (O(n²)), acá no existe un
     * caso de entrada que lo empeore: dividir siempre parte en dos mitades.
     *
     * <p>Convención de conteo: llamada recursiva = invocación distinta de la
     * inicial; división = llamada con tamaño &gt; 1; corte = llamada con tamaño 0 o
     * 1; fusión = cada merge; comparación = cada evaluación de
     * {@code derecha[der] < izquierda[izq]}.
     *
     * @param array    arreglo a ordenar sobre una copia in-place (null se ignora)
     * @param conTraza true = imprime el árbol de divisiones, cortes y fusiones
     * @return estadísticos de la recursión
     */
    public static Estadisticas mergesort(int[] array, boolean conTraza) {
        Estadisticas stats = new Estadisticas();

        // Caso borde: nulo → sin excepción, solo mensaje informativo
        if (array == null) {
            if (conTraza) {
                System.out.println("  [MergeSort] arreglo nulo → nada que ordenar.");
            }
            return stats;
        }

        if (conTraza) {
            System.out.println("  [MergeSort] original: "
                    + java.util.Arrays.toString(array));
        }

        // Arreglo auxiliar de la fusión (se reutiliza en toda la recursión)
        int[] auxiliar = new int[array.length];

        // Llamada inicial (NO recursiva): inicio=0, fin=n-1, profundidad=1
        mergesortRec(array, 0, array.length - 1, auxiliar, 1, stats, conTraza);

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
     * Llamada recursiva: CONDICIÓN DE CORTE → DIVISIÓN + dos llamadas → FUSIÓN.
     * El orden es descendente para dividir y ascendente (al volver) para fusionar.
     */
    private static void mergesortRec(int[] a, int low, int high, int[] auxiliar,
                                     int profundidad, Estadisticas stats,
                                     boolean conTraza) {
        stats.llamadasTotales++;
        if (profundidad > stats.profundidadMaxima) {
            stats.profundidadMaxima = profundidad;
        }
        String indent = indentar(profundidad);

        // CONDICIÓN DE CORTE: vacío o 1 elemento → YA está ordenado, no hay nada
        // que hacer. Sin este corte, la mitad de la mitad... nunca terminaría.
        if (low > high) {
            stats.casosDeCorte++;
            if (conTraza) {
                System.out.println(indent + "mergesort(inicio=" + low + ", fin=" + high
                        + ") [prof " + profundidad
                        + "] → CORTE: subarreglo vacío (0 elementos) → ya está ordenado");
            }
            return;
        }
        if (low == high) {
            stats.casosDeCorte++;
            if (conTraza) {
                System.out.println(indent + "mergesort(inicio=" + low + ", fin=" + high
                        + ") [prof " + profundidad + "] → CORTE: 1 elemento ["
                        + a[low] + "] → ya está ordenado (trivial)");
            }
            return;
        }

        // DIVIDIR: solo partir el problema en dos mitades. NO ordena nada acá.
        stats.divisiones++;
        int mid = (low + high) / 2;
        if (conTraza) {
            System.out.println(indent + "mergesort(inicio=" + low + ", fin=" + high
                    + ") [prof " + profundidad + "]");
            System.out.println(indent + "  divide: mitad=" + mid
                    + " → izq=" + rango(a, low, mid)
                    + " | der=" + rango(a, mid + 1, high));
        }

        // Descenso: se divide hasta llegar a la condición de corte
        mergesortRec(a, low, mid, auxiliar, profundidad + 1, stats, conTraza);
        mergesortRec(a, mid + 1, high, auxiliar, profundidad + 1, stats, conTraza);

        // Ascenso: RECÍEN AL VOLVER se fusiona (de abajo hacia arriba)
        fusionar(a, low, mid, high, auxiliar, indent, stats, conTraza);
    }

    /**
     * FUSIÓN: recorre en paralelo los dos frentes (ambos subarreglos ya ordenados)
     * eligiendo el menor de los dos. Cada elegido queda en su posición final y, como
     * en empate se toma el de la izquierda, el orden original se conserva
     * (estabilidad). Copiamos el rango a un arreglo auxiliar para leer de ahí y
     * escribir ordenado en el arreglo original.
     */
    private static void fusionar(int[] a, int low, int mid, int high, int[] auxiliar,
                                 String indent, Estadisticas stats, boolean conTraza) {
        stats.fusiones++;
        int compsAntes = stats.comparaciones;

        // Copia de seguridad del tramo antes de pisarlo
        for (int t = low; t <= high; t++) {
            auxiliar[t] = a[t];
        }

        int izq = low;          // frente de la mitad izquierda
        int der = mid + 1;      // frente de la mitad derecha
        int k = low;            // posición donde se escribe el resultado
        while (izq <= mid && der <= high) {
            stats.comparaciones++;   // 1 comparación por cada paso de elección
            if (auxiliar[der] < auxiliar[izq]) {
                a[k++] = auxiliar[der++];   // el de la derecha es menor → va primero
            } else {
                a[k++] = auxiliar[izq++];   // empate o izquierda menor → izquierda
            }                               // (el empate hacia la izquierda = estable)
        }
        // Copia de los SOBRANTES: no hay comparación, los dos lados ya están
        // ordenados y todo lo que queda es mayor (o menor) que lo ya escrito.
        while (izq <= mid) {
            a[k++] = auxiliar[izq++];
        }
        while (der <= high) {
            a[k++] = auxiliar[der++];
        }

        // Invariante de esta fusión: el tramo quedó ordenado y con los mismos
        // elementos que antes (nada se perdió ni se duplicó)
        boolean ok = fusionCorrecta(a, auxiliar, low, high);
        if (!ok) {
            stats.invariantesOk = false;
        }

        if (conTraza) {
            int comps = stats.comparaciones - compsAntes;
            System.out.println(indent + "  fusiona: izq=" + rango(auxiliar, low, mid)
                    + " der=" + rango(auxiliar, mid + 1, high)
                    + " → " + rango(a, low, high)
                    + "   (" + comps + (comps == 1 ? " comparación)" : " comparaciones)")
                    + (ok ? "" : " | INVARIANTES ERROR ✗"));
        }
    }

    /** Sangrado según la profundidad (muestra la forma del árbol de recursión). */
    private static String indentar(int profundidad) {
        StringBuilder sb = new StringBuilder();
        for (int k = 1; k < profundidad; k++) {
            sb.append("  ");
        }
        return sb.toString();
    }

    /** Representación de un tramo [low..high] del arreglo. */
    private static String rango(int[] a, int low, int high) {
        return java.util.Arrays.toString(
                java.util.Arrays.copyOfRange(a, low, high + 1));
    }

    // ======================================================================
    // VERIFICACIÓN AUXILIAR Y PRUEBAS
    // ======================================================================

    /** Comprueba que el tramo [low..high] esté en orden no decreciente. */
    private static boolean esOrdenadoRango(int[] a, int low, int high) {
        for (int t = low; t + 1 <= high; t++) {
            if (a[t] > a[t + 1]) {
                return false;
            }
        }
        return true;
    }

    /** El tramo fusionado está ordenado y contiene exactamente los mismos elementos. */
    private static boolean fusionCorrecta(int[] a, int[] auxiliar, int low, int high) {
        int[] antes = java.util.Arrays.copyOfRange(auxiliar, low, high + 1);
        int[] despues = java.util.Arrays.copyOfRange(a, low, high + 1);
        java.util.Arrays.sort(antes);
        return java.util.Arrays.equals(antes, despues) && esOrdenadoRango(a, low, high);
    }

    /** Comprueba que el arreglo completo esté en orden no decreciente. */
    private static boolean esOrdenado(int[] arreglo) {
        return esOrdenadoRango(arreglo, 0, arreglo.length - 1);
    }

    /**
     * Ejecuta un caso, verifica contra Arrays.sort y los contadores exactos.
     *
     * @param esp {totales, recursivas, divisiones, fusiones, cortes, profundidad, comparaciones}
     */
    private static void probar(String descripcion, int[] original, boolean conTraza,
                               int[] esp) {
        System.out.println("=== " + descripcion + " ===");

        // Caso borde: nulo → sin excepción
        if (original == null) {
            Estadisticas stats = mergesort(null, conTraza);
            boolean statsOk = statsOk(stats, esp);
            System.out.println("Original: null → verificación: sin excepción OK ✓, estadísticos "
                    + (statsOk ? "OK ✓" : "ERROR ✗"));
            System.out.println();
            TABLA.add(new String[]{descripcion, contadores(esp),
                    statsOk ? "OK" : "ERROR"});
            return;
        }

        System.out.println("Original: " + java.util.Arrays.toString(original));

        int[] esperado = original.clone();
        java.util.Arrays.sort(esperado);

        int[] paraOrdenar = original.clone();
        Estadisticas stats = mergesort(paraOrdenar, conTraza);

        boolean igualQueSort = java.util.Arrays.equals(paraOrdenar, esperado);
        boolean ordenado = esOrdenado(paraOrdenar);
        boolean statsOk = statsOk(stats, esp);
        boolean todoOk = igualQueSort && ordenado && stats.invariantesOk && statsOk;

        System.out.println("Verificación vs Arrays.sort: "
                + (igualQueSort ? "OK ✓" : "ERROR ✗"));
        System.out.println("Verificación orden no decreciente: "
                + (ordenado ? "OK ✓" : "ERROR ✗"));
        System.out.println("Verificación invariantes de cada fusión: "
                + (stats.invariantesOk ? "OK ✓" : "ERROR ✗"));
        System.out.println("Verificación contadores {tot, rec, div, fus, cortes, prof, comp}"
                + " (esperado " + contadores(esp) + "): "
                + (statsOk ? "OK ✓" : "ERROR ✗"));
        System.out.println(">> LLAMADAS RECURSIVAS REALIZADAS: " + stats.llamadasRecursivas
                + " | DIVISIONES: " + stats.divisiones
                + " | FUSIONES: " + stats.fusiones
                + " | CORTES: " + stats.casosDeCorte);
        System.out.println();

        TABLA.add(new String[]{descripcion,
                stats.llamadasTotales + ", " + stats.llamadasRecursivas + ", "
                        + stats.divisiones + ", " + stats.fusiones + ", "
                        + stats.casosDeCorte + ", " + stats.profundidadMaxima + ", "
                        + stats.comparaciones,
                todoOk ? "OK" : "ERROR"});
    }

    private static String contadores(int[] esp) {
        return esp[0] + ", " + esp[1] + ", " + esp[2] + ", " + esp[3] + ", "
                + esp[4] + ", " + esp[5] + ", " + esp[6];
    }

    private static boolean statsOk(Estadisticas s, int[] esp) {
        return s.llamadasTotales == esp[0]
                && s.llamadasRecursivas == esp[1]
                && s.divisiones == esp[2]
                && s.fusiones == esp[3]
                && s.casosDeCorte == esp[4]
                && s.profundidadMaxima == esp[5]
                && s.comparaciones == esp[6];
    }

    /** Fila silenciosa para la tabla de crecimiento (verifica igual). */
    private static String[] filaCrecimiento(String etiqueta, int[] original,
                                            int[] esp) {
        int[] esperado = original.clone();
        java.util.Arrays.sort(esperado);
        int[] paraOrdenar = original.clone();
        Estadisticas s = mergesort(paraOrdenar, false);
        boolean ok = java.util.Arrays.equals(paraOrdenar, esperado)
                && statsOk(s, esp);
        return new String[]{etiqueta,
                s.llamadasTotales + " / " + s.llamadasRecursivas + " / "
                        + s.divisiones + " / " + s.fusiones + " / "
                        + s.casosDeCorte + " / " + s.profundidadMaxima + " / "
                        + s.comparaciones,
                ok ? "OK" : "ERROR"};
    }

    /** ⌈log₂ n⌉ para n ≥ 1 (niveles del árbol de divisiones). */
    private static int ceilLog2(int n) {
        return (int) Math.ceil(Math.log(n) / Math.log(2));
    }

    /**
     * Comparaciones esperadas de todas las fusiones sobre {1..n} ORDENADO.
     * Con el arreglo ordenado, la izquierda siempre gana → cada fusión de un tramo
     * con L elementos a la izquierda cuesta L comparaciones (y se agota la izquierda).
     * El split de mergesort es izq = ⌈n/2⌉, der = ⌊n/2⌋ (mid = (0 + n-1)/2).
     */
    private static int compsFusionOrdenado(int n) {
        if (n <= 1) {
            return 0;
        }
        int izq = (n + 1) / 2;
        int der = n / 2;
        return compsFusionOrdenado(izq) + compsFusionOrdenado(der) + izq;
    }

    public static void main(String[] args) {
        // CASO 1: el arreglo del enunciado, con traza completa
        probar("CASO 1: {5,3,8,1,9,2,7} ejemplo (con traza)",
                new int[]{5, 3, 8, 1, 9, 2, 7}, true,
                new int[]{13, 12, 6, 6, 7, 4, 14});

        // CASO 2: el mismo tamaño YA ordenado → mismos contadores estructurales
        probar("CASO 2: {1..7} ya ordenado (con traza)",
                new int[]{1, 2, 3, 4, 5, 6, 7}, true,
                new int[]{13, 12, 6, 6, 7, 4, 11});

        // CASO 3: inverso → misma estructura, menos comparaciones
        probar("CASO 3: {7..1} inverso",
                new int[]{7, 6, 5, 4, 3, 2, 1}, false,
                new int[]{13, 12, 6, 6, 7, 4, 9});

        // Casos borde
        probar("CASO 4: un solo elemento", new int[]{42}, false,
                new int[]{1, 0, 0, 0, 1, 1, 0});
        probar("CASO 5: arreglo vacío", new int[]{}, false,
                new int[]{1, 0, 0, 0, 1, 1, 0});
        probar("CASO 6: arreglo nulo", null, false,
                new int[]{0, 0, 0, 0, 0, 0, 0});

        // Tabla de crecimiento: la estructura crece lineal + log, no cuadrático
        System.out.println("=== TABLA DE CRECIMIENTO (arreglos ordenados {1..n}) ===");
        System.out.println("(contadores: totales / recursivas / divisiones / fusiones"
                + " / cortes / profundidad / comparaciones)");
        System.out.printf("%-6s | %-38s | %s%n", "n", "CONTADORES", "VERIF");
        System.out.println("----------------------------------------------------------");
        int[] tamanos = {7, 15, 31, 63};
        for (int n : tamanos) {
            int[] ordenado = new int[n];
            for (int k = 0; k < n; k++) {
                ordenado[k] = k + 1;
            }
            // Estructura (cualquier arreglo): 2n-1, 2n-2, n-1, n-1, n, 1+⌈log₂ n⌉
            int[] esp = {2 * n - 1, 2 * n - 2, n - 1, n - 1, n,
                    1 + ceilLog2(n), compsFusionOrdenado(n)};
            String[] fila = filaCrecimiento("n=" + n, ordenado, esp);
            System.out.printf("%-6d | %-38s | %s%n", n, fila[1], fila[2]);
        }

        // Resumen de verificación de todos los casos
        System.out.println();
        System.out.println("=== RESUMEN DE VERIFICACIÓN (contadores exactos) ===");
        System.out.printf("%-46s | %-31s | %s%n", "CASO",
                "tot, rec, div, fus, cortes, prof", "VERIF");
        System.out.println("------------------------------------------------------------------");
        for (String[] fila : TABLA) {
            System.out.printf("%-46s | %-31s | %s%n", fila[0], fila[1], fila[2]);
        }

        // Análisis del objetivo: la diferencia entre dividir y fusionar
        System.out.println();
        System.out.println("=== ANÁLISIS: LA DIFERENCIA ENTRE DIVIDIR Y FUSIONAR ===");
        System.out.println("DIVIDIR  → partir el problema (mid y dos llamadas). NO");
        System.out.println("          ordena nada: hasta el último corte, el programa no");
        System.out.println("          hizo ningún trabajo útil de ordenamiento.");
        System.out.println("CORTE    → subarreglo vacío o de 1 elemento: YA está");
        System.out.println("          ordenado. Es lo que hace terminar la recursión.");
        System.out.println("FUSIONAR → recién AL VOLVER se ordena: dos frentes en");
        System.out.println("          paralelo, siempre el menor, de abajo hacia arriba");
        System.out.println("          (1s → 2s → 4s...), nunca se fusiona algo sin ordenar.");
        System.out.println();
        System.out.println("Medido en los tres arreglos de 7 elementos (los contadores");
        System.out.println("estructurales NO dependen de los datos, las comps SÍ):");
        System.out.println("  desordenado {5,3,8,1,9,2,7}: 12 recursivas, 6 divisiones,"
                + " 6 fusiones, 7 cortes, prof 4, 14 comparaciones");
        System.out.println("  ordenado    {1,2,3,4,5,6,7}: 12 recursivas, 6 divisiones,"
                + " 6 fusiones, 7 cortes, prof 4, 11 comparaciones");
        System.out.println("  inverso     {7,6,5,4,3,2,1}: 12 recursivas, 6 divisiones,"
                + " 6 fusiones, 7 cortes, prof 4,  9 comparaciones");
        System.out.println();
        System.out.println("Crecimiento (totales / recursivas / prof / comps) sobre {1..n}:");
        System.out.println("  n=7  → 13/12/4/11  · n=15 → 29/28/5/31");
        System.out.println("  n=31 → 61/60/6/79  · n=63 → 125/124/7/191");
        System.out.println("  llamadas = 2n-1 (LINEAL: n hojas + n-1 internos),");
        System.out.println("  niveles = 1 + ⌈log₂ n⌉ (LOGARÍTMICO) → O(n log n), y las");
        System.out.println("  fusiones suman n elementos por nivel → SIEMPRE, en todo");
        System.out.println("  caso: no existe una entrada que lo haga cuadrático.");
        System.out.println("  (con quicksort y pivote primer elemento sobre ordenado era");
        System.out.println("   n(n-1)/2 = O(n²) y profundidad n: la diferencia es que");
        System.out.println("   acá DIVIDIR SIEMPRE parte en dos mitades, sin importar los");
        System.out.println("   datos.)");
        System.out.println("Conclusión: dividir ordena el TRABAJO (da los niveles);");
        System.out.println("fusionar ORDENA LOS DATOS (hace el trabajo). Por eso");
        System.out.println("mergesort es O(n log n) estable, con O(n) de memoria.");
    }
}

/* ...CORRECCIONES HECHAS POR OPENCODE
====================================================================================
CORRECCIONES HECHAS POR OPENCODE
====================================================================================
[REDUNDANTE] En fusionCorrecta() el chequeo esOrdenadoRango() es de más: si despues es igual a "antes" (y antes quedó ordenado con Arrays.sort), el tramo YA está ordenado por definición.
[REDUNDANTE] El arreglo original se imprime DOS veces en los casos con traza: probar() imprime "Original:" y mergesort() vuelve a imprimir "[MergeSort] original: ...".
====================================================================================
*/

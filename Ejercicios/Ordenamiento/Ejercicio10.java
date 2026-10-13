/*====================================================================================
                                    PROMPT 
 ====================================================================================
 "Implementa en Java un sistema de RANKING DE PUNTAJES: un arreglo de jugadores con
  nombre y puntaje que debe ordenarse de MAYOR A MENOR puntaje.

  1) QUÉ ALGORITMO DE ORDENAMIENTO UTILIZAR:
     MERGESORT (dividir y conquistar) adaptado a orden DESCENDENTE (el mayor puntaje
     primero). Es recursivo, trabaja con un arreglo AUXILIAR de REFERENCIAS Jugador[] 
     para la fusión (O(n) de memoria pero solo punteros, no copias de registros) y 
     rompe empates tomando el de la IZQUIERDA, el ordenamiento es ESTABLE. 
     El orden descendente se logra eligiendo en cada fusión el puntaje MAYOR de los dos 
     frentes.

  2) RAZONAMIENTO:
     a) COMPLEJIDAD: O(n log n) EN TODO CASO (mejor, promedio y peor), contra el
        O(n²) de selección/burbuja/inserción. Un ranking de un sistema real puede
        crecer (clubes, torneos, histórico de partidas) y n² se dispara mientras
        n·log n se mantiene plano:
              n       selección n(n-1)/2    mergesort ≈ n·log₂n
              7              21                    ~20   (medido: 11)
             15             105                    ~59   (medido: 31)
             31             465                   ~155   (medido: 79)
             63           1.953                   ~378   (medido: 191)
           1.000        499.500                 ~10.000
        (columna "medido" = comparaciones EXACTAS de mergesort sobre el ranking ya
         ordenado de mayor a menor, verificadas por ejecución; n log n es la cota)
        MergeSort parte SIEMPRE por la mitad (mid=(inicio+fin)/2), por lo que su
        costo NO depende de los datos: a diferencia de quicksort con pivote primer
        elemento (peor caso O(n²) sobre dato ordenado, ver Ejercicio 7), no existe
        una entrada que lo haga cuadrático.
     b) ESTABILIDAD — CLAVE PARA UN RANKING: en un ranking hay EMPATES de puntaje
        (dos jugadores con 900). Un ordenamiento ESTABLE conserva el orden original
        entre los empatados ("el que llegó primero queda primero"), que es el
        desempate natural cuando el enunciado no define otro criterio. MergeSort es
        estable porque en el empate toma el elemento de la IZQUIERDA. En cambio,
        SELECTION SORT NO ES ESTABLE: al llevar el máximo con un swap, un registro
        empatado puede saltar por delante de otro (verificado como contraste).
     c) EL ARREGLO AUXILIAR COPIA REFERENCIAS, NO REGISTROS: cada entrada del aux
        es un puntero al objeto Jugador (nombre + puntaje NO se copian); se pagan
        n posiciones de memoria, no n copias de registros. Es el precio del O(n log n)
        y es perfectamente aceptable.
     d) POR QUÉ NO LOS DEMÁS: selección/burbuja/inserción son O(n²) e inestables;
        quicksort con pivote primer elemento es O(n²) en dato ordenado (Ejercicio 7)
        e inestable; shellsort es O(n^1.5) promedio y también inestable. Para un
        ranking donde el empate importa y n puede crecer, mergesort es la única
        opción de las vistas que combina O(n log n) + estabilidad.

  3) QUÉ DATOS RECIBE EL PROGRAMA:
     Un arreglo Jugador[] donde cada jugador tiene nombre (String) y puntaje (int,
     puede ser negativo). Todos los arreglos de prueba se definen dentro de main;
     NO se requiere lectura por teclado. Se reciben también los casos nulo, vacío y
     de un solo jugador (casos borde sin excepción). La ordenación es in-place sobre
     las referencias (el tester conserva una copia previa para mostrar el ranking
     original, verificar estabilidad y detectar pérdidas o duplicados).

  4) QUÉ DEBE MOSTRAR POR PANTALLA:
     - El ranking ORIGINAL (copia previa) con posiciones 1º, 2º, 3º...
     - CÓMO SE DIVIDE: cada llamada con profundidad, índice de la mitad y los dos
       subarreglos (izq | der), con sangrado que dibuja el árbol.
     - CUÁNDO LLEGA A LA CONDICIÓN DE CORTE: mensaje explícito en cada subarreglo
       de 0 o 1 jugador ("CORTE: 1 jugador → ya está ordenado").
     - CÓMO SE VAN FUSIONANDO: cada fusión con sus dos mitades, el resultado, las
       comparaciones y la regla de empate (izquierda primero = estable).
     - El ranking ORDENADO de mayor a menor con la posición de cada jugador.
     - Los contadores: llamadas (totales/recursivas), divisiones, fusiones, cortes,
       profundidad máxima y comparaciones.
     - Verificación completa por caso: puntajes == Arrays.sort invertido, orden
       descendente por pares, multiset de registros preservado y ESTABILIDAD
       (el orden de los empatados coincide con el original).
     - Tabla de crecimiento (n = 7, 15, 31, 63) con los contadores exactos y la
       comparación contra el n(n-1)/2 de selección.
     - Tabla resumen de todos los casos y el ANÁLISIS FINAL con la justificación
       de la elección (O(n log n) + estabilidad).

  5) CÓMO VERIFICAR QUE EL RESULTADO SEA CORRECTO:
     Verificaciones en cada caso:
       - Los puntajes ordenados deben ser iguales a Arrays.sort(copia) REVERTIDO.
       - Orden descendente verificado de a pares: arreglo[i].puntaje >= arreglo[i+1].puntaje.
       - Multiset de registros preservado: "nombre|puntaje" ordenado del original
         coincide con el del resultado (nada se perdió ni se duplicó).
       - ESTABILIDAD: para todo par de jugadores empatados, su orden relativo en el
         resultado debe ser el mismo que en el original (índices de origen crecientes).
       - Sin excepciones en los casos borde.
     Contadores EXACTOS {totales, recursivas, divisiones, fusiones, cortes,
     profundidad, comparaciones} esperados (deducidos y verificados por ejecución):
         · {Ana 1200, Pedro 900, Lucia 1500} ejemplo:      5, 4, 2, 2, 3, 3, 2
         · general con empates (n=6):                     11, 10, 5, 5, 6, 4, 10
         · ya ordenado de mayor a menor (n=6):            11, 10, 5, 5, 6, 4, 9
         · orden inverso (n=6):                           11, 10, 5, 5, 6, 4, 7
         · todos iguales (n=4):                            7, 6, 3, 3, 4, 3, 4
         · con negativos (n=4):                            7, 6, 3, 3, 4, 3, 5
         · un solo jugador (n=1):                          1, 0, 0, 0, 1, 1, 0
         · vacío (n=0):                                    1, 0, 0, 0, 1, 1, 0
         · null:                                           0, 0, 0, 0, 0, 0, 0
       (Reglas: llamada recursiva = invocación distinta de la inicial; división =
        llamada con tamaño > 1; corte = tamaño 0 o 1; fusión = cada merge; COMPARACIÓN
        = cada evaluación de "izq.puntaje >= der.puntaje" en la fusión — las copias
        de sobrantes NO cuentan.)
       Para cualquier n ≥ 1: totales = 2n-1, recursivas = 2n-2, divisiones = fusiones
       = n-1, cortes = n, profundidad = 1 + ⌈log₂ n⌉.
     Tabla de crecimiento sobre rankings ya ordenados de mayor a menor:
         n=7  → 13, 12,  6,  6,  7, 4,  11      (selección: 21)
         n=15 → 29, 28, 14, 14, 15, 5,  31      (selección: 105)
         n=31 → 61, 60, 30, 30, 31, 6,  79      (selección: 465)
         n=63 → 125,124,62, 62, 63, 7, 191      (selección: 1.953)
     Plan de pruebas obligatorio: los 9 casos anteriores (ejemplo y general con
       empates CON traza completa; los dos con empates verifican estabilidad).
"
 ====================================================================================
 */
public class Ejercicio10 {

    /**
     * Jugador del ranking: nombre + puntaje. Objeto simple e inmutable (solo
     * lectura), de modo que ordenar solo mueve REFERENCIAS.
     */
    public static class Jugador {

        public final String nombre;
        public final int puntaje;

        public Jugador(String nombre, int puntaje) {
            this.nombre = nombre;
            this.puntaje = puntaje;
        }

        @Override
        public String toString() {
            return nombre + " (" + puntaje + ")";
        }
    }

    /**
     * Estadísticos de la recursión: misma convención que MergeSort.java
     * (llamadas, divisiones, fusiones, cortes, profundidad, comparaciones).
     */
    public static class Estadisticas {

        public int llamadasTotales = 0;
        public int llamadasRecursivas = 0; // totales menos la inicial
        public int divisiones = 0;         // llamadas que parten el rango en dos
        public int fusiones = 0;           // merges ejecutados (uno por división)
        public int casosDeCorte = 0;       // llamadas con 0 o 1 jugador
        public int profundidadMaxima = 0;
        public int comparaciones = 0;

        @Override
        public String toString() {
            return "llamadas recursivas = " + llamadasRecursivas
                    + " (totales " + llamadasTotales + ")"
                    + ", divisiones = " + divisiones
                    + ", fusiones = " + fusiones
                    + ", cortes = " + casosDeCorte
                    + ", profundidad máxima = " + profundidadMaxima
                    + ", comparaciones = " + comparaciones;
        }
    }

    // Fila de la tabla resumen final: [caso, contadores, verificación]
    private static final java.util.List<String[]> TABLA = new java.util.ArrayList<>();

    // ======================================================================
    // EL ALGORITMO ELEGIDO
    // ======================================================================
    /**
     * Ordena un arreglo de jugadores por puntaje en orden descendente usando el
     * algoritmo MergeSort.
     *
     * @param jugadores arreglo a ordenar IN-PLACE por referencias (null se
     * ignora sin excepción); quien llama conserva una copia previa para
     * verificar
     * @param conTraza true = imprime el árbol de divisiones, cortes y fusiones
     * @return estadísticos de la recursión
     */
    public static Estadisticas mergesortRanking(Jugador[] jugadores, boolean conTraza) {
        Estadisticas stats = new Estadisticas();

        // Caso borde: nulo → sin excepción, solo mensaje informativo
        if (jugadores == null) {
            if (conTraza) {
                System.out.println("  [MergeSort] arreglo nulo → nada que ordenar.");
            }
            return stats;
        }

        if (conTraza) {
            System.out.println("  [MergeSort] original: "
                    + java.util.Arrays.toString(jugadores));
        }

        // Arreglo auxiliar de la fusión: guarda REFERENCIAS a Jugador (no copias
        // de registros); se reutiliza en toda la recursión
        Jugador[] auxiliar = new Jugador[jugadores.length];

        // Llamada inicial (NO recursiva): inicio=0, fin=n-1, profundidad=1
        mergesortRec(jugadores, 0, jugadores.length - 1, auxiliar, 1, stats, conTraza);

        // Toda invocación menos la inicial es una llamada recursiva
        stats.llamadasRecursivas = Math.max(0, stats.llamadasTotales - 1);

        if (conTraza) {
            System.out.println("Ranking resultante: " + java.util.Arrays.toString(jugadores));
            System.out.println("  [MergeSort] " + stats);
        }
        return stats;
    }

    /**
     * Llamada recursiva: CONDICIÓN DE CORTE → DIVISIÓN + dos llamadas → FUSIÓN.
     * Se divide hasta el corte (descenso) y RECÍEN AL VOLVER se fusiona
     * (ascenso).
     */
    private static void mergesortRec(Jugador[] a, int low, int high,
            Jugador[] auxiliar, int profundidad, Estadisticas stats, boolean conTraza) {
        stats.llamadasTotales++;
        if (profundidad > stats.profundidadMaxima) {
            stats.profundidadMaxima = profundidad;
        }
        String indent = indentar(profundidad);

        // CONDICIÓN DE CORTE: vacío o 1 jugador → YA está ordenado, no hay nada
        // que hacer. Sin este corte, la mitad de la mitad... nunca terminaría.
        if (low > high) {
            stats.casosDeCorte++;
            if (conTraza) {
                System.out.println(indent + "mergesort(inicio=" + low + ", fin=" + high
                        + ") [prof " + profundidad
                        + "] → CORTE: subarreglo vacío (0 jugadores) → ya está ordenado");
            }
            return;
        }
        if (low == high) {
            stats.casosDeCorte++;
            if (conTraza) {
                System.out.println(indent + "mergesort(inicio=" + low + ", fin=" + high
                        + ") [prof " + profundidad + "] → CORTE: 1 jugador ["
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
     * FUSIÓN DESCENDENTE Y ESTABLE: recorre en paralelo los dos frentes (ambos
     * mitades ya ordenadas de mayor a menor) eligiendo SIEMPRE EL MAYOR puntaje
     * → el elegido queda en su posición final y el resultado es ordenado del
     * doble de tamaño. En EMPATE (mismo puntaje) se toma el de la IZQUIERDA:
     * como la izquierda contiene a los que estaban antes en el arreglo
     * original, el orden relativo de los empatados se conserva → ESTABILIDAD.
     * Primero se copia el rango al arreglo auxiliar para leer de ahí y escribir
     * ordenado en el original.
     */
    private static void fusionar(Jugador[] a, int low, int mid, int high,
            Jugador[] auxiliar, String indent, Estadisticas stats, boolean conTraza) {
        stats.fusiones++;
        int compsAntes = stats.comparaciones;

        // Copia de seguridad del tramo (referencias) antes de pisarlo
        for (int t = low; t <= high; t++) {
            auxiliar[t] = a[t];
        }

        int izq = low;          // frente de la mitad izquierda
        int der = mid + 1;      // frente de la mitad derecha
        int k = low;            // posición donde se escribe el resultado
        while (izq <= mid && der <= high) {
            stats.comparaciones++;   // 1 comparación por cada paso de elección
            // ≥ (no >): el EMPATE lo gana la izquierda → ESTABLE. La comparación
            // sobre puntaje y eligiendo el mayor da el orden DESCENDENTE.
            if (auxiliar[izq].puntaje >= auxiliar[der].puntaje) {
                a[k++] = auxiliar[izq++];   // izquierda mayor o empate → izquierda
            } else {
                a[k++] = auxiliar[der++];   // derecha estrictamente mayor → derecha
            }
        }
        // Copia de los SOBRANTES: no hay comparación, ambos lados están ordenados
        // y todo lo que queda corresponde a su posición definitiva.
        while (izq <= mid) {
            a[k++] = auxiliar[izq++];
        }
        while (der <= high) {
            a[k++] = auxiliar[der++];
        }

        if (conTraza) {
            int comps = stats.comparaciones - compsAntes;
            System.out.println(indent + "  fusiona: izq=" + rango(auxiliar, low, mid)
                    + " der=" + rango(auxiliar, mid + 1, high)
                    + " → " + rango(a, low, high)
                    + "   (" + comps + (comps == 1 ? " comparación)" : " comparaciones)")
                    + "   [empate → izquierda = estable]");
        }
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

    /**
     * Representación de un tramo [low..high] del arreglo.
     */
    private static String rango(Jugador[] a, int low, int high) {
        if (low > high) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        for (int i = low; i <= high; i++) {
            if (i > low) {
                sb.append(", ");
            }
            sb.append(a[i]);
        }
        return sb.append("]").toString();
    }

    // ======================================================================
    // VERIFICACIÓN AUXILIAR Y PRUEBAS
    // ======================================================================
    /**
     * Comprueba que el ranking esté en orden DESCENDENTE de puntaje.
     */
    private static boolean esOrdenDescendente(Jugador[] ranking) {
        for (int i = 0; i + 1 < ranking.length; i++) {
            if (ranking[i].puntaje < ranking[i + 1].puntaje) {
                return false;
            }
        }
        return true;
    }

    /**
     * Lista ordenada de "nombre|puntaje": detecta registros perdidos o
     * duplicados.
     */
    private static java.util.List<String> multiset(Jugador[] jugadores) {
        java.util.List<String> lista = new java.util.ArrayList<>();
        if (jugadores != null) {
            for (Jugador j : jugadores) {
                lista.add(j.nombre + "|" + j.puntaje);
            }
        }
        java.util.Collections.sort(lista);
        return lista;
    }

    /**
     * ESTABILIDAD: para todo par de jugadores empatados, su orden relativo en
     * el resultado debe coincidir con el del original. Se implementa buscando
     * por IDENTIDAD (misma referencia de objeto) la posición de origen de cada
     * jugador ordenado y verificando que, dentro de cada puntaje empatado, esas
     * posiciones de origen sean crecientes.
     */
    private static boolean esEstable(Jugador[] original, Jugador[] ordenado) {
        for (int i = 0; i + 1 < ordenado.length; i++) {
            if (ordenado[i].puntaje != ordenado[i + 1].puntaje) {
                continue; // solo interesan los empates contiguos
            }
            if (posicionOrigen(original, ordenado[i])
                    > posicionOrigen(original, ordenado[i + 1])) {
                return false; // el empatado que estaba antes quedó después
            }
        }
        return true;
    }

    /**
     * Posición que ocupaba el jugador en el arreglo original (búsqueda por
     * identidad).
     */
    private static int posicionOrigen(Jugador[] original, Jugador jugador) {
        for (int i = 0; i < original.length; i++) {
            if (original[i] == jugador) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Puntajes en orden descendente canónico: Arrays.sort de la copia y
     * reverse.
     */
    private static int[] puntajesEsperados(Jugador[] jugadores) {
        int[] esperado = new int[jugadores.length];
        for (int i = 0; i < jugadores.length; i++) {
            esperado[i] = jugadores[i].puntaje;
        }
        java.util.Arrays.sort(esperado);
        for (int i = 0, j = esperado.length - 1; i < j; i++, j--) {
            int t = esperado[i];
            esperado[i] = esperado[j];
            esperado[j] = t;
        }
        return esperado;
    }

    private static int[] puntajes(Jugador[] jugadores) {
        int[] p = new int[jugadores.length];
        for (int i = 0; i < jugadores.length; i++) {
            p[i] = jugadores[i].puntaje;
        }
        return p;
    }

    /**
     * Muestra el ranking con posiciones 1º, 2º, 3º...
     */
    private static void mostrarRanking(String titulo, Jugador[] ranking) {
        System.out.println(titulo);
        if (ranking == null) {
            System.out.println("  (arreglo nulo)");
            return;
        }
        if (ranking.length == 0) {
            System.out.println("  (ranking vacío)");
            return;
        }
        for (int i = 0; i < ranking.length; i++) {
            System.out.println("  " + (i + 1) + "º " + ranking[i]);
        }
    }

    /**
     * Ejecuta un caso completo: ordena, verifica y registra la fila de la
     * tabla.
     *
     * @param esp {totales, recursivas, divisiones, fusiones, cortes,
     * profundidad, comparaciones} esperados, o null para no validarlos
     */
    private static void probar(String descripcion, Jugador[] original,
            int[] esp, boolean conTraza) {
        System.out.println("=== " + descripcion + " ===");

        // Caso borde: nulo → sin excepción
        if (original == null) {
            System.out.println("Original: null");
            Estadisticas s = mergesortRanking(null, conTraza);
            boolean statsOk = statsOk(s, esp);
            System.out.println("Verificación: sin excepción OK ✓, estadísticos "
                    + (statsOk ? "OK ✓" : "ERROR ✗"));
            System.out.println();
            TABLA.add(new String[]{descripcion, contadores(s), statsOk ? "OK" : "ERROR"});
            return;
        }

        mostrarRanking("Original:", original);

        // Copia previa: permite mostrar el ranking de entrada y verificar
        // estabilidad y multiset contra el estado inicial real
        Jugador[] copiaPrevia = original.clone();

        int[] esperado = puntajesEsperados(original);
        java.util.List<String> multisetOriginal = multiset(copiaPrevia);

        Estadisticas s = mergesortRanking(original, conTraza);
        mostrarRanking("Ranking ordenado (mayor a menor):", original);

        // Verificación 1: puntajes == Arrays.sort invertido
        boolean igualQueSort = java.util.Arrays.equals(puntajes(original), esperado);
        // Verificación 2: orden descendente de a pares
        boolean descendente = esOrdenDescendente(original);
        // Verificación 3: multiset de registros preservado (nada perdido ni duplicado)
        boolean registrosOk = multisetOriginal.equals(multiset(original));
        // Verificación 4: ESTABILIDAD — los empatados conservan el orden original
        boolean estable = esEstable(copiaPrevia, original);
        // Verificación 5: contadores exactos
        boolean statsOk = statsOk(s, esp);

        boolean todoOk = igualQueSort && descendente && registrosOk && estable && statsOk;

        System.out.println("Verificación vs Arrays.sort (invertido): "
                + (igualQueSort ? "OK ✓" : "ERROR ✗"));
        System.out.println("Verificación orden descendente: "
                + (descendente ? "OK ✓" : "ERROR ✗"));
        System.out.println("Verificación registros preservados (multiset): "
                + (registrosOk ? "OK ✓" : "ERROR ✗"));
        System.out.println("Verificación ESTABILIDAD (empatados conservan orden): "
                + (estable ? "OK ✓" : "ERROR ✗"));
        if (esp != null) {
            System.out.println("Verificación contadores {tot, rec, div, fus, cortes, prof, comp}"
                    + " (esperado " + contadoresEsp(esp) + "): "
                    + (statsOk ? "OK ✓" : "ERROR ✗"));
        }
        System.out.println(">> LLAMADAS RECURSIVAS: " + s.llamadasRecursivas
                + " | DIVISIONES: " + s.divisiones
                + " | FUSIONES: " + s.fusiones
                + " | CORTES: " + s.casosDeCorte
                + " | PROFUNDIDAD: " + s.profundidadMaxima
                + " | COMPARACIONES: " + s.comparaciones);
        System.out.println();

        TABLA.add(new String[]{descripcion, contadores(s), todoOk ? "OK" : "ERROR"});
    }

    private static String contadores(Estadisticas s) {
        return s.llamadasTotales + ", " + s.llamadasRecursivas + ", "
                + s.divisiones + ", " + s.fusiones + ", "
                + s.casosDeCorte + ", " + s.profundidadMaxima + ", "
                + s.comparaciones;
    }

    private static String contadoresEsp(int[] esp) {
        return esp[0] + ", " + esp[1] + ", " + esp[2] + ", " + esp[3] + ", "
                + esp[4] + ", " + esp[5] + ", " + esp[6];
    }

    private static boolean statsOk(Estadisticas s, int[] esp) {
        return esp != null
                && s.llamadasTotales == esp[0]
                && s.llamadasRecursivas == esp[1]
                && s.divisiones == esp[2]
                && s.fusiones == esp[3]
                && s.casosDeCorte == esp[4]
                && s.profundidadMaxima == esp[5]
                && s.comparaciones == esp[6];
    }

    /**
     * ⌈log₂ n⌉ para n ≥ 1 (niveles del árbol de divisiones).
     */
    private static int ceilLog2(int n) {
        return (int) Math.ceil(Math.log(n) / Math.log(2));
    }

    /**
     * Comparaciones esperadas de todas las fusiones sobre un ranking YA
     * ORDENADO de mayor a menor: la izquierda siempre gana → cada fusión de un
     * tramo con L jugadores a la izquierda cuesta L comparaciones (se agota la
     * izquierda). El split es izq = ⌈n/2⌉, der = ⌊n/2⌋ (mid = (inicio+fin)/2).
     */
    private static int compsFusionOrdenado(int n) {
        if (n <= 1) {
            return 0;
        }
        int izq = (n + 1) / 2;
        int der = n / 2;
        return compsFusionOrdenado(izq) + compsFusionOrdenado(der) + izq;
    }

    /**
     * Crea un ranking de n jugadores ya ordenado de mayor a menor (n, n-1,
     * ...).
     */
    private static Jugador[] rankingOrdenado(int n) {
        Jugador[] r = new Jugador[n];
        for (int i = 0; i < n; i++) {
            r[i] = new Jugador("J" + (i + 1), n - i);
        }
        return r;
    }

    /**
     * Fila silenciosa de la tabla de crecimiento (verifica todo igual).
     */
    private static String[] filaCrecimiento(int n) {
        Jugador[] ranking = rankingOrdenado(n);
        Jugador[] previo = ranking.clone();
        int[] esp = {2 * n - 1, 2 * n - 2, n - 1, n - 1, n,
            1 + ceilLog2(n), compsFusionOrdenado(n)};
        Estadisticas s = mergesortRanking(ranking, false);
        boolean ok = java.util.Arrays.equals(puntajes(ranking),
                puntajesEsperados(previo))
                && esOrdenDescendente(ranking)
                && esEstable(previo, ranking)
                && multiset(previo).equals(multiset(ranking))
                && statsOk(s, esp);
        long seleccion = (long) n * (n - 1) / 2;
        return new String[]{"n=" + n, contadores(s),
            String.valueOf(seleccion), ok ? "OK" : "ERROR"};
    }

    public static void main(String[] args) {
        // CASO 1: el ejemplo exacto del enunciado, con traza completa
        probar("CASO 1: ejemplo del enunciado (con traza)",
                new Jugador[]{
                    new Jugador("Ana", 1200),
                    new Jugador("Pedro", 900),
                    new Jugador("Lucia", 1500)},
                new int[]{5, 4, 2, 2, 3, 3, 2}, true);

        // CASO 2: general con puntajes repetidos (empates), con traza completa
        probar("CASO 2: general con empates (con traza)",
                new Jugador[]{
                    new Jugador("Ana", 1200),
                    new Jugador("Pedro", 900),
                    new Jugador("Lucia", 1500),
                    new Jugador("Juan", 900),
                    new Jugador("Sofia", 1700),
                    new Jugador("Diego", 1100)},
                new int[]{11, 10, 5, 5, 6, 4, 10}, true);

        // CASO 3: ranking ya ordenado de mayor a menor
        probar("CASO 3: ya ordenado de mayor a menor",
                new Jugador[]{
                    new Jugador("Sofia", 1700),
                    new Jugador("Lucia", 1500),
                    new Jugador("Ana", 1200),
                    new Jugador("Diego", 1100),
                    new Jugador("Pedro", 900),
                    new Jugador("Juan", 900)},
                new int[]{11, 10, 5, 5, 6, 4, 9}, false);

        // CASO 4: orden inverso (menor a mayor) — con empates en los extremos
        probar("CASO 4: orden inverso (menor a mayor)",
                new Jugador[]{
                    new Jugador("Juan", 900),
                    new Jugador("Pedro", 900),
                    new Jugador("Diego", 1100),
                    new Jugador("Ana", 1200),
                    new Jugador("Lucia", 1500),
                    new Jugador("Sofia", 1700)},
                new int[]{11, 10, 5, 5, 6, 4, 7}, false);

        // CASO 5: todos los puntajes iguales → máxima presión sobre la estabilidad
        probar("CASO 5: todos los puntajes iguales",
                new Jugador[]{
                    new Jugador("A", 700),
                    new Jugador("B", 700),
                    new Jugador("C", 700),
                    new Jugador("D", 700)},
                new int[]{7, 6, 3, 3, 4, 3, 4}, false);

        // CASO 6: con puntajes negativos
        probar("CASO 6: con puntajes negativos",
                new Jugador[]{
                    new Jugador("Nina", -100),
                    new Jugador("Nico", 50),
                    new Jugador("Nora", -200),
                    new Jugador("Nestor", 0)},
                new int[]{7, 6, 3, 3, 4, 3, 5}, false);

        // Casos borde
        probar("CASO 7: un solo jugador",
                new Jugador[]{new Jugador("Único", 1000)},
                new int[]{1, 0, 0, 0, 1, 1, 0}, false);

        probar("CASO 8: ranking vacío", new Jugador[]{},
                new int[]{1, 0, 0, 0, 1, 1, 0}, false);

        probar("CASO 9: arreglo nulo", null,
                new int[]{0, 0, 0, 0, 0, 0, 0}, false);

        // Tabla de crecimiento: O(n log n) vs el O(n²) de selección
        System.out.println("=== TABLA DE CRECIMIENTO (rankings ya ordenados) ===");
        System.out.println("(contadores: totales / recursivas / divisiones / fusiones"
                + " / cortes / profundidad / comparaciones)");
        System.out.printf("%-6s | %-40s | %-14s | %s%n",
                "n", "MERGESORT (medido)", "SELECCIÓN n²", "VERIF");
        System.out.println("--------------------------------------------------------------------");
        for (int n : new int[]{7, 15, 31, 63}) {
            String[] fila = filaCrecimiento(n);
            System.out.printf("%-6s | %-40s | %-14s | %s%n",
                    fila[0], fila[1], fila[2], fila[3]);
        }

        // Tabla resumen de todos los casos
        System.out.println();
        System.out.println("=== RESUMEN DE VERIFICACIÓN "
                + "(tot, rec, div, fus, cortes, prof, comps) ===");
        System.out.printf("%-46s | %-31s | %s%n", "CASO", "CONTADORES", "VERIF");
        System.out.println("------------------------------------------------------------------");
        for (String[] fila : TABLA) {
            System.out.printf("%-46s | %-31s | %s%n", fila[0], fila[1], fila[2]);
        }

        // Análisis final: por qué mergesort es conveniente para ESTE problema
        System.out.println();
        System.out.println("=== ANÁLISIS: POR QUÉ MERGESORT ES LA ELECCIÓN ADECUADA ===");
        System.out.println("1) O(n log n) EN TODO CASO: medido sobre rankings ordenados,");
        System.out.println("   n=7 → 11 comps, n=15 → 31, n=31 → 79, n=63 → 191,");
        System.out.println("   mientras selección haría 21, 105, 465 y 1.953. Parte");
        System.out.println("   SIEMPRE por la mitad → ningún dato de entrada lo");
        System.out.println("   empeora (quicksort con pivote primer elemento llegaba a");
        System.out.println("   O(n²) sobre dato ordenado, Ejercicio 7).");
        System.out.println("2) ESTABLE: los empatados conservan el orden original");
        System.out.println("   (verificado en los casos 2, 3, 4 y 5: Pedro antes que");
        System.out.println("   Juan; A,B,C,D intactos). Es el desempate natural de un");
        System.out.println("   ranking. Selección NO es estable (un swap puede cambiar");
        System.out.println("   el orden de dos jugadores empatados).");
        System.out.println("3) EL AUXILIAR COPIA REFERENCIAS: O(n) punteros, no copias");
        System.out.println("   de nombre+puntaje → la memoria extra es barata.");
        System.out.println("4) Contrapartida asumida: O(n) de memoria y algo más de");
        System.out.println("   código que un selection simple; para un sistema real");
        System.out.println("   el O(n log n) + estabilidad lo compensan con creces.");
        System.out.println("Conclusión: mergesort combina el mejor orden asintótico de");
        System.out.println("los algoritmos vistos con la estabilidad que un ranking de");
        System.out.println("puntajes con empates necesita → estrategia adecuada.");
    }
}

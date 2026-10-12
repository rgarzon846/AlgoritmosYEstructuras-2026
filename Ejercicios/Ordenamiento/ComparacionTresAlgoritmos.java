/*====================================================================================
                                    PROMPT 
 ====================================================================================
 "Implementa en Java un programa con TRES métodos de ordenamiento sobre arreglos de
 enteros:

     bubbleSort(int[] array)
     selectionSort(int[] array)
     insertionSort(int[] array)

 Los tres deben ordenar el MISMO arreglo (los mismos datos de partida: cada uno
 sobre su propia copia idéntica) y el programa debe contar cuántas comparaciones
 realiza cada algoritmo y cuántos INTERCAMBIOS (bubble y selection) o
 DESPLAZAMIENTOS (insertion) realiza cada uno.

 1) QUÉ ALGORITMOS DE ORDENAMIENTO UTILIZAR:
    - BUBBLE SORT en bubbleSort(...): compara PARES ADYACENTES (i, i+1) y los
      intercambia si están en orden incorrecto (orden ascendente), con bucle
      interno acortado una posición por pasada y corte por bandera.
    - SELECTION SORT en selectionSort(...): en cada pasada busca DIRECTAMENTE el
      menor de la región sin ordenar y lo lleva a su posición con UN intercambio.
    - INSERTION SORT en insertionSort(...): mantiene un PREFIJO ORDENADO a la
      izquierda; para cada clave recorre ese prefijo de derecha a izquierda
      DESPLAZANDO hacia la derecha los elementos mayores que la clave y la
      inserta en el hueco que queda, con corte anticipado.

 2) RAZONAMIENTO
    a) Bubble compara solo VECINOS (información local): el orden se construye con
       muchos intercambios chicos; un elemento puede "burbujear" varias posiciones
       hasta llegar a su sitio. Con bandera: si una pasada no realiza ningún
       intercambio, el arreglo ya está ordenado → mejor caso n-1 comparaciones y
       0 intercambios. Con el bucle interno acortado, el peor caso es
       n(n-1)/2 comparaciones y n(n-1)/2 intercambios.
    b) Selection no mira vecinos: recorre TODA la región sin ordenar comparando
       contra un candidato mínimo (búsqueda directa del menor) y coloca ese menor
       en su sitio definitivo con UN intercambio por pasada (a lo sumo n-1 en todo
       el algoritmo). Como debe examinar toda la región para CONFIRMAR que no queda
       un menor, siempre realiza n(n-1)/2 comparaciones y NO puede cortar antes,
       ni siquiera con el arreglo ya ordenado.
    c) Insertion INSERTA CADA CLAVE EN SU LUGAR dentro del prefijo ya ordenado: el
       bucle interno se DETIENE apenas encuentra un elemento menor o igual que la
       clave (corte anticipado) y cada corrección se paga con DESPLAZAMIENTOS: un
       corrimiento de UNA posición a la derecha es UNA sola escritura
       (array[j+1] = array[j]), mientras que un swap son TRES escrituras. Los
       desplazamientos son exactamente las INVERSIONES del arreglo → con poca
       inversión hace poco trabajo: es un algoritmo ADAPTATIVO.
    d) Sostenimiento numérico obligatorio (los contadores lo verifican), mismos
       arreglos en los tres algoritmos, formato comparaciones/intercambios-o-
       desplazamientos:
       - {5,3,8,3,1,9,2} general (n=7): bubble 21/12 · selection 21/5 ·
         insertion 16/12. Bubble y selection agotan los 21 pares = n(n-1)/2
         porque en TODAS las pasadas hubo trabajo (ninguna bandera cortó);
         selection cambia 5 veces (una por pasada donde el menor no estaba en
         su sitio); insertion hace 16 comparaciones porque el corte anticipado
         evita recorrer todo el prefijo cuando la clave ya está en su lugar
         (p. ej. al insertar 8 corta en la primera comparación).
       - {1,2,3,4,5,6} ordenado (n=6): bubble 5/0 (la bandera corta tras la
         pasada 1: n-1 comparaciones) · selection 15/0 (siempre n(n-1)/2, no
         puede detectar que ya está ordenado) · insertion 5/0 (cada clave corta
         en su 1ª comparación: n-1 = lo mínimo posible).
       - {6,5,4,3,2,1} inverso (n=6): bubble 15/15 · selection 15/3 ·
         insertion 15/15. Selection paga 3 intercambios donde bubble e
         insertion necesitan 15: es la que MINIMIZA intercambios.
       - {1,2,3,5,4,6,7} casi ordenado (n=7, UNA sola inversión (5,4)):
         bubble 11/1 (pasada 1: 6 comparaciones + 1 intercambio; pasada 2:
         5 comparaciones sin intercambio → bandera) · selection 21/1 (NO
         aprovecha el orden: mismas 21 comparaciones de siempre, n(n-1)/2) ·
         insertion 7/1 (solo la clave 4 desplaza a 5; el resto corta en la 1ª
         comparación). → INSERTION GANA ACÁ: 7 comparaciones contra 11 y 21.
       - {-3,0,-1,7} negativos (n=4): bubble 5/1 · selection 6/1 ·
         insertion 4/1.
       - {4,4,4} iguales (n=3): bubble 2/0 · selection 3/0 · insertion 2/0
         (los empates cortan: > no es cierto para claves iguales).
       - {42} / {} / null borde: 0/0 en los tres.
    Conclusión: bubble ahorra comparaciones cuando el arreglo ya está casi
    ordenado pero gasta MUCHOS intercambios; selection hace siempre el máximo de
    comparaciones pero MINIMIZA los intercambios; insertion es la que mejor
    aprovecha el orden existente (mejor caso en arreglos casi ordenados) y cada
    corrección le cuesta UN desplazamiento barato en lugar de un swap.

 3) QUÉ DATOS RECIBE EL PROGRAMA:
    Un arreglo de enteros (int[]) con positivos, negativos, ceros y valores
    repetidos, de cualquier tamaño (vacío, de 1 elemento o nulo). Como los tres
    ordenamientos son in-place y alteran el arreglo, en main se parte de un
    arreglo original y se entrega una COPIA IDÉNTICA (clone) a cada algoritmo, de
    modo que los tres ordenan los MISMOS datos. Las pruebas se definen dentro de
    main; NO se requiere lectura por teclado.

 4) QUÉ DEBE MOSTRAR POR PANTALLA:
    - El arreglo original de cada caso.
    - Para cada algoritmo su TRAZA POR PASADA: bubble (arreglo tras cada pasada y
      cuántos intercambios entre vecinos hizo, más el corte por bandera);
      selection (menor encontrado, su posición y swap o "ya estaba en su
      posición"); insertion (clave, qué elementos desplazó, por qué cortó el
      recorrido y el arreglo tras insertarla).
    - El arreglo resultante de cada algoritmo y sus contadores: comparaciones,
      intercambios o desplazamientos y pasadas.
    - La verificación de que los tres resultados son IDÉNTICOS entre sí, iguales a
      Arrays.sort(original) y están en orden no decreciente, más la verificación
      de los contadores EXACTOS de cada algoritmo.
    - Una TABLA COMPARATIVA FINAL (por caso: comparaciones e intercambios/
      desplazamientos de cada algoritmo + verificación).
    - Un ANÁLISIS FINAL que compare las tres estrategias (comparar VECINOS vs
      buscar el MENOR vs INSERTAR en su lugar) con los números de los casos, y
      concluya que insertion sort es el mejor en arreglos casi ordenados.

 5) CÓMO VERIFICAR QUE EL RESULTADO SEA CORRECTO:
    Convención de conteo (obligatoria para que los números sean reproducibles):
      - BUBBLE: COMPARACIÓN = cada evaluación de array[i] > array[i+1] (el bucle
        interno se acorta una posición por pasada); INTERCAMBIO = cada swap entre
        vecinos; corta por bandera cuando una pasada no intercambia.
      - SELECTION: COMPARACIÓN = cada evaluación de array[j] < array[posMin];
        INTERCAMBIO = solo cuando posMin != k (si el menor ya está en su posición
        NO se cuenta ni se realiza el intercambio).
      - INSERTION: COMPARACIÓN = cada evaluación de array[j] > clave (también la
        que da FALSO y produce el corte anticipado; cuando j < 0 la condición se
        acorta por j >= 0 y NO se cuenta); DESPLAZAMIENTO = cada escritura
        array[j+1] = array[j] (el corrimiento de UNA posición a la derecha); la
        escritura final de la clave en el hueco NO cuenta como desplazamiento.
    Verificaciones:
      - Los tres algoritmos reciben una copia del MISMO original; los tres
        resultados deben ser iguales entre sí (Arrays.equals), iguales a
        Arrays.sort(original) y estar en orden no decreciente
        (arreglo[i] <= arreglo[i+1]).
      - Contadores EXACTOS por caso (formato comparaciones/intercambios-o-
        desplazamientos):
          · {5,3,8,3,1,9,2}  general      : bubble 21/12 · selection 21/5  · insertion 16/12
          · {1,2,3,4,5,6}    ordenado     : bubble 5/0   · selection 15/0  · insertion 5/0
          · {6,5,4,3,2,1}    inverso      : bubble 15/15 · selection 15/3  · insertion 15/15
          · {1,2,3,5,4,6,7}  casi ordenado: bubble 11/1  · selection 21/1  · insertion 7/1
          · {-3,0,-1,7}      negativos    : bubble 5/1   · selection 6/1   · insertion 4/1
          · {4,4,4}          iguales      : bubble 2/0   · selection 3/0   · insertion 2/0
          · {42} / {} / null  borde       : 0/0 en los tres (sin excepción)
      (Los contadores de bubble/selection coinciden EXACTAMENTE con los de
      ComparacionBubbleSelection.java para los mismos arreglos, porque se usan
      las mismas reglas: bucle interno acortado + bandera en bubble; selection
      sin intercambio cuando el menor ya está en su posición.)
      Verificación A MANO de insertion en 3 casos (antes de fijarlos):
        · ordenado {1,2,3,4,5,6}: cada clave se corta en su 1ª comparación →
          comparaciones = 1+1+1+1+1 = 5, desplazamientos = 0.
        · casi ordenado {1,2,3,5,4,6,7}: solo la clave 4 desplaza a la derecha
          al 5 → 1 desplazamiento; comparaciones = 1+1+1+(2)+1+1 = 7.
        · inverso {6,5,4,3,2,1}: la clave de la posición i desplaza i-1
          elementos → desplazamientos = 1+2+3+4+5 = 15 y comparaciones = 15
          (nunca corta: siempre llega al inicio).
    Plan de pruebas obligatorio (9 casos):
      CASO 1 general con repetidos: {5,3,8,3,1,9,2} → {1,2,3,3,5,8,9}
      CASO 2 ya ordenado (mejor caso de bubble e insertion): {1,2,3,4,5,6} → igual
      CASO 3 orden inverso (peor caso de intercambios): {6,5,4,3,2,1} → {1,2,3,4,5,6}
      CASO 4 casi ordenado (ejercicio 3, mejor caso de insertion): {1,2,3,5,4,6,7} → igual
      CASO 5 con negativos: {-3,0,-1,7} → {-3,-1,0,7}
      CASO 6 todos iguales: {4,4,4} → {4,4,4}
      CASO 7 un solo elemento: {42} → {42}
      CASO 8 arreglo vacío: {} → {}
      CASO 9 arreglo nulo: null → los tres métodos sin excepción, mensaje informativo
"
 ====================================================================================
 */
public class ComparacionTresAlgoritmos {

    /**
     * Contadores compartidos por los tres algoritmos.
     *
     * <p>
     * Cada algoritmo usa los contadores que LE CORRESPONDEN: bubble y selection
     * cuentan INTERCAMBIOS (swaps); insertion cuenta DESPLAZAMIENTOS
     * (corrimientos de una posición a la derecha). Todos comparten el contador
     * de comparaciones y el de pasadas.
     */
    public static class Estadisticas {

        /**
         * Nombre del algoritmo (para la traza y la tabla final).
         */
        public final String algoritmo;
        /**
         * true → el algoritmo cuenta desplazamientos (insertion) en vez de
         * intercambios.
         */
        public final boolean cuentaDesplazamientos;
        public int pasadas = 0;
        public int comparaciones = 0;
        public int intercambios = 0;     // bubble y selection
        public int desplazamientos = 0;  // insertion
        public boolean corteBandera = false;

        public Estadisticas(String algoritmo, boolean cuentaDesplazamientos) {
            this.algoritmo = algoritmo;
            this.cuentaDesplazamientos = cuentaDesplazamientos;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder(algoritmo);
            sb.append(": comparaciones = ").append(comparaciones);
            if (cuentaDesplazamientos) {
                sb.append(", desplazamientos = ").append(desplazamientos);
            } else {
                sb.append(", intercambios = ").append(intercambios);
            }
            sb.append(", pasadas = ").append(pasadas);
            if (corteBandera) {
                sb.append(", corte por bandera = SÍ");
            }
            return sb.toString();
        }
    }

    // Fila de la tabla comparativa final:
    // [caso, bComp, bSwap, sComp, sSwap, iComp, iDesp, OK]
    private static final java.util.List<String[]> TABLA = new java.util.ArrayList<>();

    /**
     * BUBBLE SORT — ordena comparando PARES ADYACENTES (estrategia: VECINOS).
     *
     * <p>
     * Razonamiento: en cada comparación (i, i+1), si el de la izquierda es
     * mayor se intercambian → el mayor pasa un lugar hacia la derecha. El orden
     * se construye con muchos intercambios chicos usando solo información LOCAL
     * (los vecinos). El bucle interno se acorta una posición por pasada porque
     * los k últimos lugares ya quedaron fijos. Con bandera: si una pasada no
     * intercambia nada, el arreglo ya está ordenado → mejor caso n-1
     * comparaciones y 0 intercambios (O(n)). Rinde bien cuando el arreglo está
     * casi ordenado; en el peor caso hace n(n-1)/2 comparaciones y hasta
     * n(n-1)/2 intercambios (el máximo de escrituras de los tres algoritmos).
     *
     * <p>
     * Convención de conteo: una comparación por cada evaluación de
     * {@code array[i] > array[i + 1]}; un intercambio por cada swap entre
     * vecinos.
     *
     * @param array arreglo a ordenar in-place (null se ignora sin excepción)
     * @return contadores de comparaciones, intercambios y pasadas
     */
    public static Estadisticas bubbleSort(int[] array) {
        Estadisticas stats = new Estadisticas("Bubble Sort", false);

        // Caso borde: nulo → sin excepción, solo mensaje informativo
        if (array == null) {
            System.out.println("  [Bubble] arreglo nulo → nada que ordenar.");
            return stats;
        }

        System.out.println("  [Bubble] inicio: " + java.util.Arrays.toString(array));

        // Caso borde: vacío o un solo elemento → ya está ordenado, 0 pasadas
        int n = array.length;
        if (n < 2) {
            System.out.println("  [Bubble] menos de 2 elementos → ya está ordenado (0 pasadas).");
            return stats;
        }

        for (int pasada = 0; pasada < n - 1; pasada++) {
            stats.pasadas++;
            boolean huboIntercambios = false;
            int swapsPasada = 0;
            int compsPasada = stats.comparaciones;

            // Invariante: los k últimos lugares ya están fijos → el bucle interno
            // se acorta una posición por pasada
            for (int i = 0; i < n - 1 - pasada; i++) {
                stats.comparaciones++;
                if (array[i] > array[i + 1]) {
                    // Swap entre VECINOS: el mayor de los dos pasa un lugar hacia
                    // la derecha (el orden se construye con intercambios locales)
                    int temporal = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temporal;
                    stats.intercambios++;
                    swapsPasada++;
                    huboIntercambios = true;
                }
            }

            int compsPasadaTotal = stats.comparaciones - compsPasada;
            System.out.println("    Pasada " + stats.pasadas + ": "
                    + java.util.Arrays.toString(array)
                    + "  (" + swapsPasada + (swapsPasada == 1 ? " intercambio" : " intercambios")
                    + " entre vecinos, " + compsPasadaTotal
                    + (compsPasadaTotal == 1 ? " comparación)" : " comparaciones)"));

            // Bandera: si una pasada no intercambió, ya está ordenado → mejor caso O(n)
            if (!huboIntercambios) {
                stats.corteBandera = true;
                System.out.println("    Sin intercambios → ordenado, CORTO por bandera.");
                break;
            }
        }

        System.out.println("  [Bubble] resultado: " + java.util.Arrays.toString(array));
        System.out.println("  [Bubble] " + stats);
        return stats;
    }

    /**
     * SELECTION SORT — ordena buscando directamente el menor de la región
     * (estrategia: BUSCAR EL MENOR).
     *
     * <p>
     * Razonamiento: en cada pasada se recorre TODA la región sin ordenar
     * comparando contra un candidato mínimo (búsqueda directa del menor, no de
     * vecinos) y el menor se lleva a su posición definitiva con UN intercambio
     * (a lo sumo n-1 en total). Como debe examinar toda la región para
     * CONFIRMAR que no queda un menor, siempre realiza n(n-1)/2 comparaciones y
     * NO puede cortar antes, ni con el arreglo ya ordenado. Minimiza
     * intercambios a cambio de no poder aprovechar un arreglo casi ordenado: es
     * el algoritmo que MENOS escribe (1 swap por pasada como máximo) pero el
     * que SIEMPRE compara lo mismo.
     *
     * <p>
     * Convención de conteo: una comparación por cada evaluación de
     * {@code array[j] < array[posMin]}; un intercambio SOLO cuando el menor no
     * estaba ya en su posición (posMin != k).
     *
     * @param array arreglo a ordenar in-place (null se ignora sin excepción)
     * @return contadores de comparaciones, intercambios y pasadas
     */
    public static Estadisticas selectionSort(int[] array) {
        Estadisticas stats = new Estadisticas("Selection Sort", false);

        // Caso borde: nulo → sin excepción, solo mensaje informativo
        if (array == null) {
            System.out.println("  [Selection] arreglo nulo → nada que ordenar.");
            return stats;
        }

        System.out.println("  [Selection] inicio: " + java.util.Arrays.toString(array));

        // Caso borde: vacío o un solo elemento → ya está ordenado, 0 pasadas
        int n = array.length;
        if (n < 2) {
            System.out.println("  [Selection] menos de 2 elementos → ya está ordenado (0 pasadas).");
            return stats;
        }

        // n-1 pasadas: cada una fija el menor de la región [k .. n-1] en la posición k
        for (int k = 0; k < n - 1; k++) {
            stats.pasadas++;
            int compsPasada = stats.comparaciones;

            // Búsqueda DIRECTA del menor: se recorre TODA la región sin ordenar
            // comparando contra el candidato mínimo (no se miran vecinos)
            int posMin = k;
            for (int j = k + 1; j < n; j++) {
                stats.comparaciones++;
                if (array[j] < array[posMin]) {
                    posMin = j;
                }
            }

            String accion;
            if (posMin != k) {
                // UN intercambio por pasada coloca el menor en su sitio definitivo
                int temporal = array[k];
                array[k] = array[posMin];
                array[posMin] = temporal;
                stats.intercambios++;
                accion = "swap " + posMin + " ↔ " + k;
            } else {
                // El menor ya estaba en su posición → NO se cuenta intercambio
                accion = "ya estaba en su posición, sin swap";
            }

            int compsPasadaTotal = stats.comparaciones - compsPasada;
            System.out.println("    Pasada " + stats.pasadas + ": menor = " + array[k]
                    + " (posición " + posMin + ") → " + accion
                    + "  (" + compsPasadaTotal
                    + (compsPasadaTotal == 1 ? " comparación)" : " comparaciones)") + "  "
                    + java.util.Arrays.toString(array));
        }

        System.out.println("  [Selection] resultado: " + java.util.Arrays.toString(array));
        System.out.println("  [Selection] " + stats);
        return stats;
    }

    /**
     * INSERTION SORT — ordena INSERTANDO cada elemento en el prefijo ordenado
     * (estrategia: INSERTAR EN SU LUGAR).
     *
     * <p>
     * Razonamiento: el prefijo [0..i-1] ya está ordenado; para insertar la
     * clave array[i] se recorre ese prefijo de DERECHA a IZQUIERDA desplazando
     * hacia la DERECHA los elementos mayores que la clave, y la clave se
     * escribe en el hueco que queda. El bucle se CORTA apenas encuentra un
     * elemento menor o igual (corte anticipado), por eso con arreglos casi
     * ordenados cada clave está ya casi en su lugar y el trabajo total se
     * acerca a O(n): es ADAPTATIVO.
     *
     * <p>
     * <b>Por qué el contador que le corresponde son los DESPLAZAMIENTOS:</b> a
     * diferencia de bubble y selection, insertion NUNCA hace swaps: corrige el
     * orden desplazando un elemento UNA posición a la derecha
     * ({@code array[j + 1] = array[j]}), que es UNA sola escritura (un swap son
     * tres). El "trabajo de escritura" de este algoritmo se mide, por lo tanto,
     * contando esos corrimientos. Además, los desplazamientos equivalen a las
     * INVERSIONES del arreglo: casi ordenado = pocos desplazamientos, inverso =
     * n(n-1)/2 desplazamientos. Se cuentan también las comparaciones para poder
     * compararlo con bubble y selection con la MISMA métrica.
     *
     * <p>
     * Casos: ordenado → n-1 comparaciones / 0 desplazamientos (O(n)); inverso →
     * n(n-1)/2 de ambos (O(n²)). Rinde mejor que los otros dos en arreglos casi
     * ordenados (ver CASO 4: 7/1 contra 11/1 de bubble y 21/1 de selection).
     *
     * <p>
     * Convención de conteo: una comparación por cada evaluación de
     * {@code array[j] > clave} (también la falsa que produce el corte; cuando j < 0 la condición se acorta por j
     * >= 0 y no se cuenta); un desplazamiento por cada
     * {@code array[j + 1] = array[j]}. La escritura final de la clave NO
     * cuenta.
     *
     * @param array arreglo a ordenar in-place (null se ignora sin excepción)
     * @return contadores de comparaciones y desplazamientos
     */
    public static Estadisticas insertionSort(int[] array) {
        Estadisticas stats = new Estadisticas("Insertion Sort", true);

        // Caso borde: nulo → sin excepción, solo mensaje informativo
        if (array == null) {
            System.out.println("  [Insertion] arreglo nulo → nada que ordenar.");
            return stats;
        }

        System.out.println("  [Insertion] inicio: " + java.util.Arrays.toString(array));

        // Caso borde: vacío o un solo elemento → ya está ordenado, 0 pasadas
        int n = array.length;
        if (n < 2) {
            System.out.println("  [Insertion] menos de 2 elementos → ya está ordenado (0 pasadas).");
            return stats;
        }

        // El prefijo [0..i-1] ya está ordenado; se inserta array[i] en su lugar
        for (int i = 1; i < n; i++) {
            stats.pasadas++;
            int clave = array[i];
            int j = i - 1;
            int compPaso = 0;
            int despPaso = 0;
            StringBuilder traza = new StringBuilder();

            // Bucle de DESPLAZAMIENTO: recorre el prefijo ordenado de derecha a
            // izquierda y se CORTA apenas encuentra un elemento <= clave.
            // Este corte anticipado es lo que hace que, con datos casi ordenados,
            // el recorrido sea cortísimo y el algoritmo se acerque a O(n).
            while (j >= 0 && array[j] > clave) {
                stats.comparaciones++;
                compPaso++;
                if (traza.length() > 0) {
                    traza.append("; ");
                }
                traza.append(array[j]).append(" > ").append(clave)
                        .append(" → desplaza a la derecha");

                // Desplazamiento = UNA sola escritura (no es un swap de 3 escrituras)
                array[j + 1] = array[j];
                stats.desplazamientos++;
                despPaso++;
                j--;
            }

            if (j >= 0) {
                // La condición array[j] > clave se evaluó y dio FALSO → es una
                // comparación que corta el recorrido (clave ya en su lugar)
                stats.comparaciones++;
                compPaso++;
                if (traza.length() > 0) {
                    traza.append("; ");
                }
                traza.append(array[j]).append(" <= ").append(clave)
                        .append(" → corte anticipado");
            } else {
                // Llegó al inicio del arreglo: la clave es la menor hasta ahora
                // (la condición se acortó por j >= 0 → NO se cuenta comparación)
                if (traza.length() > 0) {
                    traza.append("; ");
                }
                traza.append("llegó al inicio (j < 0)");
            }

            // Inserción de la clave en el hueco (NO cuenta como desplazamiento)
            array[j + 1] = clave;

            System.out.println("    Pasada " + stats.pasadas + " (i = " + i
                    + ", clave = " + clave + "): " + traza);
            String compTxt = compPaso + (compPaso == 1 ? " comparación" : " comparaciones");
            String despTxt = despPaso + (despPaso == 1 ? " desplazamiento" : " desplazamientos");
            if (despPaso == 0) {
                System.out.println("      → ya estaba en su lugar (0 desplazamientos): "
                        + java.util.Arrays.toString(array) + "  [" + compTxt + "]");
            } else {
                System.out.println("      → clave insertada en pos " + (j + 1) + ": "
                        + java.util.Arrays.toString(array) + "  [" + compTxt + ", " + despTxt + "]");
            }
        }

        System.out.println("  [Insertion] resultado: " + java.util.Arrays.toString(array));
        System.out.println("  [Insertion] " + stats);
        return stats;
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
     * Ejecuta un caso: entrega la MISMA copia del original a cada algoritmo
     * (los tres parten de los mismos datos), ordena el esperado con Arrays.sort
     * y verifica igualdad con Arrays.sort, orden no decreciente, igualdad entre
     * los tres resultados y contadores exactos.
     *
     * @param expBubble {comparaciones, intercambios} esperados de bubble o null
     * @param expSelection {comparaciones, intercambios} esperados de selection
     * o null
     * @param expInsertion {comparaciones, desplazamientos} esperados de
     * insertion o null
     */
    private static void probar(String descripcion, int[] original,
            int[] expBubble, int[] expSelection, int[] expInsertion) {
        System.out.println("=== " + descripcion + " ===");

        // Caso borde: nulo → los tres métodos deben funcionar sin excepción
        if (original == null) {
            System.out.println("Original: null");
            Estadisticas b = bubbleSort(null);
            Estadisticas s = selectionSort(null);
            Estadisticas i = insertionSort(null);
            boolean contOk = b.comparaciones == 0 && b.intercambios == 0
                    && s.comparaciones == 0 && s.intercambios == 0
                    && i.comparaciones == 0 && i.desplazamientos == 0;
            System.out.println("Verificación: los tres sin excepción → OK ✓");
            System.out.println("Verificación contadores (esperado 0/0 en los tres): "
                    + (contOk ? "OK ✓" : "ERROR ✗"));
            System.out.println();
            TABLA.add(new String[]{descripcion,
                String.valueOf(b.comparaciones), String.valueOf(b.intercambios),
                String.valueOf(s.comparaciones), String.valueOf(s.intercambios),
                String.valueOf(i.comparaciones), String.valueOf(i.desplazamientos),
                contOk ? "OK" : "ERROR"});
            return;
        }

        System.out.println("Original: " + java.util.Arrays.toString(original));

        // Los tres parten de los MISMOS datos: una copia idéntica por algoritmo
        int[] paraBubble = original.clone();
        int[] paraSelection = original.clone();
        int[] paraInsertion = original.clone();

        // Resultado esperado: copia ordenada con Arrays.sort
        int[] esperado = original.clone();
        java.util.Arrays.sort(esperado);

        System.out.println("--- BUBBLE SORT (compara vecinos) ---");
        Estadisticas b = bubbleSort(paraBubble);
        System.out.println("--- SELECTION SORT (busca el menor) ---");
        Estadisticas s = selectionSort(paraSelection);
        System.out.println("--- INSERTION SORT (inserta en su lugar) ---");
        Estadisticas i = insertionSort(paraInsertion);

        // Verificación de resultados: los tres idénticos entre sí y vs Arrays.sort
        boolean mismosResultados = java.util.Arrays.equals(paraBubble, paraSelection)
                && java.util.Arrays.equals(paraBubble, paraInsertion);
        boolean igualQueSort = java.util.Arrays.equals(paraBubble, esperado)
                && java.util.Arrays.equals(paraSelection, esperado)
                && java.util.Arrays.equals(paraInsertion, esperado);
        boolean ordenado = esOrdenado(paraBubble) && esOrdenado(paraSelection)
                && esOrdenado(paraInsertion);

        // Verificación de contadores exactos (si el caso los define)
        boolean contBubbleOk = (expBubble == null)
                || (b.comparaciones == expBubble[0] && b.intercambios == expBubble[1]);
        boolean contSelectionOk = (expSelection == null)
                || (s.comparaciones == expSelection[0] && s.intercambios == expSelection[1]);
        boolean contInsertionOk = (expInsertion == null)
                || (i.comparaciones == expInsertion[0] && i.desplazamientos == expInsertion[1]);

        boolean todoOk = mismosResultados && igualQueSort && ordenado
                && contBubbleOk && contSelectionOk && contInsertionOk;

        System.out.println("Verificación resultados idénticos entre los tres: "
                + (mismosResultados ? "OK ✓" : "ERROR ✗"));
        System.out.println("Verificación vs Arrays.sort: "
                + (igualQueSort ? "OK ✓" : "ERROR ✗ (esperado "
                        + java.util.Arrays.toString(esperado) + ")"));
        System.out.println("Verificación orden no decreciente: "
                + (ordenado ? "OK ✓" : "ERROR ✗"));
        if (expBubble != null || expSelection != null || expInsertion != null) {
            System.out.println("Verificación contadores (esperado bubble "
                    + (expBubble != null ? expBubble[0] + "/" + expBubble[1] : "-")
                    + ", selection "
                    + (expSelection != null ? expSelection[0] + "/" + expSelection[1] : "-")
                    + ", insertion "
                    + (expInsertion != null ? expInsertion[0] + "/" + expInsertion[1] : "-")
                    + "): " + (contBubbleOk && contSelectionOk && contInsertionOk
                            ? "OK ✓" : "ERROR ✗"));
        }
        System.out.println();

        TABLA.add(new String[]{descripcion,
            String.valueOf(b.comparaciones), String.valueOf(b.intercambios),
            String.valueOf(s.comparaciones), String.valueOf(s.intercambios),
            String.valueOf(i.comparaciones), String.valueOf(i.desplazamientos),
            todoOk ? "OK" : "ERROR"});
    }

    public static void main(String[] args) {
        // Caso 1: caso general con elementos repetidos
        probar("CASO 1: general con repetidos", new int[]{5, 3, 8, 3, 1, 9, 2},
                new int[]{21, 12}, new int[]{21, 5}, new int[]{16, 12});

        // Caso 2: ya ordenado → mejor caso de bubble (bandera) e insertion
        probar("CASO 2: ya ordenado", new int[]{1, 2, 3, 4, 5, 6},
                new int[]{5, 0}, new int[]{15, 0}, new int[]{5, 0});

        // Caso 3: orden inverso → peor caso de intercambios en bubble e insertion
        probar("CASO 3: orden inverso", new int[]{6, 5, 4, 3, 2, 1},
                new int[]{15, 15}, new int[]{15, 3}, new int[]{15, 15});

        // Caso 4: casi ordenado (ejercicio 3) → mejor caso de insertion
        probar("CASO 4: casi ordenado (ejercicio 3)", new int[]{1, 2, 3, 5, 4, 6, 7},
                new int[]{11, 1}, new int[]{21, 1}, new int[]{7, 1});

        // Caso 5: con negativos
        probar("CASO 5: con negativos", new int[]{-3, 0, -1, 7},
                new int[]{5, 1}, new int[]{6, 1}, new int[]{4, 1});

        // Caso 6: todos iguales (ningún intercambio ni desplazamiento)
        probar("CASO 6: todos iguales", new int[]{4, 4, 4},
                new int[]{2, 0}, new int[]{3, 0}, new int[]{2, 0});

        // Caso 7: un solo elemento
        probar("CASO 7: un solo elemento", new int[]{42},
                new int[]{0, 0}, new int[]{0, 0}, new int[]{0, 0});

        // Caso 8: arreglo vacío
        probar("CASO 8: arreglo vacío", new int[]{},
                new int[]{0, 0}, new int[]{0, 0}, new int[]{0, 0});

        // Caso 9: arreglo nulo → sin excepción en ninguno de los tres
        probar("CASO 9: arreglo nulo", null,
                new int[]{0, 0}, new int[]{0, 0}, new int[]{0, 0});

        // Tabla comparativa final: comparaciones e intercambios/desplazamientos
        System.out.println("=== TABLA COMPARATIVA (comparaciones / intercambios-o-desplazamientos) ===");
        System.out.printf("%-36s | %11s | %11s | %11s | %s%n",
                "CASO", "BUBBLE", "SELECTION", "INSERTION", "VERIFICACIÓN");
        System.out.println("------------------------------------------------------------------------"
                + "------");
        for (String[] fila : TABLA) {
            System.out.printf("%-36s | %5s / %-4s | %5s / %-4s | %5s / %-4s | %s%n",
                    fila[0], fila[1], fila[2], fila[3], fila[4], fila[5], fila[6], fila[7]);
        }

        // Análisis: comparar vecinos vs buscar el menor vs insertar en su lugar
        System.out.println();
        System.out.println("=== ANÁLISIS FINAL: COMPARAR VECINOS vs BUSCAR EL MENOR vs INSERTAR EN SU LUGAR ===");
        System.out.println("BUBBLE (compara VECINOS): información local; el orden se");
        System.out.println("construye con muchos intercambios chicos. Con bandera detecta el");
        System.out.println("orden con n-1 comparaciones (caso ordenado: 5 comp / 0 swaps) y en");
        System.out.println("el casi ordenado corta en la pasada 2 (11 comp / 1 swap), pero en");
        System.out.println("el peor caso intercambia n(n-1)/2 veces (inverso: 15/15).");
        System.out.println("SELECTION (busca el MENOR): recorre toda la región y fija un menor");
        System.out.println("por pasada. Siempre n(n-1)/2 comparaciones, IGUAL si ya está");
        System.out.println("ordenado (15 comp en {1..6}) o casi (21 comp en {1,2,3,5,4,6,7});");
        System.out.println("a cambio hace a lo sumo n-1 intercambios (inverso: 15 comp / 3 swaps).");
        System.out.println("INSERTION (inserta en su LUGAR): prefijo ordenado + corte anticipado;");
        System.out.println("su trabajo de escritura son DESPLAZAMIENTOS (1 escritura c/u, no");
        System.out.println("swaps). Ordenado: 5 comp / 0 desp; casi ordenado: 7 comp / 1 desp;");
        System.out.println("inverso: 15 comp / 15 desp. Los desplazamientos = inversiones.");
        System.out.println();
        System.out.println("Números por caso (comparaciones / intercambios-o-desplazamientos):");
        System.out.println("  general     {5,3,8,3,1,9,2}: bubble 21/12 · selection 21/5  · insertion 16/12");
        System.out.println("  ordenado    {1,2,3,4,5,6}  : bubble 5/0   · selection 15/0  · insertion 5/0");
        System.out.println("  inverso     {6,5,4,3,2,1}  : bubble 15/15 · selection 15/3  · insertion 15/15");
        System.out.println("  casi ord.   {1,2,3,5,4,6,7}: bubble 11/1  · selection 21/1  · insertion 7/1");
        System.out.println("  negativos   {-3,0,-1,7}    : bubble 5/1   · selection 6/1   · insertion 4/1");
        System.out.println("  iguales     {4,4,4}        : bubble 2/0   · selection 3/0   · insertion 2/0");
        System.out.println();
        System.out.println("→ Bubble gana cuando casi está ordenado (bandera) pero gasta muchos");
        System.out.println("  intercambios en el peor caso (15 swaps contra 3 de selection).");
        System.out.println("→ Selection es la que MENOS escribe (1 swap por pasada como máximo)");
        System.out.println("  pero SIEMPRE hace el máximo de comparaciones (no es adaptativa).");
        System.out.println("→ CONCLUSIÓN: INSERTION SORT ES EL MEJOR EN ARREGLOS CASI ORDENADOS:");
        System.out.println("  en {1,2,3,5,4,6,7} hace 7 comparaciones y 1 desplazamiento, contra");
        System.out.println("  11/1 de bubble y 21/1 de selection; en el ordenado empata con bubble");
        System.out.println("  en el mínimo (5/0) y solo gasta 1 escritura por cada inversión.");
        System.out.println("  Cada corrección le cuesta UN desplazamiento (una escritura) en lugar");
        System.out.println("  de un swap de tres escrituras, por eso los híbridos reales (TimSort)");
        System.out.println("  recurren a insertion sort para arreglos chicos o casi ordenados.");
    }
}

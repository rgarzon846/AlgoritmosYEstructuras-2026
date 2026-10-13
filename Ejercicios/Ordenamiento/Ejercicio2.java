/*====================================================================================
                                    PROMPT 
 ====================================================================================
 "Implementa en Java un programa con DOS métodos de ordenamiento sobre arreglos de
 enteros:

     bubbleSort(int[] array)
     selectionSort(int[] array)

 Ambos deben ordenar el mismo arreglo (los mismos datos de partida) y el programa
 debe contar cuántas comparaciones e intercambios realiza cada algoritmo.

 1) QUÉ ALGORITMOS DE ORDENAMIENTO UTILIZAR:
    - BUBBLE SORT en bubbleSort(...): compara PARES ADYACENTES (i, i+1) y los
      intercambia si están en orden incorrecto (orden ascendente).
    - SELECTION SORT en selectionSort(...): en cada pasada busca DIRECTAMENTE el
      menor de la región sin ordenar y lo lleva a su posición con UN intercambio.

 2) RAZONAMIENTO 
    a) Bubble compara solo VECINOS (información local): el orden se construye con
       muchos intercambios pequeños; un elemento puede necesitar varias posiciones
       de "burbujeo" para llegar a su lugar. Con optimización de bandera: si una
       pasada no realiza ningún intercambio, el arreglo ya está ordenado → mejor
       caso n-1 comparaciones y 0 intercambios.
    b) Selection no mira vecinos: recorre TODA la región sin ordenar comparando
       contra un candidato mínimo (búsqueda directa del menor) y coloca ese menor
       en su sitio definitivo con 1 intercambio por pasada (a lo sumo n-1 en todo
       el algoritmo). Como debe examinar toda la región para CONFIRMAR que no queda
       un menor, siempre realiza n(n-1)/2 comparaciones y NO puede cortar antes,
       ni siquiera con el arreglo ya ordenado.
    c) Contraste numérico obligatorio (los contadores lo verifican):
       - Arreglo ordenado de 6: bubble → 5 comparaciones / 0 intercambios (bandera);
         selection → 15 comparaciones / 0 intercambios.
       - Arreglo inverso de 6: ambos → 15 comparaciones; bubble → 15 intercambios;
         selection → 3 intercambios.
       Conclusión: bubble ahorra comparaciones cuando el arreglo ya está casi
       ordenado; selection hace siempre el máximo de comparaciones pero MINIMIZA
       los intercambios (a lo sumo uno por pasada).

 3) QUÉ DATOS RECIBE EL PROGRAMA:
    Un arreglo de enteros (int[]) con positivos, negativos, ceros y valores
    repetidos, de cualquier tamaño (vacío, de 1 elemento o nulo). Como la ordenación
    es in-place y altera el arreglo, en main se parte de un arreglo original y se
    entrega una COPIA IDÉNTICA (clone) a cada algoritmo, de modo que ambos ordenan
    los MISMOS datos. Las pruebas se definen dentro de main; NO se requiere lectura
    por teclado.

 4) QUÉ DEBE MOSTRAR POR PANTALLA:
    - El arreglo original de cada caso.
    - Para cada algoritmo: traza breve por pasada (bubble: intercambios entre
      vecinos; selection: menor encontrado y su posición) y el arreglo resultante.
    - Los contadores de cada algoritmo: comparaciones, intercambios y pasadas.
    - La verificación de que ambos resultados son idénticos entre sí y iguales al
      resultado esperado.
    - Una tabla comparativa final con las comparaciones e intercambios de cada
      algoritmo por caso y el análisis de la diferencia (vecinos vs menor).

 5) QUÉ COMENTARIOS DEBE INCLUIR EL CÓDIGO:
    - Javadoc en bubbleSort: estrategia de comparar vecinos, bandera y cuándo rinde.
    - Javadoc en selectionSort: estrategia de buscar el menor directamente, por qué
      no puede cortar y cuándo rinde.
    - Comentario en el intercambio (swap) de bubble indicando que el mayor de cada
      par pasa un lugar hacia la derecha.
    - Comentario en la búsqueda de posMin de selection aclarando que recorre toda
      la región sin ordenar comparando contra el candidato mínimo.
    - Comentario en el clonado del arreglo explicando por qué ambos parten de los
      mismos datos, y comentarios en los casos borde (null, vacío, 1 elemento).

 6) CÓMO VERIFICAR QUE EL RESULTADO SEA CORRECTO:
    - Cada algoritmo recibe una copia del MISMO arreglo original; ambos resultados
      deben ser iguales entre sí (Arrays.equals) e iguales a Arrays.sort(original).
    - Verificar que el resultado esté en orden no decreciente (arreglo[i] <= arreglo[i+1]).
    - Verificar los contadores EXACTOS (comparaciones e intercambios) de todos los
      casos deterministas (formato comparaciones/intercambios):
        · {5, 3, 8, 3, 1, 9, 2} general : bubble 21/12 · selection 21/5
        · {1, 2, 3, 4, 5, 6}  ordenado  : bubble 5/0   · selection 15/0
        · {6, 5, 4, 3, 2, 1}  inverso   : bubble 15/15 · selection 15/3
        · {-3, 0, -1, 7}      negativos : bubble 5/1   · selection 6/1
        · {4, 4, 4}           iguales   : bubble 2/0   · selection 3/0
        · {42} / {} / null    borde     : 0/0 en ambos
      (Reglas de conteo: en selection NO se cuenta ni se realiza el intercambio
      cuando el menor ya está en su posición; en bubble el bucle interno se acorta
      una posición por pasada y corta por bandera cuando una pasada no intercambia.)
    Plan de pruebas obligatorio:
      CASO 1 general con repetidos: {5, 3, 8, 3, 1, 9, 2} → {1, 2, 3, 3, 5, 8, 9}
      CASO 2 ya ordenado (mejor caso de bubble): {1, 2, 3, 4, 5, 6} → igual
      CASO 3 orden inverso (peor caso de swaps): {6, 5, 4, 3, 2, 1} → {1, 2, 3, 4, 5, 6}
      CASO 4 con negativos: {-3, 0, -1, 7} → {-3, -1, 0, 7}
      CASO 5 todos iguales: {4, 4, 4} → {4, 4, 4}
      CASO 6 un solo elemento: {42} → {42}
      CASO 7 arreglo vacío: {} → {}
      CASO 8 arreglo nulo: null → ambos métodos sin excepción, mensaje informativo
"
 ====================================================================================
 */
public class Ejercicio2 {

    /**
     * Contadores compartidos por ambos algoritmos.
     */
    public static class Estadisticas {

        public final String algoritmo;
        public int pasadas = 0;
        public int comparaciones = 0;
        public int intercambios = 0;
        public boolean corteBandera = false;

        public Estadisticas(String algoritmo) {
            this.algoritmo = algoritmo;
        }

        @Override
        public String toString() {
            return algoritmo + ": comparaciones = " + comparaciones
                    + ", intercambios = " + intercambios
                    + ", pasadas = " + pasadas
                    + (corteBandera ? ", corte por bandera = SÍ" : "");
        }
    }

    // Fila de la tabla comparativa final: [caso, bComp, bSwap, sComp, sSwap, OK]
    private static final java.util.List<String[]> TABLA = new java.util.ArrayList<>();

    /**
     * BUBBLE SORT — ordena comparando PARES ADYACENTES.
     *
     * <p>
     * Razonamiento: en cada comparación (i, i+1), si el de la izquierda es
     * mayor se intercambian → el mayor pasa un lugar hacia la derecha. El orden
     * se construye con muchos intercambios chicos usando solo información LOCAL
     * (los vecinos). Con bandera: si una pasada no intercambia nada, el arreglo
     * ya está ordenado → mejor caso n-1 comparaciones y 0 intercambios (O(n)).
     * Rinde bien cuando el arreglo está casi ordenado; en el peor caso hace
     * n(n-1)/2 comparaciones y hasta n(n-1)/2 intercambios.
     *
     * @param array arreglo a ordenar in-place (null se ignora sin excepción)
     * @return contadores de comparaciones, intercambios y pasadas
     */
    public static Estadisticas bubbleSort(int[] array) {
        Estadisticas stats = new Estadisticas("Bubble Sort");

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

            System.out.println("    Pasada " + stats.pasadas + ": "
                    + java.util.Arrays.toString(array)
                    + "  (" + swapsPasada + " intercambios entre vecinos)");

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
     * SELECTION SORT — ordena buscando directamente el menor de la región.
     *
     * <p>
     * Razonamiento: en cada pasada se recorre TODA la región sin ordenar
     * comparando contra un candidato mínimo (búsqueda directa del menor, no de
     * vecinos) y el menor se lleva a su posición definitiva con UN intercambio
     * (a lo sumo n-1 en total). Como debe examinar toda la región para
     * CONFIRMAR que no queda un menor, siempre realiza n(n-1)/2 comparaciones y
     * NO puede cortar antes, ni con el arreglo ya ordenado. Minimiza
     * intercambios a cambio de no poder aprovechar un arreglo casi ordenado.
     *
     * @param array arreglo a ordenar in-place (null se ignora sin excepción)
     * @return contadores de comparaciones, intercambios y pasadas
     */
    public static Estadisticas selectionSort(int[] array) {
        Estadisticas stats = new Estadisticas("Selection Sort");

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

            System.out.println("    Pasada " + stats.pasadas + ": menor = " + array[k]
                    + " (posición " + posMin + ") → " + accion
                    + "  " + java.util.Arrays.toString(array));
        }

        System.out.println("  [Selection] resultado: " + java.util.Arrays.toString(array));
        System.out.println("  [Selection] " + stats);
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
     * Ejecuta un caso: entrega la MISMA copia del original a cada algoritmo,
     * compara resultados y verifica los contadores esperados.
     *
     * @param expBubble {comparaciones, intercambios} esperados de bubble o null
     * @param expSelection {comparaciones, intercambios} esperados de selection
     * o null
     */
    private static void probar(String descripcion, int[] original,
            int[] expBubble, int[] expSelection) {
        System.out.println("=== " + descripcion + " ===");

        // Caso borde: nulo → ambos métodos deben funcionar sin excepción
        if (original == null) {
            System.out.println("Original: null");
            Estadisticas b = bubbleSort(null);
            Estadisticas s = selectionSort(null);
            System.out.println("Verificación: ambos sin excepción → OK ✓");
            System.out.println();
            TABLA.add(new String[]{descripcion,
                String.valueOf(b.comparaciones), String.valueOf(b.intercambios),
                String.valueOf(s.comparaciones), String.valueOf(s.intercambios),
                "OK"});
            return;
        }

        System.out.println("Original: " + java.util.Arrays.toString(original));

        // Ambos parten de los MISMOS datos: una copia idéntica por algoritmo
        int[] paraBubble = original.clone();
        int[] paraSelection = original.clone();

        // Resultado esperado: copia ordenada con Arrays.sort
        int[] esperado = original.clone();
        java.util.Arrays.sort(esperado);

        System.out.println("--- BUBBLE SORT (compara vecinos) ---");
        Estadisticas b = bubbleSort(paraBubble);
        System.out.println("--- SELECTION SORT (busca el menor) ---");
        Estadisticas s = selectionSort(paraSelection);

        // Verificación de resultados
        boolean mismosDatos = java.util.Arrays.equals(paraBubble, paraSelection);
        boolean igualQueSort = java.util.Arrays.equals(paraBubble, esperado)
                && java.util.Arrays.equals(paraSelection, esperado);
        boolean ordenado = esOrdenado(paraBubble);

        // Verificación de contadores exactos (si el caso los define)
        boolean contBubbleOk = (expBubble == null)
                || (b.comparaciones == expBubble[0] && b.intercambios == expBubble[1]);
        boolean contSelectionOk = (expSelection == null)
                || (s.comparaciones == expSelection[0] && s.intercambios == expSelection[1]);

        boolean todoOk = mismosDatos && igualQueSort && ordenado && contBubbleOk && contSelectionOk;

        System.out.println("Verificación resultados idénticos entre sí: "
                + (mismosDatos ? "OK ✓" : "ERROR ✗"));
        System.out.println("Verificación vs Arrays.sort: "
                + (igualQueSort ? "OK ✓" : "ERROR ✗ (esperado "
                        + java.util.Arrays.toString(esperado) + ")"));
        System.out.println("Verificación orden no decreciente: "
                + (ordenado ? "OK ✓" : "ERROR ✗"));
        if (expBubble != null || expSelection != null) {
            System.out.println("Verificación contadores (esperado bubble "
                    + (expBubble != null ? expBubble[0] + "/" + expBubble[1] : "-")
                    + ", selection "
                    + (expSelection != null ? expSelection[0] + "/" + expSelection[1] : "-")
                    + "): " + (contBubbleOk && contSelectionOk ? "OK ✓" : "ERROR ✗"));
        }
        System.out.println();

        TABLA.add(new String[]{descripcion,
            String.valueOf(b.comparaciones), String.valueOf(b.intercambios),
            String.valueOf(s.comparaciones), String.valueOf(s.intercambios),
            todoOk ? "OK" : "ERROR"});
    }

    public static void main(String[] args) {
        // Caso 1: caso general con elementos repetidos
        probar("CASO 1: general con repetidos", new int[]{5, 3, 8, 3, 1, 9, 2},
                new int[]{21, 12}, new int[]{21, 5});

        // Caso 2: ya ordenado → mejor caso de bubble (bandera), selection sin cortes
        probar("CASO 2: ya ordenado", new int[]{1, 2, 3, 4, 5, 6},
                new int[]{5, 0}, new int[]{15, 0});

        // Caso 3: orden inverso → peor caso de intercambios en bubble
        probar("CASO 3: orden inverso", new int[]{6, 5, 4, 3, 2, 1},
                new int[]{15, 15}, new int[]{15, 3});

        // Caso 4: con negativos
        probar("CASO 4: con negativos", new int[]{-3, 0, -1, 7},
                new int[]{5, 1}, new int[]{6, 1});

        // Caso 5: todos iguales (ningún intercambio en ninguno)
        probar("CASO 5: todos iguales", new int[]{4, 4, 4},
                new int[]{2, 0}, new int[]{3, 0});

        // Caso 6: un solo elemento
        probar("CASO 6: un solo elemento", new int[]{42},
                new int[]{0, 0}, new int[]{0, 0});

        // Caso 7: arreglo vacío
        probar("CASO 7: arreglo vacío", new int[]{},
                new int[]{0, 0}, new int[]{0, 0});

        // Caso 8: arreglo nulo → sin excepción en ninguno de los dos
        probar("CASO 8: arreglo nulo", null,
                new int[]{0, 0}, new int[]{0, 0});

        // Tabla comparativa final: comparaciones e intercambios de cada algoritmo
        System.out.println("=== TABLA COMPARATIVA (comparaciones / intercambios) ===");
        System.out.printf("%-34s | %10s | %10s | %s%n",
                "CASO", "BUBBLE", "SELECTION", "VERIFICACIÓN");
        System.out.println("--------------------------------------------------------------"
                + "------------------");
        for (String[] fila : TABLA) {
            System.out.printf("%-34s | %5s / %-4s | %5s / %-4s | %s%n",
                    fila[0], fila[1], fila[2], fila[3], fila[4], fila[5]);
        }

        // Análisis: comparar vecinos vs buscar directamente el menor
        System.out.println();
        System.out.println("=== ANÁLISIS: COMPARAR VECINOS vs BUSCAR EL MENOR ===");
        System.out.println("BUBBLE (compara vecinos): información local; el orden se");
        System.out.println("construye con muchos intercambios. Con bandera detecta el");
        System.out.println("orden con n-1 comparaciones (caso ordenado: 5 comp / 0 swaps),");
        System.out.println("pero en el peor caso intercambia n(n-1)/2 veces (inverso: 15/15).");
        System.out.println("SELECTION (busca el menor): recorre toda la región y fija un");
        System.out.println("menor por pasada. Siempre n(n-1)/2 comparaciones (ordenado: 15");
        System.out.println("comp) pero a lo sumo n-1 intercambios (inverso: 15 comp / 3 swaps).");
        System.out.println("→ Bubble ahorra comparaciones si casi está ordenado;");
        System.out.println("→ Selection minimiza intercambios sin importar el estado inicial.");
    }
}

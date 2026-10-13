/*====================================================================================
                                    PROMPT 
 ====================================================================================
 "Implementa en Java un programa que ordene un arreglo de números enteros usando el
 algoritmo de ordenamiento BUBBLE SORT (ordenamiento burbuja).

 1) QUÉ ALGORITMO DE ORDENAMIENTO UTILIZAR:
    Bubble Sort, obligatoriamente. Se recorre el arreglo comparando PARES ADYACENTES
    (i, i+1) y se intercambian cuando están en orden incorrecto (orden ASCENDENTE),
    repitiendo pasadas hasta que el arreglo quede ordenado.

 2) RAZONAMIENTO DEL ALGORITMO
    a) En cada comparación de un par adyacente, si el de la izquierda es mayor que el
       de la derecha, se intercambian: el mayor pasa SIEMPRE un lugar hacia la
       derecha y el menor queda un lugar hacia la izquierda.
    b) En una pasada completa el mayor nunca retrocede: mientras ocupe la posición
       izquierda de un par, al ser el más grande "gana" la comparación y avanza un
       lugar; cuando queda a la derecha de un par, no se cumple izquierda > derecha y
       no se mueve. Así, cuando el bucle llega a su posición lo empuja iteración a
       iteración hasta el extremo derecho: "sube" como una burbuja hasta el final y
       ahí queda FIJO, porque los elementos que siguen a su derecha ya están en su
       lugar y no vuelve a participar en más comparaciones.
    c) Invariante: después de la pasada k, los k últimos lugares contienen los k
       mayores valores del arreglo en su posición definitiva → el bucle interno se
       acorta una posición por pasada (no hace falta comparar la región ya fijada).

 3) QUÉ DATOS RECIBE EL PROGRAMA:
    Un arreglo de enteros (int[]), con positivos, negativos, ceros y valores
    repetidos, de cualquier tamaño (vacío, de 1 elemento o nulo). Los arreglos de
    prueba se definen dentro de main; NO se requiere lectura por teclado.

 4) QUÉ DEBE MOSTRAR POR PANTALLA:
    - El arreglo original antes de ordenarlo.
    - La traza breve de cada intercambio dentro de la pasada.
    - El estado del arreglo DESPUÉS DE CADA PASADA, indicando qué elemento quedó fijo
      al final y si se cumple el invariante de la sección 2.
    - El arreglo final ordenado, las estadísticas totales (pasadas, comparaciones,
      intercambios) y la verificación del resultado.

 5) QUÉ COMENTARIOS DEBE INCLUIR EL CÓDIGO:
    - Javadoc al inicio de la clase con la estrategia del algoritmo y la explicación
      de por qué el mayor se desplaza al final (sección 2).
    - Comentario en la comparación de pares adyacentes que explique el intercambio y
      hacia dónde se mueve el mayor.
    - Comentario del invariante que justifique el acortamiento del bucle interno.
    - Comentarios en los casos borde (null, vacío, 1 elemento) y en la optimización de
      bandera (corte cuando una pasada no realiza intercambios).

 6) CÓMO VERIFICAR QUE EL RESULTADO SEA CORRECTO:
    - Para cada caso, ordenar una copia con java.util.Arrays.sort y comparar con
      Arrays.equals → mostrar OK / ERROR.
    - Tras cada pasada, comprobar que el último elemento de la región sin ordenar sea
      el máximo de esa región (invariante de la sección 2).
    - Comprobar que el arreglo final esté en orden no decreciente
      (arreglo[i] <= arreglo[i+1] para todo i).
    Plan de pruebas obligatorio:
      CASO 1 general con repetidos: {5, 3, 8, 3, 1, 9, 2} → {1, 2, 3, 3, 5, 8, 9}
      CASO 2 ya ordenado (mejor caso): {1, 2, 3, 4, 5, 6} → {1, 2, 3, 4, 5, 6}
      CASO 3 orden inverso (peor caso): {6, 5, 4, 3, 2, 1} → {1, 2, 3, 4, 5, 6}
      CASO 4 con negativos: {-3, 0, -1, 7} → {-3, -1, 0, 7}
      CASO 5 todos iguales: {4, 4, 4} → {4, 4, 4}
      CASO 6 un solo elemento: {42} → {42}
      CASO 7 arreglo vacío: {} → {}
      CASO 8 arreglo nulo: null → sin excepción, mensaje informativo
"
 ====================================================================================
 */
public class Ejercicio1 {

    /**
     * Estadísticas y verificaciones del ordenamiento.
     */
    public static class Estadisticas {

        public int pasadas = 0;
        public int comparaciones = 0;
        public int intercambios = 0;
        public boolean corteTemprano = false;
        public boolean invarianteOk = true; // se cumplió tras cada pasada

        @Override
        public String toString() {
            return "pasadas = " + pasadas
                    + ", comparaciones = " + comparaciones
                    + ", intercambios = " + intercambios
                    + ", corte por bandera = " + (corteTemprano ? "SÍ" : "NO")
                    + ", invariante verificado = " + (invarianteOk ? "SÍ ✓" : "NO ✗");
        }
    }

    /**
     * Ordena el arreglo de forma ASCENDENTE con Bubble Sort, mostrando el
     * arreglo original, la traza de cada intercambio y el estado después de
     * cada pasada.
     *
     * <p>
     * <b>Por qué el mayor se va desplazando hacia el final:</b> en cada
     * comparación de un par adyacente, el mayor de los dos pasa un lugar hacia
     * la derecha. El mayor del arreglo, en una pasada, solo gana comparaciones
     * (avanza) o no se mueve: nunca retrocede. Cuando el bucle llega a su
     * posición lo empuja paso a paso hasta la última casilla, donde queda FIJO
     * (invariante: tras la pasada k, los k últimos lugares son definitivos).
     *
     * @param arreglo a ordenar (se modifica in-place; null se ignora sin
     * excepción)
     * @return estadísticas y verificación del invariante
     */
    public static Estadisticas bubbleSort(int[] arreglo) {
        Estadisticas stats = new Estadisticas();

        // Caso borde: arreglo nulo → sin excepción, solo mensaje informativo
        if (arreglo == null) {
            System.out.println("Arreglo nulo → nada que ordenar.");
            return stats;
        }

        System.out.println("Arreglo original: " + java.util.Arrays.toString(arreglo));

        // Caso borde: vacío o un solo elemento → ya está "ordenado", 0 pasadas
        int n = arreglo.length;
        if (n < 2) {
            System.out.println("Arreglo vacío o con 1 elemento → ya está ordenado (0 pasadas).");
            return stats;
        }

        for (int pasada = 0; pasada < n - 1; pasada++) {
            stats.pasadas++;

            // Región SIN ORDENAR antes de la pasada: índices [0 .. n-1-pasada]
            int limite = n - 1 - pasada;

            // Máximo de la región sin ordenar: se calculará antes de comparar para
            // verificar después que haya quedado fijo en la última posición
            int maxRegion = arreglo[0];
            for (int j = 1; j <= limite; j++) {
                if (arreglo[j] > maxRegion) {
                    maxRegion = arreglo[j];
                }
            }

            boolean huboIntercambios = false;
            System.out.println("Pasada " + stats.pasadas
                    + " (región sin ordenar: [0.." + limite + "]):");

            // Invariante: los k últimos lugares ya están fijos → el bucle interno
            // se acorta una posición por pasada y no revisa esa región
            for (int i = 0; i < limite; i++) {
                stats.comparaciones++;
                int izq = arreglo[i];
                int der = arreglo[i + 1];

                if (izq > der) {
                    // Intercambio de pares adyacentes: el mayor de los dos pasa
                    // SIEMPRE un lugar hacia la derecha (por eso el mayor "sube"
                    // como una burbuja hasta el final de la región)
                    arreglo[i] = der;
                    arreglo[i + 1] = izq;
                    stats.intercambios++;
                    huboIntercambios = true;
                    System.out.println("   " + izq + " > " + der + " → swap pos "
                            + i + " ↔ " + (i + 1)
                            + "  " + java.util.Arrays.toString(arreglo));
                }
            }

            // Verificación del invariante: el mayor de la región quedó fijo al final
            boolean invariantePasada = (arreglo[limite] == maxRegion);
            if (!invariantePasada) {
                stats.invarianteOk = false;
            }

            // Estado del arreglo DESPUÉS de cada pasada (requisito de salida)
            System.out.println("   Estado tras la pasada " + stats.pasadas + ": "
                    + java.util.Arrays.toString(arreglo));
            System.out.println("   → Mayor de la región (" + maxRegion
                    + ") fijo en la posición " + limite
                    + ". Invariante: " + (invariantePasada ? "OK ✓" : "ERROR ✗"));

            // Optimización de bandera: si una pasada no intercambió nada, ya estaba
            // ordenado → corte anticipado (mejor caso O(n))
            if (!huboIntercambios) {
                stats.corteTemprano = true;
                System.out.println("   Sin intercambios en esta pasada → ordenado. CORTO.");
                break;
            }
        }

        System.out.println("Arreglo ordenado: " + java.util.Arrays.toString(arreglo));
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
     * Ejecuta un caso: ordena, compara contra Arrays.sort y muestra el
     * resultado.
     */
    private static void probar(String descripcion, int[] original) {
        System.out.println("=== " + descripcion + " ===");

        // Caso borde: nulo → se prueba que no lance excepción
        if (original == null) {
            System.out.println("Original: null");
            Estadisticas stats = bubbleSort(null);
            System.out.println("Verificación: OK ✓ (sin excepción)");
            System.out.println("Estadísticas: " + stats);
            System.out.println();
            return;
        }

        System.out.println("Original: " + java.util.Arrays.toString(original));

        // Vector esperado: copia ordenada con Arrays.sort
        int[] esperado = original.clone();
        java.util.Arrays.sort(esperado);

        Estadisticas stats = bubbleSort(original);

        boolean igualQueSort = java.util.Arrays.equals(original, esperado);
        boolean ordenado = esOrdenado(original);

        System.out.println("Verificación vs Arrays.sort: "
                + (igualQueSort ? "OK ✓"
                        : "ERROR ✗ (esperado " + java.util.Arrays.toString(esperado) + ")"));
        System.out.println("Verificación orden no decreciente: "
                + (ordenado ? "OK ✓" : "ERROR ✗"));
        System.out.println("Estadísticas: " + stats);
        System.out.println();
    }

    public static void main(String[] args) {
        // Caso 1: caso general con elementos repetidos
        probar("CASO 1: general con repetidos", new int[]{5, 3, 8, 3, 1, 9, 2});

        // Caso 2: ya ordenado → mejor caso (bandera corta tras 1 pasada)
        probar("CASO 2: ya ordenado (mejor caso)", new int[]{1, 2, 3, 4, 5, 6});

        // Caso 3: orden inverso → peor caso (n-1 pasadas completas)
        probar("CASO 3: orden inverso (peor caso)", new int[]{6, 5, 4, 3, 2, 1});

        // Caso 4: con negativos (verifica que no se inicialice con 0)
        probar("CASO 4: con negativos", new int[]{-3, 0, -1, 7});

        // Caso 5: todos iguales (ningún intercambio)
        probar("CASO 5: todos iguales", new int[]{4, 4, 4});

        // Caso 6: un solo elemento
        probar("CASO 6: un solo elemento", new int[]{42});

        // Caso 7: arreglo vacío
        probar("CASO 7: arreglo vacío", new int[]{});

        // Caso 8: arreglo nulo → sin excepción
        probar("CASO 8: arreglo nulo", null);

        // Resumen
        System.out.println("=== RESUMEN ===");
        System.out.println("Bubble Sort: compara pares adyacentes; el mayor de cada par");
        System.out.println("pasa un lugar a la derecha → el mayor del arreglo 'sube' como");
        System.out.println("una burbuja hasta el final de cada pasada y queda fijo ahí.");
        System.out.println("Mejor caso O(n) (bandera) · peor/promedio O(n²) · espacio O(1).");
    }
}

/* ...CORRECCIONES HECHAS POR OPENCODE
====================================================================================
CORRECCIONES HECHAS POR OPENCODE
====================================================================================
[REDUNDANTE] probar() imprime "Original: ..." y bubbleSort() vuelve a imprimir "Arreglo original: ..." → cada arreglo de prueba aparece DOS veces en la salida aunque el prompt solo pide mostrar el original una vez.
[PROMPT] El bloque "=== RESUMEN ===" final con la complejidad O(n)/O(n²)/O(1) no figura en el prompt: su sección 4 solo pide traza, estadísticas y verificación, nunca Big-O por pantalla.
====================================================================================
*/

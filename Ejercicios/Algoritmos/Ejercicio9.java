
/**
 * Algoritmo que encuentra el MAYOR elemento de una matriz
 * (arreglo bidimensional) de enteros.
 *
 * ===========================================================================
 * ESTRATEGIA ELEGIDA
 * ===========================================================================
 *
 * Recorrido NIDADO por filas y columnas manteniendo una variable `maximo`
 * que se actualiza cada vez que se encuentra un elemento MAYOR que el
 * actual (mismo patrón que el mínimo de un vector, extendido a 2D):
 *
 *   maximo = matriz[0][0]           (primera celda)
 *   para i de 0 hasta filas-1:
 *       para j de 0 hasta columnas-1:
 *           si matriz[i][j] > maximo → maximo = matriz[i][j]
 *   devolver maximo
 *
 * Sobre la inicialización:
 *   - Se usa la PRIMERA CELDA (0,0), no `Integer.MIN_VALUE` como atajo
 *     (ese valor oculta que no se inicializó con datos reales y falla en
 *     silencio con matrices vacías) ni `0` (que fallaría con matrices
 *     compuestas solo por valores negativos).
 *
 * ===========================================================================
 * JUSTIFICACIÓN: POR QUÉ ES NECESARIO RECORRER TODOS LOS ELEMENTOS
 * ===========================================================================
 *
 * 1. El mayor es una propiedad GLOBAL de la matriz: su valor depende de
 *    TODOS sus elementos.
 *
 * 2. Sin información de orden ni estructura auxiliar, cualquier celda NO
 *    visitada podría contener un valor mayor que el máximo parcial. No
 *    hay forma de descartar filas ni columnas (a diferencia de la búsqueda
 *    binaria, que requiere orden para eliminar mitades del espacio).
 *
 * 3. Solo al visitar la última celda se garantiza que no queda ningún
 *    valor posiblemente mayor.
 *
 * 4. Invariante del algoritmo: tras visitar la celda (i,j), `maximo`
 *    contiene el mayor de TODAS las celdas visitadas hasta ese momento.
 *    Por lo tanto, un único recorrido es:
 *      - SUFICIENTE (correctitud): cada actualización garantiza que
 *        `maximo` es el mayor visto; al terminar, es el mayor global.
 *      - NECESARIO (límite inferior): el máximo no se conoce sin haber
 *        observado todas las celdas, porque la que falta podría ser mayor.
 *
 * ===========================================================================
 * ANÁLISIS DE COMPLEJIDAD
 * ===========================================================================
 *
 * Temporal: O(filas × columnas)
 *   - Sea m = filas y n = columnas: se visitan m·n celdas → O(m·n).
 *   - Si la matriz es CUADRADA de lado k → O(k²).
 *   - Si N = total de elementos → O(N).
 *   - Mejor caso: O(m·n) → el mayor ya está en (0,0); igualmente hay que
 *     comparar todas las celdas contra él.
 *   - Peor caso:  O(m·n) → el mayor está en la ÚLTIMA celda (la
 *     actualización recién ocurre al final).
 *   - Caso promedio: O(m·n).
 *   Los tres casos COINCIDEN: en una búsqueda de máximo total no hay
 *   corte posible (mismo argumento que contar, mínimo y suma).
 *
 * Espacial: O(1) adicional
 *   - Solo `maximo` (y la posición guardada) y los dos índices.
 *   - No se copia la matriz ni se crea ninguna estructura auxiliar; la
 *     memoria de la matriz (m·n) es la ENTRADA, no espacio auxiliar.
 *
 * ===========================================================================
 * CONTABILIZACIÓN DE OPERACIONES
 * ===========================================================================
 *
 * El programa informa:
 *   - celdas recorridas: m·n (todas, siempre).
 *   - comparaciones contra el máximo: m·n (una por celda, incluida la
 *     primera contra sí misma) — o m·n − 1 si la primera no se cuenta.
 *   - traza: celda (i,j), valor y máximo parcial.
 */
public class Ejercicio9 {

    /**
     * Posición encontrada (fila, columna) del mayor elemento.
     */
    public static class Posicion {

        public final int fila;
        public final int columna;

        public Posicion(int fila, int columna) {
            this.fila = fila;
            this.columna = columna;
        }

        @Override
        public String toString() {
            return "(" + fila + ", " + columna + ")";
        }
    }

    /**
     * Encuentra el mayor elemento de la matriz con un recorrido nidado. Informa
     * traza y contadores de operaciones.
     *
     * @param matriz arreglo bidimensional de enteros (null/vacío → error)
     * @return el mayor valor de la matriz
     * @throws IllegalArgumentException si la matriz es null o está vacía (no
     * existe máximo en un conjunto vacío)
     */
    public static int encontrarMaximo(int[][] matriz) {
        // Caso borde: sin celdas no hay máximo → se señala con excepción
        if (matriz == null || matriz.length == 0 || matriz[0].length == 0) {
            throw new IllegalArgumentException(
                    "La matriz es null o no tiene celdas: no existe un valor máximo.");
        }

        int filas = matriz.length;
        int columnas = matriz[0].length;

        // Inicialización con la PRIMERA CELDA (no con MIN_VALUE ni 0)
        int maximo = matriz[0][0];
        Posicion posicion = new Posicion(0, 0);
        int celdasRecorridas = 0;
        int comparaciones = 0;

        System.out.println("  Matriz " + filas + " x " + columnas);
        System.out.println("  Inicial: maximo = " + maximo + " en " + posicion);

        // Recorrido NIDADO: cada celda exactamente una vez
        for (int i = 0; i < filas; i++) {
            if (matriz[i].length != columnas) {
                System.out.println("  ⚠ Fila " + i + " tiene " + matriz[i].length
                        + " columnas (se esperaban " + columnas + ").");
            }
            for (int j = 0; j < matriz[i].length; j++) {
                celdasRecorridas++;
                comparaciones++;
                if (matriz[i][j] > maximo) {
                    System.out.println("    (" + i + ", " + j + ") " + matriz[i][j]
                            + " > " + maximo + " → nuevo máximo");
                    maximo = matriz[i][j];
                    posicion = new Posicion(i, j);
                } else {
                    System.out.println("    (" + i + ", " + j + ") " + matriz[i][j]
                            + " ≤ " + maximo + " → sin cambio");
                }
            }
        }

        System.out.println("  Celdas recorridas : " + celdasRecorridas);
        System.out.println("  Comparaciones     : " + comparaciones);
        System.out.println("  Mayor = " + maximo + " encontrado en " + posicion);
        return maximo;
    }

    // ======================================================================
    // PRUEBAS
    // ======================================================================
    private static void probar(String descripcion, int[][] matriz, int esperado) {
        System.out.println("=== " + descripcion + " ===");
        imprimirMatriz(matriz);
        try {
            int maximo = encontrarMaximo(matriz);
            System.out.println("Resultado: máximo = " + maximo + " (esperado "
                    + esperado + ") → " + (maximo == esperado ? "OK ✓" : "ERROR ✗"));
        } catch (IllegalArgumentException e) {
            System.out.println("Excepción esperada: " + e.getMessage()
                    + (esperado == 0 ? "  OK ✓" : "  ERROR ✗"));
        }
        System.out.println();
    }

    private static void imprimirMatriz(int[][] matriz) {
        if (matriz == null) {
            System.out.println("Matriz: null");
            return;
        }
        if (matriz.length == 0) {
            System.out.println("Matriz: [] (0 filas)");
            return;
        }
        System.out.println("Matriz:");
        for (int[] fila : matriz) {
            System.out.println("  " + java.util.Arrays.toString(fila));
        }
    }

    public static void main(String[] args) {
        // Caso 1: matriz general, máximo en el medio
        probar("CASO 1: máximo en el medio", new int[][]{
            {3, 7, 2},
            {9, 4, 8},
            {1, 6, 5}}, 9);

        // Caso 2: máximo en la última celda (recorrido completo)
        probar("CASO 2: máximo en la última celda", new int[][]{
            {1, 2, 3},
            {4, 5, 6}}, 6);

        // Caso 3: todos los valores negativos (caza el init con 0)
        probar("CASO 3: todos negativos", new int[][]{
            {-5, -2, -9},
            {-1, -7, -4}}, -1);

        // Caso 4: todos los elementos iguales
        probar("CASO 4: todos iguales", new int[][]{
            {3, 3, 3},
            {3, 3, 3}}, 3);

        // Caso 5: matriz 1x1
        probar("CASO 5: matriz 1x1", new int[][]{{42}}, 42);

        // Caso 6: matriz null (excepción)
        probar("CASO 6: matriz null", null, 0);

        // Resumen
        System.out.println("=== RESUMEN ===");
        System.out.println("Estrategia: recorrido nidado con variable `maximo`.");
        System.out.println("Temporal  : O(filas x columnas) — los 3 casos coinciden.");
        System.out.println("Espacial  : O(1) adicional.");
        System.out.println("Necesario recorrer todo: cualquier celda pendiente");
        System.out.println("podría contener un valor mayor (propiedad global).");
    }
}

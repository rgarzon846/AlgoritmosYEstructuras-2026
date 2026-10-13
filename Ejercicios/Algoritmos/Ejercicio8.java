/**
 * Algoritmo que calcula la suma de todos los elementos de una matriz
 * (arreglo bidimensional) de enteros.
 *
 * ===========================================================================
 * ESTRATEGIA DE RECORRIDO
 * ===========================================================================
 *
 * Recorrido NIDADO por filas y columnas:
 *
 *   acumulador = 0
 *   para i de 0 hasta filas-1:          (bucle externo: filas)
 *       para j de 0 hasta columnas-1:   (bucle interno: columnas)
 *           celdasRecorridas++
 *           acumulador += matriz[i][j]
 *   devolver acumulador
 *
 * ¿Por qué este recorrido?
 *
 * 1. La suma es una operación sobre TODOS los elementos: cada celda debe
 *    visitarse EXACTAMENTE UNA VEZ. Es un recorrido completo, igual que
 *    contar apariciones o encontrar el mínimo: no existe ningún corte
 *    anticipado posible (no hay un elemento que haga conclusiva la suma
 *    antes de terminar).
 *
 * 2. El ORDEN del recorrido no cambia el resultado: la suma es
 *    CONMUTATIVA (a + b = b + a) y ASOCIATIVA ((a + b) + c = a + (b + c)),
 *    por lo tanto recorrer fila por fila, columna por columna o en zigzag
 *    produce siempre el mismo total.
 *
 * 3. Se asume una matriz RECTANGULAR (todas las filas con la misma
 *    cantidad de columnas). Para robustez, el código detecta filas de
 *    distinto largo ("ragged" — posible en Java, ya que int[][] es un
 *    arreglo de arreglos) y usa el largo de la PRIMERA fila como guía,
 *    informando la discrepancia.
 *
 * ===========================================================================
 * ANÁLISIS DE COMPLEJIDAD
 * ===========================================================================
 *
 * Temporal: O(filas × columnas)
 *   - Sea m = cantidad de filas y n = cantidad de columnas: el recorrido
 *     realiza m·n visitas de celda → O(m·n).
 *   - Si la matriz es CUADRADA de lado k: O(k²).
 *   - Si se denota N = total de elementos: O(N).
 *   - Mejor caso: O(m·n) → todas las celdas se visitan siempre.
 *   - Peor caso:  O(m·n) → ídem.
 *   - Caso promedio: O(m·n) → ídem.
 *   Los tres casos COINCIDEN: en una suma total no hay corte posible
 *   (mismo argumento que contar apariciones y encontrar el mínimo).
 *
 * Espacial: O(1) adicional
 *   - Solo el acumulador y los dos índices; la matriz ya existe y NO se
 *     crea ninguna estructura nueva.
 *   - (La memoria de la matriz en sí, m·n, no cuenta como espacio
 *     AUXILIAR del algoritmo: es la entrada.)
 *
 * ===========================================================================
 * CONTABILIZACIÓN DE OPERACIONES
 * ===========================================================================
 *
 * El programa informa:
 *   - cantidad de ELEMENTOS RECORRIDOS (celdas visitadas) = m·n
 *   - cantidad de SUMAS EFECTUADAS (acumulaciones al acumulador)
 *     = m·n si el acumulador parte de 0 (cada celda se suma), de las
 *     cuales la primera es trivial (0 + x).
 *   - traza del recorrido: (fila, columna), valor y acumulador parcial.
 */
public class Ejercicio8 {

    /**
     * Calcula la suma de todos los elementos de la matriz con un recorrido
     * nidado fila por fila. Informa traza y contadores de operaciones.
     *
     * @param matriz arreglo bidimensional de enteros (puede ser null/vacío)
     * @return suma total de todos los elementos (0 si no hay elementos)
     */
    public static long sumarMatriz(int[][] matriz) {
        // Caso borde: matriz null o sin filas → suma 0
        if (matriz == null || matriz.length == 0) {
            System.out.println("  Matriz null o sin filas → suma = 0 (0 operaciones).");
            return 0;
        }

        int filas = matriz.length;
        int columnas = matriz[0].length;

        long acumulador = 0;          // long para evitar desbordamiento
        int elementosRecorridos = 0;
        int sumasEfectuadas = 0;

        System.out.println("  Matriz " + filas + " x " + columnas);

        // Recorrido NIDADO: bucle externo por filas, interno por columnas
        for (int i = 0; i < filas; i++) {
            // Robustez: filas con distinto largo (posible en Java)
            if (matriz[i].length != columnas) {
                System.out.println("  ⚠ Fila " + i + " tiene " + matriz[i].length
                        + " columnas (se esperaban " + columnas
                        + ") → se recorre según esta fila.");
            }
            for (int j = 0; j < matriz[i].length; j++) {
                elementosRecorridos++;
                acumulador += matriz[i][j];
                sumasEfectuadas++;
                System.out.println("    (" + i + ", " + j + ") valor = "
                        + matriz[i][j] + "  → acumulador = " + acumulador);
            }
        }

        System.out.println("  Elementos recorridos: " + elementosRecorridos);
        System.out.println("  Sumas efectuadas   : " + sumasEfectuadas);
        return acumulador;
    }

    // ======================================================================
    // PRUEBAS
    // ======================================================================

    private static void probar(String descripcion, int[][] matriz, long esperado) {
        System.out.println("=== " + descripcion + " ===");
        imprimirMatriz(matriz);
        long suma = sumarMatriz(matriz);
        System.out.println("Resultado: suma = " + suma + " (esperada " + esperado
                + ") → " + (suma == esperado ? "OK ✓" : "ERROR ✗"));
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
        // Caso 1: matriz general 3x4
        probar("CASO 1: matriz 3x4", new int[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12}}, 78);

        // Caso 2: matriz 1x1
        probar("CASO 2: matriz 1x1", new int[][]{{42}}, 42);

        // Caso 3: valores negativos y ceros
        probar("CASO 3: negativos y ceros", new int[][]{
                {-3, 0, 5},
                {2, -7, 1}}, -2);

        // Caso 4: matriz con una fila vacía
        probar("CASO 4: fila vacía", new int[][]{
                {},
                {1, 2}}, 3);

        // Caso 5: matriz vacía (0 filas)
        probar("CASO 5: 0 filas", new int[][]{}, 0);

        // Resumen
        System.out.println("=== RESUMEN ===");
        System.out.println("Recorrido: nidado filas x columnas, cada celda una vez.");
        System.out.println("Temporal  : O(filas x columnas) — los 3 casos coinciden.");
        System.out.println("Espacial  : O(1) adicional (solo acumulador e índices).");
        System.out.println("El orden de recorrido no importa (suma conmutativa).");
    }
}

/* ...CORRECCIONES HECHAS POR OPENCODE
====================================================================================
CORRECCIONES HECHAS POR OPENCODE
====================================================================================
[REDUNDANTE] `elementosRecorridos` y `sumasEfectuadas` se incrementan juntas en cada iteración y siempre valen lo mismo: un contador es copia exacta del otro.
[PROMPT] El enunciado solo pedía la suma de todos los elementos; el código además detecta filas de distinto largo (ragged), imprime traza celda por celda con el acumulador parcial y ejecuta 5 pruebas.
====================================================================================
*/

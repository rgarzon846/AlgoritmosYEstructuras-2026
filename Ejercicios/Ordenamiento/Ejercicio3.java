/*====================================================================================
                                    PROMPT 
 ====================================================================================
 "Implementa en Java un programa que ordene un arreglo de números enteros usando el
 algoritmo de ordenamiento INSERTION SORT (ordenamiento por inserción), pensado para
 arreglos que YA ESTÁN CASI ORDENADOS. Caso base del enunciado:

     int[] numeros = {1, 2, 3, 5, 4, 6, 7};

 El programa debe mostrar cuántos DESPLAZAMIENTOS realiza.

 1) QUÉ ALGORITMO DE ORDENAMIENTO UTILIZAR:
    Insertion Sort, obligatoriamente. Recorre el arreglo de izquierda a derecha
    manteniendo un PREFIJO ORDENADO a la izquierda; para cada elemento nuevo (la
    clave) lo saca de su posición, recorre el prefijo ordenado de derecha a
    izquierda desplazando hacia la derecha los elementos mayores que la clave, y la
    inserta en el hueco que queda.

 2) RAZONAMIENTO
    a) CORTE ANTICIPADO: el bucle interno recorre el prefijo ordenado y se DETIENE
       apenas encuentra un elemento menor o igual a la clave (no recorre todo el
       prefijo). Si el arreglo está casi ordenado, la clave suele estar ya casi en
       su lugar → el bucle interno hace muy poco trabajo por elemento → el total
       se acerca a O(n).
    b) LOS DESPLAZAMIENTOS SON LAS INVERSIONES: cada desplazamiento corresponde a
       un par fuera de orden (inversión). Un arreglo casi ordenado tiene muy pocas
       inversiones → muy pocos desplazamientos. En el ejemplo {1,2,3,5,4,6,7} solo
       hay UNA inversión (5,4) → solo 1 desplazamiento.
    c) CASOS: mejor caso (ordenado) = n-1 comparaciones y 0 desplazamientos = O(n);
       peor caso (inverso) = n(n-1)/2 comparaciones y n(n-1)/2 desplazamientos =
       O(n²). Es decir: se adapta al orden existente (es un algoritmo ADAPTATIVO).
    d) DESPLAZAMIENTO vs SWAP: mover un elemento una posición a la derecha es UNA
       sola escritura (array[j+1] = array[j]), mientras que un swap son TRES
       escrituras → el desplazamiento es más barato.
    e) EN LA PRÁCTICA: por ser adaptativo y de baja sobrecarga, los ordenamientos
       híbridos reales (p. ej. TimSort, que usa java.util.Arrays.sort para objetos)
       recurren a insertion sort para arreglos chicos o casi ordenados.

 3) QUÉ DATOS RECIBE EL PROGRAMA:
    Un arreglo de enteros (int[]) con positivos, negativos, ceros y valores
    repetidos, de cualquier tamaño (vacío, 1 elemento o nulo), con énfasis en
    arreglos casi ordenados. Los arreglos de prueba se definen dentro de main
    (incluido el {1, 2, 3, 5, 4, 6, 7} del enunciado); NO se requiere lectura por
    teclado. La ordenación es in-place sobre el arreglo recibido.

 4) QUÉ DEBE MOSTRAR POR PANTALLA:
    - El arreglo original antes de ordenarlo.
    - Un paso por cada elemento: la clave, qué elementos desplaza, por qué se corta
      el recorrido (corte anticipado) y el estado del arreglo tras insertarla.
    - El arreglo final ordenado.
    - EL TOTAL DE DESPLAZAMIENTOS REALIZADOS (requisito explícito) y también las
      comparaciones efectuadas.
    - La verificación del resultado y una tabla comparativa final (casi ordenado /
      ordenado / inverso) que sustente el análisis de por qué casi ordenado rinde.

 5) QUÉ COMENTARIOS DEBE INCLUIR EL CÓDIGO:
    - Javadoc al inicio de la clase con la estrategia y la explicación de por qué
      funciona bien con datos casi ordenados (sección 2 resumida).
    - Comentario en el bucle de desplazamiento indicando que es UNA escritura por
      desplazamiento (no un swap).
    - Comentario en la condición de corte (array[j] > clave) explicando que salir
      apenas encuentra un elemento <= clave es lo que hace al algoritmo adaptativo.
    - Comentario de la convención de conteo (qué cuenta como comparación y qué
      cuenta como desplazamiento) y de los casos borde (null, vacío, 1 elemento).

 6) CÓMO VERIFICAR QUE EL RESULTADO SEA CORRECTO:
    Convención de conteo (obligatoria para que los números sean reproducibles):
      - Una COMPARACIÓN = cada evaluación de la condición array[j] > clave.
      - Un DESPLAZAMIENTO = cada vez que un elemento del prefijo ordenado se mueve
        UNA posición a la derecha (array[j+1] = array[j]); la escritura final de la
        clave en el hueco NO cuenta como desplazamiento.
    Verificaciones:
    - El resultado debe ser igual a java.util.Arrays.sort(original) y estar en
      orden no decreciente.
    - Contadores EXACTOS (comparaciones/desplazamientos) en todos los casos:
        · {1, 2, 3, 5, 4, 6, 7} casi ordenado (enunciado): 7 / 1
        · {1, 2, 4, 3, 6, 5, 7} casi ordenado (2 inversiones): 8 / 2
        · {1, 2, 3, 4, 5, 6, 7} ordenado (mejor caso): 6 / 0
        · {7, 6, 5, 4, 3, 2, 1} inverso (peor caso): 21 / 21
        · {-3, 0, -1, 7} con negativos: 4 / 1
        · {4, 4, 4} todos iguales: 2 / 0
        · {1, 2, 3, -5, 4, 6, 7} clave que llega al inicio: 8 / 3
        · {42} / {} / null: 0 / 0 en todos
    Plan de pruebas obligatorio (10 casos): los 8 anteriores más un solo elemento
      y arreglo nulo (este último sin excepción, mensaje informativo).
"
 ====================================================================================
 */
public class Ejercicio3 {

    /**
     * Contadores del ordenamiento por inserción.
     */
    public static class Estadisticas {

        public int comparaciones = 0;
        public int desplazamientos = 0;

        @Override
        public String toString() {
            return "comparaciones = " + comparaciones
                    + ", desplazamientos = " + desplazamientos;
        }
    }

    // Fila de la tabla comparativa final: [caso, comparaciones, desplazamientos, OK]
    private static final java.util.List<String[]> TABLA = new java.util.ArrayList<>();

    /**
     * INSERTION SORT — ordena insertando cada elemento en el prefijo ordenado.
     *
     * <p>
     * <b>Por qué funciona bien con datos casi ordenados:</b> el bucle interno
     * recorre el prefijo ordenado de derecha a izquierda y se CORTA apenas
     * encuentra un elemento menor o igual a la clave (corte anticipado). Si el
     * arreglo está casi ordenado, cada clave está ya casi en su lugar → muy
     * pocas comparaciones y muy pocos desplazamientos → el total se acerca a
     * O(n). Los desplazamientos equivalen a las inversiones del arreglo: casi
     * ordenado = pocas inversiones. Casos: ordenado → n-1 comparaciones / 0
     * desplazamientos (O(n)); inverso → n(n-1)/2 de ambos (O(n²)). Es
     * adaptativo y cada desplazamiento es solo UNA escritura (vs 3 de un swap),
     * por eso los híbridos reales (TimSort) lo usan.
     *
     * <p>
     * Convención de conteo: una comparación por cada evaluación de
     * {@code array[j] > clave}; un desplazamiento por cada movimiento de un
     * elemento una posición a la derecha. La escritura final de la clave no
     * cuenta.
     *
     * @param array arreglo a ordenar in-place (null se ignora sin excepción)
     * @return contadores de comparaciones y desplazamientos
     */
    public static Estadisticas insertionSort(int[] array) {
        Estadisticas stats = new Estadisticas();

        // Caso borde: nulo → sin excepción, solo mensaje informativo
        if (array == null) {
            System.out.println("  [Insertion] arreglo nulo → nada que ordenar.");
            return stats;
        }

        System.out.println("  [Insertion] original: " + java.util.Arrays.toString(array));

        // Caso borde: vacío o un solo elemento → ya está "ordenado"
        int n = array.length;
        if (n < 2) {
            System.out.println("  [Insertion] menos de 2 elementos → ya está ordenado.");
            return stats;
        }

        // El prefijo [0..i-1] ya está ordenado; se inserta array[i] en su lugar
        for (int i = 1; i < n; i++) {
            int clave = array[i];
            int j = i - 1;
            int compPaso = 0;
            int despPaso = 0;
            StringBuilder traza = new StringBuilder();

            // Bucle de desplazamiento: recorre el prefijo ordenado de derecha a
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
                // comparación que corta el recorrido (elemento ya en su lugar)
                stats.comparaciones++;
                compPaso++;
                if (traza.length() > 0) {
                    traza.append("; ");
                }
                traza.append(array[j]).append(" <= ").append(clave)
                        .append(" → corte anticipado");
            } else {
                // Llegó al inicio del arreglo: la clave es la menor hasta ahora
                if (traza.length() > 0) {
                    traza.append("; ");
                }
                traza.append("llegó al inicio (j < 0)");
            }

            // Inserción de la clave en el hueco (NO cuenta como desplazamiento)
            array[j + 1] = clave;

            System.out.println("    Paso i=" + i + " (clave = " + clave + "): " + traza);
            String compTxt = compPaso + (compPaso == 1 ? " comparación" : " comparaciones");
            String despTxt = despPaso + (despPaso == 1 ? " desplazamiento" : " desplazamientos");
            if (despPaso == 0) {
                System.out.println("      → ya estaba en su lugar (0 desplazamientos): "
                        + java.util.Arrays.toString(array)
                        + "  [" + compTxt + "]");
            } else {
                System.out.println("      → clave insertada en pos " + (j + 1) + ": "
                        + java.util.Arrays.toString(array)
                        + "  [" + compTxt + ", " + despTxt + "]");
            }
        }

        System.out.println("  [Insertion] arreglo ordenado: " + java.util.Arrays.toString(array));
        System.out.println("  [Insertion] TOTAL: " + stats);
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
     * Ejecuta un caso: ordena, verifica contra Arrays.sort y valida los
     * contadores.
     *
     * @param expComparaciones comparaciones exactas esperadas (null = no
     * validar)
     * @param expDesplazamientos desplazamientos exactos esperados (null = no
     * validar)
     */
    private static void probar(String descripcion, int[] original,
            Integer expComparaciones, Integer expDesplazamientos) {
        System.out.println("=== " + descripcion + " ===");

        // Caso borde: nulo → sin excepción
        if (original == null) {
            System.out.println("Original: null");
            Estadisticas stats = insertionSort(null);
            System.out.println("Verificación: sin excepción → OK ✓");
            System.out.println();
            TABLA.add(new String[]{descripcion, String.valueOf(stats.comparaciones),
                String.valueOf(stats.desplazamientos), "OK"});
            return;
        }

        System.out.println("Original: " + java.util.Arrays.toString(original));

        // Resultado esperado: copia ordenada con Arrays.sort
        int[] esperado = original.clone();
        java.util.Arrays.sort(esperado);

        int[] paraOrdenar = original.clone();
        Estadisticas stats = insertionSort(paraOrdenar);

        boolean igualQueSort = java.util.Arrays.equals(paraOrdenar, esperado);
        boolean ordenado = esOrdenado(paraOrdenar);
        boolean contadoresOk = (expComparaciones == null
                || stats.comparaciones == expComparaciones)
                && (expDesplazamientos == null
                || stats.desplazamientos == expDesplazamientos);

        boolean todoOk = igualQueSort && ordenado && contadoresOk;

        System.out.println("Verificación vs Arrays.sort: "
                + (igualQueSort ? "OK ✓" : "ERROR ✗ (esperado "
                        + java.util.Arrays.toString(esperado) + ")"));
        System.out.println("Verificación orden no decreciente: "
                + (ordenado ? "OK ✓" : "ERROR ✗"));
        if (expComparaciones != null || expDesplazamientos != null) {
            System.out.println("Verificación contadores (esperado "
                    + (expComparaciones != null ? expComparaciones : "-") + " / "
                    + (expDesplazamientos != null ? expDesplazamientos : "-")
                    + "): " + (contadoresOk ? "OK ✓" : "ERROR ✗"));
        }
        System.out.println();

        TABLA.add(new String[]{descripcion,
            String.valueOf(stats.comparaciones),
            String.valueOf(stats.desplazamientos),
            todoOk ? "OK" : "ERROR"});
    }

    public static void main(String[] args) {
        // Caso 1: el arreglo del enunciado (casi ordenado, 1 sola inversión)
        int[] numeros = {1, 2, 3, 5, 4, 6, 7};
        probar("CASO 1: casi ordenado (enunciado)", numeros, 7, 1);

        // Caso 2: casi ordenado con dos inversiones
        probar("CASO 2: casi ordenado (2 inversiones)", new int[]{1, 2, 4, 3, 6, 5, 7}, 8, 2);

        // Caso 3: ya ordenado → mejor caso (n-1 comparaciones, 0 desplazamientos)
        probar("CASO 3: ordenado (mejor caso)", new int[]{1, 2, 3, 4, 5, 6, 7}, 6, 0);

        // Caso 4: orden inverso → peor caso (n(n-1)/2 de todo)
        probar("CASO 4: inverso (peor caso)", new int[]{7, 6, 5, 4, 3, 2, 1}, 21, 21);

        // Caso 5: con negativos
        probar("CASO 5: con negativos", new int[]{-3, 0, -1, 7}, 4, 1);

        // Caso 6: todos iguales (corte anticipado en la primera comparación)
        probar("CASO 6: todos iguales", new int[]{4, 4, 4}, 2, 0);

        // Caso 7: clave que debe desplazar hasta el inicio del arreglo (j < 0)
        probar("CASO 7: clave al inicio", new int[]{1, 2, 3, -5, 4, 6, 7}, 8, 3);

        // Caso 8: un solo elemento
        probar("CASO 8: un solo elemento", new int[]{42}, 0, 0);

        // Caso 9: arreglo vacío
        probar("CASO 9: arreglo vacío", new int[]{}, 0, 0);

        // Caso 10: arreglo nulo → sin excepción
        probar("CASO 10: arreglo nulo", null, 0, 0);

        // Tabla comparativa final
        System.out.println("=== TABLA COMPARATIVA (comparaciones / desplazamientos) ===");
        System.out.printf("%-36s | %12s | %14s | %s%n",
                "CASO", "COMPARACIONES", "DESPLAZAMIENTOS", "VERIFICACIÓN");
        System.out.println("--------------------------------------------------------------"
                + "------------------");
        for (String[] fila : TABLA) {
            System.out.printf("%-36s | %12s | %14s | %s%n",
                    fila[0], fila[1], fila[2], fila[3]);
        }

        // Análisis: por qué funciona bien con casi ordenados
        System.out.println();
        System.out.println("=== ANÁLISIS: POR QUÉ RINDE CON DATOS CASI ORDENADOS ===");
        System.out.println("El bucle interno se CORTA apenas encuentra un elemento <= clave:");
        System.out.println("si cada clave está casi en su lugar, recorre muy pocos lugares.");
        System.out.println("Los desplazamientos = inversiones del arreglo:");
        System.out.println("  - ordenado   {1..7}      : 6 comp / 0 desp  → O(n)");
        System.out.println("  - casi ord.  {1,2,3,5,4,6,7}: 7 comp / 1 desp  → casi O(n)");
        System.out.println("  - inverso    {7..1}      : 21 comp / 21 desp → O(n²)");
        System.out.println("→ Se adapta al orden existente (adaptativo): con poca");
        System.out.println("  inversión hace poco trabajo; cada desplazamiento es solo");
        System.out.println("  UNA escritura (vs 3 de un swap). Por eso TimSort (Arrays.sort)");
        System.out.println("  usa insertion sort para arreglos chicos o casi ordenados.");
    }
}

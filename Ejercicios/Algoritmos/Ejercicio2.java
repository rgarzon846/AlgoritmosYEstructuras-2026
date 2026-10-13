/**
 * Algoritmo que busca un elemento dentro de un vector DESORDENADO.
 *
 * ===========================================================================
 * ESTRATEGIA ELEGIDA
 * ===========================================================================
 *
 * Recorrido secuencial de izquierda a derecha (BÚSQUEDA LINEAL), deteniendo
 * el recorrido en cuanto se encuentra el elemento o se llega al final:
 *
 *   para i de 0 hasta n-1:
 *       contador++
 *       si vector[i] == elemento → devolver i
 *   devolver -1   (no existe)
 *
 * ===========================================================================
 * JUSTIFICACIÓN: POR QUÉ ESTA ESTRATEGIA ES LA ADECUADA
 * ===========================================================================
 *
 * 1. Un vector DESORDENADO no permite descartar mitades: sin la garantía
 *    de orden no hay forma de saber en qué mitad podría estar el elemento,
 *    por lo tanto la búsqueda binaria NO aplica.
 *
 * 2. La búsqueda lineal es la única opción que no requiere MODIFICAR el
 *    vector (ordenarlo) ni usar estructuras auxiliares (p. ej. un HashSet
 *    o un mapa índice→valor), preservando los datos intactos.
 *
 * 3. Es ÓPTIMA en el sentido de límite inferior: cualquier algoritmo que
 *    no reordene ni use información adicional sobre los valores debe poder
 *    fallar ante CUALQUIER posición, porque el elemento buscado podría
 *    estar en la posición que aún no se examinó. Por lo tanto, en el peor
 *    caso todo algoritmo correcto debe examinar los n elementos: O(n) es
 *    el mínimo posible, y la búsqueda lineal lo alcanza.
 *
 * ===========================================================================
 * ANÁLISIS DE COMPLEJIDAD — LOS TRES CASOS SÍ DIFEREN ACÁ
 * ===========================================================================
 *
 * (A diferencia de los recorridos COMPLETOS —contar, mínimo— donde los tres
 *  casos coincidían en O(n), aquí el resultado depende de DÓNDE esté el
 *  elemento, porque el algoritmo puede cortar antes de tiempo.)
 *
 * Mejor caso: O(1)
 *   - El elemento está en la PRIMERA posición (índice 0): una sola
 *     comparación y se detiene.
 *
 * Peor caso: O(n)
 *   - El elemento NO EXISTE, o está en la ÚLTIMA posición: hay que
 *     recorrer las n posiciones hasta agotar el vector.
 *
 * Caso promedio: O(n)
 *   - En promedio se recorren ~n/2 posiciones (el elemento podría estar
 *     en cualquiera de las n posiciones con igual probabilidad, o no
 *     existir).
 *   - n/2 = (1/2)·n: la constante 1/2 NO cambia el orden de crecimiento,
 *     por lo tanto el caso promedio también es O(n).
 *
 * Espacial: O(1)
 *   - Solo variables de índice, contador y banderas; sin estructuras
 *     auxiliares.
 *
 * Contraste con la búsqueda binaria:
 *   - Binaria: O(log n) — pero REQUIERE vector ordenado (y el enunciado
 *     garantiza que está desordenado).
 *   - Lineal: O(n) — funciona sin orden, sin modificar el vector y sin
 *     memoria extra. El trade-off es: ¿ya tenés el orden? → binaria;
 *     ¿datos crudos? → lineal.
 *
 * ===========================================================================
 * CONTADOR DE POSICIONES
 * ===========================================================================
 *
 * El programa informa cuántas posiciones fueron recorridas hasta encontrar
 * el elemento o determinar que no existe, y muestra la traza de cada
 * posición visitada (índice y valor comparado).
 */
public class Ejercicio2 {

    /**
     * Busca {@code elemento} en {@code vector} (desordenado) con búsqueda
     * lineal. Informa la traza de cada posición y el total recorrido.
     *
     * @param vector   arreglo de enteros desordenado (puede ser null/vacío)
     * @param elemento valor a buscar
     * @return índice del elemento encontrado, o -1 si no existe
     */
    public static int buscarElemento(int[] vector, int elemento) {
        // Caso borde: sin elementos → 0 posiciones recorridas, no existe
        if (vector == null || vector.length == 0) {
            System.out.println("  Vector nulo o vacío → 0 posiciones recorridas.");
            System.out.println("  El elemento " + elemento + " NO existe.");
            return -1;
        }

        int posicionesRecorridas = 0;

        // Recorrido secuencial: se corta en cuanto se encuentra
        for (int i = 0; i < vector.length; i++) {
            posicionesRecorridas++;
            System.out.println("  [posición " + i + "] comparo " + vector[i]
                    + " vs " + elemento);

            if (vector[i] == elemento) {
                System.out.println("  ¡Encontrado! posiciones recorridas = "
                        + posicionesRecorridas + " de " + vector.length);
                return i;
            }
        }

        // Se agotó el vector sin coincidencias
        System.out.println("  Fin del vector → posiciones recorridas = "
                + posicionesRecorridas + " de " + vector.length);
        System.out.println("  El elemento " + elemento + " NO existe.");
        return -1;
    }

    // ======================================================================
    // PRUEBAS
    // ======================================================================

    private static void probar(String descripcion, int[] vector, int elemento, int esperado) {
        System.out.println("=== " + descripcion + " ===");
        System.out.println("Vector (desordenado): " + java.util.Arrays.toString(vector)
                + "  |  buscar: " + elemento);
        int indice = buscarElemento(vector, elemento);
        System.out.println("Resultado: índice " + indice + " (esperado " + esperado + ") → "
                + (indice == esperado ? "OK ✓" : "ERROR ✗"));
        System.out.println();
    }

    public static void main(String[] args) {
        // Caso 1: elemento en la primera posición → mejor caso O(1)
        probar("CASO 1: primera posición (mejor caso)", new int[]{15, 3, 8, 42, 7, 1}, 15, 0);

        // Caso 2: elemento en la última posición → peor caso O(n)
        probar("CASO 2: última posición (peor caso)", new int[]{9, 6, 2, 8, 5, 3}, 3, 5);

        // Caso 3: elemento ausente → peor caso O(n)
        probar("CASO 3: elemento ausente (peor caso)", new int[]{4, 7, 1, 9, 6}, 5, -1);

        // Caso 4: elemento en el medio → caso promedio
        probar("CASO 4: en el medio (promedio)", new int[]{11, 2, 25, 14, 8, 30, 6}, 14, 3);

        // Caso 5: un solo elemento que coincide
        probar("CASO 5: un solo elemento", new int[]{42}, 42, 0);

        // Caso 6: vector vacío
        probar("CASO 6: vector vacío", new int[]{}, 10, -1);

        // Resumen
        System.out.println("=== RESUMEN ===");
        System.out.println("Estrategia: búsqueda lineal (recorrido secuencial).");
        System.out.println("Mejor caso : O(1)   → elemento en la posición 0.");
        System.out.println("Peor caso  : O(n)   → no existe o está al final.");
        System.out.println("Promedio   : O(n)   → ~n/2 posiciones (constante no cuenta).");
        System.out.println("Espacial   : O(1)   → sin estructuras auxiliares.");
        System.out.println("Contraste : binaria O(log n) requiere orden; lineal no.");
    }
}

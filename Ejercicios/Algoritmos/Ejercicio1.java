/**
 * Algoritmo que encuentra el valor MÍNIMO de un vector de números enteros.
 *
 * ===========================================================================
 * ESTRATEGIA ELEGIDA
 * ===========================================================================
 *
 * Recorrido secuencial con UNA SOLA PASADA, manteniendo una variable
 * `minimo` que se actualiza cada vez que se encuentra un elemento menor
 * que el actual.
 *
 *   minimo = vector[0]
 *   para i de 1 hasta n-1:
 *       si vector[i] < minimo → minimo = vector[i]
 *
 * ===========================================================================
 * JUSTIFICACIÓN: POR QUÉ ESTA ESTRATEGIA ES LA CORRECTA
 * ===========================================================================
 *
 * 1. El mínimo es una propiedad GLOBAL: su valor depende de TODOS los
 *    elementos del vector. No se puede conocer sin haberlos observado.
 *
 * 2. No hay forma de descartar partes del vector sin examinarlas, porque
 *    cualquier posición no visitada podría contener un valor menor que el
 *    mínimo parcial. (A diferencia de la búsqueda binaria, donde el orden
 *    permite eliminar mitades del espacio de búsqueda.)
 *
 * 3. Un solo recorrido es a la vez SUFICIENTE y NECESARIO:
 *    - Suficiente (correctitud): tras cada paso, `minimo` guarda el menor
 *      de todos los elementos visitados hasta ese momento; al llegar al
 *      final, es el menor de todo el vector. Invariante:
 *      "minimo = min(vector[0..i])".
 *    - Necesario (límite inferior): el mínimo no se conoce sin haber
 *      observado todos los elementos, porque el que falta podría ser
 *      menor. Ninguna posición puede saltarse.
 *
 * ===========================================================================
 * COMPLEJIDAD ESPERADA
 * ===========================================================================
 *
 * Temporal: O(n)
 *   - Mejor caso: O(n)   (recorrer el vector entero es inevitable)
 *   - Peor caso: O(n)    (elementos en orden decreciente: se actualiza
 *                        en cada paso)
 *   - Caso promedio: O(n)
 *   Los tres casos coinciden: una sola pasada de n elementos, una
 *   comparación por elemento.
 *
 * Espacial: O(1)
 *   - Solo la variable `minimo` (y los índices del bucle). No se usa
 *     memoria adicional proporcional a n.
 *
 * ===========================================================================
 * ¿POR QUÉ NO ES NECESARIO ORDENAR EL VECTOR?
 * ===========================================================================
 *
 * 1. COSTO: ordenar costaría O(n log n) (o peor) para resolver un problema
 *    que se resuelve en O(n) con una sola pasada. Sería un desperdicio
 *    computacional.
 *
 * 2. EFECTO COLATERAL: ordenar MODIFICARÍA el vector original, alterando
 *    los datos que el llamador podría necesitar intactos (el mínimo debe
 *    ser una operación de solo lectura).
 *
 * 3. IRRELEVANCIA DEL ORDEN: el mínimo es el MISMO valor esté el vector
 *    ordenado o no. El orden no aporta información nueva que ayude a
 *    encontrarlo; una sola pasada ya lo identifica sin necesidad de
 *    reorganizar nada.
 *
 * (Nota: ordenar solo serviría para obtener también el máximo "gratis"
 *  con vector[0] y vector[n-1], pero el enunciado solo pide el mínimo.)
 */
public class Ejercicio1 {

    /**
     * Encuentra el valor mínimo de un vector de enteros con una sola pasada.
     *
     * @param vector arreglo de enteros (no debe ser null ni vacío)
     * @return el valor mínimo del vector
     * @throws IllegalArgumentException si el vector es null o está vacío
     *         (no existe mínimo en un conjunto vacío)
     */
    public static int encontrarMinimo(int[] vector) {
        // Caso borde: sin elementos no hay mínimo → se señala con excepción
        if (vector == null || vector.length == 0) {
            throw new IllegalArgumentException(
                    "El vector es null o está vacío: no existe un valor mínimo.");
        }

        // Invariante: 'minimo' siempre es el menor de los elementos
        // visitados hasta el momento (vector[0..i])
        int minimo = vector[0];
        int posicionMinimo = 0;
        System.out.println("  Inicial: minimo = " + minimo + " (posición 0)");

        // Una sola pasada: n-1 comparaciones
        for (int i = 1; i < vector.length; i++) {
            if (vector[i] < minimo) {
                System.out.println("  [posición " + i + "] " + vector[i]
                        + " < " + minimo + " → nuevo mínimo");
                minimo = vector[i];
                posicionMinimo = i;
            } else {
                System.out.println("  [posición " + i + "] " + vector[i]
                        + " ≥ " + minimo + " → sin cambio");
            }
        }

        System.out.println("  Fin del recorrido (" + vector.length
                + " elementos visitados) → mínimo = " + minimo
                + " en posición " + posicionMinimo);
        return minimo;
    }

    // ======================================================================
    // PRUEBAS
    // ======================================================================

    private static void probar(String descripcion, int[] vector) {
        System.out.println("=== " + descripcion + " ===");
        System.out.println("Vector: " + java.util.Arrays.toString(vector));
        try {
            int minimo = encontrarMinimo(vector);
            System.out.println("Resultado: mínimo = " + minimo);
        } catch (IllegalArgumentException e) {
            System.out.println("Excepción esperada: " + e.getMessage());
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // Caso 1: mínimo en el medio (caso general)
        probar("CASO 1: mínimo en el medio", new int[]{7, 2, 9, 4, 6});

        // Caso 2: mínimo al final (peor caso: actualiza en cada paso)
        probar("CASO 2: mínimo al final (decreciente)", new int[]{9, 7, 5, 3, 1});

        // Caso 3: valores negativos
        probar("CASO 3: valores negativos", new int[]{-3, -1, -7, -2, -5});

        // Caso 4: todos los elementos iguales
        probar("CASO 4: todos iguales", new int[]{4, 4, 4, 4});

        // Caso 5: un solo elemento
        probar("CASO 5: un solo elemento", new int[]{42});

        // Caso 6: vector vacío (excepción)
        probar("CASO 6: vector vacío", new int[]{});

        // Resumen
        System.out.println("=== RESUMEN ===");
        System.out.println("Estrategia: una sola pasada con variable `minimo`.");
        System.out.println("Temporal: O(n) — mejor, peor y promedio coinciden.");
        System.out.println("Espacial: O(1) — solo la variable `minimo`.");
        System.out.println("No hace falta ordenar: O(n log n) > O(n),");
        System.out.println("modificaría el vector y el orden no aporta nada.");
    }
}

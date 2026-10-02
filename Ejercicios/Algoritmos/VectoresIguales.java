/**
 * Algoritmo que determina si dos vectores de enteros son IGUALES
 * (misma longitud y mismo valor en cada posición).
 *
 * ===========================================================================
 * ESTRATEGIA ELEGIDA
 * ===========================================================================
 *
 * 1. Comparar PRIMERO las longitudes: si difieren, los vectores no pueden
 *    ser iguales → corte inmediato, sin recorrer ningún elemento.
 * 2. Recorrer ambos vectores SIMULTÁNEAMENTE posición por posición,
 *    con CORTE ANTICIPADO: el algoritmo finaliza apenas detecte una
 *    diferencia (devuelve false sin recorrer el resto).
 *
 *   si a.length != b.length → false            (0 comparaciones de elementos)
 *   para i de 0 hasta n-1:
 *       comparaciones++
 *       si a[i] != b[i] → false                (corte anticipado)
 *   devolver true                               (se confirmó todo)
 *
 * ===========================================================================
 * JUSTIFICACIÓN: POR QUÉ ESTA ESTRATEGIA ES LA ADECUADA
 * ===========================================================================
 *
 * 1. La igualdad se define POSICIÓN A POSICIÓN: dos vectores son iguales
 *    sii tienen la misma longitud y, en cada índice i, a[i] == b[i].
 *    Por lo tanto, basta UNA diferencia para probar la DESIGUALDAD
 *    (búsqueda de un contraejemplo), mientras que la IGUALDAD solo puede
 *    confirmarse al recorrer todas las posiciones.
 *
 * 2. El corte anticipado es lo que permite el mejor caso O(1): una vez
 *    probada la diferencia, no hay ninguna razón para seguir comparando
 *    — el resultado ya es concluyente.
 *
 * 3. Comparar posición a posición:
 *    - evita estructuras auxiliares (O(1) de memoria), y
 *    - respeta el ORDEN de los elementos: dos vectores con los mismos
 *      elementos en distinto orden NO son iguales (p. ej. {1,2} vs {2,1}).
 *
 * ===========================================================================
 * ANÁLISIS DE COMPLEJIDAD — MEJOR, PEOR Y CASO PROMEDIO
 * ===========================================================================
 *
 * Mejor caso: O(1)
 *   - Longitudes distintas → falso inmediato, 0 comparaciones de elementos.
 *   - O diferencia en la PRIMERA posición → 1 comparación.
 *
 * Peor caso: O(n)
 *   - Vectores IDÉNTICOS: hay que recorrer las n posiciones para
 *     confirmar la igualdad (no hay corte posible).
 *   - O diferencia recién en la ÚLTIMA posición.
 *   NOTA CONTRAINTUITIVA: los vectores idénticos son el PEOR caso,
 *   porque la igualdad solo se confirma al final, mientras que la
 *   desigualdad admite corte anticipado en cualquier momento.
 *
 * Caso promedio: O(n)
 *   - Si los vectores difieren, la diferencia suele aparecer en una
 *     posición intermedia: se recorre una fracción proporcional a n.
 *   - Si son iguales (u ocurren ambas situaciones con comparable
 *     frecuencia), el recorrido es de n. En todos los casos el orden de
 *     crecimiento es lineal.
 *
 * Espacial: O(1)
 *   - Solo variables de índice y contadores; sin estructuras auxiliares.
 *
 * ===========================================================================
 * CONTADOR DE POSICIONES COMPARADAS
 * ===========================================================================
 *
 * El programa informa cuántas posiciones fueron comparadas antes del corte
 * y si el corte fue anticipado (desigualdad detectada antes del final).
 */
public class VectoresIguales {

    /**
     * Determina si dos vectores son iguales, con corte anticipado al
     * primer elemento distinto. Informa posiciones comparadas.
     *
     * @param a primer vector (puede ser null)
     * @param b segundo vector (puede ser null)
     * @return true si misma longitud y mismo valor en cada posición
     */
    public static boolean vectoresIguales(int[] a, int[] b) {
        // Caso borde: null → solo iguales si AMBOS son null
        if (a == null || b == null) {
            boolean iguales = (a == b);
            System.out.println("  Algún vector es null → "
                    + (iguales ? "ambos null: iguales (0 comparaciones)"
                               : "solo uno es null: distintos (0 comparaciones)"));
            return iguales;
        }

        // 1) Comparación de longitudes: corte inmediato sin recorrer elementos
        if (a.length != b.length) {
            System.out.println("  Longitudes distintas (" + a.length + " vs "
                    + b.length + ") → distintos (0 comparaciones de elementos)");
            return false;
        }

        int n = a.length;
        int comparaciones = 0;

        // 2) Recorrido simultáneo con CORTE ANTICIPADO
        for (int i = 0; i < n; i++) {
            comparaciones++;
            if (a[i] != b[i]) {
                System.out.println("  [posición " + i + "] " + a[i] + " != "
                        + b[i] + " → CORTE ANTICIPADO en " + comparaciones
                        + " de " + n + " posiciones");
                return false;
            }
            System.out.println("  [posición " + i + "] " + a[i] + " == " + b[i]);
        }

        // Se recorrieron todas las posiciones sin diferencias
        System.out.println("  Fin del recorrido → iguales ("
                + comparaciones + " de " + n + " posiciones comparadas)");
        return true;
    }

    // ======================================================================
    // PRUEBAS
    // ======================================================================

    private static void probar(String descripcion, int[] a, int[] b, boolean esperado) {
        System.out.println("=== " + descripcion + " ===");
        System.out.println("a = " + java.util.Arrays.toString(a)
                + "   b = " + java.util.Arrays.toString(b));
        boolean resultado = vectoresIguales(a, b);
        System.out.println("Resultado: " + resultado + " (esperado " + esperado
                + ") → " + (resultado == esperado ? "OK ✓" : "ERROR ✗"));
        System.out.println();
    }

    public static void main(String[] args) {
        // Caso 1: diferencia en la primera posición → mejor caso O(1)
        probar("CASO 1: diferencia en primera posición (mejor caso)",
                new int[]{5, 2, 8}, new int[]{9, 2, 8}, false);

        // Caso 2: vectores idénticos → peor caso O(n)
        probar("CASO 2: idénticos (peor caso)",
                new int[]{1, 3, 5, 7, 9}, new int[]{1, 3, 5, 7, 9}, true);

        // Caso 3: diferencia en la última posición → peor caso O(n)
        probar("CASO 3: diferencia en última posición (peor caso)",
                new int[]{1, 3, 5, 7, 9}, new int[]{1, 3, 5, 7, 8}, false);

        // Caso 4: misma longitud, diferencia intermedia → caso promedio
        probar("CASO 4: diferencia intermedia (promedio)",
                new int[]{4, 7, 1, 9}, new int[]{4, 7, 2, 9}, false);

        // Caso 5: longitudes distintas → corte inmediato
        probar("CASO 5: longitudes distintas",
                new int[]{1, 2, 3}, new int[]{1, 2}, false);

        // Caso 6: mismos elementos en distinto orden → NO son iguales
        probar("CASO 6: mismo orden importa",
                new int[]{1, 2, 3}, new int[]{3, 2, 1}, false);

        // Caso 7: ambos vacíos → iguales
        probar("CASO 7: ambos vacíos",
                new int[]{}, new int[]{}, true);

        // Caso 8: null vs vector
        probar("CASO 8: null vs vector",
                null, new int[]{1}, false);

        // Resumen
        System.out.println("=== RESUMEN ===");
        System.out.println("Estrategia: comparación por posición con CORTE ANTICIPADO.");
        System.out.println("Mejor caso : O(1) → longitudes distintas o diferencia en i=0.");
        System.out.println("Peor caso  : O(n) → idénticos (confirmar igualdad exige todo).");
        System.out.println("Promedio   : O(n) → la diferencia aparece en zona intermedia.");
        System.out.println("Espacial   : O(1) → sin estructuras auxiliares.");
        System.out.println("Clave: UNA diferencia basta para probar desigualdad;");
        System.out.println("       la igualdad solo se confirma al final del recorrido.");
    }
}

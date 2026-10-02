/**
 * DOS versiones para invertir un vector de enteros.
 *
 * VERSIÓN 1 — Con vector auxiliar (no modifica el original).
 * VERSIÓN 2 — In-place (invierte el mismo vector, sin memoria adicional
 *             significativa).
 *
 * ===========================================================================
 * COMPARACIÓN ENTRE AMBAS SOLUCIONES
 * ===========================================================================
 *
 * 1) VENTAJAS Y DESVENTAJAS
 *
 *    Versión 1 — Con vector auxiliar:
 *      Ventajas:
 *      - NO modifica el vector original (operación no mutadora): el
 *        llamador conserva sus datos intactos y puede usarlos después.
 *      - Devuelve un vector nuevo y limpio: encaja bien en pipelines
 *        funcionales o cuando se necesita mantener el historial.
 *      Desventajas:
 *      - Necesita memoria adicional O(n): duplica el espacio ocupado.
 *      - Incluye una fase de COPIA de n elementos además de la inversión.
 *      - Para vectores muy grandes puede ser costoso (y el GC trabaja más).
 *
 *    Versión 2 — In-place:
 *      Ventajas:
 *      - Memoria adicional O(1): solo el par de variables del swap.
 *      - Ideal para vectores grandes o entornos con memoria limitada.
 *      - Una sola pasada sobre la mitad del vector.
 *      Desventajas:
 *      - MODIFICA el vector original (operación mutadora): si el llamador
 *        necesitaba conservar el orden previo, debe copiarlo él mismo
 *        (lo que anularía el ahorro de memoria).
 *      - Efecto colateral: puede sorprender si no se documenta bien.
 *
 * 2) COMPLEJIDAD TEMPORAL
 *
 *    Versión 1: O(n) en mejor, peor y caso promedio → se copian y reordenan
 *      los n elementos siempre (n copias).
 *    Versión 2: O(n) en mejor, peor y caso promedio → se realizan n/2
 *      intercambios; n/2 = (1/2)·n es orden lineal (la constante no
 *      cambia el orden de crecimiento).
 *
 * 3) COMPLEJIDAD ESPACIAL
 *
 *    Versión 1: O(n) — crea un vector nuevo del mismo tamaño.
 *    Versión 2: O(1) — solo variables temporales para el swap.
 *
 * 4) CONCLUSIÓN: ¿CUÁNDO CONVIENE CADA VERSIÓN?
 *
 *    - Conviene la versión 1 (auxiliar) cuando hay que PRESERVAR el vector
 *      original, cuando la memoria es abundante o el vector es chico.
 *    - Conviene la versión 2 (in-place) cuando la memoria es limitada o el
 *      vector es muy grande, y no importa modificar los datos originales.
 *    - Es el intercambio clásico tiempo/efectos ↔ espacio: ambas son O(n)
 *      en tiempo, pero la espacial va de O(n) a O(1).
 *
 * ===========================================================================
 */
import java.util.Arrays;

public class InversionVector {

    // ======================================================================
    // VERSIÓN 1 — Con vector auxiliar (el original queda intacto)
    // ======================================================================

    /**
     * Devuelve un NUEVO vector con los elementos en orden inverso.
     * El vector original NO se modifica.
     *
     * Complejidad: O(n) tiempo / O(n) espacio.
     *
     * @param v vector a invertir (puede ser null)
     * @return nuevo vector invertido, o null si v es null
     */
    public static int[] invertirConAuxiliar(int[] v) {
        if (v == null) {
            System.out.println("  Vector null → se devuelve null.");
            return null;
        }
        if (v.length == 0) {
            System.out.println("  Vector vacío → nada que copiar.");
            return new int[0];
        }

        int n = v.length;
        int[] invertido = new int[n]; // memoria adicional O(n)

        System.out.println("  Copiando hacia el vector auxiliar:");
        for (int i = 0; i < n; i++) {
            // El elemento i del original va a la posición (n-1-i) del nuevo
            invertido[n - 1 - i] = v[i];
            System.out.println("    invertido[" + (n - 1 - i) + "] = v[" + i
                    + "] = " + v[i] + "  → auxiliar: "
                    + Arrays.toString(invertido));
        }

        System.out.println("  Vector original intacto: " + Arrays.toString(v));
        return invertido;
    }

    // ======================================================================
    // VERSIÓN 2 — In-place (sin memoria adicional significativa)
    // ======================================================================

    /**
     * Invierte el MISMO vector intercambiando los extremos hacia el centro.
     * El vector original queda MODIFICADO.
     *
     * Complejidad: O(n) tiempo / O(1) espacio.
     *
     * @param v vector a invertir in-place (se modifica; null se ignora)
     * @return el mismo vector invertido (para encadenar llamadas)
     */
    public static int[] invertirInPlace(int[] v) {
        if (v == null) {
            System.out.println("  Vector null → nada que hacer.");
            return null;
        }
        if (v.length == 0) {
            System.out.println("  Vector vacío → nada que intercambiar.");
            return v;
        }

        int n = v.length;
        int intercambios = 0;

        // Recorre solo la MITAD del vector: i < n/2
        for (int i = 0; i < n / 2; i++) {
            int j = n - 1 - i;          // extremo opuesto
            int temporal = v[i];        // swap usando solo O(1) memoria
            v[i] = v[j];
            v[j] = temporal;
            intercambios++;
            System.out.println("    swap pos " + i + " ↔ " + j + " ("
                    + v[i] + " ↔ " + v[j] + ")  → vector: " + Arrays.toString(v));
        }

        // Si n es impar, el elemento del medio queda solo: no hay nada que hacer
        if (n % 2 != 0) {
            System.out.println("    Elemento del medio en pos " + (n / 2)
                    + " queda fijo (n impar).");
        }

        System.out.println("  " + intercambios + " intercambios realizados.");
        return v;
    }

    // ======================================================================
    // PRUEBAS
    // ======================================================================

    private static void probar(String descripcion, int[] original) {
        System.out.println("=== " + descripcion + " ===");
        System.out.println("Vector original: " + Arrays.toString(original));

        // Versión 1: con auxiliar (se le pasa una copia: el método nunca
        // modifica su argumento, pero así queda explícito el contrato)
        System.out.println("[Versión 1 — con vector auxiliar]");
        int[] copia = invertirConAuxiliar(original == null ? null : original.clone());
        int[] esperado = (original == null) ? null : new int[original.length];
        if (original != null) {
            for (int i = 0; i < original.length; i++) {
                esperado[i] = original[original.length - 1 - i];
            }
        }
        boolean ok1 = Arrays.equals(copia, esperado);
        System.out.println("  Resultado: " + Arrays.toString(copia)
                + (ok1 ? "  OK ✓" : "  ERROR ✗"));

        // Versión 2: in-place
        System.out.println("[Versión 2 — in-place]");
        int[] paraInPlace = (original == null) ? null : original.clone();
        int[] resultado = invertirInPlace(paraInPlace);
        boolean ok2 = Arrays.equals(resultado, esperado);
        System.out.println("  Resultado: " + Arrays.toString(resultado)
                + (ok2 ? "  OK ✓" : "  ERROR ✗"));

        // Verificar equivalencia entre versiones.
        // Nota: a ambas versiones se les pasa una copia (clone): la Versión 1
        // nunca modifica su argumento y la Versión 2 sí, así se preserva el
        // vector de referencia para comparar.
        System.out.println("  Ambas versiones coinciden: "
                + (ok1 && ok2 ? "SÍ ✓" : "NO ✗"));
        System.out.println();
    }

    public static void main(String[] args) {
        // Caso 1: vector general
        probar("CASO 1: vector general", new int[]{1, 2, 3, 4, 5});

        // Caso 2: cantidad PAR de elementos
        probar("CASO 2: cantidad par", new int[]{9, 7, 5, 3});

        // Caso 3: un solo elemento
        probar("CASO 3: un solo elemento", new int[]{42});

        // Caso 4: vector vacío
        probar("CASO 4: vector vacío", new int[]{});

        // Caso 5: dos elementos
        probar("CASO 5: dos elementos", new int[]{8, 4});

        // Caso 6: null
        probar("CASO 6: vector null", null);

        // Resumen
        System.out.println("=== RESUMEN ===");
        System.out.println("Versión 1 (auxiliar): O(n) tiempo / O(n) espacio.");
        System.out.println("  + preserva el original   - copia extra de memoria");
        System.out.println("Versión 2 (in-place) : O(n) tiempo / O(1) espacio.");
        System.out.println("  + sin memoria extra     - modifica el original");
        System.out.println("Ambas: O(n) en mejor, peor y caso promedio.");
    }
}

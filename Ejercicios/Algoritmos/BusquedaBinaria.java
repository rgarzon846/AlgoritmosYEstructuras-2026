/**
 * Búsqueda Binaria sobre un arreglo de enteros ordenados de forma creciente.
 *
 * ¿POR QUÉ ES MÁS EFICIENTE QUE LA BÚSQUEDA LINEAL CON DATOS ORDENADOS?
 * ----------------------------------------------------------------------
 * La búsqueda binaria explota la propiedad de orden del arreglo: en cada
 * iteración compara el objetivo contra el elemento central y, según el
 * resultado, DESCARTA LA MITAD del arreglo donde el objetivo no puede estar.
 * Es decir, reduce el espacio de búsqueda a la mitad en cada paso.
 *
 * La búsqueda lineal, en cambio, recorre los elementos uno por uno sin poder
 * aprovechar el orden: en el peor caso debe examinar los n elementos.
 *
 * COMPLEJIDAD ESPERADA:
 *  - Tiempo: O(log n) → el arreglo pasa de n, a n/2, a n/4, a n/8... hasta
 *    quedar con 1 elemento. La cantidad de comparaciones crece
 *    logarítmicamente con el tamaño del arreglo.
 *  - Espacio: O(1) en la versión iterativa (solo variables de apoyo).
 *  - Contraste: la búsqueda lineal es O(n) en tiempo y O(1) en espacio.
 *
 * Ejemplo: con n = 1.000.000 de elementos, la binaria hace a lo sumo
 * ~20 comparaciones, mientras que la lineal puede llegar a 1.000.000.
 */
public class BusquedaBinaria {

    /**
     * Devuelve el índice del objetivo en el arreglo, o -1 si no existe.
     *
     * @param arreglo  arreglo de enteros ordenado de forma creciente
     * @param objetivo valor a buscar
     * @return índice del objetivo, o -1 si no se encuentra
     */
    public static int busquedaBinaria(int[] arreglo, int objetivo) {
        // Caso borde: arreglo vacío
        if (arreglo == null || arreglo.length == 0) {
            System.out.println("  [Arreglo vacío o nulo] No hay elementos para buscar.");
            return -1;
        }

        // Variables de búsqueda
        int izquierda = 0;              // límite inferior (inclusive)
        int derecha = arreglo.length - 1; // límite superior (inclusive)
        int paso = 1;

        System.out.println("Búsqueda binaria de " + objetivo
                + " en arreglo de tamaño " + arreglo.length);
        System.out.println("--------------------------------------------------");

        // Bucle: la ventana [izquierda, derecha] se reduce a la mitad en cada paso
        while (izquierda <= derecha) {
            // Mitad del intervalo actual
            int medio = izquierda + (derecha - izquierda) / 2;
            // Nota: (izquierda + derecha) / 2 podría desbordar con índices muy grandes

            int valorMedio = arreglo[medio];

            // Traza paso a paso de las variables de búsqueda
            System.out.println("Paso " + paso + ":");
            System.out.println("  izquierda = " + izquierda
                    + ", derecha = " + derecha
                    + ", medio = " + medio);
            System.out.println("  arreglo[medio] = " + valorMedio
                    + "  ->  comparación: " + valorMedio + " vs " + objetivo);

            if (valorMedio == objetivo) {
                System.out.println("  ¡Coincidencia! Se descarta toda mitad; índice = "
                        + medio);
                System.out.println("--------------------------------------------------");
                return medio;
            } else if (valorMedio < objetivo) {
                // El objetivo está a la derecha del medio: descartamos la mitad izquierda
                System.out.println("  " + valorMedio + " < " + objetivo
                        + " -> el objetivo está a la derecha.");
                System.out.println("  Se descarta la mitad IZQUIERDA: izquierda = "
                        + (medio + 1));
                izquierda = medio + 1;
            } else {
                // El objetivo está a la izquierda del medio: descartamos la mitad derecha
                System.out.println("  " + valorMedio + " > " + objetivo
                        + " -> el objetivo está a la izquierda.");
                System.out.println("  Se descarta la mitad DERECHA: derecha = "
                        + (medio - 1));
                derecha = medio - 1;
            }

            System.out.println("  Ventana resultante: [" + izquierda + ", "
                    + derecha + "]");
            System.out.println("--------------------------------------------------");
            paso++;
        }

        // Se recorrió todo el espacio de búsqueda sin coincidencias
        System.out.println("  Se agotó la ventana de búsqueda [" + izquierda
                + ", " + derecha + "]: el objetivo NO está en el arreglo.");
        return -1;
    }

    public static void main(String[] args) {
        // Caso de prueba 1: objetivo presente en el arreglo
        int[] arreglo1 = {2, 5, 8, 12, 16, 23, 38, 45, 67, 91};
        System.out.println("=== CASO 1: objetivo presente ===");
        int r1 = busquedaBinaria(arreglo1, 23);
        System.out.println("Resultado: índice " + r1 + "\n");

        // Caso de prueba 2: objetivo ausente (entre elementos)
        int[] arreglo2 = {2, 5, 8, 12, 16, 23, 38, 45, 67, 91};
        System.out.println("=== CASO 2: objetivo ausente ===");
        int r2 = busquedaBinaria(arreglo2, 30);
        System.out.println("Resultado: índice " + r2 + "\n");

        // Caso de prueba 3: caso borde - arreglo vacío
        int[] arreglo3 = {};
        System.out.println("=== CASO 3: arreglo vacío ===");
        int r3 = busquedaBinaria(arreglo3, 10);
        System.out.println("Resultado: índice " + r3 + "\n");

        // Caso de prueba 4: objetivo mayor que todos los elementos
        int[] arreglo4 = {1, 3, 5, 7, 9};
        System.out.println("=== CASO 4: objetivo mayor que todos ===");
        int r4 = busquedaBinaria(arreglo4, 99);
        System.out.println("Resultado: índice " + r4 + "\n");

        // Resumen
        System.out.println("=== RESUMEN ===");
        System.out.println("Caso 1 (buscar 23): índice " + r1 + " (esperado 5)");
        System.out.println("Caso 2 (buscar 30): índice " + r2 + " (esperado -1)");
        System.out.println("Caso 3 (arreglo vacío): índice " + r3 + " (esperado -1)");
        System.out.println("Caso 4 (buscar 99): índice " + r4 + " (esperado -1)");
    }
}

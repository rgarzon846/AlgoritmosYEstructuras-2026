/*====================================================================================
                                    PROMPT 
 ====================================================================================
 "Implementa en Java un programa que ordene un arreglo de nombres alfabéticamente
 utilizando el algoritmo de ordenamiento SELECTION SORT. Caso base del enunciado:

     String[] nombres = {"Lucia", "Ana", "Pedro", "Juan"};

 El objetivo es razonar CÓMO CAMBIA LA COMPARACIÓN cuando se ordenan textos y no
 números.

 1) QUÉ ALGORITMO DE ORDENAMIENTO UTILIZAR:
    Selection Sort (selección), obligatoriamente, sobre un arreglo String[]: en cada
    pasada se busca el nombre MENOR de la región sin ordenar y se intercambia con el
    primer elemento de esa región. El esquema del algoritmo es idéntico al de los
    arreglos de números; lo único que cambia es la línea de comparación.

 2) RAZONAMIENTO
    a) NÚMEROS: se usan operadores primitivos directos → if (arreglo[j] < arreglo[posMin]).
       El resultado es booleano.
    b) TEXTOS: en Java NO existen operadores <, >, == para objetos → escribir
       if (a < b) con String NO COMPILA. Se usa el método compareTo (interfaz
       Comparable): if (arreglo[j].compareTo(arreglo[posMin]) < 0).
    c) compareTo devuelve un int (no un boolean): NEGATIVO = el receptor va antes
       alfabéticamente; 0 = iguales; POSITIVO = va después. Por eso la condición es
       "< 0".
    d) Orden LEXICOGRÁFICO: se compara carácter a carácter por su valor Unicode y
       gana la PRIMERA diferencia; si todo coincide hasta acabarse el más corto, el
       más corto va primero ("An" < "Ana"; "Ana" < "Anita" porque 'a'(97) < 'i'(105)).
    e) Mayúsculas/minúsculas IMPORTAN: 'A'(65) < 'a'(97) → "Ana" < "ana". Para
       ignorarlo existe compareToIgnoreCase; para orden según el idioma (ñ, acentos)
       existe java.text.Collator.
    f) El resto de Selection Sort no cambia: sigue buscando el mínimo y haciendo
       swaps; solo se reemplaza la comparación numérica por compareTo.

 3) QUÉ DATOS RECIBE EL PROGRAMA:
    Un arreglo de cadenas (String[]) con nombres propios, definido dentro de main
    (incluido {"Lucia", "Ana", "Pedro", "Juan"} del enunciado); NO se requiere
    lectura por teclado. Los arreglos de prueba no contienen elementos nulos (el
    orden natural no los soporta, igual que Arrays.sort). La ordenación es in-place.

 4) QUÉ DEBE MOSTRAR POR PANTALLA:
    - Una demostración inicial de CÓMO CAMBIA LA COMPARACIÓN: números vs textos,
      con valores REALES de compareTo (negativo/0/positivo) para distintos pares de
      nombres (prefijos, mayúsculas vs minúsculas, iguales).
    - El arreglo original.
    - Una traza por pasada: cada comparación de la búsqueda del mínimo con el valor
      de compareTo, qué nombre queda como mínimo, y el swap realizado.
    - El arreglo final ordenado alfabéticamente y los contadores: comparaciones,
      intercambios y pasadas.
    - La verificación del resultado y un análisis final que resuma el cambio de la
      comparación (números vs textos).

 5) QUÉ COMENTARIOS DEBE INCLUIR EL CÓDIGO:
    - Javadoc en selectionSort explicando la estrategia y el cambio de comparación
      (operadores vs compareTo, significado del int devuelto, orden lexicográfico).
    - Comentario en la línea del compareTo indicando que "< 0" significa "va antes
      alfabéticamente" y que devuelve int, no boolean.
    - Comentario sobre mayúsculas/minúsculas (compareToIgnoreCase / Collator) y una
      nota de que el resto del algoritmo es igual que con números.
    - Comentarios en los casos borde (arreglo nulo, vacío, un solo nombre, repetidos).

 6) CÓMO VERIFICAR QUE EL RESULTADO SEA CORRECTO:
    Convención de conteo: una comparación = cada evaluación de
    array[j].compareTo(array[posMin]) < 0; un intercambio = cada swap de posiciones.
    Verificaciones:
    - El resultado debe ser igual a java.util.Arrays.sort(String[]) (usa el MISMO
      orden natural compareTo) y estar en orden no decreciente según compareTo.
    - Contadores EXACTOS (comparaciones/intercambios):
        · {"Lucia", "Ana", "Pedro", "Juan"} enunciado: 6 / 3
        · {"ana", "Ana", "PEDRO", "pedro"} mayúsculas: 6 / 2
        · {"Anita", "Ana", "An"} prefijos: 3 / 1
        · {"Ana", "Juan", "Lucia", "Pedro"} ya ordenado: 6 / 0
        · {"Pedro", "Lucia", "Juan", "Ana"} inverso: 6 / 2
        · {"Maria", "Ana", "Maria", "Luis"} repetidos: 6 / 2
        · {"Solo"} / {} / null: 0 / 0 en todos (null sin excepción)
    (Con n nombres, selection sort siempre realiza n(n-1)/2 comparaciones.)
    Plan de pruebas obligatorio: los 9 casos anteriores.
"
 ====================================================================================
 */
public class Ejercicio4 {

    /**
     * Contadores del ordenamiento por selección sobre textos.
     */
    public static class Estadisticas {

        public int pasadas = 0;
        public int comparaciones = 0;
        public int intercambios = 0;

        @Override
        public String toString() {
            return "pasadas = " + pasadas
                    + ", comparaciones = " + comparaciones
                    + ", intercambios = " + intercambios;
        }
    }

    // Fila de la tabla final: [caso, comparaciones, intercambios, OK]
    private static final java.util.List<String[]> TABLA = new java.util.ArrayList<>();

    /**
     * SELECTION SORT sobre nombres — busca el menor con compareTo y lo lleva al
     * principio de la región con un swap por pasada.
     *
     * <p>
     * <b>Cómo cambia la comparación respecto de los números:</b> con int se
     * escribía {@code if (a < b)} (operador que devuelve boolean), pero con
     * String eso NO compila en Java porque no hay operadores para objetos: se
     * usa {@code a.compareTo(b)}, que devuelve un int (negativo = a va antes, 0
     * = iguales, positivo = a va después) y la condición es {@code < 0}. El
     * orden es lexicográfico por código de carácter: gana la primera
     * diferencia, el prefijo más corto va primero y las mayúsculas ('A'=65) van
     * antes que las minúsculas ('a'=97) — por eso a veces se usa
     * compareToIgnoreCase o Collator. El resto del algoritmo (búsqueda del
     * mínimo + swap) es idéntico al de números.
     *
     * <p>
     * Convención de conteo: una comparación por cada evaluación de
     * {@code array[j].compareTo(array[posMin]) < 0}.
     *
     * @param array nombres a ordenar in-place por orden natural (null se
     * ignora)
     * @return contadores de pasadas, comparaciones e intercambios
     */
    public static Estadisticas selectionSort(String[] array) {
        Estadisticas stats = new Estadisticas();

        // Caso borde: nulo → sin excepción, solo mensaje informativo
        if (array == null) {
            System.out.println("  [Selection] arreglo nulo → nada que ordenar.");
            return stats;
        }

        System.out.println("  [Selection] original: " + java.util.Arrays.toString(array));

        // Caso borde: vacío o un solo nombre → ya está "ordenado", 0 pasadas
        int n = array.length;
        if (n < 2) {
            System.out.println("  [Selection] menos de 2 nombres → ya está ordenado.");
            return stats;
        }

        // n-1 pasadas: cada una fija el MENOR de la región [k .. n-1] en la posición k
        for (int k = 0; k < n - 1; k++) {
            stats.pasadas++;
            System.out.println("  Pasada " + stats.pasadas + ":");

            // Búsqueda del mínimo: MISMO código que con números, pero la
            // comparación ahora es de textos → se usa compareTo en vez de <
            int posMin = k;
            for (int j = k + 1; j < n; j++) {
                stats.comparaciones++;
                int resultado = array[j].compareTo(array[posMin]);
                // compareTo devuelve int: < 0 → array[j] va ANTES alfabéticamente
                if (resultado < 0) {
                    System.out.println("    \"" + array[j] + "\".compareTo(\""
                            + array[posMin] + "\") = " + resultado
                            + " → < 0, nuevo mínimo \"" + array[j] + "\"");
                    posMin = j;
                } else {
                    System.out.println("    \"" + array[j] + "\".compareTo(\""
                            + array[posMin] + "\") = " + resultado
                            + " → " + (resultado == 0 ? "0, iguales"
                                    : "> 0, ya está antes")
                            + ", sigue \"" + array[posMin] + "\"");
                }
            }

            // Un swap por pasada lleva el menor a su posición definitiva
            if (posMin != k) {
                String temporal = array[k];
                array[k] = array[posMin];
                array[posMin] = temporal;
                stats.intercambios++;
                System.out.println("    → mínimo \"" + array[k] + "\" (posición "
                        + posMin + ") → swap " + posMin + " ↔ " + k
                        + "  " + java.util.Arrays.toString(array));
            } else {
                System.out.println("    → mínimo \"" + array[k]
                        + "\" ya estaba en la posición " + k + ", sin swap  "
                        + java.util.Arrays.toString(array));
            }
        }

        System.out.println("  [Selection] nombres ordenados: "
                + java.util.Arrays.toString(array));
        System.out.println("  [Selection] " + stats);
        return stats;
    }

    // ======================================================================
    // DEMOSTRACIÓN, VERIFICACIÓN AUXILIAR Y PRUEBAS
    // ======================================================================
    /**
     * Muestra en qué se diferencia la comparación de números vs de textos.
     */
    private static void mostrarCambioDeComparacion() {
        System.out.println("=== CÓMO CAMBIA LA COMPARACIÓN: NÚMEROS vs TEXTOS ===");
        System.out.println("Números: operador directo → 3 < 5 = true (devuelve boolean).");
        System.out.println("Textos  : NO existe el operador < para objetos → if (a < b)");
        System.out.println("          con String NO COMPILA. Se usa compareTo (Comparable):");
        System.out.println("          devuelve int → < 0 va antes | 0 iguales | > 0 va después.");
        System.out.println();
        System.out.println("  \"Ana\".compareTo(\"Lucia\")  = " + "Ana".compareTo("Lucia")
                + "  → negativo: \"Ana\" va ANTES alfabéticamente");
        System.out.println("  \"Pedro\".compareTo(\"Juan\") = " + "Pedro".compareTo("Juan")
                + "  → positivo: \"Pedro\" va DESPUÉS");
        System.out.println("  \"Pedro\".compareTo(\"Pedro\") = " + "Pedro".compareTo("Pedro")
                + "  → cero: iguales");
        System.out.println("  \"An\".compareTo(\"Ana\")     = " + "An".compareTo("Ana")
                + "  → el prefijo más corto va primero");
        System.out.println("  \"Ana\".compareTo(\"ana\")    = " + "Ana".compareTo("ana")
                + "  → mayúscula 'A'(65) < 'a'(97): importa la capitalización");
        System.out.println("          (para ignorarla: compareToIgnoreCase; para orden");
        System.out.println("           según el idioma con ñ/acentos: Collator)");
        System.out.println();
    }

    /**
     * Comprueba el orden alfabético usando el MISMO criterio (compareTo).
     */
    private static boolean estaOrdenado(String[] arreglo) {
        for (int i = 0; i + 1 < arreglo.length; i++) {
            if (arreglo[i].compareTo(arreglo[i + 1]) > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Ejecuta un caso: ordena, verifica contra Arrays.sort (mismo orden
     * natural) y valida los contadores exactos.
     *
     * @param expComparaciones comparaciones exactas esperadas
     * @param expIntercambios intercambios exactos esperados
     */
    private static void probar(String descripcion, String[] original,
            int expComparaciones, int expIntercambios) {
        System.out.println("=== " + descripcion + " ===");

        // Caso borde: nulo → sin excepción
        if (original == null) {
            System.out.println("Original: null");
            Estadisticas stats = selectionSort(null);
            System.out.println("Verificación: sin excepción → OK ✓");
            System.out.println();
            TABLA.add(new String[]{descripcion, String.valueOf(stats.comparaciones),
                String.valueOf(stats.intercambios), "OK"});
            return;
        }

        System.out.println("Original: " + java.util.Arrays.toString(original));

        // Resultado esperado: Arrays.sort usa el MISMO orden natural (compareTo)
        String[] esperado = original.clone();
        java.util.Arrays.sort(esperado);

        String[] paraOrdenar = original.clone();
        Estadisticas stats = selectionSort(paraOrdenar);

        boolean igualQueSort = java.util.Arrays.equals(paraOrdenar, esperado);
        boolean ordenado = estaOrdenado(paraOrdenar);
        boolean contadoresOk = stats.comparaciones == expComparaciones
                && stats.intercambios == expIntercambios;

        boolean todoOk = igualQueSort && ordenado && contadoresOk;

        System.out.println("Verificación vs Arrays.sort (orden natural): "
                + (igualQueSort ? "OK ✓" : "ERROR ✗ (esperado "
                        + java.util.Arrays.toString(esperado) + ")"));
        System.out.println("Verificación orden alfabético (compareTo): "
                + (ordenado ? "OK ✓" : "ERROR ✗"));
        System.out.println("Verificación contadores (esperado "
                + expComparaciones + " / " + expIntercambios + "): "
                + (contadoresOk ? "OK ✓" : "ERROR ✗"));
        System.out.println();

        TABLA.add(new String[]{descripcion,
            String.valueOf(stats.comparaciones),
            String.valueOf(stats.intercambios),
            todoOk ? "OK" : "ERROR"});
    }

    public static void main(String[] args) {
        // Demostración del objetivo: qué cambia al comparar textos
        mostrarCambioDeComparacion();

        // Caso 1: el arreglo del enunciado
        String[] nombres = {"Lucia", "Ana", "Pedro", "Juan"};
        probar("CASO 1: enunciado", nombres, 6, 3);

        // Caso 2: mayúsculas vs minúsculas (importa el código de carácter)
        probar("CASO 2: mayúsculas y minúsculas",
                new String[]{"ana", "Ana", "PEDRO", "pedro"}, 6, 2);

        // Caso 3: prefijos (el más corto va primero)
        probar("CASO 3: prefijos de un nombre",
                new String[]{"Anita", "Ana", "An"}, 3, 1);

        // Caso 4: ya ordenado → selection NO corta: siempre n(n-1)/2 comparaciones
        probar("CASO 4: ya ordenado", new String[]{"Ana", "Juan", "Lucia", "Pedro"}, 6, 0);

        // Caso 5: orden alfabético inverso
        probar("CASO 5: inverso", new String[]{"Pedro", "Lucia", "Juan", "Ana"}, 6, 2);

        // Caso 6: nombres repetidos
        probar("CASO 6: repetidos", new String[]{"Maria", "Ana", "Maria", "Luis"}, 6, 2);

        // Caso 7: un solo nombre
        probar("CASO 7: un solo nombre", new String[]{"Solo"}, 0, 0);

        // Caso 8: arreglo vacío
        probar("CASO 8: arreglo vacío", new String[]{}, 0, 0);

        // Caso 9: arreglo nulo → sin excepción
        probar("CASO 9: arreglo nulo", null, 0, 0);

        // Tabla de contadores
        System.out.println("=== TABLA (comparaciones / intercambios) ===");
        System.out.printf("%-38s | %12s | %13s | %s%n",
                "CASO", "COMPARACIONES", "INTERCAMBIOS", "VERIFICACIÓN");
        System.out.println("--------------------------------------------------------------"
                + "------------------");
        for (String[] fila : TABLA) {
            System.out.printf("%-38s | %12s | %13s | %s%n",
                    fila[0], fila[1], fila[2], fila[3]);
        }

        // Análisis final del objetivo
        System.out.println();
        System.out.println("=== ANÁLISIS: QUÉ CAMBIÓ RESPECTO DE LOS NÚMEROS ===");
        System.out.println("Números : if (a < b)               → operador, boolean.");
        System.out.println("Textos  : if (a.compareTo(b) < 0)   → método, int.");
        System.out.println("  · < 0 → a va antes | 0 → iguales | > 0 → a va después.");
        System.out.println("  · Orden lexicográfico: 1ª diferencia de caracteres decide;");
        System.out.println("    el prefijo corto va antes; 'A'(65) < 'a'(97).");
        System.out.println("  · Selection Sort en sí NO cambia: sigue buscando el mínimo");
        System.out.println("    y haciendo 1 swap por pasada; solo cambia la comparación.");
        System.out.println("  · Con n nombres siempre n(n-1)/2 comparaciones = 6 en n=4");
        System.out.println("    (incluso ya ordenado: selection no puede cortar).");
    }
}

/* ...CORRECCIONES HECHAS POR OPENCODE
====================================================================================
CORRECCIONES HECHAS POR OPENCODE
====================================================================================
[PROMPT] El prompt no pide ninguna tabla resumen por caso (solo demostración, traza, contadores, verificación y análisis final); el código agrega la lista estática TABLA y la imprime con los 9 casos.
[REDUNDANTE] El arreglo original se imprime DOS veces por caso: probar() imprime "Original:" y selectionSort() vuelve a imprimir "[Selection] original: ...".
====================================================================================
*/

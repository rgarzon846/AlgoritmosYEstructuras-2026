/*
 ====================================================================================
                                    PROMPT 
 ====================================================================================
 "Escribe un método estático en Java para buscar recursivamente un valor objetivo 
 'target' dentro de un arreglo de enteros (int[]), SIN utilizar bucles 'for', 'while' 
 o 'do-while'.

 Requisitos y fundamentos del algoritmo:
 1. POSICIÓN INICIAL DE BÚSQUEDA:
    La búsqueda debe comenzar desde el primer elemento del arreglo, es decir, el índice 0. 
    Para lograr esto sin ciclos, se utiliza un método auxiliar (helper) que reciba el 
    arreglo, el elemento a buscar y el índice actual de rastreo (`int indice`), iniciado en 0.

 2. AVANCE EN EL ARREGLO SIN CICLOS:
    El recorrido secuencial se simula delegando el análisis del siguiente elemento a la 
    siguiente llamada recursiva, incrementando el índice en 1 unidad:
        buscarRecursivo(array, target, indice + 1)

 3. CASO BASE 1: ÉXITO (VALOR ENCONTRADO)
    Si el elemento en la posición actual coincide con el valor buscado (`array[indice] == target`), 
    la búsqueda finaliza inmediatamente. La función debe devolver el índice (posición) 
    donde se encontró el número.

 4. CASO BASE 2: FRACASO (LLEGADA AL FINAL DEL ARREGLO)
    Si el índice alcanza o supera la longitud del arreglo (`indice >= array.length`), 
    significa que se han inspeccionado todos los elementos sin éxito. La función debe detener 
    la recursión y devolver `-1` para indicar que el elemento no está presente.

 5. VALOR DE RETORNO Y PLAN DE PRUEBAS:
    - Retorna el `índice` (int >= 0) donde se halla la primera coincidencia del elemento.
    - Retorna `-1` si el valor no existe en el arreglo o si el arreglo está vacío.
    
    Plan de pruebas a validar:
    - Elemento al principio: `buscar(arr, 10)` en `[10, 20, 30]` -> Esperado: `0`
    - Elemento en el medio: `buscar(arr, 30)` en `[10, 20, 30, 40]` -> Esperado: `2`
    - Elemento al final: `buscar(arr, 50)` en `[10, 20, 30, 50]` -> Esperado: `3`
    - Elemento inexistente: `buscar(arr, 99)` en `[10, 20, 30]` -> Esperado: `-1`
    - Arreglo vacío: `buscar(new int[]{}, 5)` -> Esperado: `-1`
 ====================================================================================
 */

public class BusquedaLinealRecursiva {

    /**
     * Busca un número dentro de un arreglo de forma recursiva sin usar ciclos.
     *
     * @param array Arreglo de enteros donde buscar.
     * @param target Número objetivo a encontrar.
     * @return El índice de la primera aparición de target, o -1 si no existe.
     */
    public static int buscar(int[] array, int target) {
        if (array == null || array.length == 0) {
            return -1;
        }
        // Inicia la búsqueda desde la posición 0
        return buscarAuxiliar(array, target, 0);
    }

    private static int buscarAuxiliar(int[] array, int target, int indice) {
        // CASO BASE 2: Se llegó al final del arreglo sin encontrar el objetivo
        if (indice >= array.length) {
            return -1;
        }

        // CASO BASE 1: Se encontró el elemento buscado
        if (array[indice] == target) {
            return indice;
        }

        // PASO RECURSIVO: Avanzar al siguiente índice (sin usar bucles)
        return buscarAuxiliar(array, target, indice + 1);
    }

    public static void main(String[] args) {
        int[] datos = {15, 42, 8, 23, 99, 42};

        // Batería de pruebas
        int[][] pruebas = {
            {15, 0}, // Elemento al inicio
            {23, 3}, // Elemento en el medio
            {42, 1}, // Elemento repetido (devuelve primera aparición)
            {99, 4}, // Elemento cerca del final
            {500, -1} // Elemento inexistente
        };

        System.out.println("--- PRUEBAS DE BÚSQUEDA RECURSIVA EN ARREGLO ---");
        for (int[] prueba : pruebas) {
            int objetivo = prueba[0];
            int esperado = prueba[1];

            int resultado = buscar(datos, objetivo);
            boolean pasaPrueba = (resultado == esperado);

            System.out.printf("Buscando: %-3d | Índice obtenido: %-2d | Esperado: %-2d -> [%s]%n",
                    objetivo, resultado, esperado, pasaPrueba ? "OK" : "ERROR");
        }
    }
}

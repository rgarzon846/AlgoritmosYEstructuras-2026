public class Ejercicio1 {

    /**
     * Encuentra el valor mínimo en un vector de enteros usando un barrido lineal.
     * 
     * ESTRATEGIA:
     * Se recorre el vector una sola vez comparando cada elemento con el mínimo
     * encontrado hasta el momento. Si se encuentra un elemento menor, se actualiza
     * el mínimo.
     * 
     * ¿POR QUÉ NO ES NECESARIO ORDENAR?
     * Ordenar el vector (por ejemplo con Bubble Sort) reorganizaría TODOS los 
     * elementos de menor a mayor. Pero para encontrar el mínimo, solo necesitamos
     * IDENTIFICAR cuál es el menor, no conocer el orden de todos. Es como buscar
     * la persona más baja en una fila: solo tenés que ir comparando, no necesitás
     * ordenar a todos por altura primero.
     * 
     * COMPLEJIDAD:
     * - Temporal: O(n) - un solo recorrido lineal
     * - Espacial: O(1) - solo una variable extra (el mínimo)
     * 
     * @param vector Array de enteros (debe tener al menos un elemento)
     * @return El valor mínimo del vector
     */
    public static int encontrarMinimo(int[] vector) {
        // Validación básica
        if (vector == null || vector.length == 0) {
            throw new IllegalArgumentException("El vector no puede estar vacío");
        }
        
        // Asumimos que el primer elemento es el mínimo
        int minimo = vector[0];
        
        // Recorremos desde el segundo elemento hasta el final
        // Solo necesitamos UN bucle for (no dos como en Bubble Sort)
        for (int i = 1; i < vector.length; i++) {
            // Si encontramos un elemento menor, actualizamos el mínimo
            if (vector[i] < minimo) {
                minimo = vector[i];
            }
        }
        
        return minimo;
    }

    /**
     * Método principal para demostrar el funcionamiento del algoritmo.
     */
    public static void main(String[] args) {
        // Vector de prueba
        int[] numeros = {45, 12, 67, 3, 89, 23, 1, 56};
        
        System.out.println("Vector: ");
        for (int num : numeros) {
            System.out.print(num + " ");
        }
        System.out.println();
        
        int minimo = encontrarMinimo(numeros);
        System.out.println("El valor mínimo es: " + minimo);
    }
}


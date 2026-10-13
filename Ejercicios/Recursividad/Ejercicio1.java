
/*====================================================================================
                                        PROMPT 
 ====================================================================================
 "Escribe un método estático en Java para calcular el factorial de un número entero 
 no negativo 'n' de forma recursiva. 
 
 Requisitos técnicos:
 1. Define explícitamente como CASO BASE cuando n es igual a 0, devolviendo 1 (0! = 1).
 2. Maneja el paso recursivo aplicando la definición matemática n! = n * (n - 1)!.
 3. Incluye validación de parámetros para prevenir números negativos (lanzando IllegalArgumentException).
 4. Utiliza el tipo primitivo de retorno 'long' para soportar resultados de hasta n = 20.
 
 Justificación teórica del uso de recursividad:
 El factorial es un ejemplo clásico de un problema con estructura inductiva/recursiva
 inherente en su definición matemática:
 
    Fact(n) = 1,              si n = 0 (Caso base)
    Fact(n) = n * Fact(n - 1), si n > 0 (Paso recursivo)
 
 Como la definición del problema requiere la solución del mismo problema aplicado a un
 subconjunto más pequeño (n - 1), la recursividad ofrece una traducción casi directa y 
 elegante del lenguaje matemático al código, mejorando la legibilidad sobre un enfoque 
 iterativo convencional."
 ====================================================================================
 */
public class Ejercicio1 {

    /**
     * Calcula el factorial de un número entero de manera recursiva.
     *
     * @param n Número entero no negativo (recomendado n <= 20). @
     * return El factorial de n.
     * @throws IllegalArgumentException Si n es menor que 0.
     */
    public static long calcularFactorial(int n) {
        // Validación de rango
        if (n < 0) {
            throw new IllegalArgumentException("El factorial no está definido para números negativos.");
        }

        // CASO BASE: 0! = 1
        if (n == 0) {
            return 1;
        }

        // PASO RECURSIVO: n! = n * (n - 1)!
        return n * calcularFactorial(n - 1);
    }

    public static void main(String[] args) {
        int numero = 5;
        long resultado = calcularFactorial(numero);
        System.out.println("El factorial de " + numero + " es: " + resultado);
    }
}

/*
 ====================================================================================
                                        PROMPT 
 ====================================================================================
 "Escribe un método estático en Java para imprimir un conteo regresivo desde un número 'n' 
 hasta 0 de forma recursiva.

 Requisitos y fundamentos del algoritmo:
 1. FUNCIÓN QUE NO DEVUELVE UN VALOR (TIPO `void`):
    A diferencia de funciones que realizan cálculos (como factorial o potencia), esta 
    función tiene un 'efecto secundario': imprimir en la consola. Su tipo 
    de retorno es `void` porque su objetivo no es calcular ni retornar un dato al caso recursivo, 
    sino ejecutar la acción de mostrar la secuencia de números por pantalla.

 2. CASO BASE (N < 0):
    El conteo se detiene cuando 'n' pasa de 0 a un valor negativo (n < 0). En ese punto,
    la función ejecuta una sentencia `return;` vacía para detener las llamadas recursivas
    y salir del ciclo de la pila de llamadas. (Alternativamente, el caso base es n == 0,
    imprimiendo el 0 y haciendo `return`).

 3. CÓMO SE ACERCA AL CASO BASE:
    En cada llamada recursiva, se imprime el valor actual de 'n' y luego se invoca a la 
    misma función pasando `n - 1`. Reducir 'n' en una unidad garantiza que en cada paso 
    nos aproximemos progresivamente al caso base hasta alcanzarlo.

 4. ¿QUÉ PASARÍA SI SE USARA `n + 1` EN LUGAR DE `n - 1`?:
    Si se usara `n + 1`, el valor de 'n' incrementaría en lugar de decrecer (ej. 5, 6, 7...). 
    Esto alejaría permanentemente a la función del caso base (n < 0), causando un ciclo 
    recursivo infinito que agotará la memoria de la pila de llamadas de Java (*Call Stack*) 
    y lanzará una excepción fatal: `java.lang.StackOverflowError`.

 5. CÓMO PROBAR LA EJECUCIÓN:
    - Ejecución estándar: Llamar a `imprimirConteoRegresivo(5)` y verificar que la consola 
      muestre la secuencia exacta `5, 4, 3, 2, 1, 0` en orden descendente.
    - Caso límite: Llamar a `imprimirConteoRegresivo(0)` para verificar que imprima `0` y finalice.
    - Validación de seguridad: Comprobar el manejo de un número negativo de entrada 
      (ej. `-3`), asegurando que no imprima nada o lance una excepción controlada.
 ====================================================================================
 */

public class ConteoRegresivoRecursivo {

    /**
     * Imprime un conteo regresivo desde n hasta 0 de forma recursiva.
     *
     * @param n Número entero inicial del conteo (debe ser >= 0).
     */
    public static void imprimirConteoRegresivo(int n) {
        // CASO BASE: Si n es menor a 0, detenemos la recursividad (salida inmediata)
        if (n < 0) {
            return;
        }

        // ACCIÓN DE LA FUNCIÓN: Imprimir el número actual
        System.out.println(n);

        // PASO RECURSIVO: Invocamos con (n - 1) para acercarnos al caso base
        // NOTA: Usar (n + 1) causaría un java.lang.StackOverflowError
        imprimirConteoRegresivo(n - 1);
    }

    public static void main(String[] args) {
        System.out.println("--- PRUEBA 1: Conteo desde 5 ---");
        imprimirConteoRegresivo(5);

        System.out.println("\n--- PRUEBA 2: Caso límite (n = 0) ---");
        imprimirConteoRegresivo(0);

        System.out.println("\n--- PRUEBA 3: Entrada negativa (n = -2) ---");
        imprimirConteoRegresivo(-2);
        System.out.println("(No debe imprimir números, finaliza correctamente)");
    }
}

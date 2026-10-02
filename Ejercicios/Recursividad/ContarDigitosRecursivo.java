/*
 ====================================================================================
                                        PROMPT
 ====================================================================================
 "Escribe un método estático en Java para contar la cantidad de dígitos que tiene un 
 número entero positivo de forma recursiva.

 Requisitos y fundamentos del algoritmo:
 1. REDUCCIÓN DEL NÚMERO MEDIANTE DIVISIÓN ENTERA (`n / 10`):
    En el sistema decimal (base 10), la división entera entre 10 elimina el último dígito 
    de la derecha (el dígito menos significativo). Por ejemplo, `1234 / 10 = 123`. 
    Cada llamada recursiva debe pasar como argumento `n / 10`, reduciendo la cifra hasta 
    alcanzar el caso base.

 2. CASO BASE (NÚMEROS MENORES A 10):
    Un número positivo 0 <= n < 10 (del 0 al 9) consta de exactamente un único dígito. 
    Por lo tanto, el caso base se alcanza cuando `n < 10`, punto en el cual la función 
    retorna directamente `1` y detiene las llamadas recursivas.

 3. VALORES DE RETORNO DE CADA LLAMADA:
    - Caso base (`n < 10`): Retorna `1`.
    - Paso recursivo (`n >= 10`): Retorna `1 + contarDigitos(n / 10)`. El `1` representa 
      el dígito actual que se elimina en ese paso, sumado a la cantidad de dígitos que 
      contenga el resto del número.

 4. VALIDACIÓN DE ENTRADA:
    Si el número ingresado es menor a 0, lanza una excepción de tipo `IllegalArgumentException` 
    o convierte el valor a positivo mediante `Math.abs(n)`.

 5. PLAN DE PRUEBAS DE VALIDACIÓN:
    Se deben incluir los siguientes casos de prueba:
    - Un solo dígito (Caso base): `contarDigitos(7)` -> Esperado: `1`
    - Cero (Caso borde): `contarDigitos(0)` -> Esperado: `1`
    - Múltiples dígitos estándar: `contarDigitos(12345)` -> Esperado: `5`
    - Números con ceros intermedios o finales: `contarDigitos(10020)` -> Esperado: `5`
    - Valor máximo con tipo `int`: `contarDigitos(2147483647)` -> Esperado: `10`
 ====================================================================================
 */

public class ContarDigitosRecursivo {

    /**
     * Cuenta la cantidad de dígitos de un número entero no negativo de forma
     * recursiva.
     *
     * @param n Número entero no negativo.
     * @return La cantidad total de dígitos que componen a n.
     * @throws IllegalArgumentException Si n es negativo.
     */
    public static int contarDigitos(int n) {
        // Validación de números negativos
        if (n < 0) {
            throw new IllegalArgumentException("El número debe ser mayor o igual a 0.");
        }

        // CASO BASE: Si el número tiene un solo dígito (0 <= n < 10)
        if (n < 10) {
            return 1;
        }

        // PASO RECURSIVO: 1 (dígito eliminado) + conteo de los dígitos restantes (n / 10)
        return 1 + contarDigitos(n / 10);
    }

    public static void main(String[] args) {
        // Batería de pruebas
        int[][] pruebas = {
            {7, 1}, // Un solo dígito
            {0, 1}, // Cero
            {12345, 5}, // 5 dígitos
            {10020, 5}, // Ceros internos y finales
            {2147483647, 10} // Integer.MAX_VALUE
        };

        System.out.println("--- PRUEBAS DE CONTEO RECURSIVO DE DÍGITOS ---");
        for (int[] par : pruebas) {
            int numero = par[0];
            int esperado = par[1];

            int resultado = contarDigitos(numero);
            boolean pasaPrueba = (resultado == esperado);

            System.out.printf("Número: %-10d | Obtención: %d | Esperado: %d -> [%s]%n",
                    numero, resultado, esperado, pasaPrueba ? "OK" : "ERROR");
        }
    }
}

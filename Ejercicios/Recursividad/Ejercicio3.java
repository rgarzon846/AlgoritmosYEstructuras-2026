/*
 ====================================================================================
                                        PROMPT
 ====================================================================================
 "Escribe un método estático en Java para calcular la multiplicación de dos números
 enteros 'a' y 'b' de forma recursiva, SIN utilizar el operador de multiplicación (*).

 Requisitos y fundamentos del algoritmo:
 1. MULTIPLICACIÓN COMO SUMAS REPETIDAS:
    Matemáticamente, la multiplicación `a * b` representa sumar el número `a` consigo 
    mismo un total de `b` veces (o viceversa):
        a * b = a + a + a + ... (b veces)

 2. PARÁMETRO QUE SE REDUCE Y PASO RECURSIVO:
    Tomamos el segundo parámetro `b` como el contador de repeticiones. En cada llamada 
    recursiva, reducimos `b` en 1 unidad (`b - 1`) y le sumamos `a` al resultado:
        multiplicar(a, b) = a + multiplicar(a, b - 1)

 3. CASO BASE (b = 0):
    Cualquier número multiplicado por 0 da 0 (`a * 0 = 0`). Por lo tanto, el caso base 
    ocurre cuando el factor de repetición `b` llega a 0, indicando que ya no quedan más 
    sumas por realizar.

 4. VALOR DE RETORNO:
    - Si `b == 0`, la función retorna 0.
    - En el paso recursivo, retorna la suma del factor `a` más el resultado de la 
      llamada recursiva con `b - 1`.
    - NOTA DE SIGNOS: Si `b` es negativo, la función debe convertir la operación manejando 
      los signos de forma equivalente: `a * b = (-a) * (-b)`.

 5. PLAN DE PRUEBAS DE VALIDACIÓN:
    Para garantizar el funcionamiento correcto, se deben probar los siguientes casos:
    - Factores positivos: `multiplicar(4, 3)` -> Esperado: `12`
    - Multiplicación por cero: `multiplicar(5, 0)` o `multiplicar(0, 5)` -> Esperado: `0`
    - Elemento neutro (multiplicación por 1): `multiplicar(7, 1)` -> Esperado: `7`
    - Factores con signos negativos:
        - `b` negativo: `multiplicar(4, -3)` -> Esperado: `-12`
        - `a` negativo: `multiplicar(-4, 3)` -> Esperado: `-12`
        - Ambos negativos: `multiplicar(-4, -3)` -> Esperado: `12`
 ====================================================================================
 */

public class Ejercicio3 {

    /**
     * Calcula la multiplicación de dos enteros a y b de forma recursiva sin
     * usar *.
     *
     * @param a Primer factor (número que se suma).
     * @param b Segundo factor (contador de repeticiones).
     * @return El producto de a por b.
     */
    public static int multiplicar(int a, int b) {
        // Manejo de b negativo: a * b = (-a) * (-b)
        // Esto garantiza que el parámetro b que se reduce converja siempre hacia 0.
        if (b < 0) {
            return multiplicar(-a, -b);
        }

        // CASO BASE: Cualquier número multiplicado por 0 da 0
        if (b == 0) {
            return 0;
        }

        // PASO RECURSIVO: a + sumar 'a' un total de (b - 1) veces más
        return a + multiplicar(a, b - 1);
    }

    public static void main(String[] args) {
        // Batería de pruebas
        int[][] pruebas = {
            {4, 3}, // Positivos -> 12
            {5, 0}, // Multiplicación por cero -> 0
            {0, 5}, // Multiplicación con cero -> 0
            {7, 1}, // Neutro -> 7
            {4, -3}, // b negativo -> -12
            {-4, 3}, // a negativo -> -12
            {-4, -3} // Ambos negativos -> 12
        };

        System.out.println("--- EJECUCIÓN DE PRUEBAS DE MULTIPLICACIÓN RECURSIVA ---");
        for (int[] par : pruebas) {
            int a = par[0];
            int b = par[1];
            int resultado = multiplicar(a, b);
            int esperado = a * b; // Usado solo para aserción de prueba
            boolean pasaPrueba = (resultado == esperado);

            System.out.printf("%d * %d = %d (Esperado: %d) -> [%s]%n",
                    a, b, resultado, esperado, pasaPrueba ? "OK" : "ERROR");
        }
    }
}

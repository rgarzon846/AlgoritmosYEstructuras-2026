/*
 ====================================================================================
                                    PROMPT 
 ====================================================================================
 "Escribe un método estático en Java para calcular la suma de los dígitos de un número 
 entero positivo de forma recursiva.

 Requisitos y fundamentos del algoritmo:
 1. OBTENCIÓN DEL ÚLTIMO DÍGITO MEDIANTE MÓDULO (`n % 10`):
    En el sistema decimal (base 10), el operador resto o módulo entre 10 (`n % 10`) 
    extrae de forma directa el dígito más a la derecha (las unidades del número). 
    Por ejemplo, para `n = 1234`, `1234 % 10 = 4`.

 2. REDUCCIÓN DEL NÚMERO MEDIANTE DIVISIÓN ENTERA (`n / 10`):
    La división entera por 10 (`n / 10`) elimina el último dígito extraído, desplazando 
    los dígitos restantes hacia la derecha. Siguiendo el ejemplo anterior, `1234 / 10 = 123`. 
    Este nuevo valor reducido será el argumento para la llamada recursiva.

 3. CASO BASE (NÚMEROS MENORES A 10):
    Un número no negativo 0 <= n < 10 (del 0 al 9) consta de un solo dígito. La suma de los 
    dígitos de un número de una sola cifra es el propio número. Por lo tanto, el caso base 
    se alcanza cuando `n < 10`, punto en el cual la función retorna directamente `n` y 
    detiene la recursión.

 4. COMBINACIÓN DE RESULTADOS Y PASO RECURSIVO:
    Para combinar el proceso, la llamada recursiva retorna la suma del último dígito extraído 
    más el resultado de calcular recursivamente la suma de los dígitos del número restante:
        sumaDigitos(n) = (n % 10) + sumaDigitos(n / 10)

 5. VALIDACIÓN DE ENTRADA Y PRUEBAS A VALIDAR:
    - Si el número ingresado es menor a 0, lanza una excepción `IllegalArgumentException`.
    - Plan de casos de prueba:
        - Un solo dígito (Caso base): `sumaDigitos(7)` -> Esperado: `7`
        - Cero (Caso borde): `sumaDigitos(0)` -> Esperado: `0`
        - Múltiples dígitos con suma simple: `sumaDigitos(1234)` -> Esperado: `10` (1 + 2 + 3 + 4)
        - Números con ceros intermedios o finales: `sumaDigitos(50201)` -> Esperado: `8` (5 + 0 + 2 + 0 + 1)
        - Repetición de dígitos: `sumaDigitos(999)` -> Esperado: `27` (9 + 9 + 9)
 ====================================================================================
 */

public class Ejercicio7 {

    /**
     * Calcula la suma de los dígitos de un número entero no negativo de forma
     * recursiva.
     *
     * @param n Número entero no negativo.
     * @return La suma de todos los dígitos de n.
     * @throws IllegalArgumentException Si n es negativo.
     */
    public static int sumarDigitos(int n) {
        // Validación de números negativos
        if (n < 0) {
            throw new IllegalArgumentException("El número debe ser mayor o igual a 0.");
        }

        // CASO BASE: Si el número consta de un solo dígito (0 <= n < 10)
        if (n < 10) {
            return n;
        }

        // PASO RECURSIVO: Último dígito (n % 10) + suma de dígitos restantes (n / 10)
        return (n % 10) + sumarDigitos(n / 10);
    }

    public static void main(String[] args) {
        // Batería de pruebas
        int[][] pruebas = {
            {7, 7}, // Un solo dígito
            {0, 0}, // Cero
            {1234, 10}, // 1 + 2 + 3 + 4 = 10
            {50201, 8}, // 5 + 0 + 2 + 0 + 1 = 8
            {999, 27} // 9 + 9 + 9 = 27
        };

        System.out.println("--- PRUEBAS DE SUMA RECURSIVA DE DÍGITOS ---");
        for (int[] par : pruebas) {
            int numero = par[0];
            int esperado = par[1];

            int resultado = sumarDigitos(numero);
            boolean pasaPrueba = (resultado == esperado);

            System.out.printf("Número: %-8d | Resultado: %-2d | Esperado: %-2d -> [%s]%n",
                    numero, resultado, esperado, pasaPrueba ? "OK" : "ERROR");
        }
    }
}

/* ...CORRECCIONES HECHAS POR OPENCODE
====================================================================================
CORRECCIONES HECHAS POR OPENCODE
====================================================================================
[REDUNDANTE] El comentario "// PASO RECURSIVO: Último dígito (n % 10) + suma de dígitos restantes (n / 10)" reproduce literalmente la fórmula del punto 4 del PROMPT de cabecera.
[PROMPT] El PROMPT no especifica el tipo de retorno; el código usa `int`, que alcanza (la suma máxima de un int es 46: 2+1+4+7+4+8+3+6+4+7), pero esa elección no está en el enunciado.
====================================================================================
*/

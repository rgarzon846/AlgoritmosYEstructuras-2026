/*
 ====================================================================================
                                        PROMPT 
 ====================================================================================
 "Escribe un método estático en Java para calcular la potencia de una base 'b' 
 elevada a un exponente 'e' de forma recursiva, SIN utilizar Math.pow().

 Requisitos y fundamentos del algoritmo:
 1. POTENCIA COMO MULTIPLICACIONES SUCESIVAS:
    Matemáticamente, la potencia b^e representa multiplicar la base 'b' por sí misma 
    un total de 'e' veces:
        b^e = b * b * b * ... (e veces)

 2. CASO BASE (EXPONENTE 0):
    Por definición matemática (propiedad de exponentes), cualquier número (excepto 0^0) 
    elevado a la potencia 0 es igual a 1 (b^0 = 1). Por lo tanto, cuando el exponente 'e' 
    llega a 0, la función debe devolver 1. Este actúa como el elemento neutro de la 
    multiplicación y detiene la recursión.

 3. REDUCCIÓN DEL EXPONENTE Y PASO RECURSIVO:
    El parámetro que se reduce en cada llamada recursiva es el exponente 'e' en 1 unidad 
    (e - 1). La relación recursiva se define como:
        potencia(b, e) = b * potencia(b, e - 1)

 4. VALORES DE RETORNO:
    - Si e == 0: Retorna 1 (caso base).
    - Si e > 0: Retorna 'b' multiplicado por el resultado de la llamada recursiva `potencia(b, e - 1)`.
    - Si e < 0 (Exponentes negativos): Aplica la propiedad b^(-e) = 1 / (b^e). Retorna 
      `1.0 / potencia(b, -e)`.

 5. PLAN DE PRUEBAS DE VALIDACIÓN:
    Se deben incluir los siguientes casos de prueba:
    - Exponente cero: `potencia(5, 0)` -> Esperado: `1.0`
    - Exponente uno: `potencia(5, 1)` -> Esperado: `5.0`
    - Potencia estándar positiva: `potencia(2, 4)` -> Esperado: `16.0`
    - Base negativa con exponente par e impar:
        - `potencia(-2, 3)` -> Esperado: `-8.0`
        - `potencia(-2, 4)` -> Esperado: `16.0`
    - Exponente negativo: `potencia(2, -2)` -> Esperado: `0.25` (1 / 4)
 ====================================================================================
 */

public class Ejercicio4 {

    /**
     * Calcula la potencia b^e de forma recursiva sin usar Math.pow.
     *
     * @param base Número base.
     * @param exponente Exponente (entero).
     * @return El resultado de elevar la base al exponente.
     */
    public static double potencia(double base, int exponente) {
        // Manejo de exponente negativo: b^(-e) = 1 / (b^e)
        if (exponente < 0) {
            return 1.0 / potencia(base, -exponente);
        }

        // CASO BASE: Cualquier número elevado a la 0 es 1
        if (exponente == 0) {
            return 1.0;
        }

        // PASO RECURSIVO: base * potencia(base, exponente - 1)
        return base * potencia(base, exponente - 1);
    }

    public static void main(String[] args) {
        // Batería de pruebas
        Object[][] pruebas = {
            {5.0, 0, 1.0}, // Exponente 0 -> 1.0
            {5.0, 1, 5.0}, // Exponente 1 -> 5.0
            {2.0, 4, 16.0}, // Positivo -> 16.0
            {-2.0, 3, -8.0}, // Base negativa exp impar -> -8.0
            {-2.0, 4, 16.0}, // Base negativa exp par -> 16.0
            {2.0, -2, 0.25} // Exponente negativo -> 0.25
        };

        System.out.println("--- EJECUCIÓN DE PRUEBAS DE POTENCIA RECURSIVA ---");
        for (Object[] prueba : pruebas) {
            double base = (double) prueba[0];
            int exponente = (int) prueba[1];
            double esperado = (double) prueba[2];

            double resultado = potencia(base, exponente);
            boolean pasaPrueba = Math.abs(resultado - esperado) < 1e-9; // Tolerancia para punto flotante

            System.out.printf("%.1f ^ %d = %.4f (Esperado: %.4f) -> [%s]%n",
                    base, exponente, resultado, esperado, pasaPrueba ? "OK" : "ERROR");
        }
    }
}

/* ...CORRECCIONES HECHAS POR OPENCODE
====================================================================================
CORRECCIONES HECHAS POR OPENCODE
====================================================================================
[REDUNDANTE] Los comentarios "// CASO BASE: ..." y "// PASO RECURSIVO: ..." repiten textualmente los puntos 2 y 3 del bloque PROMPT de cabecera, que ya explican exactamente lo mismo.
[PROMPT] El PROMPT nunca declara el tipo de retorno; el código elige `double`, deducible de los valores esperados (1.0, 0.25) pero no fijado por el enunciado.
====================================================================================
*/

/*
 ====================================================================================
                                        PROMPT
 ====================================================================================
 "Escribe un método estático en Java para calcular la suma recursiva de los números
 enteros desde n hasta 1.
 
 Requisitos y fundamentos del algoritmo:
 1. CASO MÁS SIMPLE Y CASO BASE (n = 0):
    El caso más simple ocurre cuando n es igual a 0. Si n es 0, la suma de los números 
    hasta 1 no contiene elementos, por lo que la función debe retornar 0. Este actúa 
    como la condición de parada de la recursividad para evitar un bucle infinito.

 2. LÓGICA DE LA RECURSIÓN:
    La suma acumulada suma(n) puede expresarse de forma inductiva como:
        suma(n) = n + suma(n - 1)
    Esto se debe a que la suma de los primeros n números es simplemente el número actual 'n'
    más la suma de todos los enteros anteriores desde (n - 1) hasta 1.

 3. VALIDACIÓN DE ENTRADA:
    Si n es un número negativo, lanza una excepción de tipo IllegalArgumentException,
    ya que el rango de suma recursiva solo está definido para enteros no negativos.

 4. CÓMO VALIDAR QUE LA FUNCIÓN FUNCIONA CORRECTAMENTE:
    - Validación Matemática: Compara el resultado devuelto por la función recursiva 
      contra la fórmula directa de la suma de los primeros n números de Gauss:
          Suma = (n * (n + 1)) / 2
      Por ejemplo, para n = 5: 5 + 4 + 3 + 2 + 1 = 15, lo cual coincide con (5 * 6) / 2 = 15.
    - Casos Borde: Comprueba que suma(0) devuelva 0 y que suma(1) devuelva 1.
 ====================================================================================
 */

public class Ejercicio2 {

    /**
     * Calcula la suma de los enteros desde n hasta 1 de forma recursiva.
     *
     * @param n Número entero no negativo.
     * @return La suma acumulada desde n hasta 1.
     * @throws IllegalArgumentException Si n es menor que 0.
     */
    public static int sumarHastaUno(int n) {
        // Validación de números negativos
        if (n < 0) {
            throw new IllegalArgumentException("El número debe ser mayor o igual a 0.");
        }

        // CASO BASE (Caso más simple): si n es 0, la suma es 0
        if (n == 0) {
            return 0;
        }

        // PASO RECURSIVO: suma(n) = n + suma(n - 1)
        return n + sumarHastaUno(n - 1);
    }

    public static void main(String[] args) {
        int n = 5;
        int resultado = sumarHastaUno(n);

        // Validación mediante la fórmula de Gauss: (n * (n + 1)) / 2
        int resultadoEsperado = (n * (n + 1)) / 2;

        System.out.println("Suma recursiva de " + n + " hasta 1: " + resultado);
        System.out.println("Validación con fórmula de Gauss: " + resultadoEsperado);
        System.out.println("¿Es correcto? " + (resultado == resultadoEsperado));
    }
}

/* ...CORRECCIONES HECHAS POR OPENCODE
====================================================================================
CORRECCIONES HECHAS POR OPENCODE
====================================================================================
[REDUNDANTE] El comentario "// Validación de números negativos" sobre el `if (n < 0)` sobra: la condición y el mensaje de la excepción ya lo dicen todo.
[PROMPT] El PROMPT pedía además comprobar los casos borde suma(0) = 0 y suma(1) = 1, pero el `main` sólo prueba n = 5, así que esos casos nunca se ejecutan.
====================================================================================
*/

/*
Prompt inicial (breve):

Implementá en Java un verificador de paréntesis balanceados usando una pila enlazada propia
(sin colecciones de la JDK). Recibe una expresión como String y devuelve true/false. La pila
guarda cada apertura que aparece; al encontrar un cierre, se compara con el tope de la pila
(debe ser su apertura correspondiente) y se saca. Si la pila termina vacía y nunca hubo
desbalance, la expresión es válida. Manejar casos borde: cadena vacía, cadena sin paréntesis,
cierre sin apertura previa y aperturas sobrantes al final. Soportar (), [] y {}. Mostrar
ejemplos válidos ("(2 + 3) * (5 - 1)", "((a + b) * c)") e inválidos ("(2 + 3", "())(") en
main con datos fijos. Complejidad O(n).
Ajustes posteriores: ninguno.
*/

/**
 * Ejercicio 7 — Verificador de paréntesis balanceados.
 * Usa una pila enlazada (LIFO) creada desde cero para comparar aperturas y cierres.
 */
public class Ejercicio7 {

    /**
     * Nodo de la pila enlazada: guarda un carácter y una referencia al nodo de abajo.
     */
    static class NodoPila {

        private char dato;
        private NodoPila siguiente;

        /**
         * Crea un nodo con el carácter indicado.
         *
         * @param dato carácter a almacenar.
         */
        public NodoPila(char dato) {
            this.dato = dato;
            this.siguiente = null;
        }

        public char getDato() {
            return dato;
        }

        public NodoPila getSiguiente() {
            return siguiente;
        }

        public void setSiguiente(NodoPila siguiente) {
            this.siguiente = siguiente;
        }
    }

    /**
     * Pila enlazada de caracteres (LIFO). El tope es el único punto de acceso.
     */
    static class PilaCaracteres {

        private NodoPila tope;
        private int tamaño;

        /**
         * Crea una pila vacía.
         */
        public PilaCaracteres() {
            this.tope = null;
            this.tamaño = 0;
        }

        /**
         * Indica si la pila no tiene elementos.
         *
         * @return true si está vacía.
         */
        public boolean estaVacia() {
            return this.tope == null;
        }

        /**
         * Devuelve el tamaño actual de la pila.
         *
         * @return cantidad de caracteres apilados.
         */
        public int getTamaño() {
            return this.tamaño;
        }

        /**
         * Apila un carácter sobre el tope. O(1)
         *
         * @param c carácter a apilar.
         */
        public void apilar(char c) {
            NodoPila nuevo = new NodoPila(c);
            nuevo.setSiguiente(this.tope); // El nuevo nodo apunta al tope anterior.
            this.tope = nuevo;
            this.tamaño++;
        }

        /**
         * Desapila y devuelve el carácter del tope. O(1)
         *
         * @return el carácter del tope, o null si la pila está vacía.
         */
        public Character desapilar() {
            if (estaVacia()) {
                return null; // Caso borde: intento de desapilar pila vacía.
            }
            char valor = this.tope.getDato();
            this.tope = this.tope.getSiguiente(); // El nuevo tope pasa a ser el de abajo.
            this.tamaño--;
            return valor;
        }

        /**
         * Devuelve el carácter del tope sin quitarlo. O(1)
         *
         * @return el carácter del tope, o null si la pila está vacía.
         */
        public Character verTope() {
            if (estaVacia()) {
                return null;
            }
            return this.tope.getDato();
        }
    }

    /**
     * Devuelve el carácter de apertura correspondiente a un cierre.
     *
     * @param cierre carácter de cierre ')' ']' o '}'.
     * @return la apertura esperada, o 0 si el carácter no es un cierre conocido.
     */
    private static char aperturaCorrespondiente(char cierre) {
        switch (cierre) {
            case ')': return '(';
            case ']': return '[';
            case '}': return '{';
            default: return 0;
        }
    }

    /**
     * Determina si los paréntesis/corchetes/llaves de la expresión están balanceados.
     * Recorre la expresión una sola vez: apila aperturas y valida cierres contra el tope.
     * Complejidad: O(n) en el largo de la expresión.
     *
     * @param expresión texto a analizar (puede contener cualquier carácter).
     * @return true si todas las delimitadores cierran en el orden correcto y no sobra ninguno.
     */
    public static boolean estaBalanceada(String expresión) {
        // Caso borde: cadena vacía se considera balanceada (no hay delimitadores que desbalancear).
        if (expresión == null || expresión.isEmpty()) {
            return true;
        }

        PilaCaracteres pila = new PilaCaracteres();

        for (int i = 0; i < expresión.length(); i++) {
            char c = expresión.charAt(i);

            if (c == '(' || c == '[' || c == '{') {
                pila.apilar(c); // Se guarda la apertura pendiente de cerrar.
            } else if (c == ')' || c == ']' || c == '}') {
                // Caso borde: un cierre sin apertura previa (o con una distinta) => imbalance.
                if (pila.estaVacia()) {
                    return false;
                }
                Character tope = pila.verTope();
                if (tope != aperturaCorrespondiente(c)) {
                    return false; // Cierra con un tipo distinto al que está arriba.
                }
                pila.desapilar(); // Se consume la apertura correspondiente.
            }
            // Los demás caracteres (números, letras, operadores, espacios) se ignoran.
        }

        // Si la pila quedó con elementos, hay aperturas sin cerrar => imbalance.
        return pila.estaVacia();
    }

    /**
     * Método principal: prueba el verificador con expresiones fijas (sin teclado).
     *
     * @param args argumentos de línea de comandos (no se usan).
     */
    public static void main(String[] args) {
        String[] validas = {
            "(2 + 3) * (5 - 1)",
            "((a + b) * c)",
            "",                                   // Caso borde: cadena vacía.
            "12345",                              // Caso borde: sin paréntesis.
            "{x + [y * (z - 1)]}",               // Mezcla de (), [] y {}.
            "([{()}])"                            // Anidamiento profundo y correcto.
        };

        String[] invalidas = {
            "(2 + 3",
            "())(",
            ")(",                                 // Caso borde: cierre antes de apertura.
            "((a + b)",                           // Falta un cierre.
            "{x + [y - 1}",                       // Llave sin cerrar.
            "([)]"                                // Orden de cierre incorrecto.
        };

        System.out.println("=== Verificador de paréntesis balanceados (pila enlazada) ===");

        System.out.println("\n--- Expresiones VÁLIDAS ---");
        for (String expresión : validas) {
            String visible = expresión.isEmpty() ? "(cadena vacía)" : "\"" + expresión + "\"";
            System.out.println(visible + " -> " + (estaBalanceada(expresión) ? "BALANCEADA" : "NO balanceada"));
        }

        System.out.println("\n--- Expresiones INVÁLIDAS ---");
        for (String expresión : invalidas) {
            System.out.println("\"" + expresión + "\" -> " + (estaBalanceada(expresión) ? "BALANCEADA" : "NO balanceada"));
        }

        // Caso borde adicional: null.
        System.out.println("\n(null) -> " + (estaBalanceada(null) ? "BALANCEADA" : "NO balanceada"));
    }
}

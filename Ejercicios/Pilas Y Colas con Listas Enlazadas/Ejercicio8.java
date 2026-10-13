/*
Prompt inicial (breve):

Implementá en Java una pila enlazada de caracteres que invierta una palabra, sin colecciones
de la JDK. La pila es LIFO: el último carácter apilado es el primero que sale, por eso al
apilar "algoritmo" completo y luego desapilar carácter a carácter se obtiene "omtirogla".
Operaciones: apilar, desapilar, estaVacia, verTope, más invertirPalabra(String) que usa la
pila internamente y devuelve la cadena invertida. Casos borde: cadena vacía y cadena de un
solo carácter (se devuelven tal cual). Mostrar varios ejemplos en main con datos fijos, sin
Scanner. Complejidad de invertirPalabra: O(n).
Ajustes posteriores: ninguno.
*/

/**
 * Ejercicio 8 — Invertir una palabra con pila enlazada.
 * Aprovecha el comportamiento LIFO de la pila para revertir el orden de los caracteres.
 */
public class Ejercicio8 {

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
     * Pila enlazada de caracteres (LIFO): el último en entrar es el primero en salir.
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
            this.tope = this.tope.getSiguiente();
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
     * Invierte una palabra apilando todos sus caracteres y desapilándolos en orden.
     * Al ser LIFO, el último carácter apilado (el último de la palabra) es el primero
     * en salir, lo que invierte la palabra. O(n)
     *
     * @param palabra texto a invertir (puede ser vacío o null).
     * @return la palabra con el orden de caracteres invertido; null si la entrada es null.
     */
    public static String invertirPalabra(String palabra) {
        // Casos borde: null o cadena vacía se devuelven tal cual.
        if (palabra == null || palabra.isEmpty()) {
            return palabra;
        }

        PilaCaracteres pila = new PilaCaracteres();

        // Se apila cada carácter de izquierda a derecha.
        for (int i = 0; i < palabra.length(); i++) {
            pila.apilar(palabra.charAt(i));
        }

        // Se desapilan todos: el primero en salir es el último carácter de la palabra.
        StringBuilder invertida = new StringBuilder();
        while (!pila.estaVacia()) {
            invertida.append(pila.desapilar());
        }

        return invertida.toString();
    }

    /**
     * Método principal: prueba la inversión con varias palabras fijas (sin teclado).
     *
     * @param args argumentos de línea de comandos (no se usan).
     */
    public static void main(String[] args) {
        System.out.println("=== Invertir una palabra con pila enlazada ===");
        System.out.println("(La pila es LIFO: el último carácter apilado es el primero en salir)");

        // Ejemplo principal de la consigna.
        String palabra = "algoritmo";
        System.out.println("\nPalabra original: \"" + palabra + "\"");
        System.out.println("Palabra invertida: \"" + invertirPalabra(palabra) + "\""); // "omtirogla"

        // Otros ejemplos.
        String[] pruebas = {"hola", "anilina", "python", "a", ""};
        System.out.println("\n--- Otros ejemplos ---");
        for (String p : pruebas) {
            String visible = p.isEmpty() ? "(cadena vacía)" : "\"" + p + "\"";
            String resultado = invertirPalabra(p);
            String visibleResultado = (resultado == null || resultado.isEmpty())
                    ? "(cadena vacía)" : "\"" + resultado + "\"";
            System.out.println(visible + " -> " + visibleResultado);
        }

        // Caso borde: null (se muestra con comprobación explícita).
        String nula = null;
        String resultadoNulo = invertirPalabra(nula);
        System.out.println("(null) -> " + (resultadoNulo == null ? "null" : "\"" + resultadoNulo + "\""));
    }
}

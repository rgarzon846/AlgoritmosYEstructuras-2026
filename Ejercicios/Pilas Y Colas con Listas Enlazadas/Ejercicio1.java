/*
Prompt inicial utilizado:
Implementá en Java una pila (stack) de enteros desde cero con lista enlazada simple, sin
colecciones de la JDK (LinkedList, ArrayList, etc.) ni Scanner/System.in.
Creá un Nodo (dato/siguiente) y una PilaEnlazada donde head es el tope: apilar y desapilar
son O(1) porque solo se ajusta head y la referencia siguiente.
Operaciones: apilar, desapilar, tope, verificar vacía, contiene, imprimir.
Caso borde: desapilar o tope sobre pila vacía -> IllegalArgumentException. Indicar complejidades.
Ajustes posteriores: ninguno.
*/

/**
 * Ejercicio 1: Pila enlazada de enteros (LIFO) construida con lista enlazada simple.
 *
 * <p>La referencia {@code head} de la lista representa el TOPE de la pila, de modo que
 * apilar y desapilar se reducen a ajustar punteros al inicio de la lista: O(1).</p>
 */
public class Ejercicio1 {

    // ---------------------------------------------------------------------
    // Nodo de la lista enlazada simple
    // ---------------------------------------------------------------------
    static class Nodo {

        private int dato;
        private Nodo siguiente;

        /**
         * Crea un nodo con el dato indicado y sin enlace (siguiente en null).
         *
         * @param dato valor entero a almacenar
         */
        public Nodo(int dato) {
            this.dato = dato;
            this.siguiente = null;
        }

        public int getDato() {
            return dato;
        }

        public Nodo getSiguiente() {
            return siguiente;
        }

        public void setSiguiente(Nodo siguiente) {
            this.siguiente = siguiente;
        }
    }

    // ---------------------------------------------------------------------
    // Pila de enteros sobre lista enlazada
    // ---------------------------------------------------------------------
    static class PilaEnlazada {

        private Nodo head; // TOPE de la pila; null significa pila vacía

        public PilaEnlazada() {
            this.head = null;
        }

        /**
         * Consulta si la pila no tiene elementos.
         *
         * @return {@code true} si la pila está vacía
         * Complejidad: O(1)
         */
        public boolean estaVacia() {
            return this.head == null;
        }

        /**
         * Apila un valor en el tope de la pila.
         *
         * @param valor entero a apilar
         * Complejidad: O(1) - solo se crean dos enlaces.
         */
        public void apilar(int valor) {
            Nodo nuevo = new Nodo(valor);
            nuevo.setSiguiente(this.head); // el nuevo nodo apunta al antiguo tope
            this.head = nuevo;             // el nuevo nodo pasa a ser el tope
        }

        /**
         * Desapila y devuelve el elemento del tope.
         *
         * @return valor que estaba en el tope
         * @throws IllegalArgumentException si la pila está vacía (caso borde)
         * Complejidad: O(1) - solo se mueve la referencia head.
         */
        public int desapilar() {
            if (estaVacia()) {
                throw new IllegalArgumentException("La pila está vacía: no se puede desapilar.");
            }
            int valor = this.head.getDato();
            this.head = this.head.getSiguiente(); // el siguiente nodo pasa a ser el tope
            return valor;
        }

        /**
         * Consulta el valor del tope sin desapilarlo.
         *
         * @return valor del tope
         * @throws IllegalArgumentException si la pila está vacía (caso borde)
         * Complejidad: O(1)
         */
        public int tope() {
            if (estaVacia()) {
                throw new IllegalArgumentException("La pila está vacía: no hay tope que consultar.");
            }
            return this.head.getDato();
        }

        /**
         * Busca si la pila contiene un valor dado.
         *
         * @param valor entero a buscar
         * @return {@code true} si el valor está en la pila
         * Complejidad: O(n) - recorre la lista en el peor caso.
         */
        public boolean contiene(int valor) {
            Nodo actual = this.head;
            while (actual != null) {
                if (actual.getDato() == valor) {
                    return true;
                }
                actual = actual.getSiguiente();
            }
            return false;
        }

        /**
         * Imprime el contenido de la pila del tope hacia la base.
         * Complejidad: O(n)
         */
        public void imprimir() {
            if (estaVacia()) {
                System.out.println("La pila está vacía.");
                return;
            }

            Nodo actual = this.head;
            System.out.print("tope -> ");
            while (actual != null) {
                System.out.print("[" + actual.getDato() + "]");
                if (actual.getSiguiente() != null) {
                    System.out.print(" -> ");
                }
                actual = actual.getSiguiente();
            }
            System.out.println(" -> base");
        }
    }

    // ---------------------------------------------------------------------
    // Pruebas con datos fijos (sin entrada por teclado)
    // ---------------------------------------------------------------------
    public static void main(String[] args) {
        PilaEnlazada pila = new PilaEnlazada();

        System.out.println("¿La pila está vacía al inicio?: " + pila.estaVacia()); // true

        System.out.println("\n--- Apilando elementos ---");
        pila.apilar(10);
        pila.apilar(20);
        pila.apilar(30);

        System.out.print("Contenido: ");
        pila.imprimir(); // tope -> [30] -> [20] -> [10] -> base

        System.out.println("Tope: " + pila.tope());                 // 30
        System.out.println("¿Contiene 20?: " + pila.contiene(20));  // true
        System.out.println("¿Contiene 99?: " + pila.contiene(99));  // false

        System.out.println("\n--- Desapilando elementos (LIFO) ---");
        System.out.println("Desapilado: " + pila.desapilar()); // 30
        System.out.println("Nuevo tope: " + pila.tope());       // 20
        System.out.println("Desapilado: " + pila.desapilar()); // 20
        System.out.println("Desapilado: " + pila.desapilar()); // 10

        System.out.println("¿La pila está vacía?: " + pila.estaVacia()); // true

        // Casos borde: operaciones sobre pila vacía
        System.out.println("\n--- Casos borde (pila vacía) ---");
        try {
            pila.desapilar();
        } catch (IllegalArgumentException e) {
            System.out.println("desapilar() -> " + e.getMessage());
        }
        try {
            pila.tope();
        } catch (IllegalArgumentException e) {
            System.out.println("tope() -> " + e.getMessage());
        }
    }
}

/*
Prompt inicial utilizado:
Implementá en Java una cola de enteros desde cero con lista enlazada simple, sin colecciones
de la JDK (LinkedList, ArrayList, etc.) ni Scanner/System.in.
La cola necesita head (frente) y tail (último): usando solo head, encolar sería O(n) porque
habría que recorrer la lista hasta el final para enlazar el nuevo elemento.
Operaciones: encolar, desencolar, consultar frente, verificar vacía, contiene, imprimir.
Casos borde: desencolar/frente sobre cola vacía -> IllegalArgumentException; al vaciar la cola,
tail debe quedar en null. Indicar complejidades. Ajustes posteriores: ninguno.
*/

/**
 * Ejercicio 2: Cola de enteros (FIFO) construida con lista enlazada simple.
 *
 * <p>Se mantienen dos referencias: {@code head} es el FRENTE (de donde se sacan los elementos)
 * y {@code tail} es el ÚLTIMO (donde se agregan). Con solo {@code head}, encolar costaría O(n)
 * por el recorrido hasta el final; con {@code tail} es O(1).</p>
 */
public class Ejercicio2 {

    // ---------------------------------------------------------------------
    // Nodo de la lista enlazada simple
    // ---------------------------------------------------------------------
    static class Nodo {

        private int dato;
        private Nodo siguiente;

        /**
         * Crea un nodo con el dato indicado y sin enlace.
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
    // Cola de enteros sobre lista enlazada
    // ---------------------------------------------------------------------
    static class ColaEnlazada {

        private Nodo head; // FRENTE de la cola (de donde se desencola); null si está vacía
        private Nodo tail; // ÚLTIMO de la cola (donde se encola); null si está vacía

        public ColaEnlazada() {
            this.head = null;
            this.tail = null;
        }

        /**
         * Consulta si la cola no tiene elementos.
         *
         * @return {@code true} si la cola está vacía
         * Complejidad: O(1)
         */
        public boolean estaVacia() {
            return this.head == null;
        }

        /**
         * Agrega un valor al final de la cola.
         *
         * @param valor entero a encolar
         * Complejidad: O(1) - gracias a la referencia tail no se recorre la lista.
         */
        public void encolar(int valor) {
            Nodo nuevo = new Nodo(valor);
            if (estaVacia()) {
                this.head = nuevo; // la cola vacía: frente y final son el mismo nodo
            } else {
                this.tail.setSiguiente(nuevo); // el antiguo último enlaza al nuevo
            }
            this.tail = nuevo; // el nuevo nodo pasa a ser el último
        }

        /**
         * Extrae y devuelve el valor del frente de la cola.
         *
         * @return valor que estaba en el frente
         * @throws IllegalArgumentException si la cola está vacía (caso borde)
         * Complejidad: O(1)
         */
        public int desencolar() {
            if (estaVacia()) {
                throw new IllegalArgumentException("La cola está vacía: no se puede desencolar.");
            }
            int valor = this.head.getDato();
            this.head = this.head.getSiguiente(); // el siguiente pasa a ser el frente
            if (this.head == null) {
                this.tail = null; // caso borde: la cola quedó vacía, tail debe quedar en null
            }
            return valor;
        }

        /**
         * Consulta el valor del frente sin desencolarlo.
         *
         * @return valor del frente
         * @throws IllegalArgumentException si la cola está vacía (caso borde)
         * Complejidad: O(1)
         */
        public int frente() {
            if (estaVacia()) {
                throw new IllegalArgumentException("La cola está vacía: no hay frente que consultar.");
            }
            return this.head.getDato();
        }

        /**
         * Busca si la cola contiene un valor dado.
         *
         * @param valor entero a buscar
         * @return {@code true} si el valor está en la cola
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
         * Imprime el contenido de la cola del frente hacia el final.
         * Complejidad: O(n)
         */
        public void imprimir() {
            if (estaVacia()) {
                System.out.println("La cola está vacía.");
                return;
            }

            Nodo actual = this.head;
            System.out.print("frente -> ");
            while (actual != null) {
                System.out.print("[" + actual.getDato() + "]");
                if (actual.getSiguiente() != null) {
                    System.out.print(" -> ");
                }
                actual = actual.getSiguiente();
            }
            System.out.println(" -> null");
        }
    }

    // ---------------------------------------------------------------------
    // Pruebas con datos fijos (sin entrada por teclado)
    // ---------------------------------------------------------------------
    public static void main(String[] args) {
        ColaEnlazada cola = new ColaEnlazada();

        System.out.println("¿La cola está vacía al inicio?: " + cola.estaVacia()); // true

        System.out.println("\n--- Encolando elementos ---");
        cola.encolar(100);
        cola.encolar(200);
        cola.encolar(300);

        System.out.print("Contenido: ");
        cola.imprimir(); // frente -> [100] -> [200] -> [300] -> null

        System.out.println("Frente: " + cola.frente());                 // 100
        System.out.println("¿Contiene 200?: " + cola.contiene(200));    // true
        System.out.println("¿Contiene 999?: " + cola.contiene(999));    // false

        System.out.println("\n--- Desencolando elementos (FIFO) ---");
        System.out.println("Desencolado: " + cola.desencolar()); // 100 (sale primero el primero)
        System.out.println("Nuevo frente: " + cola.frente());     // 200
        System.out.println("Desencolado: " + cola.desencolar()); // 200
        System.out.println("Desencolado: " + cola.desencolar()); // 300

        System.out.println("¿La cola está vacía?: " + cola.estaVacia()); // true

        // Caso borde: operaciones sobre cola vacía
        System.out.println("\n--- Casos borde (cola vacía) ---");
        try {
            cola.desencolar();
        } catch (IllegalArgumentException e) {
            System.out.println("desencolar() -> " + e.getMessage());
        }
        try {
            cola.frente();
        } catch (IllegalArgumentException e) {
            System.out.println("frente() -> " + e.getMessage());
        }

        // Caso borde: reutilizar la cola después de vaciarla (tail quedó en null)
        System.out.println("\n--- Reutilización tras vaciar ---");
        cola.encolar(400);
        cola.encolar(500);
        System.out.print("Contenido: ");
        cola.imprimir(); // frente -> [400] -> [500] -> null
        System.out.println("Frente: " + cola.frente()); // 400
    }
}

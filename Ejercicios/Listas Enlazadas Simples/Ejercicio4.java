/*
Prompt inicial utilizado:
Implementá en Java el método insertarEnPosicion(int dato, int posicion) sobre una lista enlazada
simple de enteros, sin colecciones de la JDK ni Scanner/System.in.
El método debe permitir insertar al inicio (posicion == 0), en el medio y al final
(posicion == tamaño se acepta como "insertar al final"); validar posicion < 0 o
posicion > tamaño con IllegalArgumentException (nota: posicion == tamaño es válida).
El orden de actualización de referencias es clave: primero nuevo.setSiguiente(actual.getSiguiente())
y después actual.setSiguiente(nuevo); si se hace al revés se pierde el resto de la lista
(la cola queda inaccesible para el GC y se produce un error).
En posicion == 0, como no hay nodo previo, se reutiliza la lógica de insertarAlInicio.
Incluí también los métodos base (insertarAlInicio, insertarAlFinal, imprimir, estaVacia,
getSize). Indicar complejidades. Ajustes posteriores: ninguno.
*/

/**
 * Ejercicio 4: Inserción en cualquier posición de una lista enlazada simple de enteros.
 *
 * <p>La posición pedida puede ser el inicio (0), el medio o el final (posicion == tamaño).
 * El orden de los punteros es crítico: primero se enlaza el nuevo nodo con el resto de la
 * lista y recién después se apunta a él desde el nodo anterior; al revés se pierde la cola.</p>
 */
public class Ejercicio4 {

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
    // Lista enlazada simple de enteros
    // ---------------------------------------------------------------------
    static class ListaEnlazada {

        private Nodo head; // primer nodo; null significa lista vacía
        private int tamaño; // cantidad de nodos de la lista

        public ListaEnlazada() {
            this.head = null;
            this.tamaño = 0;
        }

        /**
         * Consulta si la lista no tiene elementos.
         *
         * @return {@code true} si la lista está vacía
         * Complejidad: O(1)
         */
        public boolean estaVacia() {
            return this.head == null;
        }

        /**
         * Devuelve la cantidad de elementos de la lista.
         *
         * @return tamaño actual de la lista
         * Complejidad: O(1)
         */
        public int getSize() {
            return this.tamaño;
        }

        /**
         * Inserta un valor al inicio de la lista (nuevo head).
         *
         * @param valor entero a insertar
         * Complejidad: O(1)
         */
        public void insertarAlInicio(int valor) {
            Nodo nuevo = new Nodo(valor);
            nuevo.setSiguiente(this.head); // el nuevo nodo apunta al antiguo head
            this.head = nuevo;             // el nuevo nodo pasa a ser el head
            this.tamaño++;
        }

        /**
         * Inserta un valor al final de la lista (después del último nodo).
         *
         * @param valor entero a insertar
         * Complejidad: O(n)
         */
        public void insertarAlFinal(int valor) {
            Nodo nuevo = new Nodo(valor);
            if (estaVacia()) {
                this.head = nuevo; // lista vacía: el nuevo nodo es el único
            } else {
                Nodo actual = this.head;
                while (actual.getSiguiente() != null) { // avanzo hasta el último
                    actual = actual.getSiguiente();
                }
                actual.setSiguiente(nuevo); // el antiguo último apunta al nuevo
            }
            this.tamaño++;
        }

        /**
         * Inserta un valor en la posición indicada (0 = inicio, tamaño = al final).
         *
         * <p>Para insertar en el medio se recorre hasta el nodo anterior ({@code anterior});
         * el orden de referencias es: primero {@code nuevo.setSiguiente(actual.getSiguiente())}
         * y después {@code actual.setSiguiente(nuevo)}. Si se invierte el orden, el resto de la
         * lista queda colgando de un nodo que ya no es referenciado y se pierde la cola.</p>
         *
         * @param valor entero a insertar
         * @param posicion índice donde insertar (0 basado; posicion == tamaño equivale a al final)
         * @throws IllegalArgumentException si posicion &lt; 0 o posicion &gt; tamaño (caso borde)
         * Complejidad: O(n) - recorre hasta la posición en el peor caso.
         */
        public void insertarEnPosicion(int valor, int posicion) {
            if (posicion < 0 || posicion > this.tamaño) {
                throw new IllegalArgumentException(
                        "Posición inválida: " + posicion + " (permitidas de 0 a " + this.tamaño + ").");
            }

            if (posicion == 0) { // caso inicio: no hay nodo anterior, se reutiliza insertarAlInicio
                insertarAlInicio(valor);
                return;
            }

            Nodo actual = this.head;
            for (int i = 0; i < posicion - 1; i++) { // paro en el nodo ANTERIOR a la posición
                actual = actual.getSiguiente();
            }

            Nodo nuevo = new Nodo(valor);
            // Orden correcto: primero enlazo el nuevo con el resto...
            nuevo.setSiguiente(actual.getSiguiente());
            // ...recién después conecto el anterior al nuevo (al revés se pierde la cola).
            actual.setSiguiente(nuevo);
            this.tamaño++;
        }

        /**
         * Imprime el contenido de la lista del head hacia la cola.
         * Complejidad: O(n)
         */
        public void imprimir() {
            if (estaVacia()) {
                System.out.println("La lista está vacía.");
                return;
            }

            Nodo actual = this.head;
            System.out.print("head -> ");
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
        ListaEnlazada lista = new ListaEnlazada();

        System.out.println("=== Lista vacía: insertarEnPosicion actúa como insertar ===");
        lista.insertarEnPosicion(30, 0); // lista vacía: posicion 0 == insertarAlInicio
        System.out.print("Contenido: ");
        lista.imprimir(); // head -> [30] -> null
        System.out.println("Tamaño: " + lista.getSize()); // 1

        System.out.println("\n=== Cargando más elementos ===");
        lista.insertarAlFinal(50);
        lista.insertarAlFinal(70);
        System.out.print("Contenido: ");
        lista.imprimir(); // head -> [30] -> [50] -> [70] -> null

        System.out.println("\n=== Inserción en distintas posiciones ===");
        lista.insertarEnPosicion(10, 0); // inicio
        System.out.print("Inserto 10 al inicio:      ");
        lista.imprimir(); // head -> [10] -> [30] -> [50] -> [70] -> null

        lista.insertarEnPosicion(40, 2); // medio (entre 30 y 50)
        System.out.print("Inserto 40 en posición 2:  ");
        lista.imprimir(); // head -> [10] -> [30] -> [40] -> [50] -> [70] -> null

        lista.insertarEnPosicion(80, lista.getSize()); // final: posicion == tamaño
        System.out.print("Inserto 80 al final:       ");
        lista.imprimir(); // head -> [10] -> [30] -> [40] -> [50] -> [70] -> [80] -> null
        System.out.println("Tamaño: " + lista.getSize()); // 6

        // Casos borde: posiciones fuera de rango -> IllegalArgumentException
        System.out.println("\n=== Casos borde (posiciones inválidas) ===");
        try {
            lista.insertarEnPosicion(99, -1);
        } catch (IllegalArgumentException e) {
            System.out.println("insertarEnPosicion(99, -1) -> " + e.getMessage());
        }
        try {
            lista.insertarEnPosicion(99, 7); // tamaño = 6, válidas de 0 a 6
        } catch (IllegalArgumentException e) {
            System.out.println("insertarEnPosicion(99, 7) -> " + e.getMessage());
        }
    }
}

/*
Prompt inicial utilizado:
Implementá en Java el método obtener(int posicion) sobre una lista enlazada simple de enteros,
sin colecciones de la JDK ni Scanner/System.in.
A diferencia de un arreglo, la lista no tiene acceso directo por índice: no hay un bloque
continuo de memoria donde saltar a posicion, solo referencias de nodo a nodo, por lo que
obtener debe recorrer desde head nodo a nodo hasta llegar a la posición pedida.
Validar posicion < 0 o posicion >= tamaño con IllegalArgumentException (casos borde).
Incluí también los métodos base (insertarAlInicio, insertarAlFinal, imprimir, estaVacia,
getSize). Indicar complejidades. Ajustes posteriores: ninguno.
*/

/**
 * Ejercicio 3: Obtención de un elemento por posición en una lista enlazada simple de enteros.
 *
 * <p>Sin acceso directo por índice (a diferencia de un arreglo), la posición se alcanza
 * recorriendo desde {@code head} nodo a nodo; posiciones fuera de rango lanzan
 * {@link IllegalArgumentException}.</p>
 */
public class Ejercicio3 {

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
         * Devuelve el valor almacenado en la posición indicada (0 = head).
         *
         * <p>No hay acceso directo como en un arreglo: se recorre nodo a nodo desde head
         * hasta alcanzar la posición pedida.</p>
         *
         * @param posicion índice del elemento a devolver (0 basado)
         * @return valor almacenado en esa posición
         * @throws IllegalArgumentException si posicion &lt; 0 o posicion &gt;= tamaño (caso borde)
         * Complejidad: O(n) - recorre hasta la posición en el peor caso.
         */
        public int obtener(int posicion) {
            if (posicion < 0 || posicion >= this.tamaño) {
                throw new IllegalArgumentException(
                        "Posición inválida: " + posicion + " (la lista tiene " + this.tamaño + " elementos).");
            }

            Nodo actual = this.head;
            for (int i = 0; i < posicion; i++) { // avanzo nodo a nodo hasta la posición
                actual = actual.getSiguiente();
            }
            return actual.getDato();
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

        System.out.println("=== Cargando la lista ===");
        lista.insertarAlFinal(7);
        lista.insertarAlFinal(14);
        lista.insertarAlFinal(21);
        lista.insertarAlFinal(28);
        lista.insertarAlFinal(35);

        System.out.print("Contenido: ");
        lista.imprimir(); // head -> [7] -> [14] -> [21] -> [28] -> [35] -> null
        System.out.println("Tamaño: " + lista.getSize()); // 5

        System.out.println("\n=== Accesos por posición ===");
        System.out.println("obtener(0): " + lista.obtener(0)); // 7   (head)
        System.out.println("obtener(2): " + lista.obtener(2)); // 21  (nodo del medio)
        System.out.println("obtener(4): " + lista.obtener(4)); // 35  (último nodo)

        // Casos borde: posiciones fuera de rango -> IllegalArgumentException
        System.out.println("\n=== Casos borde (posiciones inválidas) ===");
        try {
            lista.obtener(-1);
        } catch (IllegalArgumentException e) {
            System.out.println("obtener(-1) -> " + e.getMessage());
        }
        try {
            lista.obtener(5); // tamaño = 5, válidos van de 0 a 4
        } catch (IllegalArgumentException e) {
            System.out.println("obtener(5) -> " + e.getMessage());
        }

        // Caso borde: lista vacía -> cualquier posición es inválida
        System.out.println("\n=== Caso borde: lista vacía ===");
        ListaEnlazada vacia = new ListaEnlazada();
        try {
            vacia.obtener(0);
        } catch (IllegalArgumentException e) {
            System.out.println("obtener(0) sobre vacía -> " + e.getMessage());
        }
    }
}

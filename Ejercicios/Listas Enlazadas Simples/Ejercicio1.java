/*
Prompt inicial utilizado:
Implementá en Java una lista enlazada simple de enteros desde cero, sin colecciones de la JDK
(LinkedList, ArrayList, etc.) ni Scanner/System.in.
Creá un Nodo (dato int + referencia siguiente) y una ListaEnlazada con head: el primer nodo es
head y el último tiene siguiente en null; insertar al inicio cambia head en O(1) y al final hay
que recorrer hasta el último nodo.
Operaciones: insertarAlInicio, insertarAlFinal, imprimir, estaVacia, getSize (tamaño se
incrementa/decrementa en cada inserción para conocerlo en O(1)).
Indicar complejidades de cada método. Ajustes posteriores: ninguno.
*/

/**
 * Ejercicio 1: Lista enlazada simple de enteros construida desde cero.
 *
 * <p>Cada nodo guarda un entero y una referencia al siguiente; la lista mantiene la referencia
 * {@code head} al primer nodo (null si está vacía) y el {@code tamaño} actualizado en cada
 * inserción para poder consultarlo en O(1).</p>
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
         * Complejidad: O(1) - el tamaño se mantiene actualizado en cada inserción.
         */
        public int getSize() {
            return this.tamaño;
        }

        /**
         * Inserta un valor al inicio de la lista (nuevo nuevo head).
         *
         * @param valor entero a insertar
         * Complejidad: O(1) - solo se ajusta la referencia head.
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
         * Complejidad: O(n) - hay que recorrer hasta el último nodo.
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

        System.out.println("¿La lista está vacía al inicio?: " + lista.estaVacia()); // true
        System.out.println("Tamaño al inicio: " + lista.getSize());                    // 0

        System.out.println("\n--- Insertando al inicio ---");
        lista.insertarAlInicio(30);
        lista.insertarAlInicio(20);
        lista.insertarAlInicio(10);

        System.out.print("Contenido: ");
        lista.imprimir(); // head -> [10] -> [20] -> [30] -> null
        System.out.println("Tamaño: " + lista.getSize()); // 3

        System.out.println("\n--- Insertando al final ---");
        lista.insertarAlFinal(40);
        lista.insertarAlFinal(50);

        System.out.print("Contenido: ");
        lista.imprimir(); // head -> [10] -> [20] -> [30] -> [40] -> [50] -> null
        System.out.println("Tamaño: " + lista.getSize()); // 5

        System.out.println("\n--- Lista de un solo elemento ---");
        ListaEnlazada listaUna = new ListaEnlazada();
        listaUna.insertarAlFinal(99);
        System.out.print("Contenido: ");
        listaUna.imprimir(); // head -> [99] -> null
        System.out.println("¿Está vacía?: " + listaUna.estaVacia()); // false
    }
}

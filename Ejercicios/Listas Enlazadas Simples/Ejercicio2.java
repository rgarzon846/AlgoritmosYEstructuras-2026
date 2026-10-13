/*
Prompt inicial utilizado:
Implementá en Java el método buscar(int dato) sobre una lista enlazada simple de enteros, sin
colecciones de la JDK ni Scanner/System.in.
A diferencia de un arreglo, la lista no permite acceso directo por índice: no existe una
posición física continua de memoria, solo referencias de nodo a nodo, por lo que buscar
requiere un recorrido secuencial desde head.
La búsqueda arranca en head y avanza nodo a nodo hasta encontrar el dato o llegar a null
(no encontrado); devolver boolean y no lanzar excepción si no está.
Incluí también los métodos base (insertarAlInicio, insertarAlFinal, imprimir, estaVacia,
getSize) y los casos borde (lista vacía -> false). Indicar complejidades.
Ajustes posteriores: ninguno.
*/

/**
 * Ejercicio 2: Búsqueda secuencial en una lista enlazada simple de enteros.
 *
 * <p>Como la lista no tiene acceso directo por índice (solo hay referencias de nodo a nodo),
 * buscar exige recorrer desde {@code head} hasta encontrar el dato o llegar a {@code null}.</p>
 */
public class Ejercicio2 {

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
         * Busca si la lista contiene un valor dado (recorrido secuencial desde head).
         *
         * <p>No hay acceso directo por índice: se recorre nodo a nodo hasta encontrar el
         * dato o llegar a null.</p>
         *
         * @param valor entero a buscar
         * @return {@code true} si el valor está en la lista (false si no está o la lista está vacía)
         * Complejidad: O(n) - recorre la lista completa en el peor caso.
         */
        public boolean buscar(int valor) {
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

        // Caso borde: buscar en lista vacía -> false (sin lanzar excepción)
        System.out.println("=== Caso borde: lista vacía ===");
        System.out.println("¿Está vacía?: " + lista.estaVacia()); // true
        System.out.println("¿Contiene 10?: " + lista.buscar(10)); // false

        System.out.println("\n=== Cargando la lista ===");
        lista.insertarAlFinal(15);
        lista.insertarAlFinal(22);
        lista.insertarAlFinal(8);
        lista.insertarAlFinal(41);
        lista.insertarAlFinal(22); // repetido: buscar debe dar true en la primera aparición

        System.out.print("Contenido: ");
        lista.imprimir(); // head -> [15] -> [22] -> [8] -> [41] -> [22] -> null
        System.out.println("Tamaño: " + lista.getSize()); // 5

        System.out.println("\n=== Búsquedas ===");
        System.out.println("¿Contiene 15 (primer nodo)?: " + lista.buscar(15)); // true
        System.out.println("¿Contiene 8 (nodo del medio)?: " + lista.buscar(8)); // true
        System.out.println("¿Contiene 22 (repetido)?: " + lista.buscar(22)); // true
        System.out.println("¿Contiene 99 (inexistente)?: " + lista.buscar(99)); // false
        System.out.println("¿Contiene 0 (inexistente)?: " + lista.buscar(0)); // false
    }
}

/*
Prompt inicial utilizado:
Implementá en Java el método eliminar(int dato) sobre una lista enlazada simple de enteros, sin
colecciones de la JDK ni Scanner/System.in.
El método debe eliminar la PRIMERA aparición del dato y devolver boolean: false si la lista está
vacía o el dato no existe, true si se eliminó.
Casos a contemplar: lista vacía (false), nodo a eliminar es el head (se actualiza head),
nodo del medio (se re-enlaza el anterior con el siguiente), nodo final (el anterior queda en
null) y dato inexistente (false, sin modificar la lista).
En Java no se "borra" el nodo del heap: solo se quitan las referencias a él y queda inaccesible
para el Garbage Collector, que lo recicla después.
Incluí también los métodos base (insertarAlInicio, insertarAlFinal, imprimir, estaVacia,
getSize). Indicar complejidades. Ajustes posteriores: ninguno.
*/

/**
 * Ejercicio 5: Eliminación de la primera aparición de un dato en una lista enlazada simple.
 *
 * <p>Eliminar consiste en re-enlazar los nodos para que el nodo a sacar quede sin referencias:
 * en Java no se borra físicamente del heap, sino que el Garbage Collector lo recicla cuando ya
 * es inaccesible.</p>
 */
public class Ejercicio5 {

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
         * Elimina la primera aparición del dato indicado.
         *
         * <p>En Java el nodo no se borra del heap: se quitan las referencias y queda inaccesible
         * para el Garbage Collector. Casos: head (se actualiza head), medio (el anterior apunta
         * al siguiente), final (el anterior queda con siguiente en null) e inexistente (false).</p>
         *
         * @param valor entero a eliminar
         * @return {@code true} si se eliminó alguna aparición; {@code false} si la lista está
         *         vacía o el dato no existe
         * Complejidad: O(n) - recorre la lista hasta encontrar la primera aparición.
         */
        public boolean eliminar(int valor) {
            if (estaVacia()) {
                return false; // caso borde: lista vacía, nada que eliminar
            }

            if (this.head.getDato() == valor) { // caso: el head es el nodo a eliminar
                this.head = this.head.getSiguiente(); // el siguiente pasa a ser el head
                this.tamaño--;
                return true; // el nodo viejo queda sin referencias -> lo recicla el GC
            }

            Nodo anterior = this.head;
            while (anterior.getSiguiente() != null) {
                if (anterior.getSiguiente().getDato() == valor) {
                    // El nodo a eliminar es anterior.getSiguiente(); se "salta" con:
                    anterior.setSiguiente(anterior.getSiguiente().getSiguiente());
                    // Si era el final, ahora anterior.siguiente = null; si era del medio,
                    // el resto de la lista sigue colgando desde anterior.
                    this.tamaño--;
                    return true;
                }
                anterior = anterior.getSiguiente();
            }
            return false; // dato inexistente: la lista no se modifica
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

        // Caso borde: lista vacía -> eliminar devuelve false
        System.out.println("=== Caso borde: lista vacía ===");
        System.out.println("eliminar(10): " + lista.eliminar(10)); // false

        System.out.println("\n=== Cargando la lista ===");
        lista.insertarAlFinal(10);
        lista.insertarAlFinal(20);
        lista.insertarAlFinal(30);
        lista.insertarAlFinal(20); // repetido: eliminar solo la primera aparición
        lista.insertarAlFinal(40);

        System.out.print("Contenido: ");
        lista.imprimir(); // head -> [10] -> [20] -> [30] -> [20] -> [40] -> null
        System.out.println("Tamaño: " + lista.getSize()); // 5

        System.out.println("\n=== Eliminar nodo del medio ===");
        System.out.println("eliminar(30): " + lista.eliminar(30)); // true
        System.out.print("Contenido: ");
        lista.imprimir(); // head -> [10] -> [20] -> [20] -> [40] -> null

        System.out.println("\n=== Eliminar primera aparición de un repetido ===");
        System.out.println("eliminar(20): " + lista.eliminar(20)); // true (solo la primera)
        System.out.print("Contenido: ");
        lista.imprimir(); // head -> [10] -> [20] -> [40] -> null

        System.out.println("\n=== Eliminar el head ===");
        System.out.println("eliminar(10): " + lista.eliminar(10)); // true
        System.out.print("Contenido: ");
        lista.imprimir(); // head -> [20] -> [40] -> null

        System.out.println("\n=== Eliminar el último nodo ===");
        System.out.println("eliminar(40): " + lista.eliminar(40)); // true
        System.out.print("Contenido: ");
        lista.imprimir(); // head -> [20] -> null

        // Caso borde: dato inexistente -> false, la lista no cambia
        System.out.println("\n=== Caso borde: dato inexistente ===");
        System.out.println("eliminar(99): " + lista.eliminar(99)); // false
        System.out.print("Contenido: ");
        lista.imprimir(); // head -> [20] -> null (sin cambios)

        System.out.println("eliminar(20) (último restante): " + lista.eliminar(20)); // true
        System.out.println("¿La lista quedó vacía?: " + lista.estaVacia()); // true
    }
}

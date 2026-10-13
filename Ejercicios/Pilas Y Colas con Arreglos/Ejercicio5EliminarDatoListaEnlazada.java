/*
Prompt inicial utilizado:

"Agrega a la clase de Lista Enlazada Simple de enteros un método para eliminar la primera 
aparición de un elemento por su valor con la siguiente firma:
    boolean eliminar(int dato)

Explicación del problema, gestión de memoria en Java y casos a contemplar:

1. GESTIÓN DE MEMORIA EN JAVA (GARBAGE COLLECTOR):
   A diferencia de lenguajes como C/C++ donde la memoria asignada a un nodo debe ser liberada 
   manualmente (p. ej. mediante 'free()'), en Java no existe una instrucción explícita para borrar 
   un objeto de la memoria. La eliminación de un nodo consiste conceptualmente en DESCONECTARLO 
   de la secuencia de punteros (hacer que ningún nodo activo mantenga una referencia hacia él). 
   Una vez desvinculado e inaccesible desde 'head', el Recolector de Basura (Garbage Collector) 
   de Java detecta que el objeto es inalcanzable y libera de forma automática su memoria.

2. Casos a contemplar y actualización de enlaces:
   - Lista vacía ('head == null'): No hay nada que borrar; retorna 'false'.
   - Eliminar el primer nodo ('head.data == dato'):
     * Se actualiza la referencia principal: 'head = head.next'.
     * El nodo que solía ser 'head' queda desvinculado de la lista.
     * Decrementa 'size' y retorna 'true'.
   - Eliminar un nodo del medio o el último nodo:
     * Requiere un recorrido secuencial manteniendo un puntero 'actual' que rastree el NODO ANTERIOR 
       al que queremos eliminar.
     * Se avanza mientras 'actual.next != null' y 'actual.next.data != dato'.
     * Si se encuentra el dato en 'actual.next', se reconectan los punteros: 
            actual.setNext(actual.getNext().getNext());
       Esto "saltea" o puentea el nodo objetivo, dejándolo inaccesible.
     * Decrementa 'size' y retorna 'true'.
   - Dato inexistente: Si el recorrido llega al final ('actual.next == null') sin encontrar el dato, 
     la lista permanece intacta y se retorna 'false'.

Ajustes realizados luego de la primera respuesta de OpenCode:
- Se estructuró el ciclo de búsqueda de forma limpia utilizando 'actual.next' para evitar la necesidad 
  de manejar dos punteros separados ('anterior' y 'actual').
- Se garantizó la reducción del contador 'size' únicamente cuando la eliminación es exitosa.
 */

public class Ejercicio5EliminarDatoListaEnlazada {

    static class Node {

        private int data;
        private Node next;

        public Node(int data) {
            this.data = data;
            this.next = null;
        }

        public int getData() {
            return data;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    static class SimpleLinkedList {

        private Node head;
        private int size;

        public SimpleLinkedList() {
            this.head = null;
            this.size = 0;
        }

        public int getSize() {
            return this.size;
        }

        public void insertarAlFinal(int dato) {
            Node nuevo = new Node(dato);
            if (this.head == null) {
                this.head = nuevo;
            } else {
                Node actual = this.head;
                while (actual.getNext() != null) {
                    actual = actual.getNext();
                }
                actual.setNext(nuevo);
            }
            this.size++;
        }

        /**
         * Elimina la primera aparición de un valor en la lista.
         *
         * @param dato El valor entero a eliminar.
         * @return true si el dato fue encontrado y eliminado, false en caso
         * contrario.
         */
        public boolean eliminar(int dato) {
            // CASO 1: Lista vacía
            if (this.head == null) {
                return false;
            }

            // CASO 2: Eliminar el primer nodo (head)
            if (this.head.getData() == dato) {
                // Al mover head al siguiente nodo, el primer nodo queda inaccesible
                // para que el Garbage Collector libere su memoria.
                this.head = this.head.getNext();
                this.size--;
                return true;
            }

            // CASO 3 y 4: Eliminar un nodo del medio o el último nodo
            Node actual = this.head;

            // Recorrer buscando el nodo ANTERIOR al que deseamos eliminar
            while (actual.getNext() != null && actual.getNext().getData() != dato) {
                actual = actual.getNext();
            }

            // Si actual.getNext() no es null, encontramos el nodo objetivo
            if (actual.getNext() != null) {
                // RECONEXIÓN: Saltear el nodo actual.getNext()
                // El Garbage Collector liberará el nodo desvinculado
                actual.setNext(actual.getNext().getNext());
                this.size--;
                return true;
            }

            // CASO 5: Dato inexistente
            return false;
        }

        public void imprimir() {
            Node actual = this.head;
            System.out.print("head -> ");
            while (actual != null) {
                System.out.print("[" + actual.getData() + "]");
                if (actual.getNext() != null) {
                    System.out.print(" -> ");
                }
                actual = actual.getNext();
            }
            System.out.println(" -> null");
        }
    }

    public static void main(String[] args) {
        SimpleLinkedList lista = new SimpleLinkedList();

        lista.insertarAlFinal(10);
        lista.insertarAlFinal(20);
        lista.insertarAlFinal(30);
        lista.insertarAlFinal(40);

        System.out.print("Lista inicial: ");
        lista.imprimir(); // head -> [10] -> [20] -> [30] -> [40] -> null

        System.out.println("\n--- Pruebas de Eliminación ---");

        // 1. Eliminar primer nodo
        System.out.println("Eliminar 10 (primer nodo): " + lista.eliminar(10)); // true
        System.out.print("Estado: ");
        lista.imprimir(); // head -> [20] -> [30] -> [40] -> null

        // 2. Eliminar nodo del medio
        System.out.println("Eliminar 30 (nodo del medio): " + lista.eliminar(30)); // true
        System.out.print("Estado: ");
        lista.imprimir(); // head -> [20] -> [40] -> null

        // 3. Eliminar último nodo
        System.out.println("Eliminar 40 (último nodo): " + lista.eliminar(40)); // true
        System.out.print("Estado: ");
        lista.imprimir(); // head -> [20] -> null

        // 4. Dato inexistente
        System.out.println("Eliminar 99 (dato inexistente): " + lista.eliminar(99)); // false
        System.out.print("Estado final: ");
        lista.imprimir(); // head -> [20] -> null
    }
}

/* ...CORRECCIONES HECHAS POR OPENCODE
====================================================================================
CORRECCIONES HECHAS POR OPENCODE
====================================================================================
[REDUNDANTE] Los comentarios de 'eliminar()' vuelven a explicar el Garbage Collector y la desvinculación de nodos, tema que el Prompt inicial ya desarrolla extensamente arriba.
[REDUNDANTE] 'getSize()' está declarado pero no se invoca en ningún punto del archivo.
[PROMPT] El Prompt sólo pedía 'boolean eliminar(int)'; el 'main' con cuatro escenarios de eliminación y sus salidas esperadas no los solicita.
====================================================================================
*/

/*
Prompt inicial utilizado:

"Agrega a la implementación de la Lista Enlazada Simple de enteros un método de búsqueda 
con la siguiente signatura:
    boolean buscar(int dato)

Explicación del problema y fundamento conceptual:
A diferencia de un arreglo continuo (array), una Lista Enlazada Simple NO permite acceso 
aleatorio o directo por índice (O(1)). Los nodos están dispersos en memoria y están conectados 
únicamente mediante referencias ('next'). Por esta razón, para localizar un valor no se puede 
calcular una dirección de memoria directa; es estrictamente necesario realizar un recorrido 
secuencial comenzando desde 'head', inspeccionando nodo por nodo y avanzando mediante 'actual = actual.next' 
hasta hallar el elemento deseado o alcanzar 'null' (fin de la lista).

Requisitos y lógica de implementación:
1. Caso borde (Lista vacía): Si 'head == null', no hay elementos que comparar; devuelve 'false'.
2. Recorrido secuencial:
   - Inicializar un puntero de rastreo auxiliar: 'Node actual = head'.
   - Mientras 'actual != null':
     * Comparar si 'actual.data == dato'. Si coinciden, retornar 'true' inmediatamente (búsqueda exitosa).
     * Si no coinciden, avanzar al siguiente nodo: 'actual = actual.next'.
3. Si el ciclo finaliza porque 'actual' llegó a 'null' sin encontrar el valor, retornar 'false'.

Ajustes realizados luego de la primera respuesta de OpenCode:
- Se verificó que el recorrido no modifique la referencia 'head' original de la lista, utilizando 
  siempre una variable local auxiliar para la iteración.
 */

public class Ejercicio2BuscarListaEnlazada {

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

        public void insertarAlFinal(int dato) {
            Node nuevo = new Node(dato);
            if (head == null) {
                head = nuevo;
            } else {
                Node actual = head;
                while (actual.getNext() != null) {
                    actual = actual.getNext();
                }
                actual.setNext(nuevo);
            }
            size++;
        }

        /**
         * Busca un valor dentro de la lista enlazada mediante recorrido
         * secuencial. Complejidad temporal: O(N)
         *
         * @param dato El entero a buscar.
         * @return true si el dato existe en la lista, false en caso contrario.
         */
        public boolean buscar(int dato) {
            // Puntero auxiliar para no perder la referencia a head
            Node actual = this.head;

            // Recorrido secuencial hasta el final de la lista (null)
            while (actual != null) {
                if (actual.getData() == dato) {
                    return true; // Éxito: elemento encontrado
                }
                actual = actual.getNext(); // Avanzar al siguiente nodo secuencialmente
            }

            return false; // El elemento no se encuentra en la lista
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

        lista.insertarAlFinal(15);
        lista.insertarAlFinal(42);
        lista.insertarAlFinal(8);
        lista.insertarAlFinal(23);

        System.out.print("Estado de la lista: ");
        lista.imprimir(); // head -> [15] -> [42] -> [8] -> [23] -> null

        // Pruebas del método buscar
        System.out.println("\n--- Pruebas de Búsqueda ---");
        System.out.println("¿Existe el 8?: " + lista.buscar(8));    // true
        System.out.println("¿Existe el 15?: " + lista.buscar(15));  // true (elemento en head)
        System.out.println("¿Existe el 23?: " + lista.buscar(23));  // true (elemento al final)
        System.out.println("¿Existe el 99?: " + lista.buscar(99));  // false (elemento inexistente)
    }
}

/* ...CORRECCIONES HECHAS POR OPENCODE
====================================================================================
CORRECCIONES HECHAS POR OPENCODE
====================================================================================
[REDUNDANTE] El atributo 'size' se incrementa en 'insertarAlFinal()' pero nunca se lee en ningún punto: no existe 'getSize()' ni validación que lo utilice.
[PROMPT] Los 'Ajustes' del Prompt anuncian un método sobrecargado 'buscarIndice(int dato)', pero ese método no existe en el código: sólo está 'buscar(int dato)'.
[CORREGIDO] Ajuste falso eliminado del Prompt por OpenCode: ya no se anuncia 'buscarIndice', que nunca se implementó.
[PROMPT] El Prompt sólo pedía la firma 'boolean buscar(int)'; no pidió reimplementar toda la lista ni un 'main' con cuatro consultas y su salida esperada.
====================================================================================
*/

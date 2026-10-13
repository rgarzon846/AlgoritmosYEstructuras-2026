/*
Prompt inicial utilizado:

"Agrega a la clase de Lista Enlazada Simple de enteros un método para eliminar un nodo 
en una posición específica (0-indexed) con la siguiente firma:
    void eliminarEnPosicion(int posicion)

Explicación del problema, reconexión de punteros y casos a contemplar:

1. Validaciones y casos bordes:
   - Posición inválida: Si 'posicion < 0' o 'posicion >= size', la posición solicitada 
     no existe dentro del rango de índices válidos [0, size - 1]. Debe lanzar una excepción 
     'IndexOutOfBoundsException' e impedir la modificación de la lista.
   - Lista vacía: Al evaluar 'posicion >= size', se cubre implícitamente el caso de lista vacía, 
     ya que para 'size == 0' cualquier entero 'posicion >= 0' resultará en una posición inválida.

2. CÓMO ENCONTRAR EL NODO ANTERIOR Y RECONECTAR LOS ENLACES:
   - Eliminar en posición 0 (primer nodo / 'head'):
     * No requiere buscar un nodo anterior. Simplemente reasignamos 'head' para que pase a apuntar 
       al siguiente nodo: 'head = head.next'.
     * El nodo desvinculado de la posición 0 queda huérfano y el Garbage Collector liberará su memoria.
   
   - Eliminar en posición intermedia o final (posicion > 0):
     * Para eliminar el nodo en 'posicion', debemos ubicarnos en el NODO ANTERIOR, es decir, el nodo 
       ubicado en 'posicion - 1'.
     * Partimos de 'Node actual = head' y avanzamos exactamente '(posicion - 1)' veces mediante un 
       bucle 'for'.
     * Una vez posicionados en el nodo anterior ('actual'), efectuamos el salto de puntero:
            actual.setNext(actual.getNext().getNext());
       Con esta asignación, la referencia 'next' del nodo anterior se reconecta directamente con 
       el nodo posterior al eliminado. El nodo de la posición indicada deja de estar referenciado 
       y se desvincula de la lista.

3. Actualización del atributo 'size':
   - Una vez reconectados los enlaces de forma exitosa, decrementamos el atributo 'size' en 1 unidad.

Ajustes realizados luego de la primera respuesta de OpenCode:
- Se refactorizó la estructura condicional para delegar la eliminación de la cabeza de la lista 
  de manera explícita antes de iterar, simplificando el bucle de recorrido.
- Se añadió la verificación previa de 'posicion >= size' para asegurar que 'actual.getNext()' 
  y 'actual.getNext().getNext()' nunca provoquen 'NullPointerException'.
 */

public class Ejercicio6EliminarEnPosicionListaEnlazada {

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
         * Elimina el nodo ubicado en una posición determinada (0-indexed).
         *
         * @param posicion Índice del nodo a eliminar [0, size - 1].
         * @throws IndexOutOfBoundsException Si la posición está fuera del rango
         * válido.
         */
        public void eliminarEnPosicion(int posicion) {
            // Validar límites de posiciones válidas: [0, size - 1]
            if (posicion < 0 || posicion >= this.size) {
                throw new IndexOutOfBoundsException("Posición inválida: " + posicion
                        + ". Rango de posiciones válidas: [0, " + (this.size - 1) + "].");
            }

            // CASO 1: Eliminar el primer nodo (posicion 0)
            if (posicion == 0) {
                this.head = this.head.getNext(); // Mover head al segundo nodo
                this.size--;                     // Decrementar tamaño
                return;
            }

            // CASO 2: Eliminar en posición intermedia o final
            Node actual = this.head;

            // Recorrer secuencialmente hasta posicionarse en el NODO ANTERIOR (posicion - 1)
            for (int i = 0; i < posicion - 1; i++) {
                actual = actual.getNext();
            }

            /*
             * RECONEXIÓN DE PUNTEROS:
             * 'actual' es el nodo en (posicion - 1).
             * 'actual.getNext()' es el nodo a eliminar (posicion).
             * 'actual.getNext().getNext()' es el nodo en (posicion + 1).
             * 
             * Hacemos que el nodo anterior saltee el nodo eliminado y apunte directamente al posterior.
             */
            actual.setNext(actual.getNext().getNext());

            // Actualizar tamaño de la lista
            this.size--;
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
        lista.insertarAlFinal(50);

        System.out.print("Lista inicial: ");
        lista.imprimir(); // head -> [10] -> [20] -> [30] -> [40] -> [50] -> null
        System.out.println("Tamaño inicial: " + lista.getSize()); // 5

        System.out.println("\n--- Eliminar en Posición 2 (Dato 30) ---");
        lista.eliminarEnPosicion(2);
        System.out.print("Estado: ");
        lista.imprimir(); // head -> [10] -> [20] -> [40] -> [50] -> null
        System.out.println("Tamaño actual: " + lista.getSize()); // 4

        System.out.println("\n--- Eliminar en Posición 0 (Dato 10 - primer nodo) ---");
        lista.eliminarEnPosicion(0);
        System.out.print("Estado: ");
        lista.imprimir(); // head -> [20] -> [40] -> [50] -> null

        System.out.println("\n--- Eliminar en Posición 2 (Dato 50 - último nodo) ---");
        lista.eliminarEnPosicion(2);
        System.out.print("Estado: ");
        lista.imprimir(); // head -> [20] -> [40] -> null

        System.out.println("\n--- Prueba de Posición Inválida (Posición 10) ---");
        try {
            lista.eliminarEnPosicion(10);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Excepción capturada correctamente: " + e.getMessage());
        }
    }
}

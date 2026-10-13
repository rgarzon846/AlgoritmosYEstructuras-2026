/*
Prompt inicial utilizado:

"Agrega a la clase de Lista Enlazada Simple de enteros un método para insertar un nuevo valor 
en una posición arbitraria basada en índice (0-indexed) con la siguiente firma:
    void insertarEnPosicion(int dato, int posicion)

Explicación del problema, razonamiento de punteros y orden crítico de reconexión:
Para insertar un nuevo nodo en una posición determinada de la lista, es fundamental entender 
cómo se actualizan las referencias ('next') para no perder el resto de la estructura en memoria.

1. Casos a contemplar y validaciones:
   - Posición inválida: Si 'posicion < 0' o 'posicion > size', la posición no existe. Debe lanzar 
     una excepción 'IndexOutOfBoundsException'. (Nota: 'posicion == size' ES VÁLIDA, equivale a 
     insertar al final).
   - Inserción al inicio (posicion == 0): Se delega a 'insertarAlInicio(dato)' o se actualiza 'head'.
   - Inserción en el medio / al final (posicion > 0): Se recorre secuencialmente la lista hasta 
     posicionarse en el nodo anterior al punto de inserción ('actual' estará en 'posicion - 1').

2. ORDEN CRÍTICO DE ACTUALIZACIÓN DE REFERENCIAS:
   Dado un nuevo nodo 'nuevo' y el nodo 'actual' (en 'posicion - 1'):
   
   Paso A: nuevo.setSiguiente(actual.getSiguiente());
   Paso B: actual.setSiguiente(nuevo);

   ¿QUÉ PASARÍA SI SE HACE EN EL ORDEN INCORRECTO?
   Si se ejecutara primero el Paso B ('actual.setSiguiente(nuevo)'), la referencia 'actual.next' 
   dejaría de apuntar al resto de los nodos de la lista para apuntar al nuevo nodo. En ese preciso 
   instante, SE PIERDE LA ÚNICA REFERENCIA AL RESTO DE LA LISTA. Como resultado, todos los nodos 
   subsiguientes quedan inaccesibles y serán eliminados de la memoria por el Recolector de Basura 
   (Garbage Collector) de Java, rompiendo la estructura por completo.

3. Actualización de tamaño:
   Al finalizar la inserción, se incrementa el atributo 'size' en 1.

Ajustes realizados luego de la primera respuesta de OpenCode:
- Se manejaron adecuadamente las delegaciones a métodos existentes ('insertarAlInicio' e 
  'insertarAlFinal') para reutilizar código y reducir la complejidad sintáctica.
- Se añadió un gráfico explicativo en los comentarios del método sobre el enlace temporal de nodos.
 */

public class Ejercicio4InsertarEnPosicion {

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

        public void insertarAlInicio(int dato) {
            Node nuevo = new Node(dato);
            nuevo.setNext(this.head);
            this.head = nuevo;
            this.size++;
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
         * Inserta un dato en una posición arbitraria (0-indexed).
         *
         * @param dato El valor entero a insertar.
         * @param posicion La posición deseada [0, size].
         * @throws IndexOutOfBoundsException Si la posición está fuera de rango.
         */
        public void insertarEnPosicion(int dato, int posicion) {
            // Validar límites de posición permitida: [0, size]
            if (posicion < 0 || posicion > this.size) {
                throw new IndexOutOfBoundsException("Posición inválida: " + posicion
                        + ". El rango válido de inserción es [0, " + this.size + "].");
            }

            // Caso 1: Inserción al inicio
            if (posicion == 0) {
                insertarAlInicio(dato);
                return;
            }

            // Caso 2: Inserción al final
            if (posicion == this.size) {
                insertarAlFinal(dato);
                return;
            }

            // Caso 3: Inserción en el medio
            Node nuevo = new Node(dato);
            Node actual = this.head;

            // Avanzar hasta el nodo en la posición (posicion - 1)
            for (int i = 0; i < posicion - 1; i++) {
                actual = actual.getNext();
            }

            /* 
             * ORDEN CORRECTO DE ENLACE:
             * 1. El nuevo nodo apunta al siguiente del nodo actual.
             * 2. El nodo actual se reconecta para apuntar al nuevo nodo.
             */
            nuevo.setNext(actual.getNext()); // Paso 1: Enlazar nuevo con el resto de la lista
            actual.setNext(nuevo);           // Paso 2: Enlazar anterior con el nuevo nodo

            this.size++; // Incrementar tamaño
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

        System.out.println("--- Inserciones iniciales ---");
        lista.insertarEnPosicion(10, 0); // Inicio en lista vacía: [10]
        lista.insertarEnPosicion(30, 1); // Al final: [10, 30]
        lista.insertarEnPosicion(5, 0);  // Al inicio: [5, 10, 30]

        System.out.print("Lista base: ");
        lista.imprimir(); // head -> [5] -> [10] -> [30] -> null

        System.out.println("\n--- Inserción en el medio (Posición 2, Dato 20) ---");
        lista.insertarEnPosicion(20, 2);
        System.out.print("Resultado:  ");
        lista.imprimir(); // head -> [5] -> [10] -> [20] -> [30] -> null

        System.out.println("\n--- Prueba de Posición Inválida (Posición 10) ---");
        try {
            lista.insertarEnPosicion(99, 10);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Excepción capturada correctamente: " + e.getMessage());
        }
    }
}

/* ...CORRECCIONES HECHAS POR OPENCODE
====================================================================================
CORRECCIONES HECHAS POR OPENCODE
====================================================================================
[REDUNDANTE] 'getSize()' se define pero nunca se usa en el archivo.
[PROMPT] El Prompt da por entendido que 'insertarAlInicio', 'insertarAlFinal' e 'imprimir' ya existen de ejercicios anteriores, pero el archivo los reimplementa completos por ser independiente.
[PROMPT] El Prompt sólo pedía 'void insertarEnPosicion(int, int)'; el 'main' con sus tres escenarios y la salida alineada son extras no solicitados.
====================================================================================
*/

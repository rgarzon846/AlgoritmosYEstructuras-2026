/*
Prompt inicial utilizado:

"Implementa en Java una Lista Enlazada Simple de números enteros desde cero, sin utilizar 
ninguna clase o estructura de colecciones de Java (como LinkedList, ArrayList, etc.).

Estructura de clases requerida:
1. Clase 'Node':
   - Debe representar cada elemento individual de la lista.
   - Contiene dos atributos: un valor entero 'data' (el contenido) y una referencia 
     'next' de tipo Node (puntero hacia el siguiente nodo de la secuencia).
   - Un constructor que reciba el entero 'data' e inicialice 'next' en null.

2. Clase 'SimpleLinkedList':
   - Atributos: Una referencia 'head' de tipo Node (apunta al primer nodo de la lista) 
     y un entero 'size' que mantiene la cuenta del total de elementos.
   - La referencia 'head' es el punto de entrada a la estructura; si 'head' es null, 
     significa que la lista está vacía.

Métodos a implementar y su lógica explícita:
- estaVacia(): Devuelve 'true' si 'head == null' (o si 'size == 0'), 'false' en caso contrario.
- getSize(): Devuelve el valor del atributo 'size'.
- insertarAlInicio(int dato): 
    * Crea un nuevo nodo 'nuevoNode'.
    * Hace que 'nuevoNode.next' apunte al 'head' actual.
    * Actualiza 'head' para que ahora sea 'nuevoNode'.
    * Incrementa 'size' en 1.
- insertarAlFinal(int dato):
    * Crea un nuevo nodo 'nuevoNode'.
    * Caso borde (Lista vacía): Si estaVacia() es true, asigna 'head = nuevoNode'.
    * Caso general: Recorre la lista desde 'head' usando un puntero auxiliar 'actual' 
      hasta llegar al último nodo (donde 'actual.next == null'). Luego reconecta 
      'actual.next = nuevoNode'.
    * Incrementa 'size' en 1.
- imprimir():
    * Si la lista está vacía, muestra un mensaje indicándolo.
    * Si no, recorre secuencialmente usando un nodo auxiliar 'actual = head' mientras 
      'actual != null', imprimiendo 'actual.data' y avanzando 'actual = actual.next'.

Ajustes realizados luego de la primera respuesta de OpenCode:
- Se encapsularon los atributos de las clases 'Node' y 'SimpleLinkedList' para seguir buenas prácticas de POO.
- En el método 'imprimir()', se añadió un formato visual tipo 'head -> [1] -> [2] -> null' para verificar visualmente la dirección de las referencias.
- Se aseguró la validación estricta del contador 'size' en cada operación de inserción para garantizar complejidad O(1) al consultar el tamaño de la lista.
 */

public class Ejercicio1ListaEnlazada {

    // 1. Clase que representa cada Nodo de la lista
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

        public void setData(int data) {
            this.data = data;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    // 2. Clase que gestiona la Lista Enlazada Simple
    static class SimpleLinkedList {

        private Node head;
        private int size;

        public SimpleLinkedList() {
            this.head = null;
            this.size = 0;
        }

        // Verifica si la lista no contiene elementos
        public boolean estaVacia() {
            return this.head == null;
        }

        // Devuelve la cantidad de nodos de la lista
        public int getSize() {
            return this.size;
        }

        // Inserta un nuevo elemento al principio de la lista: O(1)
        public void insertarAlInicio(int dato) {
            Node nuevo = new Node(dato);
            nuevo.setNext(this.head); // El nuevo nodo apunta al que era el primer nodo
            this.head = nuevo;         // head pasa a apuntar al nuevo nodo
            this.size++;               // Actualización del tamaño
        }

        // Inserta un nuevo elemento al final de la lista: O(N)
        public void insertarAlFinal(int dato) {
            Node nuevo = new Node(dato);

            if (estaVacia()) {
                this.head = nuevo;
            } else {
                // Recorrido secuencial hasta hallar el último nodo (aquel cuyo next es null)
                Node actual = this.head;
                while (actual.getNext() != null) {
                    actual = actual.getNext();
                }
                actual.setNext(nuevo); // Enlace del último nodo al nuevo nodo
            }
            this.size++; // Actualización del tamaño
        }

        // Imprime el contenido de la lista manteniendo el orden de las referencias
        public void imprimir() {
            if (estaVacia()) {
                System.out.println("La lista está vacía.");
                return;
            }

            Node actual = this.head;
            System.out.print("head -> ");
            while (actual != null) {
                System.out.print("[" + actual.getData() + "]");
                if (actual.getNext() != null) {
                    System.out.print(" -> ");
                }
                actual = actual.getNext(); // Avanzar al siguiente nodo
            }
            System.out.println(" -> null");
        }
    }

    // Método main para ejecutar las pruebas
    public static void main(String[] args) {
        SimpleLinkedList lista = new SimpleLinkedList();

        System.out.println("¿La lista está vacía?: " + lista.estaVacia()); // true
        System.out.println("Tamaño inicial: " + lista.getSize());        // 0

        System.out.println("\n--- Insertando elementos ---");
        lista.insertarAlFinal(10);
        lista.insertarAlFinal(20);
        lista.insertarAlInicio(5);
        lista.insertarAlFinal(30);
        lista.insertarAlInicio(1);

        System.out.print("Estado de la lista: ");
        lista.imprimir(); // head -> [1] -> [5] -> [10] -> [20] -> [30] -> null

        System.out.println("¿La lista está vacía?: " + lista.estaVacia()); // false
        System.out.println("Tamaño actual: " + lista.getSize());          // 5
    }
}

/* ...CORRECCIONES HECHAS POR OPENCODE
====================================================================================
CORRECCIONES HECHAS POR OPENCODE
====================================================================================
[REDUNDANTE] El setter 'Node.setData()' está declarado pero nunca se invoca en todo el archivo: la lista sólo agrega nodos nuevos, nunca muta el dato de un nodo existente.
[REDUNDANTE] Inicializar 'this.next = null' en el constructor de 'Node' sobra, porque Java ya asigna null por defecto a todo atributo de referencia.
[PROMPT] El Prompt describía la estructura y tres métodos, pero no pedía un 'main' con escenario de pruebas ni los comentarios '// true', '// 5' de salida esperada que el código agrega.
====================================================================================
*/

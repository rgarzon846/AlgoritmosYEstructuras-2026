/*
Prompt inicial utilizado:

"Agrega a la clase de Lista Enlazada Simple de enteros un método para acceder a un elemento 
por su posición basada en índice (0-indexed) con la siguiente firma:
    int obtener(int posicion)

Explicación del problema y razonamiento técnico:
A diferencia de un arreglo donde la posición se calcula mediante aritmética de punteros en 
tiempo constante (O(1)), las listas enlazadas requieren un recorrido secuencial desde el nodo 'head' 
avanzando un número 'posicion' de veces (O(N)). Aunque la firma del método simule el acceso indexado 
de un array (ej. lista.obtener(3)), internamente no existe un acceso directo a la memoria.

Requisitos de validación y casos a contemplar:
1. Validar que la posición esté dentro de los límites válidos [0, size - 1].
2. Si la posición es inválida (posicion < 0 o posicion >= size), se debe lanzar una excepción 
   'IndexOutOfBoundsException' con un mensaje descriptivo para evitar recorridos innecesarios o 
   errores de 'NullPointerException'.
3. Recorrido secuencial:
   - Inicializar 'Node actual = head' y un contador 'int contador = 0'.
   - Avanzar secuencialmente en un ciclo mientras 'contador < posicion', haciendo 'actual = actual.next' 
     y 'contador++'.
   - Al finalizar el ciclo, 'actual' apuntará exactamente al nodo en la posición requerida, por lo que 
     se retorna 'actual.data'.

Ajustes realizados luego de la primera respuesta de OpenCode:
- Se utilizó la propiedad 'size' almacenada en la clase para realizar la validación del límite superior 
  en tiempo O(1) antes de iniciar el recorrido secuencial.
- Se mantuvo el aislamiento de referencias mediante el uso de la variable auxiliar local 'actual' para no 
  alterar el puntero 'head' original.
 */

public class Ejercicio3ObtenerListaEnlazada {

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

        public int getSize() {
            return this.size;
        }

        /**
         * Obtiene el valor almacenado en una posición específica (0-indexed).
         * Complejidad temporal: O(N) debido al recorrido secuencial.
         *
         * @param posicion Índice del elemento a obtener.
         * @return El valor entero en la posición solicitada.
         * @throws IndexOutOfBoundsException Si la posición es < 0 o >= size.
         */
        public int obtener(int posicion) {
            // Validaciones de posiciones inválidas
            if (posicion < 0 || posicion >= this.size) {
                throw new IndexOutOfBoundsException("Posición inválida: " + posicion
                        + ". El tamaño actual de la lista es: " + this.size);
            }

            // Recorrido secuencial nodo por nodo hasta la posición requerida
            Node actual = this.head;
            for (int i = 0; i < posicion; i++) {
                actual = actual.getNext();
            }

            // Retorna el dato almacenado en el nodo alcanzado
            return actual.getData();
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

        System.out.print("Estado de la lista: ");
        lista.imprimir(); // head -> [10] -> [20] -> [30] -> [40] -> null

        System.out.println("\n--- Pruebas de Obtención ---");
        System.out.println("Elemento en posición 0 (head): " + lista.obtener(0)); // 10
        System.out.println("Elemento en posición 2: " + lista.obtener(2));        // 30
        System.out.println("Elemento en posición 3 (último): " + lista.obtener(3)); // 40

        System.out.println("\n--- Prueba de Posición Inválida ---");
        try {
            lista.obtener(5); // Posición fuera de rango
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Excepción capturada correctamente: " + e.getMessage());
        }
    }
}

/* ...CORRECCIONES HECHAS POR OPENCODE
====================================================================================
CORRECCIONES HECHAS POR OPENCODE
====================================================================================
[REDUNDANTE] 'getSize()' está definido pero no se invoca en ningún lugar del archivo, y el Prompt de este ejercicio no lo pedía.
[PROMPT] El Prompt sólo pedía 'int obtener(int posicion)'; el archivo agrega además un 'main' con captura de la excepción y mensajes de consola que la consigna no especifica.
====================================================================================
*/

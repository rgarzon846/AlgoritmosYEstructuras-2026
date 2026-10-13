/*
Prompt inicial utilizado:

"Agrega a la clase de Lista Enlazada Simple de enteros un método para modificar el valor 
almacenado en un nodo en una posición determinada (0-indexed) con la siguiente firma:
    void modificar(int posicion, int nuevoDato)

Explicación del problema, validación previa y diferencia conceptual entre dato y referencia:

1. DIFERENCIA CLAVE ENTRE MODIFICAR EL DATO Y MODIFICAR LA REFERENCIA:
   - Modificar el DATO ('nodo.setData(nuevoDato)'): Altera únicamente el valor informativo 
     contenido dentro de la celda de memoria del nodo existente. NO afecta la estructura, 
     el orden ni la continuidad de la lista, ya que la referencia 'next' permanece intacta.
   - Modificar la REFERENCIA ('nodo.setNext(otroNodo)'): Redirecciona el puntero del nodo, 
     alterando la topología de la lista (cambia la secuencia de la cadena, inserta, elimina 
     o puede romper la lista si se pierde el enlace).
   - Para este ejercicio sólo se requiere mutar el estado interno del dato almacenado, 
     manteniendo la estructura de enlaces inalterada.

2. VALIDACIÓN PREVIA AL RECORRIDO:
   Antes de iniciar el recorrido secuencial, se debe validar que la posición esté dentro 
   del rango [0, size - 1]. Validar la posición antes de recorrer evita ejecutar iteraciones 
   innecesarias y garantiza que la función falle de manera inmediata (fail-fast) mediante 
   una excepción 'IndexOutOfBoundsException' si la posición es inválida.

3. RECORRIDO SECUENCIAL Y ACTUALIZACIÓN:
   - Partir de 'Node actual = head'.
   - Avanzar secuencialmente 'posicion' veces mediante un bucle ('actual = actual.next').
   - Una vez en la posición deseada, invocar 'actual.setData(nuevoDato)'.
   - El atributo 'size' NO se modifica, pues no se agregan ni quitan nodos.

Ajustes realizados luego de la primera respuesta de OpenCode:
- Se aisló la validación inicial utilizando el atributo 'size' para evitar recorrer la lista cuando 
  la posición está fuera de límites.
- Se mantuvo el acceso encapsulado al dato a través de los getters y setters de la clase 'Node'.
 */

public class Ejercicio7ModificarListaEnlazada {

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
         * Modifica el dato contenido en el nodo ubicado en una posición
         * específica (0-indexed).
         *
         * @param posicion Índice del nodo a modificar [0, size - 1].
         * @param nuevoDato Nuevo valor entero a asignar.
         * @throws IndexOutOfBoundsException Si la posición está fuera del rango
         * válido.
         */
        public void modificar(int posicion, int nuevoDato) {
            // VALIDACIÓN PREVIA: Comprobar límites antes de iniciar cualquier recorrido
            if (posicion < 0 || posicion >= this.size) {
                throw new IndexOutOfBoundsException("Posición inválida: " + posicion
                        + ". Rango de posiciones válidas: [0, " + (this.size - 1) + "].");
            }

            // RECORRIDO SECUENCIAL hasta alcanzar el nodo en la posición deseada
            Node actual = this.head;
            for (int i = 0; i < posicion; i++) {
                actual = actual.getNext();
            }

            // MODIFICACIÓN DEL DATO: Se altera el contenido, NO la referencia 'next'
            actual.setData(nuevoDato);
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

        System.out.print("Lista original:  ");
        lista.imprimir(); // head -> [10] -> [20] -> [30] -> [40] -> null

        System.out.println("\n--- Modificar Posición 0 (Cambiar 10 por 99) ---");
        lista.modificar(0, 99);
        System.out.print("Estado:          ");
        lista.imprimir(); // head -> [99] -> [20] -> [30] -> [40] -> null

        System.out.println("\n--- Modificar Posición 2 (Cambiar 30 por 77) ---");
        lista.modificar(2, 77);
        System.out.print("Estado:          ");
        lista.imprimir(); // head -> [99] -> [20] -> [77] -> [40] -> null

        System.out.println("\n--- Modificar Posición 3 (Cambiar 40 por 500) ---");
        lista.modificar(3, 500);
        System.out.print("Estado final:    ");
        lista.imprimir(); // head -> [99] -> [20] -> [77] -> [500] -> null

        System.out.println("\n--- Prueba de Posición Inválida (Posición -1 y 10) ---");
        try {
            lista.modificar(10, 888);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Excepción capturada correctamente: " + e.getMessage());
        }
    }
}

/*
Prompt inicial utilizado:

"Agrega a la clase de Lista Enlazada Simple de enteros un método para contar cuántas veces 
aparece un determinado valor en la lista con la siguiente firma:
    int contarOcurrencias(int dato)

Explicación del problema, lógica de conteo y recorrido completo:

1. POR QUÉ NO SE DEBE CORTAR EL RECORRIDO AL ENCONTRAR LA PRIMERA COINCIDENCIA:
   A diferencia de un método de búsqueda (como 'buscar' o 'contener') o de eliminación única, 
   donde el objetivo se cumple apenas se halla el primer elemento equivalente, el método 
   'contarOcurrencias' requiere calcular la FRECUENCIA ABSOLUTA de un valor en toda la estructura.
   Si interrumpiéramos el bucle al encontrar el primer nodo coincidente (mediante un 'return' 
   o 'break'), la función omitiría las eventuales repeticiones posteriores en la lista, devolviendo 
   un número incorrecto (siempre 1 o 0). Por lo tanto, es OBLIGATORIO efectuar un recorrido completo 
   (O(n)) desde 'head' hasta 'null'.

2. LÓGICA DE RECORRIDO Y CONTRADOR:
   - Se inicializa una variable acumuladora 'contador = 0'.
   - Se emplea un nodo auxiliar 'Node actual = head' para desplazarse.
   - Mientras 'actual != null':
       * Si 'actual.getData() == dato', se incrementa 'contador++'.
       * Se avanza al siguiente nodo con 'actual = actual.getNext()'.
   - Retornar 'contador' al finalizar la iteración completa.

3. CASOS BORDE:
   - Lista vacía ('head == null'): El bucle 'while' no se ejecuta y retorna '0' correctamente.
   - Dato no presente en la lista: Se recorren todos los nodos y retorna '0'.

Ajustes realizados luego de la primera respuesta de OpenCode:
- Se aseguró de mantener la firma exacta especificada en el requerimiento.
- Se agregaron casos de prueba representativos en el método 'main' que validan listas con múltiples 
  duplicados, un solo elemento y datos ausentes.
 */

public class Ejercicio8ContarOcurrenciasListaEnlazada {

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
         * Cuenta cuántas veces se repite un determinado valor a lo largo de
         * toda la lista.
         *
         * @param dato El valor entero a buscar.
         * @return La cantidad total de apariciones del dato en la lista.
         */
        public int contarOcurrencias(int dato) {
            int contador = 0;
            Node actual = this.head;

            // Recorrido COMPLETO hasta el final de la lista (null)
            while (actual != null) {
                if (actual.getData() == dato) {
                    contador++; // No cortamos con return/break: debemos contar todas las apariciones
                }
                actual = actual.getNext(); // Avanzar siempre al siguiente nodo
            }

            return contador;
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

        // Construir la lista de ejemplo: 10 -> 20 -> 10 -> 30 -> 10 -> null
        lista.insertarAlFinal(10);
        lista.insertarAlFinal(20);
        lista.insertarAlFinal(10);
        lista.insertarAlFinal(30);
        lista.insertarAlFinal(10);

        System.out.print("Lista de prueba: ");
        lista.imprimir();

        System.out.println("\n--- Pruebas de Contar Ocurrencias ---");

        // Ejemplo pedido en la consigna: contar el valor 10
        int ocurrencias10 = lista.contarOcurrencias(10);
        System.out.println("Ocurrencias del número 10: " + ocurrencias10); // Devuelve 3

        // Contar un número que aparece una sola vez
        int ocurrencias20 = lista.contarOcurrencias(20);
        System.out.println("Ocurrencias del número 20: " + ocurrencias20); // Devuelve 1

        // Contar un número que no existe en la lista
        int ocurrencias99 = lista.contarOcurrencias(99);
        System.out.println("Ocurrencias del número 99: " + ocurrencias99); // Devuelve 0

        // Prueba en lista vacía
        SimpleLinkedList listaVacia = new SimpleLinkedList();
        System.out.println("Ocurrencias de 10 en lista vacía: " + listaVacia.contarOcurrencias(10)); // Devuelve 0
    }
}

/* ...CORRECCIONES HECHAS POR OPENCODE
====================================================================================
CORRECCIONES HECHAS POR OPENCODE
====================================================================================
[PROMPT] Los 'Ajustes' del Prompt prometen casos con 'un solo elemento', pero el 'main' sólo prueba duplicados, ausentes y lista vacía: falta ese escenario.
[REDUNDANTE] 'getSize()' se define pero no se utiliza en ningún punto del archivo.
====================================================================================
*/

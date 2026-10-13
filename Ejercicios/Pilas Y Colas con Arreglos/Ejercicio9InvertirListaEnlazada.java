/*
Prompt inicial utilizado:

"Agrega a la clase de Lista Enlazada Simple de enteros un método para invertir el orden 
de los nodos in-place (in situ) con la siguiente firma:
    void invertir()

Explicación del problema, referencias auxiliares requeridas y orden crítico de actualización:

1. REFERENCIAS AUXILIARES NECESARIAS:
   Para invertir una lista enlazada simple sin crear una lista nueva ni duplicar memoria (O(1) memoria auxiliar), 
   se requieren exactamente TRES punteros o referencias auxiliares durante la iteración:
   
   - 'anterior' (Node): Almacena la referencia al nodo previo (inicialmente 'null'). Se convertirá en el 
     nuevo enlace 'next' del nodo actual y, al finalizar el bucle, será la nueva cabeza ('head') de la lista.
   - 'actual' (Node): Apunta al nodo que se está procesando en el paso corriente (inicialmente 'head').
   - 'siguiente' (Node): Guarda temporalmente la referencia al siguiente nodo ('actual.getNext()') ANTES 
     de sobreescribir la referencia 'next' del nodo actual, evitando perder el resto de la lista.

2. ORDEN CRÍTICO DE ACTUALIZACIÓN DE REFERENCIAS (DENTRO DEL BUCLE):
   En cada paso de la iteración mientras 'actual != null':

   Paso 1: siguiente = actual.getNext(); 
           SE DEBE GUARDAR EL SIGUIENTE PRIMERO. De lo contrario, al reorientar 'actual.next', perderíamos 
           el acceso al resto de la lista en memoria.
   Paso 2: actual.setNext(anterior);     
           REORIENTACIÓN DEL ENLACE. Se invierte la dirección del puntero, haciendo que el nodo actual 
           apunte al nodo anterior en lugar del siguiente.
   Paso 3: anterior = actual;            
           DESPLAZAMIENTO. El nodo actual pasa a ser el 'anterior' para la siguiente iteración.
   Paso 4: actual = siguiente;           
           AVANCE. 'actual' avanza al nodo guardado en 'siguiente' para continuar el proceso.

   ¿POR QUÉ EL ORDEN ES FUNDAMENTAL?
   Si invertimos el Paso 1 y el Paso 2 (es decir, hacemos 'actual.setNext(anterior)' antes de guardar 
   'actual.getNext()'), romperíamos el enlace con el subgrupo subsiguiente de la lista. Toda la secuencia 
   de nodos no procesados se perdería instantáneamente y no habría forma de continuar el bucle.

3. FINALIZACIÓN:
   Al concluir el bucle ('actual == null'), la referencia 'anterior' estará apuntando al último nodo de la 
   lista original. Finalmente, se actualiza la cabeza de la lista: 'head = anterior'.

Ajustes realizados luego de la primera respuesta de OpenCode:
- Se contemplaron adecuadamente los casos bordes de lista vacía ('head == null') y lista con un solo 
  nodo ('head.getNext() == null'), los cuales retornan inmediatamente sin alterar la estructura.
 */

public class Ejercicio9InvertirListaEnlazada {

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
         * Invierte el orden de los nodos de la lista enlazada in-place (O(1)
         * memoria auxiliar).
         */
        public void invertir() {
            // Caso borde: Si la lista está vacía o tiene un único nodo, no hace falta invertir
            if (this.head == null || this.head.getNext() == null) {
                return;
            }

            Node anterior = null;
            Node actual = this.head;
            Node siguiente = null;

            // Recorrido e inversión de punteros
            while (actual != null) {
                // 1. Guardar la referencia al siguiente nodo antes de romper el enlace
                siguiente = actual.getNext();

                // 2. Invertir la referencia del nodo actual hacia el nodo anterior
                actual.setNext(anterior);

                // 3. Mover el puntero 'anterior' un paso adelante (al nodo actual)
                anterior = actual;

                // 4. Mover el puntero 'actual' un paso adelante (al nodo siguiente guardado)
                actual = siguiente;
            }

            // Al finalizar, 'anterior' apunta al último nodo original, que ahora es la nueva cabeza
            this.head = anterior;
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

        // Construir la lista del ejemplo: 10 -> 20 -> 30 -> 40 -> null
        lista.insertarAlFinal(10);
        lista.insertarAlFinal(20);
        lista.insertarAlFinal(30);
        lista.insertarAlFinal(40);

        System.out.print("Lista original:  ");
        lista.imprimir(); // head -> [10] -> [20] -> [30] -> [40] -> null

        System.out.println("\n--- Invertir Lista ---");
        lista.invertir();

        System.out.print("Lista invertida: ");
        lista.imprimir(); // head -> [40] -> [30] -> [20] -> [10] -> null

        System.out.println("\n--- Invertir nuevamente ---");
        lista.invertir();

        System.out.print("Estado final:    ");
        lista.imprimir(); // head -> [10] -> [20] -> [30] -> [40] -> null
    }
}

/* ...CORRECCIONES HECHAS POR OPENCODE
====================================================================================
CORRECCIONES HECHAS POR OPENCODE
====================================================================================
[REDUNDANTE] El guard temprano 'head.getNext() == null' es prescindible: el bucle ya deja intacta una lista de cero o un nodo sin necesidad del return.
[REDUNDANTE] 'getSize()' está declarado pero no se invoca en ningún punto del archivo.
[PROMPT] El Prompt sólo pedía 'void invertir()'; el 'main' que invierte dos veces para mostrar el regreso al orden original no estaba solicitado.
====================================================================================
*/

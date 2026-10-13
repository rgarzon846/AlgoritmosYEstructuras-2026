/*
Prompt inicial utilizado:
Implementá en Java un método eliminarEnPosicion(int posicion) para una lista enlazada simple de
enteros, sin colecciones de la JDK ni Scanner/System.in.
La lista se arma con nodos que guardan un dato y una referencia "siguiente"; un head apunta al
primer nodo y un tamaño se actualiza en cada inserción y eliminación.
Para eliminar un nodo hay que llegar al nodo ANTERIOR al que se quiere quitar (recorriendo desde
head contando posiciones) y hacer que su referencia "siguiente" apunte al siguiente del
eliminado: así el nodo queda desconectado de la cadena y el garbage collector lo libera.
Validar posiciones inválidas (posicion < 0 o posicion >= size -> IllegalArgumentException) y
contemplar el caso borde posicion == 0, donde el eliminado es el head y hay que mover la
referencia head al siguiente nodo. Indicar complejidades. Ajustes posteriores: ninguno.
*/

/**
 * Ejercicio 6: Eliminar un nodo por posición en una lista enlazada simple.
 *
 * <p>La eliminación por posición recorre la lista hasta el nodo anterior al que se elimina y
 * hace que su enlace "salte" al siguiente del eliminado; si la posición es 0 se mueve el head.</p>
 */
public class Ejercicio6 {

    // ---------------------------------------------------------------------
    // Nodo de la lista enlazada simple (guarda enteros)
    // ---------------------------------------------------------------------
    static class Nodo {

        private int dato;
        private Nodo siguiente;

        /**
         * Crea un nodo con el dato indicado y sin enlace.
         *
         * @param dato entero a almacenar en el nodo
         */
        public Nodo(int dato) {
            this.dato = dato;
            this.siguiente = null;
        }

        public int getDato() {
            return dato;
        }

        public void setDato(int dato) {
            this.dato = dato;
        }

        public Nodo getSiguiente() {
            return siguiente;
        }

        public void setSiguiente(Nodo siguiente) {
            this.siguiente = siguiente;
        }
    }

    // ---------------------------------------------------------------------
    // Lista enlazada simple de enteros (head + tamaño)
    // ---------------------------------------------------------------------
    static class ListaEnlazada {

        private Nodo head; // primer nodo; null si la lista está vacía
        private int tamaño;

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
         * Inserta un elemento al inicio de la lista.
         *
         * @param dato entero a insertar
         * Complejidad: O(1)
         */
        public void insertarAlInicio(int dato) {
            Nodo nuevo = new Nodo(dato);
            nuevo.setSiguiente(this.head);
            this.head = nuevo;
            this.tamaño++;
        }

        /**
         * Inserta un elemento al final de la lista.
         *
         * @param dato entero a insertar
         * Complejidad: O(n)
         */
        public void insertarAlFinal(int dato) {
            Nodo nuevo = new Nodo(dato);
            if (estaVacia()) {
                this.head = nuevo;
            } else {
                Nodo actual = this.head;
                while (actual.getSiguiente() != null) {
                    actual = actual.getSiguiente();
                }
                actual.setSiguiente(nuevo);
            }
            this.tamaño++;
        }

        /**
         * Busca un dato recorriendo la lista desde head hasta encontrarlo o llegar a null.
         *
         * @param dato entero a buscar
         * @return {@code true} si el dato está en la lista
         * Complejidad: O(n)
         */
        public boolean buscar(int dato) {
            Nodo actual = this.head;
            while (actual != null && actual.getDato() != dato) {
                actual = actual.getSiguiente();
            }
            return actual != null;
        }

        /**
         * Obtiene el dato almacenado en una posición determinada (0 = head).
         *
         * @param posicion índice del elemento a devolver
         * @return dato almacenado en esa posición
         * @throws IllegalArgumentException si la posición es inválida (caso borde)
         * Complejidad: O(n)
         */
        public int obtener(int posicion) {
            if (posicion < 0 || posicion >= this.tamaño) {
                throw new IllegalArgumentException(
                        "Posición inválida: " + posicion + " (tamaño = " + this.tamaño + ").");
            }
            Nodo actual = this.head;
            for (int i = 0; i < posicion; i++) {
                actual = actual.getSiguiente();
            }
            return actual.getDato();
        }

        /**
         * Inserta un nodo en una posición específica (inicio, medio o final).
         *
         * @param dato entero a insertar
         * @param posicion índice en el que quedará el nuevo nodo (0 a size inclusive)
         * @throws IllegalArgumentException si la posición es inválida (caso borde)
         * Complejidad: O(n)
         */
        public void insertarEnPosicion(int dato, int posicion) {
            if (posicion < 0 || posicion > this.tamaño) {
                throw new IllegalArgumentException(
                        "Posición inválida: " + posicion + " (tamaño = " + this.tamaño + ").");
            }
            if (posicion == 0) {
                insertarAlInicio(dato);
                return;
            }
            if (posicion == this.tamaño) {
                insertarAlFinal(dato);
                return;
            }
            Nodo nuevo = new Nodo(dato);
            Nodo anterior = this.head;
            for (int i = 0; i < posicion - 1; i++) {
                anterior = anterior.getSiguiente();
            }
            // Primero el nuevo apunta al siguiente, después el anterior apunta al nuevo.
            nuevo.setSiguiente(anterior.getSiguiente());
            anterior.setSiguiente(nuevo);
            this.tamaño++;
        }

        /**
         * Elimina la primera aparición del dato en la lista (queda inaccesible para el GC).
         *
         * @param dato entero a eliminar
         * @return {@code true} si el dato estaba y fue eliminado
         * Complejidad: O(n)
         */
        public boolean eliminar(int dato) {
            // Caso borde: lista vacía, no hay nada que eliminar.
            if (estaVacia()) {
                return false;
            }
            // Caso borde: el head es el dato buscado; el nuevo head es su siguiente.
            if (this.head.getDato() == dato) {
                this.head = this.head.getSiguiente();
                this.tamaño--;
                return true;
            }
            Nodo anterior = this.head;
            while (anterior.getSiguiente() != null && anterior.getSiguiente().getDato() != dato) {
                anterior = anterior.getSiguiente();
            }
            // Caso borde: el dato no existe en la lista.
            if (anterior.getSiguiente() == null) {
                return false;
            }
            // El anterior salta al siguiente del eliminado: el nodo queda desconectado.
            anterior.setSiguiente(anterior.getSiguiente().getSiguiente());
            this.tamaño--;
            return true;
        }

        /**
         * Elimina el nodo que está en la posición indicada y actualiza el tamaño.
         *
         * @param posicion índice del nodo a eliminar (0 = head)
         * @throws IllegalArgumentException si la posición es inválida: posicion &lt; 0 o
         *         posicion &gt;= tamaño (caso borde)
         * Complejidad: O(n)
         */
        public void eliminarEnPosicion(int posicion) {
            // Validación ANTES de recorrer: posición fuera del rango [0, tamaño - 1].
            if (posicion < 0 || posicion >= this.tamaño) {
                throw new IllegalArgumentException(
                        "Posición inválida: " + posicion + " (tamaño = " + this.tamaño + ").");
            }

            if (posicion == 0) {
                // Caso borde: se elimina el head; el nuevo head es el siguiente del eliminado.
                this.head = this.head.getSiguiente();
            } else {
                // Se recorre hasta el nodo ANTERIOR al que se elimina (posición posicion - 1).
                Nodo anterior = this.head;
                for (int i = 0; i < posicion - 1; i++) {
                    anterior = anterior.getSiguiente();
                }
                // El anterior hace "saltar" al eliminado: su enlace apunta al siguiente del
                // eliminado, dejándolo desconectado de la cadena (después lo libera el GC).
                anterior.setSiguiente(anterior.getSiguiente().getSiguiente());
            }
            this.tamaño--;
        }

        /**
         * Imprime los elementos desde head hacia null siguiando las referencias "siguiente".
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

        // Caso borde: lista vacía, cualquier posición es inválida
        System.out.println("=== Caso borde: lista vacía ===");
        try {
            lista.eliminarEnPosicion(0);
        } catch (IllegalArgumentException e) {
            System.out.println("eliminarEnPosicion(0) -> " + e.getMessage());
        }

        System.out.println("\n=== Insertando elementos ===");
        lista.insertarAlFinal(20);
        lista.insertarAlFinal(30);
        lista.insertarAlInicio(10);
        lista.insertarAlFinal(40);
        lista.insertarAlFinal(50);
        System.out.print("Lista: ");
        lista.imprimir(); // head -> [10] -> [20] -> [30] -> [40] -> [50] -> null
        System.out.println("Tamaño: " + lista.getSize()); // 5

        System.out.println("\n=== Eliminar el HEAD (posición 0 = 10) ===");
        lista.eliminarEnPosicion(0);
        System.out.print("Lista: ");
        lista.imprimir(); // head -> [20] -> [30] -> [40] -> [50] -> null
        System.out.println("Tamaño: " + lista.getSize()); // 4

        System.out.println("\n=== Eliminar un nodo intermedio (posición 1 = 30) ===");
        lista.eliminarEnPosicion(1);
        System.out.print("Lista: ");
        lista.imprimir(); // head -> [20] -> [40] -> [50] -> null
        System.out.println("Tamaño: " + lista.getSize()); // 3

        System.out.println("\n=== Eliminar el ÚLTIMO (posición size - 1 = 50) ===");
        lista.eliminarEnPosicion(lista.getSize() - 1);
        System.out.print("Lista: ");
        lista.imprimir(); // head -> [20] -> [40] -> null
        System.out.println("Tamaño: " + lista.getSize()); // 2

        // Caso borde: dejar la lista vacía eliminando sus dos últimos nodos
        System.out.println("\n=== Vaciando la lista ===");
        lista.eliminarEnPosicion(1);
        lista.eliminarEnPosicion(0);
        System.out.print("Lista: ");
        lista.imprimir(); // La lista está vacía.
        System.out.println("¿Está vacía? " + lista.estaVacia() + " | Tamaño: " + lista.getSize());

        // Caso borde: posiciones negativas y posiciones fuera de rango
        System.out.println("\n=== Caso borde: posiciones inválidas ===");
        lista.insertarAlFinal(100);
        lista.insertarAlFinal(200);
        try {
            lista.eliminarEnPosicion(-1);
        } catch (IllegalArgumentException e) {
            System.out.println("eliminarEnPosicion(-1) -> " + e.getMessage());
        }
        try {
            lista.eliminarEnPosicion(lista.getSize());
        } catch (IllegalArgumentException e) {
            System.out.println("eliminarEnPosicion(" + lista.getSize() + ") -> " + e.getMessage());
        }
        System.out.print("Lista sin cambios: ");
        lista.imprimir(); // head -> [100] -> [200] -> null
    }
}

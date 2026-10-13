/*
Prompt inicial utilizado:
Implementá en Java un método contarOcurrencias(int dato) para una lista enlazada simple de
enteros, sin colecciones de la JDK ni Scanner/System.in.
La lista se arma con nodos que guardan un dato y una referencia "siguiente"; un head apunta al
primer nodo y un tamaño lleva la cuenta de los elementos, y el recorrido es secuencial.
El método debe recorrer TODA la lista desde head hasta null contando cuántas veces aparece el
dato; NO hay que cortar el recorrido al encontrar la primera coincidencia, porque el mismo
valor puede repetirse en posiciones posteriores.
Por ejemplo, en la lista 10 -> 20 -> 10 -> 30 -> 10 -> null, contarOcurrencias(10) debe
devolver 3 (no 1): cada nodo se visita y solo se incrementa el contador si su dato coincide.
Contemplar también el caso de un valor que no aparece (devuelve 0) y la lista vacía (devuelve 0).
Indicar complejidades. Ajustes posteriores: ninguno.
*/

/**
 * Ejercicio 8: Contar las ocurrencias de un valor en una lista enlazada simple.
 *
 * <p>Se recorre la lista completa sumando coincidencias; a diferencia de buscar(), el recorrido
 * nunca se corta al encontrar el primer dato igual, porque el valor puede repetirse.</p>
 */
public class Ejercicio8 {

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
         * Cuenta cuántas veces aparece un dato en TODA la lista (sin cortar el recorrido).
         *
         * @param dato entero a contar
         * @return cantidad de coincidencias (0 si no aparece o la lista está vacía)
         * Complejidad: O(n)
         */
        public int contarOcurrencias(int dato) {
            int contador = 0;
            Nodo actual = this.head;
            // Se recorre hasta null: aunque el dato aparezca, se sigue porque puede repetirse.
            while (actual != null) {
                if (actual.getDato() == dato) {
                    contador++;
                }
                actual = actual.getSiguiente(); // NO se corta en la primera coincidencia
            }
            return contador;
        }

        /**
         * Imprime los elementos desde head hacia null siguiendo las referencias "siguiente".
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
        // Caso borde: lista vacía, cualquier dato se cuenta 0 veces
        System.out.println("=== Caso borde: lista vacía ===");
        ListaEnlazada vacia = new ListaEnlazada();
        System.out.print("Lista: ");
        vacia.imprimir(); // La lista está vacía.
        System.out.println("contarOcurrencias(10) -> " + vacia.contarOcurrencias(10)); // 0

        System.out.println("\n=== Lista con valores repetidos ===");
        ListaEnlazada lista = new ListaEnlazada();
        lista.insertarAlFinal(10);
        lista.insertarAlFinal(20);
        lista.insertarAlFinal(10);
        lista.insertarAlFinal(30);
        lista.insertarAlFinal(10);
        System.out.print("Lista: ");
        lista.imprimir(); // head -> [10] -> [20] -> [10] -> [30] -> [10] -> null
        System.out.println("Tamaño: " + lista.getSize()); // 5

        System.out.println("\n=== Contando ocurrencias ===");
        // El 10 aparece en las posiciones 0, 2 y 4: el recorrido NO corta en la primera.
        System.out.println("contarOcurrencias(10) -> " + lista.contarOcurrencias(10)); // 3
        System.out.println("contarOcurrencias(20) -> " + lista.contarOcurrencias(20)); // 1
        System.out.println("contarOcurrencias(30) -> " + lista.contarOcurrencias(30)); // 1

        // Caso borde: valor que no aparece en la lista
        System.out.println("\n=== Caso borde: valor inexistente ===");
        System.out.println("contarOcurrencias(999) -> " + lista.contarOcurrencias(999)); // 0
        System.out.println("contarOcurrencias(0) -> " + lista.contarOcurrencias(0));     // 0

        // Tras eliminar una ocurrencia, la cuenta baja: 3 -> 2
        System.out.println("\n=== Tras eliminar una ocurrencia del 10 ===");
        System.out.println("¿Eliminado el primer 10? " + lista.eliminar(10)); // true
        System.out.print("Lista: ");
        lista.imprimir(); // head -> [20] -> [10] -> [30] -> [10] -> null
        System.out.println("contarOcurrencias(10) -> " + lista.contarOcurrencias(10)); // 2
    }
}

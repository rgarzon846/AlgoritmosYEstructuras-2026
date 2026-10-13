/*
Prompt inicial utilizado:
Implementá en Java un método invertir() que invierta el orden de una lista enlazada simple de
enteros, sin colecciones de la JDK ni Scanner/System.in.
La lista se arma con nodos que guardan un dato y una referencia "siguiente"; un head apunta al
primer nodo y un tamaño lleva la cuenta de los elementos.
Invertir significa que el último nodo pase a ser el head y que cada nodo apunte al que antes le
precedía: por ejemplo 10 -> 20 -> 30 -> 40 -> null invertida da 40 -> 30 -> 20 -> 10 -> null.
Para no perder nodos hacen falta tres referencias auxiliares: "anterior" (el tramo ya invertido,
arranca en null), "actual" (el nodo que se está procesando) y "siguiente" (para no perder la
cola de la lista al modificar el enlace de actual).
El orden de actualización es fundamental: primero siguiente = actual.getSiguiente() (guardar el
resto), después actual.setSiguiente(anterior) (invertir el enlace), después anterior = actual y
por último actual = siguiente; si se pisa el enlace de actual antes de guardar siguiente, se
pierde el resto de la lista para siempre.
Casos borde: lista vacía y lista con un solo nodo (no cambian nada). Indicar complejidades.
Ajustes posteriores: ninguno.
*/

/**
 * Ejercicio 9: Invertir el orden de una lista enlazada simple.
 *
 * <p>Se recorre la lista una sola vez volteando cada enlace con la ayuda de tres referencias
 * auxiliares (anterior, actual y siguiente); al terminar, el head pasa a ser el último nodo.</p>
 */
public class Ejercicio9 {

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
         * @throws IllegalArgumentException si la posición es inválida (caso borde)
         * Complejidad: O(n)
         */
        public void eliminarEnPosicion(int posicion) {
            if (posicion < 0 || posicion >= this.tamaño) {
                throw new IllegalArgumentException(
                        "Posición inválida: " + posicion + " (tamaño = " + this.tamaño + ").");
            }
            if (posicion == 0) {
                // Caso borde: se elimina el head; el nuevo head es el siguiente del eliminado.
                this.head = this.head.getSiguiente();
            } else {
                Nodo anterior = this.head;
                for (int i = 0; i < posicion - 1; i++) {
                    anterior = anterior.getSiguiente();
                }
                anterior.setSiguiente(anterior.getSiguiente().getSiguiente());
            }
            this.tamaño--;
        }

        /**
         * Cambia el dato almacenado en la posición indicada sin tocar los enlaces de la lista.
         *
         * @param posicion índice del nodo a modificar (0 = head)
         * @param nuevoDato entero que reemplaza al dato anterior del nodo
         * @throws IllegalArgumentException si la posición es inválida (caso borde)
         * Complejidad: O(n)
         */
        public void modificar(int posicion, int nuevoDato) {
            if (posicion < 0 || posicion >= this.tamaño) {
                throw new IllegalArgumentException(
                        "Posición inválida: " + posicion + " (tamaño = " + this.tamaño + ").");
            }
            Nodo actual = this.head;
            for (int i = 0; i < posicion; i++) {
                actual = actual.getSiguiente();
            }
            actual.setDato(nuevoDato);
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
            while (actual != null) {
                if (actual.getDato() == dato) {
                    contador++;
                }
                actual = actual.getSiguiente(); // NO se corta en la primera coincidencia
            }
            return contador;
        }

        /**
         * Invierte el orden de la lista volteando cada enlace con tres referencias auxiliares
         * (anterior, actual y siguiente); el tamaño no cambia.
         * Complejidad: O(n)
         */
        public void invertir() {
            // Caso borde: lista vacía o de un solo nodo, no hay nada que invertir.
            if (estaVacia() || this.head.getSiguiente() == null) {
                return;
            }

            Nodo anterior = null; // tramo ya invertido; null porque el viejo head será el tail
            Nodo actual = this.head;
            while (actual != null) {
                // 1) Se guarda el resto de la lista ANTES de tocar el enlace de actual,
                //    si no, se pierde el acceso al resto de los nodos.
                Nodo siguiente = actual.getSiguiente();
                // 2) Se invierte el enlace: actual ahora apunta al tramo ya invertido.
                actual.setSiguiente(anterior);
                // 3) Se avanza el tramo invertido: anterior toma el lugar de actual.
                anterior = actual;
                // 4) Se avanza al siguiente nodo a procesar (el que se había guardado).
                actual = siguiente;
            }
            // El último nodo procesado (el viejo tail) es el nuevo head de la lista.
            this.head = anterior;
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
        // Caso borde: lista vacía, invertir no rompe nada
        System.out.println("=== Caso borde: lista vacía ===");
        ListaEnlazada vacia = new ListaEnlazada();
        vacia.invertir();
        System.out.print("Lista: ");
        vacia.imprimir(); // La lista está vacía.
        System.out.println("Tamaño: " + vacia.getSize()); // 0

        // Caso borde: un solo nodo, invertir lo deja igual
        System.out.println("\n=== Caso borde: un solo nodo ===");
        ListaEnlazada unNodo = new ListaEnlazada();
        unNodo.insertarAlFinal(7);
        unNodo.invertir();
        System.out.print("Lista: ");
        unNodo.imprimir(); // head -> [7] -> null

        System.out.println("\n=== Lista de cuatro elementos ===");
        ListaEnlazada lista = new ListaEnlazada();
        lista.insertarAlFinal(10);
        lista.insertarAlFinal(20);
        lista.insertarAlFinal(30);
        lista.insertarAlFinal(40);
        System.out.print("Antes de invertir:  ");
        lista.imprimir(); // head -> [10] -> [20] -> [30] -> [40] -> null
        System.out.println("Tamaño: " + lista.getSize()); // 4

        lista.invertir();
        System.out.print("Después de invertir: ");
        lista.imprimir(); // head -> [40] -> [30] -> [20] -> [10] -> null
        System.out.println("Tamaño (no cambia): " + lista.getSize()); // 4

        // Verificación: la inversión no pierde nodos, los datos siguen todos
        System.out.println("\n=== Verificación tras invertir ===");
        System.out.println("¿Está el 40 en la posición 0? " + (lista.obtener(0) == 40)); // true
        System.out.println("¿Está el 10 en la última posición? "
                + (lista.obtener(lista.getSize() - 1) == 10)); // true
        System.out.println("contarOcurrencias(30) -> " + lista.contarOcurrencias(30)); // 1

        // Invertir dos veces devuelve la lista al orden original
        System.out.println("\n=== Invertir dos veces (vuelve al orden original) ===");
        lista.invertir();
        System.out.print("Lista: ");
        lista.imprimir(); // head -> [10] -> [20] -> [30] -> [40] -> null

        // Seguir operando después de invertir: la lista sigue siendo válida
        System.out.println("\n=== Operar después de invertir ===");
        lista.insertarAlInicio(5);
        lista.eliminar(30);
        System.out.print("Tras insertarAlInicio(5) y eliminar(30): ");
        lista.imprimir(); // head -> [5] -> [10] -> [20] -> [40] -> null
    }
}

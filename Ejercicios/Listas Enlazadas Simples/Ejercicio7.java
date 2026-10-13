/*
Prompt inicial utilizado:
Implementá en Java un método modificar(int posicion, int nuevoDato) para una lista enlazada
simple de enteros, sin colecciones de la JDK ni Scanner/System.in.
La lista se arma con nodos que guardan un dato y una referencia "siguiente"; un head apunta al
primer nodo y un tamaño lleva la cuenta de los elementos.
Como la lista enlazada no tiene acceso directo por índice (no es un arreglo), hay que recorrer
nodo a nodo desde head hasta llegar a la posición pedida; recién ahí se cambia el contenido.
Validar la posición ANTES de empezar el recorrer (posicion < 0 o posicion >= size ->
IllegalArgumentException), así no se recorre la lista de más ante una posición inválida.
La diferencia clave: modificar el dato de un nodo es solo cambiar su contenido (setDato) y NO
tocar ningún enlace; modificar la referencia al siguiente reconectaría la lista y podría romperla
o perder nodos. Indicar complejidades. Ajustes posteriores: ninguno.
*/

/**
 * Ejercicio 7: Modificar el dato de un nodo por posición en una lista enlazada simple.
 *
 * <p>El método recorre hasta la posición pedida y cambia solo el contenido del nodo; los
 * enlaces "siguiente" de la lista quedan intactos, a diferencia de una eliminación.</p>
 */
public class Ejercicio7 {

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
         * @throws IllegalArgumentException si la posición es inválida: posicion &lt; 0 o
         *         posicion &gt;= tamaño (caso borde); se valida ANTES de recorrer
         * Complejidad: O(n)
         */
        public void modificar(int posicion, int nuevoDato) {
            // Validación primero: si la posición es inválida ni se recorre la lista.
            if (posicion < 0 || posicion >= this.tamaño) {
                throw new IllegalArgumentException(
                        "Posición inválida: " + posicion + " (tamaño = " + this.tamaño + ").");
            }

            // Se recorre hasta el nodo en la posición pedida (no hay acceso directo por índice).
            Nodo actual = this.head;
            for (int i = 0; i < posicion; i++) {
                actual = actual.getSiguiente();
            }
            // Solo se cambia el CONTENIDO del nodo; ningún enlace "siguiente" se modifica,
            // por eso la estructura de la lista queda exactamente igual.
            actual.setDato(nuevoDato);
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
        ListaEnlazada lista = new ListaEnlazada();

        // Caso borde: lista vacía, cualquier posición es inválida
        System.out.println("=== Caso borde: lista vacía ===");
        try {
            lista.modificar(0, 99);
        } catch (IllegalArgumentException e) {
            System.out.println("modificar(0, 99) -> " + e.getMessage());
        }

        System.out.println("\n=== Insertando elementos ===");
        lista.insertarAlFinal(20);
        lista.insertarAlFinal(30);
        lista.insertarAlInicio(10);
        lista.insertarAlFinal(40);
        lista.insertarAlFinal(50);
        System.out.print("Lista original: ");
        lista.imprimir(); // head -> [10] -> [20] -> [30] -> [40] -> [50] -> null

        System.out.println("\n=== Modificar el HEAD (posición 0: 10 -> 15) ===");
        lista.modificar(0, 15);
        System.out.print("Lista: ");
        lista.imprimir(); // head -> [15] -> [20] -> [30] -> [40] -> [50] -> null
        System.out.println("Tamaño (no cambia): " + lista.getSize()); // 5

        System.out.println("\n=== Modificar un nodo intermedio (posición 2: 30 -> 33) ===");
        lista.modificar(2, 33);
        System.out.print("Lista: ");
        lista.imprimir(); // head -> [15] -> [20] -> [33] -> [40] -> [50] -> null

        System.out.println("\n=== Modificar el ÚLTIMO (posición size - 1: 50 -> 55) ===");
        lista.modificar(lista.getSize() - 1, 55);
        System.out.print("Lista: ");
        lista.imprimir(); // head -> [15] -> [20] -> [33] -> [40] -> [55] -> null

        // Los enlaces siguen intactos: obtener() sigue devolviendo los valores de las posiciones
        System.out.println("\n=== Verificación de posiciones tras modificar ===");
        System.out.println("obtener(0): " + lista.obtener(0));  // 15
        System.out.println("obtener(2): " + lista.obtener(2));  // 33
        System.out.println("obtener(4): " + lista.obtener(4));  // 55

        // Caso borde: posiciones negativas y fuera de rango (se validan antes de recorrer)
        System.out.println("\n=== Caso borde: posiciones inválidas ===");
        try {
            lista.modificar(-1, 111);
        } catch (IllegalArgumentException e) {
            System.out.println("modificar(-1, 111) -> " + e.getMessage());
        }
        try {
            lista.modificar(lista.getSize(), 222);
        } catch (IllegalArgumentException e) {
            System.out.println("modificar(" + lista.getSize() + ", 222) -> " + e.getMessage());
        }
        System.out.print("Lista sin cambios: ");
        lista.imprimir(); // head -> [15] -> [20] -> [33] -> [40] -> [55] -> null
    }
}

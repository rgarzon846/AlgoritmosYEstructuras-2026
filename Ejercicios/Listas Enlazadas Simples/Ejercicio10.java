/*
Prompt inicial utilizado:
Tomá la lista enlazada simple de enteros que venimos armando (ejercicios 1 al 9) y convertila
en una versión genérica ListaEnlazada<T> con nodos Nodo<T>, sin colecciones de la JDK ni
Scanner/System.in.
Las clases Nodo<T> y ListaEnlazada<T> van como clases internas ESTÁTICAS dentro de la clase
pública Ejercicio10, que NO es genérica, así el main puede crear listas de distinto tipo en un
mismo archivo.
Qué cambia al generalizar: los tipos concretos (int -> T, Nodo -> Nodo<T>, Nodo siguiente ->
Nodo<T> siguiente) y la comparación de elementos, que ahora no puede usar == para los datos y
debe ser null-safe con equals() (por eso se escribe una comparación a mano).
Qué se mantiene igual: head, tamaño, el recorrido secuencial desde head hasta null y TODOS los
algoritmos (insertar, buscar, obtener, insertarEnPosicion, eliminar, eliminarEnPosicion,
modificar, contarOcurrencias, invertir, imprimir), porque no dependen del tipo de dato.
El main debe demostrar ListaEnlazada<Integer>, ListaEnlazada<String> y ListaEnlazada<Alumno>,
siendo Alumno una clase interna estática con nombre, legajo, equals y toString.
Indicar complejidades. Ajustes posteriores: ninguno.
*/

/**
 * Ejercicio 10: Lista enlazada simple GENÉRICA (ListaEnlazada&lt;T&gt; y Nodo&lt;T&gt;).
 *
 * <p>La clase pública no es genérica para poder usar el main con distintos tipos; la lista
 * genérica vive en la clase interna estática {@code ListaEnlazada}, y sus algoritmos de
 * recorrido, inserción y eliminación no cambian respecto de la versión de enteros.</p>
 */
public class Ejercicio10 {

    // ---------------------------------------------------------------------
    // Nodo genérico de la lista enlazada simple
    // ---------------------------------------------------------------------
    /**
     * Nodo genérico: guarda un dato de tipo T y una referencia al siguiente nodo.
     *
     * @param <T> tipo del dato almacenado en el nodo
     */
    static class Nodo<T> {

        private T dato;
        private Nodo<T> siguiente;

        /**
         * Crea un nodo con el dato indicado y sin enlace.
         *
         * @param dato contenido del nodo (de tipo T)
         */
        public Nodo(T dato) {
            this.dato = dato;
            this.siguiente = null;
        }

        public T getDato() {
            return dato;
        }

        public void setDato(T dato) {
            this.dato = dato;
        }

        public Nodo<T> getSiguiente() {
            return siguiente;
        }

        public void setSiguiente(Nodo<T> siguiente) {
            this.siguiente = siguiente;
        }
    }

    // ---------------------------------------------------------------------
    // Lista enlazada simple genérica (head + tamaño)
    // ---------------------------------------------------------------------
    /**
     * Lista enlazada simple de elementos de tipo T: head + tamaño, con todas las operaciones
     * de los ejercicios anteriores adaptadas al tipo genérico.
     *
     * @param <T> tipo de los elementos almacenados en la lista
     */
    static class ListaEnlazada<T> {

        private Nodo<T> head; // primer nodo; null si la lista está vacía
        private int tamaño;

        /**
         * Crea una lista enlazada vacía.
         */
        public ListaEnlazada() {
            this.head = null;
            this.tamaño = 0;
        }

        /**
         * Compara dos elementos genéricos de forma null-safe (sin usar == para los datos).
         * Si el tipo define bien equals(), dos objetos distintos pueden ser "iguales".
         *
         * @param a primer elemento a comparar (puede ser null)
         * @param b segundo elemento a comparar (puede ser null)
         * @return {@code true} si ambos elementos son iguales según equals()
         * Complejidad: O(1) en promedio (depende del equals() del tipo T)
         */
        private boolean sonIguales(T a, T b) {
            // Comparación null-safe hecha a mano: null == null es true; null contra un valor, false.
            if (a == null && b == null) {
                return true;
            }
            if (a == null || b == null) {
                return false;
            }
            return a.equals(b);
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
         * @param dato elemento a insertar (de tipo T)
         * Complejidad: O(1)
         */
        public void insertarAlInicio(T dato) {
            Nodo<T> nuevo = new Nodo<>(dato);
            nuevo.setSiguiente(this.head);
            this.head = nuevo;
            this.tamaño++;
        }

        /**
         * Inserta un elemento al final de la lista.
         *
         * @param dato elemento a insertar (de tipo T)
         * Complejidad: O(n)
         */
        public void insertarAlFinal(T dato) {
            Nodo<T> nuevo = new Nodo<>(dato);
            if (estaVacia()) {
                this.head = nuevo;
            } else {
                Nodo<T> actual = this.head;
                while (actual.getSiguiente() != null) {
                    actual = actual.getSiguiente();
                }
                actual.setSiguiente(nuevo);
            }
            this.tamaño++;
        }

        /**
         * Busca un elemento recorriendo la lista desde head (comparación con sonIguales).
         *
         * @param dato elemento a buscar (de tipo T)
         * @return {@code true} si el elemento está en la lista
         * Complejidad: O(n)
         */
        public boolean buscar(T dato) {
            Nodo<T> actual = this.head;
            while (actual != null && !sonIguales(actual.getDato(), dato)) {
                actual = actual.getSiguiente();
            }
            return actual != null;
        }

        /**
         * Obtiene el elemento almacenado en una posición determinada (0 = head).
         *
         * @param posicion índice del elemento a devolver
         * @return elemento almacenado en esa posición (de tipo T)
         * @throws IllegalArgumentException si la posición es inválida (caso borde)
         * Complejidad: O(n)
         */
        public T obtener(int posicion) {
            if (posicion < 0 || posicion >= this.tamaño) {
                throw new IllegalArgumentException(
                        "Posición inválida: " + posicion + " (tamaño = " + this.tamaño + ").");
            }
            Nodo<T> actual = this.head;
            for (int i = 0; i < posicion; i++) {
                actual = actual.getSiguiente();
            }
            return actual.getDato();
        }

        /**
         * Inserta un nodo en una posición específica (inicio, medio o final).
         *
         * @param dato elemento a insertar (de tipo T)
         * @param posicion índice en el que quedará el nuevo nodo (0 a size inclusive)
         * @throws IllegalArgumentException si la posición es inválida (caso borde)
         * Complejidad: O(n)
         */
        public void insertarEnPosicion(T dato, int posicion) {
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
            Nodo<T> nuevo = new Nodo<>(dato);
            Nodo<T> anterior = this.head;
            for (int i = 0; i < posicion - 1; i++) {
                anterior = anterior.getSiguiente();
            }
            // Primero el nuevo apunta al siguiente, después el anterior apunta al nuevo.
            nuevo.setSiguiente(anterior.getSiguiente());
            anterior.setSiguiente(nuevo);
            this.tamaño++;
        }

        /**
         * Elimina la primera ocurrencia del elemento (queda inaccesible para el GC).
         * Maneja los casos borde: lista vacía, eliminar el head y elemento inexistente.
         *
         * @param dato elemento a eliminar (de tipo T)
         * @return {@code true} si el elemento estaba y fue eliminado
         * Complejidad: O(n)
         */
        public boolean eliminar(T dato) {
            // Caso borde: lista vacía, no hay nada que eliminar.
            if (estaVacia()) {
                return false;
            }
            // Caso borde: el head es el elemento buscado; el nuevo head es su siguiente.
            if (sonIguales(this.head.getDato(), dato)) {
                this.head = this.head.getSiguiente();
                this.tamaño--;
                return true;
            }
            Nodo<T> anterior = this.head;
            while (anterior.getSiguiente() != null
                    && !sonIguales(anterior.getSiguiente().getDato(), dato)) {
                anterior = anterior.getSiguiente();
            }
            // Caso borde: el elemento no existe en la lista.
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
                Nodo<T> anterior = this.head;
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
         * @param nuevoDato elemento que reemplaza al dato anterior del nodo (de tipo T)
         * @throws IllegalArgumentException si la posición es inválida (caso borde)
         * Complejidad: O(n)
         */
        public void modificar(int posicion, T nuevoDato) {
            if (posicion < 0 || posicion >= this.tamaño) {
                throw new IllegalArgumentException(
                        "Posición inválida: " + posicion + " (tamaño = " + this.tamaño + ").");
            }
            Nodo<T> actual = this.head;
            for (int i = 0; i < posicion; i++) {
                actual = actual.getSiguiente();
            }
            // Solo se cambia el CONTENIDO del nodo; ningún enlace se modifica.
            actual.setDato(nuevoDato);
        }

        /**
         * Cuenta cuántas veces aparece un elemento en TODA la lista (sin cortar el recorrido).
         *
         * @param dato elemento a contar (de tipo T)
         * @return cantidad de coincidencias (0 si no aparece o la lista está vacía)
         * Complejidad: O(n)
         */
        public int contarOcurrencias(T dato) {
            int contador = 0;
            Nodo<T> actual = this.head;
            while (actual != null) {
                if (sonIguales(actual.getDato(), dato)) {
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
            Nodo<T> anterior = null; // tramo ya invertido; null porque el viejo head será el tail
            Nodo<T> actual = this.head;
            while (actual != null) {
                Nodo<T> siguiente = actual.getSiguiente(); // 1) guardar el resto
                actual.setSiguiente(anterior);             // 2) invertir el enlace
                anterior = actual;                         // 3) avanzar anterior
                actual = siguiente;                        // 4) avanzar actual
            }
            this.head = anterior; // el viejo tail es el nuevo head
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
            Nodo<T> actual = this.head;
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
    // Tipo de ejemplo: Alumno (nombre y legajo) con equals() y toString()
    // ---------------------------------------------------------------------
    /**
     * Alumno de ejemplo para demostrar la lista con un tipo propio.
     * El equals() identifica a un alumno por su legajo.
     */
    static class Alumno {

        private String nombre;
        private int legajo;

        /**
         * Crea un alumno con nombre y legajo.
         *
         * @param nombre nombre completo del alumno
         * @param legajo número de legajo (identificador)
         */
        public Alumno(String nombre, int legajo) {
            this.nombre = nombre;
            this.legajo = legajo;
        }

        public String getNombre() {
            return nombre;
        }

        public int getLegajo() {
            return legajo;
        }

        /**
         * Dos alumnos son iguales si tienen el mismo legajo (la identidad del alumno).
         *
         * @param obj objeto a comparar con este alumno
         * @return {@code true} si el obj es un Alumno con el mismo legajo
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Alumno otro = (Alumno) obj;
            return this.legajo == otro.legajo;
        }

        /**
         * Representación en texto: "Nombre (legajo N)".
         *
         * @return descripción del alumno
         */
        @Override
        public String toString() {
            return this.nombre + " (legajo " + this.legajo + ")";
        }
    }

    // ---------------------------------------------------------------------
    // Pruebas con datos fijos (sin entrada por teclado)
    // ---------------------------------------------------------------------
    /**
     * Método principal: demuestra la lista genérica con Integer, String y Alumno.
     *
     * @param args argumentos de línea de comandos (no se usan)
     */
    public static void main(String[] args) {
        // --- Caso borde: lista vacía ---
        System.out.println("=== Caso borde: lista vacía ===");
        ListaEnlazada<Integer> vacia = new ListaEnlazada<>();
        System.out.print("Lista: ");
        vacia.imprimir(); // La lista está vacía.
        System.out.println("contarOcurrencias(10) -> " + vacia.contarOcurrencias(10)); // 0

        // --- Lista genérica de Integer: todas las operaciones ---
        System.out.println("\n=== ListaEnlazada<Integer> ===");
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();
        lista.insertarAlFinal(20);
        lista.insertarAlFinal(30);
        lista.insertarAlInicio(10);
        lista.insertarAlFinal(40);
        lista.insertarAlFinal(10);
        System.out.print("Lista: ");
        lista.imprimir(); // head -> [10] -> [20] -> [30] -> [40] -> [10] -> null
        System.out.println("Tamaño: " + lista.getSize()); // 5
        System.out.println("¿buscar(30)? " + lista.buscar(30));             // true
        System.out.println("¿buscar(999)? " + lista.buscar(999));           // false
        System.out.println("obtener(0): " + lista.obtener(0));              // 10
        System.out.println("contarOcurrencias(10) -> " + lista.contarOcurrencias(10)); // 2

        lista.insertarEnPosicion(25, 2);
        System.out.print("Tras insertarEnPosicion(25, 2): ");
        lista.imprimir(); // head -> [10] -> [20] -> [25] -> [30] -> [40] -> [10] -> null

        lista.modificar(0, 15);
        System.out.print("Tras modificar(0, 15): ");
        lista.imprimir(); // head -> [15] -> [20] -> [25] -> [30] -> [40] -> [10] -> null

        System.out.println("¿Eliminar el 30 (primera ocurrencia)? " + lista.eliminar(30)); // true
        lista.eliminarEnPosicion(0);
        System.out.print("Tras eliminar(30) y eliminarEnPosicion(0): ");
        lista.imprimir(); // head -> [20] -> [25] -> [40] -> [10] -> null

        lista.invertir();
        System.out.print("Tras invertir(): ");
        lista.imprimir(); // head -> [10] -> [40] -> [25] -> [20] -> null
        System.out.println("Tamaño (no cambia): " + lista.getSize()); // 4

        // Caso borde: posición inválida, se lanza antes de recorrer
        System.out.println("\n=== Caso borde: posiciones inválidas ===");
        try {
            lista.obtener(-1);
        } catch (IllegalArgumentException e) {
            System.out.println("obtener(-1) -> " + e.getMessage());
        }
        try {
            lista.modificar(lista.getSize(), 99);
        } catch (IllegalArgumentException e) {
            System.out.println("modificar(" + lista.getSize() + ", 99) -> " + e.getMessage());
        }

        // --- Lista genérica de String (misma estructura, distinto tipo) ---
        System.out.println("\n=== ListaEnlazada<String> ===");
        ListaEnlazada<String> nombres = new ListaEnlazada<>();
        nombres.insertarAlFinal("Luis");
        nombres.insertarAlFinal("Ana");
        nombres.insertarAlFinal("Luis");
        nombres.insertarAlInicio("María");
        System.out.print("Lista: ");
        nombres.imprimir(); // head -> [María] -> [Luis] -> [Ana] -> [Luis] -> null
        System.out.println("contarOcurrencias(\"Luis\") -> " + nombres.contarOcurrencias("Luis")); // 2
        System.out.println("¿buscar(\"Ana\")? " + nombres.buscar("Ana")); // true
        System.out.println("¿Eliminar \"Ana\"? " + nombres.eliminar("Ana")); // true
        System.out.print("Tras eliminar(\"Ana\"): ");
        nombres.imprimir(); // head -> [María] -> [Luis] -> [Luis] -> null

        // --- Lista genérica de Alumno (tipo propio con equals por legajo) ---
        System.out.println("\n=== ListaEnlazada<Alumno> ===");
        ListaEnlazada<Alumno> alumnos = new ListaEnlazada<>();
        alumnos.insertarAlFinal(new Alumno("Ana López", 1001));
        alumnos.insertarAlFinal(new Alumno("Juan Pérez", 1002));
        alumnos.insertarAlFinal(new Alumno("Sofía Gómez", 1003));
        alumnos.insertarAlInicio(new Alumno("Diego Ruiz", 1000));
        System.out.print("Lista: ");
        alumnos.imprimir();
        // head -> [Diego Ruiz (legajo 1000)] -> [Ana López (legajo 1001)] -> ...

        // equals() por legajo: un objeto DISTINTO con el mismo legajo "es" el mismo alumno
        System.out.println("¿buscar(legajo 1002)? "
                + alumnos.buscar(new Alumno("Juan Pérez", 1002))); // true
        System.out.println("¿buscar(legajo 9999)? "
                + alumnos.buscar(new Alumno("Juan Pérez", 9999))); // false (legajo distinto)
        System.out.println("obtener(0): " + alumnos.obtener(0)); // Diego Ruiz (legajo 1000)

        System.out.println("¿Eliminar(legajo 1002)? "
                + alumnos.eliminar(new Alumno("Juan Pérez", 1002))); // true
        System.out.print("Tras eliminar(legajo 1002): ");
        alumnos.imprimir(); // sin Juan Pérez
        System.out.println("Tamaño: " + alumnos.getSize()); // 3

        alumnos.modificar(1, new Alumno("Ana López", 2000));
        System.out.print("Tras modificar(1, legajo 2000): ");
        alumnos.imprimir(); // Ana López ahora tiene legajo 2000
    }
}

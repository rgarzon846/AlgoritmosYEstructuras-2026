/*
Prompt inicial utilizado:
Implementá en Java una pila genérica <T> desde cero con lista enlazada simple, sin colecciones
de la JDK (LinkedList, ArrayList, etc.) ni Scanner/System.in.
Los genéricos permiten que la misma lógica de apilar/desapilar funcione con cualquier tipo sin
reescribir la estructura: la pila no depende de que el elemento sea Integer, String u objeto propio.
Operaciones: apilar, desapilar, consultar tope, verificar vacía, imprimir.
Caso borde: desapilar o tope sobre pila vacía -> IllegalArgumentException. Indicar complejidades.
Ajustes posteriores: agregar clase estática anidada Mascota (nombre/edad) para probar objetos propios.
*/

/**
 * Ejercicio 3: Pila genérica (LIFO) construida con lista enlazada simple.
 *
 * <p>El parámetro de tipo {@code T} hace que la estructura sea reutilizable: la lógica de
 * apilar/desapilar es idéntica sin importar el tipo de dato que almacene la pila.</p>
 */
public class Ejercicio3 {

    // ---------------------------------------------------------------------
    // Nodo genérico de la lista enlazada simple
    // ---------------------------------------------------------------------
    static class Nodo<T> {

        private T dato;
        private Nodo<T> siguiente;

        /**
         * Crea un nodo con el dato indicado y sin enlace.
         *
         * @param dato valor de tipo T a almacenar
         */
        public Nodo(T dato) {
            this.dato = dato;
            this.siguiente = null;
        }

        public T getDato() {
            return dato;
        }

        public Nodo<T> getSiguiente() {
            return siguiente;
        }

        public void setSiguiente(Nodo<T> siguiente) {
            this.siguiente = siguiente;
        }
    }

    // ---------------------------------------------------------------------
    // Pila genérica sobre lista enlazada
    // ---------------------------------------------------------------------
    /**
     * Pila cuyo contenido puede ser de cualquier tipo T.
     * head representa el TOPE: apilar y desapilar son O(1).
     *
     * @param <T> tipo de los elementos de la pila
     */
    static class PilaGenerica<T> {

        private Nodo<T> head; // TOPE de la pila; null significa pila vacía

        public PilaGenerica() {
            this.head = null;
        }

        /**
         * Consulta si la pila no tiene elementos.
         *
         * @return {@code true} si la pila está vacía
         * Complejidad: O(1)
         */
        public boolean estaVacia() {
            return this.head == null;
        }

        /**
         * Apila un elemento en el tope.
         *
         * @param valor elemento de tipo T a apilar
         * Complejidad: O(1)
         */
        public void apilar(T valor) {
            Nodo<T> nuevo = new Nodo<>(valor);
            nuevo.setSiguiente(this.head); // el nuevo nodo apunta al antiguo tope
            this.head = nuevo;             // el nuevo nodo pasa a ser el tope
        }

        /**
         * Desapila y devuelve el elemento del tope.
         *
         * @return elemento que estaba en el tope
         * @throws IllegalArgumentException si la pila está vacía (caso borde)
         * Complejidad: O(1)
         */
        public T desapilar() {
            if (estaVacia()) {
                throw new IllegalArgumentException("La pila está vacía: no se puede desapilar.");
            }
            T valor = this.head.getDato();
            this.head = this.head.getSiguiente(); // el siguiente nodo pasa a ser el tope
            return valor;
        }

        /**
         * Consulta el elemento del tope sin desapilarlo.
         *
         * @return elemento del tope
         * @throws IllegalArgumentException si la pila está vacía (caso borde)
         * Complejidad: O(1)
         */
        public T tope() {
            if (estaVacia()) {
                throw new IllegalArgumentException("La pila está vacía: no hay tope que consultar.");
            }
            return this.head.getDato();
        }

        /**
         * Imprime el contenido de la pila del tope hacia la base.
         * Complejidad: O(n)
         */
        public void imprimir() {
            if (estaVacia()) {
                System.out.println("La pila está vacía.");
                return;
            }

            Nodo<T> actual = this.head;
            System.out.print("tope -> ");
            while (actual != null) {
                System.out.print("[" + actual.getDato() + "]");
                if (actual.getSiguiente() != null) {
                    System.out.print(" -> ");
                }
                actual = actual.getSiguiente();
            }
            System.out.println(" -> base");
        }
    }

    // ---------------------------------------------------------------------
    // Objeto propio para probar la pila genérica con un tipo no primitivo
    // ---------------------------------------------------------------------
    static class Mascota {

        private String nombre;
        private int edad;

        public Mascota(String nombre, int edad) {
            this.nombre = nombre;
            this.edad = edad;
        }

        public String getNombre() {
            return nombre;
        }

        public int getEdad() {
            return edad;
        }

        @Override
        public String toString() {
            return nombre + " (" + edad + " años)";
        }
    }

    // ---------------------------------------------------------------------
    // Pruebas con datos fijos (sin entrada por teclado)
    // ---------------------------------------------------------------------
    public static void main(String[] args) {
        // 1) Pila de Integer
        System.out.println("=== Pila de Integer ===");
        PilaGenerica<Integer> pilaEnteros = new PilaGenerica<>();
        pilaEnteros.apilar(1);
        pilaEnteros.apilar(2);
        pilaEnteros.apilar(3);
        pilaEnteros.imprimir();            // tope -> [3] -> [2] -> [1] -> base
        System.out.println("Tope: " + pilaEnteros.tope());     // 3
        System.out.println("Desapilado: " + pilaEnteros.desapilar()); // 3

        // 2) Pila de String (misma estructura, otro tipo)
        System.out.println("\n=== Pila de String ===");
        PilaGenerica<String> pilaStrings = new PilaGenerica<>();
        pilaStrings.apilar("uno");
        pilaStrings.apilar("dos");
        pilaStrings.imprimir();            // tope -> [dos] -> [uno] -> base
        System.out.println("Desapilado: " + pilaStrings.desapilar()); // dos
        System.out.println("Tope: " + pilaStrings.tope());             // uno

        // 3) Pila de objetos propios (Mascota)
        System.out.println("\n=== Pila de Mascota ===");
        PilaGenerica<Mascota> pilaMascotas = new PilaGenerica<>();
        pilaMascotas.apilar(new Mascota("Toby", 5));
        pilaMascotas.apilar(new Mascota("Luna", 3));
        pilaMascotas.imprimir(); // tope -> [Luna (3 años)] -> [Toby (5 años)] -> base
        System.out.println("Tope: " + pilaMascotas.tope()); // Luna (3 años)
        Mascota atendida = pilaMascotas.desapilar();
        System.out.println("Desapilada: " + atendida.getNombre()); // Luna

        // Caso borde: operaciones sobre pila vacía
        System.out.println("\n=== Caso borde (pila de Mascota vacía) ===");
        Mascota ultima = pilaMascotas.desapilar(); // queda solo Toby en la pila
        System.out.println("Desapilada: " + ultima.getNombre()); // Toby
        System.out.println("¿La pila quedó vacía?: " + pilaMascotas.estaVacia()); // true
        try {
            pilaMascotas.desapilar();
        } catch (IllegalArgumentException e) {
            System.out.println("desapilar() -> " + e.getMessage());
        }
        try {
            pilaMascotas.tope();
        } catch (IllegalArgumentException e) {
            System.out.println("tope() -> " + e.getMessage());
        }
    }
}

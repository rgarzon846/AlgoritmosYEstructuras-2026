/*
Prompt inicial utilizado:
Implementá en Java una cola genérica <T> desde cero con lista enlazada simple, sin colecciones
de la JDK (LinkedList, ArrayList, etc.) ni Scanner/System.in.
La cola debe mantener el orden FIFO (primero en entrar, primero en salir) sin importar el tipo
de los elementos: head (frente) y tail (último) se actualizan en O(1), nunca se depende de T.
Operaciones: encolar, desencolar, consultar frente, verificar vacía, imprimir.
Casos borde: desencolar/frente sobre cola vacía -> IllegalArgumentException; al vaciar, tail en null.
Ajustes posteriores: agregar clase estática anidada Cliente (nombre/saldo) para probar objetos propios.
*/

/**
 * Ejercicio 4: Cola genérica (FIFO) construida con lista enlazada simple.
 *
 * <p>El parámetro {@code T} no altera la lógica de la cola: el orden FIFO se conserva para
 * nombres (String), objetos propios o cualquier otro tipo, porque solo se manipulan punteros.</p>
 *
 * @param <T> tipo de los elementos de la cola
 */
public class Ejercicio4 {

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
    // Cola genérica sobre lista enlazada
    // ---------------------------------------------------------------------
    /**
     * Cola cuyo contenido puede ser de cualquier tipo T.
     * head es el FRENTE y tail el ÚLTIMO: encolar y desencolar son O(1).
     *
     * @param <T> tipo de los elementos de la cola
     */
    static class ColaGenerica<T> {

        private Nodo<T> head; // FRENTE (de donde se desencola); null si está vacía
        private Nodo<T> tail; // ÚLTIMO (donde se encola); null si está vacía

        public ColaGenerica() {
            this.head = null;
            this.tail = null;
        }

        /**
         * Consulta si la cola no tiene elementos.
         *
         * @return {@code true} si la cola está vacía
         * Complejidad: O(1)
         */
        public boolean estaVacia() {
            return this.head == null;
        }

        /**
         * Agrega un elemento al final de la cola.
         *
         * @param valor elemento de tipo T a encolar
         * Complejidad: O(1) - se usa tail, no se recorre la lista.
         */
        public void encolar(T valor) {
            Nodo<T> nuevo = new Nodo<>(valor);
            if (estaVacia()) {
                this.head = nuevo; // frente y final son el mismo nodo
            } else {
                this.tail.setSiguiente(nuevo);
            }
            this.tail = nuevo;
        }

        /**
         * Extrae y devuelve el elemento del frente (el más antiguo: FIFO).
         *
         * @return elemento que estaba en el frente
         * @throws IllegalArgumentException si la cola está vacía (caso borde)
         * Complejidad: O(1)
         */
        public T desencolar() {
            if (estaVacia()) {
                throw new IllegalArgumentException("La cola está vacía: no se puede desencolar.");
            }
            T valor = this.head.getDato();
            this.head = this.head.getSiguiente();
            if (this.head == null) {
                this.tail = null; // caso borde: cola vacía, tail debe quedar en null
            }
            return valor;
        }

        /**
         * Consulta el elemento del frente sin desencolarlo.
         *
         * @return elemento del frente
         * @throws IllegalArgumentException si la cola está vacía (caso borde)
         * Complejidad: O(1)
         */
        public T frente() {
            if (estaVacia()) {
                throw new IllegalArgumentException("La cola está vacía: no hay frente que consultar.");
            }
            return this.head.getDato();
        }

        /**
         * Imprime el contenido de la cola del frente hacia el final.
         * Complejidad: O(n)
         */
        public void imprimir() {
            if (estaVacia()) {
                System.out.println("La cola está vacía.");
                return;
            }

            Nodo<T> actual = this.head;
            System.out.print("frente -> ");
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
    // Objeto propio para probar la cola genérica con un tipo no primitivo
    // ---------------------------------------------------------------------
    static class Cliente {

        private String nombre;
        private double saldo;

        public Cliente(String nombre, double saldo) {
            this.nombre = nombre;
            this.saldo = saldo;
        }

        public String getNombre() {
            return nombre;
        }

        public double getSaldo() {
            return saldo;
        }

        @Override
        public String toString() {
            return nombre + " ($" + saldo + ")";
        }
    }

    // ---------------------------------------------------------------------
    // Pruebas con datos fijos (sin entrada por teclado)
    // ---------------------------------------------------------------------
    public static void main(String[] args) {
        // 1) Cola de nombres de personas (String): se verifica el orden FIFO
        System.out.println("=== Cola de nombres (String) ===");
        ColaGenerica<String> nombres = new ColaGenerica<>();
        nombres.encolar("Ana");
        nombres.encolar("Luis");
        nombres.encolar("Sofía");
        nombres.imprimir(); // frente -> [Ana] -> [Luis] -> [Sofía] -> null

        System.out.println("Frente: " + nombres.frente());                 // Ana
        System.out.println("Sale primero: " + nombres.desencolar());       // Ana (FIFO)
        System.out.println("Sale después: " + nombres.desencolar());       // Luis
        nombres.encolar("Marta");                                          // se agrega al final
        System.out.println("Sale después: " + nombres.desencolar());       // Sofía
        System.out.println("Sale al final: " + nombres.desencolar());      // Marta
        System.out.println("¿La cola quedó vacía?: " + nombres.estaVacia()); // true

        // 2) Cola de objetos Cliente: mismo FIFO, distinto tipo
        System.out.println("\n=== Cola de Clientes (objetos propios) ===");
        ColaGenerica<Cliente> clientes = new ColaGenerica<>();
        clientes.encolar(new Cliente("Carla", 1500.50));
        clientes.encolar(new Cliente("Diego", 300.00));
        clientes.encolar(new Cliente("Elena", 820.75));
        clientes.imprimir(); // frente -> [Carla ($1500.5)] -> [Diego ($300.0)] -> [Elena ($820.75)] -> null

        Cliente atendido = clientes.desencolar();
        System.out.println("Primer cliente atendido: " + atendido.getNombre()); // Carla (FIFO)
        System.out.println("Siguiente al frente: " + clientes.frente().getNombre()); // Diego

        // Casos borde: operaciones sobre cola vacía
        System.out.println("\n=== Casos borde (cola vacía) ===");
        clientes.desencolar(); // Diego
        clientes.desencolar(); // Elena
        System.out.println("¿La cola quedó vacía?: " + clientes.estaVacia()); // true
        try {
            clientes.desencolar();
        } catch (IllegalArgumentException e) {
            System.out.println("desencolar() -> " + e.getMessage());
        }
        try {
            clientes.frente();
        } catch (IllegalArgumentException e) {
            System.out.println("frente() -> " + e.getMessage());
        }
    }
}

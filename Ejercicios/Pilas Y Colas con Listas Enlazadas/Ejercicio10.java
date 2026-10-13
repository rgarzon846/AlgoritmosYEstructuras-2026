/*
Prompt inicial (breve):

Implementá en Java una lista doblemente enlazada GENÉRICA (<T>) construida desde cero (sin
colecciones de la JDK). Cada nodo tiene dos referencias: 'anterior' y 'siguiente', de
modo que se puede recorrer en ambas direcciones. Operaciones: insertarAlInicio, insertarAlFinal,
eliminar(T) (búsqueda lineal y desconexión), imprimirAdelante e imprimirAtras. Al eliminar,
los casos especiales son: lista vacía (nada que borrar), eliminar el head (se actualiza head),
eliminar el tail (se actualiza tail) y eliminar un elemento inexistente (la lista no cambia).
La estructura mantiene referencias head y tail para acceso O(1) a ambos extremos. Mostrar todos
los casos borde en main con datos fijos. Complejidades: insertar extremos O(1), eliminar O(n),
imprimir O(n).
Ajustes posteriores: se pidió además mantener un contador de tamaño actualizado en cada
operación (getSize).
*/

/**
 * Ejercicio 10 — Lista doblemente enlazada genérica.
 * Cada nodo se enlaza con 'anterior' y 'siguiente'; head y tail permiten acceso directo a los extremos.
 *
 * @param <T> tipo genérico de los elementos almacenados.
 */
public class Ejercicio10<T> {

    /**
     * Nodo doblemente enlazado: guarda el dato y referencias a los vecinos.
     *
     * @param <T> tipo del dato almacenado.
     */
    static class NodoDoble<T> {

        private T dato;
        private NodoDoble<T> anterior;
        private NodoDoble<T> siguiente;

        /**
         * Crea un nodo con el dato indicado y sin enlaces todavía.
         *
         * @param dato contenido del nodo.
         */
        public NodoDoble(T dato) {
            this.dato = dato;
            this.anterior = null;
            this.siguiente = null;
        }

        public T getDato() {
            return dato;
        }

        public NodoDoble<T> getAnterior() {
            return anterior;
        }

        public void setAnterior(NodoDoble<T> anterior) {
            this.anterior = anterior;
        }

        public NodoDoble<T> getSiguiente() {
            return siguiente;
        }

        public void setSiguiente(NodoDoble<T> siguiente) {
            this.siguiente = siguiente;
        }
    }

    // Extremos de la lista: head es el primero, tail el último. Ambos null si está vacía.
    private NodoDoble<T> head;
    private NodoDoble<T> tail;
    private int tamaño;

    /**
     * Crea una lista doblemente enlazada vacía.
     */
    public Ejercicio10() {
        this.head = null;
        this.tail = null;
        this.tamaño = 0;
    }

    /**
     * Indica si la lista no contiene elementos.
     *
     * @return true si la lista está vacía.
     */
    public boolean estaVacia() {
        return this.head == null;
    }

    /**
     * Devuelve la cantidad de elementos de la lista.
     *
     * @return tamaño actual.
     */
    public int getSize() {
        return this.tamaño;
    }

    /**
     * Inserta un elemento al inicio de la lista. O(1)
     *
     * @param dato elemento a insertar.
     */
    public void insertarAlInicio(T dato) {
        NodoDoble<T> nuevo = new NodoDoble<>(dato);
        if (estaVacia()) {
            // Caso borde: lista vacía, head y tail apuntan al mismo nodo.
            this.head = nuevo;
            this.tail = nuevo;
        } else {
            // Caso general: el nuevo se enlaza antes del actual head.
            nuevo.setSiguiente(this.head);
            this.head.setAnterior(nuevo);
            this.head = nuevo;
        }
        this.tamaño++;
    }

    /**
     * Inserta un elemento al final de la lista. O(1)
     *
     * @param dato elemento a insertar.
     */
    public void insertarAlFinal(T dato) {
        NodoDoble<T> nuevo = new NodoDoble<>(dato);
        if (estaVacia()) {
            // Caso borde: lista vacía, head y tail apuntan al mismo nodo.
            this.head = nuevo;
            this.tail = nuevo;
        } else {
            // Caso general: el nuevo se enlaza después del actual tail.
            nuevo.setAnterior(this.tail);
            this.tail.setSiguiente(nuevo);
            this.tail = nuevo;
        }
        this.tamaño++;
    }

    /**
     * Elimina la primera ocurrencia del elemento indicado (búsqueda por equals). O(n)
     * Maneja los casos especiales: lista vacía, eliminar head, eliminar tail y elemento inexistente.
     *
     * @param dato elemento a eliminar.
     * @return true si el elemento estaba y fue eliminado.
     */
    public boolean eliminar(T dato) {
        // Caso borde: lista vacía, no hay nada que eliminar.
        if (estaVacia()) {
            return false;
        }

        // Búsqueda lineal desde head hasta encontrar el dato (o llegar al final).
        NodoDoble<T> actual = this.head;
        while (actual != null && !actual.getDato().equals(dato)) {
            actual = actual.getSiguiente();
        }

        // Caso borde: el elemento no existe en la lista.
        if (actual == null) {
            return false;
        }

        NodoDoble<T> anterior = actual.getAnterior();
        NodoDoble<T> siguiente = actual.getSiguiente();

        if (anterior == null && siguiente == null) {
            // Caso especial: era el único elemento, la lista queda vacía.
            this.head = null;
            this.tail = null;
        } else if (anterior == null) {
            // Caso especial: se elimina el head; el nuevo head es su siguiente.
            this.head = siguiente;
            this.head.setAnterior(null);
        } else if (siguiente == null) {
            // Caso especial: se elimina el tail; el nuevo tail es su anterior.
            this.tail = anterior;
            this.tail.setSiguiente(null);
        } else {
            // Caso general: nodo intermedio; se "cosen" sus vecinos entre sí.
            anterior.setSiguiente(siguiente);
            siguiente.setAnterior(anterior);
        }

        // Se desconecta el nodo eliminado para no dejar referencias colgando.
        actual.setAnterior(null);
        actual.setSiguiente(null);
        this.tamaño--;
        return true;
    }

    /**
     * Imprime los elementos desde head hacia tail siguiendo las referencias 'siguiente'. O(n)
     */
    public void imprimirAdelante() {
        if (estaVacia()) {
            System.out.println("La lista está vacía.");
            return;
        }
        NodoDoble<T> actual = this.head;
        System.out.print("head <-> ");
        while (actual != null) {
            System.out.print("[" + actual.getDato() + "]");
            if (actual.getSiguiente() != null) {
                System.out.print(" <-> ");
            }
            actual = actual.getSiguiente();
        }
        System.out.println(" <-> null");
    }

    /**
     * Imprime los elementos desde tail hacia head siguiendo las referencias 'anterior'. O(n)
     */
    public void imprimirAtras() {
        if (estaVacia()) {
            System.out.println("La lista está vacía.");
            return;
        }
        NodoDoble<T> actual = this.tail;
        System.out.print("tail <-> ");
        while (actual != null) {
            System.out.print("[" + actual.getDato() + "]");
            if (actual.getAnterior() != null) {
                System.out.print(" <-> ");
            }
            actual = actual.getAnterior();
        }
        System.out.println(" <-> null");
    }

    /**
     * Método principal: demuestra inserciones y los casos especiales de eliminación (sin teclado).
     *
     * @param args argumentos de línea de comandos (no se usan).
     */
    public static void main(String[] args) {
        System.out.println("=== Lista doblemente enlazada genérica ===");

        // --- Caso borde: lista vacía ---
        Ejercicio10<Integer> lista = new Ejercicio10<>();
        System.out.println("\n--- Lista vacía ---");
        System.out.println("¿Está vacía? " + lista.estaVacia());
        lista.imprimirAdelante();
        lista.imprimirAtras();
        System.out.println("¿Eliminar 10 en lista vacía? " + lista.eliminar(10)); // false

        // --- Inserciones ---
        System.out.println("\n--- Insertando elementos ---");
        lista.insertarAlFinal(20);
        lista.insertarAlFinal(30);
        lista.insertarAlInicio(10);
        lista.insertarAlFinal(40);
        lista.insertarAlInicio(5);
        System.out.print("Adelante: ");
        lista.imprimirAdelante(); // 5 <-> [10] <-> [20] <-> [30] <-> [40]
        System.out.print("Atrás:    ");
        lista.imprimirAtras();    // 40 <-> [30] <-> [20] <-> [10] <-> [5]
        System.out.println("Tamaño: " + lista.getSize());

        // --- Eliminar head ---
        System.out.println("\n--- Eliminar el HEAD (5) ---");
        System.out.println("¿Eliminado? " + lista.eliminar(5)); // true
        System.out.print("Adelante: ");
        lista.imprimirAdelante(); // 10 <-> [20] <-> [30] <-> [40]
        System.out.print("Atrás:    ");
        lista.imprimirAtras();

        // --- Eliminar tail ---
        System.out.println("\n--- Eliminar el TAIL (40) ---");
        System.out.println("¿Eliminado? " + lista.eliminar(40)); // true
        System.out.print("Adelante: ");
        lista.imprimirAdelante(); // 10 <-> [20] <-> [30]
        System.out.print("Atrás:    ");
        lista.imprimirAtras();

        // --- Eliminar nodo intermedio ---
        System.out.println("\n--- Eliminar el intermedio (20) ---");
        System.out.println("¿Eliminado? " + lista.eliminar(20)); // true
        System.out.print("Adelante: ");
        lista.imprimirAdelante(); // 10 <-> [30]
        System.out.print("Atrás:    ");
        lista.imprimirAtras();
        System.out.println("Tamaño: " + lista.getSize());

        // --- Eliminar inexistente ---
        System.out.println("\n--- Eliminar elemento inexistente (999) ---");
        System.out.println("¿Eliminado? " + lista.eliminar(999)); // false
        System.out.print("Adelante: ");
        lista.imprimirAdelante(); // sin cambios

        // --- Eliminar hasta dejar la lista vacía ---
        System.out.println("\n--- Vaciar la lista completa ---");
        lista.eliminar(10);
        lista.eliminar(30);
        System.out.print("Adelante: ");
        lista.imprimirAdelante();
        System.out.print("Atrás:    ");
        lista.imprimirAtras();
        System.out.println("¿Está vacía? " + lista.estaVacia() + " | Tamaño: " + lista.getSize());

        // --- Demostración con genérico String ---
        System.out.println("\n--- Lista genérica de String ---");
        Ejercicio10<String> nombres = new Ejercicio10<>();
        nombres.insertarAlFinal("Luis");
        nombres.insertarAlFinal("Ana");
        nombres.insertarAlInicio("María");
        System.out.print("Adelante: ");
        nombres.imprimirAdelante();
        System.out.print("Atrás:    ");
        nombres.imprimirAtras();
        System.out.println("¿Eliminar \"Ana\"? " + nombres.eliminar("Ana")); // true
        System.out.print("Adelante: ");
        nombres.imprimirAdelante();
    }
}

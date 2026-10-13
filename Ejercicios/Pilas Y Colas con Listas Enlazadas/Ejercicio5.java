/*
Prompt inicial utilizado:
Implementá en Java un historial de navegación con pila enlazada de Strings, sin colecciones de la
 JDK ni Scanner/System.in.
La pila modela "volver atrás": el tope es la página actual y lo que queda debajo son las páginas
anteriores, porque la última visita es la que hay que recuperar primero (LIFO).
Operaciones: visitar(página), volver(), páginaActual(), imprimir historial.
Caso borde: volver sin página previa o consultar la actual sin haber visitado nada ->
IllegalArgumentException. Indicar complejidades. Ajustes posteriores: ninguno.
*/

/**
 * Ejercicio 5: Historial de navegación basado en una pila enlazada.
 *
 * <p>La pila representa el concepto de "volver atrás": cada visita apila una página y, al volver,
 * se desapila para recuperar la anterior (la última en entrar es la primera en salir).</p>
 */
public class Ejercicio5 {

    // ---------------------------------------------------------------------
    // Nodo de la lista enlazada simple (guarda URLs como String)
    // ---------------------------------------------------------------------
    static class Nodo {

        private String pagina;
        private Nodo siguiente;

        /**
         * Crea un nodo con la página indicada y sin enlace.
         *
         * @param pagina URL de la página a almacenar
         */
        public Nodo(String pagina) {
            this.pagina = pagina;
            this.siguiente = null;
        }

        public String getPagina() {
            return pagina;
        }

        public Nodo getSiguiente() {
            return siguiente;
        }

        public void setSiguiente(Nodo siguiente) {
            this.siguiente = siguiente;
        }
    }

    // ---------------------------------------------------------------------
    // Pila enlazada de páginas
    // ---------------------------------------------------------------------
    static class PilaEnlazada {

        private Nodo head; // TOPE = página actual; null si no hay historial

        public PilaEnlazada() {
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
         * Apila una página nueva en el tope.
         *
         * @param pagina URL a apilar
         * Complejidad: O(1)
         */
        public void apilar(String pagina) {
            Nodo nuevo = new Nodo(pagina);
            nuevo.setSiguiente(this.head);
            this.head = nuevo;
        }

        /**
         * Desapila y devuelve la página del tope.
         *
         * @return página que estaba en el tope
         * @throws IllegalArgumentException si la pila está vacía (caso borde)
         * Complejidad: O(1)
         */
        public String desapilar() {
            if (estaVacia()) {
                throw new IllegalArgumentException("El historial está vacío: no hay páginas que quitar.");
            }
            String pagina = this.head.getPagina();
            this.head = this.head.getSiguiente();
            return pagina;
        }

        /**
         * Consulta la página del tope sin desapilarla.
         *
         * @return página en el tope
         * @throws IllegalArgumentException si la pila está vacía (caso borde)
         * Complejidad: O(1)
         */
        public String tope() {
            if (estaVacia()) {
                throw new IllegalArgumentException("El historial está vacío: no hay página que consultar.");
            }
            return this.head.getPagina();
        }

        /**
         * Indica si quedan páginas debajo del tope (es decir, si existe una anterior).
         *
         * @return {@code true} si hay al menos dos páginas apiladas
         * Complejidad: O(1)
         */
        public boolean hayAnterior() {
            return this.head != null && this.head.getSiguiente() != null;
        }

        /**
         * Imprime el historial del tope (actual) hacia la base (más antigua).
         * Complejidad: O(n)
         */
        public void imprimir() {
            if (estaVacia()) {
                System.out.println("El historial está vacío.");
                return;
            }

            Nodo actual = this.head;
            System.out.print("actual -> ");
            while (actual != null) {
                System.out.print("[" + actual.getPagina() + "]");
                if (actual.getSiguiente() != null) {
                    System.out.print(" -> ");
                }
                actual = actual.getSiguiente();
            }
            System.out.println(" -> base");
        }
    }

    // ---------------------------------------------------------------------
    // Sistema de historial que usa la pila enlazada
    // ---------------------------------------------------------------------
    static class HistorialNavegacion {

        private final PilaEnlazada historial; // pila: tope = página actual

        public HistorialNavegacion() {
            this.historial = new PilaEnlazada();
        }

        /**
         * Registra la visita a una página: queda como página actual.
         *
         * @param pagina URL de la página visitada
         * Complejidad: O(1)
         */
        public void visitar(String pagina) {
            this.historial.apilar(pagina);
        }

        /**
         * Vuelve a la página anterior desapilando la actual.
         *
         * @return URL de la página a la que se volvió
         * @throws IllegalArgumentException si no hay página previa (caso borde)
         * Complejidad: O(1)
         */
        public String volver() {
            if (!this.historial.hayAnterior()) {
                throw new IllegalArgumentException("No hay página anterior: no se puede volver.");
            }
            this.historial.desapilar(); // descarta la página actual
            return this.historial.tope(); // la nueva actual es la anterior
        }

        /**
         * Consulta la página actual sin modificar el historial.
         *
         * @return URL de la página actual
         * @throws IllegalArgumentException si no se visitó ninguna página (caso borde)
         * Complejidad: O(1)
         */
        public String paginaActual() {
            if (this.historial.estaVacia()) {
                throw new IllegalArgumentException("No hay ninguna página visitada todavía.");
            }
            return this.historial.tope();
        }

        /**
         * Imprime todo el historial, de la actual a la más antigua.
         * Complejidad: O(n)
         */
        public void imprimirHistorial() {
            this.historial.imprimir();
        }
    }

    // ---------------------------------------------------------------------
    // Pruebas con datos fijos (sin entrada por teclado)
    // ---------------------------------------------------------------------
    public static void main(String[] args) {
        HistorialNavegacion nav = new HistorialNavegacion();

        // Caso borde: consultar la página actual sin haber visitado nada
        System.out.println("=== Caso borde: sin visitas ===");
        try {
            nav.paginaActual();
        } catch (IllegalArgumentException e) {
            System.out.println("paginaActual() -> " + e.getMessage());
        }

        System.out.println("\n=== Navegación ===");
        nav.visitar("google.com");
        nav.visitar("facultad.edu.ar/practico");
        nav.visitar("github.com/usuarios/lista");

        System.out.print("Historial: ");
        nav.imprimirHistorial(); // actual -> [github.com/...] -> [facultad.edu.ar/...] -> [google.com] -> base
        System.out.println("Página actual: " + nav.paginaActual()); // github.com/usuarios/lista

        System.out.println("\n=== Volviendo atrás (LIFO) ===");
        System.out.println("Volviste a: " + nav.volver()); // facultad.edu.ar/practico
        System.out.println("Volviste a: " + nav.volver()); // google.com
        System.out.print("Historial: ");
        nav.imprimirHistorial(); // actual -> [google.com] -> base

        // Caso borde: "volver" sin historial previo (solo queda la página actual)
        System.out.println("\n=== Caso borde: volver sin página previa ===");
        try {
            nav.volver();
        } catch (IllegalArgumentException e) {
            System.out.println("volver() -> " + e.getMessage());
        }

        // Seguir navegando después de volver: el historial sigue funcionando
        System.out.println("\n=== Nueva visita tras volver ===");
        nav.visitar("docs.oracle.com/en/java/");
        System.out.println("Página actual: " + nav.paginaActual()); // docs.oracle.com/en/java/
        System.out.print("Historial: ");
        nav.imprimirHistorial(); // actual -> [docs...] -> [google.com] -> base
    }
}

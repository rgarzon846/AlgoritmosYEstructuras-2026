/*
Prompt inicial (breve):

Implementá en Java una cola de impresión usando una lista enlazada construida desde cero (sin
colecciones de la JDK). Un TrabajoImpresion (clase estática anidada) tiene nombre del archivo,
cantidad de páginas y usuario que lo envió. La cola FIFO es la estructura natural porque una
impresora procesa los trabajos en el orden en que llegaron (el primero enviado es el primero
impreso). Operaciones: agregarTrabajo (entra al final), imprimirProximo (saca e informa el
frente), consultarProximoArchivo (mira el frente sin sacar), mostrarPendientes (recorre la
cola). Casos borde: imprimir/consultar con la cola vacía debe avisar, no fallar. Complejidades:
agregar/imprimir/consultar O(1), mostrar O(n). Datos de prueba fijos en main, sin Scanner.
Ajustes posteriores: ninguno.
*/

/**
 * Ejercicio 9 — Cola de impresión.
 * Cola FIFO implementada con lista enlazada desde cero: la impresora atiende en orden de llegada.
 */
public class Ejercicio9 {

    /**
     * Clase estática anidada que representa un trabajo enviado a la impresora.
     */
    static class TrabajoImpresion {

        private String nombreArchivo;
        private int cantidadPaginas;
        private String usuario;

        /**
         * Crea un trabajo de impresión con sus tres datos.
         *
         * @param nombreArchivo   nombre del archivo a imprimir.
         * @param cantidadPaginas cantidad de páginas que ocupa el documento.
         * @param usuario         nombre del usuario que envió el trabajo.
         */
        public TrabajoImpresion(String nombreArchivo, int cantidadPaginas, String usuario) {
            this.nombreArchivo = nombreArchivo;
            this.cantidadPaginas = cantidadPaginas;
            this.usuario = usuario;
        }

        public String getNombreArchivo() {
            return nombreArchivo;
        }

        public int getCantidadPaginas() {
            return cantidadPaginas;
        }

        public String getUsuario() {
            return usuario;
        }

        /**
         * Calcula un tiempo estimado de impresión (1 minuto por página, sin contar arranque).
         *
         * @return minutos estimados para el trabajo.
         */
        public int tiempoEstimadoMinutos() {
            return this.cantidadPaginas;
        }

        @Override
        public String toString() {
            return "\"" + nombreArchivo + "\" (" + cantidadPaginas + " pág.) enviado por " + usuario;
        }
    }

    /**
     * Nodo interno de la cola: guarda un trabajo y una referencia al siguiente.
     */
    static class NodoCola {

        private TrabajoImpresion trabajo;
        private NodoCola siguiente;

        /**
         * Crea un nodo con el trabajo indicado.
         *
         * @param trabajo trabajo a almacenar en el nodo.
         */
        public NodoCola(TrabajoImpresion trabajo) {
            this.trabajo = trabajo;
            this.siguiente = null;
        }

        public TrabajoImpresion getTrabajo() {
            return trabajo;
        }

        public NodoCola getSiguiente() {
            return siguiente;
        }

        public void setSiguiente(NodoCola siguiente) {
            this.siguiente = siguiente;
        }
    }

    /**
     * Cola de impresión: FIFO sobre lista enlazada propia.
     */
    static class ColaImpresion {

        private NodoCola frente;
        private NodoCola fin;
        private int tamaño;

        /**
         * Crea una cola vacía (sin trabajos pendientes).
         */
        public ColaImpresion() {
            this.frente = null;
            this.fin = null;
            this.tamaño = 0;
        }

        /**
         * Indica si no hay trabajos pendientes.
         *
         * @return true si la cola está vacía.
         */
        public boolean estaVacia() {
            return this.frente == null;
        }

        /**
         * Devuelve cuántos trabajos esperan en la cola.
         *
         * @return tamaño actual de la cola.
         */
        public int getTamaño() {
            return this.tamaño;
        }

        /**
         * Agrega un trabajo al final de la cola de la impresora. O(1)
         *
         * @param trabajo trabajo a encolar.
         */
        public void agregarTrabajo(TrabajoImpresion trabajo) {
            NodoCola nuevo = new NodoCola(trabajo);
            if (estaVacia()) {
                // Caso borde: cola vacía, frente y fin apuntan al mismo nodo nuevo.
                this.frente = nuevo;
                this.fin = nuevo;
            } else {
                // Caso general: se enlaza al final y se actualiza la referencia fin.
                this.fin.setSiguiente(nuevo);
                this.fin = nuevo;
            }
            this.tamaño++;
        }

        /**
         * "Imprime" el próximo trabajo: lo saca de la cola y lo devuelve. O(1)
         *
         * @return el trabajo que estaba primero en la cola, o null si no hay pendientes.
         */
        public TrabajoImpresion imprimirProximo() {
            if (estaVacia()) {
                return null; // Caso borde: no hay nada que imprimir.
            }
            TrabajoImpresion impreso = this.frente.getTrabajo();
            this.frente = this.frente.getSiguiente();
            if (this.frente == null) {
                // Caso borde: se imprimió el último trabajo, la cola queda vacía y fin se resetea.
                this.fin = null;
            }
            this.tamaño--;
            return impreso;
        }

        /**
         * Consulta el próximo archivo a imprimir sin sacarlo de la cola. O(1)
         *
         * @return el trabajo del frente, o null si la cola está vacía.
         */
        public TrabajoImpresion consultarProximoArchivo() {
            if (estaVacia()) {
                return null; // Caso borde: no hay pendientes.
            }
            return this.frente.getTrabajo();
        }

        /**
         * Muestra todos los trabajos pendientes en orden FIFO. O(n)
         */
        public void mostrarPendientes() {
            if (estaVacia()) {
                System.out.println("No hay trabajos pendientes: la cola de impresión está vacía.");
                return;
            }
            NodoCola actual = this.frente;
            int posicion = 1;
            while (actual != null) {
                System.out.println("  #" + posicion + " " + actual.getTrabajo());
                actual = actual.getSiguiente();
                posicion++;
            }
        }
    }

    /**
     * Método principal: simula una cola de impresión con trabajos fijos (sin teclado).
     *
     * @param args argumentos de línea de comandos (no se usan).
     */
    public static void main(String[] args) {
        ColaImpresion cola = new ColaImpresion();

        System.out.println("=== Cola de impresión (FIFO) ===");
        System.out.println("Cola vacía al inicio: " + cola.estaVacia());

        // Llegada de trabajos a la impresora (se respeta el orden de envío).
        cola.agregarTrabajo(new TrabajoImpresion("informe_final.docx", 12, "ana"));
        cola.agregarTrabajo(new TrabajoImpresion("resumen.pptx", 30, "luis"));
        cola.agregarTrabajo(new TrabajoImpresion("foto_playa.jpg", 1, "maria"));
        cola.agregarTrabajo(new TrabajoImpresion("contrato.pdf", 5, "carlos"));

        System.out.println("\n--- Trabajos pendientes (orden FIFO) ---");
        cola.mostrarPendientes();
        System.out.println("Total pendientes: " + cola.getTamaño());

        System.out.println("\n--- Próximo archivo a imprimir (sin sacarlo) ---");
        TrabajoImpresion proximo = cola.consultarProximoArchivo();
        System.out.println("Próximo: " + proximo);
        System.out.println("Sigue en la cola: " + (proximo != null ? "\"" + proximo.getNombreArchivo() + "\"" : "n/a")
                + " | Pendientes: " + cola.getTamaño());

        System.out.println("\n--- Imprimiendo trabajos ---");
        while (!cola.estaVacia()) {
            TrabajoImpresion impreso = cola.imprimirProximo();
            System.out.println("Imprimiendo " + impreso
                    + " (tiempo estimado: " + impreso.tiempoEstimadoMinutos() + " min)");
        }

        System.out.println("\n--- Cola después de imprimir todo ---");
        cola.mostrarPendientes(); // Caso borde: cola vacía.
        System.out.println("Total pendientes: " + cola.getTamaño());

        // Casos borde explícitos: imprimir y consultar sobre cola vacía.
        System.out.println("\n--- Casos borde (cola vacía) ---");
        System.out.println("imprimirProximo() devuelve: " + cola.imprimirProximo());
        System.out.println("consultarProximoArchivo() devuelve: " + cola.consultarProximoArchivo());

        // Se agrega un trabajo nuevo para verificar el reinicio tras vaciarse.
        cola.agregarTrabajo(new TrabajoImpresion("extra.pdf", 2, "elena"));
        System.out.println("\n--- Tras enviar un trabajo nuevo (extra.pdf) ---");
        cola.mostrarPendientes();
    }
}

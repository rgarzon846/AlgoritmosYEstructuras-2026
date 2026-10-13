/*
Prompt inicial (breve):

Implementá en Java una cola de atención de clientes de un banco usando una lista enlazada
construida desde cero (sin colecciones de la JDK). Un Cliente (clase estática anidada) tiene
nombre, número de turno y motivo de consulta. La cola se usa porque el banco atende en FIFO:
el primero en llegar es el primero en ser atendido, por eso la estructura debe guardar el orden
de llegada. Operaciones: agregarCliente, atenderProximo (saca al frente), consultarQuienSigue
(sin sacar), imprimirFila (recorre desde el frente). Casos borde: atender/consultar la cola
vacía debe avisar con mensaje, no lanzar error. Complejidades: encolar/atender/consultar O(1),
imprimir O(n). Datos de prueba fijos en main, sin Scanner.
Ajustes posteriores: ninguno.
*/

/**
 * Ejercicio 6 — Cola de atención de clientes (banco).
 * Cola FIFO implementada con lista enlazada desde cero (nodos propios, sin LinkedList de la JDK).
 */
public class Ejercicio6 {

    /**
     * Clase estática anidada que representa a un cliente del banco.
     * Cada cliente se identifica por nombre, turno (autorreferencial al orden de llegada)
     * y el motivo por el cual concurre.
     */
    static class Cliente {

        private String nombre;
        private int numeroTurno;
        private String motivoConsulta;

        /**
         * Crea un cliente nuevo con sus tres datos.
         *
         * @param nombre        nombre y apellido del cliente.
         * @param numeroTurno   número de turno que indica su orden de llegada.
         * @param motivoConsulta motivo de la consulta en el banco.
         */
        public Cliente(String nombre, int numeroTurno, String motivoConsulta) {
            this.nombre = nombre;
            this.numeroTurno = numeroTurno;
            this.motivoConsulta = motivoConsulta;
        }

        public String getNombre() {
            return nombre;
        }

        public int getNumeroTurno() {
            return numeroTurno;
        }

        public String getMotivoConsulta() {
            return motivoConsulta;
        }

        @Override
        public String toString() {
            return "Turno #" + numeroTurno + " | " + nombre + " | Motivo: " + motivoConsulta;
        }
    }

    /**
     * Nodo interno de la cola. Cada nodo guarda un Cliente y una referencia al siguiente.
     */
    static class NodoCola {

        private Cliente cliente;
        private NodoCola siguiente;

        /**
         * Crea un nodo con el cliente indicado y sin enlace siguiente.
         *
         * @param cliente cliente a almacenar en el nodo.
         */
        public NodoCola(Cliente cliente) {
            this.cliente = cliente;
            this.siguiente = null;
        }

        public Cliente getCliente() {
            return cliente;
        }

        public NodoCola getSiguiente() {
            return siguiente;
        }

        public void setSiguiente(NodoCola siguiente) {
            this.siguiente = siguiente;
        }
    }

    /**
     * Cola de atención de clientes implementada con lista enlazada.
     * Se apoya en dos referencias: frente (sale el próximo) y fin (entra el nuevo).
     */
    static class ColaClientes {

        private NodoCola frente;
        private NodoCola fin;
        private int tamaño;

        /**
         * Crea una cola vacía (frente y fin en null).
         */
        public ColaClientes() {
            this.frente = null;
            this.fin = null;
            this.tamaño = 0;
        }

        /**
         * Indica si la cola no tiene clientes esperando.
         *
         * @return true si la cola está vacía.
         */
        public boolean estaVacia() {
            return this.frente == null;
        }

        /**
         * Devuelve cuántos clientes esperan en la cola.
         *
         * @return tamaño actual de la cola.
         */
        public int getTamaño() {
            return this.tamaño;
        }

        /**
         * Agrega un cliente al final de la fila (fin de la cola). O(1)
         *
         * @param cliente cliente a encolar.
         */
        public void agregarCliente(Cliente cliente) {
            NodoCola nuevo = new NodoCola(cliente);
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
         * Atiende al próximo cliente: lo saca del frente de la cola y lo devuelve. O(1)
         *
         * @return el cliente que estaba primero en la fila, o null si la cola está vacía.
         */
        public Cliente atenderProximo() {
            if (estaVacia()) {
                return null; // Caso borde: nada que atender.
            }
            Cliente atendido = this.frente.getCliente();
            this.frente = this.frente.getSiguiente();
            if (this.frente == null) {
                // Caso borde: se sacó al último cliente, la cola queda vacía y fin se resetea.
                this.fin = null;
            }
            this.tamaño--;
            return atendido;
        }

        /**
         * Consulta quién sigue en la fila sin quitarlo. O(1)
         *
         * @return el cliente del frente, o null si la cola está vacía.
         */
        public Cliente consultarQuienSigue() {
            if (estaVacia()) {
                return null; // Caso borde: no hay nadie en la fila.
            }
            return this.frente.getCliente();
        }

        /**
         * Imprime la fila completa desde el frente hasta el fin. O(n)
         */
        public void imprimirFila() {
            if (estaVacia()) {
                System.out.println("La fila está vacía: no hay clientes esperando.");
                return;
            }
            NodoCola actual = this.frente;
            int posicion = 1;
            while (actual != null) {
                System.out.println("  Posición " + posicion + " -> " + actual.getCliente());
                actual = actual.getSiguiente();
                posicion++;
            }
        }
    }

    /**
     * Método principal: simula la atención en un banco con datos fijos (sin teclado).
     *
     * @param args argumentos de línea de comandos (no se usan).
     */
    public static void main(String[] args) {
        ColaClientes fila = new ColaClientes();

        System.out.println("=== Banco: cola de atención de clientes ===");
        System.out.println("Cola vacía al inicio: " + fila.estaVacia());

        // Llegada de clientes en orden (se respeta el orden de llegada).
        fila.agregarCliente(new Cliente("Ana Pérez", 1, "Depósito en efectivo"));
        fila.agregarCliente(new Cliente("Luis Gómez", 2, "Extracción"));
        fila.agregarCliente(new Cliente("María Ruiz", 3, "Tarjeta de crédito"));
        fila.agregarCliente(new Cliente("Carlos Díaz", 4, "Cambio de clave"));

        System.out.println("\n--- Fila completa (orden de llegada) ---");
        fila.imprimirFila();
        System.out.println("Clientes esperando: " + fila.getTamaño());

        System.out.println("\n--- Quién sigue? ---");
        Cliente proximo = fila.consultarQuienSigue();
        System.out.println("Sigue: " + proximo);
        System.out.println("(Consulta sin sacar: siguen esperando " + fila.getTamaño() + " clientes)");

        System.out.println("\n--- Atendiendo clientes ---");
        while (!fila.estaVacia()) {
            Cliente atendido = fila.atenderProximo();
            System.out.println("Atendiendo a: " + atendido);
        }

        System.out.println("\n--- Fila después de atender a todos ---");
        fila.imprimirFila(); // Caso borde: cola vacía.
        System.out.println("Clientes esperando: " + fila.getTamaño());

        // Casos borde explícitos: atender y consultar sobre cola vacía.
        System.out.println("\n--- Casos borde (cola vacía) ---");
        System.out.println("atenderProximo() devuelve: " + fila.atenderProximo());
        System.out.println("consultarQuienSigue() devuelve: " + fila.consultarQuienSigue());

        // Se vuelve a encolar para verificar que la cola sigue funcionando tras vaciarse.
        fila.agregarCliente(new Cliente("Elena Sosa", 5, "Consulta de saldo"));
        System.out.println("\n--- Tras volver a encolar a Elena ---");
        fila.imprimirFila();
    }
}

package logico; //porque no hace commit

public class Arista {
    private Nodo nodoSalida; // Nodo de salida de la arista.
    private Nodo nodoLlegada; // Nodo de llegada de la arista.
    private int peso; // Peso de la arista 

    // Constructor de la clase Arista que inicializa sus atributos
    public Arista(Nodo nodoSalida, Nodo nodoLlegada, int peso) {
        super();
        this.nodoSalida = nodoSalida;
        this.nodoLlegada = nodoLlegada;
        this.peso = peso;
    }

    // Métodos para acceder y modificar el nodo de salida
    public Nodo getNodoSalida() {
        return nodoSalida;
    }

    public void setNodoSalida(Nodo nodoSalida) {
        this.nodoSalida = nodoSalida;
    }

    // Métodos para acceder y modificar el nodo de llegada
    public Nodo getNodoLlegada() {
        return nodoLlegada;
    }

    public void setNodoLlegada(Nodo nodoLlegada) {
        this.nodoLlegada = nodoLlegada;
    }

    // Métodos para acceder y modificar el peso de la arista
    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }
}

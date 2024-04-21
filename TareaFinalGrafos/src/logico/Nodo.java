package logico;

public class Nodo {
    private String nombreNodo; // Nombre del nodo

    // Constructor de la clase Nodo que inicializa el nombre del nodo
    public Nodo(String nombreNodo) {
        super();
        this.nombreNodo = nombreNodo;
    }

    // Método para obtener el nombre del nodo
    public String getNombreNodo() {
        return nombreNodo;
    }

    // Método para establecer el nombre del nodo
    public void setNombreNodo(String nombreNodo) {
        this.nombreNodo = nombreNodo;
    }
}

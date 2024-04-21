package logico;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Comparator;

public class Grafo {
    private ArrayList<Nodo> misNodos; // Lista de nodos en el grafo
    private ArrayList<Arista> misAristas; // Lista de aristas en el grafo
    private int cantAristas; // Cantidad total de aristas en el grafo
    private int cantNodos; // Cantidad total de nodos en el grafo
    private static Grafo grafo; // Instancia única del grafo

    // Constructor de la clase Grafo
    public Grafo() {
        super();
        this.misNodos = new ArrayList<Nodo>();
        this.misAristas = new ArrayList<Arista>();
        this.cantAristas = 0;
        this.cantNodos = 0;
    }

    // Método estático para obtener la instancia única del grafo
    public static Grafo getGrafo() {
        if(grafo == null) {
            grafo = new Grafo();
        }
        return grafo;
    }

    // Métodos getter y setter para la lista de nodos
    public ArrayList<Nodo> getMisNodos() {
        return misNodos;
    }

    public void setMisNodos(ArrayList<Nodo> misNodos) {
        this.misNodos = misNodos;
    }

    // Métodos getter y setter para la lista de aristas
    public ArrayList<Arista> getMisAristas() {
        return misAristas;
    }

    public void setMisAristas(ArrayList<Arista> misAristas) {
        this.misAristas = misAristas;
    }

    // Métodos getter para la cantidad de aristas y nodos en el grafo
    public int getCantAristas() {
        return cantAristas;
    }

    public int getCantNodos() {
        return cantNodos;
    }

    // Método para agregar un nodo al grafo
    public void addNodo(Nodo nodoAgregar) {
        misNodos.add(nodoAgregar);
        cantNodos++;
    }

    // Método para agregar una arista al grafo
    public void addArista(Nodo nodoSalida, Nodo nodoLlegada, int peso) {
        Arista aristaAgregar = new Arista(nodoSalida, nodoLlegada, peso);
        misAristas.add(aristaAgregar);
        cantAristas++;
    }

    // Método para eliminar un nodo del grafo
    public void deleteNodo(Nodo nodoEliminar) {
        misNodos.remove(nodoEliminar); // Eliminar el nodo de la lista de nodos

        // Eliminar las aristas que tienen al nodo como salida o llegada
        Iterator<Arista> iteradorAristas = misAristas.iterator();
        while (iteradorAristas.hasNext()) {
            Arista arista = iteradorAristas.next();
            if (arista.getNodoLlegada() == nodoEliminar || arista.getNodoSalida() == nodoEliminar) {
                iteradorAristas.remove();
            }
        }
    }

    // Método para eliminar una arista del grafo
    public void deleteArista(Arista aristaEliminar) {
        misAristas.remove(aristaEliminar); // Eliminar la arista de la lista de aristas
    }

    // Método para editar el nombre de un nodo en el grafo
    public void editarNodo(String nombreNodo, String nuevoNombre) {
        for (Nodo nodo : misNodos) {
            if (nombreNodo.equalsIgnoreCase(nodo.getNombreNodo())) {
                nodo.setNombreNodo(nuevoNombre);
                return;
            }
        }
        System.out.println("El nodo no se encontró en el grafo.");
    }

    // Método para editar los atributos de una arista en el grafo
    public void editarArista(Arista aristaEditar, Nodo nodoSalidaNuevo, Nodo nodoLlegadaNuevo, int pesoNuevo) {
        for (Arista arista : misAristas) {
            if(arista.equals(aristaEditar)) {
                arista.setNodoSalida(nodoSalidaNuevo);
                arista.setNodoLlegada(nodoLlegadaNuevo);
                arista.setPeso(pesoNuevo);
                return;
            }
        }
    }

    // Método para encontrar el camino más corto entre dos nodos en el grafo
    public String caminoCorto(Nodo nodoInicio, Nodo nodoFinal) {
        // Inicializar estructuras de datos
        Map<Nodo, Integer> distancias = new HashMap<>();
        Map<Nodo, Nodo> previos = new HashMap<>();
        PriorityQueue<NodoPrioridad> colaPrioridad = new PriorityQueue<>();

        // Inicializar distancias y previos
        for (Nodo nodo : misNodos) {
            distancias.put(nodo, Integer.MAX_VALUE);
            previos.put(nodo, null);
        }
        distancias.put(nodoInicio, 0); // La distancia al nodo de inicio es 0

        // Agregar el nodo de inicio a la cola de prioridad
        colaPrioridad.offer(new NodoPrioridad(nodoInicio, 0));

        while (!colaPrioridad.isEmpty()) {
            // Obtener el nodo con la distancia mínima de la cola de prioridad
            NodoPrioridad nodoActual = colaPrioridad.poll();

            // Si llegamos al nodo final, construir y devolver el camino más corto
            if (nodoActual.getNodo().equals(nodoFinal)) {
                return construirCamino(previos, nodoFinal);
            }

            // Explorar los vecinos del nodo actual
            for (Arista arista : misAristas) {
                if (arista.getNodoSalida().equals(nodoActual.getNodo())) {
                    Nodo vecino = arista.getNodoLlegada();
                    int distanciaVecino = arista.getPeso() + nodoActual.getDistancia();

                    // Actualizar distancias y previos si se encuentra un camino más corto
                    if (distanciaVecino < distancias.get(vecino)) {
                        distancias.put(vecino, distanciaVecino);
                        previos.put(vecino, nodoActual.getNodo());
                        colaPrioridad.offer(new NodoPrioridad(vecino, distanciaVecino));
                    }
                }
            }
        }

        // Si no se encontró un camino hacia el nodo final, devolver un mensaje
        return "No se encontró un camino entre los nodos especificados.";
    }

    // Método para construir el camino más corto a partir de los previos encontrados
    private String construirCamino(Map<Nodo, Nodo> previos, Nodo nodoFinal) {
        List<Nodo> camino = new ArrayList<>();
        Nodo nodoActual = nodoFinal;

        // Reconstruir el camino desde el nodo final hasta el nodo de inicio
        while (nodoActual != null) {
            camino.add(0, nodoActual);
            nodoActual = previos.get(nodoActual);
        }

        // Construir la representación del camino como una cadena de texto
        StringBuilder caminoString = new StringBuilder();
        for (Nodo nodo : camino) {
            caminoString.append(nodo.getNombreNodo()).append(" -> ");
        }
        caminoString.delete(caminoString.length() - 4, caminoString.length()); // Eliminar la flecha al final

        return caminoString.toString();
    }

    // Clase auxiliar para la cola de prioridad
    private class NodoPrioridad implements Comparable<NodoPrioridad> {
        private Nodo nodo; // Nodo
        private int distancia; // Distancia desde el nodo de inicio hasta este nodo

        // Constructor de la clase
        public NodoPrioridad(Nodo nodo, int distancia) {
            this.nodo = nodo;
            this.distancia = distancia;
        }

        // Métodos getter para el nodo y la distancia
        public Nodo getNodo() {
            return nodo;
        }

        public int getDistancia() {
            return distancia;
        }

        // Método para comparar nodos basado en su distancia
        @Override
        public int compareTo(NodoPrioridad otro) {
            return Integer.compare(this.distancia, otro.distancia);
        }
    }

	// Método para encontrar un árbol de expansión mínima utilizando el algoritmo de Prim
	public ArrayList<Arista> arbolExpansionMinimaPrim() {
	    ArrayList<Arista> arbolExpansion = new ArrayList<>();
	    Set<Nodo> nodosProcesados = new HashSet<>();
	    PriorityQueue<Arista> colaAristas = new PriorityQueue<>(Comparator.comparingInt(Arista::getPeso));
	
	    // Seleccionar un nodo inicial arbitrario
	    Nodo nodoInicial = misNodos.get(0);
	    nodosProcesados.add(nodoInicial);
	
	    // Agregar todas las aristas que tienen al nodo inicial como salida o llegada a la cola de prioridad
	    for (Arista arista : misAristas) {
	        if (arista.getNodoSalida().equals(nodoInicial) || arista.getNodoLlegada().equals(nodoInicial)) {
	            colaAristas.offer(arista);
	        }
	    }
	
	    // Iterar hasta que se hayan procesado todos los nodos o se haya formado el árbol
	    while (!colaAristas.isEmpty() && arbolExpansion.size() < misNodos.size() - 1) {
	        Arista aristaActual = colaAristas.poll();
	        Nodo nodoA = aristaActual.getNodoSalida();
	        Nodo nodoB = aristaActual.getNodoLlegada();
	
	        // Verificar si ambos nodos están procesados
	        if (nodosProcesados.contains(nodoA) && nodosProcesados.contains(nodoB)) {
	            continue; // Ignorar la arista actual
	        }
	
	        // Agregar la arista al árbol de expansión mínima
	        arbolExpansion.add(aristaActual);
	
	        // Marcar los nodos como procesados y agregar las nuevas aristas a la cola de prioridad
	        nodosProcesados.add(nodoA);
	        nodosProcesados.add(nodoB);
	        for (Arista arista : misAristas) {
	            Nodo nodoSalida = arista.getNodoSalida();
	            Nodo nodoLlegada = arista.getNodoLlegada();
	            if (nodosProcesados.contains(nodoSalida) && !nodosProcesados.contains(nodoLlegada)) {
	                colaAristas.offer(arista);
	            } else if (!nodosProcesados.contains(nodoSalida) && nodosProcesados.contains(nodoLlegada)) {
	                colaAristas.offer(arista);
	            }
	        }
	    }
	
	    return arbolExpansion;
	}

	// Método para encontrar un árbol de expansión mínima utilizando el algoritmo de Kruskal
	public ArrayList<Arista> arbolExpansionMinimaKruskal() {
	    ArrayList<Arista> arbolExpansion = new ArrayList<>();
	    Map<Nodo, Nodo> representantes = new HashMap<>();
	    ArrayList<Arista> aristasOrdenadas = new ArrayList<>(misAristas);
	
	    // Ordenar las aristas por peso de menor a mayor
	    aristasOrdenadas.sort(Comparator.comparingInt(Arista::getPeso));
	
	    // Inicializar los representantes de cada nodo
	    for (Nodo nodo : misNodos) {
	        representantes.put(nodo, nodo);
	    }
	
	    // Iterar sobre las aristas ordenadas y agregar las que no formen ciclos al árbol de expansión mínima
	    for (Arista arista : aristasOrdenadas) {
	        Nodo nodoA = arista.getNodoSalida();
	        Nodo nodoB = arista.getNodoLlegada();
	        Nodo representanteA = encontrarRepresentante(nodoA, representantes);
	        Nodo representanteB = encontrarRepresentante(nodoB, representantes);
	
	        // Verificar si los nodos pertenecen a diferentes conjuntos
	        if (!representanteA.equals(representanteB)) {
	            // Agregar la arista al árbol de expansión mínima
	            arbolExpansion.add(arista);
	
	            // Unir los conjuntos de los nodos A y B
	            unirConjuntos(representanteA, representanteB, representantes);
	        }
	    }
	
	    return arbolExpansion;
	}

	// Método auxiliar para encontrar el representante de un conjunto dado un nodo
	private Nodo encontrarRepresentante(Nodo nodo, Map<Nodo, Nodo> representantes) {
	    Nodo representante = representantes.get(nodo);
	    if (!representante.equals(nodo)) {
	        representante = encontrarRepresentante(representante, representantes);
	        representantes.put(nodo, representante); // Compresión de ruta
	    }
	    return representante;
	}
	
	// Método auxiliar para unir dos conjuntos dados sus representantes
	private void unirConjuntos(Nodo nodoA, Nodo nodoB, Map<Nodo, Nodo> representantes) {
	    for (Map.Entry<Nodo, Nodo> entry : representantes.entrySet()) {
	        if (entry.getValue().equals(nodoB)) {
	            entry.setValue(nodoA);
	        }
	    }
	}

	// Continuación del código de la clase Grafo...

    // Método para ejecutar el algoritmo de Floyd-Warshall y encontrar todos los caminos más cortos entre nodos
    public void floydWarshall() {
        int n = misNodos.size();
        int[][] distancias = new int[n][n]; // Matriz de distancias
        int[][] siguientes = new int[n][n]; // Matriz de siguientes nodos en el camino más corto

        // Inicializar las matrices de distancias y siguientes
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                distancias[i][j] = Integer.MAX_VALUE;
                siguientes[i][j] = -1;
            }
            distancias[i][i] = 0; // La distancia de un nodo a sí mismo es 0
        }

        // Asignar pesos iniciales a las aristas en las matrices de distancias y siguientes
        for (Arista arista : misAristas) {
            int origen = misNodos.indexOf(arista.getNodoSalida());
            int destino = misNodos.indexOf(arista.getNodoLlegada());
            if (origen != -1 && destino != -1) {
                distancias[origen][destino] = arista.getPeso();
                siguientes[origen][destino] = destino;
            } else {
                System.out.println("Advertencia: Se encontró una arista que se refiere a nodos inexistentes.");
            }
        }

        // Ejecutar el algoritmo de Floyd-Warshall
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (distancias[i][k] != Integer.MAX_VALUE && distancias[k][j] != Integer.MAX_VALUE
                            && distancias[i][k] + distancias[k][j] < distancias[i][j]) {
                        distancias[i][j] = distancias[i][k] + distancias[k][j];
                        siguientes[i][j] = siguientes[i][k];
                    }
                }
            }
        }

        // Imprimir resultados de los caminos más cortos encontrados
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j && distancias[i][j] != Integer.MAX_VALUE) {
                    System.out.println("Camino más corto entre " + misNodos.get(i).getNombreNodo() + " y " +
                            misNodos.get(j).getNombreNodo() + ": " + distancias[i][j]);
                    System.out.print("Ruta: " + misNodos.get(i).getNombreNodo());
                    imprimirRuta(i, j, siguientes);
                    System.out.println();
                }
            }
        }
    }

    // Método auxiliar para imprimir la ruta del camino más corto
    private void imprimirRuta(int origen, int destino, int[][] siguientes) {
        if (siguientes[origen][destino] == -1) {
            System.out.print(" (No hay ruta)");
            return;
        }

        int siguiente = destino;
        System.out.print("Ruta: " + misNodos.get(origen).getNombreNodo());

        while (siguiente != origen) {
            int previo = siguientes[origen][siguiente];
            if (previo == -1) {
                System.out.print(" (No hay ruta)");
                return;
            }
            System.out.print(" -> " + misNodos.get(siguiente).getNombreNodo());
            siguiente = previo;
        }
    }
}


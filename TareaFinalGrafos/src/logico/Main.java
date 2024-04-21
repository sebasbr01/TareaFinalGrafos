package logico;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static Grafo grafo = Grafo.getGrafo(); // Instancia única del grafo
    private static Scanner scanner = new Scanner(System.in); // Scanner para entrada del usuario

    // Método principal que ejecuta el programa
    public static void main(String[] args) {
        boolean salir = false;

        // Ciclo principal del menú
        while (!salir) {
            mostrarMenu();// Mostrar opciones del menú
            int opcion = obtenerOpcionValida("Ingrese una opción válida: "); // Obtener opción del usuario

         // Switch para manejar las opciones del menú
            switch (opcion) {
                case 1:
                    gestionarUbicaciones(); // Opción para gestionar ubicaciones
                    break;
                case 2:
                    calcularRutaMasCorta(); // Opción para calcular la ruta más corta
                    break;
                case 3:
                    calcularArbolExpansionPrim(); // Opción para calcular el árbol de expansión mínima (Prim)
                    break;
                case 4:
                    calcularArbolExpansionKruskal(); // Opción para calcular el árbol de expansión mínima (Kruskal)
                    break;
                case 5:
                    calcularCaminoMasCortoFloydWarshall(); // Opción para calcular el camino más corto (Floyd-Warshall)
                    break;
                case 6:
                    salir = true; // Opción para salir del programa
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
    }

 // Método para mostrar el menú principal
    private static void mostrarMenu() {
        System.out.println("\nSeleccione una opción:");
        System.out.println("1. Gestionar ubicaciones");
        System.out.println("2. Calcular ruta más corta (Dijkstra)");
        System.out.println("3. Calcular árbol de expansión mínima (Prim)");
        System.out.println("4. Calcular árbol de expansión mínima (Kruskal)");
        System.out.println("5. Calcular camino más corto entre todas las ubicaciones (Floyd-Warshall)");
        System.out.println("6. Salir");
    }

    // Método para gestionar las ubicaciones (agregar, editar, eliminar, etc.
    private static void gestionarUbicaciones() {
        boolean salir = false;

        while (!salir) {
            System.out.println("\nGestión de ubicaciones:");
            System.out.println("1. Agregar ubicación");
            System.out.println("2. Editar ubicación");
            System.out.println("3. Eliminar ubicación");
            System.out.println("4. Agregar conexión entre ubicaciones");
            System.out.println("5. Eliminar conexión entre ubicaciones");
            System.out.println("6. Volver al menú principal");
            int opcion = obtenerOpcionValida("Ingrese una opción válida: ");

            switch (opcion) {
                case 1:
                    agregarUbicacion(); // Opción para agregar una nueva ubicación
                    break;
                case 2:
                    editarUbicacion(); // Opción para editar una ubicación existente
                    break;
                case 3:
                    eliminarUbicacion(); // Opción para eliminar una ubicación existente
                    break;
                case 4:
                    agregarConexion(); // Opción para agregar una conexión entre ubicaciones
                    break;
                case 5:
                    eliminarConexion(); // Opción para eliminar una conexión entre ubicaciones
                    break;
                case 6:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
    }

    // Método para calcular la ruta más corta entre dos ubicaciones utilizando Dijkstr
    private static void calcularRutaMasCorta() {
        String nombreInicio = obtenerNombreUbicacionValido("Ingrese el nombre de la ubicación de inicio:");
        String nombreDestino = obtenerNombreUbicacionValido("Ingrese el nombre de la ubicación de destino:");

        Nodo nodoInicio = null;
        Nodo nodoDestino = null;

        for (Nodo nodo : grafo.getMisNodos()) {
            if (nodo.getNombreNodo().equalsIgnoreCase(nombreInicio)) {
                nodoInicio = nodo;
            }
            if (nodo.getNombreNodo().equalsIgnoreCase(nombreDestino)) {
                nodoDestino = nodo;
            }
        }

        if (nodoInicio != null && nodoDestino != null) {
            String caminoCorto = grafo.caminoCorto(nodoInicio, nodoDestino);
            System.out.println("Camino más corto: " + caminoCorto);
        } else {
            System.out.println("No se encontraron las ubicaciones especificadas.");
        }
    }

    // Método para calcular el árbol de expansión mínima utilizando el algoritmo de Prim
    private static void calcularArbolExpansionPrim() {
        System.out.println("Árbol de expansión mínima (Prim):");
        ArrayList<Arista> arbolExpansion = grafo.arbolExpansionMinimaPrim();
        if (arbolExpansion.isEmpty()) {
            System.out.println("No se pudo calcular el árbol de expansión mínima.");
        } else {
            for (Arista arista : arbolExpansion) {
                System.out.println("Conexión: " + arista.getNodoSalida().getNombreNodo() + " -> " +
                        arista.getNodoLlegada().getNombreNodo() + ", Peso: " + arista.getPeso());
            }
        }
    }

    // Método para calcular el árbol de expansión mínima utilizando el algoritmo de Kruskal
    private static void calcularArbolExpansionKruskal() {
        System.out.println("Árbol de expansión mínima (Kruskal):");
        ArrayList<Arista> arbolExpansion = grafo.arbolExpansionMinimaKruskal();
        if (arbolExpansion.isEmpty()) {
            System.out.println("No se pudo calcular el árbol de expansión mínima.");
        } else {
            for (Arista arista : arbolExpansion) {
                System.out.println("Conexión: " + arista.getNodoSalida().getNombreNodo() + " -> " +
                        arista.getNodoLlegada().getNombreNodo() + ", Peso: " + arista.getPeso());
            }
        }
    }

    // Método para calcular el camino más corto entre todas las ubicaciones utilizando Floyd-Warshall
    private static void calcularCaminoMasCortoFloydWarshall() {
        grafo.floydWarshall();
    }

    // Método para agregar una nueva ubicación al grafo
    private static void agregarUbicacion() {
        String nombre = obtenerNombreUbicacionValido("Ingrese el nombre de la nueva ubicación:");
        Nodo nuevaUbicacion = new Nodo(nombre);
        grafo.addNodo(nuevaUbicacion);
        System.out.println("Ubicación agregada correctamente.");
    }

    // Método para editar una ubicación existente en el grafo
    private static void editarUbicacion() {
        String nombreAntiguo = obtenerNombreUbicacionValido("Ingrese el nombre de la ubicación a editar:");
        String nombreNuevo = obtenerNombreUbicacionValido("Ingrese el nuevo nombre de la ubicación:");
        grafo.editarNodo(nombreAntiguo, nombreNuevo);
        System.out.println("Ubicación editada correctamente.");
    }

    // Método para eliminar una ubicación del grafo
    private static void eliminarUbicacion() {
        String nombre = obtenerNombreUbicacionValido("Ingrese el nombre de la ubicación a eliminar:");
        Nodo nodoEliminar = null;
        for (Nodo nodo : grafo.getMisNodos()) {
            if (nodo.getNombreNodo().equalsIgnoreCase(nombre)) {
                nodoEliminar = nodo;
                break;
            }
        }
        if (nodoEliminar != null) {
            grafo.deleteNodo(nodoEliminar);
            System.out.println("Ubicación eliminada correctamente.");
        } else {
            System.out.println("No se encontró la ubicación especificada.");
        }
    }

    // Método para agregar una conexión entre dos ubicaciones en el grafo
    private static void agregarConexion() {
        String nombreSalida = obtenerNombreUbicacionValido("Ingrese el nombre de la ubicación de salida:");
        String nombreLlegada = obtenerNombreUbicacionValido("Ingrese el nombre de la ubicación de llegada:");
        int peso = obtenerPesoValido("Ingrese el peso (distancia/tiempo) de la conexión:");

        Nodo nodoSalida = null;
        Nodo nodoLlegada = null;

        for (Nodo nodo : grafo.getMisNodos()) {
            if (nodo.getNombreNodo().equalsIgnoreCase(nombreSalida)) {
                nodoSalida = nodo;
            }
            if (nodo.getNombreNodo().equalsIgnoreCase(nombreLlegada)) {
                nodoLlegada = nodo;
            }
        }

        if (nodoSalida != null && nodoLlegada != null) {
            grafo.addArista(nodoSalida, nodoLlegada, peso);
            System.out.println("Conexión agregada correctamente.");
        } else {
            System.out.println("No se encontraron las ubicaciones especificadas.");
        }
    }

    // Método para eliminar una conexión entre dos ubicaciones en el grafo
    private static void eliminarConexion() {
        String nombreSalida = obtenerNombreUbicacionValido("Ingrese el nombre de la ubicación de salida:");
        String nombreLlegada = obtenerNombreUbicacionValido("Ingrese el nombre de la ubicación de llegada:");

        Nodo nodoSalida = null;
        Nodo nodoLlegada = null;

        for (Nodo nodo : grafo.getMisNodos()) {
            if (nodo.getNombreNodo().equalsIgnoreCase(nombreSalida)) {
                nodoSalida = nodo;
            }
            if (nodo.getNombreNodo().equalsIgnoreCase(nombreLlegada)) {
                nodoLlegada = nodo;
            }
        }

        if (nodoSalida != null && nodoLlegada != null) {
            for (Arista arista : grafo.getMisAristas()) {
                if (arista.getNodoSalida().equals(nodoSalida) && arista.getNodoLlegada().equals(nodoLlegada)) {
                    grafo.deleteArista(arista);
                    System.out.println("Conexión eliminada correctamente.");
                    return;
                }
            }
            System.out.println("No se encontró la conexión especificada.");
        } else {
            System.out.println("No se encontraron las ubicaciones especificadas.");
        }
    }

    // Método para obtener una opción válida del usuario
    private static int obtenerOpcionValida(String mensaje) {
        int opcion;
        while (true) {
            try {
                System.out.print(mensaje);
                opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea
                if (opcion >= 1 && opcion <= 6) {
                    break;
                } else {
                    System.out.println("Opción inválida. Intente de nuevo.");
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida. Intente de nuevo.");
                scanner.nextLine(); // Consumir la entrada incorrecta
            }
        }
        return opcion;
    }
    
    // Método para obtener el nombre de una ubicación válida del usuario
    private static String obtenerNombreUbicacionValido(String mensaje) {
        String nombre;
        while (true) {
            System.out.print(mensaje);
            nombre = scanner.nextLine();
            if (!nombre.isEmpty()) {
                break;
            } else {
                System.out.println("El nombre no puede estar vacío. Intente de nuevo.");
            }
        }
        return nombre;
    }

    // Método para obtener un peso válido del usuari
    private static int obtenerPesoValido(String mensaje) {
        int peso;
        while (true) {
            try {
                System.out.print(mensaje);
                peso = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea
                if (peso > 0) {
                    break;
                } else {
                    System.out.println("El peso debe ser un número positivo. Intente de nuevo.");
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida. Intente de nuevo.");
                scanner.nextLine(); // Consumir la entrada incorrecta
            }
        }
        return peso;
    }
}
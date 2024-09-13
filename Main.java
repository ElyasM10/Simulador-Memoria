/* 
import Clases.Particion;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
*/
import Clases.interfazGrafica;
import  javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new interfazGrafica().setVisible(true));
    }

        /*
        Scanner scanner = new Scanner(System.in);
        List<Proceso> listaProcesos = null;
        boolean archivoCargadoExitosamente = false;

        // Solicitar archivo hasta que sea cargado correctamente
        while (!archivoCargadoExitosamente) {
            System.out.println("Ingrese el nombre del archivo de texto (por ejemplo, tanda1.txt): ");
            String nombreArchivo = scanner.nextLine();

            imprimirContenidoArchivo(nombreArchivo);
            listaProcesos = cargarProcesosDesdeArchivo(nombreArchivo);

            if (listaProcesos != null && !listaProcesos.isEmpty()) {
                archivoCargadoExitosamente = true;
            } else {
                System.out.println("No se pudo cargar el archivo. Por favor, inténtelo de nuevo.");
            }
        }

        // Crear el simulador y asignar los procesos cargados
        Simulador simulador = new Simulador();
        simulador.getProcesos().addAll(listaProcesos);

        System.out.println("Ingrese el tamaño de la memoria física disponible para usuarios (en unidades): ");
        int tamanioMemoria = scanner.nextInt();
        scanner.nextLine(); // salto de línea

        System.out.println("Seleccione la política de asignación (firstfit, bestfit, nextfit, worstfit): ");
        String politicaSeleccionada = scanner.nextLine().toLowerCase();

        switch (politicaSeleccionada) {
            case "firstfit" -> simulador.setEstrategiaActual(Particion.EstrategiaAsignacion.FIRST_FIT);
            case "bestfit" -> simulador.setEstrategiaActual(Particion.EstrategiaAsignacion.BEST_FIT);
            case "nextfit" -> simulador.setEstrategiaActual(Particion.EstrategiaAsignacion.NEXT_FIT);
            case "worstfit" -> simulador.setEstrategiaActual(Particion.EstrategiaAsignacion.WORST_FIT);
            default -> {
                System.out.println("Política no reconocida. Se usará FIRST_FIT por defecto.");
                simulador.setEstrategiaActual(Particion.EstrategiaAsignacion.FIRST_FIT);
            }
        }

        System.out.println("Ingrese el tiempo de selección de partición (en unidades de tiempo): ");
        int tiempoSeleccion = scanner.nextInt();

        System.out.println("Ingrese el tiempo de carga promedio (en unidades de tiempo): ");
        int tiempoCargaPromedio = scanner.nextInt();

        System.out.println("Ingrese el tiempo de liberación de partición (en unidades de tiempo): ");
        int tiempoLiberacion = scanner.nextInt();

        simulador.setTamanioMemoria(tamanioMemoria);
        simulador.setTiempoSeleccion(tiempoSeleccion);
        simulador.setTiempoCargaPromedio(tiempoCargaPromedio);
        simulador.setTiempoLiberacion(tiempoLiberacion);

        simulador.simular();
    }

    private static void imprimirContenidoArchivo(String nombreArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            System.out.println("Contenido del archivo " + nombreArchivo + ":");
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    private static List<Proceso> cargarProcesosDesdeArchivo(String nombreArchivo) {
        List<Proceso> procesos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) {
                    continue;
                }

                String[] datos = linea.split(",");
                if (datos.length != 5) {
                    System.out.println("Línea en el archivo no válida: " + linea);
                    continue;
                }

                try {
                    int id = Integer.parseInt(datos[0].trim());
                    String nombre = datos[1].trim();
                    int memoriaRequerida = Integer.parseInt(datos[2].trim());
                    int duracion = Integer.parseInt(datos[3].trim());
                    int instanteArribo = Integer.parseInt(datos[4].trim());

                    Proceso proceso = new Proceso(id, nombre, memoriaRequerida, duracion, instanteArribo);
                    procesos.add(proceso);
                } catch (NumberFormatException e) {
                    System.out.println("Error de formato en la línea: " + linea + " - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            return null; // Retorna null en caso de error de lectura
        }
        return procesos;
    }
    */
}

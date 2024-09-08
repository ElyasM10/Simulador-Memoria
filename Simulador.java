import Clases.Particion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.*;


public class Simulador {
    private List<Particion> particiones;
    private List<Proceso> procesos;
    private Particion.EstrategiaAsignacion estrategiaActual;
    private AsignacionMemoria asignador;
    private int tamanioMemoria;
    private int tiempoSeleccion;
    private int tiempoCargaPromedio;
    private int tiempoLiberacion;
    private Registro registro;

    public Simulador() {
        this.particiones = new ArrayList<>();
        this.procesos = new ArrayList<>();
        this.tamanioMemoria = 0;
        this.estrategiaActual = Particion.EstrategiaAsignacion.FIRST_FIT;
        this.tiempoSeleccion = 0;
        this.tiempoCargaPromedio = 0;
        this.tiempoLiberacion = 0;
        this.asignador = new AsignacionMemoria(particiones);
        try {
            this.registro = new Registro("eventos.txt", "estado_particiones.txt");
        } catch (IOException e) {
            System.out.println("Error al crear los archivos de registro: " + e.getMessage());
        }
    }

    public void simular() {
        Collections.sort(procesos, Comparator.comparingInt(Proceso::getInstanteArribo));

        for (Proceso proceso : procesos) {
            Particion particion = asignarParticion(proceso);
            if (particion != null) {
                registro.registrarEvento("Asignada partición para el proceso: " + proceso.getNombre() +
                        " - Partición ID: " + particion.getId() +
                        " - Tiempo: " + proceso.getInstanteArribo());

                cargarProceso(proceso);
                liberarProceso(proceso, particion);
            } else {
                registro.registrarEvento("No se pudo asignar el proceso: " + proceso.getNombre() +
                        " - Tiempo: " + proceso.getInstanteArribo());
            }
            registro.registrarEstadoParticiones(particiones);
        }

        calcularIndicadores();

        try {
            registro.cerrar();
        } catch (IOException e) {
            System.out.println("Error al cerrar los archivos de registro: " + e.getMessage());
        }

        imprimirResultados();
    }


    public Particion asignarParticion(Proceso proceso) {
        switch (estrategiaActual) {
            case FIRST_FIT:
                return asignador.firstFit(proceso);
            case BEST_FIT:
                return asignador.bestFit(proceso);
            case NEXT_FIT:
                return asignador.nextFit(proceso);
            case WORST_FIT:
                return asignador.worstFit(proceso);
            default:
                throw new UnsupportedOperationException("Estrategia no implementada");
        }
    }

    private void liberarProceso(Proceso proceso, Particion particion) {

        try {
            System.out.println("Liberando partición para el proceso: " + proceso.getNombre());
            Thread.sleep(proceso.getDuracion() * 1000);
        } catch (InterruptedException e) {
            System.out.println("Error al liberar el proceso: " + e.getMessage());
        }

        particion.setOcupada(false); // Marcar la partición como libre
        System.out.println("Partición liberada para el proceso: " + proceso.getNombre());
    }


    public void setEstrategiaActual(Particion.EstrategiaAsignacion estrategia) {
        this.estrategiaActual = estrategia;
    }


    private void cargarProceso(Proceso proceso) {

        System.out.println("Cargando proceso: " + proceso.getNombre());

    }


    private void calcularIndicadores() {
        int tiempoTotalRetorno = 0;
        int cantidadProcesos = procesos.size();

        for (Proceso proceso : procesos) {
            int tiempoRetorno = (proceso.getInstanteArribo() + proceso.getDuracion()) - proceso.getInstanteArribo();
            tiempoTotalRetorno += tiempoRetorno;
            System.out.printf("Proceso: %s, Tiempo de Retorno: %d%n", proceso.getNombre(), tiempoRetorno);
        }

        if (cantidadProcesos > 0) {
            double tiempoMedioRetorno = (double) tiempoTotalRetorno / cantidadProcesos;
            System.out.printf("Tiempo Medio de Retorno: %.2f%n", tiempoMedioRetorno);
        } else {
            System.out.println("No hay procesos para calcular el tiempo medio de retorno.");
        }
    }


    private void imprimirResultados() {
        System.out.println("\nDiagrama de Gantt de la simulación:");
        System.out.println("Tiempo de arribo, Proceso, Inicio, Fin");

        // Encontrar el tiempo máximo para definir el ancho del diagrama de Gantt
        int tiempoMaximo = procesos.stream()
                .mapToInt(p -> p.getInstanteArribo() + p.getDuracion())
                .max()
                .orElse(0);


        System.out.print("0");
        for (int t = 1; t <= tiempoMaximo; t++) {
            System.out.print("----");
        }
        System.out.println();

        // Para cada proceso, imprimimos una representación de su ejecución en la consola
        for (Proceso proceso : procesos) {
            int tiempoInicio = proceso.getInstanteArribo();
            int tiempoFin = tiempoInicio + proceso.getDuracion();

            // Imprimir el proceso y su representación visual en el diagrama de Gantt
            StringBuilder ganttBar = new StringBuilder();
            ganttBar.append("|");
            for (int t = 0; t < tiempoMaximo; t++) {
                if (t >= tiempoInicio && t < tiempoFin) {
                    ganttBar.append("****"); // Representación de una unidad de tiempo
                } else {
                    ganttBar.append("    "); // Espacio en blanco
                }
            }
            ganttBar.append("|");

            // Imprimir el detalle del proceso
            System.out.printf("Proceso: %s, Inicio: %d, Fin: %d %s%n",
                    proceso.getNombre(), tiempoInicio, tiempoFin, ganttBar.toString());
        }

        // Imprimir la línea de tiempo al final del diagrama de Gantt
        System.out.print("0");
        for (int t = 1; t <= tiempoMaximo; t++) {
            System.out.print("----");
        }
        System.out.println();
    }

    public List<Proceso> getProcesos() {
        return procesos;
    }

    public void setProcesos(List<Proceso> procesos) {
        this.procesos = procesos;
    }

    public void setTamanioMemoria(int tamanioMemoria) {
        this.tamanioMemoria = tamanioMemoria;
    }

    public void setTiempoSeleccion(int tiempoSeleccion) {
        this.tiempoSeleccion = tiempoSeleccion;
    }

    public void setTiempoCargaPromedio(int tiempoCargaPromedio) {
        this.tiempoCargaPromedio = tiempoCargaPromedio;
    }

    public void setTiempoLiberacion(int tiempoLiberacion) {
        this.tiempoLiberacion = tiempoLiberacion;
    }

}

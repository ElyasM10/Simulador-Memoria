package Clases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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
    private int memoriaTotal;



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

/*
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
      */
    public void simular() {
    int tiempoActual = 0;
    for (Proceso proceso : procesos) {
        tiempoActual = Math.max(tiempoActual, proceso.getInstanteArribo());
        
        tiempoActual += tiempoSeleccion;
        Particion particion = asignarParticion(proceso);
        
        if (particion != null) {
            tiempoActual += tiempoCargaPromedio;
            registro.registrarEvento("Asignada partición para el proceso: " + proceso.getNombre() +
                    " - Partición ID: " + particion.getId() +
                    " - Tiempo: " + tiempoActual);
            
            tiempoActual += proceso.getDuracion();
            
            tiempoActual += tiempoLiberacion;
            liberarProceso(proceso, particion);
        } else {
            registro.registrarEvento("No se pudo asignar el proceso: " + proceso.getNombre() +
                    " - Tiempo: " + tiempoActual);
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
        Particion particionAsignada = null;
        
        switch (estrategiaActual) {
            case FIRST_FIT:
                particionAsignada = asignador.firstFit(proceso);
                break;
            case BEST_FIT:
                particionAsignada = asignador.bestFit(proceso);
                break;
            case NEXT_FIT:
                particionAsignada = asignador.nextFit(proceso);
                break;
            case WORST_FIT:
                particionAsignada = asignador.worstFit(proceso);
                break;
        }
        
        if (particionAsignada != null) {
            dividirParticion(particionAsignada, proceso.getMemoriaRequerida());
        }
        
        return particionAsignada;
    }

    private void dividirParticion(Particion particion, int tamanioRequerido) {
        if (particion.getTamaño() > tamanioRequerido) {
            int nuevaTamanio = particion.getTamaño() - tamanioRequerido;
            int nuevaDireccion = particion.getDireccionComienzo() + tamanioRequerido;
            
            Particion nuevaParticion = new Particion(particiones.size() + 1, nuevaDireccion, nuevaTamanio);
            particiones.add(particiones.indexOf(particion) + 1, nuevaParticion);
            
            particion.setTamaño(tamanioRequerido);
        }
        particion.setOcupada(true);
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


    
    private void fusionarParticionesLibres() {
        for (int i = 0; i < particiones.size() - 1; i++) {
            Particion actual = particiones.get(i);
            Particion siguiente = particiones.get(i + 1);
            
            if (!actual.isOcupada() && !siguiente.isOcupada()) {
                actual.setTamaño(actual.getTamaño() + siguiente.getTamaño());
                particiones.remove(i + 1);
                i--; // Retroceder para verificar si se puede fusionar con la siguiente
            }
        }
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

    
    public void setMemoriaTotal(int memoriaTotal) {
        this.memoriaTotal = memoriaTotal;

        particiones.add(new Particion(1, 0, memoriaTotal));
    }


}

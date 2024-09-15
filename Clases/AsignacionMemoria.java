package Clases;

import java.util.List;

public class AsignacionMemoria {

   
     // Lista de particiones de memoria
    private int ultimaParticionIndex = 0; // indice de la ultima particion asignada


/*
    public Particion firstFit(Proceso Proceso,int tamanioMemoria,int tiempoSeleccion,int tiempoCargaPromedio,int tiempoLiberacion) {
        for (Particion particion : particiones) {
            // Verificar si la particion es libre y tiene suficiente espacio para el Proceso
            if (!particion.isOcupada() && particion.gettamanio() >= Proceso.getMemoriaRequerida()) {
                // Marcar la particion actual como ocupada
                particion.setOcupada(true);
                particion.settamanio(Proceso.getMemoriaRequerida()); 
                return particion; // Retornar la particion asignada
            }
        }
        return null; 
    }
*/
//trabajo = proceso
  // Metodo para asignar un trabajo de tamano 'tamanoTrabajo'


/* 
  public Particion firstFit(List<Particion> listaParticiones, Proceso proceso, int tamanioMemoria, int tiempoSeleccion, int tiempoCargaPromedio, int tiempoLiberacion) {
    for (Particion particion : listaParticiones) {
          //True libre y false ocupado
        if (!particion.getEstado() && particion.getTamanio() >= proceso.getTamanio()) {
            // Asignar el proceso a esta particion
            particion.setEstado(true); // Marcar como ocupada
            particion.setTiempoInicio(tiempoSeleccion); // Ajustar tiempo de inicio
            particion.setTiempoFinalizacion(tiempoSeleccion + proceso.getDuracion() + tiempoCargaPromedio + tiempoLiberacion); // Ajustar tiempo de finalizacion
            return particion;
        }
    }
    return null; // No se encontro una particion adecuada
}
*/
public Particion firstFit(List<Particion> listaParticiones, Proceso proceso, int tamanioMemoria, int tiempoSeleccion, int tiempoCargaPromedio, int tiempoLiberacion) {
    System.out.println("Buscando una particion adecuada para el proceso:");
    System.out.println("Tamaño del proceso: " + proceso.getTamanio());
    System.out.println("Duracion del proceso: " + proceso.getDuracion());
    System.out.println("Tiempo de seleccion: " + tiempoSeleccion);
    System.out.println("Tiempo de carga promedio: " + tiempoCargaPromedio);
    System.out.println("Tiempo de liberacion: " + tiempoLiberacion);

    for (Particion particion : listaParticiones) {
        System.out.println("Comparando con particion:");
        System.out.println("ID de particion: " + particion.getId());
        System.out.println("Tamaño de particion: " + particion.getTamanio());
        System.out.println("Estado de particion (true = libre, false = ocupada): " + (particion.getEstado() ? "Ocupada" : "Libre"));

        // True = libre, false = ocupado
        if (!particion.getEstado() && particion.getTamanio() >= proceso.getTamanio()) {
            System.out.println("Particion adecuada encontrada:");
            System.out.println("ID de particion: " + particion.getId());
            System.out.println("Tamaño de particion: " + particion.getTamanio());

            // Asignar el proceso a esta particion
            particion.setEstado(true); // Marcar como ocupada
            particion.setTiempoInicio(tiempoSeleccion); // Ajustar tiempo de inicio
            particion.setTiempoFinalizacion(tiempoSeleccion + proceso.getDuracion() + tiempoCargaPromedio + tiempoLiberacion); // Ajustar tiempo de finalizacion

            System.out.println("Proceso asignado a particion con ID: " + particion.getId());
            System.out.println("Tiempo de inicio: " + particion.getTiempoInicio());
            System.out.println("Tiempo de finalizacion: " + particion.getTiempoFinalizacion());

            return particion;
        } else {
            System.out.println("Particion no adecuada.");
        }
    }

    System.out.println("No se encontro una particion adecuada para el proceso.");
    return null; // No se encontro una particion adecuada
}




      

 


    public Particion bestFit(List<Particion>listaParticiones,Proceso proceso, int tamanioMemoria, int tiempoSeleccion, int tiempoCargaPromedio, int tiempoLiberacion) {
        Particion mejorParticion = null;
        int menorDiferencia = Integer.MAX_VALUE; // Inicialmente, la mayor diferencia posible

        // Buscar la mejor particion disponible
        for (Particion particion : listaParticiones) {
            int diferencia = particion.getTamanio() - proceso.getTamanio();
            // Encontrar la particion más pequeña que aún puede contener el Proceso
            if (!particion.getEstado() && diferencia >= 0 && diferencia < menorDiferencia) {
                mejorParticion = particion;
                menorDiferencia = diferencia;
            }
        }

        // Si encontramos una particion adecuada, la asignamos al Proceso
        if (mejorParticion != null) {
            mejorParticion.setEstado(false);
            mejorParticion.setTamanio(proceso.getTamanio()); 
        }
        return mejorParticion;  
    }


    public Particion nextFit(List<Particion>listaParticiones,Proceso proceso, int tamanioMemoria, int tiempoSeleccion, int tiempoCargaPromedio, int tiempoLiberacion){
        int n = listaParticiones.size(); // Numero total de particiones
        int comienzoIndex = ultimaParticionIndex; // Comienza desde la ultima particion asignada

        // Recorre las particiones desde la última asignada hasta el final
        for (int i = comienzoIndex; i < n; i++) {
            if (!listaParticiones.get(i).getEstado() && listaParticiones.get(i).getTamanio() >= proceso.getTamanio()) {
                listaParticiones.get(i).setEstado(true);
                listaParticiones.get(i).setTamanio(proceso.getTamanio());

                // Actualizar el indice de la última particion asignada
                ultimaParticionIndex = i;
                return listaParticiones.get(i); // Retorna la particion asignada
            }
        }

        // Si no se encuentra una particin, continuar la busqueda desde el principio hasta la ultima particin asignada
        for (int i = 0; i < comienzoIndex; i++) {
            if (!listaParticiones.get(i).getEstado() && listaParticiones.get(i).getTamanio() >= proceso.getTamanio()) {
                listaParticiones.get(i).setEstado(true);
                listaParticiones.get(i).setTamanio(proceso.getTamanio());

                // Actualizar el indice de la ultima particion asignada
                ultimaParticionIndex = i;
                return listaParticiones.get(i); 
            }
        }

        return null; // No hay particion disponible
    }


    public Particion worstFit(List<Particion>listaParticiones,Proceso proceso, int tamanioMemoria, int tiempoSeleccion, int tiempoCargaPromedio, int tiempoLiberacion) {
      /* 
        Particion peorParticion = null;
        int mayorDiferencia = -1; // Inicialmente, la diferencia más baja posible

        // Buscar la peor particion disponible 
        for (Particion particion : listaParticiones) {
            int diferencia = particion.getTamanio() - Proceso.getTamanio();
            // Encontrar la particion mas grande que  puede contener el Proceso
            if (!particion.isOcupada() && diferencia >= 0 && diferencia > mayorDiferencia) {
                peorParticion = particion;
                mayorDiferencia = diferencia;
            }
        }

        // Si encontramos una particion se le asignam el Proceso
        if (peorParticion != null) {
            peorParticion.setOcupada(true);
            peorParticion.settamanio(Proceso.getTamanio());
        }

        return peorParticion; 
        */
        return null; 

        }

    

}

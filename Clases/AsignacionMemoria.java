package Clases;

import java.util.List;

public class AsignacionMemoria {

    private List<Particion> particiones; // Lista de particiones de memoria
    private int ultimaParticionIndex = 0; // Índice de la ultima partición asignada

    public AsignacionMemoria(List<Particion> particiones) {
        this.particiones = particiones;
    }

    public Particion firstFit(Proceso Proceso) {
        for (Particion particion : particiones) {
            // Verificar si la partición es libre y tiene suficiente espacio para el Proceso
            if (!particion.isOcupada() && particion.getTamaño() >= Proceso.getMemoriaRequerida()) {
                // Marcar la partición actual como ocupada
                particion.setOcupada(true);
                particion.setTamaño(Proceso.getMemoriaRequerida()); 
                return particion; // Retornar la partición asignada
            }
        }
        return null; 
    }


    public Particion bestFit(Proceso Proceso) {
        Particion mejorParticion = null;
        int menorDiferencia = Integer.MAX_VALUE; // Inicialmente, la mayor diferencia posible

        // Buscar la mejor partición disponible
        for (Particion particion : particiones) {
            int diferencia = particion.getTamaño() - Proceso.getMemoriaRequerida();
            // Encontrar la partición más pequeña que aún puede contener el Proceso
            if (!particion.isOcupada() && diferencia >= 0 && diferencia < menorDiferencia) {
                mejorParticion = particion;
                menorDiferencia = diferencia;
            }
        }

        // Si encontramos una partición adecuada, la asignamos al Proceso
        if (mejorParticion != null) {
            mejorParticion.setOcupada(true);
            mejorParticion.setTamaño(Proceso.getMemoriaRequerida()); 
        }
        return mejorParticion;  
    }


    public Particion nextFit(Proceso Proceso) {
        int n = particiones.size(); // Numero total de particiones
        int comienzoIndex = ultimaParticionIndex; // Comienza desde la ultima partición asignada

        // Recorre las particiones desde la última asignada hasta el final
        for (int i = comienzoIndex; i < n; i++) {
            if (!particiones.get(i).isOcupada() && particiones.get(i).getTamaño() >= Proceso.getMemoriaRequerida()) {
                particiones.get(i).setOcupada(true);
                particiones.get(i).setTamaño(Proceso.getMemoriaRequerida());

                // Actualizar el índice de la última partición asignada
                ultimaParticionIndex = i;
                return particiones.get(i); // Retorna la partición asignada
            }
        }

        // Si no se encuentra una particin, continuar la busqueda desde el principio hasta la ultima particin asignada
        for (int i = 0; i < comienzoIndex; i++) {
            if (!particiones.get(i).isOcupada() && particiones.get(i).getTamaño() >= Proceso.getMemoriaRequerida()) {
                particiones.get(i).setOcupada(true);
                particiones.get(i).setTamaño(Proceso.getMemoriaRequerida());

                // Actualizar el índice de la ultima partición asignada
                ultimaParticionIndex = i;
                return particiones.get(i); 
            }
        }

        return null; // No hay partición disponible
    }


    public Particion worstFit(Proceso Proceso) {
        Particion peorParticion = null;
        int mayorDiferencia = -1; // Inicialmente, la diferencia más baja posible

        // Buscar la peor particion disponible 
        for (Particion particion : particiones) {
            int diferencia = particion.getTamaño() - Proceso.getMemoriaRequerida();
            // Encontrar la partición mas grande que  puede contener el Proceso
            if (!particion.isOcupada() && diferencia >= 0 && diferencia > mayorDiferencia) {
                peorParticion = particion;
                mayorDiferencia = diferencia;
            }
        }

        // Si encontramos una partición se le asignam el Proceso
        if (peorParticion != null) {
            peorParticion.setOcupada(true);
            peorParticion.setTamaño(Proceso.getMemoriaRequerida());
        }

        return peorParticion; 
    }

    
    public List<Particion> getParticiones() {
        return particiones;
    }
}

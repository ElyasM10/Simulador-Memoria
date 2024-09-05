import Clases.Particion;
import java.util.ArrayList;
import java.util.List;

public class Memoria {
    private int tamañoTotal;
    private List<Particion> particiones;

    // Constructor
    public Memoria(int tamañoTotal) {
        this.tamañoTotal = tamañoTotal;
        this.particiones = new ArrayList<>();
    }

    // Método para agregar una partición
    public void agregarParticion(Particion particion) {
        particiones.add(particion);
    }

    // Métodos para gestionar la asignación de memoria (Ej. First-Fit, Best-Fit, etc.)
    public Particion asignarMemoria(Trabajo trabajo, String estrategia) {
        // Implementar lógica de asignación según la estrategia seleccionada (First-Fit, Best-Fit, etc.)
        // ...
        return null; // Retornar la partición asignada o null si no hay partición disponible
    }

    // Método para liberar memoria
    public void liberarMemoria(int idParticion) {
        for (Particion particion : particiones) {
            if (particion.getId() == idParticion) {
                particion.setOcupada(false);
                break;
            }
        }
    }

    // Obtener el estado actual de la memoria
    public List<Particion> getParticiones() {
        return particiones;
    }
}

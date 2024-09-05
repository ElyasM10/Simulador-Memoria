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


    public void agregarParticion(Particion particion) {
        particiones.add(particion);
    }

// asignación de memoria  First-Fit, Best-Fit
    public Particion asignarMemoria(Trabajo trabajo, String estrategia) {
  
        return null; 
    }

    public void liberarMemoria(int idParticion) {
        for (Particion particion : particiones) {
            if (particion.getId() == idParticion) {
                particion.setOcupada(false);
                break;
            }
        }
    }

   
    public List<Particion> getParticiones() {
        return particiones;
    }
}

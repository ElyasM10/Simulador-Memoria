package Clases;

public class Particion {
    private int id;
    private int direccionComienzo;
    private int tamaño;
    private boolean ocupada;
    private EstrategiaAsignacion estrategiaActual;


      public enum EstrategiaAsignacion {
        FIRST_FIT,
        BEST_FIT,
        NEXT_FIT,
        WORST_FIT
    }
    
    public Particion(int id, int direccionComienzo, int tamaño) {
        this.id = id;
        this.direccionComienzo = direccionComienzo;
        this.tamaño = tamaño;
        this.ocupada = false; // false (libre)/ true(ocupada)
    }

    
    public int getId() {
        return id;
    }

    public int getDireccionComienzo() {
        return direccionComienzo;
    }

    public int getTamaño() {
        return tamaño;
    }

    public void setTamaño(int tamaño){
        this.tamaño = tamaño; 
    }


    public boolean isOcupada() {
        return ocupada;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }


    @Override
    public String toString() {
        return "Particion{" +
                "id=" + id +
                ", direccionComienzo=" + direccionComienzo +
                ", tamaño=" + tamaño +
                ", ocupada=" + ocupada +
                '}';
    }
}

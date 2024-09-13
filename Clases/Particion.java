package Clases;

public class Particion {
    private int id;
    private int direccionComienzo;
    private int tamanio;
    private boolean ocupada;
    private EstrategiaAsignacion estrategiaActual;


      public enum EstrategiaAsignacion {
        FIRST_FIT,
        BEST_FIT,
        NEXT_FIT,
        WORST_FIT
    }
    
    public Particion(int id, int direccionComienzo, int tamanio) {
        this.id = id;
        this.direccionComienzo = direccionComienzo;
        this.tamanio = tamanio;
        this.ocupada = false; // false (libre)/ true(ocupada)
    }

    
    public int getId() {
        return id;
    }

    public int getDireccionComienzo() {
        return direccionComienzo;
    }

    public int gettamanio() {
        return tamanio;
    }

    public void settamanio(int tamanio){
        this.tamanio = tamanio; 
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
                ", tamanio=" + tamanio +
                ", ocupada=" + ocupada +
                '}';
    }
}

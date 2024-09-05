package Clases;

public class Particion {
    private int id;
    private int direccionComienzo;
    private int tamaño;
    private boolean ocupada;

    // Constructor
    public Particion(int id, int direccionComienzo, int tamaño) {
        this.id = id;
        this.direccionComienzo = direccionComienzo;
        this.tamaño = tamaño;
        this.ocupada = false; // Inicialmente, la partición está libre
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public int getDireccionComienzo() {
        return direccionComienzo;
    }

    public int getTamaño() {
        return tamaño;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    // Método toString para mostrar información de la partición
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

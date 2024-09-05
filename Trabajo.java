public class Trabajo {
    private String nombre;
    private int instanteArribo;
    private int duracionTotal;
    private int memoriaRequerida;

    // Constructor
    public Trabajo(String nombre, int instanteArribo, int duracionTotal, int memoriaRequerida) {
        this.nombre = nombre;
        this.instanteArribo = instanteArribo;
        this.duracionTotal = duracionTotal;
        this.memoriaRequerida = memoriaRequerida;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public int getInstanteArribo() {
        return instanteArribo;
    }

    public int getDuracionTotal() {
        return duracionTotal;
    }

    public int getMemoriaRequerida() {
        return memoriaRequerida;
    }

    // toString 
    @Override
    public String toString() {
        return "Trabajo{" +
                "nombre='" + nombre + '\'' +
                ", instanteArribo=" + instanteArribo +
                ", duracionTotal=" + duracionTotal +
                ", memoriaRequerida=" + memoriaRequerida +
                '}';
    }
}

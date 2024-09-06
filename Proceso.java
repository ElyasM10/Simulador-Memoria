public class Trabajo {
    private int id 
    private String nombre;
    private int memoriaRequerida; //Tamaño
    private int instanteArribo;
    private int duracion;
    private int memoriaRequerida;

    public Trabajo(int id;String nombre, int instanteArribo, int duracion, int memoriaRequerida) {
        this.id = id;
        this.nombre = nombre;
        this.instanteArribo = instanteArribo;
        this.duracion = duracion;
        this.memoriaRequerida = memoriaRequerida;
    }
    
    public int getID(){
        return id; 
    }

    public String getNombre() {
        return nombre;
    }

    public int getInstanteArribo() {
        return instanteArribo;
    }

    public int getDuracion() {
        return duracion;
    }

    public int getMemoriaRequerida() {
        return memoriaRequerida;
    }

    // toString 
    @Override
    public String toString() {
        return "Trabajo{" +
                "id='"+id+
                "nombre='" + nombre + '\'' +
                ", instanteArribo=" + instanteArribo +
                ", duracion=" + duracion +
                ", memoriaRequerida=" + memoriaRequerida +
                '}';
    }
}

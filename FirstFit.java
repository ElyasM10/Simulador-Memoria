import Clases.Particion;


public Particion FirstFit(Trabajo trabajo) {
    for (Particion particion : particiones) {
        if (!particion.isOcupada() && particion.getTamaño() >= trabajo.getMemoriaRequerida()) {
            particion.setOcupada(true);
            return particion;
        }
    }
    return null; // No hay partición adecuada disponible
}
dice que hay error no encuentra particiones
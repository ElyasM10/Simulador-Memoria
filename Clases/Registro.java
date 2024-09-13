package Clases;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Registro {
    private PrintWriter writerEventos;
    private PrintWriter writerEstadoParticiones;

    public Registro(String archivoEventos, String archivoEstadoParticiones) throws IOException {
        this.writerEventos = new PrintWriter(new FileWriter(archivoEventos, true));
        this.writerEstadoParticiones = new PrintWriter(new FileWriter(archivoEstadoParticiones, true));
    }

    public void registrarEvento(String mensaje) {
        writerEventos.println(mensaje);
        writerEventos.flush();
    }

    public void registrarEstadoParticiones(List<Particion> particiones) {
        writerEstadoParticiones.println("Estado de particiones en tiempo " + System.currentTimeMillis() + ":");
        for (Particion particion : particiones) {
            writerEstadoParticiones.printf("ID: %d, Comienzo: %d, Tamaño: %d, Estado: %s%n",
                    particion.getId(), particion.getDireccionComienzo(), particion.getTamaño(),
                    particion.isOcupada() ? "Ocupada" : "Libre");
        }
        writerEstadoParticiones.flush();
    }

    public void cerrar() throws IOException {
        writerEventos.close();
        writerEstadoParticiones.close();
    }
}

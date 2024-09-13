package Clases;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

class GanttPanel extends JPanel {
    private List<Proceso> procesos;

    public GanttPanel(List<Proceso> procesos) {
        this.procesos = procesos;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (procesos == null || procesos.isEmpty()) {
            return; // No hay procesos para mostrar
        }

        int x = 50; // Posición inicial x
        int y = 30; // Posición inicial y
        int alturaRectangulo = 30;
        int separacionVertical = 10;

        Random random = new Random();

        for (Proceso proceso : procesos) {
            int duracion = proceso.getDuracion();
            int anchoRectangulo = duracion * 10; // Escala de tiempo para el ancho del rectángulo

            // Generar un color aleatorio para cada proceso
            Color colorProceso = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            g.setColor(colorProceso);
            g.fillRect(x, y, anchoRectangulo, alturaRectangulo);

            // Dibujar borde del rectángulo
            g.setColor(Color.BLACK);
            g.drawRect(x, y, anchoRectangulo, alturaRectangulo);

            // Mostrar el nombre del proceso
            g.setColor(Color.WHITE); // Usar blanco para que sea visible sobre los colores de fondo
            g.drawString(proceso.getNombre(), x + 5, y + 20);

            // Mover la posición y para el siguiente proceso
            y += alturaRectangulo + separacionVertical;
        }
    }

    // Método para actualizar la lista de procesos y redibujar el panel
    public void setProcesos(List<Proceso> procesos) {
        this.procesos = procesos;
        repaint();
    }
}


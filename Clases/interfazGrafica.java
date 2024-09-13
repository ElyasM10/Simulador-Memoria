package Clases;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class interfazGrafica extends JFrame {
    private JTextField txtNombreArchivo;
    private JComboBox<String> cmbPolitica;
    private JTextField txtTamanioMemoria;
    private JTextField txtTiempoSeleccion;
    private JTextField txtTiempoCargaPromedio;
    private JTextField txtTiempoLiberacion;
    private Simulador simulador;
    private GanttPanel ganttPanel;

    public interfazGrafica() {
        setTitle("Simulador de Asignación de Memoria");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Panel de entrada con GridBagLayout
        JPanel panelEntrada = new JPanel();
        panelEntrada.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Margen entre los componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5; // Asignar peso igual a ambas columnas

        // Primer columna (izquierda)
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblNombreArchivo = new JLabel("Nombre del archivo:");
        panelEntrada.add(lblNombreArchivo, gbc);
        gbc.gridx = 1;
        txtNombreArchivo = new JTextField();
        panelEntrada.add(txtNombreArchivo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblPolitica = new JLabel("Política de asignación:");
        panelEntrada.add(lblPolitica, gbc);
        gbc.gridx = 1;
        cmbPolitica = new JComboBox<>(new String[]{"firstfit", "bestfit", "nextfit", "worstfit"});
        panelEntrada.add(cmbPolitica, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblTamanioMemoria = new JLabel("Tamaño de memoria física disponible:");
        panelEntrada.add(lblTamanioMemoria, gbc);
        gbc.gridx = 1;
        txtTamanioMemoria = new JTextField();
        panelEntrada.add(txtTamanioMemoria, gbc);

        // Segunda columna (derecha)
        gbc.gridx = 2;
        gbc.gridy = 0;
        JLabel lblTiempoSeleccion = new JLabel("Tiempo de selección de partición:");
        panelEntrada.add(lblTiempoSeleccion, gbc);
        gbc.gridx = 3;
        txtTiempoSeleccion = new JTextField();
        panelEntrada.add(txtTiempoSeleccion, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        JLabel lblTiempoCargaPromedio = new JLabel("Tiempo de carga promedio:");
        panelEntrada.add(lblTiempoCargaPromedio, gbc);
        gbc.gridx = 3;
        txtTiempoCargaPromedio = new JTextField();
        panelEntrada.add(txtTiempoCargaPromedio, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        JLabel lblTiempoLiberacion = new JLabel("Tiempo de liberación de partición:");
        panelEntrada.add(lblTiempoLiberacion, gbc);
        gbc.gridx = 3;
        txtTiempoLiberacion = new JTextField();
        panelEntrada.add(txtTiempoLiberacion, gbc);

        // Botón de cargar y simular
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4; // El botón ocupa las 4 columnas
        JButton btnCargar = new JButton("Cargar y Simular");
        panelEntrada.add(btnCargar, gbc);

        btnCargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarYSimular(); // Ejecutar simulación al hacer clic
            }
        });

        // Crear el panel de Gantt para mostrar el diagrama
        ganttPanel = new GanttPanel(new ArrayList<>());

        // Añadir paneles al contenedor principal
        panel.add(panelEntrada, BorderLayout.NORTH);
        panel.add(ganttPanel, BorderLayout.CENTER);

        add(panel);
    }

    // Método para cargar el archivo y ejecutar la simulación
    private void cargarYSimular() {
        String nombreArchivo = txtNombreArchivo.getText();
        String politicaSeleccionada = (String) cmbPolitica.getSelectedItem();
        List<Proceso> listaProcesos = cargarProcesosDesdeArchivo(nombreArchivo);

        if (listaProcesos == null || listaProcesos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se pudieron cargar procesos del archivo.");
            return;
        }

        simulador = new Simulador();
        simulador.getProcesos().addAll(listaProcesos);

        // Configurar la política de asignación seleccionada
        switch (politicaSeleccionada.toLowerCase()) {
            case "firstfit" -> simulador.setEstrategiaActual(Particion.EstrategiaAsignacion.FIRST_FIT);
            case "bestfit" -> simulador.setEstrategiaActual(Particion.EstrategiaAsignacion.BEST_FIT);
            case "nextfit" -> simulador.setEstrategiaActual(Particion.EstrategiaAsignacion.NEXT_FIT);
            case "worstfit" -> simulador.setEstrategiaActual(Particion.EstrategiaAsignacion.WORST_FIT);
            default -> {
                System.out.println("Política no reconocida. Se usará FIRST_FIT por defecto.");
                simulador.setEstrategiaActual(Particion.EstrategiaAsignacion.FIRST_FIT);
            }
        }

        // Configurar otros parámetros
        try {
            int tamanioMemoria = Integer.parseInt(txtTamanioMemoria.getText());
            int tiempoSeleccion = Integer.parseInt(txtTiempoSeleccion.getText());
            int tiempoCargaPromedio = Integer.parseInt(txtTiempoCargaPromedio.getText());
            int tiempoLiberacion = Integer.parseInt(txtTiempoLiberacion.getText());

            simulador.setTamanioMemoria(tamanioMemoria);
            simulador.setTiempoSeleccion(tiempoSeleccion);
            simulador.setTiempoCargaPromedio(tiempoCargaPromedio);
            simulador.setTiempoLiberacion(tiempoLiberacion);

            simulador.simular();
            mostrarResultados();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error en el formato de los números: " + ex.getMessage());
        }
    }

    // Método para mostrar los resultados de la simulación como un diagrama de Gantt
    private void mostrarResultados() {
        ganttPanel.setProcesos(simulador.getProcesos()); // Actualizar el panel de Gantt con los procesos simulados
    }

    // Método para cargar los procesos desde un archivo
    private List<Proceso> cargarProcesosDesdeArchivo(String nombreArchivo) {
        List<Proceso> procesos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                String[] datos = linea.split(",");
                if (datos.length != 5) {
                    JOptionPane.showMessageDialog(this, "Línea en el archivo no válida: " + linea);
                    continue;
                }

                try {
                    int id = Integer.parseInt(datos[0].trim());
                    String nombre = datos[1].trim();
                    int memoriaRequerida = Integer.parseInt(datos[2].trim());
                    int duracion = Integer.parseInt(datos[3].trim());
                    int instanteArribo = Integer.parseInt(datos[4].trim());

                    Proceso proceso = new Proceso(id, nombre, memoriaRequerida, duracion, instanteArribo);
                    procesos.add(proceso);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Error de formato en la línea: " + linea + " - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al leer el archivo: " + e.getMessage());
        }
        return procesos;
    }
}

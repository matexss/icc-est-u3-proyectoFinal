package views;

import controllers.MazeController;
import models.Cell;
import models.SolveResults;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class MazeFrame extends JFrame {

    private MazeController controller;
    private MazePanel mazePanel;
    private JComboBox<String> comboAlgoritmos;
    private JButton btnEjecutar, btnLimpiar, btnResultados, btnInicio, btnFin, btnPared;

    public MazeFrame(MazeController controller) {
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setTitle("Resolución de Laberintos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Maze Panel (editor visual del laberinto)
        mazePanel = new MazePanel();
        add(mazePanel, BorderLayout.CENTER);

        // Panel superior de controles
        JPanel topPanel = new JPanel(new FlowLayout());

        comboAlgoritmos = new JComboBox<>();
        controller.obtenerNombresAlgoritmos().forEach(comboAlgoritmos::addItem);

        btnInicio = new JButton("Set Start");
        btnFin = new JButton("Set End");
        btnPared = new JButton("Toggle Wall");

        btnEjecutar = new JButton("Ejecutar");
        btnLimpiar = new JButton("Limpiar");
        btnResultados = new JButton("Resultados");

        topPanel.add(btnInicio);
        topPanel.add(btnFin);
        topPanel.add(btnPared);
        topPanel.add(new JLabel("Algoritmo:"));
        topPanel.add(comboAlgoritmos);
        topPanel.add(btnEjecutar);
        topPanel.add(btnLimpiar);
        topPanel.add(btnResultados);

        add(topPanel, BorderLayout.NORTH);

        // Acción para botones de edición de celdas
        btnInicio.addActionListener(e -> mazePanel.setModo("start"));
        btnFin.addActionListener(e -> mazePanel.setModo("end"));
        btnPared.addActionListener(e -> mazePanel.setModo("wall"));

        // Acción para ejecutar algoritmo
        btnEjecutar.addActionListener(this::ejecutarAlgoritmo);

        // Acción para limpiar el laberinto
        btnLimpiar.addActionListener(e -> mazePanel.resetear());

        // Mostrar resultados guardados
        btnResultados.addActionListener(e -> {
            ResultadosDialog dialog = new ResultadosDialog(MazeFrame.this, controller);
            dialog.setVisible(true);
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void ejecutarAlgoritmo(ActionEvent e) {
        String algoritmo = (String) comboAlgoritmos.getSelectedItem();
        boolean[][] grid = mazePanel.getMatriz();
        Cell inicio = mazePanel.getInicio();
        Cell fin = mazePanel.getFin();

        if (inicio == null || fin == null) {
            JOptionPane.showMessageDialog(this, "Debes establecer punto de inicio y fin.");
            return;
        }

        SolveResults resultado = controller.ejecutar(algoritmo, grid, inicio, fin);
        mazePanel.mostrarResultado(resultado);
    }
}

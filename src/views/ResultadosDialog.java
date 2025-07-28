package views;

import controllers.MazeController;
import models.AlgorithmResult;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Diálogo para visualizar los resultados de los algoritmos ejecutados,
 * incluyendo una tabla y un gráfico de barras de tiempo de ejecución.
 */
public class ResultadosDialog extends JDialog {

    private final MazeController controller;
    private JTable tabla;
    private DefaultTableModel tableModel;
    private JPanel panelGrafico;
    private List<AlgorithmResult> resultados;

    public ResultadosDialog(JFrame parent, MazeController controller) {
        super(parent, "Resultados de Algoritmos", true);
        this.controller = controller;

        setLayout(new BorderLayout());
        setSize(750, 500);
        setLocationRelativeTo(parent);

        initComponents();
    }

    private void initComponents() {
        // Tabla
        tableModel = new DefaultTableModel(new String[]{"Algoritmo", "Tiempo (ms)", "Longitud del camino"}, 0);
        tabla = new JTable(tableModel);
        tabla.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setPreferredSize(new Dimension(700, 150));
        add(scrollPane, BorderLayout.NORTH);

        // Panel gráfico
        panelGrafico = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                dibujarGrafico(g);
            }
        };
        panelGrafico.setPreferredSize(new Dimension(700, 250));
        add(panelGrafico, BorderLayout.CENTER);

        // Botón inferior
        JButton btnLimpiar = new JButton("Limpiar Resultados");
        btnLimpiar.addActionListener(e -> {
            controller.limpiarResultados();
            actualizar();
        });

        JPanel southPanel = new JPanel();
        southPanel.add(btnLimpiar);
        add(southPanel, BorderLayout.SOUTH);

        actualizar();
    }

    private void actualizar() {
        resultados = controller.listarResultados();
        tableModel.setRowCount(0);

        for (AlgorithmResult r : resultados) {
            tableModel.addRow(new Object[]{
                    r.getAlgorithmName(),
                    r.getExecutionTimeMillis(),
                    r.getPathLength()
            });
        }

        panelGrafico.repaint();
    }

    private void dibujarGrafico(Graphics g) {
        if (resultados == null || resultados.isEmpty()) {
            g.setColor(Color.BLACK);
            g.drawString("No hay datos para mostrar", 20, 20);
            return;
        }

        int width = panelGrafico.getWidth();
        int height = panelGrafico.getHeight();

        int margin = 50;
        int barWidth = (width - 2 * margin) / resultados.size();
        long maxTime = resultados.stream()
                .mapToLong(AlgorithmResult::getExecutionTimeMillis)
                .max()
                .orElse(1);

        g.setColor(Color.BLACK);
        g.drawString("Gráfico de Tiempos de Ejecución (ms)", margin, 20);

        for (int i = 0; i < resultados.size(); i++) {
            AlgorithmResult r = resultados.get(i);
            int x = margin + i * barWidth;
            int barHeight = (int) ((r.getExecutionTimeMillis() * 1.0 / maxTime) * (height - 80));
            int y = height - barHeight - 40;

            g.setColor(Color.BLUE);
            g.fillRect(x, y, barWidth - 10, barHeight);

            g.setColor(Color.BLACK);
            g.drawRect(x, y, barWidth - 10, barHeight);

            g.setFont(new Font("Arial", Font.PLAIN, 10));
            g.drawString(r.getAlgorithmName(), x + 2, height - 25);
            g.drawString(r.getExecutionTimeMillis() + " ms", x + 2, y - 5);
        }
    }
}

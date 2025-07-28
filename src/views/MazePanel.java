package views;

import controllers.MazeController;
import models.Cell;
import models.CellState;
import models.SolveResults;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Panel visual del laberinto con celdas graficadas.
 */
public class MazePanel extends JPanel {

    private static final int SIZE = 40;
    private int filas = 10;
    private int columnas = 10;

    private Cell[][] cells;
    private JButton[][] buttons;
    private MazeController controller;

    public MazePanel() {
        solicitarTamanioLaberinto();
        inicializarCeldas();
        setLayout(new GridLayout(filas, columnas));
        setPreferredSize(new Dimension(columnas * SIZE, filas * SIZE));
        inicializarBotones();
    }

    private void solicitarTamanioLaberinto() {
        try {
            String inputFilas = JOptionPane.showInputDialog(null, "Ingrese número de filas:", "Tamaño del laberinto", JOptionPane.QUESTION_MESSAGE);
            String inputCols = JOptionPane.showInputDialog(null, "Ingrese número de columnas:", "Tamaño del laberinto", JOptionPane.QUESTION_MESSAGE);
            filas = Math.max(5, Integer.parseInt(inputFilas.trim()));
            columnas = Math.max(5, Integer.parseInt(inputCols.trim()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Entrada inválida. Usando tamaño por defecto 10x10.");
            filas = columnas = 10;
        }
    }

    private void inicializarCeldas() {
        cells = new Cell[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                cells[i][j] = new Cell(i, j);
                cells[i][j].setState(CellState.EMPTY);
            }
        }
    }

    private void inicializarBotones() {
        buttons = new JButton[filas][columnas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                JButton btn = new JButton();
                btn.setBackground(Color.WHITE);
                btn.setOpaque(true);
                btn.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                final int fila = i;
                final int col = j;

                btn.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (controller != null) {
                            controller.onCellClicked(fila, col);
                            actualizarVista(); // Refrescar vista al interactuar
                        }
                    }
                });

                buttons[i][j] = btn;
                add(btn);
            }
        }
    }

    public void setController(MazeController controller) {
        this.controller = controller;
        controller.setMatriz(cells);
    }

    public Cell[][] getCells() {
        return cells;
    }

    public JButton getButton(int fila, int col) {
        return buttons[fila][col];
    }

    public void limpiarCeldasVisitadas() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Cell cell = cells[i][j];
                if (cell.getState() == CellState.VISITED || cell.getState() == CellState.PATH) {
                    cell.setState(CellState.EMPTY);
                    buttons[i][j].setBackground(Color.WHITE);
                }
            }
        }
    }

    public void mostrarResultado(SolveResults resultado) {
        limpiarCeldasVisitadas();

        for (Cell cell : resultado.getVisited()) {
            if (cell != null && !cell.equals(controller.getStartCell()) && !cell.equals(controller.getEndCell())) {
                cells[cell.getRow()][cell.getCol()].setState(CellState.VISITED);
                buttons[cell.getRow()][cell.getCol()].setBackground(Color.CYAN);
            }
        }

        for (Cell cell : resultado.getPath()) {
            if (cell != null && !cell.equals(controller.getStartCell()) && !cell.equals(controller.getEndCell())) {
                cells[cell.getRow()][cell.getCol()].setState(CellState.PATH);
                buttons[cell.getRow()][cell.getCol()].setBackground(Color.BLUE);
            }
        }

        actualizarInicioYFin();
    }

    public void pintarPaso(int fila, int columna, Color color) {
        buttons[fila][columna].setBackground(color);
    }

    public void resetear() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Cell cell = cells[i][j];
                if (cell.getState() != CellState.WALL) {
                    cell.setState(CellState.EMPTY);
                    buttons[i][j].setBackground(Color.WHITE);
                }
            }
        }
        actualizarInicioYFin();
    }

    private void actualizarVista() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Cell cell = cells[i][j];
                switch (cell.getState()) {
                    case EMPTY -> buttons[i][j].setBackground(Color.WHITE);
                    case WALL -> buttons[i][j].setBackground(Color.BLACK);
                    case START -> buttons[i][j].setBackground(Color.GREEN);
                    case END -> buttons[i][j].setBackground(Color.RED);
                    case PATH -> buttons[i][j].setBackground(Color.BLUE);
                    case VISITED -> buttons[i][j].setBackground(Color.CYAN);
                }
            }
        }
    }

    private void actualizarInicioYFin() {
        if (controller.getStartCell() != null) {
            Cell s = controller.getStartCell();
            buttons[s.getRow()][s.getCol()].setBackground(Color.GREEN);
        }
        if (controller.getEndCell() != null) {
            Cell e = controller.getEndCell();
            buttons[e.getRow()][e.getCol()].setBackground(Color.RED);
        }
    }
}

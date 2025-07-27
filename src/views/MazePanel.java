package views;

import models.Cell;
import models.CellState;
import models.SolveResults;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MazePanel extends JPanel {

    private static final int SIZE = 40;
    private int filas = 10;
    private int columnas = 10;

    private CellState[][] grid;
    private Cell inicio, fin;

    private String modoActual = "wall";

    public MazePanel() {
        solicitarTamanioLaberinto();
        inicializarGrid();

        setPreferredSize(new Dimension(columnas * SIZE, filas * SIZE));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int fila = e.getY() / SIZE;
                int col = e.getX() / SIZE;

                if (!dentroDelLaberinto(fila, col)) return;

                switch (modoActual) {
                    case "start" -> establecerInicio(fila, col);
                    case "end" -> establecerFin(fila, col);
                    case "wall" -> alternarPared(fila, col);
                }

                repaint();
            }
        });
    }

    private void solicitarTamanioLaberinto() {
        try {
            String inputFilas = JOptionPane.showInputDialog(null, "Ingrese número de filas:", "Tamaño del laberinto", JOptionPane.QUESTION_MESSAGE);
            String inputCols = JOptionPane.showInputDialog(null, "Ingrese número de columnas:", "Tamaño del laberinto", JOptionPane.QUESTION_MESSAGE);

            filas = Math.max(2, Integer.parseInt(inputFilas));
            columnas = Math.max(2, Integer.parseInt(inputCols));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Entrada inválida. Usando tamaño por defecto 10x10.");
            filas = columnas = 10;
        }
    }

    private boolean dentroDelLaberinto(int fila, int col) {
        return fila >= 0 && fila < filas && col >= 0 && col < columnas;
    }

    private void inicializarGrid() {
        grid = new CellState[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                grid[i][j] = CellState.EMPTY;
            }
        }
        inicio = null;
        fin = null;
    }

    public void setModo(String modo) {
        this.modoActual = modo;
    }

    private void establecerInicio(int fila, int col) {
        if (inicio != null) {
            grid[inicio.getRow()][inicio.getCol()] = CellState.EMPTY;
        }
        inicio = new Cell(fila, col);
        grid[fila][col] = CellState.START;
    }

    private void establecerFin(int fila, int col) {
        if (fin != null) {
            grid[fin.getRow()][fin.getCol()] = CellState.EMPTY;
        }
        fin = new Cell(fila, col);
        grid[fila][col] = CellState.END;
    }

    private void alternarPared(int fila, int col) {
        if (inicio != null && inicio.getRow() == fila && inicio.getCol() == col) return;
        if (fin != null && fin.getRow() == fila && fin.getCol() == col) return;

        grid[fila][col] = (grid[fila][col] == CellState.WALL) ? CellState.EMPTY : CellState.WALL;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int fila = 0; fila < filas; fila++) {
            for (int col = 0; col < columnas; col++) {
                switch (grid[fila][col]) {
                    case WALL -> g.setColor(Color.BLACK);
                    case START -> g.setColor(Color.GREEN);
                    case END -> g.setColor(Color.RED);
                    case PATH -> g.setColor(Color.BLUE);
                    case VISITED -> g.setColor(Color.CYAN);
                    default -> g.setColor(Color.WHITE);
                }

                g.fillRect(col * SIZE, fila * SIZE, SIZE, SIZE);
                g.setColor(Color.GRAY);
                g.drawRect(col * SIZE, fila * SIZE, SIZE, SIZE);
            }
        }
    }

    public boolean[][] getMatriz() {
        boolean[][] matriz = new boolean[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matriz[i][j] = (grid[i][j] != CellState.WALL);
            }
        }
        return matriz;
    }

    public Cell getInicio() {
        return inicio;
    }

    public Cell getFin() {
        return fin;
    }

    public void resetear() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (grid[i][j] != CellState.WALL) {
                    grid[i][j] = CellState.EMPTY;
                }
            }
        }
        if (inicio != null) grid[inicio.getRow()][inicio.getCol()] = CellState.START;
        if (fin != null) grid[fin.getRow()][fin.getCol()] = CellState.END;

        repaint();
    }

    public void mostrarResultado(SolveResults resultado) {
        resetear();

        // Pintar visitados primero
        for (Cell cell : resultado.getVisited()) {
            if (!cell.equals(inicio) && !cell.equals(fin)) {
                grid[cell.getRow()][cell.getCol()] = CellState.VISITED;
            }
        }

        // Pintar camino después (sobrescribe VISITED si coinciden)
        for (Cell cell : resultado.getPath()) {
            if (!cell.equals(inicio) && !cell.equals(fin)) {
                grid[cell.getRow()][cell.getCol()] = CellState.PATH;
            }
        }

        // Reafirmar posiciones de inicio y fin
        if (inicio != null) grid[inicio.getRow()][inicio.getCol()] = CellState.START;
        if (fin != null) grid[fin.getRow()][fin.getCol()] = CellState.END;

        repaint();
    }
}

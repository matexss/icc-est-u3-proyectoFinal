package solver.solverImpl;

import solver.MazeSolver;
import models.Cell;
import models.SolveResults;

import java.util.*;

public class MazeSolverRecursivo implements MazeSolver {

    @Override
    public SolveResults getPath(boolean[][] grid, Cell start, Cell end) {
        List<Cell> path = new ArrayList<>();
        Set<Cell> visited = new HashSet<>();

        if (grid == null || grid.length == 0) {
            return new SolveResults(path, visited);
        }

        if (findPath(grid, start, end, path, visited)) {
            return new SolveResults(path, visited);
        }

        return new SolveResults(new ArrayList<>(), visited);
    }

    /**
     * Método recursivo simple que se mueve solo hacia abajo y derecha.
     */
    private boolean findPath(boolean[][] grid, Cell current, Cell end, List<Cell> path, Set<Cell> visited) {
        int row = current.getRow();
        int col = current.getCol();

        // Verificar si está fuera de rango o en una celda inválida
        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length || !grid[row][col] || visited.contains(current)) {
            return false;
        }

        visited.add(current);
        path.add(current);

        if (current.equals(end)) {
            return true;
        }

        // Movimiento: solo abajo y derecha
        if (findPath(grid, new Cell(row + 1, col), end, path, visited) ||
                findPath(grid, new Cell(row, col + 1), end, path, visited)) {
            return true;
        }

        // Retroceso si no encuentra salida
        path.remove(path.size() - 1);
        return false;
    }
}

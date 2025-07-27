package solver.solverImpl;

import solver.MazeSolver;
import models.Cell;
import models.SolveResults;

import java.util.*;

public class MazeSolverRecursivoCompletoBT implements MazeSolver {

    private List<Cell> path;
    private Set<Cell> visited;
    private boolean[][] grid;
    private Cell end;

    public MazeSolverRecursivoCompletoBT() {
        this.path = new ArrayList<>();
        this.visited = new LinkedHashSet<>();
    }

    @Override
    public SolveResults getPath(boolean[][] grid, Cell start, Cell end) {
        this.path.clear();
        this.visited.clear();
        this.grid = grid;
        this.end = end;

        if (grid == null || grid.length == 0) {
            return new SolveResults(new ArrayList<>(), new LinkedHashSet<>());
        }

        if (findPath(start)) {
            return new SolveResults(path, visited);
        }

        return new SolveResults(new ArrayList<>(), visited);
    }

    /**
     * Método recursivo con backtracking que explora todas las direcciones.
     */
    private boolean findPath(Cell current) {
        if (!isInBounds(current) || !isValid(current)) {
            return false;
        }

        visited.add(current);
        path.add(current);

        if (current.equals(end)) {
            return true;
        }

        if (findPath(new Cell(current.getRow(), current.getCol() + 1)) ||   // derecha
                findPath(new Cell(current.getRow() + 1, current.getCol())) ||   // abajo
                findPath(new Cell(current.getRow(), current.getCol() - 1)) ||   // izquierda
                findPath(new Cell(current.getRow() - 1, current.getCol()))) {   // arriba
            return true;
        }

        path.remove(path.size() - 1);
        return false;
    }

    private boolean isValid(Cell cell) {
        return grid[cell.getRow()][cell.getCol()] && !visited.contains(cell);
    }

    private boolean isInBounds(Cell cell) {
        int row = cell.getRow();
        int col = cell.getCol();
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }
}

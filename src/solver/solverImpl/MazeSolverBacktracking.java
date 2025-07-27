package solver.solverImpl;

import solver.MazeSolver;
import models.Cell;
import models.SolveResults;

import java.util.*;

/**
 * Implementación de un algoritmo de resolución de laberinto mediante backtracking puro.
 */
public class MazeSolverBacktracking implements MazeSolver {

    private List<Cell> path;
    private Set<Cell> visited;
    private boolean[][] grid;
    private Cell end;

    public MazeSolverBacktracking() {
        path = new ArrayList<>();
        visited = new HashSet<>();
    }

    @Override
    public SolveResults getPath(boolean[][] grid, Cell start, Cell end) {
        this.grid = grid;
        this.end = end;
        path.clear();
        visited.clear();

        if (grid == null || grid.length == 0) {
            return new SolveResults(new ArrayList<>(), new HashSet<>());
        }

        findPath(start);
        return new SolveResults(path, visited);
    }

    private boolean findPath(Cell current) {
        if (!isInMaze(current) || !isValid(current)) return false;

        visited.add(current);
        path.add(current);

        if (current.equals(end)) {
            return true;
        }

        int r = current.getRow();
        int c = current.getCol();

        if (
                findPath(new Cell(r + 1, c)) ||
                        findPath(new Cell(r - 1, c)) ||
                        findPath(new Cell(r, c + 1)) ||
                        findPath(new Cell(r, c - 1))
        ) {
            return true;
        }

        // Backtrack
        path.remove(path.size() - 1);
        return false;
    }

    private boolean isValid(Cell cell) {
        return grid[cell.getRow()][cell.getCol()] && !visited.contains(cell);
    }

    private boolean isInMaze(Cell cell) {
        int r = cell.getRow(), c = cell.getCol();
        return r >= 0 && r < grid.length && c >= 0 && c < grid[0].length;
    }
}

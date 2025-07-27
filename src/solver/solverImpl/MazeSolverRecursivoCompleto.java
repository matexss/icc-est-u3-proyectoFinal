package solver.solverImpl;

import solver.MazeSolver;
import models.Cell;
import models.SolveResults;

import java.util.*;

public class MazeSolverRecursivoCompleto implements MazeSolver {

    private List<Cell> path;
    private Set<Cell> visited;
    private boolean[][] grid;
    private Cell end;

    public MazeSolverRecursivoCompleto() {
        path = new ArrayList<>();
        visited = new HashSet<>();
    }

    @Override
    public SolveResults getPath(boolean[][] grid, Cell start, Cell end) {
        path.clear();
        visited.clear();
        this.grid = grid;
        this.end = end;

        if (grid == null || grid.length == 0) return new SolveResults(path, visited);

        if (findPath(start)) {
            return new SolveResults(path, visited);
        }

        return new SolveResults(new ArrayList<>(), visited);
    }

    private boolean findPath(Cell current) {
        if (!isInMaze(current) || !isValid(current)) return false;

        visited.add(current);
        path.add(current);

        if (current.equals(end)) return true;

        if (findPath(new Cell(current.getRow() + 1, current.getCol())) ||  // abajo
                findPath(new Cell(current.getRow(), current.getCol() + 1)) ||  // derecha
                findPath(new Cell(current.getRow() - 1, current.getCol())) ||  // arriba
                findPath(new Cell(current.getRow(), current.getCol() - 1))) {  // izquierda
            return true;
        }

        path.remove(path.size() - 1);
        return false;
    }

    private boolean isValid(Cell current) {
        return grid[current.getRow()][current.getCol()] && !visited.contains(current);
    }

    private boolean isInMaze(Cell current) {
        int r = current.getRow(), c = current.getCol();
        return r >= 0 && r < grid.length && c >= 0 && c < grid[0].length;
    }
}

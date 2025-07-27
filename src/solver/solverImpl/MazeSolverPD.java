package solver.solverImpl;

import solver.MazeSolver;
import models.Cell;
import models.SolveResults;

import java.util.*;

public class MazeSolverPD implements MazeSolver {

    private List<Cell> path;
    private Set<Cell> visited;
    private boolean[][] grid;
    private Cell end;
    private Map<String, Boolean> memo;

    public MazeSolverPD() {
        path = new ArrayList<>();
        visited = new HashSet<>();
        memo = new HashMap<>();
    }

    @Override
    public SolveResults getPath(boolean[][] grid, Cell start, Cell end) {
        path.clear();
        visited.clear();
        memo.clear();
        this.grid = grid;
        this.end = end;

        if (grid == null || grid.length == 0) {
            return new SolveResults(new ArrayList<>(), new HashSet<>());
        }

        if (findPath(start)) {
            return new SolveResults(path, visited);
        }

        return new SolveResults(new ArrayList<>(), visited);
    }

    private boolean findPath(Cell current) {
        if (!isInMaze(current)) return false;
        if (!isValid(current)) return false;

        String key = current.getRow() + "," + current.getCol();
        if (memo.containsKey(key)) return memo.get(key);

        visited.add(current);
        path.add(current);

        if (current.equals(end)) {
            memo.put(key, true);
            return true;
        }

        int r = current.getRow();
        int c = current.getCol();

        boolean found =
                findPath(new Cell(r + 1, c)) ||
                        findPath(new Cell(r, c + 1)) ||
                        findPath(new Cell(r - 1, c)) ||
                        findPath(new Cell(r, c - 1));

        if (!found) {
            path.remove(path.size() - 1);
        }

        memo.put(key, found);
        return found;
    }

    private boolean isValid(Cell cell) {
        return grid[cell.getRow()][cell.getCol()] && !visited.contains(cell);
    }

    private boolean isInMaze(Cell cell) {
        int r = cell.getRow(), c = cell.getCol();
        return r >= 0 && r < grid.length && c >= 0 && c < grid[0].length;
    }
}

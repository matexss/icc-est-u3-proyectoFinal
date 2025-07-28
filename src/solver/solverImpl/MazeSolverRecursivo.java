package solver.solverImpl;

import solver.MazeSolver;
import models.*;

import java.util.*;

/**
 * Algoritmo recursivo simple (solo abajo y derecha).
 */
public class MazeSolverRecursivo implements MazeSolver {

    private Set<Cell> visited;
    private List<Cell> path;
    private Cell[][] maze;
    private Cell end;

    @Override
    public SolveResults getPath(Cell[][] maze, Cell start, Cell end) {
        this.visited = new LinkedHashSet<>();
        this.path = new ArrayList<>();
        this.maze = maze;
        this.end = end;

        findPath(start);
        return new SolveResults(path, visited);
    }

    private boolean findPath(Cell current) {
        int r = current.getRow(), c = current.getCol();

        if (!isValid(r, c) || visited.contains(current)) return false;

        visited.add(current);
        path.add(current);

        if (current.equals(end)) return true;

        // Movimiento solo a la derecha y abajo, pero con validación previa
        if (r + 1 < maze.length && findPath(maze[r + 1][c])) return true;
        if (c + 1 < maze[0].length && findPath(maze[r][c + 1])) return true;

        path.remove(path.size() - 1);
        return false;
    }

    private boolean isValid(int r, int c) {
        return r >= 0 && r < maze.length && c >= 0 && c < maze[0].length && maze[r][c].getState() != CellState.WALL;
    }
}

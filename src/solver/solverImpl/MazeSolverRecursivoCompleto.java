package solver.solverImpl;

import solver.MazeSolver;
import models.*;

import java.util.*;

/**
 * Algoritmo recursivo completo: explora en las 4 direcciones.
 */
public class MazeSolverRecursivoCompleto implements MazeSolver {

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

        boolean encontrado = findPath(start);

        if (!encontrado) path.clear();  // si no se llegó al final, no hay camino
        Collections.reverse(path); // porque se construye hacia atrás

        return new SolveResults(path, visited);
    }

    private boolean findPath(Cell current) {
        int r = current.getRow(), c = current.getCol();

        if (!isValid(r, c) || visited.contains(current)) return false;

        visited.add(current);
        path.add(current);

        if (current.equals(end)) return true;

        // Exploración en 4 direcciones con validación previa
        int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};
        for (int[] dir : dirs) {
            int nr = r + dir[0], nc = c + dir[1];
            if (nr >= 0 && nr < maze.length && nc >= 0 && nc < maze[0].length) {
                if (findPath(maze[nr][nc])) return true;
            }
        }

        // Backtrack
        path.remove(path.size() - 1);
        return false;
    }

    private boolean isValid(int r, int c) {
        return r >= 0 && r < maze.length && c >= 0 && c < maze[0].length
                && maze[r][c].getState() != CellState.WALL;
    }
}

package solver.solverImpl;

import solver.MazeSolver;
import models.*;

import java.util.*;

/**
 * Implementación del algoritmo BFS para resolver laberintos.
 */
public class MazeSolverBFS implements MazeSolver {

    @Override
    public SolveResults getPath(Cell[][] maze, Cell start, Cell end) {
        int rows = maze.length;
        int cols = maze[0].length;
        boolean[][] visitedMatrix = new boolean[rows][cols];
        Map<Cell, Cell> parent = new HashMap<>();
        Queue<Cell> queue = new LinkedList<>();
        List<Cell> visited = new ArrayList<>();

        queue.add(start);
        visitedMatrix[start.getRow()][start.getCol()] = true;

        while (!queue.isEmpty()) {
            Cell current = queue.poll();
            visited.add(current);

            if (current.equals(end)) break;

            for (int[] dir : new int[][]{{1,0},{-1,0},{0,1},{0,-1}}) {
                int r = current.getRow() + dir[0];
                int c = current.getCol() + dir[1];

                if (r >= 0 && r < rows && c >= 0 && c < cols && !visitedMatrix[r][c] && maze[r][c].getState() != CellState.WALL) {
                    Cell neighbor = maze[r][c];
                    visitedMatrix[r][c] = true;
                    parent.put(neighbor, current);
                    queue.offer(neighbor);
                }
            }
        }

        List<Cell> path = new ArrayList<>();
        Cell current = end;
        while (parent.containsKey(current)) {
            path.add(current);
            current = parent.get(current);
        }

        if (current.equals(start)) {
            path.add(start);
            Collections.reverse(path);
        } else {
            path.clear(); // no se llegó
        }

        return new SolveResults(path, new HashSet<>(visited));
    }
}

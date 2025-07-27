package solver.solverImpl;

import solver.MazeSolver;
import models.Cell;
import models.SolveResults;

import java.util.*;

public class MazeSolverBFS implements MazeSolver {

    @Override
    public SolveResults getPath(boolean[][] grid, Cell start, Cell end) {
        Queue<Cell> queue = new LinkedList<>();
        Set<Cell> visited = new HashSet<>();
        Map<Cell, Cell> parent = new HashMap<>();
        queue.offer(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Cell current = queue.poll();
            if (current.equals(end)) {
                return reconstructPath(start, end, parent, visited);
            }

            for (Cell neighbor : getNeighbors(current, grid)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parent.put(neighbor, current);
                    queue.offer(neighbor);
                }
            }
        }

        // No se encontró camino
        return new SolveResults(new ArrayList<>(), visited);
    }

    private List<Cell> getNeighbors(Cell cell, boolean[][] grid) {
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        List<Cell> neighbors = new ArrayList<>();
        for (int[] d : dirs) {
            int r = cell.getRow() + d[0];
            int c = cell.getCol() + d[1];
            if (r >= 0 && r < grid.length && c >= 0 && c < grid[0].length && grid[r][c]) {
                neighbors.add(new Cell(r, c));
            }
        }
        return neighbors;
    }

    private SolveResults reconstructPath(Cell start, Cell end, Map<Cell, Cell> parent, Set<Cell> visited) {
        List<Cell> path = new ArrayList<>();
        Cell current = end;
        while (!current.equals(start)) {
            path.add(current);
            current = parent.get(current);
        }
        path.add(start);
        Collections.reverse(path);
        return new SolveResults(path, visited);
    }
}

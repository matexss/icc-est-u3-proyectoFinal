package models;

import java.util.List;
import java.util.Set;

/**
 * Contiene el camino encontrado y las celdas exploradas durante la resolución del laberinto.
 */
public class SolveResults {

    private final List<Cell> path;
    private final Set<Cell> visited;

    public SolveResults(List<Cell> path, Set<Cell> visited) {
        this.path = path;
        this.visited = visited;
    }

    public List<Cell> getPath() {
        return path;
    }

    public Set<Cell> getVisited() {
        return visited;
    }
}

package models;

import java.util.List;
import java.util.Set;


public class SolveResults {

    private List<Cell> path;
    private Set<Cell> visited;

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

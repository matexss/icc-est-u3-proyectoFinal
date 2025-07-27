package solver;

import models.Cell;
import models.SolveResults;

public interface MazeSolver {
    SolveResults getPath(boolean[][] grid, Cell start, Cell end);
}

import controllers.MazeController;
import dao.AlgorithmResultDAO;
import dao.Impl.AlgorithmResultDAOFile;
import solver.MazeSolver;
import solver.solverImpl.*;

import views.MazeFrame;

import javax.swing.*;

public class MazeApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Inicializar DAO
            AlgorithmResultDAO resultDAO = new AlgorithmResultDAOFile();

            // Inicializar controlador
            MazeController controller = new MazeController(resultDAO);

            // Registrar algoritmos disponibles
            controller.registrarAlgoritmo("DFS", new MazeSolverDFS());
            controller.registrarAlgoritmo("BFS", new MazeSolverBFS());
            controller.registrarAlgoritmo("Recursivo", new MazeSolverRecursivo());
            controller.registrarAlgoritmo("Recursivo Completo", new MazeSolverRecursivoCompleto());
            controller.registrarAlgoritmo("Recursivo Backtracking", new MazeSolverRecursivoCompletoBT());
            controller.registrarAlgoritmo("Programación Dinámica", new MazeSolverPD());

            // Lanzar la interfaz gráfica
            new MazeFrame(controller);
        });
    }
}

import controllers.MazeController;
import dao.AlgorithmResultDAO;
import dao.Impl.AlgorithmResultDAOFile;
import solver.solverImpl.*;

import views.MazeFrame;
import views.MazePanel;

import javax.swing.*;

public class MazeApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Inicializar DAO
            AlgorithmResultDAO resultDAO = new AlgorithmResultDAOFile();

            MazePanel panel = new MazePanel();

            // Crear controlador
            MazeController controller = new MazeController(resultDAO);
            controller.setMatriz(panel.getCells());
            panel.setController(controller); // conectar panel con controlador

            controller.registrarAlgoritmo("DFS", new MazeSolverDFS());
            controller.registrarAlgoritmo("BFS", new MazeSolverBFS());
            controller.registrarAlgoritmo("RECURSIVO", new MazeSolverRecursivo());
            controller.registrarAlgoritmo("RECURSIVO COMPLETO", new MazeSolverRecursivoCompleto());
            controller.registrarAlgoritmo("RECURSIVO COMPLETO BT", new MazeSolverRecursivoCompletoBT());
            controller.registrarAlgoritmo("BACKTRACKING", new MazeSolverRecursivoCompletoBT());

            new MazeFrame(controller, panel);
        });
    }
}

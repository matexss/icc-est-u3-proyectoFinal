package controllers;

import dao.AlgorithmResultDAO;
import models.AlgorithmResult;
import models.Cell;
import models.SolveResults;
import solver.MazeSolver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MazeController {

    private AlgorithmResultDAO resultDAO;
    private Map<String, MazeSolver> algoritmos;

    public MazeController(AlgorithmResultDAO resultDAO) {
        this.resultDAO = resultDAO;
        this.algoritmos = new HashMap<>();
    }


    public void registrarAlgoritmo(String nombre, MazeSolver solver) {
        algoritmos.put(nombre, solver);
    }

    public SolveResults ejecutar(String nombreAlgoritmo, boolean[][] grid, Cell inicio, Cell fin) {
        MazeSolver solver = algoritmos.get(nombreAlgoritmo);
        if (solver == null) {
            throw new IllegalArgumentException("Algoritmo no registrado: " + nombreAlgoritmo);
        }

        long inicioTiempo = System.currentTimeMillis();
        SolveResults resultado = solver.getPath(grid, inicio, fin);
        long finTiempo = System.currentTimeMillis();

        int longitud = resultado.getPath().size();
        long tiempoTotal = finTiempo - inicioTiempo;

        AlgorithmResult registro = new AlgorithmResult(nombreAlgoritmo, tiempoTotal, longitud);
        resultDAO.guardarResultado(registro);

        return resultado;
    }

    public List<AlgorithmResult> listarResultados() {
        return resultDAO.obtenerResultados();
    }

    public void limpiarResultados() {
        resultDAO.limpiarResultados();
    }

    public boolean existeAlgoritmo(String nombre) {
        return algoritmos.containsKey(nombre);
    }

    public Set<String> obtenerNombresAlgoritmos() {
        return algoritmos.keySet();
    }
}

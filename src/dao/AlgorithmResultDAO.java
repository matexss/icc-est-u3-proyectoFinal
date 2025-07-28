package dao;

import models.AlgorithmResult;

import java.util.List;

/**
 * Interfaz para acceder y manipular los resultados de los algoritmos.
 */
public interface AlgorithmResultDAO {
    void guardarResultado(AlgorithmResult resultado);
    List<AlgorithmResult> obtenerResultados();
    void limpiarResultados();
}

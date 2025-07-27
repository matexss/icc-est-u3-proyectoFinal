package dao.Impl;

import dao.AlgorithmResultDAO;
import models.AlgorithmResult;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class AlgorithmResultDAOFile implements AlgorithmResultDAO {

    private static final String FILE_PATH = "results.csv";

    @Override
    public void guardarResultado(AlgorithmResult nuevo) {
        List<AlgorithmResult> existentes = obtenerResultados();
        boolean actualizado = false;

        for (int i = 0; i < existentes.size(); i++) {
            AlgorithmResult r = existentes.get(i);
            if (r.getAlgorithmName().equalsIgnoreCase(nuevo.getAlgorithmName())) {
                existentes.set(i, nuevo); // Actualiza
                actualizado = true;
                break;
            }
        }

        if (!actualizado) {
            existentes.add(nuevo);
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (AlgorithmResult r : existentes) {
                writer.println(r.toString()); // Formato: nombre,tiempo,longitud,timestamp
            }
        } catch (IOException e) {
            System.err.println("Error al guardar resultados: " + e.getMessage());
        }
    }

    @Override
    public List<AlgorithmResult> obtenerResultados() {
        List<AlgorithmResult> resultados = new ArrayList<>();
        File archivo = new File(FILE_PATH);

        if (!archivo.exists()) return resultados;

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 4) {
                    String nombre = partes[0];
                    long tiempo = Long.parseLong(partes[1]);
                    int longitud = Integer.parseInt(partes[2]);
                    LocalDateTime fecha = LocalDateTime.parse(partes[3]);
                    resultados.add(new AlgorithmResult(nombre, tiempo, longitud));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al leer resultados: " + e.getMessage());
        }

        return resultados;
    }

    @Override
    public void limpiarResultados() {
        File archivo = new File(FILE_PATH);
        if (archivo.exists()) {
            archivo.delete();
        }
    }
}

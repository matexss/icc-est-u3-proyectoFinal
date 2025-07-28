package models;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Representa el resultado de un algoritmo de búsqueda.
 */
public class AlgorithmResult implements Serializable {

    private final String algorithmName;
    private final int pathLength;
    private final long executionTimeMillis;
    private final LocalDateTime timestamp;

    public AlgorithmResult(String algorithmName, int pathLength, long executionTimeMillis) {
        this.algorithmName = algorithmName;
        this.pathLength = pathLength;
        this.executionTimeMillis = executionTimeMillis;
        this.timestamp = LocalDateTime.now();
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public int getPathLength() {
        return pathLength;
    }

    public long getExecutionTimeMillis() {
        return executionTimeMillis;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return algorithmName + "," + pathLength + "," + executionTimeMillis + "," + timestamp;
    }
}

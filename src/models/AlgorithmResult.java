package models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AlgorithmResult implements Serializable {

    private String algorithmName;
    private long executionTimeMillis;
    private int pathLength;
    private LocalDateTime timestamp;

    public AlgorithmResult(String algorithmName, long executionTimeMillis, int pathLength) {
        this.algorithmName = algorithmName;
        this.executionTimeMillis = executionTimeMillis;
        this.pathLength = pathLength;
        this.timestamp = LocalDateTime.now();
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public long getExecutionTimeMillis() {
        return executionTimeMillis;
    }

    public int getPathLength() {
        return pathLength;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return algorithmName + "," + executionTimeMillis + "," + pathLength + "," + timestamp;
    }
}

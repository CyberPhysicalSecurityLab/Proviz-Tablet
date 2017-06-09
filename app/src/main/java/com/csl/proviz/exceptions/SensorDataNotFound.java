package com.csl.proviz.exceptions;

/**
 * Created by Burak on 5/11/17.
 */

public class SensorDataNotFound extends Exception {
    private String sensorId;
    private String variableName;

    @Override
    public String toString() {
        return "SensorDataNotFound{" +
                "sensorId='" + sensorId + '\'' +
                ", variableName='" + variableName + '\'' +
                '}';
    }

    public SensorDataNotFound(String sensorId, String variableName) {
        super();
        this.sensorId = sensorId;
        this.variableName = variableName;
    }
}

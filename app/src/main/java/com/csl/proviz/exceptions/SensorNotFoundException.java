package com.csl.proviz.exceptions;

/**
 * Created by Burak on 5/11/17.
 */

public class SensorNotFoundException extends Exception {

    private String sensorId;

    public SensorNotFoundException(String sensorId) {
        super();
        this.sensorId = sensorId;
    }

    @Override
    public String toString() {
        return "SensorNotFoundException{" +
                "sensorId='" + sensorId + '\'' +
                '}';
    }
}

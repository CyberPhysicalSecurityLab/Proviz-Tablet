package com.csl.proviz.model.webservice;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by Burak on 5/11/17.
 */

public class SensorVariable {
    @JsonIgnore
    private Sensor sensor;
    private String variableName;
    private double minThreshold;
    private double maxThreshold;
    private double sampleRate;

    public SensorVariable() {
    }

    public Sensor getSensor() {

        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public double getMinThreshold() {
        return minThreshold;
    }

    public void setMinThreshold(double minThreshold) {
        this.minThreshold = minThreshold;
    }

    public double getMaxThreshold() {
        return maxThreshold;
    }

    public void setMaxThreshold(double maxThreshold) {
        this.maxThreshold = maxThreshold;
    }

    public double getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(double sampleRate) {
        this.sampleRate = sampleRate;
    }


}

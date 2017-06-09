package com.csl.proviz.model.webservice;

import com.csl.proviz.ProjectConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by Burak on 5/9/17.
 */

public class SensorData {

    private String sensorName;
    private String sensorId;
    private String sensorUnit;
    private double sensorValue;
    private String variableName;

    @JsonIgnore
    private Sensor parentSensor;

    @JsonIgnore
    private ProjectConstants.MESSAGE_TYPE message;

    @JsonIgnore
    private SensorVariable sensorVariable;

    public SensorVariable getSensorVariable() {
        return sensorVariable;
    }

    public void setSensorVariable(SensorVariable sensorVariable) {
        this.sensorVariable = sensorVariable;
    }

    public ProjectConstants.MESSAGE_TYPE getMessage() {
        return message;
    }

    public void setMessage(ProjectConstants.MESSAGE_TYPE message) {
        this.message = message;
    }

    public Sensor getParentSensor() {
        return parentSensor;
    }

    public void setParentSensor(Sensor parentSensor) {
        this.parentSensor = parentSensor;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }



    public double getSensorValue() {
        return sensorValue;
    }

    public void setSensorValue(double sensorValue) {
        this.sensorValue = sensorValue;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getSensorUnit() {
        return sensorUnit;
    }

    public void setSensorUnit(String sensorUnit) {
        this.sensorUnit = sensorUnit;
    }
}

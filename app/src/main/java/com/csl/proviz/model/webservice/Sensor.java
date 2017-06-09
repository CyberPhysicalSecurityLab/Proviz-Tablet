package com.csl.proviz.model.webservice;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

/**
 * Created by Burak on 5/11/17.
 */

public class Sensor {

    private ArrayList<SensorVariable> sensorVariables;


    @JsonIgnore
    private Board parentBoard;




    @JsonIgnore
    private ArrayList<SensorData> sensorData;

    public ArrayList<SensorData> getSensorData() {
        return sensorData;
    }

    public void setSensorData(ArrayList<SensorData> sensorData) {
        this.sensorData = sensorData;
    }


    private String sensorName;
    private String sensorId;

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

    public Board getParentBoard() {
        return parentBoard;
    }

    public void setParentBoard(Board parentBoard) {
        this.parentBoard = parentBoard;
    }



    public ArrayList<SensorVariable> getSensorVariables() {
        return sensorVariables;
    }

    public void setSensorVariables(ArrayList<SensorVariable> sensorVariables) {
        this.sensorVariables = sensorVariables;
        for(SensorVariable sensorVariable:sensorVariables)
        {
            SensorData sensorDataElement = new SensorData();
            sensorDataElement.setVariableName(sensorVariable.getVariableName());
            sensorData.add(sensorDataElement);
        }
    }

    public Sensor() {
sensorData = new ArrayList<>();
    }
}

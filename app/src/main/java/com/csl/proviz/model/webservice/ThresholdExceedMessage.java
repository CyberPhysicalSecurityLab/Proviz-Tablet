package com.csl.proviz.model.webservice;

import com.csl.proviz.ProjectConstants;

/**
 * Created by Burak on 5/10/17.
 */

public class ThresholdExceedMessage {
    private Board board;
    private String sensorId;
    private double sensorValue;
    private ProjectConstants.MESSAGE_TYPE message_type;

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
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

    public ProjectConstants.MESSAGE_TYPE getMessage_type() {
        return message_type;
    }

    public void setMessage_type(ProjectConstants.MESSAGE_TYPE message_type) {
        this.message_type = message_type;
    }
}

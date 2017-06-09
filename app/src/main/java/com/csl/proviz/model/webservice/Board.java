package com.csl.proviz.model.webservice;

import com.csl.proviz.BoardView;
import com.csl.proviz.IncomingDataListener;
import com.csl.proviz.ProjectConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

/**
 * Created by Burak on 5/9/17.
 */

public class Board {
    private String boardId;
    private String boardName;
    /*
    0 - Arduino
    1 - Raspberry Pi
    2 - BeagleBone
     */
    private ProjectConstants.DEVICE_TYPE boardType;
    private ArrayList<Sensor> sensors;

    @JsonIgnore
    ArrayList<IncomingDataListener> subscribers;

    @JsonIgnore
    private BoardView boardView;

    public BoardView getBoardView() {
        return boardView;
    }

    public void setBoardView(BoardView boardView) {
        this.boardView = boardView;
    }

    public Board() {
        subscribers = new ArrayList<>();
    }

    public String getBoardId() {

        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public ArrayList<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(ArrayList<Sensor> sensors) {
        this.sensors = sensors;
    }


    public ProjectConstants.DEVICE_TYPE getBoardType() {
        return boardType;
    }

    public void setBoardType(ProjectConstants.DEVICE_TYPE boardType) {
        this.boardType = boardType;
    }

    public void addSubscribe(IncomingDataListener incomingDataListener)
    {
        subscribers.add(incomingDataListener);
    }
    public void notifySubscribersForNewValue()
    {
        for(IncomingDataListener incomingDataListener:subscribers)
        {
            incomingDataListener.onDataReceived();
        }
    }

    public void unscribeBoard(Object listener)
    {
        subscribers.remove(listener);
    }
}

package com.csl.proviz;

import com.csl.proviz.exceptions.BoardNotFoundException;
import com.csl.proviz.exceptions.SensorDataNotFound;
import com.csl.proviz.exceptions.SensorNotFoundException;
import com.csl.proviz.model.webservice.Board;
import com.csl.proviz.model.webservice.Cluster;
import com.csl.proviz.model.webservice.Sensor;
import com.csl.proviz.model.webservice.SensorData;
import com.csl.proviz.model.webservice.SensorVariable;

import java.util.ArrayList;

/**
 * Created by Burak on 5/10/17.
 */

public class DataManager {
    private static DataManager self;
    private ArrayList<IncomingDataListener> subscriberList;
    public static DataManager getInstance()
    {
     if(self == null)
         self = new DataManager();
        return self;
    }

    public DataManager(){subscriberList = new ArrayList<>();}
    public Cluster getCluster() {
        return cluster;
    }

    private Cluster cluster;


    public void subscribeIncomingDataListener(IncomingDataListener incomingDataListener)
    {
        subscriberList.add(incomingDataListener);
    }
    public void updateValues(Cluster incomingCluster)
    {
        if(cluster.getSessionId().contains(incomingCluster.getSessionId()))
        {
          this.cluster = incomingCluster;
//            ThresholdExceedMessage thresholdExceedMessage = isAnySensorHasExceedValue();
//            if(thresholdExceedMessage != null)
//                notifyThresholdExceed(thresholdExceedMessage);

        }
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    public void bindAllPiecesTogether()
    {
       ArrayList<Board> boards = cluster.getBoards();
        for(Board board: boards)
        {
            for(Sensor sensor: board.getSensors())
            {
                sensor.setParentBoard(board);
                for(SensorVariable sensorVariable: sensor.getSensorVariables())
                {
                    sensorVariable.setSensor(sensor);
                }
                for(SensorVariable sensorVariable: sensor.getSensorVariables())
                {
                    sensorVariable.setSensor(sensor);
                }
                for(SensorData sensorData: sensor.getSensorData())
                {
                    sensorData.setParentSensor(sensor);
                    for(SensorVariable sensorVariable: sensor.getSensorVariables())
                    {
                        if(sensorVariable.getVariableName().compareTo(sensorData.getVariableName()) == 0)
                        {
                            sensorData.setSensorVariable(sensorVariable);
                            break;
                        }
                    }

                }
            }
        }
    }

    public void replaceSensorDataValuesWithNewData(Sensor sensor, SensorData sensorData) throws SensorDataNotFound {
        for(SensorData oldsensorData:sensor.getSensorData())
        {
            if(oldsensorData.getVariableName().compareTo(sensorData.getVariableName()) == 0)
            {
                oldsensorData.setSensorUnit(sensorData.getSensorUnit());
                oldsensorData.setSensorName(sensorData.getSensorName());
                oldsensorData.setSensorId(sensorData.getSensorId());
                double value = sensorData.getSensorValue();
                oldsensorData.setSensorValue(value);
                SensorVariable sensorVariable = oldsensorData.getSensorVariable();
                if(value > sensorVariable.getMaxThreshold())
                {
                    oldsensorData.setMessage(ProjectConstants.MESSAGE_TYPE.UPPERBOUNDEXCEED);
                }
                else if(value< sensorVariable.getMinThreshold())
                {
                    oldsensorData.setMessage(ProjectConstants.MESSAGE_TYPE.LOWERBOUNDEXCEED);
                }
                else
                {
                    oldsensorData.setMessage(ProjectConstants.MESSAGE_TYPE.NORMAL);
                }

                return;
            }
        }
        throw new SensorDataNotFound(sensor.getSensorId(),sensorData.getVariableName());
    }

    public Board findBoardbyBoardId(String boardID) throws BoardNotFoundException
    {
        ArrayList<Board> boards = cluster.getBoards();
        for(Board board: boards)
        {
            if(board.getBoardId().compareTo(boardID) == 0)
            {
             return board;
            }

        }
        throw new BoardNotFoundException(boardID);

    }

    public Sensor findSensorbySensorId(Board board,String sensorId) throws SensorNotFoundException
    {
        ArrayList<Sensor> sensors = board.getSensors();
        for(Sensor sensor:sensors)
        {
            if(sensor.getSensorId().compareTo(sensorId) ==0)
            {
                return sensor;
            }


        }
        throw new SensorNotFoundException(sensorId);

    }



//    private ThresholdExceedMessage isAnySensorHasExceedValue()
//    {
//        for(Board board: cluster.getBoards())
//        {
//            for(Sensor sensor: board.getSensors()) {
//                for (SensorData sensorData : sensor.getSensorData()) {
//                    double maxVal = sensor.getMaxThreshold();
//                    double minVal = sensor.getMinThreshold();
//                    if (sensorData.getSensorValue() > maxVal) {
//                        ThresholdExceedMessage thresholdExceedMessage = new ThresholdExceedMessage();
//                        thresholdExceedMessage.setBoard(board);
//                        thresholdExceedMessage.setMessage_type(ProjectConstants.MESSAGE_TYPE.UPPERBOUNDEXCEED);
//                        return thresholdExceedMessage;
//                    } else if (sensorData.getSensorValue() < minVal) {
//                        ThresholdExceedMessage thresholdExceedMessage = new ThresholdExceedMessage();
//                        thresholdExceedMessage.setBoard(board);
//                        thresholdExceedMessage.setMessage_type(ProjectConstants.MESSAGE_TYPE.LOWERBOUNDEXCEED);
//                        return thresholdExceedMessage;
//                    }
//
//                }
//            }
//        }
//        return null;
//    }
}

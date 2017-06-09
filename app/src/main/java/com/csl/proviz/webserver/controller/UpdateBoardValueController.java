package com.csl.proviz.webserver.controller;

import com.csl.proviz.DataManager;
import com.csl.proviz.ProjectConstants;
import com.csl.proviz.model.webservice.Board;
import com.csl.proviz.model.webservice.IncomingDeviceData;
import com.csl.proviz.model.webservice.Sensor;
import com.csl.proviz.model.webservice.SensorData;
import com.csl.proviz.model.webservice.response.GetValueFromDeviceResponse;
import com.csl.proviz.webserver.WebServiceManager;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ws.rs.Path;

/**
 * Created by Burak on 5/10/17.
 */
@Path("/sendvalue")
public class UpdateBoardValueController extends HttpHandler {

    private ObjectMapper objectMapper;
    private volatile Object lock = new Object();
    public UpdateBoardValueController()
    {
        super();
        objectMapper = new ObjectMapper();
    }
//    public GetValueFromDeviceResponse updateBoardValues(IncomingDeviceData request)
//    {
//        GetValueFromDeviceResponse getValueFromDeviceResponse = new GetValueFromDeviceResponse();
//        try{
//        Board board  = DataManager.getInstance().findBoardbyBoardId(request.getDeviceId());
//            for(Sensor sensor: board.getSensors()) {
//                for (SensorData sensorData : request.getSensors()) {
//                    if(sensor.getSensorId().compareTo(sensorData.getSensorId()) == 0)
//                    {
//                        DataManager.getInstance().replaceSensorDataValuesWithNewData(sensor,sensorData);
//
//                    }
//                }
//            }
//
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//            getValueFromDeviceResponse.setResult(ProjectConstants.OPERATION_RESULT.FAIL);
//        }
//        getValueFromDeviceResponse.setResult(ProjectConstants.OPERATION_RESULT.SUCCESS);
//        return getValueFromDeviceResponse;
//    }

    @Override
    public void service(Request request, Response response) throws Exception {
        response.setContentType("application/json");
        GetValueFromDeviceResponse getValueFromDeviceResponse = new GetValueFromDeviceResponse();
        try {


            String requestBody = WebServiceManager.getRequestBody(request);

            synchronized (lock) {
                IncomingDeviceData incomingDeviceData = objectMapper.readValue(requestBody, IncomingDeviceData.class);



                    Board board = DataManager.getInstance().findBoardbyBoardId(incomingDeviceData.getDeviceId());
                    for (Sensor sensor : board.getSensors()) {
                        for (SensorData sensorData : incomingDeviceData.getSensors()) {
                            if (sensor.getSensorId().compareTo(sensorData.getSensorId()) == 0) {
                                DataManager.getInstance().replaceSensorDataValuesWithNewData(sensor, sensorData);

                            }
                        }
                    }

                board.notifySubscribersForNewValue();
                getValueFromDeviceResponse.setResult(ProjectConstants.OPERATION_RESULT.SUCCESS);




            }


        }
//        IOException, JsonParseException, JsonMappingException
        catch (JsonParseException jsonParseException)
        {
            getValueFromDeviceResponse.setResult(ProjectConstants.OPERATION_RESULT.FAIL);
            jsonParseException.printStackTrace();
        }
        catch(JsonMappingException jsonMappingException)
        {
            getValueFromDeviceResponse.setResult(ProjectConstants.OPERATION_RESULT.FAIL);
            jsonMappingException.printStackTrace();
        }
        catch(IOException ioException)
        {
            getValueFromDeviceResponse.setResult(ProjectConstants.OPERATION_RESULT.FAIL);
ioException.printStackTrace();
        }
        String responseBody = objectMapper.writeValueAsString(getValueFromDeviceResponse);

        PrintWriter printWriter = new PrintWriter(response.getOutputStream());
        printWriter.write(responseBody);
        printWriter.flush();
        printWriter.close();

    }


}

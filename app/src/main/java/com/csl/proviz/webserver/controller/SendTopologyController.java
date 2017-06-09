package com.csl.proviz.webserver.controller;

import com.csl.proviz.DataManager;
import com.csl.proviz.MainActivity;
import com.csl.proviz.ProjectConstants;
import com.csl.proviz.model.webservice.request.TopologyRequest;
import com.csl.proviz.model.webservice.response.TopologyResponse;
import com.csl.proviz.webserver.WebServiceManager;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;

import java.io.PrintWriter;

import javax.ws.rs.Path;

/**
 * Created by Burak on 5/11/17.
 */
@Path("/sendtopology")
public class SendTopologyController extends HttpHandler{
    private ObjectMapper objectMapper;
    private volatile Object lock= new Object();
    private MainActivity mainActivity;

    public SendTopologyController(MainActivity mainActivity)
    {
        super();
        objectMapper = new ObjectMapper();
        this.mainActivity = mainActivity;
    }

    public TopologyResponse updateToplogy(TopologyRequest request)
    {
        TopologyResponse topologyResponse = new TopologyResponse();


        try{
            DataManager.getInstance().setCluster(request.getCluster());
            DataManager.getInstance().bindAllPiecesTogether();

            topologyResponse.setOperationResult(ProjectConstants.OPERATION_RESULT.SUCCESS);

        }
        catch (Exception ex)
        {
            topologyResponse.setOperationResult(ProjectConstants.OPERATION_RESULT.FAIL);
        }
        return topologyResponse;
    }

    @Override
    public void service(Request request, Response response) throws Exception {
        response.setContentType("application/json");
        TopologyResponse topologyResponse = new TopologyResponse();
        try{
            String requestBody = WebServiceManager.getRequestBody(request);

            TopologyRequest topologyRequest = objectMapper.readValue(requestBody,TopologyRequest.class);
            DataManager.getInstance().setCluster(topologyRequest.getCluster());
            DataManager.getInstance().bindAllPiecesTogether();

            topologyResponse.setOperationResult(ProjectConstants.OPERATION_RESULT.SUCCESS);
            ProjectConstants.getInstance().setConnected(true);
            mainActivity.changeView1Topology();
        }
        catch (Exception ex)
        {
            topologyResponse.setOperationResult(ProjectConstants.OPERATION_RESULT.FAIL);
            ex.printStackTrace();
        }
        String responseBody = objectMapper.writeValueAsString(topologyResponse);

        PrintWriter printWriter = new PrintWriter(response.getOutputStream());
        printWriter.write(responseBody);
        printWriter.flush();
        printWriter.close();


    }
}

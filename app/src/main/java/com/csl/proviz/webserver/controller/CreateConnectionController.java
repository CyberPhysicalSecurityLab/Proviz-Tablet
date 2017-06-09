package com.csl.proviz.webserver.controller;

import com.csl.proviz.ProjectConstants;
import com.csl.proviz.ProjectGlobals;
import com.csl.proviz.model.webservice.request.CreateConnectionRequest;
import com.csl.proviz.model.webservice.response.CreateConnectionResponse;
import com.csl.proviz.webserver.WebServiceManager;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;

import java.io.PrintWriter;

import javax.ws.rs.Path;

/**
 * Created by Burak on 5/9/17.
 */
@Path("/createconnection")
public class CreateConnectionController extends HttpHandler{

    private ObjectMapper objectMapper;
    private volatile Object lock = new Object();

    public CreateConnectionController() {
        super();
        objectMapper = new ObjectMapper();

    }

//    public CreateConnectionResponse createConnectionBetweenTabletAndDesktop(CreateConnectionRequest request)
//    {
//        CreateConnectionResponse createConnectionResponse = new CreateConnectionResponse();
//        if(request.getSessionId().contains(""))
//        {
//            ProjectGlobals.getInstance().setSessionId(request.getSessionId());
//            createConnectionResponse.setResponse(58);
//        }
//        else
//        {
//            createConnectionResponse.setResponse(67);
//        }
//        return createConnectionResponse;
//    }

    @Override
    public void service(Request request, Response response) throws Exception {

        response.setContentType("application/json");
        CreateConnectionResponse createConnectionResponse = new CreateConnectionResponse();
        try{
            String requestBody = WebServiceManager.getRequestBody(request);
            synchronized (lock)
            {
                CreateConnectionRequest createConnectionRequest = objectMapper.readValue(requestBody,CreateConnectionRequest.class);
                if(createConnectionRequest.getSessionId().contains(""))
                {
                    ProjectGlobals.getInstance().setSessionId(createConnectionRequest.getSessionId());
                    createConnectionResponse.setResponse(ProjectConstants.OPERATION_RESULT.SUCCESS);
                }
                else
                {
                    createConnectionResponse.setResponse(ProjectConstants.OPERATION_RESULT.FAIL);
                }
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            createConnectionResponse.setResponse(ProjectConstants.OPERATION_RESULT.FAIL);
        }
        String responseBody = objectMapper.writeValueAsString(createConnectionResponse);

        PrintWriter printWriter = new PrintWriter(response.getOutputStream());
        printWriter.write(responseBody);
        printWriter.flush();
        printWriter.close();
    }
}

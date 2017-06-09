package com.csl.proviz.webserver.controller;



import com.csl.proviz.webserver.WebServiceManager;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;

import java.io.PrintWriter;


/**
 * Created by Burak on 5/16/17.
 */

public class testController extends HttpHandler {



    @Override
    public void service(Request request, Response response) throws Exception {
        String responseBody = WebServiceManager.getRequestBody(request);
        response.setContentType("application/json");

        PrintWriter printWriter = new PrintWriter(response.getOutputStream());
        printWriter.write(responseBody);
        printWriter.flush();
        printWriter.close();

    }
}



package com.csl.proviz.webserver;

import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import com.csl.proviz.MainActivity;
import com.csl.proviz.webserver.controller.CreateConnectionController;
import com.csl.proviz.webserver.controller.SendTopologyController;
import com.csl.proviz.webserver.controller.UpdateBoardValueController;
import com.csl.proviz.webserver.controller.testController;

import org.apache.commons.io.IOUtils;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.Request;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by Burak on 5/9/17.
 */

public class WebServiceManager {
    // The Java method will process HTTP GET requests

    private   URI BASE_URI;

    private static WebServiceManager self;
    private boolean isAlreadyStarted;
    private HttpServer server;
    private MainActivity mainActivity;

    public static WebServiceManager getInstance(MainActivity mainActivity)
    {
        if(self == null)
            self = new WebServiceManager(mainActivity);
        return self;
    }

    public WebServiceManager(MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;
        BASE_URI = URI.create("http://"+ getIPAdress());
    }

    private String getIPAdress()
    {
        try {
            WifiManager wifiManager = (WifiManager) mainActivity.getSystemService(WIFI_SERVICE);

            return Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return "127.0.0.1";
        }
    }
    public void start() throws Exception {   try {

        if(isAlreadyStarted == true)
            throw new Exception("Webservice deamon has been already invoked.");
        isAlreadyStarted = true;
        Map<String, String> initParams = new HashMap<>();
//        initParams.put(
//                ServerProperties.PROVIDER_PACKAGES,
//                testController.class.getPackage().getName());
//        initParams.put("com.sun.jersey.api.json.POJOMappingFeature", "true");
//
//
//        ResourceConfig rc = new ResourceConfig();
//        rc.packages(testController.class.getPackage().getName());
//
//        final Map<String, Object> initParams2 = new HashMap<String, Object>();
//        initParams2.put("com.sun.jersey.config.property.packages", "rest");
//        initParams2.put("com.sun.jersey.api.json.POJOMappingFeature", "true");
//
//        rc.addProperties(initParams2);


        HttpServer httpServer = HttpServer.createSimpleServer("/", getIPAdress(), 9997);



        httpServer.getServerConfiguration().addHttpHandler(new testController(),"/time");
        httpServer.getServerConfiguration().addHttpHandler(new CreateConnectionController(),"/createconnection");
        httpServer.getServerConfiguration().addHttpHandler(new UpdateBoardValueController(),"/sendvalue");
        httpServer.getServerConfiguration().addHttpHandler(new SendTopologyController(mainActivity),"/sendtopology");

                Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                    @Override
                    public void run() {
                        server.shutdownNow();
                    }
                }));

        httpServer.start();
        System.out.println("WebService Started ");

        Thread.currentThread().join();

    } catch (IOException | InterruptedException ex) {
        ex.printStackTrace();
    }
    }

    public static String getRequestBody(Request request) throws IOException
    {
        StringWriter writer = new StringWriter();
        IOUtils.copy(request.getInputStream(), writer);
        String theString = writer.toString();
        request.getInputStream().close();
        return theString;

    }

    public void stopWebService()
    {

    }

    public boolean isAlreadyStarted() {
        return isAlreadyStarted;
    }
}


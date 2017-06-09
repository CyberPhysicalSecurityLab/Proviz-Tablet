package com.csl.proviz;

/**
 * Created by Burak on 5/10/17.
 */

public class ProjectConstants {
    private static ProjectConstants self;

    private boolean isConnected;

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public enum MESSAGE_TYPE{
        UPPERBOUNDEXCEED,
        LOWERBOUNDEXCEED,
        NORMAL
    }

    public enum OPERATION_RESULT{
        SUCCESS,
        FAIL
    }

    public enum DEVICE_TYPE
    {
        ARDUINO,
        RASPBERRY_PI,
        BEAGLEBONE,
        Server
    }



    public static ProjectConstants getInstance()
    {
        if(self == null)
            self = new ProjectConstants();
        return self;
    }
}

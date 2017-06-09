package com.csl.proviz;


/**
 * Created by Burak on 5/9/17.
 */

public class ProjectGlobals {

    private static ProjectGlobals self;

    private String sessionId;

    public ProjectGlobals()
    {
        sessionId = "";
    }
    public static ProjectGlobals getInstance()
    {
        if(self == null)
            self = new ProjectGlobals();
        return self;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}

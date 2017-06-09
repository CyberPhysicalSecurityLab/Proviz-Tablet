package com.csl.proviz.model.webservice.request;

/**
 * Created by Burak on 5/9/17.
 */

public class CreateConnectionRequest {
    private String sessionId;

    public CreateConnectionRequest() {}

    public String getSessionId() {

        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}

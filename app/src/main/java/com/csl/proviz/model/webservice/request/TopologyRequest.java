package com.csl.proviz.model.webservice.request;

import com.csl.proviz.model.webservice.Cluster;

/**
 * Created by Burak on 5/11/17.
 */

public class TopologyRequest {
    private String sessionId;
    private Cluster cluster;

    public TopologyRequest() {}

    public String getSessionId() {

        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }
}

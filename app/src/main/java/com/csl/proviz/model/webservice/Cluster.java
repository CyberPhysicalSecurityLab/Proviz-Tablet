package com.csl.proviz.model.webservice;

import java.util.ArrayList;

/**
 * Created by Burak on 5/9/17.
 */

public class Cluster {
    private String sessionId;
    private ArrayList<Board> boards;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public ArrayList<Board> getBoards() {
        return boards;
    }

    public void setBoards(ArrayList<Board> boards) {
        this.boards = boards;
    }

    public Cluster() {}
}

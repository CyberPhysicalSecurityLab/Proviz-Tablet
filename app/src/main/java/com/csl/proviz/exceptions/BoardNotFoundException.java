package com.csl.proviz.exceptions;

/**
 * Created by Burak on 5/11/17.
 */

public class BoardNotFoundException extends Exception{
    String boardId;

    @Override
    public String toString() {
        return "BoardNotFoundException{" +
                "boardId='" + boardId + '\'' +
                '}';
    }

    public BoardNotFoundException(String boardId) {
     super();
        this.boardId = boardId;
    }
}

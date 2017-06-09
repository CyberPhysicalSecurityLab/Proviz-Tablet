package com.csl.proviz.model.webservice.response;

import com.csl.proviz.ProjectConstants;

/**
 * Created by Burak on 5/9/17.
 */

public class UpdateBoardValueResponse {
    /*
    58 - SUCCESSFUL
    67 - FAIL
     */
    private ProjectConstants.OPERATION_RESULT result;

    public ProjectConstants.OPERATION_RESULT getResult() {
        return result;
    }

    public void setResult(ProjectConstants.OPERATION_RESULT result) {
        this.result = result;
    }

    public UpdateBoardValueResponse() {

    }
}

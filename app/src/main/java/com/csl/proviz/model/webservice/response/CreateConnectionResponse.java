package com.csl.proviz.model.webservice.response;

import com.csl.proviz.ProjectConstants;

/**
 * Created by Burak on 5/9/17.
 */

public class CreateConnectionResponse {
    /*
    58 - SUCCESS
    67 - ALREADY CONNECTED

     */
    private ProjectConstants.OPERATION_RESULT response;

    public CreateConnectionResponse() {}

    public ProjectConstants.OPERATION_RESULT getResponse() {
        return response;
    }

    public void setResponse(ProjectConstants.OPERATION_RESULT response) {
        this.response = response;
    }
}

package com.csl.proviz.model.webservice.response;

import com.csl.proviz.ProjectConstants;

/**
 * Created by Burak on 5/11/17.
 */

public class GetValueFromDeviceResponse {
    private ProjectConstants.OPERATION_RESULT result;

    public ProjectConstants.OPERATION_RESULT getResult() {
        return result;
    }

    public void setResult(ProjectConstants.OPERATION_RESULT result) {
        this.result = result;
    }
}

package com.csl.proviz.model.webservice.response;

import com.csl.proviz.ProjectConstants;

/**
 * Created by Burak on 5/11/17.
 */

public class TopologyResponse {
    private ProjectConstants.OPERATION_RESULT operationResult;

    public TopologyResponse() {}

    public ProjectConstants.OPERATION_RESULT getOperationResult() {

        return operationResult;
    }

    public void setOperationResult(ProjectConstants.OPERATION_RESULT operationResult) {
        this.operationResult = operationResult;
    }
}

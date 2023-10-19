package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.step.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class ResponsesFromAnExecutedUploadStepResponse {
    private List<ResponsesFromAnExecutedUploadFileStepResponse> files;

    public ResponsesFromAnExecutedUploadStepResponse(final List<ResponsesFromAnExecutedUploadFileStepResponse> files) {
        this.files = files;
    }

    public List<ResponsesFromAnExecutedUploadFileStepResponse> getFiles() {
        return files;
    }

    public void setFiles(List<ResponsesFromAnExecutedUploadFileStepResponse> files) {
        this.files = files;
    }

}

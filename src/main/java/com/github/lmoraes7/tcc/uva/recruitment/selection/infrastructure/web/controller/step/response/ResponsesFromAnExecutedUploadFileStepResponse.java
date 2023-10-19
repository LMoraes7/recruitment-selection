package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.step.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class ResponsesFromAnExecutedUploadFileStepResponse {
    private byte[] bytes;
    private String typeFile;

    public ResponsesFromAnExecutedUploadFileStepResponse(final byte[] bytes, final String typeFile) {
        this.bytes = bytes;
        this.typeFile = typeFile;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getTypeFile() {
        return typeFile;
    }

    public void setTypeFile(String typeFile) {
        this.typeFile = typeFile;
    }

}

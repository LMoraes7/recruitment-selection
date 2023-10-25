package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.handler.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class ErrorResponse {

    private String code;
    private String message;
    private List<ErrorMessageResponse> errors;

    public ErrorResponse(String code, String message, List<ErrorMessageResponse> errors) {
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ErrorMessageResponse> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorMessageResponse> errors) {
        this.errors = errors;
    }

}

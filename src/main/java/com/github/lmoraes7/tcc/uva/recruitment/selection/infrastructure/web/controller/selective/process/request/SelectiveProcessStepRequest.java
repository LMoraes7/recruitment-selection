package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.selective.process.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class SelectiveProcessStepRequest {
    @NotBlank
    private String identifier;
    @Positive
    private Long limitTime;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Long getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(Long limitTime) {
        this.limitTime = limitTime;
    }

}

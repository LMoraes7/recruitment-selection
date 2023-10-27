package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.feedback.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FeedbackRequest {
    private String additionalInfo;
    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("10.0")
    private BigDecimal pointing;

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public BigDecimal getPointing() {
        return pointing;
    }

    public void setPointing(BigDecimal pointing) {
        this.pointing = pointing;
    }

}

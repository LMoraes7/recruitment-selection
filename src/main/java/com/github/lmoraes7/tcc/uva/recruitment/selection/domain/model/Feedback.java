package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.Result;

import java.math.BigDecimal;

public final class Feedback {
    private String identifier;
    private Result result;
    private BigDecimal pointing;
    private String additionalInfo;

    public Feedback(
            final String identifier,
            final Result result,
            final BigDecimal pointing,
            final String additionalInfo
    ) {
        this.identifier = identifier;
        this.result = result;
        this.pointing = pointing;
        this.additionalInfo = additionalInfo;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Result getResult() {
        return result;
    }

    public BigDecimal getPointing() {
        return pointing;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

}

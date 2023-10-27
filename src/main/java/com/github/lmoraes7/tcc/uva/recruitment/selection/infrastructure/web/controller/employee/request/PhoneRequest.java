package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.employee.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class PhoneRequest {
    @NotBlank
    @Pattern(regexp = "^[0-9]{3}$")
    private String ddd;
    @NotBlank
    @Pattern(regexp = "^[0-9]{8,9}$")
    private String number;

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

}

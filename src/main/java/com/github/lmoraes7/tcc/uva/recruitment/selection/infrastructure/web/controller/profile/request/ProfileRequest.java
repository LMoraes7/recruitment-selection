package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.profile.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Collections;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class ProfileRequest {
    @NotBlank
    private String name;
    @NotNull
    @Size(min = 1)
    private Set<String> functionsIdentifiers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getFunctionsIdentifiers() {
        return Collections.unmodifiableSet(functionsIdentifiers);
    }

    public void setFunctionsIdentifiers(Set<String> functionsIdentifiers) {
        this.functionsIdentifiers = functionsIdentifiers;
    }

}

package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.login.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class LoginResponse {
    private String type;
    private String accessToken;
    private long creationDate;
    private long expirationDate;
    private long expirationIn;

    public LoginResponse(
            final String type,
            final String accessToken,
            final long creationDate,
            final long expirationDate,
            final long expirationIn
    ) {
        this.type = type;
        this.accessToken = accessToken;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
        this.expirationIn = expirationIn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    public long getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(long expirationDate) {
        this.expirationDate = expirationDate;
    }

    public long getExpirationIn() {
        return expirationIn;
    }

    public void setExpirationIn(long expirationIn) {
        this.expirationIn = expirationIn;
    }

}

package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.vo;

import java.util.concurrent.TimeUnit;

public class AccessToken {
    private final String type;
    private final String hash;
    private final long creationDate;
    private final long expirationDate;
    private final long expiresIn;

    public AccessToken(String type, String hash, long creationDate, long expirationDate) {
        this.type = type;
        this.hash = hash;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
        this.expiresIn = TimeUnit.MINUTES.toSeconds(expirationDate - creationDate);
    }

    public String getType() {
        return type;
    }

    public String getHash() {
        return hash;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public long getExpirationDate() {
        return expirationDate;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

}

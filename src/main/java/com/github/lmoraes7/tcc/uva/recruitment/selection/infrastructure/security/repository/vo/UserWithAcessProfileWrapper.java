package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.repository.vo;

public final class UserWithAcessProfileWrapper {
    private final String userId;
    private final String username;
    private final String password;
    private final String accessProfileId;
    private final String value;

    public UserWithAcessProfileWrapper(
            final String userId,
            final String username,
            final String password,
            final String accessProfileId,
            final String value
    ) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.accessProfileId = accessProfileId;
        this.value = value;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAccessProfileId() {
        return accessProfileId;
    }

    public String getValue() {
        return value;
    }
}

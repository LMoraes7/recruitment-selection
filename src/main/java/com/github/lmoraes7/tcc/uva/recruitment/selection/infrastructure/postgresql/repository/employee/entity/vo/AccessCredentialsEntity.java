package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.employee.entity.vo;

public final class AccessCredentialsEntity {
    private String username;
    private String password;

    public AccessCredentialsEntity(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}

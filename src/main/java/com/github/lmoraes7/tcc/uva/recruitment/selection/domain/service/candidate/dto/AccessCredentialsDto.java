package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidate.dto;

import java.util.Objects;

public final class AccessCredentialsDto {
    private final String password;

    public AccessCredentialsDto(final String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AccessCredentialsDto that = (AccessCredentialsDto) object;
        return Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }

}

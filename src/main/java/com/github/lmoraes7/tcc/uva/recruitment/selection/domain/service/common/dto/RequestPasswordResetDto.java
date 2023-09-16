package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.common.dto;

import java.util.Objects;

public final class RequestPasswordResetDto {
    private final String email;

    public RequestPasswordResetDto(final String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        RequestPasswordResetDto that = (RequestPasswordResetDto) object;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

}

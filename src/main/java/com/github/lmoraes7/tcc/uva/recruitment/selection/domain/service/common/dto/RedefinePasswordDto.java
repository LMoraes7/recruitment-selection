package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.common.dto;

import java.util.Objects;

public final class RedefinePasswordDto {
    private final String code;
    private final String newPassword;

    public RedefinePasswordDto(final String code, final String newPassword) {
        this.code = code;
        this.newPassword = newPassword;
    }

    public String getCode() {
        return code;
    }

    public String getNewPassword() {
        return newPassword;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        RedefinePasswordDto that = (RedefinePasswordDto) object;
        return Objects.equals(code, that.code) && Objects.equals(newPassword, that.newPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, newPassword);
    }

}

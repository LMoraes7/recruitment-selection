package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.common.dto;

import java.util.Objects;

public final class PhoneDto {
    private final String ddd;
    private final String number;

    public PhoneDto(final String ddd, final String number) {
        this.ddd = ddd;
        this.number = number;
    }

    public String getDdd() {
        return ddd;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        PhoneDto phoneDto = (PhoneDto) object;
        return Objects.equals(ddd, phoneDto.ddd) && Objects.equals(number, phoneDto.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ddd, number);
    }

}

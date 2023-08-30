package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.employee.dto;

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
}

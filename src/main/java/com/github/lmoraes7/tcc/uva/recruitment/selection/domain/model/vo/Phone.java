package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo;

import java.util.Objects;

public final class Phone {
    private String ddd;
    private String number;

    public Phone(final String ddd, final String number) {
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
        Phone phone = (Phone) object;
        return Objects.equals(ddd, phone.ddd) && Objects.equals(number, phone.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ddd, number);
    }

}

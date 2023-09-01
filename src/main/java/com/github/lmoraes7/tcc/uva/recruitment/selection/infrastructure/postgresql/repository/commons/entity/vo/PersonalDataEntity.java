package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons.entity.vo;

import java.time.LocalDate;
import java.util.Objects;

public class PersonalDataEntity {
    private final String name;
    private final String cpf;
    private final String email;
    private final LocalDate dateOfBirth;
    private final String phone;
    private final String address;

    public PersonalDataEntity(
            final String name,
            final String cpf,
            final String email,
            final LocalDate dateOfBirth,
            final String phone,
            final String address
    ) {
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        PersonalDataEntity that = (PersonalDataEntity) object;
        return Objects.equals(name, that.name) && Objects.equals(cpf, that.cpf) && Objects.equals(email, that.email) && Objects.equals(dateOfBirth, that.dateOfBirth) && Objects.equals(phone, that.phone) && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cpf, email, dateOfBirth, phone, address);
    }

}

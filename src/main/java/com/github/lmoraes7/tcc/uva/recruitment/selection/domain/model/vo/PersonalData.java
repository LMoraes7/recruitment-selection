package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo;

import java.time.LocalDate;
import java.util.Objects;

public final class PersonalData {
    private String name;
    private String cpf;
    private String email;
    private LocalDate dateOfBirth;
    private Phone phone;
    private Address address;

    public PersonalData(
            final String name,
            final String cpf,
            final String email,
            final LocalDate dateOfBirth,
            final Phone phone,
            final Address address
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

    public Phone getPhone() {
        return phone;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        PersonalData that = (PersonalData) object;
        return Objects.equals(name, that.name) && Objects.equals(cpf, that.cpf) && Objects.equals(email, that.email) && Objects.equals(dateOfBirth, that.dateOfBirth) && Objects.equals(phone, that.phone) && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cpf, email, dateOfBirth, phone, address);
    }

}

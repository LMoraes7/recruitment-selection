package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.common.dto;

import java.time.LocalDate;
import java.util.Objects;

public final class PersonalDataDto {
    private final String name;
    private final String cpf;
    private final String email;
    private final LocalDate dateOfBirth;
    private final PhoneDto phone;
    private final AddressDto address;

    public PersonalDataDto(
            final String name,
            final String cpf,
            final String email,
            final LocalDate dateOfBirth,
            final PhoneDto phone,
            final AddressDto address
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

    public PhoneDto getPhone() {
        return phone;
    }

    public AddressDto getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        PersonalDataDto that = (PersonalDataDto) object;
        return Objects.equals(name, that.name) && Objects.equals(cpf, that.cpf) && Objects.equals(email, that.email) && Objects.equals(dateOfBirth, that.dateOfBirth) && Objects.equals(phone, that.phone) && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cpf, email, dateOfBirth, phone, address);
    }

}

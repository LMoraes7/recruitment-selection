package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.employee.dto;

import java.time.LocalDate;

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

}

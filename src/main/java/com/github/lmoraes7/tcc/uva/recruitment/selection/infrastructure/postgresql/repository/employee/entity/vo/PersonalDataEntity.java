package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.employee.entity.vo;

import java.time.LocalDate;

public class PersonalDataEntity {
    private String name;
    private String cpf;
    private String email;
    private LocalDate dateOfBirth;
    private String phone;
    private String address;

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

}

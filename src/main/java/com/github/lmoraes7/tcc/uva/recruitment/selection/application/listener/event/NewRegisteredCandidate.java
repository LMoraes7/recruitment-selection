package com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event;

import java.util.Objects;

public final class NewRegisteredCandidate {
    private final String identifier;
    private final String email;
    private final String cpf;

    public NewRegisteredCandidate(
            final String identifier,
            final String email,
            final String cpf
    ) {
        this.identifier = identifier;
        this.email = email;
        this.cpf = cpf;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        NewRegisteredCandidate that = (NewRegisteredCandidate) object;
        return Objects.equals(identifier, that.identifier) && Objects.equals(email, that.email) && Objects.equals(cpf, that.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, email, cpf);
    }

}

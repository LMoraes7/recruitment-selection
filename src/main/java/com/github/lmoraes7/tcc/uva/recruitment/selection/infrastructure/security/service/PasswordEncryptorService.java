package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public final class PasswordEncryptorService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordEncryptorService(final PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String execute(final String password) {
        return this.passwordEncoder.encode(password);
    }

}

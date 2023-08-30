package com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator;

import java.util.UUID;

public final class GeneratorResetPasswordCode {

    public static String execute() {
        return "RST-".concat(generateHash());
    }

    private static String generateHash() {
        final var split = UUID.randomUUID().toString().split("-");
        return split[1].concat(split[2]);
    }
}

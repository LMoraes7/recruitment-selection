package com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator;

import java.util.UUID;

public final class GeneratorIdentifier {

    public static String forProfile() {
        return "PRO-".concat(generateHash());
    }

    public static String forEmployee() {
        return "EMP-".concat(generateHash());
    }

    private static String generateHash() {
        final var split = UUID.randomUUID().toString().split("-");
        return split[1].concat(split[2]);
    }
}

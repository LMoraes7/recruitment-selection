package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class CommonFunctions {

    public static Set<String> validateIdentifiers(
            final Collection<String> validIdentifiers,
            final Collection<String> identifiersToValidate
    ) {
        final Set<String> invalidIdentifiers = new HashSet<>(identifiersToValidate);

        identifiersToValidate.forEach(it -> {
            if (validIdentifiers.contains(it))
                invalidIdentifiers.remove(it);
        });

        return invalidIdentifiers;
    }

}

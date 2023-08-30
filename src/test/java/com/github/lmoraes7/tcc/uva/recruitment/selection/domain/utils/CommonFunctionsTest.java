package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.utils;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.utils.CommonFunctions.validateIdentifiers;
import static org.junit.jupiter.api.Assertions.*;

final class CommonFunctionsTest {

    @Test
    void when_requested_should_return_an_empty_list_of_String_containing_the_identifiers_that_are_not_contained_in_the_valid_list() {
        final String arg0 = UUID.randomUUID().toString();
        final String arg1 = UUID.randomUUID().toString();
        final String arg2 = UUID.randomUUID().toString();

        final Collection<String> validIdentifiers = List.of(arg0, arg1, arg2);
        final Collection<String> identifiersToValidate = List.of(arg1, arg0, arg2);

        assertDoesNotThrow(() -> assertTrue(validateIdentifiers(validIdentifiers, identifiersToValidate).isEmpty()));
    }

    @Test
    void when_requested_should_return_a_non_empty_list_of_String_containing_the_identifiers_that_are_not_contained_in_the_valid_list() {
        final String arg0 = UUID.randomUUID().toString();
        final String arg1 = UUID.randomUUID().toString();
        final String arg2 = UUID.randomUUID().toString();
        final String arg3 = UUID.randomUUID().toString();

        final Collection<String> validIdentifiers = List.of(arg0, arg1, arg2);
        final Collection<String> identifiersToValidate = List.of(arg1, arg0, arg3, arg2);

        assertDoesNotThrow(() -> {
            final Set<String> invalidateIdentifiers = validateIdentifiers(validIdentifiers, identifiersToValidate);

            assertFalse(invalidateIdentifiers.isEmpty());
            assertEquals(
                    invalidateIdentifiers,
                    identifiersToValidate.stream().filter(it -> !validIdentifiers.contains(it)).collect(Collectors.toSet())
            );
        });
    }

}
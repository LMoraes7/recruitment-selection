package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.filter.converter;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidate;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Function;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Profile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.Functionality;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.AccessCredentials;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class ConverterHelper {

    public static Employee employeeDetailsToDomain(final String ownerIdentifier, final UserDetails it) {
        return new Employee(
                ownerIdentifier,
                new AccessCredentials(
                        it.getUsername(),
                        it.getPassword(),
                        null
                )
        );
    }

    public static Employee employeeDetailsToDomainWithProfiles(final String ownerIdentifier, final UserDetails it) {
        return new Employee(
                ownerIdentifier,
                new AccessCredentials(
                        it.getUsername(),
                        it.getPassword(),
                        Set.of(
                                new Profile(
                                        it.getAuthorities()
                                                .stream()
                                                .map(x -> new Function(Functionality.valueOf(x.getAuthority())))
                                                .collect(Collectors.toSet())
                                )
                        )
                )
        );
    }

    public static Candidate candidateDetailsToDomain(final String ownerIdentifier, final UserDetails it) {
        return new Candidate(
                ownerIdentifier,
                new AccessCredentials(
                        it.getUsername(),
                        it.getPassword(),
                        null
                )
        );
    }

    public static Candidate candidateDetailsToDomainWithProfile(final String ownerIdentifier, final UserDetails it) {
        return new Candidate(
                ownerIdentifier,
                new AccessCredentials(
                        it.getUsername(),
                        it.getPassword(),
                        Set.of(
                                new Profile(
                                        it.getAuthorities()
                                                .stream()
                                                .map(x -> new Function(Functionality.valueOf(x.getAuthority())))
                                                .collect(Collectors.toSet())
                                )
                        )
                )
        );
    }

}

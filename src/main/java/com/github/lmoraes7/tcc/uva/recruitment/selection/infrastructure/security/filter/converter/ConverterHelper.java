package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.filter.converter;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidate;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.AccessCredentials;
import org.springframework.security.core.userdetails.UserDetails;

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

}

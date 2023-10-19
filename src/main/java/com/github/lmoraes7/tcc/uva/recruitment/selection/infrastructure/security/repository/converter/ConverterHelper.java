package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.repository.converter;

import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.repository.vo.UserWithAcessProfileWrapper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.vo.CustomGrantedAuthority;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.vo.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public final class ConverterHelper {

    public static UserDetails entityWrapperToUserDetails(final Collection<UserWithAcessProfileWrapper> userEntities) {
        final var first = userEntities.stream().findFirst().get();
        final var customGrantedAuthority = userEntities
                .stream()
                .map(it -> new CustomGrantedAuthority(it.getAccessProfileId(), it.getValue()))
                .toList();

        return new CustomUserDetails(
                first.getUserId(),
                first.getUsername(),
                first.getPassword(),
                customGrantedAuthority
        );
    }

}

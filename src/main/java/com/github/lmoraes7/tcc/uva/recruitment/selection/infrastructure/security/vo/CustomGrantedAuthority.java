package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.vo;

import org.springframework.security.core.GrantedAuthority;

public final class CustomGrantedAuthority implements GrantedAuthority {

    private final String id;
    private final String value;

    public CustomGrantedAuthority(final String id, final String value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public String getAuthority() {
        return this.value;
    }

}

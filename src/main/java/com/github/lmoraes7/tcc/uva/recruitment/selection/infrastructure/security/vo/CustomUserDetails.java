package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.vo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public final class CustomUserDetails implements UserDetails {
    private final String id;
    private final String username;
    private final String password;
    private final Collection<CustomGrantedAuthority> customGrantedAuthority;

    public CustomUserDetails(
            final String id,
            final String username,
            final String password,
            final Collection<CustomGrantedAuthority> customGrantedAuthority
    ) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.customGrantedAuthority = Objects.requireNonNullElseGet(customGrantedAuthority, ArrayList::new);
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.unmodifiableCollection(this.customGrantedAuthority);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Profile;

import java.util.Objects;
import java.util.Set;

public final class AccessCredentials {
    private String username;
    private String password;
    private Set<Profile> profiles;

    public AccessCredentials(
            final String username,
            final String password,
            final Set<Profile> profiles
    ) {
        this.username = username;
        this.password = password;
        this.profiles = profiles;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Set<Profile> getProfiles() {
        return profiles;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AccessCredentials that = (AccessCredentials) object;
        return Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(profiles, that.profiles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, profiles);
    }

}

package com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event;

import java.util.Objects;

public final class PasswordReset {
    private final String username;

    public PasswordReset(final String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        PasswordReset that = (PasswordReset) object;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

}

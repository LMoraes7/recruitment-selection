package com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event;

import org.springframework.context.ApplicationEvent;

import java.util.Objects;

public final class LoginSuccessfullyEvent {
    private final String username;

    public LoginSuccessfullyEvent(final String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        LoginSuccessfullyEvent that = (LoginSuccessfullyEvent) object;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

}

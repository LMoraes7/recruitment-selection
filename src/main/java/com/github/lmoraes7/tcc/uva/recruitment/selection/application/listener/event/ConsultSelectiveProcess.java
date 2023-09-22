package com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event;

import java.util.Objects;

public final class ConsultSelectiveProcess {
    private final String identifier;
    private final String status;

    public ConsultSelectiveProcess(
            final String identifier,
            final String status
    ) {
        this.identifier = identifier;
        this.status = status;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ConsultSelectiveProcess that = (ConsultSelectiveProcess) object;
        return Objects.equals(identifier, that.identifier) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, status);
    }

}

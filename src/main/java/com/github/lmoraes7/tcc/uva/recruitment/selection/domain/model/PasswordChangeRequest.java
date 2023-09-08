package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.BusinessException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.APIX_005;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.APIX_006;

public final class PasswordChangeRequest {
    private final String code;
    private final String emailEntity;
    private final TypeEntity typeEntity;
    private final LocalDateTime requestedIn;
    private final LocalDateTime expiredIn;
    private final Boolean isUsed;

    public PasswordChangeRequest(
            final String code,
            final String emailEntity,
            final TypeEntity typeEntity,
            final LocalDateTime requestedIn,
            final LocalDateTime expiredIn,
            final Boolean isUsed
    ) {
        this.code = code;
        this.emailEntity = emailEntity;
        this.typeEntity = typeEntity;
        this.requestedIn = requestedIn;
        this.expiredIn = expiredIn;
        this.isUsed = isUsed;
    }

    public String getCode() {
        return code;
    }

    public String getEmailEntity() {
        return emailEntity;
    }

    public TypeEntity getTypeEntity() {
        return typeEntity;
    }

    public void hasItAlreadyBeenUsed() {
        if (this.isUsed)
            throw new BusinessException(APIX_005, List.of(this.code));
    }

    public void isItExpired() {
        if (LocalDateTime.now().isAfter(this.expiredIn))
            throw new BusinessException(APIX_006, List.of(this.code));
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        PasswordChangeRequest that = (PasswordChangeRequest) object;
        return Objects.equals(code, that.code) && Objects.equals(emailEntity, that.emailEntity) && typeEntity == that.typeEntity && Objects.equals(requestedIn, that.requestedIn) && Objects.equals(expiredIn, that.expiredIn) && Objects.equals(isUsed, that.isUsed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, emailEntity, typeEntity, requestedIn, expiredIn, isUsed);
    }

}

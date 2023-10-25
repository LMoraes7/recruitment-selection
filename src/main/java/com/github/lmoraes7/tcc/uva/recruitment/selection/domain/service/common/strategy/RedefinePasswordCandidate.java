package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.common.strategy;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.BusinessException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.NotFoundException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.PasswordChangeRequest;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.common.dto.RedefinePasswordDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidate.CandidateRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.service.PasswordEncryptorService;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.APIX_007;

@Component
public class RedefinePasswordCandidate implements RedefinePasswordStrategy {
    private final CandidateRepository candidateRepository;
    private final PasswordEncryptorService passwordEncryptorService;

    public RedefinePasswordCandidate(
            final CandidateRepository candidateRepository,
            final PasswordEncryptorService passwordEncryptorService
    ) {
        this.candidateRepository = candidateRepository;
        this.passwordEncryptorService = passwordEncryptorService;
    }

    @Override
    public TypeEntity getTypeEntity() {
        return TypeEntity.CAN;
    }

    @Override
    public void execute(final PasswordChangeRequest passwordChangeRequest, final RedefinePasswordDto redefinePasswordDto) {
        try {
            this.candidateRepository.changePassword(
                    passwordChangeRequest.getEmailEntity(),
                    this.passwordEncryptorService.execute(redefinePasswordDto.getNewPassword())
            );
        } catch (final NotFoundException ex) {
            throw new BusinessException(APIX_007, "Email provided is not valid");
        }
    }

}

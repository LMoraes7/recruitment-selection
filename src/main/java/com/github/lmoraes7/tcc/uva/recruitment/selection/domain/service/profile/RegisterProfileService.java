package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.profile;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.NewRegisteredProfile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.BusinessException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Profile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.profile.dto.ProfileDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.profile.ProfileRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.APIX_001;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.profile.converter.ConverterHelper.toModel;

@Service
public final class RegisterProfileService {
    private final ProfileRepository profileRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public RegisterProfileService(
            final ProfileRepository profileRepository,
            final ApplicationEventPublisher applicationEventPublisher
    ) {
        this.profileRepository = profileRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public Profile save(final ProfileDto dto) {
        Profile profile;

        try {
            profile = this.profileRepository.save(toModel(dto));
        } catch (final DataIntegrityViolationException ex) {
            throw new BusinessException(APIX_001, List.of(dto.getName()));
        }

        this.applicationEventPublisher.publishEvent(
                new NewRegisteredProfile(
                        profile.getIdentifier(),
                        profile.getName()
                )
        );

        return profile;
    }

}

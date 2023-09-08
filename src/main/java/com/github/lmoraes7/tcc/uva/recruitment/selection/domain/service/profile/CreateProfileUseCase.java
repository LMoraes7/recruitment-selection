package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.profile;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.NewRegisteredProfile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Profile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.profile.dto.ProfileDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.function.FunctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public final class CreateProfileUseCase {
    private final FunctionRepository functionRepository;
    private final RegisterProfileService registerProfileService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public CreateProfileUseCase(
            final FunctionRepository functionRepository,
            final RegisterProfileService registerProfileService,
            final ApplicationEventPublisher applicationEventPublisher
    ) {
        this.functionRepository = functionRepository;
        this.registerProfileService = registerProfileService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public Profile execute(final Employee employee, final ProfileDto dto) {
        final Profile profile = employee.createProfile(dto, this.functionRepository, this.registerProfileService);

        this.applicationEventPublisher.publishEvent(
                new NewRegisteredProfile(
                        profile.getIdentifier(),
                        profile.getName()
                )
        );

        return profile;
    }

}

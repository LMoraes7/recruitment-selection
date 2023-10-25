package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.profile;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.NewRegisteredProfile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.BusinessException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Profile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.profile.dto.ProfileDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.function.FunctionRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Set;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.APIX_002;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

final class CreateProfileUseCaseTest {
    private final FunctionRepository functionRepository = mock(FunctionRepository.class);
    private final RegisterProfileService registerProfileService = mock(RegisterProfileService.class);
    private final ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);
    private final CreateProfileUseCase createProfileUseCase = new CreateProfileUseCase(
        this.functionRepository,
        this.registerProfileService,
        this.applicationEventPublisher
    );

    private Employee employee;
    private ProfileDto profileDto;
    private Profile profile;

    @BeforeEach
    void setUp() {
        this.employee = TestUtils.dummyObject(Employee.class);
        this.profileDto = TestUtils.dummyObject(ProfileDto.class);
        this.profile = TestUtils.dummyObject(Profile.class);
    }

    @Test
    void when_prompted_should_successfully_create_an_employee() {
        when(this.functionRepository.fetchIdentifiers(this.profileDto.getFunctionsIdentifiers()))
                .thenReturn(this.profileDto.getFunctionsIdentifiers());
        when(this.registerProfileService.save(this.profileDto)).thenReturn(this.profile);

        assertDoesNotThrow(() -> this.createProfileUseCase.execute(this.employee, this.profileDto));

        verify(this.functionRepository, only()).fetchIdentifiers(this.profileDto.getFunctionsIdentifiers());
        verify(this.registerProfileService, only()).save(this.profileDto);
        verify(this.applicationEventPublisher, only()).publishEvent(
                new NewRegisteredProfile(
                        this.profile.getIdentifier(),
                        this.profile.getName()
                )
        );
    }

    @Test
    void when_requested_it_must_throw_a_BusinessException_when_the_informed_profiles_do_not_exist() {
        when(this.functionRepository.fetchIdentifiers(this.profileDto.getFunctionsIdentifiers()))
                .thenReturn(Set.of());

        final BusinessException exception = assertThrows(
                BusinessException.class,
                () -> this.createProfileUseCase.execute(this.employee, this.profileDto)
        );

        assertNotNull(exception);
        assertEquals(APIX_002, exception.getError());

        verify(this.functionRepository, only()).fetchIdentifiers(this.profileDto.getFunctionsIdentifiers());
        verifyNoInteractions(this.registerProfileService, this.applicationEventPublisher);
    }

}
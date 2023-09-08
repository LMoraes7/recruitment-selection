package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.profile;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.BusinessException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Function;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Profile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.profile.dto.ProfileDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.profile.ProfileRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.utils.ProfileArgMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.stream.Collectors;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.profile.converter.ConverterHelper.toModel;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

final class RegisterProfileServiceTest {
    private final ProfileRepository profileRepository = Mockito.mock(ProfileRepository.class);
    private final RegisterProfileService registerProfileService = new RegisterProfileService(this.profileRepository);

    private ProfileDto profileDto;

    @BeforeEach
    void setUp() {
        this.profileDto = dummyObject(ProfileDto.class);
    }

    @Test
    void when_saving_a_profile_with_a_name_already_registered_it_must_throw_a_BusinessException() {
        final Profile profile = toModel(this.profileDto);

        when(this.profileRepository.save(argThat(new ProfileArgMatchers(profile)))).thenThrow(DataIntegrityViolationException.class);

        final BusinessException businessException = assertThrows(
                BusinessException.class,
                () -> this.registerProfileService.save(this.profileDto)
        );

        assertNotNull(businessException);
        assertNotNull(businessException.getError());
        assertNotNull(businessException.getArgs());

        assertEquals(Error.APIX_001, businessException.getError());
        assertEquals(1, businessException.getArgs().size());
        assertTrue(businessException.getArgs().contains(this.profileDto.getName()));

        verify(this.profileRepository, only()).save(argThat(new ProfileArgMatchers(profile)));
    }

    @Test
    void when_save_a_profile_it_must_save_successfully() {
        final Profile profile = toModel(this.profileDto);

        when(this.profileRepository.save(argThat(new ProfileArgMatchers(profile)))).thenReturn(profile);

        assertDoesNotThrow(
                () -> {
                    final Profile profileCreated = this.registerProfileService.save(this.profileDto);

                    assertNotNull(profileCreated);
                    assertNotNull(profileCreated.getIdentifier());
                    assertNotNull(profileCreated.getName());
                    assertNotNull(profileCreated.getFunctions());

                    assertEquals(profileCreated.getName(), this.profileDto.getName());
                    assertEquals(
                            profileCreated.getFunctions().stream().map(Function::getIdentifier).collect(Collectors.toSet()),
                            this.profileDto.getFunctionsIdentifiers()
                    );

                    verify(this.profileRepository, only()).save(argThat(new ProfileArgMatchers(profile)));
                }
        );
    }
}
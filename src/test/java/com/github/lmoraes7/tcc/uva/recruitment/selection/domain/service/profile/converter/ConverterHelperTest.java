package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.profile.converter;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Function;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Profile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.profile.dto.ProfileDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.profile.converter.ConverterHelper.toModel;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.*;

final class ConverterHelperTest {

    private ProfileDto profileDto;

    @BeforeEach
    void setUp() {
        this.profileDto = dummyObject(ProfileDto.class);
    }

    @Test
    void when_prompted_should_successfully_create_a_profile() {
        assertDoesNotThrow(() -> {
            final Profile profile = toModel(this.profileDto);

            assertNotNull(profile);
            assertNotNull(profile.getIdentifier());
            assertEquals(profile.getName(), profileDto.getName());
            assertEquals(
                    profile.getFunctions().stream().map(Function::getIdentifier).collect(Collectors.toSet()),
                    profileDto.getFunctionsIdentifiers()
            );
        });
    }

}
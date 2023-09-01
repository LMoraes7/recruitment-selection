package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.BusinessException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.employee.RegisterEmployeeService;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.employee.dto.EmployeeDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.profile.RegisterProfileService;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.profile.dto.ProfileDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.function.FunctionRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.profile.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.APIX_002;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.APIX_004;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.profile.converter.ConverterHelper.toModel;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

final class EmployeeTest {

    private final FunctionRepository functionRepository = mock(FunctionRepository.class);
    private final RegisterProfileService registerProfileService = mock(RegisterProfileService.class);
    private final ProfileRepository profileRepository = mock(ProfileRepository.class);
    private final RegisterEmployeeService registerEmployeeService = mock(RegisterEmployeeService.class);

    private Employee employee;
    private ProfileDto profileDto;
    private EmployeeDto employeeDto;

    @BeforeEach
    void setUp() {
        this.employee = dummyObject(Employee.class);
        this.profileDto = dummyObject(ProfileDto.class);
        this.employeeDto = dummyObject(EmployeeDto.class);
    }

    @Test
    void when_prompted_should_successfully_create_a_profile() {
        when(this.functionRepository.fetchIdentifiers(this.profileDto.getFunctionsIdentifiers()))
                .thenReturn(this.profileDto.getFunctionsIdentifiers());
        when(this.registerProfileService.save(profileDto)).thenReturn(toModel(this.profileDto));

        assertDoesNotThrow(() -> {
            final Profile profile = this.employee.createProfile(
                    this.profileDto,
                    this.functionRepository,
                    this.registerProfileService
            );

            assertNotNull(profile);
            assertNotNull(profile.getIdentifier());
            assertEquals(profile.getName(), this.profileDto.getName());
            assertEquals(
                    profile.getFunctions().stream().map(Function::getIdentifier).collect(Collectors.toSet()),
                    this.profileDto.getFunctionsIdentifiers()
            );
        });

        verify(this.functionRepository, only()).fetchIdentifiers(this.profileDto.getFunctionsIdentifiers());
        verify(this.registerProfileService, only()).save(profileDto);
    }

    @Test
    void when_requested_it_should_throw_a_BusinessException_when_an_invalid_Function_identifier_is_informed_when_create_profile() {
        final Collection<String> validIdentifiers = Collections.singleton(
                this.profileDto.getFunctionsIdentifiers().stream().findAny().get()
        );

        when(this.functionRepository.fetchIdentifiers(this.profileDto.getFunctionsIdentifiers()))
                .thenReturn(validIdentifiers);

        final BusinessException businessException = assertThrows(
                BusinessException.class,
                () -> this.employee.createProfile(
                        this.profileDto,
                        this.functionRepository,
                        this.registerProfileService
                )
        );

        final Set<String> invalidIdentifiers = this.profileDto.getFunctionsIdentifiers()
                .stream()
                .filter(it -> !validIdentifiers.contains(it))
                .collect(Collectors.toSet());

        assertNotNull(businessException.getError());
        assertEquals(businessException.getError(), APIX_002);
        assertNotNull(businessException.getArgs());
        assertEquals(businessException.getArgs().size(), invalidIdentifiers.size());
        assertTrue(businessException.getArgs().containsAll(invalidIdentifiers));

        verify(this.functionRepository, only()).fetchIdentifiers(this.profileDto.getFunctionsIdentifiers());
        verifyNoInteractions(this.registerProfileService);
    }

    @Test
    void when_prompted_should_successfully_create_a_employee() {
        when(this.profileRepository.fetchIdentifiers(this.employeeDto.getProfilesIdentifiers()))
                .thenReturn(this.employeeDto.getProfilesIdentifiers());
        when(this.registerEmployeeService.save(this.employeeDto)).thenReturn(this.employee);

        assertDoesNotThrow(() -> {
            this.employee.createEmployee(
                    this.employeeDto,
                    this.profileRepository,
                    this.registerEmployeeService
            );
        });

        verify(this.profileRepository, only()).fetchIdentifiers(this.employeeDto.getProfilesIdentifiers());
        verify(this.registerEmployeeService, only()).save(this.employeeDto);
    }

    @Test
    void when_requested_it_should_throw_a_BusinessException_when_an_invalid_Function_identifier_is_informed_when_create_employee() {
        final Collection<String> validIdentifiers = Collections.singleton(
                this.employeeDto.getProfilesIdentifiers().stream().findAny().get()
        );

        when(this.profileRepository.fetchIdentifiers(this.employeeDto.getProfilesIdentifiers()))
                .thenReturn(validIdentifiers);

        final BusinessException businessException = assertThrows(
                BusinessException.class,
                () -> this.employee.createEmployee(
                        this.employeeDto,
                        this.profileRepository,
                        this.registerEmployeeService
                )
        );

        final Set<String> invalidIdentifiers = this.employeeDto.getProfilesIdentifiers()
                .stream()
                .filter(it -> !validIdentifiers.contains(it))
                .collect(Collectors.toSet());

        assertNotNull(businessException.getError());
        assertEquals(businessException.getError(), APIX_004);
        assertNotNull(businessException.getArgs());
        assertEquals(businessException.getArgs().size(), invalidIdentifiers.size());
        assertTrue(businessException.getArgs().containsAll(invalidIdentifiers));

        verify(this.profileRepository, only()).fetchIdentifiers(this.employeeDto.getProfilesIdentifiers());
        verifyNoInteractions(this.registerEmployeeService);
    }

}
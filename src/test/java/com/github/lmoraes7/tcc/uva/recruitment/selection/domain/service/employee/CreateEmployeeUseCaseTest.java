package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.employee;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.NewRegisteredEmployee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.BusinessException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.employee.dto.EmployeeDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.profile.ProfileRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Set;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.APIX_004;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

final class CreateEmployeeUseCaseTest {

    private final ProfileRepository profileRepository = mock(ProfileRepository.class);
    private final RegisterEmployeeService registerEmployeeService = mock(RegisterEmployeeService.class);
    private final ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);
    private final CreateEmployeeUseCase createEmployeeUseCase = new CreateEmployeeUseCase(
            this.profileRepository,
            this.registerEmployeeService,
            this.applicationEventPublisher
    );


    private Employee employee;
    private EmployeeDto employeeDto;

    @BeforeEach
    void setUp() {
        this.employee = TestUtils.dummyObject(Employee.class);
        this.employeeDto = TestUtils.dummyObject(EmployeeDto.class);
    }

    @Test
    void when_prompted_should_successfully_create_an_employee() {
        when(this.profileRepository.fetchIdentifiers(this.employeeDto.getProfilesIdentifiers()))
                .thenReturn(this.employeeDto.getProfilesIdentifiers());
        when(this.registerEmployeeService.save(this.employeeDto)).thenReturn(this.employee);

        assertDoesNotThrow(() -> this.createEmployeeUseCase.execute(this.employee, this.employeeDto));

        verify(this.profileRepository, only()).fetchIdentifiers(this.employeeDto.getProfilesIdentifiers());
        verify(this.registerEmployeeService, only()).save(this.employeeDto);
        verify(this.applicationEventPublisher, only()).publishEvent(
                new NewRegisteredEmployee(
                        this.employee.getIdentifier(),
                        this.employee.getPersonalData().getEmail(),
                        this.employee.getPersonalData().getCpf()
                )
        );
    }

    @Test
    void when_requested_it_must_throw_a_BusinessException_when_the_informed_profiles_do_not_exist() {
        when(this.profileRepository.fetchIdentifiers(this.employeeDto.getProfilesIdentifiers()))
                .thenReturn(Set.of());

        final BusinessException exception = assertThrows(
                BusinessException.class,
                () -> this.createEmployeeUseCase.execute(this.employee, this.employeeDto)
        );

        assertNotNull(exception);
        assertEquals(APIX_004, exception.getError());

        verify(this.profileRepository, only()).fetchIdentifiers(this.employeeDto.getProfilesIdentifiers());
        verifyNoInteractions(this.registerEmployeeService, this.applicationEventPublisher);
    }

}
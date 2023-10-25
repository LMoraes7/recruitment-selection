package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.employee;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.BusinessException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.employee.dto.EmployeeDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons.CommonRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.employee.EmployeeRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.service.PasswordEncryptorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

final class RegisterEmployeeServiceTest {

    private final CommonRepository commonRepository = mock(CommonRepository.class);
    private final EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
    private final PasswordEncryptorService passwordEncryptorService = mock(PasswordEncryptorService.class);
    private final RegisterEmployeeService registerEmployeeService = new RegisterEmployeeService(
            this.commonRepository,
            this.employeeRepository,
            this.passwordEncryptorService
    );

    private EmployeeDto employeeDto;
    private Employee employee;

    @BeforeEach
    void setUp() {
        this.employeeDto = dummyObject(EmployeeDto.class);
        this.employee = dummyObject(Employee.class);
    }

    @Test
    void when_saving_a_employee_must_throw_a_BusinessException() {
        doThrow(DataIntegrityViolationException.class)
                .when(this.commonRepository)
                .saveRecords(
                        this.employeeDto.getPersonalData().getEmail(),
                        this.employeeDto.getPersonalData().getCpf(),
                        TypeEntity.EMP
                );

        final BusinessException businessException = assertThrows(
                BusinessException.class,
                () -> this.registerEmployeeService.save(this.employeeDto)
        );

        assertNotNull(businessException);
        assertNotNull(businessException.getError());

        assertEquals(Error.APIX_003, businessException.getError());

        verify(this.commonRepository, only()).saveRecords(
                this.employeeDto.getPersonalData().getEmail(),
                this.employeeDto.getPersonalData().getCpf(),
                TypeEntity.EMP
        );
        verifyNoInteractions(this.employeeRepository, this.passwordEncryptorService);
    }

    @Test
    void when_save_a_employee_it_must_save_successfully() {
        when(this.employeeRepository.save(any())).thenReturn(this.employee);

        assertDoesNotThrow(() -> this.registerEmployeeService.save(this.employeeDto));

        verify(this.commonRepository, times(1)).saveRecords(
                this.employeeDto.getPersonalData().getEmail(),
                this.employeeDto.getPersonalData().getCpf(),
                TypeEntity.EMP
        );
        verify(this.employeeRepository, only()).save(any());
        verify(this.commonRepository, times(1))
                .savePasswordChangeRequest(eq(this.employee.getPersonalData().getEmail()), eq(TypeEntity.EMP), any(String.class));
        verifyNoMoreInteractions(this.commonRepository);
    }

}
package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.common.strategy;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.BusinessException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.NotFoundException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.PasswordChangeRequest;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.common.dto.RedefinePasswordDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.employee.EmployeeRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.service.PasswordEncryptorService;
import com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.APIX_007;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

final class RedefinePasswordEmployeeTest {

    private final EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
    private final PasswordEncryptorService passwordEncryptorService = mock(PasswordEncryptorService.class);
    private final RedefinePasswordEmployee redefinePasswordEmployee = new RedefinePasswordEmployee(
            this.employeeRepository,
            this.passwordEncryptorService
    );

    private PasswordChangeRequest passwordChangeRequest;
    private RedefinePasswordDto redefinePasswordDto;

    @BeforeEach
    void setUp() {
        this.passwordChangeRequest = TestUtils.dummyObject(PasswordChangeRequest.class);
        this.redefinePasswordDto = TestUtils.dummyObject(RedefinePasswordDto.class);
    }

    @Test
    void when_requested_it_should_return_the_entity_type_successfully() {
        assertDoesNotThrow(() -> assertEquals(TypeEntity.EMP, this.redefinePasswordEmployee.getTypeEntity()));

        verifyNoInteractions(this.employeeRepository, this.passwordEncryptorService);
    }

    @Test
    void when_prompted_you_should_change_the_password_successfully() {
        when(this.passwordEncryptorService.execute(redefinePasswordDto.getNewPassword()))
                .thenReturn(redefinePasswordDto.getNewPassword());

        assertDoesNotThrow(() -> this.redefinePasswordEmployee.execute(this.passwordChangeRequest, this.redefinePasswordDto));

        verify(this.employeeRepository, only()).changePassword(this.passwordChangeRequest.getEmailEntity(), redefinePasswordDto.getNewPassword());
        verify(this.passwordEncryptorService, only()).execute(redefinePasswordDto.getNewPassword());
    }

    @Test
    void when_asked_it_should_throw_a_BusinessException_when_it_doesnt_find_the_username() {
        when(this.passwordEncryptorService.execute(redefinePasswordDto.getNewPassword()))
                .thenReturn(redefinePasswordDto.getNewPassword());
        doThrow(new NotFoundException(this.redefinePasswordDto.getCode(), Employee.class)).when(this.employeeRepository)
                .changePassword(this.passwordChangeRequest.getEmailEntity(), redefinePasswordDto.getNewPassword());

        final BusinessException exception = assertThrows(
                BusinessException.class,
                () -> this.redefinePasswordEmployee.execute(this.passwordChangeRequest, this.redefinePasswordDto)
        );

        assertNotNull(exception);
        assertEquals(APIX_007, exception.getError());

        verify(this.employeeRepository, only()).changePassword(this.passwordChangeRequest.getEmailEntity(), redefinePasswordDto.getNewPassword());
        verify(this.passwordEncryptorService, only()).execute(redefinePasswordDto.getNewPassword());
    }


}
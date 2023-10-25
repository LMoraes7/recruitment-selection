package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.employee;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorResetPasswordCode;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.BusinessException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.employee.dto.EmployeeDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons.CommonRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.employee.EmployeeRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.service.PasswordEncryptorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.APIX_003;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.employee.converter.ConverterHelper.toModel;

@Service
public final class RegisterEmployeeService {
    private final CommonRepository commonRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncryptorService passwordEncryptorService;

    @Autowired
    public RegisterEmployeeService(
            final CommonRepository commonRepository,
            final EmployeeRepository employeeRepository,
            final PasswordEncryptorService passwordEncryptorService
    ) {
        this.commonRepository = commonRepository;
        this.employeeRepository = employeeRepository;
        this.passwordEncryptorService = passwordEncryptorService;
    }

    public Employee save(final EmployeeDto dto) {
        try {
            this.commonRepository.saveRecords(dto.getPersonalData().getEmail(), dto.getPersonalData().getCpf(), TypeEntity.EMP);
        } catch (final DataIntegrityViolationException ex) {
            throw new BusinessException(
                    APIX_003,
                    "CPF or Email provided have already been registered"
            );
        }

        final Employee employee = this.employeeRepository.save(toModel(dto, passwordEncryptorService));

        final String code = GeneratorResetPasswordCode.execute();
        this.commonRepository.savePasswordChangeRequest(employee.getPersonalData().getEmail(), TypeEntity.EMP, code);

        return employee;
    }

}
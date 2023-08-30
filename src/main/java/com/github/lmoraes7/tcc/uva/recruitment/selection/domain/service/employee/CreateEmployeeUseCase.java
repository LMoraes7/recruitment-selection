package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.employee;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.employee.dto.EmployeeDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.profile.ProfileRepository;
import org.springframework.stereotype.Service;

@Service
public final class CreateEmployeeUseCase {
    private final ProfileRepository profileRepository;
    private final RegisterEmployeeService registerEmployeeService;

    public CreateEmployeeUseCase(
            final ProfileRepository profileRepository,
            final RegisterEmployeeService registerEmployeeService
    ) {
        this.profileRepository = profileRepository;
        this.registerEmployeeService = registerEmployeeService;
    }

    public Employee execute(final Employee employee, final EmployeeDto dto) {
        return employee.createEmployee(
                dto,
                this.profileRepository,
                this.registerEmployeeService
        );
    }

}

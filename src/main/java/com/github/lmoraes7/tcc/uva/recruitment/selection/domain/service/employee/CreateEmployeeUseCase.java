package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.employee;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.NewRegisteredEmployee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.employee.dto.EmployeeDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.profile.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public final class CreateEmployeeUseCase {
    private final ProfileRepository profileRepository;
    private final RegisterEmployeeService registerEmployeeService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public CreateEmployeeUseCase(
            final ProfileRepository profileRepository,
            final RegisterEmployeeService registerEmployeeService,
            final ApplicationEventPublisher applicationEventPublisher
    ) {
        this.profileRepository = profileRepository;
        this.registerEmployeeService = registerEmployeeService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public Employee execute(final Employee employee, final EmployeeDto dto) {
        final Employee employeeCreated = employee.createEmployee(
                dto,
                this.profileRepository,
                this.registerEmployeeService
        );

        this.applicationEventPublisher.publishEvent(
                new NewRegisteredEmployee(
                        employeeCreated.getIdentifier(),
                        employeeCreated.getPersonalData().getEmail(),
                        employeeCreated.getPersonalData().getCpf()
                )
        );

        return employeeCreated;
    }

}

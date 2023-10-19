package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.employee;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.employee.CreateEmployeeUseCase;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.context.SecurityEmployeeContext;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.employee.converter.ConverterHelper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.employee.request.EmployeeRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
@Validated
public final class EmployeeController {
    private final CreateEmployeeUseCase createEmployeeUseCase;

    @Autowired
    public EmployeeController(final CreateEmployeeUseCase createEmployeeUseCase) {
        this.createEmployeeUseCase = createEmployeeUseCase;
    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody @Valid final EmployeeRequest request) {
        this.createEmployeeUseCase.execute(
                SecurityEmployeeContext.getContext(),
                ConverterHelper.toDto(request)
        );

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}

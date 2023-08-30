package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.profile;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Profile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.profile.dto.ProfileDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.function.FunctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class CreateProfileUseCase {
    private final FunctionRepository functionRepository;
    private final RegisterProfileService registerProfileService;

    @Autowired
    public CreateProfileUseCase(
            final FunctionRepository functionRepository,
            final RegisterProfileService registerProfileService
    ) {
        this.functionRepository = functionRepository;
        this.registerProfileService = registerProfileService;
    }

    public Profile execute(final Employee employee, final ProfileDto dto) {
        return employee.createProfile(dto, this.functionRepository, this.registerProfileService);
    }

}

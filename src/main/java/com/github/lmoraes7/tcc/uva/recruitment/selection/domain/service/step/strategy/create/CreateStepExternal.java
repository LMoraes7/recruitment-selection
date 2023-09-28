package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy.create;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Step;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.StepDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.ExternalStepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.converter.ConverterHelper.toExternalStepModel;

@Service
public final class CreateStepExternal implements CreateStepStrategy {
    private final ExternalStepRepository externalStepRepository;

    @Autowired
    public CreateStepExternal(final ExternalStepRepository externalStepRepository) {
        this.externalStepRepository = externalStepRepository;
    }

    @Override
    public TypeStep getTypeStep() {
        return TypeStep.EXTERNAL;
    }

    @Override
    public Step execute(final StepDto dto) {
        return this.externalStepRepository.save(toExternalStepModel(dto));
    }

}

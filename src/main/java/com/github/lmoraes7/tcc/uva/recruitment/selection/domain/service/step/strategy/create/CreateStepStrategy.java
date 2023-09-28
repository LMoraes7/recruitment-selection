package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy.create;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Step;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.StepDto;

public interface CreateStepStrategy {
    TypeStep getTypeStep();

    Step execute(final StepDto dto);
}

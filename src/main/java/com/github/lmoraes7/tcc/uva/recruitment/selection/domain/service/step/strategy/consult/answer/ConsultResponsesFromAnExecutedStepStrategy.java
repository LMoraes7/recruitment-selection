package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy.consult.answer;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ConsultResponsesFromAnExecutedStepDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ResponsesFromAnExecutedStep;

public interface ConsultResponsesFromAnExecutedStepStrategy {
    TypeStep getType();
    ResponsesFromAnExecutedStep execute(final ConsultResponsesFromAnExecutedStepDto dto);
}

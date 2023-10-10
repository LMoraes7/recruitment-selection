package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy.consult.answer;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ConsultResponsesFromAnExecutedStepDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ResponsesFromAnExecutedStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ResponsesFromAnExecutedTheoricalQuestionStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ResponsesFromAnExecutedTheoricalStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.TheoricalTestStepCandidacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class ConsultResponsesFromAnExecutedTheoricalTestStep implements ConsultResponsesFromAnExecutedStepStrategy {
    private final TheoricalTestStepCandidacyRepository theoricalTestStepCandidacyRepository;

    @Autowired
    public ConsultResponsesFromAnExecutedTheoricalTestStep(final TheoricalTestStepCandidacyRepository theoricalTestStepCandidacyRepository) {
        this.theoricalTestStepCandidacyRepository = theoricalTestStepCandidacyRepository;
    }

    @Override
    public TypeStep getType() {
        return TypeStep.THEORETICAL_TEST;
    }

    @Override
    public ResponsesFromAnExecutedStep execute(final ConsultResponsesFromAnExecutedStepDto dto) {
        final List<ResponsesFromAnExecutedTheoricalQuestionStep> result = this.theoricalTestStepCandidacyRepository
                .consultTestExecuted(dto.getApplicationIdentifier(), dto.getStepIdentifier());

        return new ResponsesFromAnExecutedStep(
                dto.getApplicationIdentifier(),
                dto.getStepIdentifier(),
                dto.getTypeStep(),
                new ResponsesFromAnExecutedTheoricalStep(result),
                null
        );
    }

}

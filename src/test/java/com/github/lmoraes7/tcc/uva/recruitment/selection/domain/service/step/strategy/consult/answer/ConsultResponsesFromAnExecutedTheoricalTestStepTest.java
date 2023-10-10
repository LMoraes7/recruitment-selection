package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy.consult.answer;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ConsultResponsesFromAnExecutedStepDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ResponsesFromAnExecutedTheoricalQuestionStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.TheoricalTestStepCandidacyRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

final class ConsultResponsesFromAnExecutedTheoricalTestStepTest {
    private final TheoricalTestStepCandidacyRepository theoricalTestStepCandidacyRepository = mock(TheoricalTestStepCandidacyRepository.class);
    private final ConsultResponsesFromAnExecutedTheoricalTestStep consultResponsesFromAnExecutedTheoricalTestStep = new ConsultResponsesFromAnExecutedTheoricalTestStep(this.theoricalTestStepCandidacyRepository);

    private ConsultResponsesFromAnExecutedStepDto consultResponsesFromAnExecutedStepDto;
    private List<ResponsesFromAnExecutedTheoricalQuestionStep> questionSteps;

    @BeforeEach
    void setUp() {
        this.consultResponsesFromAnExecutedStepDto = dummyObject(ConsultResponsesFromAnExecutedStepDto.class);
        this.questionSteps = List.of(
                dummyObject(ResponsesFromAnExecutedTheoricalQuestionStep.class),
                dummyObject(ResponsesFromAnExecutedTheoricalQuestionStep.class),
                dummyObject(ResponsesFromAnExecutedTheoricalQuestionStep.class)
        );
    }

    @Test
    void when_requested_you_must_query_the_questions_successfully() {
        when(this.theoricalTestStepCandidacyRepository.consultTestExecuted(
                this.consultResponsesFromAnExecutedStepDto.getApplicationIdentifier(),
                this.consultResponsesFromAnExecutedStepDto.getStepIdentifier()
        )).thenReturn(this.questionSteps);

        assertDoesNotThrow(() -> this.consultResponsesFromAnExecutedTheoricalTestStep.execute(this.consultResponsesFromAnExecutedStepDto));

        verify(this.theoricalTestStepCandidacyRepository, only()).consultTestExecuted(
                this.consultResponsesFromAnExecutedStepDto.getApplicationIdentifier(),
                this.consultResponsesFromAnExecutedStepDto.getStepIdentifier()
        );
    }

}
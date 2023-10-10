package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy.consult.answer;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ConsultResponsesFromAnExecutedStepDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ResponsesFromAnExecutedUploadFileStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.UploadFileStepCandidacyRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

final class ConsultResponsesFromAnExecutedUploadFileStepTest {
    private final UploadFileStepCandidacyRepository uploadFileStepCandidacyRepository = mock(UploadFileStepCandidacyRepository.class);
    private final ConsultResponsesFromAnExecutedUploadFileStep consultResponsesFromAnExecutedUploadFileStep = new ConsultResponsesFromAnExecutedUploadFileStep(this.uploadFileStepCandidacyRepository);

    private ConsultResponsesFromAnExecutedStepDto consultResponsesFromAnExecutedStepDto;
    private List<ResponsesFromAnExecutedUploadFileStep> responsesFromAnExecutedUploadFileSteps;

    @BeforeEach
    void setUp() {
        this.consultResponsesFromAnExecutedStepDto = TestUtils.dummyObject(ConsultResponsesFromAnExecutedStepDto.class);
        this.responsesFromAnExecutedUploadFileSteps = List.of(
                TestUtils.dummyObject(ResponsesFromAnExecutedUploadFileStep.class),
                TestUtils.dummyObject(ResponsesFromAnExecutedUploadFileStep.class),
                TestUtils.dummyObject(ResponsesFromAnExecutedUploadFileStep.class)
        );
    }

    @Test
    void when_prompted_it_should_query_the_files_successfully() {
        when(this.uploadFileStepCandidacyRepository.consultTestExecuted(
                this.consultResponsesFromAnExecutedStepDto.getApplicationIdentifier(),
                this.consultResponsesFromAnExecutedStepDto.getStepIdentifier()
        )).thenReturn(this.responsesFromAnExecutedUploadFileSteps);

        assertDoesNotThrow(() -> this.consultResponsesFromAnExecutedUploadFileStep.execute(this.consultResponsesFromAnExecutedStepDto));

        verify(this.uploadFileStepCandidacyRepository, only()).consultTestExecuted(
                this.consultResponsesFromAnExecutedStepDto.getApplicationIdentifier(),
                this.consultResponsesFromAnExecutedStepDto.getStepIdentifier()
        );
    }

}
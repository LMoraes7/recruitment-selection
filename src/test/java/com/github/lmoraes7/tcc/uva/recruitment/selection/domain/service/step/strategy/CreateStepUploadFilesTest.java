package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.UploadFileStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeFile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.StepDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.TypeUploadFileDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy.create.CreateStepUploadFiles;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.UploadFilelStepRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

final class CreateStepUploadFilesTest {
    private final UploadFilelStepRepository uploadFilelStepRepository = mock(UploadFilelStepRepository.class);
    private final CreateStepUploadFiles createStepUploadFiles = new CreateStepUploadFiles(this.uploadFilelStepRepository);

    private StepDto stepDto;
    private UploadFileStep uploadFileStep;

    @BeforeEach
    void setUp() {
        this.stepDto = new StepDto(
                "title",
                "description",
                TypeStep.UPLOAD_FILES,
                null,
                Set.of(
                        new TypeUploadFileDto("description", TypeFile.PDF)
                )
        );
        this.uploadFileStep = dummyObject(UploadFileStep.class);
    }

    @Test
    void when_prompted_should_create_a_step_successfully() {
        when(this.uploadFilelStepRepository.save(any(UploadFileStep.class))).thenReturn(this.uploadFileStep);

        assertDoesNotThrow(() -> this.createStepUploadFiles.execute(this.stepDto));

        verify(this.uploadFilelStepRepository, only()).save(any(UploadFileStep.class));
    }

    @Test
    void when_requested_it_must_return_the_type_of_step_to_be_treated() {
        assertDoesNotThrow(() -> assertEquals(TypeStep.UPLOAD_FILES, this.createStepUploadFiles.getTypeStep()));

        verifyNoInteractions(this.uploadFilelStepRepository);
    }

}
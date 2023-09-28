package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeFile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.SpecificExecutionStepCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper.UploadFileStepCandidacyRowMapper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper.vo.UploadFileStepCandidacyVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.query.StepCommands.FIND_FILES_TO_BE_SENT;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

final class UploadFileStepCandidacyRepositoryTest {
    private final JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
    private final UploadFileStepCandidacyRowMapper uploadFileStepCandidacyRowMapper = mock(UploadFileStepCandidacyRowMapper.class);
    private final UploadFileStepCandidacyRepository uploadFileStepCandidacyRepository = new UploadFileStepCandidacyRepository(this.jdbcTemplate, this.uploadFileStepCandidacyRowMapper);

    private String candidacyIdentifier;
    private String candidateIdentifier;
    private String selectiveProcessIdentifier;
    private String stepIdentifier;
    private List<UploadFileStepCandidacyVo> uploadFileStepCandidacyVos;

    @BeforeEach
    void setUp() {
        this.candidacyIdentifier = GeneratorIdentifier.forCandidacy();
        this.candidateIdentifier = GeneratorIdentifier.forCandidate();
        this.selectiveProcessIdentifier = GeneratorIdentifier.forSelectiveProcess();
        this.stepIdentifier = GeneratorIdentifier.forStep();
        this.uploadFileStepCandidacyVos = List.of(
                new UploadFileStepCandidacyVo(
                        this.candidacyIdentifier,
                        this.candidateIdentifier,
                        this.selectiveProcessIdentifier,
                        this.stepIdentifier,
                        dummyObject(String.class),
                        dummyObject(TypeFile.class)
                ),
                new UploadFileStepCandidacyVo(
                        this.candidacyIdentifier,
                        this.candidateIdentifier,
                        this.selectiveProcessIdentifier,
                        this.stepIdentifier,
                        dummyObject(String.class),
                        dummyObject(TypeFile.class)
                ),
                new UploadFileStepCandidacyVo(
                        this.candidacyIdentifier,
                        this.candidateIdentifier,
                        this.selectiveProcessIdentifier,
                        this.stepIdentifier,
                        dummyObject(String.class),
                        dummyObject(TypeFile.class)
                ),
                new UploadFileStepCandidacyVo(
                        this.candidacyIdentifier,
                        this.candidateIdentifier,
                        this.selectiveProcessIdentifier,
                        this.stepIdentifier,
                        dummyObject(String.class),
                        dummyObject(TypeFile.class)
                )
        );
    }

    @Test
    void when_requested_must_consult_a_upload_file_step_successfully() {
        when(this.jdbcTemplate.query(
                FIND_FILES_TO_BE_SENT.sql,
                this.uploadFileStepCandidacyRowMapper,
                this.candidacyIdentifier,
                this.candidateIdentifier,
                this.selectiveProcessIdentifier,
                this.stepIdentifier
        )).thenReturn(this.uploadFileStepCandidacyVos);

        assertDoesNotThrow(() -> {
            final Optional<SpecificExecutionStepCandidacyDto> optional = this.uploadFileStepCandidacyRepository.findFileToBeSent(
                    this.candidacyIdentifier,
                    this.candidateIdentifier,
                    this.selectiveProcessIdentifier,
                    this.stepIdentifier
            );

            assertNotNull(optional);
            assertTrue(optional.isPresent());
        });

        verify(this.jdbcTemplate, only()).query(
                FIND_FILES_TO_BE_SENT.sql,
                this.uploadFileStepCandidacyRowMapper,
                this.candidacyIdentifier,
                this.candidateIdentifier,
                this.selectiveProcessIdentifier,
                this.stepIdentifier
        );
    }

    @Test
    void when_prompted_must_fail_to_query_a_upload_file_step() {
        when(this.jdbcTemplate.query(
                FIND_FILES_TO_BE_SENT.sql,
                this.uploadFileStepCandidacyRowMapper,
                this.candidacyIdentifier,
                this.candidateIdentifier,
                this.selectiveProcessIdentifier,
                this.stepIdentifier
        )).thenReturn(List.of());

        assertDoesNotThrow(() -> {
                    final Optional<SpecificExecutionStepCandidacyDto> optional = this.uploadFileStepCandidacyRepository.findFileToBeSent(
                            this.candidacyIdentifier,
                            this.candidateIdentifier,
                            this.selectiveProcessIdentifier,
                            this.stepIdentifier
                    );

                    assertNotNull(optional);
                    assertTrue(optional.isEmpty());
                }
        );

        verify(this.jdbcTemplate, only()).query(
                FIND_FILES_TO_BE_SENT.sql,
                this.uploadFileStepCandidacyRowMapper,
                this.candidacyIdentifier,
                this.candidateIdentifier,
                this.selectiveProcessIdentifier,
                this.stepIdentifier
        );
    }
}
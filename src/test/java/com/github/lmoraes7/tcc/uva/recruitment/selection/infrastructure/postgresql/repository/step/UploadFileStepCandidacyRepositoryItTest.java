package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeFile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ExecuteFileDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ExecuteUploadFileStepCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.SpecificExecutionStepCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.batch.SaveUploadFileStepCandidacyBatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.query.StepCommands.SAVE_EXECUTION_UPLOAD_FILES_STEP;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("integration")
@SpringBootTest
final class UploadFileStepCandidacyRepositoryItTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UploadFileStepCandidacyRepository uploadFileStepCandidacyRepository;

    private String candidacyIdentifier;
    private String candidateIdentifier;
    private String selectiveProcessIdentifier;
    private String stepIdentifier;

    @BeforeEach
    void setUp() {
        this.candidacyIdentifier = "APP-123456781";
        this.candidateIdentifier = "CAN-123456781";
        this.selectiveProcessIdentifier = "SEL-123456781";
        this.stepIdentifier = "STE-123456781";
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/upload_file_step_candidacy_repository_test.sql"})
    void when_requested_must_consult_a_upload_file_step_successfully() {
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
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/upload_file_step_candidacy_repository_test.sql"})
    void when_prompted_must_fail_to_query_a_upload_file_step_1() {
        assertDoesNotThrow(() -> {
                    final Optional<SpecificExecutionStepCandidacyDto> optional = this.uploadFileStepCandidacyRepository.findFileToBeSent(
                            GeneratorIdentifier.forCandidacy(),
                            this.candidateIdentifier,
                            this.selectiveProcessIdentifier,
                            this.stepIdentifier
                    );

                    assertNotNull(optional);
                    assertTrue(optional.isEmpty());
                }
        );
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/upload_file_step_candidacy_repository_test.sql"})
    void when_prompted_must_fail_to_query_a_upload_file_step_2() {
        assertDoesNotThrow(() -> {
                    final Optional<SpecificExecutionStepCandidacyDto> optional = this.uploadFileStepCandidacyRepository.findFileToBeSent(
                            this.candidacyIdentifier,
                            GeneratorIdentifier.forCandidate(),
                            this.selectiveProcessIdentifier,
                            this.stepIdentifier
                    );

                    assertNotNull(optional);
                    assertTrue(optional.isEmpty());
                }
        );
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/upload_file_step_candidacy_repository_test.sql"})
    void when_prompted_must_fail_to_query_a_upload_file_step_3() {
        assertDoesNotThrow(() -> {
                    final Optional<SpecificExecutionStepCandidacyDto> optional = this.uploadFileStepCandidacyRepository.findFileToBeSent(
                            this.candidacyIdentifier,
                            this.candidateIdentifier,
                            GeneratorIdentifier.forSelectiveProcess(),
                            this.stepIdentifier
                    );

                    assertNotNull(optional);
                    assertTrue(optional.isEmpty());
                }
        );
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/upload_file_step_candidacy_repository_test.sql"})
    void when_prompted_must_fail_to_query_a_upload_file_step_4() {
        assertDoesNotThrow(() -> {
                    final Optional<SpecificExecutionStepCandidacyDto> optional = this.uploadFileStepCandidacyRepository.findFileToBeSent(
                            this.candidacyIdentifier,
                            this.candidateIdentifier,
                            this.selectiveProcessIdentifier,
                            GeneratorIdentifier.forStep()
                    );

                    assertNotNull(optional);
                    assertTrue(optional.isEmpty());
                }
        );
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/upload_file_step_candidacy_repository_test.sql"})
    void when_prompted_you_must_save_the_successfully_executed_step() throws IOException {
        final File mp4File = new File("./mp4_file_test.mp4");
        final File pdfFile = new File("./pdf_file_test.pdf");

        final ExecuteUploadFileStepCandidacyDto uploadFile = new ExecuteUploadFileStepCandidacyDto(
            List.of(
                    new ExecuteFileDto(
                            Files.readAllBytes(mp4File.toPath()),
                            TypeFile.MP4
                    ),
                    new ExecuteFileDto(
                            Files.readAllBytes(pdfFile.toPath()),
                            TypeFile.PDF
                    ),
                    new ExecuteFileDto(
                            Files.readAllBytes(pdfFile.toPath()),
                            TypeFile.PDF
                    )
            )
        );

        assertEquals(0, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "applications_steps_upload_files"));

        assertDoesNotThrow(() -> this.uploadFileStepCandidacyRepository.saveTestExecuted(this.candidacyIdentifier, this.stepIdentifier, uploadFile));

        assertEquals(3, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "applications_steps_upload_files"));
    }

}
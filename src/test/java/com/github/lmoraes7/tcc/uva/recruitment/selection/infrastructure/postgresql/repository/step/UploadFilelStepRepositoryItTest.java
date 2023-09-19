package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.UploadFileStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeFile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.FileVo;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.StepData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("integration")
@SpringBootTest
final class UploadFilelStepRepositoryItTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UploadFilelStepRepository uploadFilelStepRepository;

    private UploadFileStep step;

    @BeforeEach
    void setUp() {
        this.step = new UploadFileStep(
                new StepData(
                        GeneratorIdentifier.forStep(),
                        "title",
                        "description",
                        TypeStep.UPLOAD_FILES
                ),
                Set.of(
                        new FileVo(
                                "description",
                                TypeFile.PDF
                        ),
                        new FileVo(
                                "description 2",
                                TypeFile.MP4
                        )
                )
        );
    }

    @Test
    @Transactional
    void when_prompted_it_should_save_a_step_successfully() {
        assertEquals(0, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "steps"));
        assertEquals(0, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "steps_upload_files"));

        assertDoesNotThrow(() -> this.uploadFilelStepRepository.save(this.step));

        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "steps"));
        assertEquals(this.step.getFiles().size(), JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "steps_upload_files"));
    }

}
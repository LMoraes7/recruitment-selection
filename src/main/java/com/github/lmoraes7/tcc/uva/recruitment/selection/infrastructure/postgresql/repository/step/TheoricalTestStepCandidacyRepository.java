package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeQuestion;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ExecuteTheoricalTestStepCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ResponsesFromAnExecutedTheoricalQuestionStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.SpecificExecutionStepCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.batch.SaveAnswerMultipleChoiceTheoricalTestStepBatch;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.converter.ConverterHelper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper.TheoricalTestStepCandidacyRowMapper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper.vo.TheoricalTestStepCandidacyVo;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.vo.StepBatch;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.query.StepCommands.*;

@Repository
public class TheoricalTestStepCandidacyRepository {
    private final JdbcTemplate jdbcTemplate;
    private final TheoricalTestStepCandidacyRowMapper theoricalTestStepCandidacyRowMapper;

    public TheoricalTestStepCandidacyRepository(
            final JdbcTemplate jdbcTemplate,
            final TheoricalTestStepCandidacyRowMapper theoricalTestStepCandidacyRowMapper
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.theoricalTestStepCandidacyRowMapper = theoricalTestStepCandidacyRowMapper;
    }

    public Optional<SpecificExecutionStepCandidacyDto> findQuestions(
            final String candidacyIdentifier,
            final String candidateIdentifier,
            final String selectiveProcessIdentifier,
            final String stepIdentifier
    ) {
        final List<TheoricalTestStepCandidacyVo> result = this.jdbcTemplate.query(
                FIND_QUESTIONS_TO_BE_EXECUTED.sql,
                this.theoricalTestStepCandidacyRowMapper,
                candidacyIdentifier,
                candidateIdentifier,
                selectiveProcessIdentifier,
                stepIdentifier
        );

        if (result.isEmpty())
            return Optional.empty();

        return Optional.of(ConverterHelper.theoricalTesttoDto(result));
    }

    public void saveTestExecuted(
            final String candidacyIdentifier,
            final String stepIdentifier,
            final ExecuteTheoricalTestStepCandidacyDto theoricalTest
    ) {
        final List<StepBatch> stepBatches = theoricalTest.getQuestions().stream().map(it -> {
            String answerIdentifier = null;
            String answerDiscursive = null;

            if (it.getType() == TypeQuestion.MULTIPLE_CHOICE)
                answerIdentifier = it.getAnswer().getAnswer();
            else
                answerDiscursive = it.getAnswer().getAnswer();

            return new StepBatch(
                    candidacyIdentifier,
                    stepIdentifier,
                    it.getQuestionIdentifier(),
                    answerIdentifier,
                    it.getType(),
                    answerDiscursive
            );
        }).toList();

        this.jdbcTemplate.batchUpdate(
                SAVE_EXECUTION_QUESTION_MULTIPLE_CHOICE.sql,
                new SaveAnswerMultipleChoiceTheoricalTestStepBatch(stepBatches)
        );
    }

    public List<ResponsesFromAnExecutedTheoricalQuestionStep> consultTestExecuted(
            final String candidacyIdentifier,
            final String stepIdentifier
    ) {
        return this.jdbcTemplate.query(
                FIND_QUESTIONS_EXECUTEDS.sql,
                (rs, rowNumber) -> {
                    final TypeQuestion typeQuestion = TypeQuestion.valueOf(rs.getString("question_type"));

                    if (typeQuestion == TypeQuestion.MULTIPLE_CHOICE)
                        return new ResponsesFromAnExecutedTheoricalQuestionStep(
                                rs.getString("question_identifier"),
                                rs.getString("answer_identifier"),
                                typeQuestion,
                                rs.getString("question_description"),
                                rs.getString("answer_description"),
                                null,
                                rs.getBoolean("answer_correct")
                        );

                    return new ResponsesFromAnExecutedTheoricalQuestionStep(
                            rs.getString("question_identifier"),
                            null,
                            typeQuestion,
                            rs.getString("question_description"),
                            null,
                            rs.getString("discursive_answer"),
                            null
                    );
                },
                candidacyIdentifier,
                stepIdentifier
        );
    }

}

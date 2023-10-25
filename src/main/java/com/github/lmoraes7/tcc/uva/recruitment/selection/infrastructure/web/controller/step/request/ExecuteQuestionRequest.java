package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.step.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class ExecuteQuestionRequest {
    @NotBlank
    private String questionIdentifier;
    @NotBlank
    @Pattern(regexp = "DISCURSIVE|MULTIPLE_CHOICE")
    private String type;
    @NotNull
    @Valid
    private ExecuteAnswerRequest answer;

    public String getQuestionIdentifier() {
        return questionIdentifier;
    }

    public void setQuestionIdentifier(String questionIdentifier) {
        this.questionIdentifier = questionIdentifier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ExecuteAnswerRequest getAnswer() {
        return answer;
    }

    public void setAnswer(ExecuteAnswerRequest answer) {
        this.answer = answer;
    }

}

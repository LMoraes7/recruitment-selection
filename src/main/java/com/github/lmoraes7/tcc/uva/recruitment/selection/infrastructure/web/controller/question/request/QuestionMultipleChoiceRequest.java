package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.question.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class QuestionMultipleChoiceRequest {
    @NotBlank
    private String description;

    @Size(min = 5, max = 10)
    @NotNull
    private Set<AnswerRequest> answers;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<AnswerRequest> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<AnswerRequest> answers) {
        this.answers = answers;
    }

}

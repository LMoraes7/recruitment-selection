package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.step.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class StepRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    @Pattern(regexp = "THEORETICAL_TEST|UPLOAD_FILES|EXTERNAL")
    private String type;
    @Size(min = 3)
    private Set<String> questionsIdentifiers;
    @Size(min = 1)
    @Valid
    private Set<TypeUploadFileRequest> dataUploadFiles;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<String> getQuestionsIdentifiers() {
        return questionsIdentifiers;
    }

    public void setQuestionsIdentifiers(Set<String> questionsIdentifiers) {
        this.questionsIdentifiers = questionsIdentifiers;
    }

    public Set<TypeUploadFileRequest> getDataUploadFiles() {
        return dataUploadFiles;
    }

    public void setDataUploadFiles(Set<TypeUploadFileRequest> dataUploadFiles) {
        this.dataUploadFiles = dataUploadFiles;
    }

}

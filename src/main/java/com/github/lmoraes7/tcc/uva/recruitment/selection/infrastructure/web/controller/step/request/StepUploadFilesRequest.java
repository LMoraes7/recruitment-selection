package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.step.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class StepUploadFilesRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotNull
    @Size(min = 1, max = 3)
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

    public Set<TypeUploadFileRequest> getDataUploadFiles() {
        return dataUploadFiles;
    }

    public void setDataUploadFiles(Set<TypeUploadFileRequest> dataUploadFiles) {
        this.dataUploadFiles = dataUploadFiles;
    }

}

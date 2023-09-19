package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public final class StepDto {
    private final String title;
    private final String description;
    private final TypeStep type;
    private final Set<String> questionsIdentifiers;
    private final Set<TypeUploadFileDto> dataUploadFiles;

    public StepDto(
            final String title,
            final String description,
            final TypeStep type,
            final Set<String> questionsIdentifiers,
            final Set<TypeUploadFileDto> dataUploadFiles
    ) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.questionsIdentifiers = questionsIdentifiers;
        this.dataUploadFiles = dataUploadFiles;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TypeStep getType() {
        return type;
    }

    public Set<String> getQuestionsIdentifiers() {
        return Collections.unmodifiableSet(questionsIdentifiers);
    }

    public Collection<TypeUploadFileDto> getDataUploadFiles() {
        return Collections.unmodifiableCollection(dataUploadFiles);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        StepDto stepDto = (StepDto) object;
        return Objects.equals(title, stepDto.title) && Objects.equals(description, stepDto.description) && type == stepDto.type && Objects.equals(questionsIdentifiers, stepDto.questionsIdentifiers) && Objects.equals(dataUploadFiles, stepDto.dataUploadFiles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, type, questionsIdentifiers, dataUploadFiles);
    }

}

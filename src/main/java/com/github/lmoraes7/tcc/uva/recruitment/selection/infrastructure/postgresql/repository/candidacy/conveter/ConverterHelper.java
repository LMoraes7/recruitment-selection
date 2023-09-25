package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.conveter;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.StepCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.entity.CandidacyEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.entity.StepCandidacyEntity;

import java.util.ArrayList;
import java.util.List;

public final class ConverterHelper {

    public static CandidacyEntity toEntity(
            final String candidateIdentifier,
            final String selectiveProcessIdentifier,
            final Candidacy candidacy
    ) {
        return new CandidacyEntity(
                candidacy.getIdentifier(),
                candidacy.getStatus().name(),
                candidateIdentifier,
                selectiveProcessIdentifier,
                toEntity(candidacy.getSteps())
        );
    }

    private static List<StepCandidacyEntity> toEntity(final List<StepCandidacy> steps) {
        final List<StepCandidacyEntity> stepsEntity = new ArrayList<>();

        for (int i = 0; i < (steps.size() - 1); i++) {
            final StepCandidacy step = steps.get(i);
            stepsEntity.add(
                    new StepCandidacyEntity(
                            step.getData().getIdentifier(),
                            step.getLimitTime(),
                            steps.get(i + 1).getData().getIdentifier(),
                            step.getStatusStepCandidacy().name(),
                            step.getReleaseData()
                    )
            );
        }

        final StepCandidacy step = steps.get(steps.size() - 1);
        stepsEntity.add(
                new StepCandidacyEntity(
                        step.getData().getIdentifier(),
                        step.getLimitTime(),
                        null,
                        step.getStatusStepCandidacy().name(),
                        step.getReleaseData()
                )
        );

        return stepsEntity;
    }

}

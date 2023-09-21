package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.converter;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.SelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.StepSelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.entity.SelectiveProcessEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.entity.StepSelectiveProcessEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.utils.ArraySqlValue;

import java.util.ArrayList;
import java.util.List;

public final class ConverterHelper {

    public static SelectiveProcessEntity toEntity(final SelectiveProcess selectiveProcess) {
        return new SelectiveProcessEntity(
                selectiveProcess.getIdentifier(),
                selectiveProcess.getTitle(),
                selectiveProcess.getDescription(),
                selectiveProcess.getDesiredPosition(),
                selectiveProcess.getStatus().name(),
                ArraySqlValue.create(selectiveProcess.getResponsibilities().toArray(new String[0])),
                ArraySqlValue.create(selectiveProcess.getRequirements().toArray(new String[0])),
                ArraySqlValue.create(selectiveProcess.getAdditionalInfos().toArray(new String[0])),
                toEntity(selectiveProcess.getSteps())
        );
    }

    private static List<StepSelectiveProcessEntity> toEntity(final List<StepSelectiveProcess> steps) {
        final List<StepSelectiveProcessEntity> stepsEntity = new ArrayList<>();

        for (int i = 0; i < (steps.size() - 1); i++) {
            final StepSelectiveProcess step = steps.get(i);
            stepsEntity.add(
                    new StepSelectiveProcessEntity(
                            step.getData().getIdentifier(),
                            step.getLimitTime(),
                            steps.get(i + 1).getData().getIdentifier()
                    )
            );
        }

        final StepSelectiveProcess step = steps.get(steps.size() - 1);
        stepsEntity.add(
                new StepSelectiveProcessEntity(
                        step.getData().getIdentifier(),
                        step.getLimitTime(),
                        null
                )
        );

        return stepsEntity;
    }

}

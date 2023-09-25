package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.converter;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.InternalErrorException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.ExternalStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.SelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.StepSelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.StepData;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.entity.SelectiveProcessEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.entity.StepSelectiveProcessEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.rowmapper.vo.SelectiveProcessStepsVo;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.utils.ArraySqlValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.INTG_002;

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

    public static SelectiveProcess toDomain(final List<SelectiveProcessStepsVo> vos) {
        final SelectiveProcessStepsVo spsv = vos.stream()
                .findFirst()
                .orElseThrow(() -> new InternalErrorException(INTG_002));

        final Map<String, SelectiveProcessStepsVo> allStepsInMap = vos.stream()
                .collect(Collectors.toMap(SelectiveProcessStepsVo::getStepIdentifier, it -> it));


        final Map<String, SelectiveProcessStepsVo> voMap = vos.stream()
                .filter(it -> it.getNextStepIdentifier() != null)
                .collect(Collectors.toMap(SelectiveProcessStepsVo::getNextStepIdentifier, it -> allStepsInMap.get(it.getNextStepIdentifier())));

        SelectiveProcessStepsVo firstStep = null;

        for (final SelectiveProcessStepsVo vo : vos) {
            if (!voMap.containsKey(vo.getStepIdentifier())) {
                firstStep = vo;
                break;
            }
        }

        if (firstStep == null)
            throw new InternalErrorException(INTG_002);

        final List<StepSelectiveProcess> steps = new ArrayList<>();
        steps.add(new ExternalStep(new StepData(firstStep.getStepIdentifier(), firstStep.getTypeStep()), firstStep.getStepLimitTime()));

        String next = firstStep.getNextStepIdentifier();
        while (next != null) {
            final SelectiveProcessStepsVo vo = voMap.get(next);
            steps.add(new ExternalStep(new StepData(vo.getStepIdentifier(), vo.getTypeStep()), vo.getStepLimitTime()));

            next = vo.getNextStepIdentifier();
        }

        return new SelectiveProcess(
                spsv.getSelectiveProcessIdentifier(),
                spsv.getStatusSelectiveProcess(),
                steps
        );
    }

}

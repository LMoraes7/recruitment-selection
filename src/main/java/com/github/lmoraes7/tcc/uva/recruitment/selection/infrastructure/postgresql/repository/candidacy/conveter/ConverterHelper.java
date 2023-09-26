package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.conveter;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.InternalErrorException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.StepCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.dto.SelectiveProcessSpecificCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.dto.SpecificCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.dto.StepSpecificCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.dto.SummaryOfCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.entity.CandidacyEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.entity.StepCandidacyEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.rowmapper.vo.CandidacyPageVo;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.rowmapper.vo.CandidacyVo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.INTG_002;

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

    public static SpecificCandidacyDto toDto(final List<CandidacyVo> candidacyVos) {
        final CandidacyVo cvo = candidacyVos.stream()
                .findFirst()
                .orElseThrow(() -> new InternalErrorException(INTG_002));

        final Map<String, CandidacyVo> allStepsInMap = candidacyVos.stream()
                .collect(Collectors.toMap(CandidacyVo::getStepId, it -> it));

        final Map<String, CandidacyVo> voMap = candidacyVos.stream()
                .filter(it -> it.getNextStepId() != null)
                .collect(Collectors.toMap(CandidacyVo::getNextStepId, it -> allStepsInMap.get(it.getNextStepId())));

        CandidacyVo firstStep = null;

        for (final CandidacyVo vo : candidacyVos) {
            if (!voMap.containsKey(vo.getStepId())) {
                firstStep = vo;
                break;
            }
        }

        if (firstStep == null)
            throw new InternalErrorException(INTG_002);

        final List<StepSpecificCandidacyDto> steps = new ArrayList<>();
        steps.add(new StepSpecificCandidacyDto(firstStep.getStepId(), firstStep.getStepStatus(), firstStep.getStepTitle(), firstStep.getStepType()));

        String next = firstStep.getNextStepId();
        while (next != null) {
            final CandidacyVo vo = voMap.get(next);
            steps.add(new StepSpecificCandidacyDto(vo.getStepId(), vo.getStepStatus(), vo.getStepTitle(), vo.getStepType()));

            next = vo.getNextStepId();
        }

        return new SpecificCandidacyDto(
                firstStep.getCandidacyId(),
                firstStep.getCandidacyStatus(),
                new SelectiveProcessSpecificCandidacyDto(
                        firstStep.getSelectiveProcessId(),
                        firstStep.getSelectiveProcessTitle()
                ),
                steps
        );
    }

    public static List<SummaryOfCandidacy> toDto(final Collection<CandidacyPageVo> candidacyPageVos) {
        return candidacyPageVos.stream().map(it -> new SummaryOfCandidacy(it.getIdentifier(), it.getStatus(), it.getSelectiveProcessTitle())).toList();
    }

}

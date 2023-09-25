package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.converter;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.ExternalStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.SelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.StepCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusStepCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.StepData;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public final class ConverterHelper {

    public static Candidacy toModel(final SelectiveProcess selectiveProcess) {
        final List<StepCandidacy> stepsCandidacy = selectiveProcess.getSteps()
                .stream()
                .map(it -> new ExternalStep(
                        new StepData(it.getData().getIdentifier(), it.getData().getType()),
                        it.getLimitTime(),
                        StatusStepCandidacy.BLOCKED
                ))
                .collect(Collectors.toList());

        final StepCandidacy firstStep = stepsCandidacy.get(0);
        if (firstStep.getData().getType() != TypeStep.EXTERNAL) {
            stepsCandidacy.add(
                    0,
                    new ExternalStep(
                            new StepData(firstStep.getData().getIdentifier(), firstStep.getData().getType()),
                            firstStep.getLimitTime(),
                            StatusStepCandidacy.WAITING_FOR_EXECUTION,
                            LocalDate.now()
                    )
            );
        }

        return new Candidacy(
                GeneratorIdentifier.forCandidacy(),
                StatusCandidacy.IN_PROGRESS,
                stepsCandidacy
        );
    }

}

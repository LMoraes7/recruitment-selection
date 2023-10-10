package com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.ConsultExecutedStepCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.ExecuteStepCandidacy;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ExecuteStepCandidacyListener {

    @Async
    @EventListener
    public void executeStepCandidacy(final ExecuteStepCandidacy event) {

    }

    @Async
    @EventListener
    public void consultExecutedStepCandidacy(final ConsultExecutedStepCandidacy event) {

    }

}

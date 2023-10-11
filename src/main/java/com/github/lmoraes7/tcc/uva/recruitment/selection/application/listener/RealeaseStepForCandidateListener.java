package com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.RealeaseStepForCandidate;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class RealeaseStepForCandidateListener {

    @Async
    @EventListener
    public void realeaseStepForCandidate(final RealeaseStepForCandidate event) {

    }

}

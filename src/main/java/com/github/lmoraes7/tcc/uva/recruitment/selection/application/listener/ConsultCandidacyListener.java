package com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.ConsultCandidacy;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ConsultCandidacyListener {

    @Async
    @EventListener
    public void consultCandidacy(final ConsultCandidacy event) {

    }

}

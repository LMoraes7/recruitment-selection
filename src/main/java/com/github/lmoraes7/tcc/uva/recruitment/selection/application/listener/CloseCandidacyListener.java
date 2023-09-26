package com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.CloseCandidacy;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class CloseCandidacyListener {

    @Async
    @EventListener
    public void closeCandidacy(final CloseCandidacy event) {

    }

}

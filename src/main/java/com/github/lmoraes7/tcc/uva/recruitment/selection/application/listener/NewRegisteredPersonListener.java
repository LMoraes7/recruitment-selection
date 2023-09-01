package com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.NewRegisteredCandidate;
import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.NewRegisteredEmployee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.application.publisher.EmailPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class NewRegisteredPersonListener {
    private final EmailPublisher emailPublisher;

    public NewRegisteredPersonListener(final EmailPublisher emailPublisher) {
        this.emailPublisher = emailPublisher;
    }

    @Async
    @EventListener
    public void newRegisteredEmployee(final NewRegisteredEmployee event) {

    }

    @Async
    @EventListener
    public void newRegisteredCandidate(final NewRegisteredCandidate event) {

    }

}

package com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.ConsultSelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.ConsultSelectiveProcessPagineted;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ConsultSelectiveProcessListener {

    @Async
    @EventListener
    public void consultdSelectiveProcess(final ConsultSelectiveProcess event) {

    }

    @Async
    @EventListener
    public void consultSelectiveProcessPagineted(final ConsultSelectiveProcessPagineted event) {

    }

}

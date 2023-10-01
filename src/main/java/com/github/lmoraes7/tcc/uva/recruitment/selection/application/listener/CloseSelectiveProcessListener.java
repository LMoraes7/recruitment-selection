package com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.CloseCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.CloseSelectiveProcess;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class CloseSelectiveProcessListener {

    @Async
    @EventListener
    public void closeSelectiveProcess(final CloseSelectiveProcess event) {

    }

}

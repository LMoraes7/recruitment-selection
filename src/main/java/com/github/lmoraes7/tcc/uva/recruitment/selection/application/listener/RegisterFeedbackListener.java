package com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.RegisterFeedback;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class RegisterFeedbackListener {

    @Async
    @EventListener
    public void registerFeedback(final RegisterFeedback event) {

    }

}

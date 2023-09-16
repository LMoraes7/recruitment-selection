package com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.NewRequestPasswordReset;
import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.PasswordReset;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PasswordResetListener {

    @Async
    @EventListener
    public void passwordReset(final PasswordReset event) {

    }

    @Async
    @EventListener
    public void newRequestPasswordReset(final NewRequestPasswordReset event) {

    }

}

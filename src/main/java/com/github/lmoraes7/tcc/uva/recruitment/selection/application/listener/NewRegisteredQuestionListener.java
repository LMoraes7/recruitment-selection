package com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.NewRegisteredQuestion;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class NewRegisteredQuestionListener {

    @Async
    @EventListener
    public void newRegisteredQuestion(final NewRegisteredQuestion event) {

    }

}

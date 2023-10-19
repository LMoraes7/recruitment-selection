package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.feedback.converter;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.feedback.dto.RegisterFeedbackDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.feedback.request.FeedbackRequest;

public final class ConverterHelper {

    public static RegisterFeedbackDto toDto(
            final String candidacyIdentifier,
            final String stepIdentifier,
            final FeedbackRequest request
    ) {
        return new RegisterFeedbackDto(
                candidacyIdentifier,
                stepIdentifier,
                request.getAdditionalInfo(),
                request.getPointing()
        );
    }

}

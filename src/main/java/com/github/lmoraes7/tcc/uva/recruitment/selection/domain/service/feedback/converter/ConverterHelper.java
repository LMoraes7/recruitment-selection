package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.feedback.converter;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Feedback;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.Result;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.feedback.dto.RegisterFeedbackDto;

import java.math.BigDecimal;

public final class ConverterHelper {

    public static Feedback toModel(final RegisterFeedbackDto dto) {
        Result result = null;
        if (dto.getPointing().compareTo(new BigDecimal("6.00")) >= 0)
            result = Result.APPROVED;
        else
            result = Result.DISAPPROVED;

        return new Feedback(
                GeneratorIdentifier.forFeedback(),
                result,
                dto.getPointing(),
                dto.getAdditionalInfo()
        );
    }

}

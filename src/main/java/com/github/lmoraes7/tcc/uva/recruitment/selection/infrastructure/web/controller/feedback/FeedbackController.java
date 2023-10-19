package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.feedback;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.feedback.RegisterFeedbackUseCase;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.context.SecurityEmployeeContext;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.feedback.converter.ConverterHelper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.feedback.request.FeedbackRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback")
@Validated
public final class FeedbackController {
    private final RegisterFeedbackUseCase registerFeedbackUseCase;

    @Autowired
    public FeedbackController(final RegisterFeedbackUseCase registerFeedbackUseCase) {
        this.registerFeedbackUseCase = registerFeedbackUseCase;
    }

    @PostMapping("/candidacy/{candidacyIdentifier}/step/{stepIdentifier}")
    public ResponseEntity<?> createFeedback(
            @RequestBody @Valid final FeedbackRequest request,
            @PathVariable final String candidacyIdentifier,
            @PathVariable final String stepIdentifier
    ) {
        this.registerFeedbackUseCase.execute(
                SecurityEmployeeContext.getContext(),
                ConverterHelper.toDto(candidacyIdentifier, stepIdentifier, request)
        );

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}

package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.step;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.*;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.SpecificExecutionStepCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.context.SecurityCandidateContext;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.context.SecurityEmployeeContext;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.step.converter.ConverterHelper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.step.request.ExecuteStepCandidacyRequest;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.step.request.ReleaseStepForCandidateRequest;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.step.request.StepRequest;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.step.response.ResponsesFromAnExecutedStepResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/step")
@Validated
public class StepController {
    private final ConsultResponsesFromAnExecutedStepUseCase consultResponsesFromAnExecutedStepUseCase;
    private final ConsultSpecificExecutionStepCandidacyUseCase consultSpecificExecutionStepCandidacyUseCase;
    private final CreateStepUseCase createStepUseCase;
    private final ExecuteStepCandidacyUseCase executeStepCandidacyUseCase;
    private final ReleaseStepForCandidateUseCase releaseStepForCandidateUseCase;

    @Autowired
    public StepController(
            final ConsultResponsesFromAnExecutedStepUseCase consultResponsesFromAnExecutedStepUseCase,
            final ConsultSpecificExecutionStepCandidacyUseCase consultSpecificExecutionStepCandidacyUseCase,
            final CreateStepUseCase createStepUseCase,
            final ExecuteStepCandidacyUseCase executeStepCandidacyUseCase,
            final ReleaseStepForCandidateUseCase releaseStepForCandidateUseCase
    ) {
        this.consultResponsesFromAnExecutedStepUseCase = consultResponsesFromAnExecutedStepUseCase;
        this.consultSpecificExecutionStepCandidacyUseCase = consultSpecificExecutionStepCandidacyUseCase;
        this.createStepUseCase = createStepUseCase;
        this.executeStepCandidacyUseCase = executeStepCandidacyUseCase;
        this.releaseStepForCandidateUseCase = releaseStepForCandidateUseCase;
    }

    @GetMapping("/{stepIdentifier}/type/THEORETICAL_TEST/application/{applicationIdentifier}")
    public ResponseEntity<ResponsesFromAnExecutedStepResponse> consultResponsesFromAnExecutedStepTheorical(
            @PathVariable final String stepIdentifier,
            @PathVariable final String applicationIdentifier
    ) {
        final ResponsesFromAnExecutedStepResponse response = ConverterHelper.toResponse(
                this.consultResponsesFromAnExecutedStepUseCase.execute(
                        SecurityEmployeeContext.getContext(),
                        ConverterHelper.toDto(stepIdentifier, TypeStep.THEORETICAL_TEST.name(), applicationIdentifier)
                )
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{stepIdentifier}/type/UPLOAD_FILES/application/{applicationIdentifier}")
    public void consultResponsesFromAnExecutedStepUpload(
            @PathVariable final String stepIdentifier,
            @PathVariable final String applicationIdentifier,
            final HttpServletResponse httpServletResponse
    ) throws IOException {
//        final ResponsesFromAnExecutedStepResponse response = ConverterHelper.toResponse(
//                this.consultResponsesFromAnExecutedStepUseCase.execute(
//                        SecurityEmployeeContext.getContext(),
//                        ConverterHelper.toDto(stepIdentifier, TypeStep.UPLOAD_FILES.name(), applicationIdentifier)
//                )
//        );
//
//        try (final ZipOutputStream zipOutputStream = new ZipOutputStream(httpServletResponse.getOutputStream())) {
//
//        }
    }

    @GetMapping("/{stepIdentifier}/type/{type}/candidacy/{candidacyIdentifier}/selective-process/{selectiveProcessIdentifier}")
    public void consultSpecificExecutionStepCandidacy(
            @PathVariable final String stepIdentifier,
            @PathVariable @Valid @Pattern(regexp = "THEORETICAL_TEST|UPLOAD_FILES|EXTERNAL") final String type,
            @PathVariable final String candidacyIdentifier,
            @PathVariable final String selectiveProcessIdentifier
    ) {
        SpecificExecutionStepCandidacyDto result = this.consultSpecificExecutionStepCandidacyUseCase.execute(
                SecurityCandidateContext.getContext(),
                ConverterHelper.toDto(stepIdentifier, type, candidacyIdentifier, selectiveProcessIdentifier)
        );
        ConverterHelper.toResponse(result);
    }

    @PostMapping
    public ResponseEntity<?> createStep(@RequestBody @Valid final StepRequest request) {
        this.createStepUseCase.execute(
                SecurityEmployeeContext.getContext(),
                ConverterHelper.toDto(request)
        );

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{stepIdentifier}/type/{type}/candidacy/{candidacyIdentifier}/selective-process/{selectiveProcessIdentifier}")
    public void executeStepCandidacy(
            @PathVariable final String stepIdentifier,
            @PathVariable @Valid @Pattern(regexp = "THEORETICAL_TEST|UPLOAD_FILES") final String type,
            @PathVariable final String candidacyIdentifier,
            @PathVariable final String selectiveProcessIdentifier,
            @RequestBody @Valid final ExecuteStepCandidacyRequest request
    ) {

    }

    @PatchMapping("/{stepIdentifier}/candidacy/{candidacyIdentifier}")
    public ResponseEntity<?> releaseStepForCandidate(
            @PathVariable final String stepIdentifier,
            @PathVariable final String candidacyIdentifier,
            @RequestBody(required = false) @Valid final ReleaseStepForCandidateRequest request
    ) {
        this.releaseStepForCandidateUseCase.execute(
                SecurityEmployeeContext.getContext(),
                ConverterHelper.toDto(stepIdentifier, candidacyIdentifier, request)
        );

        return ResponseEntity.noContent().build();
    }

}

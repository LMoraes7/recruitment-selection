package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.step;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.*;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ResponsesFromAnExecutedStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.SpecificExecutionStepCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.context.SecurityCandidateContext;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.context.SecurityEmployeeContext;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.step.converter.ConverterHelper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.step.request.*;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.step.response.ResponsesFromAnExecutedStepResponse;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.step.response.SpecificExecutionStepCandidacyResponse;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.zip.ZipEntry;
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
                        ConverterHelper.toDto(
                                stepIdentifier,
                                TypeStep.THEORETICAL_TEST.name(),
                                applicationIdentifier
                        )
                )
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{stepIdentifier}/type/UPLOAD_FILES/application/{applicationIdentifier}", produces = "application/zip")
    public void consultResponsesFromAnExecutedStepUpload(
            @PathVariable final String stepIdentifier,
            @PathVariable final String applicationIdentifier,
            final HttpServletResponse httpServletResponse
    ) throws IOException {
        final ResponsesFromAnExecutedStep result = this.consultResponsesFromAnExecutedStepUseCase.execute(
                SecurityEmployeeContext.getContext(),
                ConverterHelper.toDto(stepIdentifier, TypeStep.UPLOAD_FILES.name(), applicationIdentifier)
        );

        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.addHeader("Content-Disposition", "attachment; filename=\"files.zip\"");

        try (final ZipOutputStream zipOutputStream = new ZipOutputStream(httpServletResponse.getOutputStream())) {
            result.getUploadStepExecuted().getFiles().forEach(it -> {
                try {
                    zipOutputStream.putNextEntry(new ZipEntry(it.getName()));
                    zipOutputStream.write(it.getBytes());
                    zipOutputStream.closeEntry();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            zipOutputStream.finish();
        }
    }

    @GetMapping("/{stepIdentifier}/type/{type}/candidacy/{candidacyIdentifier}/selective-process/{selectiveProcessIdentifier}")
    public ResponseEntity<SpecificExecutionStepCandidacyResponse> consultSpecificExecutionStepCandidacy(
            @PathVariable final String stepIdentifier,
            @PathVariable @Valid @Pattern(regexp = "THEORETICAL_TEST|UPLOAD_FILES|EXTERNAL") final String type,
            @PathVariable final String candidacyIdentifier,
            @PathVariable final String selectiveProcessIdentifier
    ) {
        final SpecificExecutionStepCandidacyDto result = this.consultSpecificExecutionStepCandidacyUseCase.execute(
                SecurityCandidateContext.getContext(),
                ConverterHelper.toDto(
                        stepIdentifier,
                        type,
                        candidacyIdentifier,
                        selectiveProcessIdentifier
                )
        );
        return ResponseEntity.ok(ConverterHelper.toResponse(result));
    }

    @PostMapping("/theorical_test")
    public ResponseEntity<?> createStepTheoricalStep(@RequestBody @Valid final StepTheoricalTestRequest request) {
        this.createStepUseCase.execute(
                SecurityEmployeeContext.getContext(),
                ConverterHelper.toDto(request)
        );

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/upload_files")
    public ResponseEntity<?> createStepUploadFiles(@RequestBody @Valid final StepUploadFilesRequest request) {
        this.createStepUseCase.execute(
                SecurityEmployeeContext.getContext(),
                ConverterHelper.toDto(request)
        );

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/external")
    public ResponseEntity<?> createStepExternal(@RequestBody @Valid final StepExternalRequest request) {
        this.createStepUseCase.execute(
                SecurityEmployeeContext.getContext(),
                ConverterHelper.toDto(request)
        );

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{stepIdentifier}/type/THEORETICAL_TEST/candidacy/{candidacyIdentifier}/selective-process/{selectiveProcessIdentifier}")
    public ResponseEntity<Object> executeStepCandidacyTheoricalTest(
            @PathVariable final String stepIdentifier,
            @PathVariable final String candidacyIdentifier,
            @PathVariable final String selectiveProcessIdentifier,
            @RequestBody @Valid final ExecuteStepCandidacyTheoricalTestRequest request
    ) {
        this.executeStepCandidacyUseCase.execute(
                SecurityCandidateContext.getContext(),
                ConverterHelper.toDto(
                        stepIdentifier,
                        candidacyIdentifier,
                        selectiveProcessIdentifier,
                        request
                )
        );
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{stepIdentifier}/type/UPLOAD_FILES/candidacy/{candidacyIdentifier}/selective-process/{selectiveProcessIdentifier}")
    public ResponseEntity<?> executeStepCandidacyUploadFiles(
            @PathVariable final String stepIdentifier,
            @PathVariable final String candidacyIdentifier,
            @PathVariable final String selectiveProcessIdentifier,
            @RequestParam("files") final MultipartFile[] files
    ) {
        this.executeStepCandidacyUseCase.execute(
                SecurityCandidateContext.getContext(),
                ConverterHelper.toDto(
                        stepIdentifier,
                        candidacyIdentifier,
                        selectiveProcessIdentifier,
                        files
                )
        );
        return ResponseEntity.ok().build();
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

package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.candidacy;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.CloseCandidacyUseCase;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.ConsultListOfCandidacyUseCase;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.ConsultSpecificCandidacyUseCase;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.CreateCandidacyUseCase;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.context.SecurityCandidateContext;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.candidacy.converter.ConverterHelper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.candidacy.response.CandidacyPaginatedResponse;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.candidacy.response.SpecificCandidacyResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/candidacy")
@Validated
public class CandidacyController {
    private final CloseCandidacyUseCase closeCandidacyUseCase;
    private final ConsultListOfCandidacyUseCase consultListOfCandidacyUseCase;
    private final ConsultSpecificCandidacyUseCase consultSpecificCandidacyUseCase;
    private final CreateCandidacyUseCase createCandidacyUseCase;

    @Autowired
    public CandidacyController(
            final CloseCandidacyUseCase closeCandidacyUseCase,
            final ConsultListOfCandidacyUseCase consultListOfCandidacyUseCase,
            final ConsultSpecificCandidacyUseCase consultSpecificCandidacyUseCase,
            final CreateCandidacyUseCase createCandidacyUseCase
    ) {
        this.closeCandidacyUseCase = closeCandidacyUseCase;
        this.consultListOfCandidacyUseCase = consultListOfCandidacyUseCase;
        this.consultSpecificCandidacyUseCase = consultSpecificCandidacyUseCase;
        this.createCandidacyUseCase = createCandidacyUseCase;
    }

    @PatchMapping("/close/{candidacyIdentifier}/selective-process/{selectiveProcessIdentifier}")
    public ResponseEntity<?> closeCandidacy(
            @PathVariable final String candidacyIdentifier,
            @PathVariable final String selectiveProcessIdentifier
    ) {
        this.closeCandidacyUseCase.execute(
                SecurityCandidateContext.getContext(),
                ConverterHelper.toDto(candidacyIdentifier, selectiveProcessIdentifier)
        );

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<CandidacyPaginatedResponse> consultListOfCandidacy(
            @RequestParam @Valid @Min(1) final Integer pageSize,
            @RequestParam @Valid @Min(0) final Integer pageNumber
    ) {
        final CandidacyPaginatedResponse response = ConverterHelper.toResponse(
                this.consultListOfCandidacyUseCase.execute(
                        SecurityCandidateContext.getContext(),
                        ConverterHelper.toDto(pageSize, pageNumber)
                )
        );

        if (response.getCandidacies().isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{candidacyIdentifier}")
    public ResponseEntity<SpecificCandidacyResponse> consultSpecificCandidacy(@PathVariable final String candidacyIdentifier) {
        final SpecificCandidacyResponse response = ConverterHelper.toResponse(
                this.consultSpecificCandidacyUseCase.execute(
                        SecurityCandidateContext.getContext(),
                        candidacyIdentifier
                )
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/selective-process/{selectiveProcessIdentifier}")
    public ResponseEntity<?> createCandidacy(@PathVariable final String selectiveProcessIdentifier) {
        this.createCandidacyUseCase.execute(
                SecurityCandidateContext.getContext(),
                ConverterHelper.toDto(selectiveProcessIdentifier)
        );

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}

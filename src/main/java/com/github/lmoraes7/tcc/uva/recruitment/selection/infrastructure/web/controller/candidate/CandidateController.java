package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.candidate;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidate.CreateCandidateUseCase;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.candidate.converter.ConverterHelper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.candidate.request.CandidateRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidate")
@Validated
public class CandidateController {
    private final CreateCandidateUseCase createCandidateUseCase;

    public CandidateController(final CreateCandidateUseCase createCandidateUseCase) {
        this.createCandidateUseCase = createCandidateUseCase;
    }

    @PostMapping
    public ResponseEntity<?> createCandidate(@RequestBody @Valid final CandidateRequest request) {
        this.createCandidateUseCase.execute(ConverterHelper.toDto(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}

package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.selective.process;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process.CloseSelectiveProcessUseCase;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process.ConsultListOfSelectiveProcessUseCase;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process.ConsultSpecificSelectiveProcessUseCase;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process.CreateSelectiveProcessUseCase;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.context.SecurityEmployeeContext;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.selective.process.converter.ConverterHelper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.selective.process.request.SelectiveProcessRequest;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.selective.process.response.SelectiveProcessoPaginatedResponse;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.selective.process.response.SpecificSelectiveProcessResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/selective-process")
@Validated
public final class SelectiveProcessController {
    private final CloseSelectiveProcessUseCase closeSelectiveProcessUseCase;
    private final ConsultListOfSelectiveProcessUseCase consultListOfSelectiveProcessUseCase;
    private final ConsultSpecificSelectiveProcessUseCase consultSpecificSelectiveProcessUseCase;
    private final CreateSelectiveProcessUseCase createSelectiveProcessUseCase;

    @Autowired
    public SelectiveProcessController(
            final CloseSelectiveProcessUseCase closeSelectiveProcessUseCase,
            final ConsultListOfSelectiveProcessUseCase consultListOfSelectiveProcessUseCase,
            final ConsultSpecificSelectiveProcessUseCase consultSpecificSelectiveProcessUseCase,
            final CreateSelectiveProcessUseCase createSelectiveProcessUseCase
    ) {
        this.closeSelectiveProcessUseCase = closeSelectiveProcessUseCase;
        this.consultListOfSelectiveProcessUseCase = consultListOfSelectiveProcessUseCase;
        this.consultSpecificSelectiveProcessUseCase = consultSpecificSelectiveProcessUseCase;
        this.createSelectiveProcessUseCase = createSelectiveProcessUseCase;
    }

    @PatchMapping("/{selectiveProcessIdentifier]")
    public ResponseEntity<?> closeSelectiveProcess(@PathVariable final String selectiveProcessIdentifier) {
        this.closeSelectiveProcessUseCase.execute(
                SecurityEmployeeContext.getContext(),
                selectiveProcessIdentifier
        );

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<SelectiveProcessoPaginatedResponse> consultListOfSelectiveProcess(
            @RequestParam @Valid @Min(1) final Integer pageSize,
            @RequestParam @Valid @Min(0) final Integer pageNumber
    ) {
        final SelectiveProcessoPaginatedResponse response = ConverterHelper.toResponse(this.consultListOfSelectiveProcessUseCase.execute(ConverterHelper.toDto(pageSize, pageNumber)));

        if (response.getSelectiveProcesses().isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{selectiveProcessIdentifier}")
    public ResponseEntity<SpecificSelectiveProcessResponse> consultSpecificSelectiveProcess(@PathVariable final String selectiveProcessIdentifier) {
        final SpecificSelectiveProcessResponse response = ConverterHelper.toResponse(this.consultSpecificSelectiveProcessUseCase.execute(selectiveProcessIdentifier));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> createSelectiveProcess(@RequestBody @Valid final SelectiveProcessRequest request) {
        this.createSelectiveProcessUseCase.execute(
                SecurityEmployeeContext.getContext(),
                ConverterHelper.toDto(request)
        );

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}

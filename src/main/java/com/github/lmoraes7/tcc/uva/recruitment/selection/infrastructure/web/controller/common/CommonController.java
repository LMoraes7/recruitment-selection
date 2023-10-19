package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.common;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.common.RedefinePasswordUseCase;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.common.RequestPasswordResetUseCase;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.common.converter.ConverterHelper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.common.request.RedefinePasswordRequest;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.common.request.ResetPasswordRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/common")
@Validated
public final class CommonController {
    private final RedefinePasswordUseCase redefinePasswordUseCase;
    private final RequestPasswordResetUseCase requestPasswordResetUseCase;

    @Autowired
    public CommonController(
            final RedefinePasswordUseCase redefinePasswordUseCase,
            final RequestPasswordResetUseCase requestPasswordResetUseCase
    ) {
        this.redefinePasswordUseCase = redefinePasswordUseCase;
        this.requestPasswordResetUseCase = requestPasswordResetUseCase;
    }

    @PostMapping("/redefine-password")
    public ResponseEntity<?> redefinePassword(@RequestBody @Valid final RedefinePasswordRequest request) {
        this.redefinePasswordUseCase.execute(ConverterHelper.toDto(request));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid final ResetPasswordRequest request) {
        this.requestPasswordResetUseCase.execute(ConverterHelper.toDto(request));
        return ResponseEntity.ok().build();
    }

}

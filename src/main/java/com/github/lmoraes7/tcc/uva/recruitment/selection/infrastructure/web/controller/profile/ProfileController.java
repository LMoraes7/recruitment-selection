package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.profile;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.profile.CreateProfileUseCase;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.context.SecurityEmployeeContext;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.profile.converter.ConverterHelper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.profile.request.ProfileRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
@Validated
public class ProfileController {
    private final CreateProfileUseCase createProfileUseCase;

    @Autowired
    public ProfileController(final CreateProfileUseCase createProfileUseCase) {
        this.createProfileUseCase = createProfileUseCase;
    }

    @PostMapping
    public ResponseEntity<?> createProfile(@RequestBody @Valid final ProfileRequest request) {
        this.createProfileUseCase.execute(
                SecurityEmployeeContext.getContext(),
                ConverterHelper.toDto(request)
        );

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}

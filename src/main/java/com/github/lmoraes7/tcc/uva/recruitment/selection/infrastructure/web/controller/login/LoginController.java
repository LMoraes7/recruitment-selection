package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.login;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.login.LoginUseCase;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.login.constants.LoginType;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.login.request.LoginRequest;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.login.converter.ConverterHelper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.login.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class LoginController {
    private final LoginUseCase loginUseCase;

    @Autowired
    public LoginController(final LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid final LoginRequest request) {
        final var token = this.loginUseCase.login(request.getUsername(), request.getPassword(), LoginType.valueOf(request.getLoginType()));
        return ResponseEntity.ok(ConverterHelper.credentialAccessToResponse(token));
    }

}

package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.login.converter;

import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.vo.AccessToken;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.login.response.LoginResponse;

public final class ConverterHelper {

    public static LoginResponse credentialAccessToResponse(final AccessToken accessToken) {
        return new LoginResponse(
                accessToken.getType(),
                accessToken.getHash(),
                accessToken.getCreationDate(),
                accessToken.getExpirationDate(),
                accessToken.getExpiresIn()
        );
    }

}

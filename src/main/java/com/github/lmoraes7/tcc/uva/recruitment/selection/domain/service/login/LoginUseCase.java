package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.login;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.FailedLogin;
import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.LoginSuccessfullyEvent;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.BusinessException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.login.constants.LoginType;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.service.JwtAccessTokenService;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.vo.AccessToken;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.vo.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.APIX_022;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.filter.converter.ConverterHelper.candidateDetailsToDomainWithProfile;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.filter.converter.ConverterHelper.employeeDetailsToDomainWithProfiles;

@Service
public final class LoginUseCase {
    private final AuthenticationManager authenticationManager;
    private final JwtAccessTokenService jwtAccessTokenService;
    private final ApplicationEventPublisher publisher;

    @Autowired
    public LoginUseCase(
            final AuthenticationManager authenticationManager,
            final JwtAccessTokenService jwtAccessTokenService,
            final ApplicationEventPublisher publisher
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtAccessTokenService = jwtAccessTokenService;
        this.publisher = publisher;
    }

    public AccessToken login(final String username, final String password, final LoginType loginType) {
        if (loginType == LoginType.CAN)
            return authenticateCandidate(username, password);

        return authenticateEmployee(username, password);
    }

    private AccessToken authenticateCandidate(String username, String password) {
        CustomUserDetails userDetails;
        try {
            final Authentication authenticate = this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            userDetails = (CustomUserDetails) authenticate.getPrincipal();
        } catch (final AuthenticationException ex) {
            this.publisher.publishEvent(new FailedLogin(username));
            throw new BusinessException(APIX_022, "credentials are invalid");
        }

        final AccessToken credentialAccess = this.jwtAccessTokenService.generateCredentialAccess(
                candidateDetailsToDomainWithProfile(userDetails.getId(), userDetails)
        );
        this.publisher.publishEvent(new LoginSuccessfullyEvent(username));
        return credentialAccess;
    }

    private AccessToken authenticateEmployee(String username, String password) {
        CustomUserDetails userDetails;
        try {
            final Authentication authenticate = this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            userDetails = (CustomUserDetails) authenticate.getPrincipal();
        } catch (final AuthenticationException ex) {
            this.publisher.publishEvent(new FailedLogin(username));
            throw new BusinessException(APIX_022, "credentials are invalid");
        }

        final AccessToken credentialAccess = this.jwtAccessTokenService.generateCredentialAccess(
                employeeDetailsToDomainWithProfiles(userDetails.getId(), userDetails)
        );
        this.publisher.publishEvent(new LoginSuccessfullyEvent(username));
        return credentialAccess;
    }

}

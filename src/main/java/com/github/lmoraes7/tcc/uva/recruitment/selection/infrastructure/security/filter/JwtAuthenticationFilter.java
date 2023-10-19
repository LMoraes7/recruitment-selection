package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.filter;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidate;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.context.SecurityCandidateContext;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.context.SecurityEmployeeContext;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.repository.UserDetailsRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.service.JwtAccessTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.filter.converter.ConverterHelper.candidateDetailsToDomain;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.filter.converter.ConverterHelper.employeeDetailsToDomain;


public final class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtAccessTokenService jwtTokenService;
    private final UserDetailsRepository userDetailsRepository;

    public JwtAuthenticationFilter(
            final JwtAccessTokenService jwtTokenService,
            final UserDetailsRepository userDetailsRepository
    ) {
        this.jwtTokenService = jwtTokenService;
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain
    ) throws ServletException, IOException {
        final var accessToken = retrieveAccessToken(request);
        final var isValid = this.jwtTokenService.credentialAccessIsValid(accessToken);

        if (isValid)
            authenticateClient(accessToken);

        filterChain.doFilter(request, response);
    }

    private String retrieveAccessToken(final HttpServletRequest request) {
        final var acessToken = request.getHeader("Authorization");
        if (acessToken == null || !acessToken.startsWith("Bearer "))
            return null;
        return acessToken.substring(7);
    }

    private void authenticateClient(final String acessToken) {
        final var ownerIdentifier = this.jwtTokenService.getOwnerIdentifier(acessToken);
        if (ownerIdentifier.startsWith("EMP")) {
            this.userDetailsRepository.findEmployeeByIdWithAccessProfile(ownerIdentifier).ifPresent((it -> {
                final Employee employee = employeeDetailsToDomain(ownerIdentifier, it);
                final var authentication = new UsernamePasswordAuthenticationToken(
                        employee,
                        null,
                        it.getAuthorities()
                );

                final var securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authentication);

                SecurityContextHolder.setContext(securityContext);
                SecurityEmployeeContext.setContext(employee);
            }));
        } else {
            this.userDetailsRepository.findCandidateByIdWithAccessProfile(ownerIdentifier).ifPresent((it -> {
                final Candidate candidate = candidateDetailsToDomain(ownerIdentifier, it);
                final var authentication = new UsernamePasswordAuthenticationToken(
                        candidate,
                        null,
                        it.getAuthorities()
                );

                final var securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authentication);

                SecurityContextHolder.setContext(securityContext);
                SecurityCandidateContext.setContext(candidate);
            }));
        }
    }

}

package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.service;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidate;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Profile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.vo.AccessToken;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashSet;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

@Service
public final class JwtAccessTokenService {

    private final Key key;
    private final String expiration;

    public JwtAccessTokenService(
            final Key key,
            @Value("${api.jwt.expiration}")
            final String expiration
    ) {
        this.key = key;
        this.expiration = expiration;
    }

    public AccessToken generateCredentialAccess(final Candidate candidate) {
        final var now = new Date();
        final var expirationDate = new Date(now.getTime() + SECONDS.toMillis(Long.parseLong(expiration)));
        final HashSet<String> functionalities = new HashSet<>();

        for (Profile profile : candidate.getAccessCredentials().getProfiles()) {
            functionalities.addAll(profile.getFunctions().stream().map(it -> it.getName().name()).collect(Collectors.toSet()));
        }

        final String acessToken = Jwts.builder()
                .setIssuer("api-recruitment-selection")
                .setSubject(candidate.getIdentifier())
                .setAudience(candidate.getAccessCredentials().getUsername())
                .setExpiration(expirationDate)
                .setIssuedAt(now)
                .claim("scopes", functionalities)
                .signWith(key)
                .compact();

        return new AccessToken(
                "Bearer",
                acessToken,
                MILLISECONDS.toMinutes(now.getTime()),
                MILLISECONDS.toMinutes(expirationDate.getTime())
        );
    }

    public AccessToken generateCredentialAccess(final Employee employee) {
        final var now = new Date();
        final var expirationDate = new Date(now.getTime() + SECONDS.toMillis(Long.parseLong(expiration)));
        final HashSet<String> functionalities = new HashSet<>();

        for (Profile profile : employee.getAccessCredentials().getProfiles()) {
            functionalities.addAll(profile.getFunctions().stream().map(it -> it.getName().name()).collect(Collectors.toSet()));
        }

        final String acessToken = Jwts.builder()
                .setIssuer("api-recruitment-selection")
                .setSubject(employee.getIdentifier())
                .setAudience(employee.getAccessCredentials().getUsername())
                .setExpiration(expirationDate)
                .setIssuedAt(now)
                .claim("scopes", functionalities)
                .signWith(key)
                .compact();

        return new AccessToken(
                "Bearer",
                acessToken,
                MILLISECONDS.toMinutes(now.getTime()),
                MILLISECONDS.toMinutes(expirationDate.getTime())
        );
    }

    public String getOwnerIdentifier(final String hash) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(hash).getBody().getSubject();
    }

    public boolean credentialAccessIsValid(final String hash) {
        if (hash == null)
            return false;

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(hash);
            return true;
        } catch (final JwtException ex) {
            return false;
        }
    }

}

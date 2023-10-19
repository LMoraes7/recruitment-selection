package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.config;

import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.filter.JwtAuthenticationFilter;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.repository.UserDetailsRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.service.CustomUserDetailsService;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.service.JwtAccessTokenService;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.Key;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChainConfig(
            final HttpSecurity http,
            final JwtAccessTokenService jwtTokenService,
            final UserDetailsRepository userDetailsRepository
    ) throws Exception {
        return http.authorizeHttpRequests((requests) -> {
            try {
                requests.requestMatchers(PATCH, "/candidacy/close/**").authenticated()
                        .requestMatchers(GET, "/candidacy").authenticated()
                        .requestMatchers(GET, "/candidacy/**").authenticated()
                        .requestMatchers(POST, "/candidacy/selective-process/**").authenticated()
                        .requestMatchers(POST, "/candidate").permitAll()
                        .requestMatchers(POST, "/common/redefine-password").permitAll()
                        .requestMatchers(POST, "/common/reset-password").permitAll()
                        .requestMatchers(POST, "/employee").authenticated()
                        .requestMatchers(POST, "/feedback/candidacy/**").authenticated()
                        .requestMatchers(POST, "/feedback/candidacy/**").authenticated()
                        .requestMatchers(POST, "/login").permitAll()
                        .requestMatchers(POST, "/profile").authenticated()
                        .requestMatchers(POST, "/question").authenticated()
                        .requestMatchers(PATCH, "/selective-process/**").authenticated()
                        .requestMatchers(GET, "/selective-process").permitAll()
                        .requestMatchers(GET, "/selective-process").permitAll()
                        .requestMatchers(GET, "/selective-process/**").permitAll()
                        .requestMatchers(POST, "/selective-process").authenticated()
                        .requestMatchers(GET, "/step/**/type/**/application/**").authenticated()
                        .requestMatchers(GET, "/step/**/type/**/candidacy/**/selective-process/**").authenticated()
                        .requestMatchers(POST, "/step").authenticated()
                        .requestMatchers(POST, "/step/**").authenticated()
                        .requestMatchers(PATCH, "/step/**").authenticated()
                        .anyRequest().authenticated()
                        .and().csrf().disable()
                        .sessionManagement().sessionCreationPolicy(STATELESS)
                        .and().addFilterBefore(
                                new JwtAuthenticationFilter(jwtTokenService, userDetailsRepository),
                                UsernamePasswordAuthenticationFilter.class
                        );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).build();
    }

    @Bean
    public AuthenticationManager authenticationManagerConfig(
            final HttpSecurity http,
            final PasswordEncoder passwordEncoder,
            final CustomUserDetailsService customUserDetailsService
    ) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder)
                .and().build();
    }

    @Bean
    public Key keyConfig() {
        return Keys.secretKeyFor(HS256);
    }

}

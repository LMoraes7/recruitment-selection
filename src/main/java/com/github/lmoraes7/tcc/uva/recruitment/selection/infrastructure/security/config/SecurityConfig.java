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
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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
                requests.antMatchers(PATCH, "/candidacy/close/**").authenticated()//.hasRole("FUNC_CLOSE_CANDIDACY")
                        .antMatchers(GET, "/candidacy").authenticated()//.hasRole("FUNC_CONSULT_CANDIDACY")
                        .antMatchers(GET, "/candidacy/**").authenticated()//.hasRole("FUNC_CONSULT_ALL_CANDIDACY")
                        .antMatchers(POST, "/candidacy/**").authenticated()//.hasRole("FUNC_CREATE_CANDIDACY")
                        .antMatchers(POST, "/candidate").permitAll()
                        .antMatchers(POST, "/common/reset-password", "/common/redefine-password").permitAll()
                        .antMatchers(POST, "/employee").authenticated()//.hasRole("FUNC_CREATE_EMPLOYEE")
                        .antMatchers(POST, "/feedback/**").authenticated()//.hasRole("FUNC_CREATE_FEEDBACK")
                        .antMatchers(POST, "/login").permitAll()
                        .antMatchers(POST, "/profile").authenticated()//.hasRole("FUNC_CREATE_PROFILE")
                        .antMatchers(POST, "/question").authenticated()//.hasRole("FUNC_CREATE_QUESTION")
                        .antMatchers(PATCH, "/selective-process/**").authenticated()//.hasRole("FUNC_CLOSE_SELECTIVE_PROCESS")
                        .antMatchers(GET, "/selective-process").permitAll()
                        .antMatchers(GET, "/selective-process/**").permitAll()
                        .antMatchers(POST, "/selective-process").authenticated()//.hasRole("FUNC_CREATE_SELECTIVE_PROCESS")
                        .antMatchers(GET, "/step/**/type/**/application/**").authenticated()//.hasRole("FUNC_CONSULT_STEP_ANSWERS")
                        .antMatchers(GET, "/step/**/type/**/candidacy/**/selective-process/**").authenticated()//.hasRole("FUNC_CONSULT_STEP")
                        .antMatchers(POST, "/step/EXTERNAL").authenticated()//.hasRole("FUNC_CREATE_STEP")
                        .antMatchers(POST, "/step/UPLOAD_FILES").authenticated()//.hasRole("FUNC_CREATE_STEP")
                        .antMatchers(POST, "/step/THEORETICAL_TEST").authenticated()//.hasRole("FUNC_CREATE_STEP")
                        .antMatchers(POST, "/step/**/type/THEORETICAL_TEST/**").authenticated()//.hasRole("FUNC_EXEC_STEP")
                        .antMatchers(POST, "/step/**/type/UPLOAD_FILES/**").authenticated()//.hasRole("FUNC_EXEC_STEP")
                        .antMatchers(PATCH, "/step/**").authenticated()//.hasRole("FUNC_RELEASE_STEP")
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

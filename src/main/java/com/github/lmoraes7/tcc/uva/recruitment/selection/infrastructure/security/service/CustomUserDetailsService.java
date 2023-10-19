package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.service;

import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.repository.UserDetailsRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public final class CustomUserDetailsService implements UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;

    public CustomUserDetailsService(final UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final Optional<UserDetails> optionalCandidate = this.userDetailsRepository.findCandidateByUsername(username);
        if (optionalCandidate.isPresent())
            return optionalCandidate.get();

        return this.userDetailsRepository.findEmployeeByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("credentials are invalid"));
    }
}

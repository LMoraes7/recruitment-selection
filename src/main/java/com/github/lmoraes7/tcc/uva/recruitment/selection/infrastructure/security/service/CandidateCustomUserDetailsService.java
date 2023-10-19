package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.service;

import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.repository.UserDetailsRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public final class CandidateCustomUserDetailsService implements UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;

    public CandidateCustomUserDetailsService(final UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return this.userDetailsRepository.findCandidateByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("credentials are invalid"));
    }
}

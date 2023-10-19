package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.context;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidate;

public class SecurityCandidateContext {
    private static ThreadLocal<Candidate> candidateContext = new ThreadLocal<>();

    public static void setContext(final Candidate candidate) {
        candidateContext.set(candidate);
    }

    public static Candidate getContext() {
        return candidateContext.get();
    }

    public static void clearContext() {
        candidateContext.remove();
    }

}

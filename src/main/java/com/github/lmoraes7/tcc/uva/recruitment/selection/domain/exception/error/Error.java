package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error;

public enum Error {
//  Nome de Perfil já utilizado
    APIX_001("apix-001.error"),
//  Funcionalidades informadas não existem
    APIX_002("apix-002.error"),
//  CPF ou email informado já foram cadastrados
    APIX_003("apix-003.error"),
//  Perfis informados não existem
    APIX_004("apix-004.error"),
//  Erro ao buscar o perfil de candidato
    INTG_001("intg-001.error");

    public final String code;

    Error(final String code) {
        this.code = code;
    }

}

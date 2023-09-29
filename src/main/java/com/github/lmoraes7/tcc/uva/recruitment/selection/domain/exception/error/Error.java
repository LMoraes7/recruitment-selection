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
//  Código de reset de senha já utilizado
    APIX_005("apix-005.error"),
//  Código de reset de senha já expirado
    APIX_006("apix-006.error"),
//  Email informado para reset de senha não existe
    APIX_007("apix-007.error"),
//  Questões informadas não existem
    APIX_008("apix-008.error"),
//  Questões informadas não são do mesmo tipo
    APIX_009("apix-009.error"),
//  Etapas informadas não existem
    APIX_010("apix-010.error"),
//  Etapas externas informadas não podem receber deadline
    APIX_011("apix-011.error"),
//  Processo seletivo informado não pode mais receber inscrições pq está fechado
    APIX_012("apix-012.error"),
//  Etapa informada não se encotra no status aguardando execução para ser executada
    APIX_013("apix-013.error"),
//  Etapa informada já passou da validade para ser executada
    APIX_014("apix-014.error"),
//  Etapa de questão foi informada para ser executa contendo 2 tipos diferentes de questão
    APIX_015("apix-015.error"),
//  Etapa de envio de arquivos teve mais ou menos arquivos enviados do que o definido na etapa
    APIX_016("apix-016.error"),
//  Etapa de envio de arquivos teve arquivos de tipos inválidos
    APIX_017("apix-017.error"),
//  Erro ao buscar o perfil de candidato
    INTG_001("intg-001.error"),
//  Erro interno genérico
    INTG_002("intg-002.error");

    public final String code;

    Error(final String code) {
        this.code = code;
    }

}

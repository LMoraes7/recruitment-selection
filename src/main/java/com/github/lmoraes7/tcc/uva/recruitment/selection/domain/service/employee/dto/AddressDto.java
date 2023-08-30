package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.employee.dto;

public final class AddressDto {
    private final Integer number;
    private final String complement;
    private final String cep;
    private String place;
    private String neighborhood;
    private String locality;
    private String uf;

    public AddressDto(final Integer number, final String complement, final String cep) {
        this.number = number;
        this.complement = complement;
        this.cep = cep;
    }

    public Integer getNumber() {
        return number;
    }

    public String getComplement() {
        return complement;
    }

    public String getCep() {
        return cep;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

}

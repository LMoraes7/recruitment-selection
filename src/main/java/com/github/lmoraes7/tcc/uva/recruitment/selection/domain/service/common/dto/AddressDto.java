package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.common.dto;

import java.util.Objects;

public final class AddressDto {
    private final Integer number;
    private final String complement;
    private final String cep;
    private final String place;
    private final String neighborhood;
    private final String locality;
    private final String uf;

    public AddressDto(
            final Integer number,
            final String complement,
            final String cep,
            final String place,
            final String neighborhood,
            final String locality,
            final String uf
    ) {
        this.number = number;
        this.complement = complement;
        this.cep = cep;
        this.place = place;
        this.neighborhood = neighborhood;
        this.locality = locality;
        this.uf = uf;
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

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getLocality() {
        return locality;
    }

    public String getUf() {
        return uf;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AddressDto that = (AddressDto) object;
        return Objects.equals(number, that.number) && Objects.equals(complement, that.complement) && Objects.equals(cep, that.cep) && Objects.equals(place, that.place) && Objects.equals(neighborhood, that.neighborhood) && Objects.equals(locality, that.locality) && Objects.equals(uf, that.uf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, complement, cep, place, neighborhood, locality, uf);
    }

}

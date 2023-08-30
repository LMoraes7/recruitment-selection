package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo;

import java.util.Objects;

public final class Address {
    private String place;
    private Integer number;
    private String complement;
    private String neighborhood;
    private String locality;
    private String uf;
    private String cep;

    public Address(
            final String place,
            final Integer number,
            final String complement,
            final String neighborhood,
            final String locality,
            final String uf,
            final String cep
    ) {
        this.place = place;
        this.number = number;
        this.complement = complement;
        this.neighborhood = neighborhood;
        this.locality = locality;
        this.uf = uf;
        this.cep = cep;
    }

    public String getPlace() {
        return place;
    }

    public Integer getNumber() {
        return number;
    }

    public String getComplement() {
        return complement;
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

    public String getCep() {
        return cep;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Address address = (Address) object;
        return Objects.equals(place, address.place) && Objects.equals(number, address.number) && Objects.equals(complement, address.complement) && Objects.equals(neighborhood, address.neighborhood) && Objects.equals(locality, address.locality) && Objects.equals(uf, address.uf) && Objects.equals(cep, address.cep);
    }

    @Override
    public int hashCode() {
        return Objects.hash(place, number, complement, neighborhood, locality, uf, cep);
    }

}

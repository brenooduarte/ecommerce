package com.ecommerce.domain.dto.view;

import com.ecommerce.domain.models.Address;
import com.ecommerce.domain.models.City;
import lombok.Data;

@Data
public class AddressDTOView {

    private Long id;

    private String cep;

    private String street;

    private String number;

    private String additional;

    private String neighborhood;

    private City city;

    public AddressDTOView(Address address){
        this.id = address.getId();
        this.cep = address.getCep();
        this.street = address.getStreet();
        this.number = address.getNumber();
        this.additional = address.getAdditional();
        this.neighborhood = address.getNeighborhood();
        this.city = address.getCity();
    }

}

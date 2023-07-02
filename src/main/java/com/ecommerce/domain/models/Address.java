package com.ecommerce.domain.models;

import com.ecommerce.domain.dto.form.AddressDTOForm;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "tb_address")
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String cep;

    @Column
    private String street;

    @Column
    private String number;

    @Column
    private String additional;

    @Column
    private String neighborhood;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Address(AddressDTOForm addressDTOForm, City city) {
        this.cep = addressDTOForm.getCep();
        this.street = addressDTOForm.getStreet();
        this.number = addressDTOForm.getNumber();
        this.additional = addressDTOForm.getAdditional();
        this.neighborhood = addressDTOForm.getNeighborhood();
        this.city = city;
    }
}

package com.ecommerce.domain.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tb_address")
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

    @Column(name = "city_name")
    private String city;

    @Column(name = "state_name")
    private String state;

}

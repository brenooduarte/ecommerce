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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

}

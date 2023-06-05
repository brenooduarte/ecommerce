package com.ecommerce.domain.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    //TODO Adicionar mapeamento
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "city_address_id")
//    private City city;

}
